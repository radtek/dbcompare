--系统参数CMS_ORG_PARAMETER信息
<#list sysParameters as sysParameter>
<#if sysParameter.type==2>
  update cms_sys_parameter set PARAMETER_VALUE = '${sysParameter.parameterValue!}' WHERE PARAMETER_NAME='${sysParameter.parameterName!}';
   
<#elseif  sysParameter.type==0 && sysParameter.typeDatabase==0>
  insert into cms_sys_parameter (PARAMETER_NAME, PARAMETER_VALUE, REMARK, PHYSICS_FLAG, CREATE_TIME, UPDATE_TIME) 
values ('${sysParameter.parameterName!}','${sysParameter.parameterValue!}','${sysParameter.remark!}',1,sysdate,sysdate); 

</#if>
<#if  sysParameter.parameterName=='ClEANING_FILE_IS_NULL' && sysParameter.parameterValue=='TRUE'>
  update cms_sys_parameter set PARAMETER_VALUE = 'FALSE' WHERE PARAMETER_NAME='MULTI_CLEANFILE_PER_SEND'; 
  
</#if>
</#list>

