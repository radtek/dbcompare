package com.wft.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

 
/**
 * @author zhangzhengyi
 * vo复制属性，当遇到某属性为null不复制
 */
public abstract class BeanNotNullCopyUtils extends org.springframework.beans.BeanUtils {

	  public static void copyProperties(Object source, Object target) throws BeansException {
	    Assert.notNull(source, "Source must not be null");
	    Assert.notNull(target, "Target must not be null");
	    Class<?> actualEditable = target.getClass();
	    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
	    for (PropertyDescriptor targetPd : targetPds) {
	      if (targetPd.getWriteMethod() != null) {
	        PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
	        if (sourcePd != null && sourcePd.getReadMethod() != null) {
	          try {
	            Method readMethod = sourcePd.getReadMethod();
	            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
	              readMethod.setAccessible(true);
	            }
	            Object value = readMethod.invoke(source);
	            Class clz = targetPd.getPropertyType();
	            if(clz==String.class){
	            	String valstr = (String)value;
	            	if(valstr!=null&&valstr.trim().length()==0){
	            		value = null;
	            	}
	            }
               // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
	            if (value != null) {
	              Method writeMethod = targetPd.getWriteMethod();
	              if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
	                writeMethod.setAccessible(true);
	              }
	              writeMethod.invoke(target, value);
	            }
	          } catch (Throwable ex) {
	            throw new FatalBeanException("Could not copy properties from source to target", ex);
	          }
	        }
	      }
	    }
	  }
	  
	  public static void main(String[] args) {
		  
	}
	}