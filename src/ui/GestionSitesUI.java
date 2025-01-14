package ui;

import service.GestionSites;
import dao.Site;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestionSitesUI extends JFrame {
    private GestionSites gestionSite;
    private JTable table;
    private DefaultTableModel tableModel;

    public GestionSitesUI() {
        gestionSite = new GestionSites();

        setTitle("Gérer les Sites");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JButton addButton = new JButton("Ajouter Site");
        panel.add(addButton, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Adresse", "Description", "Editer", "Supprimer"}, 0);
        table = new JTable(tableModel);

        table.getColumn("Editer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editer").setCellEditor(new ButtonEditor(new JCheckBox(), this, true, false));
        table.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
        table.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), this, false, false));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadSites();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSite();
            }
        });
    }

    private void loadSites() {
        List<Site> sites = gestionSite.getAllSites();
        tableModel.setRowCount(0); // Clear existing rows

        if (sites.isEmpty()) {
            tableModel.addRow(new Object[]{"Vide", "Vide", "Vide", "Vide", "Editer", "Supprimer"});
        } else {
            for (Site site : sites) {
                tableModel.addRow(new Object[]{
                        site.getId(),
                        site.getName(),
                        site.getAdresse(),
                        site.getDescription(),
                        "Editer",
                        "Supprimer"
                });
            }
        }
    }

    private void addSite() {
        JTextField nameField = new JTextField();
        JTextField adresseField = new JTextField();
        JTextField descriptionField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nameField);
        panel.add(new JLabel("Adresse:"));
        panel.add(adresseField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Site", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Site site = new Site();
            site.setName(nameField.getText());
            site.setAdresse(adresseField.getText());
            site.setDescription(descriptionField.getText());
            gestionSite.addSite(site);
            loadSites();
        }
    }

    public void editSite(int id) {
        Site site = gestionSite.getSite(id);
        if (site != null) {
            JTextField nameField = new JTextField(site.getName());
            JTextField adresseField = new JTextField(site.getAdresse());
            JTextField descriptionField = new JTextField(site.getDescription());

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Nom:"));
            panel.add(nameField);
            panel.add(new JLabel("Adresse:"));
            panel.add(adresseField);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editer Site", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                site.setName(nameField.getText());
                site.setAdresse(adresseField.getText());
                site.setDescription(descriptionField.getText());
                gestionSite.updateSite(id, site);
                loadSites();
            }
        }
    }

    public void deleteSite(int id) {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce site?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionSite.deleteSite(id);
            loadSites();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionSitesUI().setVisible(true);
            }
        });
    }
}
