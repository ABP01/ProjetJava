package ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Souscription;

public class SouscriptionFormDialog extends JDialog {
    private Souscription souscription;
    private boolean confirmed;
    private JTextField txtIdAbonne;
    private JTextField txtIdAbonnement;
    private JTextField txtDateDebut;

    public SouscriptionFormDialog(Frame owner) {
        this(owner, new Souscription(0, 0, 0, new Date()));
    }

    public SouscriptionFormDialog(Frame owner, Souscription souscription) {
        super(owner, "Formulaire Souscription", true);
        this.souscription = souscription;
        this.confirmed = false;

        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("ID Abonné:"));
        txtIdAbonne = new JTextField(String.valueOf(souscription.getIdAbonne()));
        panel.add(txtIdAbonne);
        panel.add(new JLabel("ID Abonnement:"));
        txtIdAbonnement = new JTextField(String.valueOf(souscription.getIdAbonnement()));
        panel.add(txtIdAbonnement);
        panel.add(new JLabel("Date de début:"));
        txtDateDebut = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(souscription.getDateDebut()));
        panel.add(txtDateDebut);
        add(panel, BorderLayout.CENTER);

        JButton btnSave = new JButton("Enregistrer");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    souscription.setIdAbonne(Integer.parseInt(txtIdAbonne.getText()));
                    souscription.setIdAbonnement(Integer.parseInt(txtIdAbonnement.getText()));
                    souscription.setDateDebut(new SimpleDateFormat("yyyy-MM-dd").parse(txtDateDebut.getText()));
                    confirmed = true;
                    dispose();
                } catch (NumberFormatException | ParseException ex) {
                    JOptionPane.showMessageDialog(SouscriptionFormDialog.this, "Erreur de format des données.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnSave, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Souscription getSouscription() {
        return souscription;
    }
}