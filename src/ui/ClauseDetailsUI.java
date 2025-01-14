package ui;

import dao.Clause;
import dao.Standard;
import service.GestionStandards;
import service.GestionClauses;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class ClauseDetailsUI extends JFrame {
    private Clause clause;
    private GestionStandards gestionStandards;
    private GestionClauses gestionClauses;
    private JTable table;
    private DefaultTableModel tableModel;

    public ClauseDetailsUI(Clause clause) {
        this.clause = clause;
        this.gestionStandards = new GestionStandards();
        this.gestionClauses = new GestionClauses();

        setTitle("Détails de la Clause");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        JLabel descriptionLabel = new JLabel("Description: " + clause.getDescription());
        JLabel referenceLabel = new JLabel("Référence: " + clause.getReference());
        detailsPanel.add(descriptionLabel);
        detailsPanel.add(referenceLabel);
        panel.add(detailsPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Référence", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                editStandard((int) table.getValueAt(row, 0));
                return null;
            }
        });

        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                deleteStandard((int) table.getValueAt(row, 0));
                return null;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel tablePanel = new JPanel(new BorderLayout());
        JLabel tableTitle = new JLabel("Liste des Standards rattachés à la Clause");
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Ajouter Standard");
        buttonPanel.add(addButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        loadStandards();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStandard();
            }
        });
    }

    private void loadStandards() {
        List<Standard> allStandards = gestionStandards.getAllStandards();
        List<Standard> clauseStandards = clause.getStandards();

        // Synchroniser les standards de la clause avec ceux dans standards.ser
        List<Standard> synchronizedStandards = clauseStandards.stream()
                .map(clauseStandard -> allStandards.stream()
                        .filter(standard -> standard.getId() == clauseStandard.getId())
                        .findFirst()
                        .orElse(clauseStandard))
                .collect(Collectors.toList());

        clause.setStandards(synchronizedStandards);
        gestionClauses.updateClause(clause.getId(), clause);

        tableModel.setRowCount(0); // Clear existing rows

        if (synchronizedStandards.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Editer", "Supprimer"});
        } else {
            for (Standard standard : synchronizedStandards) {
                tableModel.addRow(new Object[]{
                        standard.getId(),
                        standard.getDescription(),
                        standard.getReference(),
                        "Editer",
                        "Supprimer"
                });
            }
        }
    }

    private void addStandard() {
        JTextField descriptionField = new JTextField();
        JTextField referenceField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Référence:"));
        panel.add(referenceField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Standard", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Standard standard = new Standard();
            standard.setDescription(descriptionField.getText());
            standard.setReference(referenceField.getText());
            standard.addClause(clause); // Lier le standard à la clause
            clause.addStandard(standard); // Ajouter le standard à la clause
            gestionStandards.addStandard(standard);
            gestionClauses.updateClause(clause.getId(), clause); // Mettre à jour la clause
            loadStandards();
        }
    }

    public void editStandard(int id) {
        Standard standard = gestionStandards.getStandard(id);
        if (standard != null) {
            JTextField descriptionField = new JTextField(standard.getDescription());
            JTextField referenceField = new JTextField(standard.getReference());

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Référence:"));
            panel.add(referenceField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editer Standard", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                standard.setDescription(descriptionField.getText());
                standard.setReference(referenceField.getText());
                gestionStandards.updateStandard(id, standard);
                clause.getStandards().removeIf(s -> s.getId() == id);
                clause.addStandard(standard); // Synchroniser avec la clause
                gestionClauses.updateClause(clause.getId(), clause); // Mettre à jour la clause
                loadStandards();
            }
        }
    }

    public void deleteStandard(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce standard?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionStandards.deleteStandard(id);
            clause.getStandards().removeIf(standard -> standard.getId() == id); // Supprimer le standard de la clause
            gestionClauses.updateClause(clause.getId(), clause); // Mettre à jour la clause
            loadStandards();
        }
    }
}
