 --Create INDEX
 <#list columns as column>
 create index ${shema!}.${column.indexName!} on ${shema!}.${table!}(${column.columnNames!}) online;
</#list>