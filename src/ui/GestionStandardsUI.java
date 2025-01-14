package ui;

import service.GestionStandards;
import service.GestionClauses;
import dao.Standard;
import dao.Clause;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class GestionStandardsUI extends JFrame {
    private GestionStandards gestionStandards;
    private GestionClauses gestionClauses;
    private JTable table;
    private DefaultTableModel tableModel;

    public GestionStandardsUI() {
        gestionStandards = new GestionStandards();
        gestionClauses = new GestionClauses();

        setTitle("Gérer les Standards");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JButton addButton = new JButton("Ajouter Standard");
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

        loadStandards();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStandard();
            }
        });
    }

    private void loadStandards() {
        List<Standard> standards = gestionStandards.getAllStandards();
        tableModel.setRowCount(0); // Clear existing rows

        if (standards.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Détails", "Editer", "Supprimer"});
        } else {
            for (Standard standard : standards) {
                tableModel.addRow(new Object[]{
                        standard.getId(),
                        standard.getDescription(),
                        standard.getReference(),
                        "Détails",
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
            gestionStandards.addStandard(standard);
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
                loadStandards();
            }
        }
    }

    public void deleteStandard(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce standard?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionStandards.deleteStandard(id);
            loadStandards();
        }
    }

    public void viewDetails(int id) {
        Standard standard = gestionStandards.getStandard(id);
        if (standard != null) {
            new StandardDetailsUI(standard).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionStandardsUI().setVisible(true);
            }
        });
    }
}
