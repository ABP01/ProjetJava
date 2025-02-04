package ui;

import dao.AbonnementDAO;
import models.Abonnement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAbonnementFrame extends JFrame {
    private JTextField libelleField;
    private JTextField dureeField;
    private JTextField prixField;

    public AddAbonnementFrame() {
        setTitle("Ajouter un abonnement");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Libellé:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        libelleField = new JTextField(20);
        panel.add(libelleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Durée (mois):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        dureeField = new JTextField(20);
        panel.add(dureeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Prix Mensuel:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        prixField = new JTextField(20);
        panel.add(prixField, gbc);

        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(0, 150, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setBackground(new Color(150, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String libelle = libelleField.getText();
                    int duree = Integer.parseInt(dureeField.getText());
                    float prix = Float.parseFloat(prixField.getText());

                    if (libelle.isEmpty() || duree <= 0 || prix <= 0) {
                        JOptionPane.showMessageDialog(AddAbonnementFrame.this, "Veuillez remplir tous les champs correctement.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Abonnement abonnement = new Abonnement(0, libelle, duree, prix);
                    new AbonnementDAO().addAbonnement(abonnement);
                    JOptionPane.showMessageDialog(AddAbonnementFrame.this, "Abonnement ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddAbonnementFrame.this, "Veuillez entrer des valeurs numériques valides pour la durée et le prix.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddAbonnementFrame.this, "Erreur lors de l'ajout de l'abonnement", "Erreur", JOptionPane.ERROR_MESSAGE);
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