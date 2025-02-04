package ui;

import dao.AbonnementDAO;
import models.Abonnement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DeleteAbonnementFrame extends JFrame {
    private JTable abonnementTable;

    public DeleteAbonnementFrame() {
        setTitle("Supprimer un abonnement");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "Libellé", "Durée (mois)", "Prix Mensuel"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        abonnementTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(abonnementTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        List<Abonnement> abonnements = null;
        try {
            abonnements = AbonnementDAO.getAllAbonnements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (abonnements != null) {
            for (Abonnement abonnement : abonnements) {
                Object[] rowData = {
                        abonnement.getId(),
                        abonnement.getLibelleOffre(),
                        abonnement.getDureeMois(),
                        abonnement.getPrixMensuel()
                };
                model.addRow(rowData);
            }
        }

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(150, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = abonnementTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    try {
                        AbonnementDAO.deleteAbonnement(id);
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(DeleteAbonnementFrame.this, "Abonnement supprimé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(DeleteAbonnementFrame.this, "Erreur lors de la suppression de l'abonnement", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(DeleteAbonnementFrame.this, "Veuillez sélectionner un abonnement à supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(deleteButton, BorderLayout.SOUTH);

        add(panel);
    }
}