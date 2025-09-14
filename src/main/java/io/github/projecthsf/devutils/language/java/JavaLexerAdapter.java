package io.github.projecthsf.devutils.language.java;

import com.intellij.lexer.FlexAdapter;

public class JavaLexerAdapter extends FlexAdapter {

    public JavaLexerAdapter() {
        super(new _JavaLexer(null));
    }

}
