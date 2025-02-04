package ui;

import dao.AbonneDAO;
import models.Abonne;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AbonneListFrame extends JFrame {
    private JTable abonneTable;
    private DefaultTableModel model;
    private JTextField searchField;

    public AbonneListFrame() {
        setTitle("Liste des abonnés");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        searchField = new JTextField();
        searchField.setToolTipText("Rechercher un abonné...");
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText().toLowerCase();
                try {
                    filterTable(searchText);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(searchField, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nom", "Prénom", "Date Inscription", "Numéro Téléphone", "Statut Souscription"};
        model = new DefaultTableModel(columnNames, 0);
        abonneTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(abonneTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        try {
            loadAbonnes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        add(panel);
    }

    private void loadAbonnes() throws SQLException {
        List<Abonne> abonnes = null;
        abonnes = AbonneDAO.getAbonnes();
        if (abonnes != null) {
            for (Abonne abonne : abonnes) {
                Object[] rowData = {
                        abonne.getId(),
                        abonne.getNom(),
                        abonne.getPrenom(),
                        abonne.getDateInscription(),
                        abonne.getNumeroTelephone(),
                        abonne.getAbonnementActif()
                };
                model.addRow(rowData);
            }
        }
    }

    private void filterTable(String searchText) throws SQLException {
        List<Abonne> abonnes = null;
        abonnes = AbonneDAO.getAbonnes();
        if (abonnes != null) {
            List<Abonne> filteredAbonnes = abonnes.stream()
                    .filter(abonne -> abonne.getNom().toLowerCase().contains(searchText) ||
                            abonne.getPrenom().toLowerCase().contains(searchText) ||
                            abonne.getNumeroTelephone().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());

            model.setRowCount(0);
            for (Abonne abonne : filteredAbonnes) {
                Object[] rowData = {
                        abonne.getId(),
                        abonne.getNom(),
                        abonne.getPrenom(),
                        abonne.getDateInscription(),
                        abonne.getNumeroTelephone(),
                        abonne.getAbonnementActif()
                };
                model.addRow(rowData);
            }
        }
    }
}