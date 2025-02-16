package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import dao.AbonneDAO;
import models.Abonne;

public class GestionAbonnesFrame extends JFrame {
    private JPanel contentPane;
    private DefaultTableModel abonnesModel;
    private JTable tableAbonnes;
    private JTextField searchField;
    private ArrayList<Abonne> abonnesList;

    public GestionAbonnesFrame() {
        setTitle("Gestion des Abonnés - IAI-TOGO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        abonnesList = new ArrayList<>();

        // Panneau principal
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Tableau des abonnés
        abonnesModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Nom", "Prénom", "Date d'inscription", "Numéro de téléphone", "Statut" });
        tableAbonnes = new JTable(abonnesModel);
        contentPane.add(new JScrollPane(tableAbonnes), BorderLayout.CENTER);

        // Panneau d'actions pour les abonnés
        JPanel panelActionsAbonnes = new JPanel();
        contentPane.add(panelActionsAbonnes, BorderLayout.SOUTH);
        panelActionsAbonnes.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnAjouterAbonne = new JButton("Ajouter");
        panelActionsAbonnes.add(btnAjouterAbonne);
        JButton btnModifierAbonne = new JButton("Modifier");
        panelActionsAbonnes.add(btnModifierAbonne);
        JButton btnSupprimerAbonne = new JButton("Supprimer");
        panelActionsAbonnes.add(btnSupprimerAbonne);

        // Panneau de recherche pour les abonnés
        JPanel panelRechercheAbonnes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        contentPane.add(panelRechercheAbonnes, BorderLayout.NORTH);

        JLabel lblRecherche = new JLabel("Recherche:");
        panelRechercheAbonnes.add(lblRecherche);

        searchField = new JTextField();
        searchField.setColumns(20);
        panelRechercheAbonnes.add(searchField);

        JButton btnRecherche = new JButton("Rechercher");
        panelRechercheAbonnes.add(btnRecherche);

        // ActionListener pour le bouton de recherche
        btnRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                rechercherAbonne(searchText);
            }
        });

        // ActionListener pour le bouton d'ajout
        btnAjouterAbonne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterAbonne();
            }
        });

        // ActionListener pour le bouton de modification
        btnModifierAbonne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierAbonne();
            }
        });

        // ActionListener pour le bouton de suppression
        btnSupprimerAbonne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerAbonne();
            }
        });

        // Charger les abonnés au démarrage
        chargerAbonnes();

        // Afficher la fenêtre
        setVisible(true);
    }

    private void rechercherAbonne(String searchText) {
        DefaultTableModel model = (DefaultTableModel) tableAbonnes.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableAbonnes.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchText));
    }

    private void ajouterAbonne() {
        AbonneFormDialog dialog = new AbonneFormDialog(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            Abonne newAbonne = dialog.getAbonne();
            abonnesList.add(newAbonne);
            abonnesModel.addRow(new Object[] {
                    newAbonne.getId(),
                    newAbonne.getNom(),
                    newAbonne.getPrenom(),
                    newAbonne.getDateInscription(),
                    newAbonne.getNumeroTelephone(),
                    newAbonne.getStatutSouscription()
            });
        }
    }

    private void modifierAbonne() {
        int selectedRow = tableAbonnes.getSelectedRow();
        if (selectedRow >= 0) {
            Abonne selectedAbonne = abonnesList.get(selectedRow);
            AbonneFormDialog dialog = new AbonneFormDialog(this, selectedAbonne);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                Abonne updatedAbonne = dialog.getAbonne();
                abonnesList.set(selectedRow, updatedAbonne);
                abonnesModel.setValueAt(updatedAbonne.getNom(), selectedRow, 1);
                abonnesModel.setValueAt(updatedAbonne.getPrenom(), selectedRow, 2);
                abonnesModel.setValueAt(updatedAbonne.getDateInscription(), selectedRow, 3);
                abonnesModel.setValueAt(updatedAbonne.getNumeroTelephone(), selectedRow, 4);
                abonnesModel.setValueAt(updatedAbonne.getStatutSouscription(), selectedRow, 5);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné à modifier.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerAbonne() {
        int selectedRow = tableAbonnes.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet abonné ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                abonnesList.remove(selectedRow);
                abonnesModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un abonné à supprimer.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chargerAbonnes() {
        List<Abonne> abonnes = AbonneDAO.getAbonnes();
        abonnesList.clear();
        abonnesModel.setRowCount(0); // Clear existing rows

        for (Abonne abonne : abonnes) {
            abonnesList.add(abonne);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionAbonnesFrame());
    }
}
