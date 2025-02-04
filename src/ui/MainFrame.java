package ui;

import dao.AbonneDAO;
import dao.AbonnementDAO;
import models.Abonne;
import models.Abonnement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class MainFrame extends JFrame {
    private JTable abonneTable;
    private JTable abonnementTable;

    public MainFrame() {
        setTitle("Gestion de la salle de sport");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Abonnés", createAbonnePanel());
        tabbedPane.addTab("Abonnements", createAbonnementPanel());

        add(tabbedPane);
    }

    private JPanel createAbonnePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Exemple de tableau pour afficher les abonnés
        String[] columnNames = {"ID", "Nom", "Prénom", "Date Inscription", "Numéro Téléphone", "Statut Souscription"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        abonneTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(abonneTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Récupérer les données de la base de données
        List<Abonne> abonnes = AbonneDAO.getAbonnes();
        for (Abonne abonne : abonnes) {
            Object[] rowData = {
                abonne.getId(),
                abonne.getNom(),
                abonne.getPrenom(),
                abonne.getDateInscription(),
                abonne.getNumeroTelephone(),
                abonne.getAbonnementActif()
            };
            model.addRow(rowData);
        }

        JButton addButton = new JButton("Ajouter un abonné");
        addButton.addActionListener(e -> {
            AddAbonneFrame addAbonneFrame = new AddAbonneFrame();
            addAbonneFrame.setVisible(true);
        });
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAbonnementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Exemple de tableau pour afficher les abonnements
        String[] columnNames = {"ID", "Libellé", "Durée (mois)", "Prix Mensuel"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        abonnementTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(abonnementTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Récupérer les données de la base de données
        List<Abonnement> abonnements = null;
        try {
            abonnements = AbonnementDAO.getAllAbonnements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (abonnements != null) {
            for (Abonnement abonnement : abonnements) {
                Object[] rowData = {
                    abonnement.getId(),
                    abonnement.getLibelleOffre(),
                    abonnement.getDureeMois(),
                    abonnement.getPrixMensuel()
                };
                model.addRow(rowData);
            }
        }

        abonnementTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = abonnementTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int id = (int) model.getValueAt(selectedRow, 0);
                        try {
                            Abonnement abonnement = AbonnementDAO.getAllAbonnements().stream()
                                    .filter(a -> a.getId() == id)
                                    .findFirst()
                                    .orElse(null);
                            if (abonnement != null) {
                                ViewAbonnementFrame viewAbonnementFrame = new ViewAbonnementFrame(abonnement);
                                viewAbonnementFrame.setVisible(true);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        JButton addButton = new JButton("Ajouter un abonnement");
        addButton.addActionListener(e -> {
            AddAbonnementFrame addAbonnementFrame = new AddAbonnementFrame();
            addAbonnementFrame.setVisible(true);
        });
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}