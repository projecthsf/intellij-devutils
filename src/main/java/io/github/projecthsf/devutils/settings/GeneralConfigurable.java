package io.github.projecthsf.devutils.settings;

import com.intellij.openapi.options.Configurable;
import io.github.projecthsf.devutils.forms.GeneralSettingForm;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

final class GeneralConfigurable implements Configurable {

    private GeneralSettingForm form;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        form = new GeneralSettingForm();
        return form;
    }

    @Override
    public boolean isModified() {
        StateComponent.State state = Objects.requireNonNull(StateComponent.getInstance().getState());
        return true;
    }

    @Override
    public void apply() {
        StateComponent.State state = Objects.requireNonNull(StateComponent.getInstance().getState());

    }

    @Override
    public void reset() {
        StateComponent.State state = Objects.requireNonNull(StateComponent.getInstance().getState());

    }

    @Override
    public void disposeUIResources() {
        form = null;
    }

}
