package io.github.projecthsf.devutils.enums;

public enum ActionGroupEnum {
    APPLY_DATASET("Apply With Dataset"),
    NAME_CASE("Name Case Converter"),
    STRING_UTILS("String Utils"),
    ;

    private String name;
    ActionGroupEnum(String name) {
        this.name = name;
    }
}
