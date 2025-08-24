package io.github.projecthsf.devutils.actions.namecase;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

public class NameCaseAction extends AnAction {
    private NameCaseEnum nameCase;
    public NameCaseAction(@NotNull NameCaseEnum nameCase) {
        super(nameCase.getText(), "", nameCase.getIcon());
        this.nameCase = nameCase;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;
        ActionUtil.replaceString(caret, nameCase);
    }
}
