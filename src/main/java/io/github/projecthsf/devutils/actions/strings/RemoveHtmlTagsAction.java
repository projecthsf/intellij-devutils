package io.github.projecthsf.devutils.actions.strings;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.util.text.StringUtil;
import io.github.projecthsf.devutils.actions.CommonAction;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RemoveHtmlTagsAction extends CommonAction {
    public RemoveHtmlTagsAction() {
        super(ActionEnum.REMOVE_HTML_TAGS);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;
        ActionUtil.replaceString(caret, StringUtil.removeHtmlTags(caret.getSelectedText()));
    }
}
