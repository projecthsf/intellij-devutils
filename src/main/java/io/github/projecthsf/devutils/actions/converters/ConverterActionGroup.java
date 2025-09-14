package io.github.projecthsf.devutils.actions.converters;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Caret;
import com.intellij.ui.AnActionButton;
import io.github.projecthsf.devutils.actions.CommonAction;
import io.github.projecthsf.devutils.actions.CommonGroupAction;
import io.github.projecthsf.devutils.actions.converters.dto.CommonToDTOAction;
import io.github.projecthsf.devutils.actions.converters.dto.JsonToDTOAction;
import io.github.projecthsf.devutils.actions.converters.dto.SqlToDTOAction;
import io.github.projecthsf.devutils.enums.ActionGroupEnum;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConverterActionGroup extends CommonGroupAction {
    ConverterActionGroup() {
        super(ActionGroupEnum.CONVERTERS);
    }
    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        List<AnAction> actions = new ArrayList<>();
        actions.add(new JsonToDTOAction());
        actions.add(new SqlToDTOAction());
        return actions.toArray(new AnAction[0]);
    }
}
