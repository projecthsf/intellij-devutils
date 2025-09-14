package io.github.projecthsf.devutils.forms.settings;

import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.enums.CsvSeparatorEnum;
import io.github.projecthsf.devutils.forms.TableForm;
import io.github.projecthsf.devutils.forms.toolWindows.DatasetSnippetWindowForm;
import io.github.projecthsf.devutils.utils.DatasetUtil;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DatasetToDTOSettingForm extends DatasetSnippetWindowForm {
    protected VariableTable variable;
    public DatasetToDTOSettingForm() {
        super(false);
        variable = new VariableTable();
        init();
    }

    @Override
    protected JPanel getCenterPanel() {
        String tooltipMsg = DatasetUtil.getTemplate("templates/variable-tooltip.html");
        JButton variableTooltip = DatasetUtil.getToolTipButton("Variables", tooltipMsg);
        return FormBuilder.createFormBuilder()
                .addComponent(variableTooltip)
                .addComponent(variable.getComponent())
                .addComponent(super.getCenterPanel())
                .getPanel();
    }

    public void addListeners(DocumentListener datasetListener, DocumentListener templateCodeListner, ActionListener separatorLister, TableModelListener tableModelListener) {
        super.addListeners(datasetListener, templateCodeListner, separatorLister);
        variable.addListener(tableModelListener);
    }

    public Map<String, String> getVariable() {
        Map<String, String> map = new HashMap<>();
        for (Vector vector: variable.getRecords()) {
            map.put((String) vector.get(0), (String) vector.get(1));
        }

        return map;
    }

    @Override
    public void reset() {
        System.out.println("== reset");
        super.reset();
        variable.reset();
    }

    public void updateForm(CsvSeparatorEnum csvSeparator, String dataset, String codeTemplate, Map<String, String> variables) {
        super.updateForm(csvSeparator, dataset, codeTemplate);
        List<Object[]> items = new ArrayList<>();
        for (String key: variables.keySet()) {
            items.add(new Object[]{key, variables.get(key)});
        }
        variable.resetAndUpdate(items);
    }

    static class VariableTable extends TableForm {
        private JBTextField varName;
        private JBTextField varValue;
        public VariableTable() {
            super(400, 100);
            init();
        }

        @Override
        protected Map<String, JComponent> getConfigFields() {
            varName = new JBTextField("");
            varValue  = new JBTextField("");
            Map<String, JComponent> fields = new LinkedHashMap<>();
            fields.put("Name", varName);
            fields.put("Default Value", varValue);
            return fields;
        }


        @Override
        protected List<Object> validateAndGetFormValues() {
            return List.of(varName.getText(), varValue.getText());
        }

        @Override
        protected void resetFormFields() {
            varName.setText("");
            varValue.setText("");
        }

    }
}
