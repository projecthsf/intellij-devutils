package io.github.projecthsf.devutils.toolWindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import io.github.projecthsf.devutils.toolWindow.forms.ApplyDatasetForm;
import org.jetbrains.annotations.NotNull;

public class DevUtilsToolWindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        Content content = ContentFactory.getInstance().createContent(new ApplyDatasetForm(toolWindow), "Apply Dataset", false);
        toolWindow.getContentManager().addContent(content);
    }


}
