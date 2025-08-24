package io.github.projecthsf.devutils.enums;

public enum JsonDataTypeEnum {
    STRING("java.lang.String", "String"),
    INTEGER("java.lang.Integer", "Interger"),
    DOUBLE("java.lang.Double", "Dobule"),
    OBJECT("java.lang.Object", "Object"),
    BOOLEAN("java.lang.Boolean", "Boolean"),
    ;
    private String sourceDataType;
    private String targetDataType;
    JsonDataTypeEnum(String sourceDataType, String dataType) {
        this.sourceDataType = sourceDataType;
        this.targetDataType = dataType;
    }

    public String getSourceDataType() {
        return sourceDataType;
    }

    public String getTargetDataType() {
        return targetDataType;
    }

    public static JsonDataTypeEnum getByJsonDataType(String dataType) {
        for (JsonDataTypeEnum value: JsonDataTypeEnum.values()) {
            if (value.getSourceDataType().equals(dataType)) {
                return value;
            }
        }

        return null;
    }
}
