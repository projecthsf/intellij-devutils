package io.github.projecthsf.devutils.language.java;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class JavaTokenType extends IElementType {

    public JavaTokenType(@NotNull @NonNls String debugName) {
        super(debugName, JavaLanguage.INSTANCE);
    }
}
