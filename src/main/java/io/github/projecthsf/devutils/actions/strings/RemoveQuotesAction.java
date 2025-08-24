package io.github.projecthsf.devutils.actions.strings;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.util.text.StringUtil;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RemoveQuotesAction extends AnAction {
    public RemoveQuotesAction(@NotNull String title, Icon icon) {
        super(title, "", icon);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;
        String str = caret.getSelectedText();
        str = str.replace("\"", "");
        str = str.replace("'", "");
        ActionUtil.replaceString(caret, str);
    }
}
