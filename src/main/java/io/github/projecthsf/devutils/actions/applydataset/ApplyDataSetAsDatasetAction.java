package io.github.projecthsf.devutils.actions.applydataset;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.content.Content;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.toolWindow.forms.ApplyDatasetForm;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ApplyDataSetAsDatasetAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;
        assert event.getProject() != null;

        ToolWindow toolWindow = ToolWindowManager.getInstance(event.getProject()).getToolWindow("DevUtils");
        assert toolWindow != null;
        toolWindow.show();

        ApplyDatasetForm toolWindowPanel = getToolWindowPanel(toolWindow);
        assert toolWindowPanel != null;

        toolWindowPanel.updateDataSet(caret.getSelectedText());


        //DataGridDialog dialog = new DataGridDialog(caret);
        //dialog.show();
    }

    private ApplyDatasetForm getToolWindowPanel(ToolWindow toolWindow) {
        for (Content content: toolWindow.getContentManager().getContents()) {
            if (content.getComponent() instanceof ApplyDatasetForm toolWindowPanel) {
                return toolWindowPanel;
            }
        }

        return null;
    }



    static class DataGridDialog extends DialogWrapper {
        private @NotNull Caret caret;
        protected DataGridDialog(@NotNull Caret caret) {
            super(true); // use current window as parent
            this.caret = caret;
            setTitle("Create Test Order");
            setSize(600, 400);
            init();
        }

        @Override
        protected @Nullable JComponent createCenterPanel() {

            JTextArea dataList = new JBTextArea("This is a JTextArea with a border.", 5, 40);

            JTextArea template = new JTextArea(caret.getSelectedText(), 5, 40);

            JTextArea preview = new JBTextArea("Preview ... ", 50, 40);
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.add(new JBLabel("Data list"));
            leftPanel.add(new JScrollPane(dataList));
            leftPanel.add(new JBLabel("Template"));
            leftPanel.add(new JScrollPane(template));
            leftPanel.setSize(200, 500);
            //leftPanel.add(template);

            panel.add(leftPanel, BorderLayout.WEST);
            panel.add(new JScrollPane(preview), BorderLayout.CENTER);
            panel.setSize(500, 400);











//Lay out the panel.

            return FormBuilder.createFormBuilder()
                    //.addTooltip("Data grid")
                    //.addComponent(new JBTextArea(5, 10), BorderLayout.CENTER)
                    //.addComponent(pane)
                    //.addComponent(new JBLabel("Data list"))
                    //.addComponent(dataList)
                    //.addComponent(template)
                    //.addComponent(preview)
                    //.addComponent(panel)
                    .addLabeledComponent("Data list", new JScrollPane(dataList))
                    .addLabeledComponent("Template", new JScrollPane(template))
                    .addLabeledComponent("Preview", new JScrollPane(preview))
                    //.addLabeledComponent("Data list", new JBTextArea("123423"))
                    .getPanel();
        }
    }
}
