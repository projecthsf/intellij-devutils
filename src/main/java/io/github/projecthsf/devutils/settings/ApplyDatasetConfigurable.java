package io.github.projecthsf.devutils.settings;

import io.github.projecthsf.devutils.forms.settings.ApplyDatasetSettingForm;

import java.util.Set;

public class ApplyDatasetConfigurable extends CommonMasterDetail<ApplyDatasetSettingForm> {
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
            form.updateForm(itemName, setting.getApplyDatasetMap().get(itemName).getCodeTemplate());
            return;
        }

        form.updateForm(itemName, "");
    }

    @Override
    protected void applyChange() {

    }

    @Override
    protected boolean isFormModified(String itemName) {
        return !setting.getApplyDatasetMap().containsKey(itemName) ||
                !setting.getApplyDatasetMap().get(itemName).getCodeTemplate().equals(form.getTemplateCode());
    }
}
