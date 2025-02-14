package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthentificationFrame extends JFrame {
    public AuthentificationFrame() {
        setTitle("Authentification");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel lblUsername = new JLabel("Nom d'utilisateur:");
        JTextField txtUsername = new JTextField();
        JLabel lblPassword = new JLabel("Mot de passe:");
        JPasswordField txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Connexion");

        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(new JLabel());
        panel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                if (username.equals("admin") && password.equals("admin")) {
                    new MainMenu().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect");
                }
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AuthentificationFrame().setVisible(true);
            }
        });
    }
}