package io.github.projecthsf.devutils.actions.dataset;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import io.github.projecthsf.devutils.actions.CommonAction;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.toolWindow.controller.DatasetSnippetWindowController;
import io.github.projecthsf.devutils.utils.ActionUtil;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import org.jetbrains.annotations.NotNull;

public class DatasetSnippetAsDatasetAction extends CommonAction {
    public DatasetSnippetAsDatasetAction() {
        super(ActionEnum.APPLY_DATA_SET_AS_DATASET);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;
        assert event.getProject() != null;

        ToolWindow toolWindow = ToolWindowManager.getInstance(event.getProject()).getToolWindow("DevUtils");
        assert toolWindow != null;
        toolWindow.show();

        DatasetSnippetWindowController toolWindowPanel = ApplyDatasetUtil.getToolWindowPanel(toolWindow);
        assert toolWindowPanel != null;

        toolWindowPanel.updateDataSet(caret.getSelectedText());
    }
}

