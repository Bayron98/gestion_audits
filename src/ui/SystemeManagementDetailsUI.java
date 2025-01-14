package ui;

import dao.SystemeManagement;
import dao.Processus;
import service.GestionSystemeManagement;
import dao.Responsable;
import service.GestionResponsables;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SystemeManagementDetailsUI extends JFrame {
    private SystemeManagement systemeManagement;
    private JTable table;
    private DefaultTableModel tableModel;

    public SystemeManagementDetailsUI(SystemeManagement systemeManagement) {
        this.systemeManagement = systemeManagement;

        setTitle("Détails du Système de Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel descriptionLabel = new JLabel("Description: " + systemeManagement.getDescription());
        JLabel nomLabel = new JLabel("Nom: " + systemeManagement.getNom());
        String responsableNom = (systemeManagement.getResponsable() != null) ? systemeManagement.getResponsable().getNom() : "N/A";
        JLabel responsableLabel = new JLabel("Responsable: " + responsableNom);

        JPanel infoPanel = new JPanel(new GridLayout(5, 1));
        infoPanel.add(descriptionLabel);
        infoPanel.add(nomLabel);
        infoPanel.add(responsableLabel);
        infoPanel.add(new JLabel("")); // Empty space
        JLabel processusTitle = new JLabel("Liste des Processus:");
        infoPanel.add(processusTitle);


        panel.add(infoPanel, BorderLayout.NORTH);



        JButton addButton = new JButton("Ajouter Processus");
        panel.add(addButton, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Nom", "Responsable", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadProcessus();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProcessus();
            }
        });
    }

    private void loadProcessus() {
        tableModel.setRowCount(0); // Clear existing rows

        if (systemeManagement.getProcessus().isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Vide", "Editer", "Supprimer"});
        } else {
            for (Processus processus : systemeManagement.getProcessus()) {
                tableModel.addRow(new Object[]{
                        processus.getId(),
                        processus.getDescription(),
                        processus.getName(),
                        processus.getResponsable().getNom(),
                        "Editer",
                        "Supprimer"
                });
            }
        }
    }

    private void addProcessus() {
        JTextField descriptionField = new JTextField();
        JTextField nameField = new JTextField();
        JComboBox<Responsable> responsableComboBox = new JComboBox<>();

        List<Responsable> responsables = (List<Responsable>) new GestionResponsables().getAllResponsables();
        for (Responsable responsable : responsables) {
            if ("responsable processus".equals(responsable.getRole())) {
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
        panel.add(nameField);
        panel.add(new JLabel("Responsable:"));
        panel.add(responsableComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Processus", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Processus processus = new Processus();
            processus.setDescription(descriptionField.getText());
            processus.setName(nameField.getText());
            processus.setResponsable((Responsable) responsableComboBox.getSelectedItem());
            processus.setId(systemeManagement.getProcessus().size() + 1);
            systemeManagement.addProcessus(processus);
            new GestionSystemeManagement().updateSystemeManagement(systemeManagement.getId(), systemeManagement);
            loadProcessus();
        }
    }

    public void editProcessus(int id) {
        Processus processus = null;
        for (Processus p : systemeManagement.getProcessus()) {
            if (p.getId() == id) {
                processus = p;
                break;
            }
        }

        if (processus != null) {
            JTextField descriptionField = new JTextField(processus.getDescription());
            JTextField nameField = new JTextField(processus.getName());
            JComboBox<Responsable> responsableComboBox = new JComboBox<>();

            List<Responsable> responsables = (List<Responsable>) new GestionResponsables().getAllResponsables();
            for (Responsable responsable : responsables) {
                if ("responsable processus".equals(responsable.getRole())) {
                    responsableComboBox.addItem(responsable);
                }
            }
            responsableComboBox.setSelectedItem(processus.getResponsable());

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
            panel.add(nameField);
            panel.add(new JLabel("Responsable:"));
            panel.add(responsableComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editer Processus", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                processus.setDescription(descriptionField.getText());
                processus.setName(nameField.getText());
                processus.setResponsable((Responsable) responsableComboBox.getSelectedItem());
                new GestionSystemeManagement().updateSystemeManagement(systemeManagement.getId(), systemeManagement);
                loadProcessus();
            }
        }
    }

    public void deleteProcessus(int id) {
        Processus processusToRemove = null;
        for (Processus processus : systemeManagement.getProcessus()) {
            if (processus.getId() == id) {
                processusToRemove = processus;
                break;
            }
        }

        if (processusToRemove != null) {
            systemeManagement.getProcessus().remove(processusToRemove);
            new GestionSystemeManagement().updateSystemeManagement(systemeManagement.getId(), systemeManagement);
            loadProcessus();
        }
    }
}
