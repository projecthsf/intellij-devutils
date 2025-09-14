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

public class DatasetSnippetWindowForm extends AbstractDatasetWindowForm {

    //protected final Editor codeTemplate;
    public DatasetSnippetWindowForm() {
        super();
        codeTemplate = ActionUtil.getEditor("");
        init();
    }

    public DatasetSnippetWindowForm(boolean init) {
        dataSet = ActionUtil.getEditorEx(LanguageEnum.JAVA);
        //codeTemplate = ActionUtil.getEditor("");
        preview = ActionUtil.getEditor("", true);
        separartor = new ComboBox<>(CsvSeparatorEnum.values());
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
                .addComponent(ActionUtil.getEditorPanel(dataSet, 400, 100))
                .addComponent(codeTemplatePanel)
                .addComponent(ActionUtil.getEditorPanel(codeTemplate, 400, 180))
                .addComponent(new JBLabel("Result"))
                .addComponent(ActionUtil.getEditorPanel(preview, 400, 180))
                .getPanel();
    }
}


