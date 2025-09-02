package io.github.projecthsf.devutils.actions.converters.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.service.VelocityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonToDTOAction extends CommonToDTOAction {

    public JsonToDTOAction() {
        super(ActionEnum.JSON_TO_DTO);
    }
    @Override
    protected VelocityService.ClassDTO getTableDTO(String selectedText) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(selectedText, new TypeReference<Map<String, Object>>() {});

        if (map.isEmpty()) {
            return null;
        }

        List<VelocityService.PropertyDTO> columns = new ArrayList<>();
        for (String key: map.keySet()) {
            String sourceDataType = getType(map.get(key));
            if (!settings.getDataTypeMap(LanguageEnum.JSON).containsKey(sourceDataType)) {
                continue;
            }
            String targetDataType = settings.getDataTypeMap(LanguageEnum.JSON).get(sourceDataType);
            columns.add(new VelocityService.PropertyDTO(
                    key,
                    targetDataType
            ));

        }
        return new VelocityService.ClassDTO("YourClassName", columns);
    }

    private String getType(Object o) {
        if (o == null) {
            return Object.class.getName();
        }

        return o.getClass().getName();
    }
}
