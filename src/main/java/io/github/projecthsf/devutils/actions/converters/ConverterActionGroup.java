package io.github.projecthsf.devutils.actions.converters;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Caret;
import io.github.projecthsf.devutils.actions.converters.dto.CommonToDTOAction;
import io.github.projecthsf.devutils.actions.converters.dto.JsonToDTOAction;
import io.github.projecthsf.devutils.actions.converters.dto.SqlToDTOAction;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConverterActionGroup extends DefaultActionGroup {
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
        //actions.add(new TextToCsvAction("Text to CSV", AllIcons.FileTypes.Text));
        actions.add(new JsonToDTOAction("JSON to DTO", AllIcons.FileTypes.Json));
        actions.add(new SqlToDTOAction("SQL to DTO", AllIcons.Nodes.DataSchema));
        return actions.toArray(new AnAction[0]);
    }
}
