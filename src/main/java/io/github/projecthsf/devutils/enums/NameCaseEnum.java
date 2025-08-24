package io.github.projecthsf.devutils.enums;


import javax.swing.*;

public enum NameCaseEnum {
    CAMEL_CASE("camelCase", "To camelCase", null),
    SNAKE_CASE("snakeCase", "To snake__case", null),
    KEBAB_CASE("kebabCase", "To kebab-case", null),
    PASCAL_CASE("pascalCase", "To PascalCase", null),
    UPPER_CASE("upperCase", "To UPPER CASE", null),
    CONSTANT_CASE("constantCase", "To CONSTANT__CASE", null),
    LOWER_CASE("lowerCase", "To lower case", null),
    ;

    private final String code;
    private final String text;
    private final Icon icon;
    NameCaseEnum(String code,String text, Icon icon) {
        this.code = code;
        this.text = text;
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }
}
