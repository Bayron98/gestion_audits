package ui;

import service.GestionOrganisation;
import dao.Organisation;

import javax.swing.*;
import java.awt.*;

public class GestionOrganisationUI extends JFrame {
    private GestionOrganisation gestionOrganisation;
    private Organisation organisation;
    private JPanel panel;
    private JPanel detailsPanel;
    private JPanel buttonPanel;

    public GestionOrganisationUI() {
        gestionOrganisation = new GestionOrganisation();

        setTitle("Gérer l'Organisation");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel(new BorderLayout());
        add(panel);

        loadOrganisationDetails();
    }

    private void loadOrganisationDetails() {
        panel.removeAll();

        organisation = gestionOrganisation.getOrganisation();
        if (organisation == null) {
            JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton addButton = new JButton("Ajouter Organisation");
            addPanel.add(addButton);
            panel.add(addPanel, BorderLayout.CENTER);

            addButton.addActionListener(e -> addOrganisation());
        } else {
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton editButton = new JButton("Éditer Organisation");
            JButton deleteButton = new JButton("Supprimer Organisation");
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);

            editButton.addActionListener(e -> editOrganisation());
            deleteButton.addActionListener(e -> deleteOrganisation());

            detailsPanel = new JPanel(new GridLayout(3, 2));
            detailsPanel.add(new JLabel("Nom:"));
            detailsPanel.add(new JLabel(organisation.getName()));

            detailsPanel.add(new JLabel("Adresse:"));
            detailsPanel.add(new JLabel(organisation.getAdresse()));

            panel.add(buttonPanel, BorderLayout.NORTH);
            panel.add(detailsPanel, BorderLayout.CENTER);

            // Ajouter le contenu de GestionSitesUI en dessous des détails de l'organisation
            GestionSitesUI gestionSitesUI = new GestionSitesUI();
            JPanel sitesPanel = new JPanel(new BorderLayout());
            JLabel sitesTitle = new JLabel("Liste des Sites rattachés à l'Organisation");
            sitesPanel.add(sitesTitle, BorderLayout.NORTH);
            sitesPanel.add(gestionSitesUI.getContentPane(), BorderLayout.CENTER);
            panel.add(sitesPanel, BorderLayout.SOUTH);

            // S'assurer que le tableau des sites et le titre restent visibles
            gestionSitesUI.addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    sitesPanel.revalidate();
                    sitesPanel.repaint();
                }
            });
        }

        panel.revalidate();
        panel.repaint();
    }

    private void addOrganisation() {
        JTextField nameField = new JTextField();
        JTextField adresseField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nameField);
        panel.add(new JLabel("Adresse:"));
        panel.add(adresseField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter Organisation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            organisation = new Organisation();
            organisation.setName(nameField.getText());
            organisation.setAdresse(adresseField.getText());
            gestionOrganisation.addOrganisation(organisation);
            loadOrganisationDetails();
        }
    }

    private void editOrganisation() {
        JTextField nameField = new JTextField(organisation.getName());
        JTextField adresseField = new JTextField(organisation.getAdresse());

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nameField);
        panel.add(new JLabel("Adresse:"));
        panel.add(adresseField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Éditer Organisation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            organisation.setName(nameField.getText());
            organisation.setAdresse(adresseField.getText());
            gestionOrganisation.updateOrganisation(organisation);
            JOptionPane.showMessageDialog(this, "Organisation mise à jour avec succès.");
            loadOrganisationDetails();
        }
    }

    private void deleteOrganisation() {
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette organisation?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestionOrganisation.deleteOrganisation();
            loadOrganisationDetails();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionOrganisationUI().setVisible(true));
    }
}
