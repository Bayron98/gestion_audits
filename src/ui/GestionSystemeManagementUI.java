package ui;

import service.GestionSystemeManagement;
import service.GestionResponsables;
import dao.SystemeManagement;
import dao.Responsable;
import dao.Processus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestionSystemeManagementUI extends JFrame {
    private GestionSystemeManagement gestionSystemeManagement;
    private GestionResponsables gestionResponsables;
    private JTable table;
    private DefaultTableModel tableModel;

    public GestionSystemeManagementUI() {
        gestionSystemeManagement = new GestionSystemeManagement();
        gestionResponsables = new GestionResponsables();

        setTitle("Gérer les Systèmes de Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JButton addButton = new JButton("Ajouter Système");
        panel.add(addButton, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Nom", "Responsable", "Détails", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Détails").setCellRenderer(new ButtonRenderer());
        table.getColumn("Détails").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, true));
        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadSystemeManagement();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSystemeManagement();
            }
        });
    }

    private void loadSystemeManagement() {
        List<SystemeManagement> systemes = gestionSystemeManagement.getAllSystemeManagement();
        tableModel.setRowCount(0); // Clear existing rows

        if (systemes.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Vide", "Détails", "Editer", "Supprimer"});
        } else {
            for (SystemeManagement systeme : systemes) {
                String responsableNom = (systeme.getResponsable() != null) ? systeme.getResponsable().getNom() : "N/A";
                tableModel.addRow(new Object[]{
                        systeme.getId(),
                        systeme.getDescription(),
                        systeme.getNom(),
                        responsableNom,
                        "Détails",
                        "Editer",
                        "Supprimer"
                });
            }
        }
    }

    private void addSystemeManagement() {
        JTextField descriptionField = new JTextField();
        JTextField nomField = new JTextField();
        JComboBox<Responsable> responsableComboBox = new JComboBox<>();

        List<Responsable> responsables = (List<Responsable>) gestionResponsables.getAllResponsables();
        for (Responsable responsable : responsables) {
            if ("responsable système".equals(responsable.getRole())) {
                responsableComboBox.addItem(responsable);
            }
        }

        responsableComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Responsable) {
                    value = ((Responsable) value).getNom();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Responsable:"));
        panel.add(responsableComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Système", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            SystemeManagement systeme = new SystemeManagement();
            systeme.setDescription(descriptionField.getText());
            systeme.setNom(nomField.getText());
            systeme.setResponsable((Responsable) responsableComboBox.getSelectedItem());
            gestionSystemeManagement.addSystemeManagement(systeme);
            loadSystemeManagement();
        }
    }

    public void editSystemeManagement(int id) {
        SystemeManagement systeme = gestionSystemeManagement.getSystemeManagement(id);
        if (systeme != null) {
            JTextField descriptionField = new JTextField(systeme.getDescription());
            JTextField nomField = new JTextField(systeme.getNom());
            JComboBox<Responsable> responsableComboBox = new JComboBox<>();

            List<Responsable> responsables = (List<Responsable>) gestionResponsables.getAllResponsables();
            for (Responsable responsable : responsables) {
                if ("responsable système".equals(responsable.getRole())) {
                    responsableComboBox.addItem(responsable);
                }
            }
            responsableComboBox.setSelectedItem(systeme.getResponsable());

            responsableComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof Responsable) {
                        value = ((Responsable) value).getNom();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Nom:"));
            panel.add(nomField);
            panel.add(new JLabel("Responsable:"));
            panel.add(responsableComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editer Système", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                systeme.setDescription(descriptionField.getText());
                systeme.setNom(nomField.getText());
                systeme.setResponsable((Responsable) responsableComboBox.getSelectedItem());
                gestionSystemeManagement.updateSystemeManagement(id, systeme);
                loadSystemeManagement();
            }
        }
    }

    public void deleteSystemeManagement(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce système?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionSystemeManagement.deleteSystemeManagement(id);
            loadSystemeManagement();
        }
    }

    public void viewDetails(int id) {
        SystemeManagement systeme = gestionSystemeManagement.getSystemeManagement(id);
        if (systeme != null) {
            new SystemeManagementDetailsUI(systeme).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionSystemeManagementUI().setVisible(true);
            }
        });
    }
}
