package io.github.projecthsf.devutils.actions.namecase;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Caret;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NameCaseActionGroup extends DefaultActionGroup {
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        event.getPresentation().setVisible(caret.getSelectedText() != null);
    }

    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        List<AnAction> actions = new ArrayList<>();
        for (NameCaseEnum nameCase: NameCaseEnum.values()) {
            actions.add(new NameCaseAction(nameCase));
        }

        //actions.add(new GetStringLengthAction("Get String length", AllIcons.Gutter.Unique));
        //actions.add(new ApplyDataSetAction("Apply Data List", null));
        return actions.toArray(new AnAction[0]);
    }
}