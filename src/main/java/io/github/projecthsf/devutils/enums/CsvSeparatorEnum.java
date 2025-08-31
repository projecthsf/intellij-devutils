package io.github.projecthsf.devutils.enums;

import org.jetbrains.annotations.NotNull;

public enum CsvSeparatorEnum {
    COMMA(','),
    SPACE('\s'),
    TAB('\t'),
    SEMICOLOON(';'),
    PIPE('|'),
    ;

    private char separator;
    CsvSeparatorEnum(char separator) {
        this.separator = separator;
    }

    public char getSeparator() {
        return separator;
    }
}
