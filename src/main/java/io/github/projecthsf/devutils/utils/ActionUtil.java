package io.github.projecthsf.devutils.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.LightVirtualFile;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ActionUtil {
    public static Caret getSelectedCaret(@NotNull AnActionEvent event) {
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        return editor.getCaretModel().getPrimaryCaret();
    }

    public static EditorEx getEditorEx(@Nullable String text, boolean readOnly) {
        LightVirtualFile virtualFile = new LightVirtualFile("StringUtils.ApplyDataList", PlainTextLanguage.INSTANCE, text);
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        document = document != null ? document : EditorFactory.getInstance().createDocument(text);

        EditorEx editor = (EditorEx) EditorFactory.getInstance().createEditor(document);
        if (readOnly) {
            editor = (EditorEx) EditorFactory.getInstance().createViewer(document);
        }

        editor.setEmbeddedIntoDialogWrapper(true);
        editor.getSettings().setFoldingOutlineShown(true);
        editor.getSettings().setCaretRowShown(true);
        editor.getSettings().setVerticalScrollJump(20);
        editor.setCaretEnabled(true);
        editor.setHorizontalScrollbarVisible(true);
        editor.setVerticalScrollbarVisible(true);

        return editor;
    }

    public static void replaceString(Caret caret, NameCaseEnum nameCase) {
        assert caret.getSelectedText() != null;
        replaceString(caret, NameCaseUtil.toNameCase(nameCase, caret.getSelectedText()));

    }

    public static void replaceString(Caret caret, String replace) {
        WriteCommandAction.runWriteCommandAction(caret.getEditor().getProject(), () ->
                caret.getEditor().getDocument().replaceString(caret.getSelectionStart(), caret.getSelectionEnd(), replace)
        );

        caret.removeSelection();
    }


}
