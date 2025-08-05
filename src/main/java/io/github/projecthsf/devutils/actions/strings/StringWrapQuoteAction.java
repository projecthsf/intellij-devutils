package io.github.projecthsf.devutils.actions.strings;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.ui.Messages;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class StringWrapQuoteAction extends AnAction {
    StringWrapQuoteAction(@NotNull String text, @Nullable Icon icon) {
        super(text, "", icon);
    }
    private static Map<String, String> quoteMapping = new HashMap<>();
    static {
        quoteMapping.put("DoubleQuote", "\"");
        quoteMapping.put("SingleQuote", "'");
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;

        String delimiter = Messages.showEditableChooseDialog("Quote:", "Join To One Line", null, quoteMapping.keySet().toArray(new String[0]), "DoubleQuote", null);
        if (delimiter == null) {
            delimiter = "DoubleQuote";
        }
        String quote = quoteMapping.get(delimiter);
        String[] lines = caret.getSelectedText().split("\n");
        ActionUtil.replaceString(caret, quote + String.join(quote + "\n" + quote, lines) + quote);
    }
}
