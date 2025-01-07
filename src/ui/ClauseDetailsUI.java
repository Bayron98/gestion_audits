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
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

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
        List<Standard> standards = clause.getStandards();
        tableModel.setRowCount(0); // Clear existing rows

        if (standards.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Editer", "Supprimer"});
        } else {
            for (Standard standard : standards) {
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
        String description = JOptionPane.showInputDialog(this, "Description:");
        String reference = JOptionPane.showInputDialog(this, "Référence:");
        if (description != null && reference != null) {
            Standard standard = new Standard();
            standard.setDescription(description);
            standard.setReference(reference);
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
            String description = JOptionPane.showInputDialog(this, "Description:", standard.getDescription());
            String reference = JOptionPane.showInputDialog(this, "Référence:", standard.getReference());
            if (description != null && reference != null) {
                standard.setDescription(description);
                standard.setReference(reference);
                gestionStandards.updateStandard(id, standard);
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
