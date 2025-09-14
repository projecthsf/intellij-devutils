package io.github.projecthsf.devutils.forms;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class TableForm {
    JBTable table;
    private ToolbarDecorator decorator;
    Map<String, JComponent> fields;
    private List<Object[]> configRecords;

    public TableForm(int width, int height) {
        this(new ArrayList<>(), width, height);
    }

    public TableForm(List<Object[]> configRecords, int width, int height) {
        this.fields = getConfigFields();
        this.configRecords = configRecords;
        table = new JBTable(new TableModel(this));
        decorator = ToolbarDecorator.createDecorator(table);
        decorator.setAddAction(new AddOrEditButtonRunnable(this)); // Custom add action
        decorator.setRemoveAction(new RemoveButtonRunnable(this)); // Custom remove action
        if (width > 0 || height > 0) {
            decorator.setPreferredSize(new Dimension(width, height));
        }
    }

    public void addListener(TableModelListener listener) {
        table.getModel().addTableModelListener(listener);
    }

    protected void init() {
        reset();
    }

    public JPanel getComponent() {
        return decorator.createPanel();
    }

    public Vector[] getRecords() {
        if (table.getModel() instanceof DefaultTableModel model) {
            Vector<Vector> dataVector = model.getDataVector();
            return dataVector.toArray(new Vector[0]);

        }
        return new Vector[0];
    }

    protected abstract Map<String, JComponent> getConfigFields();
    protected abstract List<Object> validateAndGetFormValues();
    protected abstract void resetFormFields();
    protected boolean isCellEditable(int row, int column) {
        return true;
    }

    public void reset() {
        TableModel model = (TableModel) table.getModel();
        model.setRowCount(0);
        for (Object[] objects: configRecords) {
            model.addRow(objects);
        }
    }

    public void resetAndUpdate(List<Object[]> records) {
        TableModel model = (TableModel) table.getModel();
        model.setRowCount(0);
        for (Object[] objects: records) {
            model.addRow(objects);
        }
    }

    static class TableModel extends DefaultTableModel {
        private TableForm form;
        public TableModel(TableForm form) {
            super(new Object[][]{}, form.fields.keySet().toArray(new String[0]));
            this.form = form;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return form.isCellEditable(row, column);
        }
    }

    static class AddOrEditButtonRunnable implements AnActionButtonRunnable {
        private TableForm form;
        public AddOrEditButtonRunnable(TableForm form) {
            super();
            this.form = form;
        }

        @Override
        public void run(AnActionButton anActionButton) {
            new PopupWindown(form).show();
        }
    }

    static class RemoveButtonRunnable implements AnActionButtonRunnable {
        private TableForm form;
        public RemoveButtonRunnable(TableForm form) {
            this.form = form;
        }

        @Override
        public void run(AnActionButton anActionButton) {
            TableModel model = (TableModel)form.table.getModel();
            // need to remove from highest to lowest index
            for (int i = form.table.getSelectedRows().length - 1; i >= 0; i--) {
                int row = form.table.getSelectedRows()[i];
                model.removeRow(row);
            }
        }
    }

    static class PopupWindown extends DialogWrapper {
        private TableForm form;
        public PopupWindown(TableForm form) {
            super(false);
            this.form = form;
            init();
        }

        @Override
        protected @Nullable JComponent createCenterPanel() {
            form.resetFormFields();
            FormBuilder builder = FormBuilder.createFormBuilder();
            for (String key: form.fields.keySet()) {
                builder.addLabeledComponent(key, form.fields.get(key));
            }

            return builder.getPanel();
        }

        @Override
        protected void applyFields() {
            List<Object> values = form.validateAndGetFormValues();
            if (values == null || values.size() == 0 || values.size() < form.fields.size()) {
                Messages.showErrorDialog("No values OR Number of values is not match with fields", "Invalid");
                return;
            }

            DefaultTableModel model = (DefaultTableModel)form.table.getModel();
            model.addRow(values.toArray());
        }
    }
}
