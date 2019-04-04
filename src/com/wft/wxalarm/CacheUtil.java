 

package com.wft.wxalarm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName:CacheUtil <br/>
 * @see 	 
 */
public class CacheUtil {
	
	private static long default_long_overdue = 7000000l;//7200s

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
     * 存入缓存
     */
    public void set(String key,Object value,long timeout){
    	synchronized (CacheUtil.class) {
	        this.cache.put(key, value);
	        long currentTimeMillis = System.currentTimeMillis();
			if(timeout > 0l){
	        	this.tasks.put(key,timeout*1000l + currentTimeMillis);
	        }else{
	        	this.tasks.put(key,default_long_overdue + currentTimeMillis);
	        }
    	}
    	
    }
	
    /**
     * 获取缓存
     */
    public Object get(String key){
    	Long timeout = this.tasks.get(key);
		if(timeout != null && System.currentTimeMillis() <= timeout ){
			return this.cache.get(key);
		}else{
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
	
}

