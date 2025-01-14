package ui;

import dao.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardAdmin extends JFrame {
    private JButton gestionUsersButton;
    private JButton gestionAuditButton;
    private JButton gestionActionButton;
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
        navbar.setLayout(new GridLayout(11, 1)); // 10 rows, 1 column
        navbar.setBackground(Color.LIGHT_GRAY); // Changer la couleur de fond de la navbar

        gestionUsersButton = new JButton("Gérer les Utilisateurs");
        gestionAuditButton = new JButton("Gérer les Audits");
        gestionActionButton = new JButton("Gérer les Actions");
        gestionStandardsButton = new JButton("Gérer les Standards");
        gestionClausesButton = new JButton("Gérer les Clauses");
        gestionSitesButton = new JButton("Gérer les Sites");
        gestionResponsablesButton = new JButton("Gérer les Responsables");
        gestionOrganisationButton = new JButton("Gérer l'Organisation");
        gestionProcessusButton = new JButton("Gérer les Processus");
        gestionSystemeManagementButton = new JButton("Gérer le Système de Management");
        logoutButton = new JButton("Se Déconnecter");

        navbar.add(gestionUsersButton);
        navbar.add(gestionAuditButton);
        navbar.add(gestionActionButton);
        navbar.add(gestionStandardsButton);
        navbar.add(gestionClausesButton);
        navbar.add(gestionSitesButton);
        navbar.add(gestionResponsablesButton);
        navbar.add(gestionOrganisationButton);
        navbar.add(gestionProcessusButton);
        navbar.add(gestionSystemeManagementButton);
        navbar.add(logoutButton);

        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        // Ajouter des renseignements et un guide dans le mainPanel à l'accueil
        JPanel homePanel = new JPanel(new GridLayout(3, 1));
        JLabel welcomeLabel = new JLabel("Bienvenue sur le Tableau de Bord Admin", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        homePanel.add(welcomeLabel);

        JLabel infoLabel = new JLabel("Renseignements et Guide", JLabel.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        homePanel.add(infoLabel);

        JTextArea guideArea = new JTextArea();
        guideArea.setText("1. Pour gérer les utilisateurs, cliquez sur 'Gérer les Utilisateurs'.\n"
                + "2. Pour gérer les audits, cliquez sur 'Gérer les Audits'.\n"
                + "3. Pour gérer les standards, cliquez sur 'Gérer les Standards'.\n"
                + "4. Pour gérer les clauses, cliquez sur 'Gérer les Clauses'.\n"
                + "5. Pour gérer les sites, cliquez sur 'Gérer les Sites'.\n"
                + "6. Pour gérer les responsables, cliquez sur 'Gérer les Responsables'.\n"
                + "7. Pour gérer l'organisation, cliquez sur 'Gérer l'Organisation'.\n"
                + "8. Pour gérer les processus, cliquez sur 'Gérer les Processus'.\n"
                + "9. Pour gérer le système de management, cliquez sur 'Gérer le Système de Management'.\n"
                + "10. Pour vous déconnecter, cliquez sur 'Se Déconnecter'.");
        guideArea.setEditable(false);
        homePanel.add(guideArea);

        mainPanel.add(homePanel, "home");

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(header, BorderLayout.NORTH);
        add(navbar, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        gestionUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionUsers();
            }
        });

        gestionAuditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionAudit();
            }
        });

        gestionActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGestionAction();
            }
        });

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

        // Afficher le panneau d'accueil par défaut
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "home");
    }

    private void showGestionUsers() {
        mainPanel.removeAll();
        mainPanel.add(new GestionUsersUI().getContentPane());
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showGestionAudit() {
        mainPanel.removeAll();
        mainPanel.add(new GestionAuditUI().getContentPane());
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    public void showGestionAction(){
        mainPanel.removeAll();
        mainPanel.add(new GestionActionUI().getContentPane());
        mainPanel.revalidate();
        mainPanel.repaint();
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
