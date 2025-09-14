package io.github.projecthsf.devutils.forms.toolWindows;

import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.service.VelocityService;
import io.github.projecthsf.devutils.settings.StateComponent;
import io.github.projecthsf.devutils.utils.DatasetUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatasetToDTOWindowFormHandler extends FormHandler {
    private final DatasetToDTOWindowForm form;
    public DatasetToDTOWindowFormHandler(DatasetToDTOWindowForm form) {
        this.form = form;
        form.addListeners(
                new TextAreaDocumentListener(this, true),
                new VariableListener(this),
                new ComboBoxListener(this)
        );
    }

    @Override
    public void updateForm(String templateName) {
        StateComponent.DatasetToDTOState state = setting.getDatesetToDtoMap().get(templateName);
        form.updateForm(state.getCsvSeparator(), state.getDataset(), state.getCodeTemplate(), state.getVariables());
    }

    String getPreviewString(boolean updateDataset) {
        if (form.getDataset().isEmpty() || form.getCodeTemplate().isEmpty()) {
            return "";
        }
        VelocityService service = VelocityService.getInstance();
        List<DatasetUtil.DatatsetDTO> dtos = DatasetUtil.getDatasetRecords(form.getSeparator(), form.getDataset(), updateDataset);

        List<Map<Object, String>> records = new ArrayList<>();
        for (DatasetUtil.DatatsetDTO dto: dtos) {
            records.add(dto.getVelocity());
        }

        return service.merge(form.getVariables(), records, form.getCodeTemplate());
    }

    static class ComboBoxListener implements ActionListener {
        private final DatasetToDTOWindowFormHandler controller;
        ComboBoxListener(DatasetToDTOWindowFormHandler controller) {
            this.controller = controller;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String preview = controller.getPreviewString(true);
            controller.modified = true;
            controller.form.updatePreview(preview);
        }
    }

    static class VariableListener implements javax.swing.event.DocumentListener {
        private final DatasetToDTOWindowFormHandler controller;
        VariableListener(DatasetToDTOWindowFormHandler controller) {
            this.controller = controller;
        }

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            String preview = controller.getPreviewString(true);
            controller.modified = true;
            controller.form.updatePreview(preview);
        }


        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            String preview = controller.getPreviewString(true);
            controller.modified = true;
            controller.form.updatePreview(preview);
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {

        }
    }

    static class TextAreaDocumentListener implements DocumentListener {
        private DatasetToDTOWindowFormHandler controller;
        private final boolean updateDataset;
        TextAreaDocumentListener(DatasetToDTOWindowFormHandler controller, boolean updateDataset) {
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

}
