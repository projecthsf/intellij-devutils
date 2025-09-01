package io.github.projecthsf.devutils.actions.applydataset;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Caret;
import io.github.projecthsf.devutils.actions.CommonGroupAction;
import io.github.projecthsf.devutils.actions.strings.*;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.enums.ActionGroupEnum;
import io.github.projecthsf.devutils.settings.StateComponent;
import io.github.projecthsf.devutils.utils.ActionUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApplyDatasetActionGroup extends CommonGroupAction {
    ApplyDatasetActionGroup() {
        super(ActionGroupEnum.APPLY_DATASET);
    }

    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        List<AnAction> actions = new ArrayList<>();
        actions.add(new ApplyDataSetAsDatasetAction());
        actions.add(new ApplyDataSetAsCodeTemplateAction());
        return actions.toArray(new AnAction[0]);
    }
}
