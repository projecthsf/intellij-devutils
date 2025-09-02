package io.github.projecthsf.devutils.actions.converters.dto;

import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.enums.SqlDataTypeEnum;
import io.github.projecthsf.devutils.service.VelocityService;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SqlToDTOAction extends CommonToDTOAction {
    public SqlToDTOAction(@NotNull String title, Icon icon) {
        super(title, icon);
    }

    protected VelocityService.TableDTO getTableDTO(String sql) throws Exception {
        Statement statement = CCJSqlParserUtil.parse(sql);

        if (statement instanceof CreateTable createTable) {
            List<VelocityService.ColumnDTO> columns = new ArrayList<>();
            for (ColumnDefinition columnDefinition: createTable.getColumnDefinitions()) {
                columns.add(new VelocityService.ColumnDTO(
                        columnDefinition.getColumnName().replace("`", ""),
                        getDataType(columnDefinition.getColDataType().getDataType())
                ));
            }

            return new VelocityService.TableDTO(createTable.getTable().getName().replace("`", ""), columns);
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

        if (settings.getDataTypeMap(LanguageEnum.SQL).containsKey(type.toUpperCase())) {
            return settings.getDataTypeMap(LanguageEnum.SQL).get(type.toUpperCase());
        }

        return String.format("%s[NotMapping]",type.toUpperCase());
    }
}
