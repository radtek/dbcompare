package com.wft.content.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.wft.content.dao.SysParameterDao;
import com.wft.content.dto.CheckType;
import com.wft.content.dto.SysParameter;
import com.wft.db.DBTempory;

/**
 * @author admin
 * 系统参数表CMS_ORG_PARAMETER
 */
public class SysParameterService  {


	private final static Logger log = Logger.getLogger(SysParameterService.class);
	
	public static List<SysParameter> getDatas(MyArrayList<SysParameter> standLs ,MyArrayList<SysParameter> standnoLs ) throws Exception{
		return ParameterService.getDatas(standLs, standnoLs);
	}
	/*public static List<SysParameter> getDatas(MyArrayList<SysParameter> standLs ,MyArrayList<SysParameter> standnoLs ) throws Exception{
		List<SysParameter> standfinallys = new ArrayList<SysParameter>();
		Iterator<SysParameter> sysIt = standLs.iterator();
		while(sysIt.hasNext()){
			SysParameter sys = sysIt.next();
			if(standnoLs.contains(sys)){//相同
				//log.info("相同:"+sys);
				sys.setType(CheckType.EXIST_SAME);
				standfinallys.add(sys);
				standnoLs.remove(sys);//去掉相同的
				sysIt.remove();
			}else{
				
				if(standnoLs.containsKey(sys.getParameterName())){//存在不相同
					//log.info("不相同:"+sys);
					sys.setType(CheckType.EXIST_NO_SAME);
					standfinallys.add(sys);
					standnoLs.removeKey(sys.getParameterName());//去掉key相同的
					sysIt.remove();
				}else{
					sys.setType(CheckType.NO_EXIST);//不存在
					standoks.add(sys);
					standnoLs.remove(sys);
				}
			}
		}//end for
		log.info("standLs.size()还有:"+standLs.size());
		log.info("standnoLs.size()还有:"+standnoLs.size());
		standfinallys.addAll(standLs);
		standfinallys.addAll(standnoLs);
		log.info("standoks.size()最终有:"+standfinallys.size());
		//过滤掉sysParameter_filter_key无需比对
		String fileterKey  = SystemMessage.getString("sysParameter_filter_key");
		if(StringUtils.isNotBlank(fileterKey)){
			Iterator<SysParameter> finallyIts = standfinallys.iterator();
			while(finallyIts.hasNext()){
				
				SysParameter sys = finallyIts.next();
				if(fileterKey.contains(sys.getParameterName())){
				 
					finallyIts.remove();
				}
			}
			log.info("standoks.size()最终过滤掉还有:"+standfinallys.size());
		}
		
		return standfinallys;
		
	}*/
	
	
	public static void main(String[] args) throws Exception {
		DBTempory tstandnoLs = new DBTempory("jdbc:oracle:thin:@123.207.118.153:1521:ntest","zhangzhengyi","zhangzhengyi","zhangzhengyi");
		DBTempory tstandLs = new DBTempory("jdbc:oracle:thin:@123.207.118.153:1521:ntest","bolz","bolz","bolz");
		MyArrayList<SysParameter> standLs  = SysParameterDao.getDatas(tstandLs, CheckType.CAN_KAO_DATABASE);
		MyArrayList<SysParameter> standnoLs  = SysParameterDao.getDatas(tstandnoLs, CheckType.NO_CAN_KAO_DATABASE);
		List<SysParameter> ls = getDatas(standLs,standnoLs);
		for(SysParameter sys:ls){
			log.info(sys);
		}
		
		 
	}
}
