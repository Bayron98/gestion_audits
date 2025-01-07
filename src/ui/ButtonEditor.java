package ui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private GestionStandardsUI gestionStandardsUI;
    private GestionClausesUI gestionClausesUI;
    private StandardDetailsUI standardDetailsUI;
    private ClauseDetailsUI clauseDetailsUI;
    private boolean isEdit;
    private boolean isView;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, GestionStandardsUI gestionStandardsUI, boolean isEdit, boolean isView) {
        super(checkBox);
        this.gestionStandardsUI = gestionStandardsUI;
        this.isEdit = isEdit;
        this.isView = isView;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public ButtonEditor(JCheckBox checkBox, GestionClausesUI gestionClausesUI, boolean isEdit, boolean isView) {
        super(checkBox);
        this.gestionClausesUI = gestionClausesUI;
        this.isEdit = isEdit;
        this.isView = isView;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public ButtonEditor(JCheckBox checkBox, StandardDetailsUI standardDetailsUI, boolean isEdit, boolean isView) {
        super(checkBox);
        this.standardDetailsUI = standardDetailsUI;
        this.isEdit = isEdit;
        this.isView = isView;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public ButtonEditor(JCheckBox checkBox, ClauseDetailsUI clauseDetailsUI, boolean isEdit, boolean isView) {
        super(checkBox);
        this.clauseDetailsUI = clauseDetailsUI;
        this.isEdit = isEdit;
        this.isView = isView;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
            if (isView) {
                if (gestionStandardsUI != null) {
                    gestionStandardsUI.viewDetails(id);
                } else if (gestionClausesUI != null) {
                    gestionClausesUI.viewDetails(id);
                }
            } else if (isEdit) {
                if (gestionStandardsUI != null) {
                    gestionStandardsUI.editStandard(id);
                } else if (gestionClausesUI != null) {
                    gestionClausesUI.editClause(id);
                } else if (standardDetailsUI != null) {
                    standardDetailsUI.editClause(id);
                } else if (clauseDetailsUI != null) {
                    clauseDetailsUI.editStandard(id);
                }
            } else {
                if (gestionStandardsUI != null) {
                    gestionStandardsUI.deleteStandard(id);
                } else if (gestionClausesUI != null) {
                    gestionClausesUI.deleteClause(id);
                } else if (standardDetailsUI != null) {
                    standardDetailsUI.deleteClause(id);
                } else if (clauseDetailsUI != null) {
                    clauseDetailsUI.deleteStandard(id);
                }
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
