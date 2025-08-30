package io.github.projecthsf.devutils.actions.converters.csv;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.util.text.Strings;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import io.github.projecthsf.devutils.toolWindow.controller.ApplyDatasetWindowController;
import io.github.projecthsf.devutils.utils.ActionUtil;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TextToCsvAction extends AnAction {
    public TextToCsvAction(@NotNull String text, @Nullable Icon icon) {
        super(text, "", icon);
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Caret caret = ActionUtil.getSelectedCaret(event);
        assert caret.getSelectedText() != null;
        assert event.getProject() != null;

        ToolWindow toolWindow = ToolWindowManager.getInstance(event.getProject()).getToolWindow("DevUtils");
        assert toolWindow != null;
        toolWindow.show();

        ApplyDatasetWindowController toolWindowPanel = ApplyDatasetUtil.getToolWindowPanel(toolWindow);
        assert toolWindowPanel != null;

        toolWindowPanel.updateDataSet(getCsvText(caret));
    }

    private String getCsvText(Caret caret) {
        String selectedText = caret.getSelectedText();
        selectedText = selectedText.replaceAll("\s+|\t", " ");
        List<String> result = new ArrayList<>();
        for (String text: selectedText.split("\n")) {
            text = text.trim();
            text = Strings.trimEnd(text, ";");
            text = Strings.trimStart(text, ";");
            text.replace(" ", ",");
            result.add(text);
        }

        return Strings.join(result, "\n").replace(" ", ",");
    }
}
