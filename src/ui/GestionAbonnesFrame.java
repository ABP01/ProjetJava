    package ui;

    import javax.swing.*;
    import java.awt.*;
    import java.sql.SQLException;
    import java.util.List;
    import dao.AbonneDAO;
    import models.Abonne;

    public class GestionAbonnesFrame extends JFrame {
        private JTable tableAbonnes;
        private JTextField txtNom, txtPrenom, txtTelephone;
        private JButton btnAjouter, btnModifier, btnSupprimer, btnRechercher;

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
            tableAbonnes = new JTable();
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
            // Implémentez la logique pour modifier un abonné
        }

        private void supprimerAbonne() {
            // Implémentez la logique pour supprimer un abonné
        }

        private void rechercherAbonne() {
            // Implémentez la logique pour rechercher un abonné
        }

        private void chargerAbonnes() {
            List<Abonne> abonnes = AbonneDAO.getAbonnes();
            // Implémentez la logique pour charger les abonnés dans la JTable
        }
    }