package io.github.projecthsf.devutils.forms.settings;

import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import io.github.projecthsf.devutils.forms.FormHandler;
import io.github.projecthsf.devutils.service.VelocityService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DtoTemplateSettingFormHandler extends FormHandler {
    DtoTemplateSettingForm form;
    public DtoTemplateSettingFormHandler(DtoTemplateSettingForm form) {
        this.form = form;
        form.addListeners(new TextAreaDocumentListener(this));
    }

    private String getPreviewString() {
        List<VelocityService.PropertyDTO> columns = new ArrayList<>();
        VelocityService.PropertyDTO id = new VelocityService.PropertyDTO(
                "id",
                "INT"
        );
        id.setName("id");
        id.setType("Integer");
        columns.add(id);

        VelocityService.PropertyDTO name = new VelocityService.PropertyDTO(
                "user_name",
                "VARCHAR"
        );
        name.setName("userName");
        name.setType("String");
        columns.add(name);

        VelocityService.ClassDTO classDTO = new VelocityService.ClassDTO("your_class_name", columns);
        VelocityService service = VelocityService.getInstance();
        return service.merge(classDTO, form.getTemplateCode());
    }

    static class TextAreaDocumentListener implements DocumentListener {
        private DtoTemplateSettingFormHandler controller;
        TextAreaDocumentListener(DtoTemplateSettingFormHandler controller) {
            this.controller = controller;
        }

        @Override
        public void documentChanged(@NotNull DocumentEvent event) {
            String preview = controller.getPreviewString();
            controller.form.updatePreview(preview);
        }
    }
}
