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
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.enums.CsvSeparatorEnum;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import io.github.projecthsf.devutils.utils.DatasetUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatasetSnippetWindowForm extends JPanel {
    protected final EditorEx dataSet;
    protected final ComboBox<CsvSeparatorEnum> separartor;
    protected final Editor codeTemplate;
    protected final Editor preview;

    public DatasetSnippetWindowForm() {
        dataSet = ActionUtil.getEditorEx(LanguageEnum.JAVA);
        codeTemplate = ActionUtil.getEditor("");
        preview = ActionUtil.getEditor("", true);
        separartor = new ComboBox<>(CsvSeparatorEnum.values());

        //dataSet.getDocument().addDocumentListener(new TextAreaDocumentListener(request, this));
        //templateCode.getDocument().addDocumentListener(new TextAreaDocumentListener(request, this));
        //separartor.addActionListener(new ComboBoxListener(request, this));

        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
        add(getCenterPanel(), BorderLayout.PAGE_START);
        //add(getControlPanel(), BorderLayout.CENTER);
    }

    public void reset() {
        separartor.setSelectedItem(CsvSeparatorEnum.COMMA);
        ActionUtil.updateText(dataSet, "");
        ActionUtil.updateText(codeTemplate, "");
        //ActionUtil.updateText(preview, "");
    }

    public void updateForm(CsvSeparatorEnum csvSeparator, String dataset, String codeTemplate) {
        separartor.setSelectedItem(csvSeparator);
        ActionUtil.updateText(dataSet, dataset);
        ActionUtil.updateText(this.codeTemplate, codeTemplate);
    }

    public String getDataset() {
        return dataSet.getDocument().getText();
    }

    public CsvSeparatorEnum getSeparator() {
        return (CsvSeparatorEnum) separartor.getSelectedItem();
    }

    public void addListeners(DocumentListener datasetListener, DocumentListener templateCodeListner, ActionListener separatorLister) {
        dataSet.getDocument().addDocumentListener(datasetListener);
        codeTemplate.getDocument().addDocumentListener(templateCodeListner);
        separartor.addActionListener(separatorLister);
    }

    public String getCodeTemplate() {
        return codeTemplate.getDocument().getText();
    }

    public String getPreview() {
        return preview.getDocument().getText();
    }

    public void updatePreview(String text) {
        ActionUtil.updateText(preview, text);
    }

    public void updateDataset(String text) {
        ActionUtil.updateText(dataSet, text);
    }

    public void updateCodeTemplate(String text) {
        ActionUtil.updateText(codeTemplate, text);
    }

    public void updateCodeTemplate(Caret caret) {
        ActionUtil.updateText(codeTemplate, caret.getSelectedText());
    }

    private JPanel getCenterPanel() {
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
                .addComponent(codeTemplatePanel)
                .addComponent(ActionUtil.getEditorPanel(codeTemplate, 400, 180))
                .addComponent(new JBLabel("Result"))
                .addComponent(ActionUtil.getEditorPanel(preview, 400, 180))
                .getPanel();
    }

    public static class Request {
        private ToolWindow toolWindow;
        public Request(ToolWindow toolWindow) {
            this.toolWindow = toolWindow;
        }

    }
    static class ApplyResult {
        private Caret caret;

        public ApplyResult(Caret caret) {
            this.caret = caret;
        }

        public Caret getCaret() {
            return caret;
        }

        public void setCaret(Caret caret) {
            this.caret = caret;
        }
    }
}


