package io.github.projecthsf.devutils.forms.toolWindows;

import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.util.text.Strings;
import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.service.VelocityService;
import io.github.projecthsf.devutils.toolWindow.controller.ApplyDatasetWindowController;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ApplyDatasetWindowFormHandler extends FormHandler {
    private ApplyDatasetWindowForm form;
    public ApplyDatasetWindowFormHandler(ApplyDatasetWindowForm form) {
        this.form = form;

        String datasetSample = ApplyDatasetUtil.getTemplate("templates/applydataset-dataset-sample.tpl");
        form.updateDataset(datasetSample);
        form.addListeners(
                new TextAreaDocumentListener(this, true),
                new TextAreaDocumentListener(this, false),
                new ComboBoxListener(this)
        );
        String codeTemplateSample = ApplyDatasetUtil.getTemplate("templates/applydataset-code-template-sample.tpl");
        form.updateCodeTemplate(codeTemplateSample);
    }


    String getPreviewString(boolean updateDataset) {
        if (form.getDataset().isEmpty() || form.getCodeTemplate().isEmpty()) {
            return "";
        }
        VelocityService service = VelocityService.getInstance();
        List<ApplyDatasetUtil.DatatsetDTO> dtos = ApplyDatasetUtil.getDatasetRecords(form.getSeparator(), form.getDataset(), updateDataset);


        List<String> items = new ArrayList<>();
        for (ApplyDatasetUtil.DatatsetDTO dto: dtos) {
            items.add(getPreviewString(service, dto));
        }

        return Strings.join(items, "\n");
    }

    private String getPreviewString(VelocityService service, ApplyDatasetUtil.DatatsetDTO dto) {
        String codeTemplate = form.getCodeTemplate();
        for (String key: dto.getSimplify().keySet()) {
            codeTemplate = codeTemplate.replace(key, dto.getSimplify().get(key));
        }

        try {
            codeTemplate = service.merge(dto.getVelocity(), codeTemplate);
        } catch (Exception e) {

        }
        return codeTemplate;
    }

    static class ComboBoxListener implements ActionListener {
        private ApplyDatasetWindowFormHandler controller;
        ComboBoxListener(ApplyDatasetWindowFormHandler controller) {
            this.controller = controller;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String preview = controller.getPreviewString(true);
            controller.form.updatePreview(preview);
        }
    }

    static class TextAreaDocumentListener implements DocumentListener {
        private ApplyDatasetWindowFormHandler controller;
        private final boolean updateDataset;
        TextAreaDocumentListener(ApplyDatasetWindowFormHandler controller, boolean updateDataset) {
            this.controller = controller;
            this.updateDataset = updateDataset;
        }

        @Override
        public void documentChanged(@NotNull DocumentEvent event) {
            String preview = controller.getPreviewString(updateDataset);
            controller.form.updatePreview(preview);
        }
    }


}
