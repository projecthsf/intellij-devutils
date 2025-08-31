Code template
===========

## a. Access column values:
<pre>
// simplify
$0 / ${0} = value of first column
$1 / ${1} = value of second column

// velocity
$cols[0] / $cols["0"] = value of first column
$cols[1] / $cols["1"] = value of second column
</pre>
 
## b. Name case format:
<pre>
// simplify
${0.camelCase}/${1.camelCase}/...
${0.pascalCase}/${1.pascalCase}/...
${0.kebabCase}/${1.kebabCase}/...
${0.snakeCase}/${1.snakeCase}/...
${0.upperCase}/${1.upperCase}/...
${0.lowerCase}/${1.lowerCase}/...
${0.constantCase}/${1.constantCase}/...

// velocity
$cols["0.camelCase"]/$cols["1.camelCase"]/...
$cols["0.pascalCase"]/$cols["1.pascalCase"]/...
$cols["0.kebabCase"]/$cols["1.kebabCase"]/...
$cols["0.snakeCase"]/$cols["1.snakeCase"]/...
$cols["0.upperCase"]/$cols["1.upperCase"]/...
$cols["0.lowerCase"]/$cols["1.lowerCase"]/...
$cols["0.constantCase"]/$cols["1.constantCase"]/...

// or
$NameCaseUtil.camelCase($cols[0])/$NameCaseUtil.camelCase($cols[1])/...
</pre>
** You can also use any function that Velocity supports

## c. Example:
### - Simple Dataset
<pre>
Anne Hathaway,1982
Sydney Sweeney,1997
</pre>

### Code template
###### Simplify
![Simplify code template](https://raw.githubusercontent.com/projecthsf/intellij-devutils/refs/heads/2-csv-dataset-velocity/docs/images/code-template-simplify.png "Simplify code template")
<pre>
Name: $0 Year: $1 - UpperCase: ${0.upperCase}
</pre>
** Note **\$0**=**\${0}** and **\$1**=**\${1}**

###### Velocity
![Velocity code template](https://raw.githubusercontent.com/projecthsf/intellij-devutils/refs/heads/2-csv-dataset-velocity/docs/images/code-template-velocity.png "Velocity code template")
<pre>
Name: $cols[0] Year: $cols[1] - UpperCase: $cols["0.upperCase"]
</pre>
** Note $cols["0.upperCase"] = \$NameCaseUtil.upperCase(\$cols[0]) 
### Result
<pre>
Name: Anne Hathaway Year: 1982 - UpperCase: ANNE HATHAWAY
Name: Sydney Sweeney Year: 1997 - UpperCase: SYDNEY SWEENEY
</pre>

### - Advance Dataset
<pre>
private boolean date;
private int name;
private BigDecimal price;
</pre>
** Since this dataset is not separte by comma (,), we can choose separator = SPACE(' ')

![Velocity advance code template](https://raw.githubusercontent.com/projecthsf/intellij-devutils/refs/heads/2-csv-dataset-velocity/docs/images/code-template-velocity-advance.png "Velocity advance code template")
### Code template
<pre>
- FieldName: "${2}" -- end with semicolon(;)
    DataType: ${1}
    Remove last semicolon(;): $cols[2].replace(';', '')
</pre>

### Result
<pre>
- FieldName: "date;" --  end with semicolon(;)
    DataType: boolean
    Remove last semicolon(;): date

- FieldName: "name;" --  end with semicolon(;)
    DataType: int
    Remove last semicolon(;): name

- FieldName: "price;" --  end with semicolon(;)
    DataType: BigDecimal
    Remove last semicolon(;): price
</pre>

## References:
<a target="ApacheVelocity" href="https://velocity.apache.org/engine/1.7/user-guide.html#methods">Velocity document</a>
