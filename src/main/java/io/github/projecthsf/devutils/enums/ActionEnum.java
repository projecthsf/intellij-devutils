package io.github.projecthsf.devutils.enums;

import com.intellij.icons.AllIcons;

import javax.swing.*;

public enum ActionEnum {
    APPLY_DATA_SET_AS_CODE_TEMPLATE("Copy as code template", null, ActionGroupEnum.APPLY_DATASET),
    APPLY_DATA_SET_AS_DATASET("Copy as dataset", null, ActionGroupEnum.APPLY_DATASET),
    SQL_TO_DTO("SQL to DTO", AllIcons.FileTypes.Jsp, ActionGroupEnum.CONVERTERS),
    JSON_TO_DTO("JSON to DTO", AllIcons.Nodes.DataSchema, ActionGroupEnum.CONVERTERS),
    NAME_CASE("Switch name cases between camel case/ snake case/...", null, ActionGroupEnum.NAME_CASE),
    GET_LENGTH("Get length", null, ActionGroupEnum.STRING_UTILS),
    REMOVE_HTML_TAGS("Remove HTML tags", null, ActionGroupEnum.STRING_UTILS),
    REMOVE_QUOTES("Remove quotes", null, ActionGroupEnum.STRING_UTILS),
    STRING_JOIN("Join to one line", null, ActionGroupEnum.STRING_UTILS),
    STRING_LIST("Make string list",  null, ActionGroupEnum.STRING_UTILS),
    STRING_SPLIT("Split to multi line", null, ActionGroupEnum.STRING_UTILS),
    STRING_TOGGLE_QUOTE("Toggle quotes", null, ActionGroupEnum.STRING_UTILS),
    STRING_WRAP_QUOTE("Wrap line with quote", null, ActionGroupEnum.STRING_UTILS),
    ;
    private String title;
    private Icon icon;
    private ActionGroupEnum group;
    ActionEnum(String title, Icon icon, ActionGroupEnum group) {
        this.title = title;
        this.icon = icon;
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public Icon getIcon() {
        return icon;
    }

    public ActionGroupEnum getGroup() {
        return group;
    }

    public ActionEnum setTitle(String title) {
        this.title = title;
        return this;
    }

    public ActionEnum setIcon(Icon icon) {
        this.icon = icon;
        return this;
    }
}
