class ${classname}DTO:
    def __init__(self#foreach($property in $properties), ${property.name}#end):
#foreach($property in $properties)
        self.${property.name} = ${property.name}
#end