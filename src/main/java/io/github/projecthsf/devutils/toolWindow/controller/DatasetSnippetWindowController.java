package io.github.projecthsf.devutils.toolWindow.controller;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import io.github.projecthsf.devutils.forms.toolWindows.DatasetSnippetWindowForm;
import io.github.projecthsf.devutils.forms.toolWindows.DatasetSnippetWindowFormHandler;
import io.github.projecthsf.devutils.settings.StateComponent;
import io.github.projecthsf.devutils.utils.DatasetUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DatasetSnippetWindowController extends JPanel {
    StateComponent.State setting = Objects.requireNonNull(StateComponent.getInstance().getState());
    @NotNull ToolWindow toolWindow;
    DatasetSnippetWindowForm form = new DatasetSnippetWindowForm();
    DatasetSnippetWindowFormHandler handler;
    public DatasetSnippetWindowController(@NotNull ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(getControlPanel(), BorderLayout.SOUTH);

        handler = new DatasetSnippetWindowFormHandler(form);
    }

    private JPanel getControlPanel() {
        JPanel panel = new JPanel();
        JButton snippetTooltip = DatasetUtil.getToolTipButton("Snippets", "Check Setting > Dev Utils > Apply Dataset > Snippets");

        JButton applyBtn = new JButton("Copy to clipboard");
        applyBtn.addActionListener(new ApplyButtonActionListener(this));

        JComboBox<String> templates = new JComboBox<>();
        resetListTemplates(templates);

        templates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (templates.getSelectedItem() == null) {
                    return;
                }

                if (handler.isModified()) {
                    int result = Messages.showYesNoDialog("All your changes will be reset. Are your sure to change?", "Confirm", AllIcons.Toolwindows.InfoEvents);
                    if (result > 0) {
                        // no
                        return;
                    }
                }
                String selectedItem = (String) templates.getSelectedItem();
                if (DatasetUtil.EMPTY_TEMPLATE_NAME.equals(selectedItem)) {
                    form.reset();
                    handler.setModified(false);
                    return;
                }

                if (!setting.getApplyDatasetMap().containsKey(selectedItem)) {
                    Messages.showErrorDialog("This item has been deleted from configuration. Pls click refersh button", "Error");
                    return;
                }

                StateComponent.ApplyDatasetState state = setting.getApplyDatasetMap().get(selectedItem);
                form.updateForm(state.getCsvSeparator(), state.getDataset(), state.getCodeTemplate());

                handler.setModified(false);
            }
        });

        JButton refreshBtn = new JButton(AllIcons.Actions.Refresh);
        refreshBtn.setPreferredSize(new Dimension(20, 20));
        refreshBtn.addActionListener(e -> {
            resetListTemplates(templates);
        });

        panel.add(snippetTooltip);
        panel.add(templates);
        panel.add(refreshBtn);
        panel.add(applyBtn);

        return panel;
    }

    private void resetListTemplates(JComboBox<String> templates) {
        templates.removeAllItems();
        templates.addItem(DatasetUtil.EMPTY_TEMPLATE_NAME);
        for (String key: setting.getApplyDatasetMap().keySet()) {
            templates.addItem(key);
        }
    }

    public void updateCodeTemplate(Caret caret) {
        form.updateCodeTemplate(caret);
    }

    public void updateDataSet(String text) {
        form.updateDataset(text);
    }

    public static class ApplyButtonActionListener implements ActionListener {
        private final DatasetSnippetWindowController controller;
        ApplyButtonActionListener(DatasetSnippetWindowController controller) {
            this.controller = controller;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CopyPasteManager.getInstance().setContents(new StringSelection(controller.form.getPreview()));
        }
    }

}


