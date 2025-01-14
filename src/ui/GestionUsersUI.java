package ui;

import service.GestionUsers;
import dao.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestionUsersUI extends JFrame {
    private GestionUsers gestionUsers;
    private JTable table;
    private DefaultTableModel tableModel;

    public GestionUsersUI() {
        gestionUsers = new GestionUsers();

        setTitle("Gérer les Utilisateurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JButton addButton = new JButton("Ajouter Utilisateur");
        panel.add(addButton, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Username", "Role", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadUsers();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
    }

    private void loadUsers() {
        List<User> users = gestionUsers.getAllUsers();
        tableModel.setRowCount(0); // Clear existing rows

        if (users.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Vide", "Editer", "Supprimer"});
        } else {
            for (User user : users) {
                tableModel.addRow(new Object[]{
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getRole(),
                        "Editer",
                        "Supprimer"
                });
            }
        }
    }

    private void addUser() {
        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"admin", "auditeur"});

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nameField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Utilisateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            User user = new User(nameField.getText(), usernameField.getText(), passwordField.getText(), (String) roleComboBox.getSelectedItem());
            user.setId(gestionUsers.getAllUsers().size() + 1);
            gestionUsers.addUser(user);
            loadUsers();
        }
    }

    public void editUser(int id) {
        User user = gestionUsers.getUser(id);
        if (user != null) {
            JTextField nameField = new JTextField(user.getName());
            JTextField usernameField = new JTextField(user.getUsername());
            JTextField passwordField = new JTextField(user.getPassword());
            JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"admin", "auditeur"});
            roleComboBox.setSelectedItem(user.getRole());

            JPanel panel = new JPanel(new GridLayout(4, 2));
            panel.add(new JLabel("Nom:"));
            panel.add(nameField);
            panel.add(new JLabel("Username:"));
            panel.add(usernameField);
            panel.add(new JLabel("Password:"));
            panel.add(passwordField);
            panel.add(new JLabel("Role:"));
            panel.add(roleComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editer Utilisateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                user.setName(nameField.getText());
                user.setUsername(usernameField.getText());
                user.setPassword(passwordField.getText());
                user.setRole((String) roleComboBox.getSelectedItem());
                gestionUsers.updateUser(id, user);
                loadUsers();
            }
        }
    }

    public void deleteUser(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet utilisateur?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionUsers.deleteUser(id);
            loadUsers();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionUsersUI().setVisible(true);
            }
        });
    }
}
