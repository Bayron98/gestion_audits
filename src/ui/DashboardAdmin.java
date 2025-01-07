package ui;

import dao.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardAdmin extends JFrame {
    private JButton gestionStandardsButton;
    private JButton gestionClausesButton;
    private JButton logoutButton;
    private JPanel mainPanel;
    private JLabel userLabel;

    public DashboardAdmin(User user) {
        setTitle("Tableau de Bord Admin");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(Color.DARK_GRAY);
        header.setPreferredSize(new Dimension(getWidth(), 50));

        userLabel = new JLabel("Espace Admin - Connecté en tant que : " + user.getName());
        userLabel.setForeground(Color.WHITE);
        header.add(userLabel, BorderLayout.CENTER);

        JPanel navbar = new JPanel();
        navbar.setLayout(new GridLayout(10, 1)); // 10 rows, 1 column
        navbar.setBackground(Color.LIGHT_GRAY); // Changer la couleur de fond de la navbar

        gestionStandardsButton = new JButton("Gérer les Standards");
        gestionClausesButton = new JButton("Gérer les Clauses");
        logoutButton = new JButton("Se Déconnecter");

        navbar.add(gestionStandardsButton);
        navbar.add(gestionClausesButton);
        navbar.add(new JLabel()); // Empty space
        navbar.add(new JLabel()); // Empty space
        navbar.add(new JLabel()); // Empty space
        navbar.add(new JLabel()); // Empty space
        navbar.add(new JLabel()); // Empty space
        navbar.add(new JLabel()); // Empty space
        navbar.add(new JLabel()); // Empty space
        navbar.add(logoutButton);

        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        add(header, BorderLayout.NORTH);
        add(navbar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        gestionStandardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionStandards();
            }
        });

        gestionClausesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionClauses();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    private void showGestionStandards() {
        mainPanel.removeAll();
        mainPanel.add(new GestionStandardsUI().getContentPane());
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showGestionClauses() {
        mainPanel.removeAll();
        mainPanel.add(new GestionClausesUI().getContentPane());
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void logout() {
        new Login().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Passer un utilisateur fictif pour l'exemple
                User user = new User("Admin", "admin", "password", "admin");
                new DashboardAdmin(user).setVisible(true);
            }
        });
    }
}
