package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTable abonneTable;
    private JTable abonnementTable;

    public MainFrame() {
        setTitle("Gestion de la salle de sport");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Abonnés", createAbonnePanel());
        tabbedPane.addTab("Abonnements", createAbonnementPanel());

        add(tabbedPane);
    }

    private JPanel createAbonnePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Exemple de tableau pour afficher les abonnés
        String[] columnNames = {"ID", "Nom", "Prénom", "Date Inscription", "Numéro Téléphone", "Statut Souscription"};
        Object[][] data = {}; // Récupérer les données de la base de données

        abonneTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(abonneTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter un abonné");
        addButton.addActionListener(e -> {
            AddAbonneFrame addAbonneFrame = new AddAbonneFrame();
            addAbonneFrame.setVisible(true);
        });
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAbonnementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Exemple de tableau pour afficher les abonnements
        String[] columnNames = {"ID", "Libellé", "Durée (mois)", "Prix Mensuel"};
        Object[][] data = {}; // Récupérer les données de la base de données

        abonnementTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(abonnementTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter un abonnement");
        addButton.addActionListener(e -> {
            // Ouvrir un formulaire pour ajouter un abonnement
        });
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}