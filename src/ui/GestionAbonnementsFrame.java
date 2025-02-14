package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import dao.AbonnementDAO;
import models.Abonnement;

public class GestionAbonnementsFrame extends JFrame {
    private JTable tableAbonnements;
    private JTextField txtLibelle, txtDuree, txtPrix;
    private JButton btnAjouter, btnModifier, btnSupprimer;

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
        tableAbonnements = new JTable();
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
        // Implémentez la logique pour modifier un abonnement
    }

    private void supprimerAbonnement() {
        // Implémentez la logique pour supprimer un abonnement
    }

    private void chargerAbonnements() {
        // Implémentez la logique pour charger les abonnements dans la JTable
    }
}