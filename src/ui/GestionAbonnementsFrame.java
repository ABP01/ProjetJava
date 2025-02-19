package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import dao.AbonnementDAO;
import models.Abonnement;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Font;

public class GestionAbonnementsFrame extends JFrame {
    private JTable tableAbonnements;
    private JTextField txtLibelle, txtDuree, txtPrix, txtRechercher;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnValider;
    private DefaultTableModel abonnementsModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public GestionAbonnementsFrame() {
        SwingUtilities.invokeLater(() -> {
            setTitle("Gestion des Abonnements");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            initUI();
            chargerAbonnements();
            setVisible(true);
        });
    }

    private void initUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel pour les champs de saisie
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Elargir les champs de texte

        txtLibelle = new JTextField();
        txtDuree = new JTextField();
        txtPrix = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(new JLabel("Libellé:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtLibelle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(new JLabel("Durée (mois):"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtDuree, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(new JLabel("Prix Mensuel:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtPrix, gbc);

        panelForm.setBorder(BorderFactory.createTitledBorder("Détails de l'abonnement"));

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnValider = new JButton("Valider");

        btnAjouter.setFont(new Font("Arial", Font.BOLD, 12));
        btnModifier.setFont(new Font("Arial", Font.BOLD, 12));
        btnSupprimer.setFont(new Font("Arial", Font.BOLD, 12));
        btnValider.setFont(new Font("Arial", Font.BOLD, 12));

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnValider);

        // Table pour afficher les abonnements
        abonnementsModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Libellé", "Durée", "Prix Mensuel" });
        tableAbonnements = new JTable(abonnementsModel);
        sorter = new TableRowSorter<>(abonnementsModel);
        tableAbonnements.setRowSorter(sorter);
        tableAbonnements.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableAbonnements.setFillsViewportHeight(true);
        tableAbonnements.setSelectionBackground(Color.YELLOW);

        JTableHeader header = tableAbonnements.getTableHeader();
        header.setBackground(Color.BLUE);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tableAbonnements);

        // Ajout des composants à la fenêtre
        panelPrincipal.add(panelForm, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        
        add(panelPrincipal);

        // Gestion des événements
        btnAjouter.addActionListener(e -> ajouterAbonnement());
        btnModifier.addActionListener(e -> remplirChampsPourModification());
        btnSupprimer.addActionListener(e -> supprimerAbonnement());
        btnValider.addActionListener(e -> validerModification());
    }

    private void ajouterAbonnement() {
        String libelle = txtLibelle.getText();
        int duree;
        float prix;
        try {
            duree = Integer.parseInt(txtDuree.getText());
            prix = Float.parseFloat(txtPrix.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides pour la durée et le prix.");
            return;
        }
        if (libelle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }
        Abonnement abonnement = new Abonnement(0, libelle, duree, prix);
        try {
            AbonnementDAO abonnementDAO = new AbonnementDAO();
            abonnementDAO.addAbonnement(abonnement);
            chargerAbonnements();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'abonnement.");
            e.printStackTrace();
        }
    }

    private void remplirChampsPourModification() {
        int selectedRow = tableAbonnements.getSelectedRow();
        if (selectedRow != -1) {
            txtLibelle.setText(tableAbonnements.getValueAt(selectedRow, 1).toString());
            txtDuree.setText(tableAbonnements.getValueAt(selectedRow, 2).toString());
            txtPrix.setText(tableAbonnements.getValueAt(selectedRow, 3).toString());
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonnement à modifier.");
        }
    }

    private void validerModification() {
        int selectedRow = tableAbonnements.getSelectedRow();
        if (selectedRow != -1) {
            int abonnementId = (int) tableAbonnements.getValueAt(selectedRow, 0);
            modifierAbonnement(abonnementId);
        } else {
            JOptionPane.showMessageDialog(this, "Aucune modification à valider.");
        }
    }

    private void modifierAbonnement(int abonnementId) {
        String libelle = txtLibelle.getText();
        int duree;
        float prix;
        try {
            duree = Integer.parseInt(txtDuree.getText());
            prix = Float.parseFloat(txtPrix.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides pour la durée et le prix.");
            return;
        }
        if (libelle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }
        Abonnement abonnement = new Abonnement(abonnementId, libelle, duree, prix);
        try {
            AbonnementDAO abonnementDAO = new AbonnementDAO();
            abonnementDAO.updateAbonnement(abonnement);
            chargerAbonnements();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour de l'abonnement.");
            e.printStackTrace();
        }
    }

    private void supprimerAbonnement() {
        int selectedRow = tableAbonnements.getSelectedRow();
        if (selectedRow != -1) {
            int abonnementId = (int) tableAbonnements.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet abonnement ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    AbonnementDAO.deleteAbonnement(abonnementId);
                    chargerAbonnements();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'abonnement.");
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonnement à supprimer.");
        }
    }

    private void rechercherAbonnement() {
        String searchText = txtRechercher.getText();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }

    private void chargerAbonnements() {
        try {
            List<Abonnement> abonnements = AbonnementDAO.getAllAbonnements();
            abonnementsModel.setRowCount(0); // Clear existing rows

            for (Abonnement abonnement : abonnements) {
                abonnementsModel.addRow(new Object[] {
                        abonnement.getId(),
                        abonnement.getLibelleOffre(),
                        abonnement.getDureeMois(),
                        abonnement.getPrixMensuel()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des abonnements.");
            e.printStackTrace();
        }
    }
}