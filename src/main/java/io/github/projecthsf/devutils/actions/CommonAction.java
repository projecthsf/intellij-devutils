package io.github.projecthsf.devutils.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.settings.StateComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class CommonAction extends AnAction {
    private final StateComponent.State state = Objects.requireNonNull(StateComponent.getInstance().getState());
    protected ActionEnum action;
    public CommonAction(ActionEnum action) {
        super(action.getTitle(), "", action.getIcon());
        this.action = action;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        if (!state.getActionAndGroupMap().containsKey(action.getGroup())) {
            e.getPresentation().setVisible(false);
            return;
        }

        e.getPresentation().setVisible(state.getActionAndGroupMap().get(action.getGroup()).get(action));
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
