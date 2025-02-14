package ui;

import javax.swing.*;
import java.awt.*;

public class StatistiquesFrame extends JFrame {
    private JLabel lblAbonnesActifs, lblChiffreAffaires;

    public StatistiquesFrame() {
        setTitle("Statistiques");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        lblAbonnesActifs = new JLabel("Nombre d'abonnés actifs: 0");
        lblChiffreAffaires = new JLabel("Chiffre d'affaires mensuel estimé: 0.00 €");

        panel.add(lblAbonnesActifs);
        panel.add(lblChiffreAffaires);

        add(panel);

        // Charger les statistiques au démarrage
        chargerStatistiques();
    }

    private void chargerStatistiques() {
        // Implémentez la logique pour calculer et afficher les statistiques
    }
}