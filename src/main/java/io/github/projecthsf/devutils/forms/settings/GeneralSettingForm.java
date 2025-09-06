package io.github.projecthsf.devutils.forms.settings;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.enums.ActionGroupEnum;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GeneralSettingForm extends JPanel {
    private Map<ActionEnum, JBCheckBox> actionMap = new HashMap<>();
    private Map<ActionGroupEnum, Map<ActionEnum, Boolean>> actionAndGroupMap;
    public GeneralSettingForm(Map<ActionGroupEnum, Map<ActionEnum, Boolean>> actionAndGroupMap) {
        this.actionAndGroupMap = actionAndGroupMap;
        setLayout(new BorderLayout());
        add(getOptions(), BorderLayout.NORTH);
    }

    private JPanel getOptions() {
        FormBuilder builder =  FormBuilder.createFormBuilder();
        for (ActionGroupEnum group: ActionGroupEnum.values()) {
            builder.addComponent(new JBLabel(group.getDisplayName()));
            Map<ActionEnum, Boolean> map = actionAndGroupMap.get(group);
            for (ActionEnum action: map.keySet()) {
                JBCheckBox checkBox = new JBCheckBox(action.getTitle(), map.get(action));
                actionMap.put(action, checkBox);
                builder.addLabeledComponent("   ", checkBox);
            }
        }
        return builder.getPanel();
    }

    public Boolean getActionMap(ActionEnum action) {
        return actionMap.get(action).isSelected();
    }

    public void reset() {
        for (ActionEnum action: actionMap.keySet()) {
            actionMap.get(action).setSelected(actionAndGroupMap.get(action.getGroup()).get(action));
        }
    }
}
