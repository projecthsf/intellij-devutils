package io.github.projecthsf.devutils.actions.dataset;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.projecthsf.devutils.actions.CommonGroupAction;
import io.github.projecthsf.devutils.enums.ActionGroupEnum;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ApplyDatasetActionGroup extends CommonGroupAction {
    ApplyDatasetActionGroup() {
        super(ActionGroupEnum.APPLY_DATASET);
    }

    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        List<AnAction> actions = new ArrayList<>();
        actions.add(new DatasetSnippetAsDatasetAction());
        actions.add(new DatasetSnippetAsCodeTemplateAction());
        return actions.toArray(new AnAction[0]);
    }
}
