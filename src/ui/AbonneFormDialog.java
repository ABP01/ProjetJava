package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Abonne;

public class AbonneFormDialog extends JDialog {
    private JTextField nomField, prenomField, telField, statutField;
    private boolean confirmed = false;
    private Abonne abonne;

    public AbonneFormDialog(JFrame parent) {
        this(parent, null);
    }

    public AbonneFormDialog(JFrame parent, Abonne abonne) {
        super(parent, "Formulaire Abonné", true);
        this.abonne = abonne;

        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nom:"));
        nomField = new JTextField();
        panel.add(nomField);

        panel.add(new JLabel("Prénom:"));
        prenomField = new JTextField();
        panel.add(prenomField);

        panel.add(new JLabel("Numéro de téléphone:"));
        telField = new JTextField();
        panel.add(telField);

        panel.add(new JLabel("Statut Souscription:"));
        statutField = new JTextField();
        panel.add(statutField);

        if (abonne != null) {
            nomField.setText(abonne.getNom());
            prenomField.setText(abonne.getPrenom());
            telField.setText(abonne.getNumeroTelephone());
            statutField.setText(abonne.getStatutSouscription());
        } else {
            this.abonne = new Abonne();
        }

        JButton btnConfirmer = new JButton("Confirmer");
        btnConfirmer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    abonne.setNom(nomField.getText());
                    abonne.setPrenom(prenomField.getText());
                    abonne.setDateInscription(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    abonne.setNumeroTelephone(telField.getText());
                    abonne.setStatutSouscription(statutField.getText());
                    confirmed = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AbonneFormDialog.this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnConfirmer);
        buttonPanel.add(btnAnnuler);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private boolean validateFields() {
        return !nomField.getText().isEmpty() && !prenomField.getText().isEmpty()
                && !telField.getText().isEmpty() && !statutField.getText().isEmpty();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Abonne getAbonne() {
        return abonne;
    }
}