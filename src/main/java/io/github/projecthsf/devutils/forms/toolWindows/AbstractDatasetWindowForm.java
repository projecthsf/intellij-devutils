package io.github.projecthsf.devutils.forms.toolWindows;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.ui.ComboBox;
import io.github.projecthsf.devutils.enums.CsvSeparatorEnum;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class AbstractDatasetWindowForm extends JPanel {
    protected EditorEx dataSet;
    protected Editor codeTemplate;
    protected ComboBox<CsvSeparatorEnum> separartor;
    protected Editor preview;

    AbstractDatasetWindowForm() {
        dataSet = ActionUtil.getEditorEx(LanguageEnum.JAVA);
        codeTemplate = ActionUtil.getEditor("");
        preview = ActionUtil.getEditor("", true);
        separartor = new ComboBox<>(CsvSeparatorEnum.values());

        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
    }

    protected void init() {
        add(getCenterPanel(), BorderLayout.PAGE_START);
    }

    protected abstract JPanel getCenterPanel();

    public String getPreview() {
        return preview.getDocument().getText();
    }

    public String getCodeTemplate() {
        return codeTemplate.getDocument().getText();
    }

    public String getDataset() {
        return dataSet.getDocument().getText();
    }

    public CsvSeparatorEnum getSeparator() {
        return (CsvSeparatorEnum) separartor.getSelectedItem();
    }

    public void updateForm(CsvSeparatorEnum csvSeparator, String dataset, String template) {
        ActionUtil.updateText(codeTemplate, template);
        separartor.setSelectedItem(csvSeparator);
        ActionUtil.updateText(dataSet, dataset);
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

    public void reset() {
        separartor.setSelectedItem(CsvSeparatorEnum.COMMA);
        ActionUtil.updateText(dataSet, "");
        ActionUtil.updateText(codeTemplate, "");
        ActionUtil.updateText(preview, "");
    }

    public void addListeners(DocumentListener datasetListener, DocumentListener templateCodeListner, ActionListener separatorLister) {
        dataSet.getDocument().addDocumentListener(datasetListener);
        if (templateCodeListner != null) {
            codeTemplate.getDocument().addDocumentListener(templateCodeListner);
        }
        separartor.addActionListener(separatorLister);
    }
}
