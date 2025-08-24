package io.github.projecthsf.devutils.service;

import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import io.github.projecthsf.devutils.enums.SqlDataTypeEnum;
import io.github.projecthsf.devutils.settings.StateComponent;
import io.github.projecthsf.devutils.utils.DataTypeMappingUtil;
import io.github.projecthsf.devutils.utils.NameCaseUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class VelocityService {
    private static VelocityEngine velocityEngine;
    private final StringResourceRepository repo;

    VelocityService(StringResourceRepository repo) {
        this.repo = repo;
    }
    public static VelocityService getInstance() {
        if (velocityEngine == null) {
            velocityEngine = new VelocityEngine();
            velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "string");
            velocityEngine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
            velocityEngine.addProperty("string.resource.loader.repository.static", "false");
            velocityEngine.init();
        }

        StringResourceRepository repo = (StringResourceRepository) velocityEngine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);

        return new VelocityService(repo);
    }

    public String merge(TableDTO dto, String templateString) {
        repo.putStringResource("FROM_TEMPLATE_STRING", templateString);
        Template template = velocityEngine.getTemplate("FROM_TEMPLATE_STRING");
        VelocityContext context = new VelocityContext();
        context.put("NameCaseUtil", NameCaseUtil.class);
        context.put("classname", NameCaseUtil.toNameCase(NameCaseEnum.PASCAL_CASE, dto.getTableName()));
        context.put("originalClassname", dto.getTableName());
        context.put("properties", getFinalProperties(dto.getColumns()));
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

    private List<ColumnDTO> getFinalProperties(List<ColumnDTO> properties) {
        List<ColumnDTO> newList = new ArrayList<>();
        if (properties == null) {
            return newList;
        }

        for (ColumnDTO property: properties) {
            ColumnDTO dto = new ColumnDTO(property.getName(), property.getType());
            dto.setName(NameCaseUtil.toNameCase(NameCaseEnum.CAMEL_CASE, property.getName()));
            newList.add(dto);
        }

        return newList;
    }

    public static class ColumnDTO {
        private String name;
        private String type;
        private final String originalName;
        private final String originalType;

        public ColumnDTO(
                String name,
                String type
        ) {
            this.name = name;
            this.originalName = name;
            this.type = type;
            this.originalType = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public ColumnDTO setName(String name) {
            this.name = name;
            return this;
        }

        public ColumnDTO setType(String type) {
            this.type = type;
            return this;
        }

        public String getOriginalName() {
            return originalName;
        }

        public String getOriginalType() {
            return originalType;
        }
    }

    public static class TableDTO {
        private String tableName;
        private List<ColumnDTO> columns;

        public TableDTO(String tableName, List<ColumnDTO> properties) {
            this.tableName = tableName;
            this.columns = properties;
        }

        public String getTableName() {
            return tableName;
        }

        public List<ColumnDTO> getColumns() {
            return columns;
        }
    }

}
