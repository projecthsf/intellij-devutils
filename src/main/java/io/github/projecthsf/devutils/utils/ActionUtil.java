package io.github.projecthsf.devutils.utils;

import com.intellij.json.JsonLanguage;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.util.ui.JBUI;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ActionUtil {
    public static Caret getSelectedCaret(@NotNull AnActionEvent event) {
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        return editor.getCaretModel().getPrimaryCaret();
    }

    public static Editor getEditor(@Nullable String text) {
        return getEditor(text, null);
    }

    public static Editor getEditor(@Nullable String text, @Nullable Boolean readOnly) {
        LightVirtualFile virtualFile = new LightVirtualFile("StringUtils", PlainTextLanguage.INSTANCE, text);
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        document = document != null ? document : EditorFactory.getInstance().createDocument(text);

        Editor editor = EditorFactory.getInstance().createEditor(document);
        if (Boolean.TRUE.equals(readOnly)) {
            editor = EditorFactory.getInstance().createViewer(document);
        }
        return editor;
    }

    public static EditorEx getEditorEx(LanguageEnum language) {
        return getEditorEx(language, "", null);
    }

    public static EditorEx getEditorEx(LanguageEnum language, @Nullable String text) {
        return getEditorEx(language, text, null);
    }

    public static EditorEx getEditorEx(LanguageEnum language, @Nullable String text, @Nullable Boolean readOnly) {
        LightVirtualFile virtualFile = new LightVirtualFile("StringUtils", language.getFileType(), text);
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        document = document != null ? document : EditorFactory.getInstance().createDocument(text);

        Editor editor = EditorFactory.getInstance().createEditor(document);
        if (Boolean.TRUE.equals(readOnly)) {
            editor = EditorFactory.getInstance().createViewer(document);
        }
        EditorEx editorEx = (EditorEx) editor;
        editorEx.setHighlighter(EditorHighlighterFactory.getInstance().createEditorHighlighter(null, language.getFileType()));
        return editorEx;
    }

    public static JPanel getEditorPanel(Editor editor, int width, int height) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(editor.getComponent());
        panel.setPreferredSize(JBUI.size(width, height));
        return panel;
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

    public static void updateText(Editor editor, String replace) {
        WriteCommandAction.runWriteCommandAction(editor.getProject(), () ->
                editor.getDocument().setText(replace)
        );
    }

}
