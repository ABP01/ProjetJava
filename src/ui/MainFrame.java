package ui;

import dao.AbonneDAO;
import dao.AbonnementDAO;
import models.Abonne;
import models.Abonnement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class MainFrame extends JFrame {
    private JTable abonneTable;
    private JTable abonnementTable;
    private DefaultTableModel abonneTableModel;
    private DefaultTableModel abonnementTableModel;

    public MainFrame() {
        setTitle("Gestion de la salle de sport");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem abonnesMenuItem = new JMenuItem("Abonnés");
        JMenuItem abonnementsMenuItem = new JMenuItem("Abonnements");
        JMenuItem quitMenuItem = new JMenuItem("Quitter");
        menu.add(abonnesMenuItem);
        menu.add(abonnementsMenuItem);
        menu.addSeparator();
        menu.add(quitMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel abonnePanel = createAbonnePanel();
        JPanel abonnementPanel = createAbonnementPanel();
        tabbedPane.addTab("Abonnés", abonnePanel);
        tabbedPane.addTab("Abonnements", abonnementPanel);

        abonnesMenuItem.addActionListener(e -> tabbedPane.setSelectedComponent(abonnePanel));
        abonnementsMenuItem.addActionListener(e -> tabbedPane.setSelectedComponent(abonnementPanel));
        quitMenuItem.addActionListener(e -> System.exit(0));

        add(tabbedPane);
    }

    private JPanel createAbonnePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Nom", "Prénom", "Date Inscription", "Téléphone", "Statut"};
        abonneTableModel = new DefaultTableModel(columnNames, 0);
        abonneTable = new JTable(abonneTableModel);
        JScrollPane scrollPane = new JScrollPane(abonneTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        refreshAbonneTable();
        panel.add(createButtonPanel(() -> refreshAbonneTable()), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAbonnementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Libellé", "Durée", "Prix"};
        abonnementTableModel = new DefaultTableModel(columnNames, 0);
        abonnementTable = new JTable(abonnementTableModel);
        JScrollPane scrollPane = new JScrollPane(abonnementTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        refreshAbonnementTable();
        panel.add(createButtonPanel(() -> refreshAbonnementTable()), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createButtonPanel(Runnable refreshAction) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        JButton refreshButton = new JButton("Rafraîchir");

        addButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ajout en cours..."));
        editButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modification en cours..."));
        deleteButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Suppression en cours..."));
        refreshButton.addActionListener(e -> refreshAction.run());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        return buttonPanel;
    }

    private void refreshAbonneTable() {
        abonneTableModel.setRowCount(0);
        List<Abonne> abonnes = AbonneDAO.getAbonnes();
        for (Abonne abonne : abonnes) {
            abonneTableModel.addRow(new Object[]{
                    abonne.getId(), abonne.getNom(), abonne.getPrenom(), abonne.getDateInscription(), abonne.getNumeroTelephone(), abonne.getAbonnementActif()
            });
        }
    }

    private void refreshAbonnementTable() {
        abonnementTableModel.setRowCount(0);
        try {
            List<Abonnement> abonnements = AbonnementDAO.getAllAbonnements();
            for (Abonnement abonnement : abonnements) {
                abonnementTableModel.addRow(new Object[]{
                        abonnement.getId(), abonnement.getLibelleOffre(), abonnement.getDureeMois(), abonnement.getPrixMensuel()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
