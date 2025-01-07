package ui;

import javax.swing.*;

public class GestionStandardsUI extends JFrame {
    public GestionStandardsUI() {
        setTitle("Gérer les Standards");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ajouter les composants pour gérer les Standards
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Ajouter les composants pour gérer les Standards (CRUD)
        // ...existing code...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionStandardsUI().setVisible(true);
            }
        });
    }
}
