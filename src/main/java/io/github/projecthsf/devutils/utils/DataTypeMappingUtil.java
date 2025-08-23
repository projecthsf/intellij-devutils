package io.github.projecthsf.devutils.utils;

import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.enums.SqlDataTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class DataTypeMappingUtil {
    private static final Map<String, LanguageEnum> defaultLanguage = new HashMap<>();
    static {
        defaultLanguage.put("IU", LanguageEnum.JAVA);
        defaultLanguage.put("IC", LanguageEnum.JAVA);
        defaultLanguage.put("IE", LanguageEnum.JAVA);
        defaultLanguage.put("PS", LanguageEnum.PHP);
        //defaultLanguage.put("WS", LanguageEnum.PLAINTEXT);
        defaultLanguage.put("PY", LanguageEnum.PYTHON);
        defaultLanguage.put("PC", LanguageEnum.PYTHON);
        defaultLanguage.put("PE", LanguageEnum.PYTHON);
        //defaultLanguage.put("RM", LanguageEnum.RUBY);
        //defaultLanguage.put("OC", LanguageEnum.PLAINTEXT);
        //defaultLanguage.put("CL", LanguageEnum.C);
        defaultLanguage.put("GO", LanguageEnum.GO);
        //defaultLanguage.put("DB", LanguageEnum.PLAINTEXT);
        //defaultLanguage.put("RD", LanguageEnum.PLAINTEXT);
        defaultLanguage.put("AI", LanguageEnum.JAVA);
        //defaultLanguage.put("RR", LanguageEnum.PLAINTEXT);
        //defaultLanguage.put("QA", LanguageEnum.PLAINTEXT);
    }


    public static String getKey(SqlDataTypeEnum dataType) {
        return String.format("%s", dataType.name());
    }
}
