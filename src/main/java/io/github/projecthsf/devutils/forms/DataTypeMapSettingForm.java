package io.github.projecthsf.devutils.forms;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class DataTypeMapSettingForm extends JPanel {
    private LanguageEnum language;
    private Map<String, String> dataTypes;
    JBTable table;
    public DataTypeMapSettingForm(LanguageEnum language, Map<String, String> dataTypes) {
        this.language = language;
        this.dataTypes = dataTypes;
        setLayout(new BorderLayout());
        add(getDataTypeMappingPanel(), BorderLayout.NORTH);
    }


    private JPanel getDataTypeMappingPanel() {
        // Define column names
        String[] columnNames = {language.name() + " Data Type", "Data Type Mapping"};
        DefaultTableModel model = new DataTypeMappingDefaultTableModel(new Object[][]{}, columnNames);
        table = new JBTable(model);
        resetForm();
        //table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create the ToolbarDecorator
        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(table);
        decorator.setAddAction(new AddOrEditButtonRunnable(this)); // Custom add action
        decorator.setRemoveAction(new RemoveButtonRunnable(this)); // Custom remove action
        decorator.setPreferredSize(new Dimension(400, 600));

        FormBuilder builder = FormBuilder.createFormBuilder();
        builder.addComponent(decorator.createPanel());
        return builder.getPanel();
    }

    public Map<String, String> getDataTypeMap() {
        Map<String, String> dataTypeMap = new HashMap<>();
        if (table.getModel() instanceof DefaultTableModel model) {
            Vector<Vector> dataVector = model.getDataVector();

            for (Vector rowData : dataVector) {
                dataTypeMap.put((String) rowData.get(0), (String) rowData.get(1));
            }
        }

        return dataTypeMap;
    }


    public void resetForm() {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        for (String key: dataTypes.keySet()) {
            model.addRow(new Object[]{key, dataTypes.get(key)});
        }
    }

    static class DataTypeMappingDefaultTableModel extends DefaultTableModel {
        public DataTypeMappingDefaultTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1;
        }
    }

    static class AddOrEditButtonRunnable implements AnActionButtonRunnable {
        private DataTypeMapSettingForm form;
        public AddOrEditButtonRunnable(DataTypeMapSettingForm form) {
            super();
            this.form = form;
        }

        @Override
        public void run(AnActionButton anActionButton) {
            new DataTypeMappingPopupWindown(form).show();
        }
    }

    static class RemoveButtonRunnable implements AnActionButtonRunnable {
        private DataTypeMapSettingForm form;
        public RemoveButtonRunnable(DataTypeMapSettingForm form) {
            this.form = form;
        }

        @Override
        public void run(AnActionButton anActionButton) {
            DefaultTableModel model = (DefaultTableModel)form.table.getModel();
            // need to remove from highest to lowest index
            for (int i = form.table.getSelectedRows().length - 1; i >= 0; i--) {
                int row = form.table.getSelectedRows()[i];
                model.removeRow(row);
            }
        }
    }

    static class DataTypeMappingPopupWindown extends DialogWrapper {
        private DataTypeMapSettingForm form;
        private JBTextField key = new JBTextField("", 20);
        private JBTextField value = new JBTextField("", 20);
        public DataTypeMappingPopupWindown(DataTypeMapSettingForm form) {
            super(false);
            this.form = form;
            init();
        }

        @Override
        protected @Nullable JComponent createCenterPanel() {
            return FormBuilder.createFormBuilder()
                    .addLabeledComponent("Sql Data Type", key)
                    .addLabeledComponent("Mapping Data Type", value)
                    .getPanel();
        }

        @Override
        protected void applyFields() {
            if (key.getText().isEmpty() || value.getText().isEmpty()) {
                Messages.showErrorDialog("Sql Data Type and Mapping Data Type are required!", "Invalid");
                return;
            }

            if (form.getDataTypeMap().containsKey(key.getText().toUpperCase())) {
                Messages.showErrorDialog(String.format("Sql Data Type: %s is aleardy existed!", key.getText()), "Invalid");
                return;
            }
            DefaultTableModel model = (DefaultTableModel)form.table.getModel();
            model.addRow(new Object[]{key.getText().toUpperCase(), value.getText()});
        }
    }
}
