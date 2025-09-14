package io.github.projecthsf.devutils.settings;

import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.forms.settings.DatasetToDTOSettingForm;
import io.github.projecthsf.devutils.forms.settings.DatasetToDTOSettingFormHandler;
import io.github.projecthsf.devutils.utils.DatasetUtil;

import java.util.Set;

public class DatasetToDTOConfigurable extends CommonMasterDetail<DatasetToDTOSettingForm> {
    @Override
    protected Set<String> getItemNames() {
        return setting.getDatesetToDtoMap().keySet();
    }

    @Override
    DatasetToDTOSettingForm createForm() {
        return new DatasetToDTOSettingForm();
    }

    @Override
    protected void updateForm(String itemName) {
        if (setting.getDatesetToDtoMap().containsKey(itemName)) {
            StateComponent.DatasetToDTOState state = setting.getDatesetToDtoMap().get(itemName);
            form.updateForm(
                    state.getCsvSeparator(),
                    state.getDataset(),
                    state.getCodeTemplate(),
                    state.getVariables()
            );
            return;
        }

        form.reset();
    }



    @Override
    protected void applyChange(String itemName) {
        setting.getDatesetToDtoMap().put(itemName, new StateComponent.DatasetToDTOState(form.getSeparator(), form.getDataset(), form.getCodeTemplate(), form.getVariable()));
    }

    @Override
    protected boolean isFormModified(String itemName) {
        if (!setting.getDatesetToDtoMap().containsKey(itemName)) {
            return true;
        }

        StateComponent.DatasetToDTOState state = setting.getDatesetToDtoMap().get(itemName);
        if (form.getVariable().size() != state.getVariables().size()) {
            return true;
        }

        for (String key: form.getVariable().keySet()) {
            if (!setting.getDatesetToDtoMap().get(itemName).getVariables().containsKey(key)) {
                return true;
            }

            if (!setting.getDatesetToDtoMap().get(itemName).getVariables().get(key).equals(form.getVariable().get(key))) {
                return true;
            }
        }

        return !state.getCsvSeparator().equals(form.getSeparator()) ||
                !state.getDataset().equals(form.getDataset()) ||
                !state.getCodeTemplate().equals(form.getCodeTemplate());
    }


    @Override
    protected FormHandler getFormHandler() {
        return new DatasetToDTOSettingFormHandler(form);
    }

    @Override
    protected void deleteItem(String itemName) {
        setting.getDatesetToDtoMap().remove(itemName);
    }
}
