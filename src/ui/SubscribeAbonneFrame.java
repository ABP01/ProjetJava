package ui;

import dao.AbonneDAO;
import dao.AbonnementDAO;
import models.Abonne;
import models.Abonnement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SubscribeAbonneFrame extends JFrame {
    private JComboBox<Abonne> abonneComboBox;
    private JComboBox<Abonnement> abonnementComboBox;

    public SubscribeAbonneFrame() throws SQLException {
        setTitle("Souscrire un abonné");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Abonné:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        abonneComboBox = new JComboBox<>();
        List<Abonne> abonnes = AbonneDAO.getAbonnes();
        for (Abonne abonne : abonnes) {
            abonneComboBox.addItem(abonne);
        }
        panel.add(abonneComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Abonnement:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        abonnementComboBox = new JComboBox<>();
        try {
            List<Abonnement> abonnements = AbonnementDAO.getAllAbonnements();
            for (Abonnement abonnement : abonnements) {
                abonnementComboBox.addItem(abonnement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        panel.add(abonnementComboBox, gbc);

        JButton subscribeButton = new JButton("Souscrire");
        subscribeButton.setBackground(new Color(0, 150, 0));
        subscribeButton.setForeground(Color.WHITE);
        subscribeButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setBackground(new Color(150, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(subscribeButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        subscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Abonne abonne = (Abonne) abonneComboBox.getSelectedItem();
                Abonnement abonnement = (Abonnement) abonnementComboBox.getSelectedItem();
                if (abonne != null && abonnement != null) {
                    AbonneDAO.subscribeAbonne(abonne.getId(), abonnement.getId());
                    JOptionPane.showMessageDialog(SubscribeAbonneFrame.this, "Abonné souscrit avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}