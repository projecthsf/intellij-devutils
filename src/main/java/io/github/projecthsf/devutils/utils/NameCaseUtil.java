package io.github.projecthsf.devutils.utils;

import com.intellij.webSymbols.utils.NameCaseUtils;
import io.github.projecthsf.devutils.enums.NameCaseEnum;

public class NameCaseUtil {
    public static String toNameCase(NameCaseEnum nameCase, String text) {
        return switch (nameCase) {
            case CAMEL_CASE -> NameCaseUtils.toCamelCase(text);
            case KEBAB_CASE -> NameCaseUtils.toKebabCase(text);
            case PASCAL_CASE -> NameCaseUtils.toPascalCase(text);
            case SNAKE_CASE -> NameCaseUtils.toSnakeCase(text);
            case CONSTANT_CASE -> NameCaseUtils.toSnakeCase(text).toUpperCase();
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
