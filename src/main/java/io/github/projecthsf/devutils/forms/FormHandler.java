package io.github.projecthsf.devutils.forms;

public class FormHandler {
    protected boolean modified = false;
    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
