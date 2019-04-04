package com.wft.content.service;

import java.util.ArrayList;
import java.util.Iterator;

import com.wft.content.dao.CompareKey;
import com.wft.content.dto.SysParameter;

/**
 * @author admin
 * 之所以复写ArrayList 需要做CompareKey比较
 * @param <E> CompareKey
 */
public class MyArrayList<E extends CompareKey> extends ArrayList<E> {
 
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public boolean containsKey( String key) {
			return indexOfkey(key) >= 0;
	  }
	
	public boolean removeKey( String key) {
		Iterator<E> sysIt = this.iterator();
		while(sysIt.hasNext()){
			E e = sysIt.next();
			if (e.key().equals(key)){
				sysIt.remove();
				return true;
			}
		}
		return false;
		 
	}
	  
	  public int indexOfkey(String key) {
		  int size = this.size();
		  for (int i = 0; i < size; i++){
			  E e = this.get(i);
			  if (e.key().equals(key))
				    return i;
		  }
				
		  return -1;
	  }
}
