package io.github.projecthsf.devutils.forms;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import io.github.projecthsf.devutils.enums.LanguageEnum;

import javax.swing.*;
import java.awt.*;

public class GeneralSettingForm extends JPanel {
    ComboBox<LanguageEnum> languages;
    public GeneralSettingForm() {
        languages  = new ComboBox<>(LanguageEnum.values());
        setLayout(new BorderLayout());
        add(getOptions(), BorderLayout.NORTH);
    }

    private JPanel getOptions() {
        return FormBuilder.createFormBuilder()
                //.addLabeledComponent("Main language: ", languages)
                .getPanel();
    }
}
