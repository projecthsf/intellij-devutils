package io.github.projecthsf.devutils.actions.converters.dto;

import io.github.projecthsf.devutils.enums.ActionEnum;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.enums.SqlDataTypeEnum;
import io.github.projecthsf.devutils.service.VelocityService;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

import java.util.ArrayList;
import java.util.List;

public class SqlToDTOAction extends CommonToDTOAction {

    public SqlToDTOAction() {
        super(ActionEnum.SQL_TO_DTO);
    }

    protected VelocityService.ClassDTO getTableDTO(String sql) throws Exception {
        Statement statement = CCJSqlParserUtil.parse(sql);
        if (statement instanceof CreateTable createTable) {
            List<VelocityService.PropertyDTO> columns = new ArrayList<>();
            for (ColumnDefinition columnDefinition: createTable.getColumnDefinitions()) {
                columns.add(new VelocityService.PropertyDTO(
                        columnDefinition.getColumnName().replace("`", ""),
                        getDataType(columnDefinition.getColDataType().getDataType())
                ));
            }

            return new VelocityService.ClassDTO(createTable.getTable().getName().replace("`", ""), columns);
        }
        return null;
    }

    private String getDataType(String dataType) throws Exception {
        String type = dataType.split("\\(")[0];
        type = type.trim();
        type = type.split(" ")[0];
        try {
            SqlDataTypeEnum.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new Exception("Not support type: " + dataType);
        }

        if (state.getDataTypeMap(LanguageEnum.SQL).containsKey(type.toUpperCase())) {
            return state.getDataTypeMap(LanguageEnum.SQL).get(type.toUpperCase());
        }

        return String.format("%s[NotMapping]",type.toUpperCase());
    }
}
