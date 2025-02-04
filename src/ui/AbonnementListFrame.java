package ui;

import dao.AbonnementDAO;
import models.Abonnement;
import ui.ViewAbonnementFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class AbonnementListFrame extends JFrame {
    private JTable abonnementTable;

    public AbonnementListFrame() {
        setTitle("Liste des abonnements");
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

        abonnementTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = abonnementTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int id = (int) model.getValueAt(selectedRow, 0);
                        try {
                            Abonnement abonnement = AbonnementDAO.getAllAbonnements().stream()
                                    .filter(a -> a.getId() == id)
                                    .findFirst()
                                    .orElse(null);
                            if (abonnement != null) {
                                ViewAbonnementFrame viewAbonnementFrame = new ViewAbonnementFrame(abonnement);
                                viewAbonnementFrame.setVisible(true);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        JButton addButton = new JButton("Ajouter un abonnement");
        addButton.addActionListener(e -> {
            AddAbonnementFrame addAbonnementFrame = new AddAbonnementFrame();
            addAbonnementFrame.setVisible(true);
        });
        panel.add(addButton, BorderLayout.SOUTH);

        add(panel);
    }
}