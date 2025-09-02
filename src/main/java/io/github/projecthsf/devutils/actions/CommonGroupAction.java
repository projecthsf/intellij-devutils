package io.github.projecthsf.devutils.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Caret;
import io.github.projecthsf.devutils.actions.applydataset.ApplyDataSetAsCodeTemplateAction;
import io.github.projecthsf.devutils.actions.applydataset.ApplyDataSetAsDatasetAction;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.enums.ActionGroupEnum;
import io.github.projecthsf.devutils.settings.StateComponent;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class CommonGroupAction extends DefaultActionGroup {
    private final StateComponent.State state = Objects.requireNonNull(StateComponent.getInstance().getState());
    private final ActionGroupEnum group;
    public CommonGroupAction(ActionGroupEnum group) {
        this.group = group;
    }
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        event.getPresentation().setVisible(true);
        if (caret.getSelectedText() == null) {
            event.getPresentation().setVisible(false);
            return;
        }

        if (!isAvailableAction()) {
            event.getPresentation().setVisible(false);
        }

    }

    private boolean isAvailableAction() {
        if (!state.getActionAndGroupMap().containsKey(group)) {
            return false;
        }

        for (Boolean checked: state.getActionAndGroupMap().get(group).values()) {
            if (checked) {
                return true;
            }
        }

        return false;
    }
}
