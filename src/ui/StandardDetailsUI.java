package ui;

import dao.Standard;
import dao.Clause;
import service.GestionStandards;
import service.GestionClauses;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class StandardDetailsUI extends JFrame {
    private Standard standard;
    private GestionStandards gestionStandards;
    private GestionClauses gestionClauses;
    private JTable table;
    private DefaultTableModel tableModel;

    public StandardDetailsUI(Standard standard) {
        this.standard = standard;
        this.gestionStandards = new GestionStandards();
        this.gestionClauses = new GestionClauses();

        setTitle("Détails du Standard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        JLabel descriptionLabel = new JLabel("Description: " + standard.getDescription());
        JLabel referenceLabel = new JLabel("Référence: " + standard.getReference());
        detailsPanel.add(descriptionLabel);
        detailsPanel.add(referenceLabel);
        panel.add(detailsPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Référence", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                editClause((int) table.getValueAt(row, 0));
                return null;
            }
        });

        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                deleteClause((int) table.getValueAt(row, 0));
                return null;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel tablePanel = new JPanel(new BorderLayout());
        JLabel tableTitle = new JLabel("Liste des Clauses rattachées au Standard");
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Ajouter Clause");
        buttonPanel.add(addButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        loadClauses();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClause();
            }
        });
    }

    private void loadClauses() {
        List<Clause> allClauses = gestionClauses.getAllClauses();
        List<Clause> standardClauses = standard.getClauses();

        // Synchroniser les clauses du standard avec celles dans clauses.ser
        List<Clause> synchronizedClauses = standardClauses.stream()
                .map(standardClause -> allClauses.stream()
                        .filter(clause -> clause.getId() == standardClause.getId())
                        .findFirst()
                        .orElse(standardClause))
                .collect(Collectors.toList());

        standard.setClauses(synchronizedClauses);
        gestionStandards.updateStandard(standard.getId(), standard);

        tableModel.setRowCount(0); // Clear existing rows

        if (synchronizedClauses.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Editer", "Supprimer"});
        } else {
            for (Clause clause : synchronizedClauses) {
                tableModel.addRow(new Object[]{
                        clause.getId(),
                        clause.getDescription(),
                        clause.getReference(),
                        "Editer",
                        "Supprimer"
                });
            }
        }
    }

    private void addClause() {
        JTextField descriptionField = new JTextField();
        JTextField referenceField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Référence:"));
        panel.add(referenceField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Clause", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Clause clause = new Clause();
            clause.setDescription(descriptionField.getText());
            clause.setReference(referenceField.getText());
            clause.addStandard(standard); // Lier la clause au standard
            standard.addClause(clause); // Ajouter la clause au standard
            gestionClauses.addClause(clause);
            gestionStandards.updateStandard(standard.getId(), standard); // Mettre à jour le standard
            loadClauses();
        }
    }

    public void editClause(int id) {
        Clause clause = gestionClauses.getClause(id);
        if (clause != null) {
            JTextField descriptionField = new JTextField(clause.getDescription());
            JTextField referenceField = new JTextField(clause.getReference());

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Référence:"));
            panel.add(referenceField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editer Clause", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                clause.setDescription(descriptionField.getText());
                clause.setReference(referenceField.getText());
                gestionClauses.updateClause(id, clause);
                standard.getClauses().removeIf(c -> c.getId() == id);
                standard.addClause(clause); // Synchroniser avec le standard
                gestionStandards.updateStandard(standard.getId(), standard); // Mettre à jour le standard
                loadClauses();
            }
        }
    }

    public void deleteClause(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette clause?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionClauses.deleteClause(id);
            standard.getClauses().removeIf(clause -> clause.getId() == id); // Supprimer la clause du standard
            gestionStandards.updateStandard(standard.getId(), standard); // Mettre à jour le standard
            loadClauses();
        }
    }
}
