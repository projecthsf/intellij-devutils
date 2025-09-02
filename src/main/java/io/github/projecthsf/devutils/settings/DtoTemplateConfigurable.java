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
import io.github.projecthsf.devutils.forms.DtoTemplateSettingForm;
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

public final class DtoTemplateConfigurable extends MasterDetailsComponent {
    StateComponent.State state = Objects.requireNonNull(StateComponent.getInstance().getState());
    DtoTemplateSettingForm form = new DtoTemplateSettingForm();
    public DtoTemplateConfigurable() {
        // must after form initiated
        loadExistedTreeNodes();
    }

    @Override
    public void reset() {
        myRoot.removeAllChildren();
        for (String key: state.getDtoTemplateMap().keySet()) {
            addNewItem(key);
        }
    }

    Tree getMyTree() {
        return myTree;
    }

    private void loadExistedTreeNodes() {
        myTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getNewLeadSelectionPath();
                if (path == null) {
                    return;
                }

                MasterDetailsComponent.MyNode node = (MasterDetailsComponent.MyNode)path.getLastPathComponent();
                String name = node.getConfigurable().getDisplayName();
                String content = "";
                if (state.getDtoTemplateMap().containsKey(name)) {
                    content = state.getDtoTemplateMap().get(name);
                }

                form.updateForm(name, content);
            }
        });
        initTree();
        for (String key: state.getDtoTemplateMap().keySet()) {
            addNewItem(key);
        }
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "";
    }

    @Override
    protected @Nullable List<AnAction> createActions(boolean fromPopup) {
        ArrayList<AnAction> result = new ArrayList<>();
        result.add(new AddDumAwareAction(this));
        result.add(new RemoveDumbAwareAction(this));
        //result.add(new MasterDetailsComponent.MyDeleteAction());
        return result;
    }

     void addNewItem(String name) {
        MasterDetailsComponent.MyNode nodeToAdd = new MasterDetailsComponent.MyNode(new ItemNamedConfigurable(this, name));
        myRoot.add(nodeToAdd);
        ((DefaultTreeModel)myTree.getModel()).reload(this.myRoot);
        selectNodeInTree(nodeToAdd);
    }

    static class AddDumAwareAction extends DumbAwareAction {
        private DtoTemplateConfigurable configurable;
        public AddDumAwareAction(DtoTemplateConfigurable configurable) {
            super(AllIcons.General.Add);
            this.configurable = configurable;
        }

        @Override
        public @NotNull ActionUpdateThread getActionUpdateThread() {
            return ActionUpdateThread.EDT;
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
            String templateName = Messages.showInputDialog("Template name", "New", null);
            if (templateName == null || templateName.isEmpty()) {
                return;
            }

            //StateComponent.State state = Objects.requireNonNull(StateComponent.getInstance().getState());
            if (configurable.state.getDtoTemplateMap().containsKey(templateName)) {
                Messages.showErrorDialog(String.format("Template name: %s is existed!", templateName), "Existed");
                return;
            }

            configurable.addNewItem(templateName);
        }
    }

    static class RemoveDumbAwareAction extends DumbAwareAction {
        private DtoTemplateConfigurable configurable;
        public RemoveDumbAwareAction(DtoTemplateConfigurable configurable) {
            super(AllIcons.General.Remove);
            this.configurable = configurable;
        }

        @Override
        public @NotNull ActionUpdateThread getActionUpdateThread() {
            return ActionUpdateThread.EDT;
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
            if (configurable.myTree == null || configurable.myTree.getSelectionPaths() == null || configurable.myTree.getSelectionPaths().length == 0) {
                Messages.showErrorDialog("No item has been chosen", "Error");
                return;
            }

            int answer = Messages.showYesNoDialog("Are you sure you want to delete?", "Confirm", AllIcons.General.QuestionDialog);

            if (answer == 0) {
                /*
                for (TreePath path: configurable.myTree.getSelectionPaths()) {
                    MasterDetailsComponent.MyNode node = (MasterDetailsComponent.MyNode)path.getLastPathComponent();
                    configurable.state.getDtoTemplateMap().remove(node.getDisplayName());
                }*/
                // yes
                configurable.removePaths(configurable.getMyTree().getSelectionPaths());
            }
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
                if (ApplyDatasetUtil.DEFAULT_TEMPLATE_NAME.equals(node.getDisplayName())) {
                    e.getPresentation().setEnabled(false);
                    return;
                }
            }
        }
    }

    static class ItemNamedConfigurable extends NamedConfigurable<String> {
        private final DtoTemplateConfigurable configurable;
        private final @NotNull String displayName;
        ItemNamedConfigurable(@NotNull DtoTemplateConfigurable configurable, @NotNull String displayName) {
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
            String content = "";
            if (configurable.state.getDtoTemplateMap().containsKey(displayName)) {
                content = configurable.state.getDtoTemplateMap().get(displayName);
            }
            configurable.form.updateForm(displayName, content);
            return configurable.form;
        }

        @Override
        public @NlsContexts.ConfigurableName @NotNull String getDisplayName() {
            return displayName;
        }


        @Override
        public boolean isModified() {
            String previewContent = this.getPreviewString(configurable.state, configurable.form.getTemplateCode());
            configurable.form.updatePreview(previewContent);
            return !configurable.state.getDtoTemplateMap().containsKey(configurable.form.getTemplateName()) ||
                    !configurable.state.getDtoTemplateMap().get(configurable.form.getTemplateName()).equals(configurable.form.getTemplateCode());
        }

        @Override
        public void apply() throws ConfigurationException {
            if (configurable.form.getTemplateName().isEmpty()) {
                Messages.showErrorDialog("Template name is mandatory!", "Error");
                return;
            }

            if (configurable.form.getTemplateCode().isEmpty()) {
                Messages.showErrorDialog("Template code is mandatory!", "Error");
                return;
            }

            configurable.state.getDtoTemplateMap().put(configurable.form.getTemplateName(), configurable.form.getTemplateCode());
        }

        private String getPreviewString(StateComponent.State settings, String templateCode) {
            List<VelocityService.PropertyDTO> columns = new ArrayList<>();
            columns.add(new VelocityService.PropertyDTO(
                    "id",
                    "INT"
            ));
            columns.add(new VelocityService.PropertyDTO(
                    "user_name",
                    "VARCHAR"
            ));

            VelocityService.ClassDTO classDTO = new VelocityService.ClassDTO("your_class_name", columns);
            VelocityService service = VelocityService.getInstance();
            String content = service.merge(classDTO, templateCode);
            return content;
        }
    }


}
