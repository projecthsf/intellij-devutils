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

public class StringSplitAction extends CommonAction {
    StringSplitAction() {
        super(ActionEnum.STRING_SPLIT);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;

        String separator = Messages.showInputDialog("Separator: ", "Split To Multi Line", null);
        if (separator == null) {
            return;
        }

        String[] lines = caret.getSelectedText().split(separator);
        ActionUtil.replaceString(caret, String.join("\n", lines));
    }
}
