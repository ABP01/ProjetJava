package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.AbonneDAO;
import dao.AbonnementDAO;
import dao.SouscriptionDAO;
import models.Abonne;
import models.Abonnement;
import models.Souscription;

public class MainMenu extends JFrame {
    private JPanel contentPane;
    private DefaultTableModel abonnesModel;
    private JTable tableAbonnes;
    private JTable tableAbonnements;
    private JTable tableSouscriptions;
    private JTextField searchField;
    private List<Souscription> souscriptionsList;
    private DefaultTableModel souscriptionsModel;

    public MainMenu() {
        setTitle("Système de Gestion des Abonnés et Abonnements - IAI-TOGO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Panneau principal
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Menu principal avec onglets
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // Onglet des abonnés
        JPanel panelAbonnes = new JPanel();
        tabbedPane.addTab("Abonnés", new ImageIcon("icons/subscribers.png"), panelAbonnes, "Gérer les abonnés");
        panelAbonnes.setLayout(new BorderLayout(0, 0));

        // Tableau des abonnés
        abonnesModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Nom", "Prénom", "Date d'inscription", "Numéro de téléphone", "Statut" });
        tableAbonnes = new JTable(abonnesModel);
        panelAbonnes.add(new JScrollPane(tableAbonnes), BorderLayout.CENTER);

        // Panneau d'actions pour les abonnés
        JPanel panelActionsAbonnes = new JPanel();
        panelAbonnes.add(panelActionsAbonnes, BorderLayout.SOUTH);
        panelActionsAbonnes.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnAjouterAbonne = new JButton("Ajouter");
        panelActionsAbonnes.add(btnAjouterAbonne);
        JButton btnModifierAbonne = new JButton("Modifier");
        panelActionsAbonnes.add(btnModifierAbonne);
        JButton btnSupprimerAbonne = new JButton("Supprimer");
        panelActionsAbonnes.add(btnSupprimerAbonne);

        // Panneau de recherche pour les abonnés
        JPanel panelRechercheAbonnes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelAbonnes.add(panelRechercheAbonnes, BorderLayout.NORTH);

        JLabel lblRecherche = new JLabel("Recherche:");
        panelRechercheAbonnes.add(lblRecherche);

        searchField = new JTextField();
        searchField.setColumns(20);
        panelRechercheAbonnes.add(searchField);

        JButton btnRecherche = new JButton("Rechercher");
        panelRechercheAbonnes.add(btnRecherche);

        // ActionListener pour le bouton de recherche
        btnRecherche.addActionListener(e -> {
            String searchText = searchField.getText();
            rechercherAbonne(searchText);
        });

        // ActionListener pour les boutons des abonnés
        btnAjouterAbonne.addActionListener(e -> new GestionAbonnesFrame().setVisible(true));
        btnModifierAbonne.addActionListener(e -> {
            int selectedRow = tableAbonnes.getSelectedRow();
            if (selectedRow != -1) {
                int abonneId = (int) tableAbonnes.getValueAt(selectedRow, 0);
                new GestionAbonnesFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné à modifier.");
            }
        });
        btnSupprimerAbonne.addActionListener(e -> {
            int selectedRow = tableAbonnes.getSelectedRow();
            if (selectedRow != -1) {
                int abonneId = (int) tableAbonnes.getValueAt(selectedRow, 0);
                AbonneDAO.deleteAbonne(abonneId);
                chargerAbonnes();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné à supprimer.");
            }
        });

        // Onglet des abonnements
        JPanel panelAbonnements = new JPanel();
        tabbedPane.addTab("Abonnements", new ImageIcon("icons/subscriptions.png"), panelAbonnements,
                "Gérer les abonnements");
        panelAbonnements.setLayout(new BorderLayout(0, 0));

        // Tableau des abonnements
        tableAbonnements = new JTable(new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Libellé", "Durée (mois)", "Prix Mensuel" }));
        panelAbonnements.add(new JScrollPane(tableAbonnements), BorderLayout.CENTER);

        // Panneau d'actions pour les abonnements
        JPanel panelActionsAbonnements = new JPanel();
        panelAbonnements.add(panelActionsAbonnements, BorderLayout.SOUTH);
        panelActionsAbonnements.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnAjouterAbonnement = new JButton("Ajouter");
        panelActionsAbonnements.add(btnAjouterAbonnement);
        JButton btnModifierAbonnement = new JButton("Modifier");
        panelActionsAbonnements.add(btnModifierAbonnement);
        JButton btnSupprimerAbonnement = new JButton("Supprimer");
        panelActionsAbonnements.add(btnSupprimerAbonnement);

        // ActionListener pour les boutons des abonnements
        btnAjouterAbonnement.addActionListener(e -> new GestionAbonnementsFrame(0).setVisible(true));
        btnModifierAbonnement.addActionListener(e -> {
            int selectedRow = tableAbonnements.getSelectedRow();
            if (selectedRow != -1) {
                int abonnementId = (int) tableAbonnements.getValueAt(selectedRow, 0);
                new GestionAbonnementsFrame(abonnementId).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonnement à modifier.");
            }
        });
        btnSupprimerAbonnement.addActionListener(e -> {
            int selectedRow = tableAbonnements.getSelectedRow();
            if (selectedRow != -1) {
                int abonnementId = (int) tableAbonnements.getValueAt(selectedRow, 0);
                try {
                    AbonnementDAO.deleteAbonnement(abonnementId);
                    chargerAbonnements();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'abonnement.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonnement à supprimer.");
            }
        });

        // Onglet des souscriptions
        JPanel panelSouscriptions = new JPanel();
        tabbedPane.addTab("Souscriptions", new ImageIcon("icons/subscriptions.png"), panelSouscriptions,
                "Gérer les souscriptions");
        panelSouscriptions.setLayout(new BorderLayout(0, 0));

        // Tableau des souscriptions
        souscriptionsModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "ID Abonné", "ID Abonnement", "Date de début" });
        tableSouscriptions = new JTable(souscriptionsModel);
        panelSouscriptions.add(new JScrollPane(tableSouscriptions), BorderLayout.CENTER);

        // Panneau d'actions pour les souscriptions
        JPanel panelActionsSouscriptions = new JPanel();
        panelSouscriptions.add(panelActionsSouscriptions, BorderLayout.SOUTH);
        panelActionsSouscriptions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnAjouterSouscription = new JButton("Ajouter");
        panelActionsSouscriptions.add(btnAjouterSouscription);
        JButton btnModifierSouscription = new JButton("Modifier");
        panelActionsSouscriptions.add(btnModifierSouscription);
        JButton btnSupprimerSouscription = new JButton("Supprimer");
        panelActionsSouscriptions.add(btnSupprimerSouscription);

        // ActionListener pour les boutons des souscriptions
        btnAjouterSouscription.addActionListener(e -> {
            String idAbonneStr = JOptionPane.showInputDialog(this, "Entrez l'ID de l'abonné:");
            String idAbonnementStr = JOptionPane.showInputDialog(this, "Entrez l'ID de l'abonnement:");
            if (idAbonneStr != null && idAbonnementStr != null) {
                try {
                    int idAbonne = Integer.parseInt(idAbonneStr);
                    int idAbonnement = Integer.parseInt(idAbonnementStr);
                    Souscription souscription = new Souscription(0, idAbonne, idAbonnement, new java.util.Date());
                    SouscriptionDAO souscriptionDAO = new SouscriptionDAO();
                    souscriptionDAO.addSouscription(souscription);
                    chargerSouscriptions();
                } catch (NumberFormatException | SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la souscription.");
                }
            }
        });

        btnModifierSouscription.addActionListener(e -> {
            int selectedRow = tableSouscriptions.getSelectedRow();
            if (selectedRow != -1) {
                int modelRow = tableSouscriptions.convertRowIndexToModel(selectedRow);
                Souscription selectedSouscription = souscriptionsList.get(modelRow);
                SouscriptionFormDialog dialog = new SouscriptionFormDialog(this, selectedSouscription);
                dialog.setVisible(true);

                if (dialog.isConfirmed()) {
                    Souscription updatedSouscription = dialog.getSouscription();
                    try {
                        SouscriptionDAO.updateSouscription(updatedSouscription);
                        souscriptionsList.set(modelRow, updatedSouscription);
                        souscriptionsModel.setValueAt(updatedSouscription.getIdAbonne(), modelRow, 1);
                        souscriptionsModel.setValueAt(updatedSouscription.getIdAbonnement(), modelRow, 2);
                        souscriptionsModel.setValueAt(updatedSouscription.getDateDebut(), modelRow, 3);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la modification de la souscription.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une souscription à modifier.");
            }
        });

        btnSupprimerSouscription.addActionListener(e -> {
            int selectedRow = tableSouscriptions.getSelectedRow();
            if (selectedRow != -1) {
                int modelRow = tableSouscriptions.convertRowIndexToModel(selectedRow);
                try {
                    SouscriptionDAO.deleteSouscription(souscriptionsList.get(modelRow).getId());
                    souscriptionsList.remove(modelRow);
                    souscriptionsModel.removeRow(modelRow);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de la souscription.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une souscription à supprimer.");
            }
        });

        // Onglet des statistiques
        JPanel panelStatistiques = new JPanel();
        tabbedPane.addTab("Statistiques", new ImageIcon("icons/statistics.png"), panelStatistiques,
                "Voir les statistiques");
        panelStatistiques.setLayout(new BorderLayout(0, 0));

        // Contenu des statistiques
        JTextArea textAreaStatistiques = new JTextArea();
        textAreaStatistiques.setEditable(false);
        panelStatistiques.add(new JScrollPane(textAreaStatistiques), BorderLayout.CENTER);

        // Ajout d'un panneau de titre
        JPanel panelTitle = new JPanel();
        contentPane.add(panelTitle, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("SYSTÈME DE GESTION DES ABONNÉS ET ABONNEMENTS");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitle.add(lblTitle);

        // Ajout de l'action de déconnexion dans le menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem logout = new JMenuItem("Déconnexion", new ImageIcon("icons/logout.png"));
        menu.add(logout);
        setJMenuBar(menuBar);

        // ActionListener pour le bouton de déconnexion
        logout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });

        // Charger les abonnés au démarrage
        chargerAbonnes();
        chargerAbonnements();
        chargerSouscriptions();

        // Afficher la fenêtre
        setVisible(true);
    }

    private void rechercherAbonne(String searchText) {
        DefaultTableModel model = (DefaultTableModel) tableAbonnes.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableAbonnes.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchText));
    }

    private void chargerAbonnes() {
        List<Abonne> abonnes = AbonneDAO.getAbonnes();
        DefaultTableModel model = (DefaultTableModel) tableAbonnes.getModel();
        model.setRowCount(0); // Clear existing rows

        for (Abonne abonne : abonnes) {
            model.addRow(new Object[] {
                    abonne.getId(),
                    abonne.getNom(),
                    abonne.getPrenom(),
                    abonne.getDateInscription(),
                    abonne.getNumeroTelephone(),
                    abonne.getAbonnementActif() ? "Actif" : "Inactif"
            });
        }
    }

    private void chargerAbonnements() {
        try {
            List<Abonnement> abonnements = AbonnementDAO.getAllAbonnements();
            DefaultTableModel model = (DefaultTableModel) tableAbonnements.getModel();
            model.setRowCount(0); // Clear existing rows

            for (Abonnement abonnement : abonnements) {
                model.addRow(new Object[] {
                        abonnement.getId(),
                        abonnement.getLibelleOffre(),
                        abonnement.getDureeMois(),
                        abonnement.getPrixMensuel()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chargerSouscriptions() {
        try {
            souscriptionsList = SouscriptionDAO.getAllSouscriptions();
            DefaultTableModel model = (DefaultTableModel) tableSouscriptions.getModel();
            model.setRowCount(0); // Clear existing rows

            for (Souscription souscription : souscriptionsList) {
                model.addRow(new Object[] {
                        souscription.getId(),
                        souscription.getIdAbonne(),
                        souscription.getIdAbonnement(),
                        souscription.getDateDebut()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Exécuter l'application
        SwingUtilities.invokeLater(MainMenu::new);
    }
}