package ui;

import models.Abonnement;
import dao.AbonnementDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAbonnementFrame extends JFrame {
    private Abonnement abonnement;
    private JTextField libelleField;
    private JTextField dureeField;
    private JTextField prixField;

    public EditAbonnementFrame(Abonnement abonnement) {
        this.abonnement = abonnement;
        setTitle("Modifier Abonnement");
        setSize(400, 300);
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
        libelleField = new JTextField(20);
        libelleField.setText(abonnement.getLibelleOffre());
        panel.add(libelleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Durée (mois):"), gbc);

        gbc.gridx = 1;
        dureeField = new JTextField(20);
        dureeField.setText(String.valueOf(abonnement.getDureeMois()));
        panel.add(dureeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Prix Mensuel:"), gbc);

        gbc.gridx = 1;
        prixField = new JTextField(20);
        prixField.setText(String.valueOf(abonnement.getPrixMensuel()));
        panel.add(prixField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String libelle = libelleField.getText();
                    int duree = Integer.parseInt(dureeField.getText());
                    float prix = Float.parseFloat(prixField.getText());

                    if (libelle.isEmpty() || duree <= 0 || prix <= 0) {
                        JOptionPane.showMessageDialog(EditAbonnementFrame.this, "Veuillez remplir tous les champs correctement.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    abonnement.setLibelleOffre(libelle);
                    abonnement.setDureeMois(duree);
                    abonnement.setPrixMensuel(prix);
                    new AbonnementDAO().updateAbonnement(abonnement);
                    JOptionPane.showMessageDialog(EditAbonnementFrame.this, "Abonnement modifié avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(EditAbonnementFrame.this, "Veuillez entrer des valeurs numériques valides pour la durée et le prix.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EditAbonnementFrame.this, "Erreur lors de la modification de l'abonnement", "Erreur", JOptionPane.ERROR_MESSAGE);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Abonnement abonnement = new Abonnement(); // Remplacez par un abonnement réel
            EditAbonnementFrame frame = new EditAbonnementFrame(abonnement);
            frame.setVisible(true);
        });
    }
}