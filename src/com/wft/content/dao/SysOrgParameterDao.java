package com.wft.content.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wft.content.dto.SysOrgParameter;
import com.wft.content.service.MyArrayList;
import com.wft.db.DBTempory;

/** 
 * 系统参数表CMS_ORG_PARAMETER_CONF
 *
 */
public class SysOrgParameterDao {

	private final static Logger log = Logger.getLogger(SysOrgParameterDao.class);
	
	public static MyArrayList<SysOrgParameter> getDatas(DBTempory dbTempory,int typeDatabase) throws Exception{
		MyArrayList<SysOrgParameter> ls = new MyArrayList<SysOrgParameter>();
		 String sql="select * from "+dbTempory.getShema()+ ".CMS_ORG_PARAMETER_CONF where 1=1";
		List<Map<String,String>> maplist = dbTempory.getDatasByTable(sql);
		for(Map<String,String> mps:maplist){
			SysOrgParameter sysParameter = new SysOrgParameter();
			ls.add(sysParameter);
			sysParameter.setParameterName(mps.get("PARAMETER_NAME"));
			sysParameter.setParameterValue(mps.get("PARAMETER_VALUE"));
			sysParameter.setRemark(mps.get("REMARK"));
			sysParameter.setFlds1(mps.get("FLD_S1"));
			sysParameter.setFlds2(mps.get("FLD_S2"));
			sysParameter.setModuleId(mps.get("MODULE_ID"));
			sysParameter.setOrgId(mps.get("ORG_ID"));
			 
			sysParameter.setTypeDatabase(typeDatabase);
		}
		log.info("size:"+ls.size());
		return ls;
	}
	
 
}
