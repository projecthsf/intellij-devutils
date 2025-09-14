package io.github.projecthsf.devutils.forms.settings;

import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.service.VelocityService;
import io.github.projecthsf.devutils.utils.DatasetUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DatasetToDTOSettingFormHandler extends FormHandler {
    private DatasetToDTOSettingForm form;
    public DatasetToDTOSettingFormHandler(DatasetToDTOSettingForm form) {
        this.form = form;

        form.addListeners(
                new TextAreaDocumentListener(this, true),
                new TextAreaDocumentListener(this, false),
                new ComboBoxListener(this),
                new VariableListener(this)
        );
        /*
        String datasetSample = ApplyDatasetUtil.getTemplate("templates/applydataset-dataset-sample.tpl");
        form.updateDataset(datasetSample);

        String codeTemplateSample = ApplyDatasetUtil.getTemplate("templates/applydataset-code-template-sample.tpl");
        form.updateCodeTemplate(codeTemplateSample);*/
    }


    String getPreviewString(boolean updateDataset) {
        if (form.getDataset().isEmpty() || form.getCodeTemplate().isEmpty()) {
            return "";
        }
        VelocityService service = VelocityService.getInstance();
        List<DatasetUtil.DatatsetDTO> dtos = DatasetUtil.getDatasetRecords(form.getSeparator(), form.getDataset(), updateDataset);

        Map<String, String> fields = new HashMap<>();
        for (Vector vector: form.variable.getRecords()) {
            fields.put((String) vector.get(0), (String) vector.get(1));
        }

        List<Map<Object, String>> records = new ArrayList<>();
        for (DatasetUtil.DatatsetDTO dto: dtos) {
            records.add(dto.getVelocity());
        }

        return service.merge(fields, records, form.getCodeTemplate());
    }

    static class VariableListener implements TableModelListener {
        private DatasetToDTOSettingFormHandler controller;
        VariableListener(DatasetToDTOSettingFormHandler controller) {
            this.controller = controller;
        }

        @Override
        public void tableChanged(TableModelEvent e) {
            String preview = controller.getPreviewString(true);
            controller.modified = true;
            controller.form.updatePreview(preview);
        }
    }

    static class ComboBoxListener implements ActionListener {
        private DatasetToDTOSettingFormHandler controller;
        ComboBoxListener(DatasetToDTOSettingFormHandler controller) {
            this.controller = controller;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String preview = controller.getPreviewString(true);
            controller.modified = true;
            controller.form.updatePreview(preview);
        }
    }

    static class TextAreaDocumentListener implements DocumentListener {
        private DatasetToDTOSettingFormHandler controller;
        private final boolean updateDataset;
        TextAreaDocumentListener(DatasetToDTOSettingFormHandler controller, boolean updateDataset) {
            this.controller = controller;
            this.updateDataset = updateDataset;
        }

        @Override
        public void documentChanged(@NotNull DocumentEvent event) {
            String preview = controller.getPreviewString(updateDataset);
            controller.modified = true;
            controller.form.updatePreview(preview);
        }
    }

    @Override
    public void updateForm(String templateName) {
        System.out.println("=== updateForm");
    }

    @Override
    public void resetForm() {
        System.out.println("=== resetForm");
    }


}
