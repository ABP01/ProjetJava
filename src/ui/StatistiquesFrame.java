package ui;

import models.Abonne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class StatistiquesFrame extends JFrame {
    private JLabel lblTotalAbonnes, lblAbonnesActifs, lblRevenuMensuel, lblAbonnementPopulaire;
    private JButton btnRafraichir;

    public StatistiquesFrame() {
        setTitle("Statistiques des Abonnés");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblTotalAbonnes = new JLabel("Total des abonnés: 0");
        lblAbonnesActifs = new JLabel("Abonnés actifs: 0");
        lblRevenuMensuel = new JLabel("Revenu mensuel: 0,00 €");
        lblAbonnementPopulaire = new JLabel("Abonnement le plus populaire: Aucun");

        btnRafraichir = new JButton("Rafraîchir les statistiques");
        btnRafraichir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rafraichirStatistiques();
            }
        });

        panel.add(lblTotalAbonnes);
        panel.add(lblAbonnesActifs);
        panel.add(lblRevenuMensuel);
        panel.add(lblAbonnementPopulaire);
        panel.add(btnRafraichir);

        add(panel);

        // Charger les statistiques initiales
        rafraichirStatistiques();
    }

    private void rafraichirStatistiques() {
        // Simuler des données d'abonnés
        List<Abonne> abonnes = Arrays.asList(
                new Abonne("Alice", true, 50.00, "Mensuel"),
                new Abonne("Bob", false, 100.00, "Semestriel"),
                new Abonne("Charlie", true, 50.00, "Mensuel"),
                new Abonne("David", true, 200.00, "Annuel")
        );

        int totalAbonnes = abonnes.size();
        int abonnesActifs = 0;
        double revenuMensuel = 0.0;
        String abonnementPopulaire = calculerAbonnementPopulaire(abonnes);

        // Calculer les statistiques
        for (Abonne abonne : abonnes) {
            if (abonne.isActif()) {
                abonnesActifs++;
                revenuMensuel += abonne.getMontantAbonnement();
            }
        }

        // Mettre à jour les labels avec les nouvelles données
        lblTotalAbonnes.setText("Total des abonnés: " + totalAbonnes);
        lblAbonnesActifs.setText("Abonnés actifs: " + abonnesActifs);
        lblRevenuMensuel.setText("Revenu mensuel: " + String.format("%.2f", revenuMensuel) + " €");
        lblAbonnementPopulaire.setText("Abonnement le plus populaire: " + abonnementPopulaire);
    }

    private String calculerAbonnementPopulaire(List<Abonne> abonnes) {
        // Calculer l'abonnement le plus populaire (simple approche)
        int mensuel = 0, semestriel = 0, annuel = 0;

        for (Abonne abonne : abonnes) {
            String typeAbonnement = String.valueOf(abonne.getAbonnement());
            if (typeAbonnement.equals("Mensuel")) {
                mensuel++;
            } else if (typeAbonnement.equals("Semestriel")) {
                semestriel++;
            } else if (typeAbonnement.equals("Annuel")) {
                annuel++;
            }
        }

        // Retourner le type d'abonnement le plus populaire
        if (mensuel >= semestriel && mensuel >= annuel) {
            return "Mensuel";
        } else if (semestriel >= annuel) {
            return "Semestriel";
        } else {
            return "Annuel";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StatistiquesFrame().setVisible(true);
            }
        });
    }
}
