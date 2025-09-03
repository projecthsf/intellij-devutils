package io.github.projecthsf.devutils.actions.converters.dto;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.MasterDetailsComponent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NamedConfigurable;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import io.github.projecthsf.devutils.actions.CommonAction;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.forms.ToDTOForm;
import io.github.projecthsf.devutils.service.VelocityService;
import io.github.projecthsf.devutils.settings.StateComponent;
import io.github.projecthsf.devutils.utils.ActionUtil;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import io.github.projecthsf.devutils.utils.DialogUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.Objects;

public abstract class CommonToDTOAction extends CommonAction {
    ToDTOForm form;
    VelocityService.ClassDTO tableDTO;
    public CommonToDTOAction(ActionEnum action) {
        super(action);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;

        form = new ToDTOForm();
        try {
            tableDTO = getTableDTO(caret.getSelectedText());
        } catch (Exception e) {
            DialogUtil.showErrorTraceMessage("Trace: " + e.getMessage(), "JSON parse error");
            return;
        }

        if (state == null) {
            Messages.showErrorDialog("No templates config found!", "Error");
            return;
        }

        ToDTODialog dialog = new ToDTODialog(this);
        dialog.show();

    }

    protected abstract VelocityService.ClassDTO getTableDTO(String selectedText) throws Exception;

    static class ToDTODialog extends DialogWrapper {
        private CommonToDTOAction action;
        ToDTODialog(CommonToDTOAction action) {
            super(false);
            setTitle(action.action.getTitle());
            this.action = action;
            init();
        }

        @Override
        protected @Nullable JComponent createCenterPanel() {
            return new ToDTOMasterDetailComponent(action).createComponent();
        }

        @Override
        protected Action @NotNull [] createActions() {
            return new Action[]{
                    new CopyAction(this, action),
                    getCancelAction(),
            };
        }
    }

    static class CopyAction extends AbstractAction {
        private CommonToDTOAction action;
        private DialogWrapper dialog;
        CopyAction(DialogWrapper dialog, CommonToDTOAction action) {
            super("Copy to clipboard");
            this.action = action;
            this.dialog = dialog;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            CopyPasteManager.getInstance().setContents(new StringSelection(action.form.getPreview()));
            dialog.doCancelAction(e);
        }
    }

    static class ToDTOMasterDetailComponent extends MasterDetailsComponent {
        private CommonToDTOAction action;
        ToDTOMasterDetailComponent(CommonToDTOAction action) {
            this.action = action;
            initTree();

            myTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    TreePath path = e.getNewLeadSelectionPath();
                    if (path == null) {
                        return;
                    }

                    MyNode node = (MyNode)path.getLastPathComponent();
                    VelocityService service = VelocityService.getInstance();
                    String content = service.merge(action.tableDTO, action.state.getDtoTemplateMap().get(node.getDisplayName()));
                    action.form.updateForm(
                            action.tableDTO.getClassName(),
                            node.getDisplayName(),
                            content
                    );
                }
            });

            for (String key: action.state.getDtoTemplateMap().keySet()) {
                addNewItem(key, ApplyDatasetUtil.DEFAULT_TEMPLATE_NAME.equals(key));
            }
        }
        @Override
        public @NlsContexts.ConfigurableName String getDisplayName() {
            return "";
        }

        public MyNode getRoot() {
            return myRoot;
        }

        private void addNewItem(String name, boolean selected) {
            MyNode nodeToAdd = new MyNode(new ItemNameConfigurable(action, name));
            myRoot.add(nodeToAdd);
            ((DefaultTreeModel)myTree.getModel()).reload(myRoot);
            if (selected) {
                selectNodeInTree(nodeToAdd);
            }
        }

    }

    static class ItemNameConfigurable extends NamedConfigurable<String> {
        private CommonToDTOAction action;
        private String displayName;
        ItemNameConfigurable(
                CommonToDTOAction action,
                String displayName
        ) {
            this.action = action;
            this.displayName = displayName;
        }
        @Override
        public JComponent createOptionsPanel() {
            return action.form;
        }

        @Override
        public @NlsContexts.DetailedDescription String getBannerSlogan() {
            return "";
        }

        @Override
        public String getEditableObject() {
            return "";
        }

        @Override
        public void setDisplayName(@NlsSafe String s) {

        }

        @Override
        public @NlsContexts.ConfigurableName String getDisplayName() {
            return displayName;
        }

        @Override
        public void apply() throws ConfigurationException {

        }

        @Override
        public boolean isModified() {
            return true;
        }
    }
}
