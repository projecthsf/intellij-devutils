package io.github.projecthsf.devutils.actions.strings;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Caret;
import io.github.projecthsf.devutils.actions.namecase.NameCaseAction;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StringsActionGroup extends DefaultActionGroup {
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        event.getPresentation().setVisible(caret.getSelectedText() != null);
    }

    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        List<AnAction> actions = new ArrayList<>();
        actions.add(new GetLengthAction("Get length", null));
        actions.add(new StringSplitAction("Split to multi line", null));
        actions.add(new StringJoinAction("Join to one line", null));
        actions.add(new RemoveHtmlTagsAction("Remove HTML tags", AllIcons.General.Remove));
        actions.add(new StringWrapQuoteAction("Wrap line with quote", null));
        actions.add(new StringToggleQuoteAction("Toggle quotes", null));
        actions.add(new RemoveQuotesAction("Remove quotes", null));
        actions.add(new StringListAction("Make string list", null));
        return actions.toArray(new AnAction[0]);
    }
}