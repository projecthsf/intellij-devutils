package io.github.projecthsf.devutils.language.java;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class JavaFileType extends LanguageFileType {

    public static final JavaFileType INSTANCE = new JavaFileType();

    private JavaFileType() {
        super(JavaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "DevUtilJAVA";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Java";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "devutiljava";
    }

    @Override
    public Icon getIcon() {
        return JavaIcons.FILE;
    }

}
