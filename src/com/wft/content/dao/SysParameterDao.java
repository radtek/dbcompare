package com.wft.content.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wft.content.dto.SysParameter;
import com.wft.content.service.MyArrayList;
import com.wft.db.DBTempory;

/** 
 * 系统参数表CMS_SYS_PARAMETER
 *
 */
public class SysParameterDao {

	private final static Logger log = Logger.getLogger(SysParameterDao.class);
	
	public static MyArrayList<SysParameter> getDatas(DBTempory dbTempory,int typeDatabase) throws Exception{
		MyArrayList<SysParameter> ls = new MyArrayList<SysParameter>();
		 String sql="select * from "+dbTempory.getShema()+ ".CMS_SYS_PARAMETER where 1=1";
		List<Map<String,String>> maplist = dbTempory.getDatasByTable(sql);
		for(Map<String,String> mps:maplist){
			SysParameter sysParameter = new SysParameter();
			ls.add(sysParameter);
			sysParameter.setParameterName(mps.get("PARAMETER_NAME"));
			sysParameter.setParameterValue(mps.get("PARAMETER_VALUE"));
			sysParameter.setRemark(mps.get("REMARK"));
			sysParameter.setTypeDatabase(typeDatabase);
		}
		log.info("size:"+ls.size());
		return ls;
	}
	
 
}
