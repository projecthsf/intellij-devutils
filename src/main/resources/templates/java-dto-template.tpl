package org.dev.utils.dto;
import java.lang.*;
import java.math.*;
import java.sql.*;
public class ${classname}DTO {
#foreach($property in $properties)
    private ${property.type} ${property.name};
#end
}