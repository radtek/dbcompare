<#list columns as column>
<#if column.timeCol>
ALTER TABLE ${column.shema!}.${column.table!} ADD ( ${column.code!} ${column.type!} );
<#elseif  column.notNull>
ALTER TABLE ${column.shema!}.${column.table!} ADD ( ${column.code!} ${column.type!}(${column.len!}${column.scaleStr}) NOT NULL );
<#else>
ALTER TABLE ${column.shema!}.${column.table!} ADD ( ${column.code!} ${column.type!}(${column.len!}${column.scaleStr}) DEFAULT NULL);
  
</#if>

comment on column ${column.shema!}.${column.table!}.${column.code!} is '${column.comment!}';
</#list>

