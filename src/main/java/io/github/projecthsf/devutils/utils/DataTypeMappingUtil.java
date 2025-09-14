package io.github.projecthsf.devutils.utils;

import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.enums.SqlDataTypeEnum;
import io.github.projecthsf.devutils.settings.StateComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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


    public static String getDataTypeFromSql(String sqlType) {
        StateComponent.State setting = Objects.requireNonNull(StateComponent.getInstance().getState());
        if (setting.getDataTypeMap(LanguageEnum.SQL).containsKey(sqlType)) {
            return setting.getDataTypeMap(LanguageEnum.SQL).get(sqlType);
        }

        return "SQL_TYPE_NOT_MAP_" + sqlType;
    }

    public static String getDataTypeFromJson(String jsonType) {
        StateComponent.State setting = Objects.requireNonNull(StateComponent.getInstance().getState());
        if (setting.getDataTypeMap(LanguageEnum.JSON).containsKey(jsonType)) {
            return setting.getDataTypeMap(LanguageEnum.JSON).get(jsonType);
        }

        return "JSON_TYPE_NOT_MAP_" + jsonType;
    }
}
