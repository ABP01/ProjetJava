package ui;

import dao.AbonneDAO;
import models.Abonne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AddAbonneFrame extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField numeroTelephoneField;
    private JCheckBox abonnementActifCheckBox;

    public AddAbonneFrame() {
        setTitle("Ajouter un abonné");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nom:"));
        nomField = new JTextField();
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        prenomField = new JTextField();
        panel.add(prenomField);
        panel.add(new JLabel("Numéro de téléphone:"));
        numeroTelephoneField = new JTextField();
        panel.add(numeroTelephoneField);
        panel.add(new JLabel("Abonnement actif:"));
        abonnementActifCheckBox = new JCheckBox();
        panel.add(abonnementActifCheckBox);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String numeroTelephone = numeroTelephoneField.getText();
                boolean abonnementActif = abonnementActifCheckBox.isSelected();

                Abonne abonne = new Abonne(0, nom, prenom, new Date(), numeroTelephone, abonnementActif);
                AbonneDAO.ajouterAbonne(abonne);
                JOptionPane.showMessageDialog(AddAbonneFrame.this, "Abonné ajouté avec succès");
                dispose();
            }
        });
        panel.add(addButton);

        add(panel);
    }
}