package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardAdmin extends JFrame {
    private JButton gestionStandardsButton;
    private JButton gestionClausesButton;

    public DashboardAdmin() {
        setTitle("Tableau de Bord Admin");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        gestionStandardsButton = new JButton("Gérer les Standards");
        gestionStandardsButton.setBounds(50, 50, 150, 25);
        panel.add(gestionStandardsButton);

        gestionClausesButton = new JButton("Gérer les Clauses");
        gestionClausesButton.setBounds(200, 50, 150, 25);
        panel.add(gestionClausesButton);

        gestionStandardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionStandardsUI().setVisible(true);
            }
        });

        gestionClausesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionClausesUI().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DashboardAdmin().setVisible(true);
            }
        });
    }
}
