package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Gestion Salle de Sport");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel("Gestion Salle de Sport", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(52, 152, 219));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Tabs Panel
        JPanel tabPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        tabPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabPanel.setBackground(new Color(245, 245, 245));
        String[] tabNames = {"Abonné", "Abonnement", "Souscription", "Statistiques"};
        for (String name : tabNames) {
            JButton button = new JButton(name);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.setBackground(new Color(41, 128, 185));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            button.setBorderPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            tabPanel.add(button);
        }
        mainPanel.add(tabPanel, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Abonné"));
        formPanel.setBackground(new Color(245, 245, 245));

        formPanel.add(createStyledLabel("ID Abonné:"));
        JTextField idField = createStyledTextField();
        formPanel.add(idField);

        formPanel.add(createStyledLabel("Nom:"));
        JTextField nomField = createStyledTextField();
        formPanel.add(nomField);

        formPanel.add(createStyledLabel("Prénom:"));
        JTextField prenomField = createStyledTextField();
        formPanel.add(prenomField);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Recherche"));
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.add(createStyledLabel("Recherche:"));
        JTextField searchField = createStyledTextField();
        JButton searchButton = createStyledButton("Rechercher", new Color(46, 204, 113));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel, BorderLayout.EAST);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton addButton = createStyledButton("Ajouter", new Color(231, 76, 60));
        buttonPanel.add(addButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ActionListener for addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Abonné ajouté avec succès!", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(mainPanel);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return textField;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
}