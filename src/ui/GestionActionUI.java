package ui;

import service.GestionAction;
import service.GestionResponsables;
import service.GestionAudit;
import dao.Action;
import dao.Responsable;
import dao.SystemeExigence;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class GestionActionUI extends JFrame {
    private GestionAction gestionAction;
    private GestionResponsables gestionResponsables;
    private GestionAudit gestionAudit;
    private JTable table;
    private DefaultTableModel tableModel;

    public GestionActionUI() {
        gestionAction = new GestionAction();
        gestionResponsables = new GestionResponsables();
        gestionAudit = new GestionAudit();

        setTitle("Gérer les Actions");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JButton addButton = new JButton("Ajouter Action");
        panel.add(addButton, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Description", "Date Début Prévue", "Date Fin Prévue", "Date Fin Réelle", "Système Exigence", "Responsable", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadActions();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAction();
            }
        });
    }

    private void loadActions() {
        List<Action> actions = gestionAction.getAllActions();
        tableModel.setRowCount(0); // Clear existing rows

        if (actions.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Vide", "Vide", "Vide", "Vide", "Vide", "Editer", "Supprimer"});
        } else {
            for (Action action : actions) {
                tableModel.addRow(new Object[]{
                        action.getId(),
                        action.getNom(),
                        action.getDescription(),
                        action.getDate_debut_prevue(),
                        action.getDate_fin_prevue(),
                        action.getDate_fin_reelle(),
                        action.getSysteme_exigence().getId(),
                        action.getResponsable().getNom() + " " + action.getResponsable().getPrenom(),
                        "Editer",
                        "Supprimer"
                });
            }
        }
    }

    private void addAction() {
        JTextField nomField = new JTextField();
        JTextField descriptionField = new JTextField();
        JSpinner dateDebutPrevueSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner dateFinPrevueSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner dateFinReelleSpinner = new JSpinner(new SpinnerDateModel());
        dateDebutPrevueSpinner.setEditor(new JSpinner.DateEditor(dateDebutPrevueSpinner, "dd/MM/yyyy"));
        dateFinPrevueSpinner.setEditor(new JSpinner.DateEditor(dateFinPrevueSpinner, "dd/MM/yyyy"));
        dateFinReelleSpinner.setEditor(new JSpinner.DateEditor(dateFinReelleSpinner, "dd/MM/yyyy"));
        JComboBox<SystemeExigence> systemeExigenceComboBox = new JComboBox<>();
        JComboBox<Responsable> responsableComboBox = new JComboBox<>();

        List<SystemeExigence> systemeExigences = gestionAudit.getAllSystemeExigence();
        for (SystemeExigence systemeExigence : systemeExigences) {
            systemeExigenceComboBox.addItem(systemeExigence);
        }

        List<Responsable> responsables = gestionResponsables.getAllResponsables();
        for (Responsable responsable : responsables) {
            responsableComboBox.addItem(responsable);
        }

        systemeExigenceComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof SystemeExigence) {
                    value = ((SystemeExigence) value).getId();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        responsableComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Responsable) {
                    value = ((Responsable) value).getNom() + " " + ((Responsable) value).getPrenom();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Date Début Prévue:"));
        panel.add(dateDebutPrevueSpinner);
        panel.add(new JLabel("Date Fin Prévue:"));
        panel.add(dateFinPrevueSpinner);
        panel.add(new JLabel("Date Fin Réelle:"));
        panel.add(dateFinReelleSpinner);
        panel.add(new JLabel("Système Exigence:"));
        panel.add(systemeExigenceComboBox);
        panel.add(new JLabel("Responsable:"));
        panel.add(responsableComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Action", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            SystemeExigence selectedSystemeExigence = (SystemeExigence) systemeExigenceComboBox.getSelectedItem();
            Responsable selectedResponsable = (Responsable) responsableComboBox.getSelectedItem();
            if (selectedSystemeExigence != null && selectedResponsable != null) {
                Action action = new Action();
                action.setNom(nomField.getText());
                action.setDescription(descriptionField.getText());
                action.setDate_debut_prevue((Date) dateDebutPrevueSpinner.getValue());
                action.setDate_fin_prevue((Date) dateFinPrevueSpinner.getValue());
                action.setDate_fin_reelle((Date) dateFinReelleSpinner.getValue());
                action.setSysteme_exigence(selectedSystemeExigence);
                action.setResponsable(selectedResponsable);
                gestionAction.addAction(action);
                loadActions();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un système d'exigence et un responsable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void editAction(int id) {
        Action action = gestionAction.getAction(id);
        if (action != null) {
            JTextField nomField = new JTextField(action.getNom());
            JTextField descriptionField = new JTextField(action.getDescription());
            JSpinner dateDebutPrevueSpinner = new JSpinner(new SpinnerDateModel(action.getDate_debut_prevue(), null, null, java.util.Calendar.DAY_OF_MONTH));
            JSpinner dateFinPrevueSpinner = new JSpinner(new SpinnerDateModel(action.getDate_fin_prevue(), null, null, java.util.Calendar.DAY_OF_MONTH));
            JSpinner dateFinReelleSpinner = new JSpinner(new SpinnerDateModel(action.getDate_fin_reelle(), null, null, java.util.Calendar.DAY_OF_MONTH));
            dateDebutPrevueSpinner.setEditor(new JSpinner.DateEditor(dateDebutPrevueSpinner, "dd/MM/yyyy"));
            dateFinPrevueSpinner.setEditor(new JSpinner.DateEditor(dateFinPrevueSpinner, "dd/MM/yyyy"));
            dateFinReelleSpinner.setEditor(new JSpinner.DateEditor(dateFinReelleSpinner, "dd/MM/yyyy"));
            JComboBox<SystemeExigence> systemeExigenceComboBox = new JComboBox<>();
            JComboBox<Responsable> responsableComboBox = new JComboBox<>();

            List<SystemeExigence> systemeExigences = gestionAudit.getAllSystemeExigence();
            for (SystemeExigence systemeExigence : systemeExigences) {
                systemeExigenceComboBox.addItem(systemeExigence);
            }
            systemeExigenceComboBox.setSelectedItem(action.getSysteme_exigence());

            List<Responsable> responsables = gestionResponsables.getAllResponsables();
            for (Responsable responsable : responsables) {
                responsableComboBox.addItem(responsable);
            }
            responsableComboBox.setSelectedItem(action.getResponsable());

            systemeExigenceComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof SystemeExigence) {
                        value = ((SystemeExigence) value).getId();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });

            responsableComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof Responsable) {
                        value = ((Responsable) value).getNom() + " " + ((Responsable) value).getPrenom();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });

            JPanel panel = new JPanel(new GridLayout(8, 2));
            panel.add(new JLabel("Nom:"));
            panel.add(nomField);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Date Début Prévue:"));
            panel.add(dateDebutPrevueSpinner);
            panel.add(new JLabel("Date Fin Prévue:"));
            panel.add(dateFinPrevueSpinner);
            panel.add(new JLabel("Date Fin Réelle:"));
            panel.add(dateFinReelleSpinner);
            panel.add(new JLabel("Système Exigence:"));
            panel.add(systemeExigenceComboBox);
            panel.add(new JLabel("Responsable:"));
            panel.add(responsableComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editer Action", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                SystemeExigence selectedSystemeExigence = (SystemeExigence) systemeExigenceComboBox.getSelectedItem();
                Responsable selectedResponsable = (Responsable) responsableComboBox.getSelectedItem();
                if (selectedSystemeExigence != null && selectedResponsable != null) {
                    action.setNom(nomField.getText());
                    action.setDescription(descriptionField.getText());
                    action.setDate_debut_prevue((Date) dateDebutPrevueSpinner.getValue());
                    action.setDate_fin_prevue((Date) dateFinPrevueSpinner.getValue());
                    action.setDate_fin_reelle((Date) dateFinReelleSpinner.getValue());
                    action.setSysteme_exigence(selectedSystemeExigence);
                    action.setResponsable(selectedResponsable);
                    gestionAction.updateAction(id, action);
                    loadActions();
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez sélectionner un système d'exigence et un responsable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void deleteAction(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette action?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionAction.deleteAction(id);
            loadActions();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionActionUI().setVisible(true);
            }
        });
    }
}
