package io.github.projecthsf.devutils.language.java;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class JavaFile extends PsiFileBase {

    public JavaFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, JavaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return JavaFileType.INSTANCE;
    }

}
