package ui;

import dao.Audit;
import dao.SystemeExigence;
import dao.User;
import service.GestionAudit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AuditDetailsUI extends JFrame {
    private Audit audit;
    private JTable table;
    private DefaultTableModel tableModel;

    public AuditDetailsUI(Audit audit) {
        this.audit = audit;

        setTitle("Détails de l'Audit");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel intituleLabel = new JLabel("Intitulé: " + audit.getIntitule());
        JLabel dateDebutLabel = new JLabel("Date Début: " + audit.getDate_debut());
        JLabel dateFinLabel = new JLabel("Date Fin: " + audit.getDate_fin());
        JLabel statusLabel = new JLabel("Status: " + audit.getStatus());
        JLabel typeLabel = new JLabel("Type: " + audit.getType());
        JLabel userLabel = new JLabel("Auditeur: " + audit.getUser().getName());

        JPanel infoPanel = new JPanel(new GridLayout(9, 1));
        infoPanel.add(intituleLabel);
        infoPanel.add(dateDebutLabel);
        infoPanel.add(dateFinLabel);
        infoPanel.add(statusLabel);
        infoPanel.add(typeLabel);
        infoPanel.add(userLabel);
        infoPanel.add(new JLabel("")); // Empty space
        JLabel systemeExigenceTitle = new JLabel("Liste des Systèmes d'Exigence:");
        infoPanel.add(systemeExigenceTitle);

        panel.add(infoPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Status", "Constats", "Ecarts", "Clause", "Modifier"}, 0);
        table = new JTable(tableModel);

            table.getColumn("Modifier").setCellRenderer(new ButtonRenderer());
            table.getColumn("Modifier").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));


        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadSystemeExigence();
    }

    private void loadSystemeExigence() {
        List<SystemeExigence> systemeExigences = audit.getSysteme_exigence();
        tableModel.setRowCount(0); // Clear existing rows

        if (systemeExigences == null || systemeExigences.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Vide", "Vide", "Modifier"});
        } else {
            for (SystemeExigence systemeExigence : systemeExigences) {
                tableModel.addRow(new Object[]{
                        systemeExigence.getId(),
                        systemeExigence.getStatus(),
                        systemeExigence.getConstats(),
                        systemeExigence.getEcarts(),
                        systemeExigence.getClause().getReference(),
                        "Modifier"
                });
            }
        }
    }

    public void editSystemeExigence(int id) {
        SystemeExigence systemeExigence = null;
        for (SystemeExigence se : audit.getSysteme_exigence()) {
            if (se.getId() == id) {
                systemeExigence = se;
                break;
            }
        }

        if (systemeExigence != null) {
            JTextField constatsField = new JTextField(systemeExigence.getConstats());
            JTextField ecartsField = new JTextField(systemeExigence.getEcarts());
            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Non évalué", "Conforme", "Non conforme", "Partiellement conforme", "Exclusion", "En cours de traitement", "Clôturé"});

            statusComboBox.setSelectedItem(systemeExigence.getStatus());

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Constats:"));
            panel.add(constatsField);
            panel.add(new JLabel("Ecarts:"));
            panel.add(ecartsField);
            panel.add(new JLabel("Status:"));
            panel.add(statusComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Modifier Système d'Exigence", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                systemeExigence.setConstats(constatsField.getText());
                systemeExigence.setEcarts(ecartsField.getText());
                systemeExigence.setStatus((String) statusComboBox.getSelectedItem());
                new GestionAudit().updateAudit(audit.getId(), audit);
                loadSystemeExigence();
            }
        }
    }
}
