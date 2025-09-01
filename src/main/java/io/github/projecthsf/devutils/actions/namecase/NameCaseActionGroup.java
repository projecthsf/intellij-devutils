package io.github.projecthsf.devutils.actions.namecase;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Caret;
import io.github.projecthsf.devutils.actions.CommonGroupAction;
import io.github.projecthsf.devutils.enums.ActionGroupEnum;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NameCaseActionGroup extends CommonGroupAction {
    NameCaseActionGroup() {
        super(ActionGroupEnum.NAME_CASE);
    }

    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        List<AnAction> actions = new ArrayList<>();
        for (NameCaseEnum nameCase: NameCaseEnum.values()) {
            actions.add(new NameCaseAction(nameCase));
        }
        return actions.toArray(new AnAction[0]);
    }
}
