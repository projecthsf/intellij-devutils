package io.github.projecthsf.devutils.settings;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.MasterDetailsComponent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NamedConfigurable;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.ui.treeStructure.Tree;
import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.forms.settings.DtoTemplateSettingForm;
import io.github.projecthsf.devutils.forms.settings.DtoTemplateSettingFormHandler;
import io.github.projecthsf.devutils.service.VelocityService;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class DtoTemplateConfigurable extends CommonMasterDetail<DtoTemplateSettingForm> {
    @Override
    protected Set<String> getItemNames() {
        return setting.getDtoTemplateMap().keySet();
    }

    @Override
    protected void updateForm(String itemName) {
        if (setting.getDtoTemplateMap().containsKey(itemName)) {
            form.updateForm(itemName, setting.getDtoTemplateMap().get(itemName));
            return;
        }

        form.updateForm(itemName, "");
    }

    @Override
    protected boolean isFormModified(String itemName) {
        return !setting.getDtoTemplateMap().containsKey(itemName) ||
                !setting.getDtoTemplateMap().get(itemName).equals(form.getTemplateCode());
    }

    @Override
    protected void applyChange(String itemName) {
        if (form.getTemplateCode().isEmpty()) {
            Messages.showErrorDialog("Template code is mandatory!", "Error");
            return;
        }

        setting.getDtoTemplateMap().put(itemName, form.getTemplateCode());
    }

    @Override
    DtoTemplateSettingForm createForm() {
        return new DtoTemplateSettingForm();
    }

    @Override
    protected FormHandler getFormHandler() {
        return new DtoTemplateSettingFormHandler(form);
    }
}
