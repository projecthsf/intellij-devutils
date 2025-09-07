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
import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.utils.DatasetUtil;
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

public abstract class CommonMasterDetail<F extends JComponent> extends MasterDetailsComponent {
    StateComponent.State setting = Objects.requireNonNull(StateComponent.getInstance().getState());
    F form;
    String itemName;
    Boolean hasModified = false;

    public CommonMasterDetail() {
        this.form = createForm();
        getFormHandler();
        itemName = DatasetUtil.DEFAULT_TEMPLATE_NAME;
        loadExistedTreeNodes();
    }

    protected FormHandler getFormHandler() {
        return null;
    }
    protected abstract Set<String> getItemNames();
    protected abstract void updateForm(String itemName);
    protected abstract boolean isFormModified(String itemName);
    protected abstract void applyChange(String itemName);
    protected abstract void deleteItem(String itemName);

    abstract F createForm();
    private void loadExistedTreeNodes() {
        initTree();
        reset();
        myTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getNewLeadSelectionPath();
                if (path == null) {
                    return;
                }

                MasterDetailsComponent.MyNode node = (MasterDetailsComponent.MyNode)path.getLastPathComponent();
                itemName = node.getConfigurable().getDisplayName();
                updateForm(itemName);
            }
        });

    }
    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "";
    }

    @Override
    public void reset() {
        clearChildren();
        hasModified = false;
        for (String itemName: getItemNames()) {
            addNewItem(itemName);
        }
    }

    @Override
    protected @Nullable List<AnAction> createActions(boolean fromPopup) {
        ArrayList<AnAction> result = new ArrayList<>();
        result.add(new AddDumAwareAction(this));
        result.add(new RemoveDumbAwareAction(this));
        return result;
    }

    void addNewItem(String name) {
        MasterDetailsComponent.MyNode node = new MasterDetailsComponent.MyNode(new ItemNamedConfigurable(this, name));
        myRoot.add(node);
        ((DefaultTreeModel)myTree.getModel()).reload(this.myRoot);
        selectNodeInTree(node);
    }

    static class AddDumAwareAction extends DumbAwareAction {
        private CommonMasterDetail configurable;
        public AddDumAwareAction(CommonMasterDetail configurable) {
            super(AllIcons.General.Add);
            this.configurable = configurable;
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            String templateName = Messages.showInputDialog("Template name", "New", null);
            if (templateName == null || templateName.isEmpty()) {
                return;
            }

            if (configurable.getItemNames().contains(templateName)) {
                Messages.showErrorDialog(String.format("Template name: %s is existed!", templateName), "Existed");
                return;
            }

            configurable.hasModified = true;
            configurable.addNewItem(templateName);
        }
    }

    static class RemoveDumbAwareAction extends DumbAwareAction {
        private CommonMasterDetail configurable;
        public RemoveDumbAwareAction(CommonMasterDetail configurable) {
            super(AllIcons.General.Remove);
            this.configurable = configurable;
        }


        @Override
        public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
            if (configurable.myTree == null || configurable.myTree.getSelectionPaths() == null || configurable.myTree.getSelectionPaths().length == 0) {
                Messages.showErrorDialog("No item has been chosen", "Error");
                return;
            }


            int answer = Messages.showYesNoDialog("Are you sure you want to delete? This action cannot be reverted", "Confirm", AllIcons.General.QuestionDialog);

            if (answer == 0) {
                // yes
                configurable.deleteItem(configurable.itemName);
                configurable.removePaths(configurable.myTree.getSelectionPaths());
            }
        }

        @Override
        public @NotNull ActionUpdateThread getActionUpdateThread() {
            return ActionUpdateThread.BGT;
        }

        @Override
        public void update(@NotNull AnActionEvent e) {
            super.update(e);
            if (configurable.myTree == null || configurable.myTree.getSelectionPaths() == null || configurable.myTree.getSelectionPaths().length == 0) {
                e.getPresentation().setEnabled(false);
                return;
            }

            for (TreePath path: configurable.myTree.getSelectionPaths()) {
                MasterDetailsComponent.MyNode node = (MasterDetailsComponent.MyNode)path.getLastPathComponent();
                if (DatasetUtil.DEFAULT_TEMPLATE_NAME.equals(node.getDisplayName())) {
                    e.getPresentation().setEnabled(false);
                    return;
                }
            }
        }
    }

    static class ItemNamedConfigurable extends NamedConfigurable<String> {
        private final CommonMasterDetail configurable;
        private final @NotNull String displayName;
        ItemNamedConfigurable(@NotNull CommonMasterDetail configurable, @NotNull String displayName) {
            this.configurable = configurable;
            this.displayName = displayName;
        }
        @Override
        public void setDisplayName(@NlsSafe String s) {

        }

        @Override
        public String getEditableObject() {
            return "";
        }

        @Override
        public @NlsContexts.DetailedDescription String getBannerSlogan() {
            return "";
        }

        @Override
        public JComponent createOptionsPanel() {
            configurable.updateForm(displayName);
            return configurable.form;
        }

        @Override
        public @NlsContexts.ConfigurableName @NotNull String getDisplayName() {
            return displayName;
        }


        @Override
        public boolean isModified() {
            if (configurable.hasModified) {
                return true;
            }
            return configurable.isFormModified(configurable.itemName);
        }

        @Override
        public void apply() throws ConfigurationException {
            configurable.applyChange(configurable.itemName);
            configurable.hasModified = false;
        }
    }
}
