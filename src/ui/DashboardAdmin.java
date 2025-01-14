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
    private JButton gestionSitesButton;
    private JButton gestionResponsablesButton;
    private JButton gestionOrganisationButton;
    private JButton gestionProcessusButton;
    private JButton gestionSystemeManagementButton;
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
        gestionSitesButton = new JButton("Gérer les Sites");
        gestionResponsablesButton = new JButton("Gérer les Responsables");
        gestionOrganisationButton = new JButton("Gérer l'Organisation");
        gestionProcessusButton = new JButton("Gérer les Processus");
        gestionSystemeManagementButton = new JButton("Gérer le Système de Management");
        logoutButton = new JButton("Se Déconnecter");

        navbar.add(gestionStandardsButton);
        navbar.add(gestionClausesButton);
        navbar.add(gestionSitesButton);
        navbar.add(gestionResponsablesButton);
        navbar.add(gestionOrganisationButton);
        navbar.add(gestionProcessusButton);
        navbar.add(gestionSystemeManagementButton);
        navbar.add(new JLabel()); // Empty space
        navbar.add(new JLabel()); // Empty space
        navbar.add(logoutButton);

        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(header, BorderLayout.NORTH);
        add(navbar, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

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

        gestionSitesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionSites();
            }
        });

;

        gestionOrganisationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionOrganisation();
            }
        });

        gestionResponsablesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionResponsables();
            }
        });

        gestionProcessusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionProcessus();
            }
        });

        gestionSystemeManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionSystemeManagement();
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

    private void showGestionSites() {
        mainPanel.removeAll();
        mainPanel.add(new GestionSitesUI().getContentPane());
        mainPanel.revalidate();
        mainPanel.repaint();
    }



    private void showGestionOrganisation() {
        mainPanel.removeAll();
        mainPanel.add(new GestionOrganisationUI().getContentPane());
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showGestionResponsables() {
        mainPanel.removeAll();
        mainPanel.add(new GestionResponsablesUI().getContentPane());
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showGestionProcessus() {
        mainPanel.removeAll();
        mainPanel.add(new GestionProcessusUI().getContentPane());
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showGestionSystemeManagement() {
        mainPanel.removeAll();
        mainPanel.add(new GestionSystemeManagementUI().getContentPane());
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
