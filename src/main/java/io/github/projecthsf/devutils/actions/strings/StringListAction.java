package io.github.projecthsf.devutils.actions.strings;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.util.text.Strings;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StringListAction extends AnAction {
    StringListAction(@NotNull String text, @Nullable Icon icon) {
        super(text, "", icon);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;

        String str = caret.getSelectedText();
        str = str.replace(" ", "");
        str = str.replace(",", "\", \"");

        List<String> items = new ArrayList<>();
        String[] lines = str.split("\n");
        for (String line: lines) {
            items.add("\"" + line + "\"");
        }
        ActionUtil.replaceString(caret, Strings.join(items, "\n"));
    }
}
