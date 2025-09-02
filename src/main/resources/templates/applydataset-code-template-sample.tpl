- Row: $0,$1
	// simplify
	Name: $0 - UpperCase ${0.upperCase}
	Year:${1}
	// velocity
	Name: $cols[0] - UpperCase $cols["0.upperCase"] or $NameCaseUtil.upperCase($cols[0])
	Year:$cols[1]
	First Name: $cols[0].split(' ')[0]
