// GestionSouscriptionsFrame.java
package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.SouscriptionDAO;
import models.Souscription;

public class GestionSouscriptionsFrame extends JFrame {
    private JPanel contentPane;
    private DefaultTableModel souscriptionsModel;
    private JTable tableSouscriptions;
    private JTextField searchField;
    private List<Souscription> souscriptionsList;

    public GestionSouscriptionsFrame() {
        setTitle("Gestion des Souscriptions - IAI-TOGO");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        souscriptionsList = new ArrayList<>();

        // Panneau principal
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Tableau des souscriptions
        souscriptionsModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "ID Abonné", "ID Abonnement", "Date de début" });
        tableSouscriptions = new JTable(souscriptionsModel);
        contentPane.add(new JScrollPane(tableSouscriptions), BorderLayout.CENTER);

        // Panneau d'actions pour les souscriptions
        JPanel panelActionsSouscriptions = new JPanel();
        contentPane.add(panelActionsSouscriptions, BorderLayout.SOUTH);
        panelActionsSouscriptions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnAjouterSouscription = new JButton("Ajouter");
        panelActionsSouscriptions.add(btnAjouterSouscription);
        JButton btnModifierSouscription = new JButton("Modifier");
        panelActionsSouscriptions.add(btnModifierSouscription);
        JButton btnSupprimerSouscription = new JButton("Supprimer");
        panelActionsSouscriptions.add(btnSupprimerSouscription);

        // Panneau de recherche pour les souscriptions
        JPanel panelRechercheSouscriptions = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        contentPane.add(panelRechercheSouscriptions, BorderLayout.NORTH);

        JLabel lblRecherche = new JLabel("Recherche:");
        panelRechercheSouscriptions.add(lblRecherche);

        searchField = new JTextField();
        searchField.setColumns(20);
        panelRechercheSouscriptions.add(searchField);

        JButton btnRecherche = new JButton("Rechercher");
        panelRechercheSouscriptions.add(btnRecherche);

        // ActionListener pour le bouton de recherche
        btnRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                rechercherSouscription(searchText);
            }
        });

        // ActionListener pour le bouton d'ajout
        btnAjouterSouscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterSouscription();
            }
        });

        // ActionListener pour le bouton de modification
        btnModifierSouscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierSouscription();
            }
        });

        // ActionListener pour le bouton de suppression
        btnSupprimerSouscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerSouscription();
            }
        });

        // Charger les souscriptions au démarrage
        chargerSouscriptions();

        // Afficher la fenêtre
        setVisible(true);
    }

    private void rechercherSouscription(String searchText) {
        DefaultTableModel model = (DefaultTableModel) tableSouscriptions.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableSouscriptions.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchText));
    }

    private void ajouterSouscription() {
        SouscriptionFormDialog dialog = new SouscriptionFormDialog(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            Souscription newSouscription = dialog.getSouscription();
            try {
                SouscriptionDAO dao = new SouscriptionDAO();
                dao.addSouscription(newSouscription);
                souscriptionsList.add(newSouscription);
                souscriptionsModel.addRow(new Object[] {
                        newSouscription.getId(),
                        newSouscription.getIdAbonne(),
                        newSouscription.getIdAbonnement(),
                        newSouscription.getDateDebut()
                });
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la souscription.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierSouscription() {
        int selectedRow = tableSouscriptions.getSelectedRow();
        if (selectedRow >= 0) {
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
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification de la souscription.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une souscription à modifier.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerSouscription() {
        int selectedRow = tableSouscriptions.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = tableSouscriptions.convertRowIndexToModel(selectedRow);
            int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette souscription ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    SouscriptionDAO.deleteSouscription(souscriptionsList.get(modelRow).getId());
                    souscriptionsList.remove(modelRow);
                    souscriptionsModel.removeRow(modelRow);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de la souscription.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une souscription à supprimer.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des souscriptions.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionSouscriptionsFrame());
    }
}