
<#list columns as column>
 <#if column.drop>
 <#if column.constraintName?matches("(SYS_C|sys_c)[0-9]*")>
 
<#else>
 --drop constraint ${column.constraintType!} (${column.columnNames!})
 alter table  ${shema!}.${table!} drop constraints ${column.constraintName!} ;
 </#if>
 <#else>
 --Create constraint
 <#if column.constraintType=='P'>
ALTER TABLE ${shema!}.${table!} ADD constraint ${column.constraintName!} PRIMARY KEY (${column.columnNames!}) using index;
<#elseif column.constraintType=='U'>
ALTER TABLE ${shema!}.${table!} ADD constraint ${column.constraintName!} UNIQUE (${column.columnNames!}) using index;
<#elseif column.constraintType=='C'>
ALTER TABLE ${shema!}.${table!} ADD   CHECK (${column.searchCondition!});
<#else>
   --外键无法生成${table!}.${constraintName!}
</#if>

</#if>
</#list> 
 