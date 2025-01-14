package ui;

import dao.Audit;
import dao.User;
import service.GestionAudit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardAuditeur extends JFrame {
    private User auditeur;
    private GestionAudit gestionAudit;
    private JTable table;
    private DefaultTableModel tableModel;

    public DashboardAuditeur(User auditeur) {
        this.auditeur = auditeur;
        gestionAudit = new GestionAudit();

        setTitle("Tableau de Bord Auditeur");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(Color.DARK_GRAY);
        header.setPreferredSize(new Dimension(getWidth(), 50));

        JLabel headerLabel = new JLabel("Bienvenue, " + auditeur.getName(), JLabel.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(headerLabel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Se Déconnecter");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        header.add(logoutButton, BorderLayout.EAST);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel auditsTitle = new JLabel("Liste de vos audits disponibles", JLabel.CENTER);
        auditsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(auditsTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Intitulé", "Date Début", "Date Fin", "Status", "Type", "Standard", "Processus", "Détails", "Modifier"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Détails").setCellRenderer(new ButtonRenderer());
        table.getColumn("Détails").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, true));
        table.getColumn("Modifier").setCellRenderer(new ButtonRenderer());
        table.getColumn("Modifier").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadAudits();

        add(header, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    private void loadAudits() {
        List<Audit> audits = gestionAudit.getAllAudits();
        tableModel.setRowCount(0); // Clear existing rows

        for (Audit audit : audits) {
            if (audit.getUser().getId() == auditeur.getId()) {
                String standardReference = (audit.getStandard() != null) ? audit.getStandard().getReference() : "N/A";
                String processusName = (audit.getProcessus() != null) ? audit.getProcessus().getName() : "N/A";
                tableModel.addRow(new Object[]{
                        audit.getId(),
                        audit.getIntitule(),
                        audit.getDate_debut(),
                        audit.getDate_fin(),
                        audit.getStatus(),
                        audit.getType(),
                        standardReference,
                        processusName,
                        "Détails",
                        "Modifier"
                });
            }
        }
    }

    private void logout() {
        new Login().setVisible(true);
        this.dispose();
    }

    public void viewDetails(int id) {
        Audit audit = gestionAudit.getAudit(id);
        if (audit != null) {
            new AuditDetailsUI(audit).setVisible(true);
        }
    }

    public void editAudit(int id) {
        Audit audit = gestionAudit.getAudit(id);
        if (audit != null) {
            JTextField intituleField = new JTextField(audit.getIntitule());
            JSpinner dateDebutSpinner = new JSpinner(new SpinnerDateModel(audit.getDate_debut(), null, null, java.util.Calendar.DAY_OF_MONTH));
            JSpinner dateFinSpinner = new JSpinner(new SpinnerDateModel(audit.getDate_fin(), null, null, java.util.Calendar.DAY_OF_MONTH));
            dateDebutSpinner.setEditor(new JSpinner.DateEditor(dateDebutSpinner, "dd/MM/yyyy"));
            dateFinSpinner.setEditor(new JSpinner.DateEditor(dateFinSpinner, "dd/MM/yyyy"));
            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Planifié", "En cours", "Suspendu", "Terminé", "Validé", "Archivé"});
            statusComboBox.setSelectedItem(audit.getStatus());
            JTextField typeField = new JTextField(audit.getType());

            JPanel panel = new JPanel(new GridLayout(6, 2));
            panel.add(new JLabel("Intitulé:"));
            panel.add(intituleField);
            panel.add(new JLabel("Date Début:"));
            panel.add(dateDebutSpinner);
            panel.add(new JLabel("Date Fin:"));
            panel.add(dateFinSpinner);
            panel.add(new JLabel("Status:"));
            panel.add(statusComboBox);
            panel.add(new JLabel("Type:"));
            panel.add(typeField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Modifier Audit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                audit.setIntitule(intituleField.getText());
                audit.setDate_debut((java.util.Date) dateDebutSpinner.getValue());
                audit.setDate_fin((java.util.Date) dateFinSpinner.getValue());
                audit.setStatus((String) statusComboBox.getSelectedItem());
                audit.setType(typeField.getText());
                gestionAudit.updateAudit(audit.getId(), audit);
                loadAudits();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Passer un utilisateur fictif pour l'exemple
                User auditeur = new User("Auditeur", "auditeur", "password", "auditeur");
                new DashboardAuditeur(auditeur).setVisible(true);
            }
        });
    }
}
