package io.github.projecthsf.devutils.forms.settings;

import io.github.projecthsf.devutils.enums.CsvSeparatorEnum;
import io.github.projecthsf.devutils.forms.toolWindows.ApplyDatasetWindowForm;
import io.github.projecthsf.devutils.utils.ActionUtil;

public class ApplyDatasetSettingForm extends ApplyDatasetWindowForm {
    public void updateForm(
            String displayName,
            CsvSeparatorEnum csvSeparator,
            String dataset,
            String codeTemplate
    ) {
        separartor.setSelectedItem(csvSeparator);
        ActionUtil.updateText(dataSet, dataset);
        ActionUtil.updateText(this.codeTemplate, codeTemplate);
    }

    public String getCodeTemplate() {
        return codeTemplate.getDocument().getText();
    }

}
