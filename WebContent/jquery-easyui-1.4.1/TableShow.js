//表关键字突出标识
var Table = {};
 Table.keys = {
		"CREATE":"CREATE",
		"TABLE":"TABLE",
		"DROP":"DROP",
		"UPDATE":"UPDATE",
		"SYSDATE":"SYSDATE",
		"COMMENT":"COMMENT",
		//"ON":"ON",
		"COLUMN":"COLUMN",
		"NOT":"NOT",
		"NULL":"NULL",
		"NUMBER":"NUMBER",
		"VARCHAR2":"VARCHAR2",
		"TIMESTAMP":"TIMESTAMP",
		//"IS":"IS",
		"ALTER":"ALTER",
		"ADD":"ADD",
		"PRIMARY":"PRIMARY",
		"KEY":"KEY",
		"SEQUENCE":"SEQUENCE",
		"START":"START",
		"WITH":"WITH"
		//"ID":"ID",
		//"NAME":"NAME",
};

 Table.show1=function(str){
	var content = "";
	content = str.replace(/\r\n/g,"<br/>").replace(/;/g,";<br/>");
	content = str.replace(/&/g,"<br/>");
	for (var key in Table.keys){
		var reger=new RegExp(key,"gi");
		content = content.replace(reger,"<span style='color:blue;'>"+key+"</span>");
	}
	return content;
}; 