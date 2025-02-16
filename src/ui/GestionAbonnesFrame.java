package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.AbonneDAO;
import models.Abonne;

public class GestionAbonnesFrame extends JFrame {
    private JTable tableAbonnes;
    private JTextField txtNom, txtPrenom, txtTelephone, txtRecherche;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnRechercher;
    private DefaultTableModel abonnesModel;

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

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnRechercher = new JButton("Rechercher");
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnRechercher);

        // Table pour afficher les abonnés
        abonnesModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Nom", "Prénom", "Date d'inscription", "Téléphone", "Statut" });
        tableAbonnes = new JTable(abonnesModel);
        JScrollPane scrollPane = new JScrollPane(tableAbonnes);

        // Ajout des composants à la fenêtre
        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        // Gestion des événements
        btnAjouter.addActionListener(e -> ajouterAbonne());
        btnModifier.addActionListener(e -> modifierAbonne());
        btnSupprimer.addActionListener(e -> supprimerAbonne());
        btnRechercher.addActionListener(e -> rechercherAbonne());

        // Charger les abonnés au démarrage
        chargerAbonnes();
    }

    private void ajouterAbonne() {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String telephone = txtTelephone.getText();

        if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
            return;
        }

        Abonne abonne = new Abonne(0, nom, prenom, new java.util.Date(), telephone, true);
        AbonneDAO.ajouterAbonne(abonne);
        chargerAbonnes();
    }

    private void modifierAbonne() {
        int selectedRow = tableAbonnes.getSelectedRow();
        if (selectedRow != -1) {
            int abonneId = (int) tableAbonnes.getValueAt(selectedRow, 0);
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String telephone = txtTelephone.getText();

            if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
                return;
            }

            Abonne abonne = new Abonne(abonneId, nom, prenom, new java.util.Date(), telephone, true);
            AbonneDAO.updateAbonne(abonne);
            chargerAbonnes();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné à modifier");
        }
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
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné à supprimer");
        }
    }

    private void rechercherAbonne() {
        String searchText = txtNom.getText();
        DefaultTableModel model = (DefaultTableModel) tableAbonnes.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableAbonnes.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchText));
    }

    private void chargerAbonnes() {
        List<Abonne> abonnes = AbonneDAO.getAbonnes();
        abonnesModel.setRowCount(0); // Clear existing rows

        for (Abonne abonne : abonnes) {
            abonnesModel.addRow(new Object[] {
                    abonne.getId(),
                    abonne.getNom(),
                    abonne.getPrenom(),
                    abonne.getDateInscription(),
                    abonne.getNumeroTelephone(),
                    abonne.getAbonnementActif() ? "Actif" : "Inactif"
            });
        }
    }
}