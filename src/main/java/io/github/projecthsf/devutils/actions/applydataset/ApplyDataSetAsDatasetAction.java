package io.github.projecthsf.devutils.actions.applydataset;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import io.github.projecthsf.devutils.toolWindow.contents.ApplyDatasetWindowPanel;
import io.github.projecthsf.devutils.utils.ActionUtil;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ApplyDataSetAsDatasetAction extends AnAction {
    public ApplyDataSetAsDatasetAction(@NotNull String text, @Nullable Icon icon) {
        super(text, "", icon);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;
        assert event.getProject() != null;

        ToolWindow toolWindow = ToolWindowManager.getInstance(event.getProject()).getToolWindow("DevUtils");
        assert toolWindow != null;
        toolWindow.show();

        ApplyDatasetWindowPanel toolWindowPanel = ApplyDatasetUtil.getToolWindowPanel(toolWindow);
        assert toolWindowPanel != null;

        toolWindowPanel.updateDataSet(caret.getSelectedText());
    }
}
