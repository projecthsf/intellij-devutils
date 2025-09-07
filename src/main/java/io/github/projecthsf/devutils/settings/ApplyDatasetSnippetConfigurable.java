package io.github.projecthsf.devutils.settings;

import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.forms.settings.ApplyDatasetSettingForm;
import io.github.projecthsf.devutils.forms.toolWindows.ApplyDatasetWindowFormHandler;

import java.util.Set;

public class ApplyDatasetSnippetConfigurable extends CommonMasterDetail<ApplyDatasetSettingForm> {
    @Override
    protected Set<String> getItemNames() {
        return setting.getApplyDatasetMap().keySet();
    }

    @Override
    ApplyDatasetSettingForm createForm() {
        return new ApplyDatasetSettingForm();
    }

    @Override
    protected void updateForm(String itemName) {
        if (setting.getApplyDatasetMap().containsKey(itemName)) {
            StateComponent.ApplyDatasetState state = setting.getApplyDatasetMap().get(itemName);
            form.updateForm(
                    state.getCsvSeparator(),
                    state.getDataset(),
                    state.getCodeTemplate()
            );
            return;
        }

        form.reset();
    }

    @Override
    protected void applyChange(String itemName) {
        setting.getApplyDatasetMap().put(itemName, new StateComponent.ApplyDatasetState(form.getSeparator(), form.getDataset(), form.getCodeTemplate()));
    }

    @Override
    protected boolean isFormModified(String itemName) {
        if (!setting.getApplyDatasetMap().containsKey(itemName)) {
            return true;
        }

        StateComponent.ApplyDatasetState state = setting.getApplyDatasetMap().get(itemName);
        return !state.getCsvSeparator().equals(form.getSeparator()) ||
                !state.getDataset().equals(form.getDataset()) ||
                !state.getCodeTemplate().equals(form.getCodeTemplate());

    }

    @Override
    protected FormHandler getFormHandler() {
        return new ApplyDatasetWindowFormHandler(form);
    }

    @Override
    protected void deleteItem(String itemName) {
        setting.getApplyDatasetMap().remove(itemName);
    }
}
