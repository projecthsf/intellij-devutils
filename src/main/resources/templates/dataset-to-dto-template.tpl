package org.dev.utils.dto;
import java.lang.*;
import java.math.*;
import java.sql.*;
public class $NameCaseUtil.pascalCase(${className}) {
#foreach($row in $rows)
    #set ($field = $row[0].replace('`', ''))
    #set ($type = $row[1].split('\(')[0])
    private $DataTypeUtil.getDataTypeFromSql($type) $NameCaseUtil.camelCase($field);
#end
}