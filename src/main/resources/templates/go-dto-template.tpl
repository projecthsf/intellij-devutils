package dto

type ${classname}DTO struct {
#foreach($property in $properties)
     ${property.name} ${property.type}
#end
}