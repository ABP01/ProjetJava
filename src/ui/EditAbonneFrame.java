package ui;

import models.Abonne;
import dao.AbonneDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAbonneFrame extends JFrame {
    private Abonne abonne;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateInscriptionField;
    private JTextField numeroTelephoneField;
    private JCheckBox abonnementActifCheckBox;

    public EditAbonneFrame(Abonne abonne) {
        this.abonne = abonne;
        setTitle("Modifier Abonné");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Nom:"));
        nomField = new JTextField(abonne.getNom());
        panel.add(nomField);

        panel.add(new JLabel("Prénom:"));
        prenomField = new JTextField(abonne.getPrenom());
        panel.add(prenomField);

        panel.add(new JLabel("Date Inscription:"));
        dateInscriptionField = new JTextField(abonne.getDateInscription().toString());
        panel.add(dateInscriptionField);

        panel.add(new JLabel("Numéro Téléphone:"));
        numeroTelephoneField = new JTextField(abonne.getNumeroTelephone());
        panel.add(numeroTelephoneField);

        panel.add(new JLabel("Statut Souscription:"));
        abonnementActifCheckBox = new JCheckBox("Actif", abonne.getAbonnementActif());
        panel.add(abonnementActifCheckBox);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAbonne();
            }
        });
        panel.add(saveButton);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);

        add(panel);
    }

    private void saveAbonne() {
        abonne.setNom(nomField.getText());
        abonne.setPrenom(prenomField.getText());
        abonne.setDateInscription(java.sql.Date.valueOf(dateInscriptionField.getText()));
        abonne.setNumeroTelephone(numeroTelephoneField.getText());
        abonne.setAbonnementActif(abonnementActifCheckBox.isSelected());

        try {
            AbonneDAO.updateAbonne(abonne);
            JOptionPane.showMessageDialog(this, "Abonné mis à jour avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour de l'abonné", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}