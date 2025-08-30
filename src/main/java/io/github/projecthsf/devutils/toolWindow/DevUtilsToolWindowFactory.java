package io.github.projecthsf.devutils.toolWindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import io.github.projecthsf.devutils.toolWindow.controller.ApplyDatasetWindowController;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DevUtilsToolWindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        toolWindow.getContentManager().addContent(createContent("Apply Dataset", new ApplyDatasetWindowController(toolWindow)));
        //toolWindow.getContentManager().addContent(createContent("Sql to DTO", new SqlToDTOWindowPanel(toolWindow)));
    }


    private Content createContent(String name, JPanel panel) {
        return ContentFactory.getInstance().createContent(panel, name, false);
    }
}
