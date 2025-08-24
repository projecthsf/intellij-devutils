Class ${classname}DTO {
#foreach($property in $properties)
   ${property.type} $${property.name};
#end
}