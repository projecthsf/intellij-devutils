package io.github.projecthsf.devutils.toolWindow.forms;

import com.intellij.icons.AllIcons;
import com.intellij.ide.HelpTooltip;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.utils.ActionUtil;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ApplyDatasetForm extends JPanel {
    private final JTextArea dataSet;
    private final JTextArea codeTemplate;
    private final ApplyResult applyResult;


    private final JTextArea preview;
    private final JButton applyBtn;
    private final JButton closeBtn;
    private final String datasetTooltipMsg;
    private final String codeTemplateToolTip;

    public ApplyDatasetForm(ToolWindow toolWindow) {
        dataSet = new JTextArea("", 10, 20);
        codeTemplate = new JTextArea("", 10, 20);

        preview = new JTextArea("", 10, 20);
        preview.setEnabled(false);

        applyResult = new ApplyResult();

        dataSet.getDocument().addDocumentListener(new TextAreaDocumentListener(dataSet, codeTemplate, preview));
        codeTemplate.getDocument().addDocumentListener(new TextAreaDocumentListener(dataSet, codeTemplate, preview));

        applyBtn = new JButton("Apply & Copy");
        applyBtn.addActionListener(new ApplyButtonActionListener(applyResult, preview));

        closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> {toolWindow.hide();});

        datasetTooltipMsg = getTooltipMsg("templates/dataset_tooltip.html");
        codeTemplateToolTip = getTooltipMsg("templates/code_template_tooltip.html");


        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
        add(getCenterPanel(), BorderLayout.PAGE_START);
        add(getControlPanel(), BorderLayout.CENTER);
    }

    public void updateDataSet(String text) {
        dataSet.setText(text);
    }

    public void updateCodeTemplate(Caret caret) {
        codeTemplate.setText(caret.getSelectedText());
        applyResult.setCaret(caret);

    }

    private JPanel getCenterPanel() {

        JButton dataSetTooltip = getToolTipButton("Data set", datasetTooltipMsg);
        JButton codeTemplateTooltip = getToolTipButton("Code template", codeTemplateToolTip);

        return FormBuilder.createFormBuilder()
                .addComponent(dataSetTooltip)
                .addComponent(new JScrollPane(dataSet))
                .addComponent(codeTemplateTooltip)
                .addComponent(new JScrollPane(codeTemplate))
                .addComponent(new JBLabel("Result"))
                .addComponent(new JScrollPane(preview))
                .getPanel();
    }

    private JButton getToolTipButton(String title, String toolTipMsg) {
        JButton button = new JButton(title, AllIcons.General.Information);
        button.setBorderPainted(false);
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        new HelpTooltip().setTitle(title).setDescription(toolTipMsg).installOn(button);
        return button;
    }

    private String getTooltipMsg(String templateName) {
        InputStream contentStream = getClass().getClassLoader().getResourceAsStream(templateName);
        if (contentStream == null) {
            return null;
        }

        try {
            return IOUtils.toString(contentStream, StandardCharsets.UTF_8);
        } catch (Exception ignore) {

        }

        return null;
    }

    private JPanel getControlPanel() {
        JPanel panel = new JPanel();
        panel.add(closeBtn);
        panel.add(applyBtn);

        return panel;
    }


    static class ApplyResult {
        private Caret caret;

        public Caret getCaret() {
            return caret;
        }

        public void setCaret(Caret caret) {
            this.caret = caret;
        }
    }

    static class TextAreaDocumentListener implements DocumentListener {
        private final JTextArea dataList;
        private final JTextArea template;
        private final JTextArea preview;
        TextAreaDocumentListener(
                JTextArea dataList,
                JTextArea template,
                JTextArea preview
        ) {
            this.dataList = dataList;
            this.template = template;
            this.preview = preview;
        }
        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            preview.setText(ApplyDatasetUtil.getPreviewString(dataList, template));
        }

        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            preview.setText(ApplyDatasetUtil.getPreviewString(dataList, template));
        }

        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }
    static class ApplyButtonActionListener implements ActionListener {
        private final ApplyResult applyResult;
        private final JTextArea preview;
        ApplyButtonActionListener(
                ApplyResult applyResult,
                JTextArea preview
        ) {
            this.applyResult = applyResult;
            this.preview = preview;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (applyResult.getCaret() != null) {
                ActionUtil.replaceString(applyResult.getCaret(), preview.getText());
            }

            CopyPasteManager.getInstance().setContents(new StringSelection(preview.getText()));
        }
    }
}


