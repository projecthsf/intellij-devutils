package io.github.projecthsf.devutils.enums;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.PlainTextFileType;

import javax.swing.*;

public enum LanguageEnum {
    JAVA(AllIcons.FileTypes.Java,PlainTextFileType.INSTANCE),
    PHP(AllIcons.Language.Php, PlainTextFileType.INSTANCE),
    PYTHON(AllIcons.Language.Python,PlainTextFileType.INSTANCE),

    GO(AllIcons.Language.GO,PlainTextFileType.INSTANCE),
    JSON(AllIcons.FileTypes.JsonSchema,PlainTextFileType.INSTANCE),
    SQL(AllIcons.FileTypes.Diagram,PlainTextFileType.INSTANCE),
    //C(AllIcons.FileTypes.MicrosoftWindows,PlainTextFileType.INSTANCE),

    //RUBY(AllIcons.Language.Ruby,PlainTextFileType.INSTANCE),
    //PLAINTEXT(AllIcons.FileTypes.Any_type,PlainTextFileType.INSTANCE),
    ;
    private Icon icon;
    private LanguageFileType fileType;

    LanguageEnum(Icon icon, LanguageFileType fileType) {
        this.icon = icon;
        this.fileType = fileType;
    }

    public Icon getIcon() {
        return icon;
    }

    public LanguageFileType getFileType() {
        return fileType;
    }
}
