package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.AbonnementDAO;
import models.Abonnement;

public class GestionAbonnementsFrame extends JFrame {
    private JTable tableAbonnements;
    private JTextField txtLibelle, txtDuree, txtPrix;
    private JButton btnAjouter, btnModifier, btnSupprimer;
    private DefaultTableModel abonnementsModel;

    public GestionAbonnementsFrame() {
        setTitle("Gestion des Abonnements");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel pour les champs de saisie
        JPanel panelForm = new JPanel(new GridLayout(3, 2));
        panelForm.add(new JLabel("Libellé:"));
        txtLibelle = new JTextField();
        panelForm.add(txtLibelle);
        panelForm.add(new JLabel("Durée (mois):"));
        txtDuree = new JTextField();
        panelForm.add(txtDuree);
        panelForm.add(new JLabel("Prix mensuel:"));
        txtPrix = new JTextField();
        panelForm.add(txtPrix);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);

        // Table pour afficher les abonnements
        abonnementsModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Libellé", "Durée (mois)", "Prix Mensuel" });
        tableAbonnements = new JTable(abonnementsModel);
        JScrollPane scrollPane = new JScrollPane(tableAbonnements);

        // Ajout des composants à la fenêtre
        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        // Gestion des événements
        btnAjouter.addActionListener(e -> ajouterAbonnement());
        btnModifier.addActionListener(e -> modifierAbonnement());
        btnSupprimer.addActionListener(e -> supprimerAbonnement());

        // Charger les abonnements au démarrage
        chargerAbonnements();
    }

    private void ajouterAbonnement() {
        String libelle = txtLibelle.getText();
        int duree = Integer.parseInt(txtDuree.getText());
        float prix = Float.parseFloat(txtPrix.getText());

        if (libelle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
            return;
        }

        try {
            Abonnement abonnement = new Abonnement();
            abonnement.setLibelleOffre(libelle);
            abonnement.setDureeMois(duree);
            abonnement.setPrixMensuel(prix);

            AbonnementDAO abonnementDAO = new AbonnementDAO();
            abonnementDAO.addAbonnement(abonnement);
            chargerAbonnements(); // Recharger la liste des abonnements
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'abonnement");
        }
    }

    private void modifierAbonnement() {
        int selectedRow = tableAbonnements.getSelectedRow();
        if (selectedRow != -1) {
            int abonnementId = (int) tableAbonnements.getValueAt(selectedRow, 0);
            String libelle = txtLibelle.getText();
            int duree = Integer.parseInt(txtDuree.getText());
            float prix = Float.parseFloat(txtPrix.getText());

            if (libelle.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
                return;
            }

            try {
                Abonnement abonnement = new Abonnement(abonnementId, libelle, duree, prix);
                AbonnementDAO abonnementDAO = new AbonnementDAO();
                abonnementDAO.updateAbonnement(abonnement);
                chargerAbonnements(); // Recharger la liste des abonnements
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification de l'abonnement");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonnement à modifier");
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
                    chargerAbonnements(); // Recharger la liste des abonnements
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'abonnement");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonnement à supprimer");
        }
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
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des abonnements");
        }
    }
}