package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import dao.AbonneDAO;
import dao.dbconn;
import models.Abonne;

public class GestionAbonnesFrame extends JFrame {
    private JTable tableAbonnes;
    private JTextField txtNom, txtPrenom, txtTelephone, txtRechercher;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnValider, btnSouscrire, btnRenouveler, btnResilier;
    private DefaultTableModel abonnesModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public GestionAbonnesFrame() {
        setTitle("Gestion des Abonnés");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel pour les champs de saisie
        JPanel panelForm = new JPanel(new GridLayout(4, 2));
        panelForm.add(new JLabel("Nom:"));
        txtNom = new JTextField();
        panelForm.add(txtNom);
        panelForm.add(new JLabel("Prénom:"));
        txtPrenom = new JTextField();
        panelForm.add(txtPrenom);
        panelForm.add(new JLabel("Téléphone:"));
        txtTelephone = new JTextField();
        panelForm.add(txtTelephone);

        // Panel pour la recherche
        JPanel panelRechercher = new JPanel(new BorderLayout());
        panelRechercher.add(new JLabel("Rechercher:"), BorderLayout.WEST);
        txtRechercher = new JTextField();
        panelRechercher.add(txtRechercher, BorderLayout.CENTER);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnValider = new JButton("Valider");
        btnSouscrire = new JButton("Souscrire");
        btnRenouveler = new JButton("Renouveler");
        btnResilier = new JButton("Résilier");
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnValider);
        panelBoutons.add(btnSouscrire);
        panelBoutons.add(btnRenouveler);
        panelBoutons.add(btnResilier);

        // Table pour afficher les abonnés
        abonnesModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Nom", "Prénom", "Téléphone", "Abonné" });
        tableAbonnes = new JTable(abonnesModel);
        sorter = new TableRowSorter<>(abonnesModel);
        tableAbonnes.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(tableAbonnes);

        // Ajout des composants à la fenêtre
        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(panelRechercher, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        // Gestion des événements
        btnAjouter.addActionListener(e -> ajouterAbonne());
        btnModifier.addActionListener(e -> remplirChampsPourModification());
        btnSupprimer.addActionListener(e -> supprimerAbonne());
        btnValider.addActionListener(e -> validerModification());
        btnSouscrire.addActionListener(e -> souscrireAbonnement());
        btnRenouveler.addActionListener(e -> renouvelerAbonnement());
        btnResilier.addActionListener(e -> resilierAbonnement());
        txtRechercher.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                rechercherAbonne();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                rechercherAbonne();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                rechercherAbonne();
            }
        });

        // Charger les abonnés au démarrage
        chargerAbonnes();
    }

    private void ajouterAbonne() {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String telephone = txtTelephone.getText();

        if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        Abonne abonne = new Abonne(0, nom, prenom, new java.util.Date(), telephone, false); // Par défaut, non abonné
        AbonneDAO.ajouterAbonne(abonne);
        chargerAbonnes();
    }

    private void remplirChampsPourModification() {
        int selectedRow = tableAbonnes.getSelectedRow();
        if (selectedRow != -1) {
            txtNom.setText(tableAbonnes.getValueAt(selectedRow, 1).toString());
            txtPrenom.setText(tableAbonnes.getValueAt(selectedRow, 2).toString());
            txtTelephone.setText(tableAbonnes.getValueAt(selectedRow, 3).toString());
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné à modifier.");
        }
    }

    private void validerModification() {
        int selectedRow = tableAbonnes.getSelectedRow();
        if (selectedRow != -1) {
            int abonneId = (int) tableAbonnes.getValueAt(selectedRow, 0);
            modifierAbonne(abonneId);
        } else {
            JOptionPane.showMessageDialog(this, "Aucune modification à valider.");
        }
    }

    private void modifierAbonne(int abonneId) {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String telephone = txtTelephone.getText();

        if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        Abonne abonne = new Abonne(abonneId, nom, prenom, new java.util.Date(), telephone, false); // Par défaut, non abonné
        AbonneDAO.updateAbonne(abonne);
        chargerAbonnes();
    }

    private void supprimerAbonne() {
        int selectedRow = tableAbonnes.getSelectedRow();
        if (selectedRow != -1) {
            int abonneId = (int) tableAbonnes.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet abonné ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                AbonneDAO.deleteAbonne(abonneId);
                chargerAbonnes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné à supprimer.");
        }
    }

    private void souscrireAbonnement() {
        int selectedRow = tableAbonnes.getSelectedRow();
        if (selectedRow != -1) {
            int abonneId = (int) tableAbonnes.getValueAt(selectedRow, 0);
            int abonnementId = 1; // Example ID, replace with actual logic
            System.out.println("Souscrire abonnement for Abonne ID: " + abonneId + ", Abonnement ID: " + abonnementId);
            AbonneDAO.souscrireAbonnement(abonneId, abonnementId);
            chargerAbonnes();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné.");
        }
    }

    private void renouvelerAbonnement() {
        int selectedRow = tableAbonnes.getSelectedRow();
        if (selectedRow != -1) {
            int abonneId = (int) tableAbonnes.getValueAt(selectedRow, 0);
            System.out.println("Renouveler abonnement for Abonne ID: " + abonneId);
            AbonneDAO.renouvelerAbonnement(abonneId);
            chargerAbonnes();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné.");
        }
    }

    private void resilierAbonnement() {
        int selectedRow = tableAbonnes.getSelectedRow();
        if (selectedRow != -1) {
            int abonneId = (int) tableAbonnes.getValueAt(selectedRow, 0);
            System.out.println("Résilier abonnement for Abonne ID: " + abonneId);
            AbonneDAO.resilierAbonnement(abonneId);
            chargerAbonnes();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné.");
        }
    }

    private void rechercherAbonne() {
        String searchText = txtRechercher.getText();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }

    private void chargerAbonnes() {
        List<Abonne> abonnes = AbonneDAO.getAbonnes();
        abonnesModel.setRowCount(0); // Clear existing rows

        for (Abonne abonne : abonnes) {
            abonnesModel.addRow(new Object[] {
                    abonne.getId(),
                    abonne.getNom(),
                    abonne.getPrenom(),
                    abonne.getNumeroTelephone(),
                    abonne.getAbonnementActif() ? "Oui" : "Non"
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionAbonnesFrame().setVisible(true));
    }
}