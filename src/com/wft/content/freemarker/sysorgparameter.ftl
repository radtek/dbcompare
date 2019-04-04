----受理机构参数CMS_ORG_PARAMETER_CONF信息
<#list sysorgParameters as sysorgParameter>
<#if sysorgParameter.type==2>
  update CMS_ORG_PARAMETER_CONF set PARAMETER_VALUE = '${sysorgParameter.parameterValue!}',FLD_S1='${sysorgParameter.flds1!}',FLD_S2='${sysorgParameter.flds2!}' WHERE PARAMETER_NAME='${sysorgParameter.parameterName!}' and ORG_ID='${sysorgParameter.orgId!}';
  
<#elseif  sysorgParameter.type==0 && sysorgParameter.typeDatabase==0>
  insert into CMS_ORG_PARAMETER_CONF (ID,ORG_ID,MODULE_ID,FLD_S1,FLD_S2,PARAMETER_NAME, PARAMETER_VALUE, REMARK, PHYSICS_FLAG, CREATE_TIME, UPDATE_TIME) 
values (SEQ_CMS_ORG_PARAMETER_CONF.NEXTVAL,'${sysorgParameter.orgId!}','${sysorgParameter.moduleId!}','${sysorgParameter.flds1!}','${sysorgParameter.flds2!}','${sysorgParameter.parameterName!}','${sysorgParameter.parameterValue!}','${sysorgParameter.remark!}',1,sysdate,sysdate);
 
</#if>
</#list>

