package io.github.projecthsf.devutils.forms;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class SqlToDTOWindowForm extends JPanel {
    private final EditorEx dataSet;
    private final Editor templateCode;
    private final Editor preview;

    public SqlToDTOWindowForm(Request request) {
        //dataSet = ActionUtil.getEditorEx(SqlFileType.INSTANCE);
        dataSet = ActionUtil.getEditorEx(LanguageEnum.JAVA);
        templateCode = ActionUtil.getEditor("");
        preview = ActionUtil.getEditor("", true);

        dataSet.getDocument().addDocumentListener(new TextAreaDocumentListener(request, this));
        templateCode.getDocument().addDocumentListener(new TextAreaDocumentListener(request, this));


        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
        add(getCenterPanel(), BorderLayout.PAGE_START);
        //add(getControlPanel(), BorderLayout.CENTER);
    }

    public void reset() {
        ActionUtil.updateText(dataSet, "");
        ActionUtil.updateText(templateCode, "");
        //ActionUtil.updateText(preview, "");
    }

    public String getDataSet() {
        return dataSet.getDocument().getText();
    }

    public String getTemplateCode() {
        return templateCode.getDocument().getText();
    }

    public String getPreview() {
        return preview.getDocument().getText();
    }

    public void updatePreview(String text) {
        ActionUtil.updateText(preview, text);
    }

    public void updateDataSet(String text) {
        ActionUtil.updateText(dataSet, text);
    }

    public void updateCodeTemplate(Caret caret) {
        ActionUtil.updateText(templateCode, caret.getSelectedText());
    }

    private JPanel getCenterPanel() {
        String datasetTooltipMsg = ApplyDatasetUtil.getTemplate("templates/dataset-tooltip.html");
        String codeTemplateToolTip = ApplyDatasetUtil.getTemplate("templates/code-template-tooltip.html");
        JButton dataSetTooltip = ApplyDatasetUtil.getToolTipButton("Data set (CSV)", datasetTooltipMsg);
        JButton codeTemplateTooltip = ApplyDatasetUtil.getToolTipButton("Code template", codeTemplateToolTip);

        return FormBuilder.createFormBuilder()
                .addComponent(dataSetTooltip)
                .addComponent(ActionUtil.getEditorPanel(dataSet, 400, 180))
                .addComponent(codeTemplateTooltip)
                .addComponent(ActionUtil.getEditorPanel(templateCode, 400, 180))
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

    static class TextAreaDocumentListener implements DocumentListener {
        private Request request;
        private SqlToDTOWindowForm form;
        TextAreaDocumentListener(
                Request request,
                SqlToDTOWindowForm form
        ) {
            this.request = request;
            this.form = form;
        }

        @Override
        public void documentChanged(@NotNull DocumentEvent event) {
            String preview = ApplyDatasetUtil.getPreviewString(form.getDataSet(), form.getTemplateCode());
            form.updatePreview(preview);
        }
    }
}
