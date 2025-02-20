package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import dao.UserDAO;

public class LoginForm extends JFrame {
    public LoginForm() {
        setTitle("Connexion - Salle de Sport");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Header Label
        JLabel headerLabel = new JLabel("Bienvenue", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(100, 149, 237));
        headerLabel.setForeground(Color.WHITE);
        panel.add(headerLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nom d'utilisateur:"));
        JTextField usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Mot de passe:"));
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordField);

        panel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Se connecter");
        buttonPanel.add(loginButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // ActionListener for login button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
        
            if (UserDAO.authenticate(username, password)) {
                JOptionPane.showMessageDialog(null, "Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                new MainMenu().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}