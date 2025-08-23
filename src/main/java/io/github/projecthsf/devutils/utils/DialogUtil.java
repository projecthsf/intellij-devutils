package io.github.projecthsf.devutils.utils;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DialogUtil {
    public static void showErrorTraceMessage(String message, String title) {
        new ErrorTraceMessageDiaglog(message, title).show();
    }

    static class ErrorTraceMessageDiaglog extends DialogWrapper {
        private String message;
        private String title;
        ErrorTraceMessageDiaglog(String message, String title) {
            super(false);
            setTitle(title);
            this.message = message;
            init();

        }
        @Override
        protected @Nullable JComponent createCenterPanel() {
            return FormBuilder.createFormBuilder()
                    .addComponent(new JBTextArea(message, 20, 300))
                    .getPanel();
        }
    }
}
