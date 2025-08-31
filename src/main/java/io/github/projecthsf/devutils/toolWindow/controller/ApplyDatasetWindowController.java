package io.github.projecthsf.devutils.toolWindow.controller;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.util.text.Strings;
import com.intellij.openapi.wm.ToolWindow;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import io.github.projecthsf.devutils.forms.ApplyDatasetWindowForm;
import io.github.projecthsf.devutils.service.VelocityService;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import io.github.projecthsf.devutils.utils.NameCaseUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.*;
import java.util.List;

public class ApplyDatasetWindowController extends JPanel {
    @NotNull ToolWindow toolWindow;
    ApplyDatasetWindowForm form = new ApplyDatasetWindowForm(new ApplyDatasetWindowForm.Request(toolWindow));
    public ApplyDatasetWindowController(@NotNull ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(getControlPanel(), BorderLayout.SOUTH);

        form.addListeners(
                new TextAreaDocumentListener(this, true),
                new TextAreaDocumentListener(this, false),
                new ComboBoxListener(this)
        );
    }

    private JPanel getControlPanel() {
        JPanel panel = new JPanel();
        JButton applyBtn = new JButton("Copy to clipboard");
        applyBtn.addActionListener(new ApplyButtonActionListener(form));

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> {toolWindow.hide();});

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {form.reset();});
        panel.add(closeBtn);
        panel.add(resetButton);
        panel.add(applyBtn);

        return panel;
    }

    public void updateCodeTemplate(Caret caret) {
        form.updateCodeTemplate(caret);
    }

    public void updateDataSet(String text) {
        form.updateDataSet(text);
    }

    String getPreviewString(boolean updateDataset) {
        if (form.getDataSet().isEmpty() || form.getTemplateCode().isEmpty()) {
            return "";
        }
        VelocityService service = VelocityService.getInstance();
        List<ApplyDatasetUtil.DatatsetDTO> dtos = ApplyDatasetUtil.getDatasetRecords(form.getSeparator(), form.getDataSet(), updateDataset);


        List<String> items = new ArrayList<>();
        for (ApplyDatasetUtil.DatatsetDTO dto: dtos) {
            items.add(getPreviewString(service, dto));
        }

        return Strings.join(items, "\n");
    }

    private String getPreviewString(VelocityService service, ApplyDatasetUtil.DatatsetDTO dto) {
        String codeTemplate = form.getTemplateCode();
        for (String key: dto.getSimplify().keySet()) {
            codeTemplate = codeTemplate.replace(key, dto.getSimplify().get(key));
        }

        try {
            codeTemplate = service.merge(dto.getVelocity(), codeTemplate);
        } catch (Exception e) {

        }
        return codeTemplate;
    }

    public static class ApplyButtonActionListener implements ActionListener {
        private final ApplyDatasetWindowForm form;
        ApplyButtonActionListener(ApplyDatasetWindowForm form) {
            this.form = form;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CopyPasteManager.getInstance().setContents(new StringSelection(form.getPreview()));
        }
    }

    static class ComboBoxListener implements ActionListener {
        private ApplyDatasetWindowController controller;
        ComboBoxListener(ApplyDatasetWindowController controller) {
            this.controller = controller;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String preview = controller.getPreviewString(true);
            controller.form.updatePreview(preview);
        }
    }

    static class TextAreaDocumentListener implements DocumentListener {
        private ApplyDatasetWindowController controller;
        private final boolean updateDataset;
        TextAreaDocumentListener(ApplyDatasetWindowController controller, boolean updateDataset) {
            this.controller = controller;
            this.updateDataset = updateDataset;
        }

        @Override
        public void documentChanged(@NotNull DocumentEvent event) {
            String preview = controller.getPreviewString(updateDataset);
            controller.form.updatePreview(preview);
        }
    }
}


