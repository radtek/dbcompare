package com.wft.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangzhengyi
 * 日志描述，某一方法需要记录日志的，请添加该注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogDescription {

	/**
	 * @return
	 * 日记描述，spring el表达式
	 */
	public String description() default "no description";

	/**
	 * @return
	 * 操作类型 
	 */
	public String type() default "no type";

	/**
	 * @return
	 * 实体表
	 */
	public String entityType() default "no entityType";
}
