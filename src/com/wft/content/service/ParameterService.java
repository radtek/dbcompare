package com.wft.content.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wft.content.dao.CompareKey;
import com.wft.content.dto.CheckType;
import com.wft.util.SystemMessage;

public class ParameterService {


	private final static Logger log = Logger.getLogger(ParameterService.class);
	
	
	public static <T extends CompareKey> List<T> getDatas(MyArrayList<T> standLs ,MyArrayList<T> standnoLs ) throws Exception{
		List<T> standfinallys = new ArrayList<T>();
		Iterator<T> sysIt = standLs.iterator();
		while(sysIt.hasNext()){
			T sys = sysIt.next();
			if(standnoLs.contains(sys)){//相同
				log.info("相同:"+sys.key());
				sys.setType(CheckType.EXIST_SAME);
				standfinallys.add(sys);
				standnoLs.remove(sys);//去掉相同的
				sysIt.remove();
			}else{
				
				if(standnoLs.containsKey(sys.key())){//存在不相同
					//log.info("不相同:"+sys);
					sys.setType(CheckType.EXIST_NO_SAME);
					standfinallys.add(sys);
					standnoLs.removeKey(sys.key());//去掉key相同的
					sysIt.remove();
				}else{
					/*sys.setType(CheckType.NO_EXIST);//不存在
					standoks.add(sys);
					standnoLs.remove(sys);*/
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
			Iterator<T> finallyIts = standfinallys.iterator();
			while(finallyIts.hasNext()){
				
				T sys = finallyIts.next();
				if(fileterKey.contains(sys.key())){
				 
					finallyIts.remove();
				}
			}
			log.info("standoks.size()最终过滤掉还有:"+standfinallys.size());
		}
		
		return standfinallys;
		
	}
	
	 
}
