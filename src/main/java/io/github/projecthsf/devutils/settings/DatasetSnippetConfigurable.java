package io.github.projecthsf.devutils.settings;

import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.forms.settings.DatasetSnippetSettingForm;
import io.github.projecthsf.devutils.forms.toolWindows.DatasetSnippetWindowFormHandler;

import java.util.Set;

public class DatasetSnippetConfigurable extends CommonMasterDetail<DatasetSnippetSettingForm> {
    @Override
    protected Set<String> getItemNames() {
        return setting.getDatasetSnippetMap().keySet();
    }

    @Override
    DatasetSnippetSettingForm createForm() {
        return new DatasetSnippetSettingForm();
    }

    @Override
    protected void updateForm(String itemName) {
        if (setting.getDatasetSnippetMap().containsKey(itemName)) {
            StateComponent.DatasetSnippetState state = setting.getDatasetSnippetMap().get(itemName);
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
        setting.getDatasetSnippetMap().put(itemName, new StateComponent.DatasetSnippetState(form.getSeparator(), form.getDataset(), form.getCodeTemplate()));
    }

    @Override
    protected boolean isFormModified(String itemName) {
        if (!setting.getDatasetSnippetMap().containsKey(itemName)) {
            return true;
        }

        StateComponent.DatasetSnippetState state = setting.getDatasetSnippetMap().get(itemName);
        return !state.getCsvSeparator().equals(form.getSeparator()) ||
                !state.getDataset().equals(form.getDataset()) ||
                !state.getCodeTemplate().equals(form.getCodeTemplate());

    }

    @Override
    protected FormHandler getFormHandler() {
        return new DatasetSnippetWindowFormHandler(form);
    }

    @Override
    protected void deleteItem(String itemName) {
        setting.getDatasetSnippetMap().remove(itemName);
    }
}
