package ui;

import dao.Standard;
import dao.Clause;
import service.GestionClauses;
import service.GestionStandards;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StandardDetailsUI extends JFrame {
    private Standard standard;
    private GestionClauses gestionClauses;
    private GestionStandards gestionStandards;
    private JTable table;
    private DefaultTableModel tableModel;

    public StandardDetailsUI(Standard standard) {
        this.standard = standard;
        this.gestionClauses = new GestionClauses();
        this.gestionStandards = new GestionStandards();

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
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

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
        List<Clause> clauses = standard.getClauses();
        tableModel.setRowCount(0); // Clear existing rows

        if (clauses.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Editer", "Supprimer"});
        } else {
            for (Clause clause : clauses) {
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
        String description = JOptionPane.showInputDialog(this, "Description:");
        String reference = JOptionPane.showInputDialog(this, "Référence:");
        if (description != null && reference != null) {
            Clause clause = new Clause();
            clause.setDescription(description);
            clause.setReference(reference);
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
            String description = JOptionPane.showInputDialog(this, "Description:", clause.getDescription());
            String reference = JOptionPane.showInputDialog(this, "Référence:", clause.getReference());
            if (description != null && reference != null) {
                clause.setDescription(description);
                clause.setReference(reference);
                gestionClauses.updateClause(id, clause);
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
