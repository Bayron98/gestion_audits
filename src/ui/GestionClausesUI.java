package ui;

import javax.swing.*;

public class GestionClausesUI extends JFrame {
    public GestionClausesUI() {
        setTitle("Gérer les Clauses");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ajouter les composants pour gérer les Clauses
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Ajouter les composants pour gérer les Clauses (CRUD)
        // ...existing code...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionClausesUI().setVisible(true);
            }
        });
    }
}
