
package com.wft.db;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * ClassName:CacheUtil <br/>
 * @see 	 
 */
public class CacheUtil {
	
	private final static Logger log = Logger.getLogger(CacheUtil.class);
	
	public static long default_long_overdue_10h = 36000000l;//10h
	public static long default_long_overdue_10m = 600l;//10m
	private  static CacheUtil cacheObj = new CacheUtil();// 缓存实例对象
	
    /**
     * 虚拟缓存
     */
    private volatile Map<String,Object> cache;
    
    /**
     * 过期时间
     */
    private volatile Map<String,Long> tasks ;
    
	private CacheUtil() {
		cache = new ConcurrentHashMap<String, Object>();
		tasks = new ConcurrentHashMap<String,Long>();
	}
    
	/**
	 * 采用单例模式获取缓存对象实例
	 * 
	 * @return
	 */
	public  static CacheUtil getInstance() {
		 
		return cacheObj;
	}

	
	
	 /**
     * 存入缓存 默认10h
     */
    public void set(String key,Object value){
     
    	set(key,value,0L);
    }
    
    /**
     * 存入缓存
     */
    public void set(String key,Object value,long timeout){
     
    	log.info(String.format("存入缓存{%s}",key));
    	synchronized (CacheUtil.class) {
	        this.cache.put(key, value);
	        long currentTimeMillis = System.currentTimeMillis();
			if(timeout > 0l){
	        	this.tasks.put(key,timeout*1000l + currentTimeMillis);
	        }else{
	        	this.tasks.put(key,default_long_overdue_10h + currentTimeMillis);
	        }
    	}
    	
    }
	
    /**
     * 获取缓存
     */
    public Object get(String key){
    	
    	Long timeout = this.tasks.get(key);
		if(timeout != null && System.currentTimeMillis() <= timeout ){
			log.info(String.format("获取缓存成功{%s}",key));
			return this.cache.get(key);
		}else{
			log.info(String.format("获取缓存失败{%s}",key));
			this.cache.remove(key);
			return null;
		}
    }

    /**
     * 删除缓存
     */
    public void delete(String key){
    	synchronized (CacheUtil.class) {
	    	this.cache.remove(key);
	    	this.tasks.remove(key);
    	}
    }
	
    /**
     * 删除缓存
     */
    public void clear( ){
    	synchronized (CacheUtil.class) {
    		log.info("清楚缓存成功");
	    	this.cache.clear();
	    	this.tasks.clear();
    	}
    }
    
    /**
     * 删除缓存 key-模糊查询
     */
    public void clear( String keys){
    	synchronized (CacheUtil.class) {
    		Iterator<String> cacheIt = this.cache.keySet().iterator();
    		while(cacheIt.hasNext()){
    			String key = cacheIt.next();
    			if(key.toUpperCase().contains(keys.toUpperCase())){
    				log.info(String.format("清楚cache缓存成功{%s}",key));
    				cacheIt.remove();
    			}
    		}
	    	 
	    	 
	    	Iterator<String> tasksIt = this.tasks.keySet().iterator();
    		while(tasksIt.hasNext()){
    			String key = tasksIt.next();
    			if(key.toUpperCase().contains(keys.toUpperCase())){
    				log.info(String.format("清楚tasks缓存成功{%s}",key));
    				tasksIt.remove();
    			}
    		}
    	}
    }
}

