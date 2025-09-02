package io.github.projecthsf.devutils.enums;

public enum ActionGroupEnum {
    CONVERTERS("Converters"),
    NAME_CASE("Name Case Converter"),
    APPLY_DATASET("Apply With Dataset"),
    STRING_UTILS("String Utils"),
    ;

    private String displayName;
    ActionGroupEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
