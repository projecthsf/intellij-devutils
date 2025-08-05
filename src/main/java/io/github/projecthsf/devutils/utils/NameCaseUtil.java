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


}
