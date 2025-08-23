package io.github.projecthsf.devutils.enums;

public enum SqlDataTypeEnum {
    CHAR("String"),
    VARCHAR("String"),
    TEXT("String"),
    BLOB("String"),
    VARBINARY("String"),
    ENUM("String"),
    SET("String"),
    TINYINT("Integer"),
    SMALLINT("Integer"),
    MEDIUMINT("Integer"),
    INT("Integer"),
    INTEGER("Integer"),
    BIGINT("Long"),
    FLOAT("Float"),
    REAL("Float"),
    DOUBLE("Double"),
    NUMERIC("BigDecimal"),
    DECIMAL("BigDecimal"),
    BOOLEAN("Boolean"),
    DATE("Date"),
    TIME("Time"),
    DATETIME("Timestamp"),
    TIMESTAMP("Timestamp"),
    ;
    private String dataType;
    SqlDataTypeEnum(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }
}
