class: ${classname} -> table name with pascal case
original class: ${originalClassname} -> table name
properties:
#foreach($property in $properties)
    ======= Field: ${property.originalName}
    - Type: ${property.type}
    - Original Type: ${property.originalType}
    - Name: ${property.name}
    - Original Name: ${property.originalName}
      * Utils:
    - Camel case: ${NameCaseUtil.camelCase(${property.originalName})}
    - Snake case: ${NameCaseUtil.snakeCase(${property.originalName})}
    - Kebab case: ${NameCaseUtil.kebabCase(${property.originalName})}
    - Constant case: ${NameCaseUtil.constantCase(${property.originalName})}
    - Upper case: ${NameCaseUtil.upperCase(${property.originalName})}
    - Lower case: ${NameCaseUtil.lowerCase(${property.originalName})}

#end
