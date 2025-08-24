package io.github.projecthsf.devutils.toolWindow.contents;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.wm.ToolWindow;
import io.github.projecthsf.devutils.forms.SqlToDTOWindowForm;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SqlToDTOWindowPanel extends JPanel {
    private @NotNull ToolWindow toolWindow;
    private SqlToDTOWindowForm form = new SqlToDTOWindowForm(new SqlToDTOWindowForm.Request(toolWindow));
    public SqlToDTOWindowPanel(@NotNull ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(getControlPanel(), BorderLayout.SOUTH);
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

    public static class ApplyButtonActionListener implements ActionListener {
        private final SqlToDTOWindowForm form;
        ApplyButtonActionListener(SqlToDTOWindowForm form) {
            this.form = form;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CopyPasteManager.getInstance().setContents(new StringSelection(form.getPreview()));
        }
    }
}


