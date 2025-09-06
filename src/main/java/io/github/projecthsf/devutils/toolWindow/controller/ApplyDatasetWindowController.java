package io.github.projecthsf.devutils.toolWindow.controller;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.util.text.Strings;
import com.intellij.openapi.wm.ToolWindow;
import io.github.projecthsf.devutils.forms.toolWindows.ApplyDatasetWindowForm;
import io.github.projecthsf.devutils.forms.toolWindows.ApplyDatasetWindowFormHandler;
import io.github.projecthsf.devutils.service.VelocityService;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ApplyDatasetWindowController extends JPanel {
    @NotNull ToolWindow toolWindow;
    ApplyDatasetWindowForm form = new ApplyDatasetWindowForm();
    public ApplyDatasetWindowController(@NotNull ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(getControlPanel(), BorderLayout.SOUTH);

        new ApplyDatasetWindowFormHandler(form);
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
        form.updateDataset(text);
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

}


