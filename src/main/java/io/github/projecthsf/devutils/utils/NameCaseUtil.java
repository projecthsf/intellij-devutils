package io.github.projecthsf.devutils.utils;
import io.github.projecthsf.devutils.enums.NameCaseEnum;

public class NameCaseUtil {
    public static String toNameCase(NameCaseEnum nameCase, String text) {
        return switch (nameCase) {
            case CAMEL_CASE -> io.github.projecthsf.utils.NameCaseUtil.toCamelCase(text);
            case KEBAB_CASE -> io.github.projecthsf.utils.NameCaseUtil.toKebabCase(text);
            case PASCAL_CASE -> io.github.projecthsf.utils.NameCaseUtil.toPascalCase(text);
            case SNAKE_CASE -> io.github.projecthsf.utils.NameCaseUtil.toSnakeCase(text);
            case CONSTANT_CASE -> io.github.projecthsf.utils.NameCaseUtil.toSnakeCase(text).toUpperCase();
            case UPPER_CASE -> text.toUpperCase();
            case LOWER_CASE -> text.toLowerCase();
        };
    }

    // $NameCaseUtil.camelCase($field.name)
    public static String camelCase(String text) {
        return toNameCase(NameCaseEnum.CAMEL_CASE, text);
    }

    public static String kebabCase(String text) {
        return toNameCase(NameCaseEnum.KEBAB_CASE, text);
    }

    public static String pascalCase(String text) {
        return toNameCase(NameCaseEnum.PASCAL_CASE, text);
    }

    public static String snakeCase(String text) {
        return toNameCase(NameCaseEnum.SNAKE_CASE, text);
    }

    public static String constantCase(String text) {
        return toNameCase(NameCaseEnum.CONSTANT_CASE, text);
    }

    public static String upperCase(String text) {
        return toNameCase(NameCaseEnum.UPPER_CASE, text);
    }

    public static String lowerCase(String text) {
        return toNameCase(NameCaseEnum.LOWER_CASE, text);
    }
}
