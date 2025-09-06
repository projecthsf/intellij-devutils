package io.github.projecthsf.devutils.forms.settings;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.ui.components.ActionLink;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.utils.ActionUtil;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DtoTemplateSettingForm extends JPanel {
    private final JBTextField templateName = new JBTextField();
    private final Editor templateCode = ActionUtil.getEditor("");
    private final Editor preview = ActionUtil.getEditor("", true);
    public DtoTemplateSettingForm() {
        templateName.setEnabled(false);
        add(getEditorForm());
    }

    private JPanel getEditorForm() {
        JPanel templateCodePanel = new JPanel(new BorderLayout());
        templateCodePanel.add(templateCode.getComponent(), BorderLayout.CENTER);
        templateCodePanel.setPreferredSize(new Dimension(500, 300));

        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.add(preview.getComponent(), BorderLayout.CENTER);
        previewPanel.setPreferredSize(new Dimension(500, 250)); // Example preferred size

        String tooltip = ApplyDatasetUtil.getTemplate("templates/language-dto-template-tooltip.html");
        JPanel codeTemplatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        codeTemplatePanel.add(ApplyDatasetUtil.getToolTipButton("Code template", tooltip));
        ActionLink link = new ActionLink(
                "View document",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BrowserUtil.browse("https://github.com/projecthsf/intellij-devutils/blob/main/docs/settings/dto-templates.md");
                    }
                }
        );
        link.setExternalLinkIcon();
        codeTemplatePanel.add(link);
        return FormBuilder.createFormBuilder()
                .addLabeledComponent("Name", templateName)
                .addComponent(codeTemplatePanel)
                .addComponent(templateCodePanel)
                .addComponent(new JLabel("Preview"))
                .addComponent(previewPanel)
                .getPanel();
    }

    public void updatePreview(String text) {
        ActionUtil.updateText(preview, text);
    }
    public void updateForm(String displayName, String content) {
        templateName.setText(displayName);
        ActionUtil.updateText(templateCode, content);
    }

    public String getTemplateName() {
        return templateName.getText();
    }

    public String getTemplateCode() {
        return templateCode.getDocument().getText();
    }

    public void addListeners(DocumentListener codeTemplateListener) {
        templateCode.getDocument().addDocumentListener(codeTemplateListener);
    }

}
