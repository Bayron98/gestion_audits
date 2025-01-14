package ui;

import service.GestionAudit;
import service.GestionUsers;
import service.GestionStandards;
import service.GestionSystemeManagement;
import dao.Audit;
import dao.User;
import dao.Standard;
import dao.Processus;
import dao.SystemeManagement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class GestionAuditUI extends JFrame {
    private GestionAudit gestionAudit;
    private GestionUsers gestionUsers;
    private GestionStandards gestionStandards;
    private GestionSystemeManagement gestionSystemeManagement;
    private JTable table;
    private DefaultTableModel tableModel;

    public GestionAuditUI() {
        gestionAudit = new GestionAudit();
        gestionUsers = new GestionUsers();
        gestionStandards = new GestionStandards();
        gestionSystemeManagement = new GestionSystemeManagement();

        setTitle("Gérer les Audits");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JButton addButton = new JButton("Ajouter Audit");
        panel.add(addButton, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Intitulé", "Date Début", "Date Fin", "Status", "Type", "Auditeur", "Standard", "Processus", "Détails", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Détails").setCellRenderer(new ButtonRenderer());
        table.getColumn("Détails").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, true));
        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadAudits();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAudit();
            }
        });
    }

    private void loadAudits() {
        List<Audit> audits = gestionAudit.getAllAudits();
        tableModel.setRowCount(0); // Clear existing rows

        if (audits.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Vide", "Vide", "Vide", "Vide", "Vide", "Vide", "Détails", "Editer", "Supprimer"});
        } else {
            for (Audit audit : audits) {
                String standardReference = (audit.getStandard() != null) ? audit.getStandard().getReference() : "N/A";
                String processusName = (audit.getProcessus() != null) ? audit.getProcessus().getName() : "N/A";
                tableModel.addRow(new Object[]{
                        audit.getId(),
                        audit.getIntitule(),
                        audit.getDate_debut(),
                        audit.getDate_fin(),
                        audit.getStatus(),
                        audit.getType(),
                        audit.getUser().getName(),
                        standardReference,
                        processusName,
                        "Détails",
                        "Editer",
                        "Supprimer"
                });
            }
        }
    }

    private void addAudit() {
        JTextField intituleField = new JTextField();
        JSpinner dateDebutSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner dateFinSpinner = new JSpinner(new SpinnerDateModel());
        dateDebutSpinner.setEditor(new JSpinner.DateEditor(dateDebutSpinner, "dd/MM/yyyy"));
        dateFinSpinner.setEditor(new JSpinner.DateEditor(dateFinSpinner, "dd/MM/yyyy"));
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Planifié", "En cours", "Suspendu", "Terminé", "Validé", "Archivé"});
        JTextField typeField = new JTextField();
        JComboBox<User> userComboBox = new JComboBox<>();
        JComboBox<Standard> standardComboBox = new JComboBox<>();
        JComboBox<Processus> processusComboBox = new JComboBox<>();

        List<User> users = gestionUsers.getAllUsers();
        for (User user : users) {
            if ("auditeur".equals(user.getRole())) {
                userComboBox.addItem(user);
            }
        }

        List<Standard> standards = gestionStandards.getAllStandards();
        for (Standard standard : standards) {
            standardComboBox.addItem(standard);
        }

        List<SystemeManagement> systemes = gestionSystemeManagement.getAllSystemeManagement();
        for (SystemeManagement systeme : systemes) {
            for (Processus processus : systeme.getProcessus()) {
                processusComboBox.addItem(processus);
            }
        }

        userComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof User) {
                    value = ((User) value).getName();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        standardComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Standard) {
                    value = ((Standard) value).getReference();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        processusComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Processus) {
                    value = ((Processus) value).getName();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Intitulé:"));
        panel.add(intituleField);
        panel.add(new JLabel("Date Début:"));
        panel.add(dateDebutSpinner);
        panel.add(new JLabel("Date Fin:"));
        panel.add(dateFinSpinner);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(new JLabel("Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Auditeur:"));
        panel.add(userComboBox);
        panel.add(new JLabel("Standard:"));
        panel.add(standardComboBox);
        panel.add(new JLabel("Processus:"));
        panel.add(processusComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Audit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            User selectedUser = (User) userComboBox.getSelectedItem();
            Standard selectedStandard = (Standard) standardComboBox.getSelectedItem();
            Processus selectedProcessus = (Processus) processusComboBox.getSelectedItem();
            if (selectedUser != null && selectedStandard != null && selectedProcessus != null) {
                Audit audit = new Audit();
                audit.setIntitule(intituleField.getText());
                audit.setDate_debut((Date) dateDebutSpinner.getValue());
                audit.setDate_fin((Date) dateFinSpinner.getValue());
                audit.setStatus((String) statusComboBox.getSelectedItem());
                audit.setType(typeField.getText());
                audit.setUser(selectedUser);
                audit.setStandard(selectedStandard);
                audit.setProcessus(selectedProcessus);
                gestionAudit.addAudit(audit);
                loadAudits();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un auditeur, un standard et un processus.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void editAudit(int id) {
        Audit audit = gestionAudit.getAudit(id);
        if (audit != null) {
            JTextField intituleField = new JTextField(audit.getIntitule());
            JSpinner dateDebutSpinner = new JSpinner(new SpinnerDateModel(audit.getDate_debut(), null, null, java.util.Calendar.DAY_OF_MONTH));
            JSpinner dateFinSpinner = new JSpinner(new SpinnerDateModel(audit.getDate_fin(), null, null, java.util.Calendar.DAY_OF_MONTH));
            dateDebutSpinner.setEditor(new JSpinner.DateEditor(dateDebutSpinner, "dd/MM/yyyy"));
            dateFinSpinner.setEditor(new JSpinner.DateEditor(dateFinSpinner, "dd/MM/yyyy"));
            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Planifié", "En cours", "Suspendu", "Terminé", "Validé", "Archivé"});
            statusComboBox.setSelectedItem(audit.getStatus());
            JTextField typeField = new JTextField(audit.getType());
            JComboBox<User> userComboBox = new JComboBox<>();
            JComboBox<Standard> standardComboBox = new JComboBox<>();
            JComboBox<Processus> processusComboBox = new JComboBox<>();

            List<User> users = gestionUsers.getAllUsers();
            for (User user : users) {
                if ("auditeur".equals(user.getRole())) {
                    userComboBox.addItem(user);
                }
            }
            userComboBox.setSelectedItem(audit.getUser());

            List<Standard> standards = gestionStandards.getAllStandards();
            for (Standard standard : standards) {
                standardComboBox.addItem(standard);
            }
            standardComboBox.setSelectedItem(audit.getStandard());

            List<SystemeManagement> systemes = gestionSystemeManagement.getAllSystemeManagement();
            for (SystemeManagement systeme : systemes) {
                for (Processus processus : systeme.getProcessus()) {
                    processusComboBox.addItem(processus);
                }
            }
            processusComboBox.setSelectedItem(audit.getProcessus());

            userComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof User) {
                        value = ((User) value).getName();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });

            standardComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof Standard) {
                        value = ((Standard) value).getReference();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });

            processusComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof Processus) {
                        value = ((Processus) value).getName();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });

            JPanel panel = new JPanel(new GridLayout(8, 2));
            panel.add(new JLabel("Intitulé:"));
            panel.add(intituleField);
            panel.add(new JLabel("Date Début:"));
            panel.add(dateDebutSpinner);
            panel.add(new JLabel("Date Fin:"));
            panel.add(dateFinSpinner);
            panel.add(new JLabel("Status:"));
            panel.add(statusComboBox);
            panel.add(new JLabel("Type:"));
            panel.add(typeField);
            panel.add(new JLabel("Auditeur:"));
            panel.add(userComboBox);
            panel.add(new JLabel("Standard:"));
            panel.add(standardComboBox);
            panel.add(new JLabel("Processus:"));
            panel.add(processusComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editer Audit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                User selectedUser = (User) userComboBox.getSelectedItem();
                Standard selectedStandard = (Standard) standardComboBox.getSelectedItem();
                Processus selectedProcessus = (Processus) processusComboBox.getSelectedItem();
                if (selectedUser != null && selectedStandard != null && selectedProcessus != null) {
                    audit.setIntitule(intituleField.getText());
                    audit.setDate_debut((Date) dateDebutSpinner.getValue());
                    audit.setDate_fin((Date) dateFinSpinner.getValue());
                    audit.setStatus((String) statusComboBox.getSelectedItem());
                    audit.setType(typeField.getText());
                    audit.setUser(selectedUser);
                    audit.setStandard(selectedStandard);
                    audit.setProcessus(selectedProcessus);
                    gestionAudit.updateAudit(id, audit);
                    loadAudits();
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez sélectionner un auditeur, un standard et un processus.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void deleteAudit(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet audit?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionAudit.deleteAudit(id);
            loadAudits();
        }
    }

    public void viewDetails(int id) {
        Audit audit = gestionAudit.getAudit(id);
        if (audit != null) {
            new AuditDetailsUI(audit).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionAuditUI().setVisible(true);
            }
        });
    }
}
