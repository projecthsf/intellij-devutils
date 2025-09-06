package io.github.projecthsf.devutils.forms.actions;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.editor.Editor;
import com.intellij.ui.components.*;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.utils.ActionUtil;

import javax.swing.*;
import java.awt.*;


public class ToDTOForm extends JPanel {
    private final JBLabel sourceLabel = new JBLabel("");
    private final JBLabel templateLabel = new JBLabel("");
    private final Editor preview = ActionUtil.getEditor("");
    public ToDTOForm() {
        ApplicationInfo applicationInfo = ApplicationInfo.getInstance();
        add(getPreviewPanel(), BorderLayout.CENTER);
    }

    private JPanel getPreviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(preview.getComponent(), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(600, 500));
        return FormBuilder.createFormBuilder()
                .addLabeledComponent("Source:", sourceLabel)
                .addLabeledComponent("Template: ", templateLabel)
                .addComponent(new JLabel("Preview:"))
                .addComponent(panel)
                .addComponent(new JBLabel("Access Settings > Dev Utils > DTO Templates"))
                .getPanel();
    }

    public void updateForm(String tableName, String template, String content) {
        sourceLabel.setText(tableName);
        templateLabel.setText(template);
        ActionUtil.updateText(preview, content);
    }

    public String getPreview() {
        return preview.getDocument().getText();
    }
}
