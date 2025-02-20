package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import dao.AbonneDAO;
import dao.dbconn;
import models.Abonne;

public class StatistiquesFrame extends JFrame {
    private JLabel lblTotalAbonnes, lblAbonnesActifs, lblRevenuMensuel, lblAbonnementPopulaire;
    private JButton btnRafraichir;

    public StatistiquesFrame() {
        setTitle("Statistiques des Abonnés");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with better styling
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Stats panel with card-like appearance
        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 15, 15));

        // Create styled stat cards
        lblTotalAbonnes = createStyledLabel("Total des abonnés");
        lblAbonnesActifs = createStyledLabel("Abonnés actifs");
        lblRevenuMensuel = createStyledLabel("Revenu mensuel");
        lblAbonnementPopulaire = createStyledLabel("Abonnement populaire");

        statsPanel.add(createStatCard(lblTotalAbonnes));
        statsPanel.add(createStatCard(lblAbonnesActifs));
        statsPanel.add(createStatCard(lblRevenuMensuel));
        statsPanel.add(createStatCard(lblAbonnementPopulaire));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRafraichir = new JButton("Rafraîchir les statistiques");
        styleButton(btnRafraichir);
        buttonPanel.add(btnRafraichir);

        mainPanel.add(statsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        btnRafraichir.addActionListener(e -> rafraichirStatistiques());

        // Initial load
        rafraichirStatistiques();
    }

    // Change from private to public
    public void rafraichirStatistiques() {
        try {
            // Get total subscribers
            List<Abonne> abonnes = AbonneDAO.getAbonnes();
            int totalAbonnes = abonnes.size();

            // Get active subscribers
            int abonnesActifs = AbonneDAO.getActiveAbonneCount();

            // Get monthly revenue
            String sql = "SELECT SUM(ab.prix_mensuel) as total_revenue " +
                    "FROM souscription s " +
                    "JOIN abonnement ab ON s.id_abonnement = ab.id " +
                    "WHERE s.date_fin IS NULL";

            float revenuMensuel = 0;
            try (Connection conn = dbconn.getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    revenuMensuel = rs.getFloat("total_revenue");
                }
            }

            // Get most popular subscription type
            String abonnementPopulaire = getMostPopularSubscription();

            // Update labels directly without animation for testing
            lblTotalAbonnes.setText("Total des abonnés: " + totalAbonnes);
            lblAbonnesActifs.setText("Abonnés actifs: " + abonnesActifs);
            lblRevenuMensuel.setText("Revenu mensuel: " + String.format("%,.2f €", revenuMensuel));
            lblAbonnementPopulaire.setText("Abonnement le plus populaire: " + abonnementPopulaire);

        } catch (Exception e) {
            e.printStackTrace(); // For debugging
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement des statistiques: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getMostPopularSubscription() {
        try {
            String sql = "SELECT a.libelle, COUNT(*) as count " +
                    "FROM souscription s " +
                    "JOIN abonnement a ON s.id_abonnement = a.id " +
                    "WHERE s.date_fin IS NULL " +
                    "GROUP BY a.libelle " +
                    "ORDER BY count DESC " +
                    "LIMIT 1";

            try (Connection conn = dbconn.getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {

                if (rs.next()) {
                    return rs.getString("libelle") + " (" + rs.getInt("count") + " abonnés)";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // For debugging
        }
        return "Aucun abonnement actif";
    }

    private JPanel createStatCard(JLabel label) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(255, 255, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private JLabel createStyledLabel(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(66, 139, 202));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(51, 122, 183));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(66, 139, 202));
            }
        });
    }

    private void updateLabelWithAnimation(JLabel label, String newText) {
        Timer timer = new Timer(50, null);
        final int[] alpha = { 0 };

        label.setForeground(new Color(0, 0, 0, alpha[0]));
        label.setText(newText);

        timer.addActionListener(e -> {
            alpha[0] += 25;
            if (alpha[0] > 255) {
                alpha[0] = 255;
                timer.stop();
            }
            label.setForeground(new Color(0, 0, 0, alpha[0]));
        });

        timer.start();
    }
}
