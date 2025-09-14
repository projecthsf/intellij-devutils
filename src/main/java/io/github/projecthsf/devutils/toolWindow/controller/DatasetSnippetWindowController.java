package io.github.projecthsf.devutils.toolWindow.controller;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.enums.DatasetTypeEnum;
import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.forms.toolWindows.*;
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
    //DatasetSnippetWindowForm form = new DatasetSnippetWindowForm();
    DatasetSnippetWindowFormHandler handler;

    Map<DatasetTypeEnum, AbstractDatasetWindowForm> forms = new HashMap<>();
    Map<DatasetTypeEnum, FormHandler> handlers = new HashMap<>();

    ComboBox<String> templates = new ComboBox<>();
    ComboBox<DatasetTypeEnum> types = new ComboBox<>(DatasetTypeEnum.values());
    public DatasetSnippetWindowController(@NotNull ToolWindow toolWindow) {
        DatasetSnippetWindowForm snippetForm = new DatasetSnippetWindowForm();

        forms.put(DatasetTypeEnum.SNIPPETS, snippetForm);
        handlers.put(DatasetTypeEnum.SNIPPETS, new DatasetSnippetWindowFormHandler(snippetForm));

        DatasetToDTOWindowForm dtoForm = new DatasetToDTOWindowForm();
        dtoForm.setVisible(false);
        forms.put(DatasetTypeEnum.DTOS, dtoForm);
        handlers.put(DatasetTypeEnum.DTOS, new DatasetToDTOWindowFormHandler(dtoForm));

        this.toolWindow = toolWindow;
        setLayout(new BorderLayout());
        add(getWindowForm(), BorderLayout.PAGE_START);
        add(getControlPanel(), BorderLayout.SOUTH);
    }

    AbstractDatasetWindowForm getForm() {
        DatasetTypeEnum type = (DatasetTypeEnum) types.getSelectedItem();
        return forms.get(type);
    }

    FormHandler getHandler() {
        DatasetTypeEnum type = (DatasetTypeEnum) types.getSelectedItem();
        return handlers.get(type);
    }

    private JPanel getWindowForm() {
        return FormBuilder.createFormBuilder()
                .addComponent(forms.get(DatasetTypeEnum.SNIPPETS))
                .addComponent(forms.get(DatasetTypeEnum.DTOS))
                .getPanel();
    }

    private JPanel getControlPanel() {
        JPanel panel = new JPanel();
        JButton snippetTooltip = DatasetUtil.getToolTipButton("Type", "Check Setting > Dev Utils > Apply Dataset <br /> 1. Snippets <br /> 2. DTOs");

        JButton applyBtn = new JButton("Copy to clipboard");
        applyBtn.addActionListener(new ApplyButtonActionListener(this));

        resetListTemplates(DatasetTypeEnum.SNIPPETS);

        types.addActionListener(new TypeActionListener(this));
        templates.addActionListener(new TemplateActionListener(this));

        JButton refreshBtn = new JButton(AllIcons.Actions.Refresh);
        refreshBtn.setPreferredSize(new Dimension(20, 20));
        refreshBtn.addActionListener(new RefreshButtonActionListener(this));

        panel.add(snippetTooltip);
        panel.add(types);
        panel.add(templates);
        panel.add(refreshBtn);
        panel.add(applyBtn);

        return panel;
    }

    Set<String> getTemplateNames(DatasetTypeEnum type) {
        if (type == DatasetTypeEnum.DTOS) {
            return setting.getDatesetToDtoMap().keySet();
        }

        return setting.getDatasetSnippetMap().keySet();
    }

    private void resetListTemplates(DatasetTypeEnum type) {
        templates.removeAllItems();
        if (type == DatasetTypeEnum.SNIPPETS) {
            templates.addItem(DatasetUtil.EMPTY_TEMPLATE_NAME);
        }

        for (String key : getTemplateNames(type)) {
            templates.addItem(key);
        }
    }

    public void updateCodeTemplate(String text) {
        if (DatasetTypeEnum.DTOS == types.getSelectedItem()) {
            types.setSelectedItem(DatasetTypeEnum.SNIPPETS);
            templates.setSelectedItem(DatasetUtil.EMPTY_TEMPLATE_NAME);
        }

        forms.get(DatasetTypeEnum.SNIPPETS).updateCodeTemplate(text);
    }

    public void updateDataSet(String text) {
        if (DatasetTypeEnum.DTOS == types.getSelectedItem()) {
            types.setSelectedItem(DatasetTypeEnum.SNIPPETS);
            templates.setSelectedItem(DatasetUtil.EMPTY_TEMPLATE_NAME);
        }
        forms.get(DatasetTypeEnum.SNIPPETS).updateDataset(text);
    }

    public static class ApplyButtonActionListener implements ActionListener {
        private final DatasetSnippetWindowController controller;
        ApplyButtonActionListener(DatasetSnippetWindowController controller) {
            this.controller = controller;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CopyPasteManager.getInstance().setContents(new StringSelection(controller.getForm().getPreview()));
        }
    }

    public static class RefreshButtonActionListener implements ActionListener {
        private final DatasetSnippetWindowController controller;
        RefreshButtonActionListener(DatasetSnippetWindowController controller) {
            this.controller = controller;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.resetListTemplates((DatasetTypeEnum) controller.types.getSelectedItem());
        }
    }

    public static class TemplateActionListener implements ActionListener {
        private final DatasetSnippetWindowController controller;
        public TemplateActionListener(DatasetSnippetWindowController controller) {
            this.controller = controller;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller.templates.getSelectedItem() == null) {
                return;
            }

            if (controller.getHandler().isModified()) {
                int result = Messages.showYesNoDialog("All your changes will be reset. Are your sure to change?", "Confirm", AllIcons.Toolwindows.InfoEvents);
                if (result > 0) {
                    // no
                    return;
                }
            }
            String selectedItem = (String) controller.templates.getSelectedItem();
            if (DatasetUtil.EMPTY_TEMPLATE_NAME.equals(selectedItem)) {
                controller.getHandler().resetForm();
                controller.getHandler().setModified(false);
                return;
            }

            DatasetTypeEnum type = (DatasetTypeEnum) controller.types.getSelectedItem();
            if (!controller.getTemplateNames(type).contains(selectedItem)) {
                Messages.showErrorDialog("This item has been deleted from configuration. Pls click refersh button", "Error");
                return;
            }

            //StateComponent.DatasetSnippetState state = controller.setting.getDatasetSnippetMap().get(selectedItem);
            //controller.form.updateForm(state.getCsvSeparator(), state.getDataset(), state.getCodeTemplate());
            controller.getHandler().updateForm(selectedItem);
            controller.getHandler().setModified(false);
        }
    }

    public static class TypeActionListener implements ActionListener {
        private final DatasetSnippetWindowController controller;
        public TypeActionListener(DatasetSnippetWindowController controller) {
            this.controller = controller;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            for (DatasetTypeEnum type: controller.forms.keySet()) {
                controller.forms.get(type).setVisible(false);
            }

            DatasetTypeEnum type = (DatasetTypeEnum) controller.types.getSelectedItem();
            controller.resetListTemplates(type);
            controller.forms.get(type).setVisible(true);
        }
    }

}


