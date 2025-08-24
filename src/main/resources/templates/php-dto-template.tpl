<?php

namespace Dev\Utils;

class ${classname}DTO {
#foreach($property in $properties)
    private ?${property.type} $${property.name};
#end
}