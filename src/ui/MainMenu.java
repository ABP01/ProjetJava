package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("SYSTÈME DE GESTION DES ABONNÉS ET ABONNEMENTS");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(144, 238, 144));

        // Header
        JLabel headerLabel = new JLabel("SYSTÈME DE GESTION DES ABONNÉS ET ABONNEMENTS", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(173, 216, 230));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Tabs Panel
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new GridLayout(1, 4));

        JButton btnAbonne = new JButton("Abonné");
        JButton btnAbonnement = new JButton("Abonnement");
        JButton btnSouscription = new JButton("Souscription");
        JButton btnStatistiques = new JButton("Statistiques");

        tabPanel.add(btnAbonne);
        tabPanel.add(btnAbonnement);
        tabPanel.add(btnSouscription);
        tabPanel.add(btnStatistiques);

        mainPanel.add(tabPanel, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(new Color(144, 238, 144));

        formPanel.add(new JLabel("ID abonné:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Nom:"));
        JTextField nomField = new JTextField();
        formPanel.add(nomField);

        formPanel.add(new JLabel("Prénom:"));
        JTextField prenomField = new JTextField();
        formPanel.add(prenomField);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(new JLabel("Recherche:"));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.EAST);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        buttonPanel.add(addButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add ActionListener for the addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'abonné.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionAbonnesFrame().setVisible(true);
            }
        });
    }
}