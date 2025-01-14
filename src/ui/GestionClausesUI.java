package ui;

import service.GestionClauses;
import service.GestionStandards;
import dao.Clause;
import dao.Standard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class GestionClausesUI extends JFrame {
    private GestionClauses gestionClauses;
    private GestionStandards gestionStandards;
    private JTable table;
    private DefaultTableModel tableModel;

    public GestionClausesUI() {
        gestionClauses = new GestionClauses();
        gestionStandards = new GestionStandards();

        setTitle("Gérer les Clauses");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JButton addButton = new JButton("Ajouter Clause");
        panel.add(addButton, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Référence", "Détails", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Détails").setCellRenderer(new ButtonRenderer());
        table.getColumn("Détails").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, true));
        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadClauses();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClause();
            }
        });
    }

    private void loadClauses() {
        List<Clause> clauses = gestionClauses.getAllClauses();
        tableModel.setRowCount(0); // Clear existing rows

        if (clauses.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Détails", "Editer", "Supprimer"});
        } else {
            for (Clause clause : clauses) {
                tableModel.addRow(new Object[]{
                        clause.getId(),
                        clause.getDescription(),
                        clause.getReference(),
                        "Détails",
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
            gestionClauses.addClause(clause);
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
                loadClauses();
            }
        }
    }

    public void deleteClause(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette clause?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionClauses.deleteClause(id);
            loadClauses();
        }
    }

    public void viewDetails(int id) {
        Clause clause = gestionClauses.getClause(id);
        if (clause != null) {
            new ClauseDetailsUI(clause).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionClausesUI().setVisible(true);
            }
        });
    }
}
