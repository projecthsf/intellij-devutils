package io.github.projecthsf.devutils.settings;

import com.intellij.openapi.options.Configurable;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.forms.settings.GeneralSettingForm;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

final class GeneralConfigurable implements Configurable {

    private GeneralSettingForm form;
    StateComponent.State state = Objects.requireNonNull(StateComponent.getInstance().getState());
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
        form = new GeneralSettingForm(state.getActionAndGroupMap());
        return form;
    }

    @Override
    public boolean isModified() {
        for (ActionEnum action: ActionEnum.values()) {
            if (form.getActionMap(action) != state.getActionAndGroupMap().get(action.getGroup()).get(action)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void apply() {
        for (ActionEnum action: ActionEnum.values()) {
            state.getActionAndGroupMap().get(action.getGroup()).put(action, form.getActionMap(action));
        }
    }

    @Override
    public void reset() {
        form.reset();
    }

    @Override
    public void disposeUIResources() {
        form = null;
    }

}
