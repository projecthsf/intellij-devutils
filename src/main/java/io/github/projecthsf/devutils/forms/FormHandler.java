package io.github.projecthsf.devutils.forms;

import io.github.projecthsf.devutils.settings.StateComponent;

import java.util.Objects;

public abstract class FormHandler {
    protected StateComponent.State setting = Objects.requireNonNull(StateComponent.getInstance().getState());
    protected boolean modified = false;
    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public abstract void updateForm(String templateName);
    public void resetForm(){}
}
