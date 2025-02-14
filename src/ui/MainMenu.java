import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Gestion de la salle de sport");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton btnGestionAbonnes = new JButton("Gestion des abonnés");
        JButton btnGestionAbonnements = new JButton("Gestion des abonnements");
        JButton btnStatistiques = new JButton("Statistiques");
        JButton btnQuitter = new JButton("Quitter");

        panel.add(btnGestionAbonnes);
        panel.add(btnGestionAbonnements);
        panel.add(btnStatistiques);
        panel.add(btnQuitter);

        btnGestionAbonnes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionAbonnesFrame().setVisible(true);
            }
        });

        btnGestionAbonnements.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionAbonnementsFrame().setVisible(true);
            }
        });

        btnStatistiques.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatistiquesFrame().setVisible(true);
            }
        });

        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(panel);
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