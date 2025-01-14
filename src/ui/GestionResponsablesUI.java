package ui;

import service.GestionResponsables;
import dao.Responsable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestionResponsablesUI extends JFrame {
    private GestionResponsables gestionResponsables;
    private JTable table;
    private DefaultTableModel tableModel;

    public GestionResponsablesUI() {
        gestionResponsables = new GestionResponsables();

        setTitle("Gérer les Responsables");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JButton addButton = new JButton("Ajouter Responsable");
        panel.add(addButton, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Email", "Téléphone", "Rôle", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadResponsables();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addResponsable();
            }
        });
    }

    private void loadResponsables() {
        List<Responsable> responsables = gestionResponsables.getAllResponsables();
        tableModel.setRowCount(0); // Clear existing rows

        if (responsables.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Vide", "Vide", "Vide", "Editer", "Supprimer"});
        } else {
            for (Responsable responsable : responsables) {
                tableModel.addRow(new Object[]{
                        responsable.getId(),
                        responsable.getNom(),
                        responsable.getPrenom(),
                        responsable.getEmail(),
                        responsable.getTel(),
                        responsable.getRole(),
                        "Editer",
                        "Supprimer"
                });
            }
        }
    }

    private void addResponsable() {
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telField = new JTextField();
        String[] roles = {"responsable système", "responsable processus"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Téléphone:"));
        panel.add(telField);
        panel.add(new JLabel("Rôle:"));
        panel.add(roleComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Responsable", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Responsable responsable = new Responsable();
            responsable.setNom(nomField.getText());
            responsable.setPrenom(prenomField.getText());
            responsable.setEmail(emailField.getText());
            responsable.setTel(telField.getText());
            responsable.setRole((String) roleComboBox.getSelectedItem());
            gestionResponsables.addResponsable(responsable);
            loadResponsables();
        }
    }

    public void editResponsable(int id) {
        Responsable responsable = gestionResponsables.getResponsable(id);
        if (responsable != null) {
            JTextField nomField = new JTextField(responsable.getNom());
            JTextField prenomField = new JTextField(responsable.getPrenom());
            JTextField emailField = new JTextField(responsable.getEmail());
            JTextField telField = new JTextField(responsable.getTel());
            String[] roles = {"responsable système", "responsable processus"};
            JComboBox<String> roleComboBox = new JComboBox<>(roles);
            roleComboBox.setSelectedItem(responsable.getRole());

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("Nom:"));
            panel.add(nomField);
            panel.add(new JLabel("Prénom:"));
            panel.add(prenomField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Téléphone:"));
            panel.add(telField);
            panel.add(new JLabel("Rôle:"));
            panel.add(roleComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editer Responsable", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                responsable.setNom(nomField.getText());
                responsable.setPrenom(prenomField.getText());
                responsable.setEmail(emailField.getText());
                responsable.setTel(telField.getText());
                responsable.setRole((String) roleComboBox.getSelectedItem());
                gestionResponsables.updateResponsable(id, responsable);
                loadResponsables();
            }
        }
    }

    public void deleteResponsable(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce responsable?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionResponsables.deleteResponsable(id);
            loadResponsables();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionResponsablesUI().setVisible(true);
            }
        });
    }
}
