--Create TABLE
CREATE TABLE ${shema!}.${table!} (
<#list columns as column>
	<#if column_has_next>
		    <#if column.timeCol>
		     ${column.code!} ${column.type!} ,
		    <#elseif column.notNull>
			   ${column.code!} ${column.type!}(${column.len!'10'}${column.scaleStr}) NOT NULL,
			<#else>
			   ${column.code!} ${column.type!}(${column.len!'255'}${column.scaleStr}),
			</#if>
	 <#else>
		 	<#if column.timeCol>
		     ${column.code!} ${column.type!} 
		    <#elseif column.notNull>
			   ${column.code!} ${column.type!}(${column.len!}${column.scale}${column.scaleStr}) NOT NULL
			<#else>
			   ${column.code!} ${column.type!}(${column.len!}${column.scale}${column.scaleStr})
			</#if>
		</#if> 
	</#list> 
)
LOGGING
NOCOMPRESS
NOCACHE
;
--comment 
<#list columns as column>
comment on column ${column.shema!}.${column.table!}.${column.code!} is '${column.comment!}'; 
</#list>

 
