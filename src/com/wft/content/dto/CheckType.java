package com.wft.content.dto;

/**
 *检测类型
 *
 */
public class CheckType {

	//不存在
	public static final int NO_EXIST  = 0;
	//存在相同
	public static final int EXIST_SAME  = 1;
	//存在不相同
	public static final int EXIST_NO_SAME  = 2;
	 
	
	//是否是参考库0或者被比较库1
	public static final int CAN_KAO_DATABASE  = 0;
	
	//是否是参考库0或者被比较库1
	public static final int NO_CAN_KAO_DATABASE  = 1;
}
