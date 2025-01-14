package ui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor<T> extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private T context;
    private boolean isEdit;
    private boolean isView;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, T context, boolean isEdit, boolean isView) {
        super(checkBox);
        this.context = context;
        this.isEdit = isEdit;
        this.isView = isView;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    public ButtonEditor(JCheckBox checkBox, T context) {
        this(checkBox, context, false, false);
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
                if (context instanceof GestionStandardsUI) {
                    ((GestionStandardsUI) context).viewDetails(id);
                } else if (context instanceof GestionClausesUI) {
                    ((GestionClausesUI) context).viewDetails(id);
                }else if (context instanceof GestionSystemeManagementUI) {
                    ((GestionSystemeManagementUI) context).viewDetails(id);
                }
            } else if (isEdit) {
                if (context instanceof GestionStandardsUI) {
                    ((GestionStandardsUI) context).editStandard(id);
                } else if (context instanceof GestionClausesUI) {
                    ((GestionClausesUI) context).editClause(id);

                }else if (context instanceof GestionSitesUI) {
                    ((GestionSitesUI) context).editSite(id);
                }else if (context instanceof GestionResponsablesUI) {
                    ((GestionResponsablesUI) context).editResponsable(id);
                }else if (context instanceof GestionSystemeManagementUI) {
                    ((GestionSystemeManagementUI) context).editSystemeManagement(id);
                }else if (context instanceof SystemeManagementDetailsUI) {
                    ((SystemeManagementDetailsUI) context).editProcessus(id);
                }
            } else {
                if (context instanceof GestionStandardsUI) {
                    ((GestionStandardsUI) context).deleteStandard(id);
                } else if (context instanceof GestionClausesUI) {
                    ((GestionClausesUI) context).deleteClause(id);
                }else if (context instanceof GestionSitesUI) {
                    ((GestionSitesUI) context).deleteSite(id);
                }else if (context instanceof GestionResponsablesUI) {
                    ((GestionResponsablesUI) context).deleteResponsable(id);
                }else if (context instanceof GestionSystemeManagementUI) {
                    ((GestionSystemeManagementUI) context).deleteSystemeManagement(id);
                }else if (context instanceof SystemeManagementDetailsUI) {
                    ((SystemeManagementDetailsUI) context).deleteProcessus(id);
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
