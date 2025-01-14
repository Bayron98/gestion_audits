package ui;

import service.GestionSystemeManagement;
import dao.SystemeManagement;
import dao.Processus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionProcessusUI extends JFrame {
    private GestionSystemeManagement gestionSystemeManagement;
    private JTable table;
    private DefaultTableModel tableModel;

    public GestionProcessusUI() {
        gestionSystemeManagement = new GestionSystemeManagement();

        setTitle("Gérer les Processus");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Nom", "Responsable", "Système de Management"}, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadProcessus();
    }

    private void loadProcessus() {
        List<SystemeManagement> systemes = gestionSystemeManagement.getAllSystemeManagement();
        tableModel.setRowCount(0); // Clear existing rows

        for (SystemeManagement systeme : systemes) {
            for (Processus processus : systeme.getProcessus()) {
                tableModel.addRow(new Object[]{
                        processus.getId(),
                        processus.getDescription(),
                        processus.getName(),
                        processus.getResponsable().getNom(),
                        systeme.getNom()
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionProcessusUI().setVisible(true);
            }
        });
    }
}
