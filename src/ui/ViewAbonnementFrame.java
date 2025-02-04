package ui;

import models.Abonnement;

import javax.swing.*;
import java.awt.*;

public class ViewAbonnementFrame extends JFrame {

    private Abonnement abonnement;

    public ViewAbonnementFrame(Abonnement abonnement) {
        this.abonnement = abonnement;
        setTitle("Détails de l'abonnement");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("ID:"));
        panel.add(new JLabel(String.valueOf(abonnement.getId())));
        panel.add(new JLabel("Libellé:"));
        panel.add(new JLabel(abonnement.getLibelleOffre()));
        panel.add(new JLabel("Durée (mois):"));
        panel.add(new JLabel(String.valueOf(abonnement.getDureeMois())));
        panel.add(new JLabel("Prix Mensuel:"));
        panel.add(new JLabel(String.valueOf(abonnement.getPrixMensuel())));

        add(panel);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}