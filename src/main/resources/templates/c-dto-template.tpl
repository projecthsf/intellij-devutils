class ${classname}DTO {
public:
    #foreach($property in $properties)
        ${property.type} $${property.name};
    #end
};