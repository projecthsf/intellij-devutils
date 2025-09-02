DTO Code Templates
====
Base on Apache Velocity
## 1. Variables and Util
- ${classname} or $classname: your class name in Pascal case
- ${originalClassname} or $originalClassname: your original class name
- \$NameCaseUtil is a util support to name case format, supported functions: 
  - camelCase
  - kebabCase
  - pascalCase
  - snakeCase
  - constantCase
  - upperCase
  - lowerCase
 
    <p>Example: $NameCaseUtil.upperCase($classname) </p>
- \$properties:  [\$property, ...]<br />
  $property: {
    - name: field name in camel case format
    - originalName: original field name
    - type: data type after mapping (Settings > Dev Utils > Data Type Mapping > SQL)
    - originalType: original data type that detected from source
  <br />}

<p>Example: </p>
<pre>
package org.dev.utils.dto;
import java.lang.*;
import java.math.*;
import java.sql.*;
// original class name: $originalClassName
public class ${className}DTO {
#foreach($property in $properties)
    // Original type: $property.originalType
    // Original name: $property.originalName
    private ${property.type} ${property.name};
#end
}
</pre>

![DTO template place holder](https://raw.githubusercontent.com/projecthsf/intellij-devutils/refs/heads/main/docs/images/setting-dto-templates.png "Dto Templates")


## References:
1. [Apache Velocity document!](https://velocity.apache.org/engine/1.7/user-guide.html#methods)
