package io.github.projecthsf.devutils.actions.strings;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.ui.Messages;
import io.github.projecthsf.devutils.actions.CommonAction;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class StringJoinAction extends CommonAction {
    StringJoinAction() {
        super(ActionEnum.STRING_JOIN);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;

        String delimiter = Messages.showInputDialog("Delimiter: ", "Join To One Line", null);
        if (delimiter == null) {
            delimiter = "";
        }
        String[] lines = caret.getSelectedText().split("\n");
        ActionUtil.replaceString(caret, String.join(delimiter, lines));
    }
}
