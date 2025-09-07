package io.github.projecthsf.devutils.language.java;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public interface JavaTokenSets {
    TokenSet COMMENTS = TokenSet.create(JavaTypes.COMMENT);
    TokenSet KEYWORDS = TokenSet.create(
        JavaTypes.KEY_ABSTRACT,
        JavaTypes.KEY_CLASS,
        JavaTypes.KEY_DEFAULT,
        JavaTypes.KEY_EXTENDS,
        JavaTypes.KEY_FINAL,
        JavaTypes.KEY_IMPLEMENTS,
        JavaTypes.KEY_IMPORT,
        JavaTypes.KEY_NEW,
        JavaTypes.KEY_PACKAGE,
        JavaTypes.KEY_PRIVATE,
        JavaTypes.KEY_PROTECT,
        JavaTypes.KEY_PUBLIC,
        JavaTypes.KEY_STATIC,
        JavaTypes.KEY_STRICTFP,
        JavaTypes.KEY_SUPER,
        JavaTypes.KEY_THIS,
        JavaTypes.KEY_THROW,
        JavaTypes.KEY_THROWS,
        JavaTypes.KEY_VOID,

        JavaTypes.DATATYPE_BYTE,
        JavaTypes.DATATYPE_SHORT,
        JavaTypes.DATATYPE_INT,
        JavaTypes.DATATYPE_LONG,
        JavaTypes.DATATYPE_FLOAT,
        JavaTypes.DATATYPE_DOUBLE,
        JavaTypes.DATATYPE_BOOLEAN,
        JavaTypes.DATATYPE_CHAR
    );

}
