package io.github.projecthsf.devutils.forms.toolWindows;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.ActionLink;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.enums.CsvSeparatorEnum;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import io.github.projecthsf.devutils.utils.DatasetUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class DatasetToDTOWindowForm extends AbstractDatasetWindowForm {

    //protected final JTextArea codeTemplate;
    protected VariablePanel variable;

    public DatasetToDTOWindowForm() {
        super();
        //codeTemplate = new JTextArea("");
        variable = new VariablePanel();
        init();
    }

    public void updateForm(CsvSeparatorEnum csvSeparator, String dataset, String template, Map<String, String> variables) {
        variable.removeItems();
        for (String key: variables.keySet()) {
            JTextField field = new JTextField(variables.get(key));
            field.getDocument().addDocumentListener(variable.getListener());
            variable.addItem(key, field);
        }

        variable.repaint();
        // must be update after variable update
        super.updateForm(csvSeparator, dataset, template);
    }

    public void addListeners(
            DocumentListener datasetListener,
            javax.swing.event.DocumentListener variableListener,
            ActionListener separatorLister
    ) {

        variable.setListener(variableListener);
        super.addListeners(datasetListener, null, separatorLister);
    }

    public Map<String, String> getVariables() {
        Map<String, String> items = new HashMap<>();
        for (String key: variable.getItems().keySet()) {
            items.put(key, variable.getItems().get(key).getText());
        }

        return items;
    }

    protected JPanel getCenterPanel() {
        String datasetTooltipMsg = DatasetUtil.getTemplate("templates/dataset-tooltip.html");
        JButton dataSetTooltip = DatasetUtil.getToolTipButton("Dataset (CSV)", datasetTooltipMsg);
        JPanel datasetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datasetPanel.add(dataSetTooltip);
        datasetPanel.add(new JBLabel("Separator"));
        datasetPanel.add(separartor);

        String codeTemplateTooltipMsg = DatasetUtil.getTemplate("templates/code-template-tooltip.html");
        JButton codeTemplateTooltip = DatasetUtil.getToolTipButton("Code template", codeTemplateTooltipMsg);
        JPanel codeTemplatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        codeTemplatePanel.add(codeTemplateTooltip);
        ActionLink link = new ActionLink(
                "View document",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BrowserUtil.browse("https://github.com/projecthsf/intellij-devutils/blob/main/docs/apply-dataset/code-template.md");
                    }
                }
        );
        link.setExternalLinkIcon();
        codeTemplatePanel.add(link);
        return FormBuilder.createFormBuilder()
                .addComponent(datasetPanel)
                .addComponent(ActionUtil.getEditorPanel(dataSet, 400, 180))
                .addComponent(variable)
                .addComponent(new JBLabel("Result"))
                .addComponent(ActionUtil.getEditorPanel(preview, 400, 180))
                .getPanel();
    }

    public static class VariablePanel extends JPanel {
        private Map<String, JTextField> items = new HashMap<>();
        private javax.swing.event.DocumentListener listener;

        public javax.swing.event.DocumentListener getListener() {
            return listener;
        }

        public void setListener(javax.swing.event.DocumentListener listener) {
            this.listener = listener;
        }

        public void  removeItems() {
            removeAll();
            items = new HashMap<>();
        }

        public void addItem(String key, JTextField field) {
            add(new JBLabel(key));
            add(field);
            items.put(key, field);
        }

        public Map<String, JTextField> getItems() {
            return items;
        }
    }
}


