package com.wft.util;

import java.util.ArrayList;
import java.util.List;



/**
 * @author admin
 * 常量信息
 */
public class CommonUtil {
	
	//物理标识
	public static final int ALL = 0;
	public static final int EABLE = 1;
	public static final int DISEABLE = 2;
	
	//是否检查入库
	public static final int CHECK_ON = 0;
	public static final int CHECK_OFF = 1;
	
	//是否追加到标准库脚本文件
	public static final int APPEND_ON = 0;
	public static final int APPEND_OFF = 1;
	//换行
	public final static String LINE = System.getProperty("line.separator");
	
	//银行级别
	public  final static String BANK_TYPE_A = "A";
	public  final static String BANK_TYPE_B = "B";
	public  final static String BANK_TYPE_C = "C";
	

	//生成类型
	//表结构差异_0缺表 _1缺列，通过前台传输 A|1,B|0这种方式
	 //表结构差异表结构差异A|0缺表 B|1缺列 C|2缺约束  D|3缺序列
	public  final static String GEN_TYPE_0 = "0";
	public  final static String GEN_TYPE_1 = "1";
	public  final static String GEN_TYPE_2 = "2";
	public  final static String GEN_TYPE_3 = "3";
	public  final static String GEN_TYPE_STR = "0,1,2,3";
	
	
	//CHECK_RESULT is '检测结果 0-为比较 1-比较无差异 2-比较有差异';
	public static final int CHECK_RESULT_NOT_START = 0;
	public static final int CHECK_RESULT_OK = 1;
	public static final int CHECK_RESULT_FAIL = 2;
	
	//编码
	public final static String UTF_8 = "UTF-8";
	public final static String GB2312 = "GB2312";
	//输入编码
	public final static String IN_CODE = UTF_8;
	//输出编码
	public final static String OUT_CODE = GB2312;
	
	//批
	public static class BatchData<T> {

		private int batchIndex;
		
		private List<T> dataList;

		public int getBatchIndex() {
			return batchIndex;
		}

		public void setBatchIndex(int batchIndex) {
			this.batchIndex = batchIndex;
		}

		public List<T> getDataList() {
			return dataList;
		}

		public void setBatchData(List<T> dataList) {
			this.dataList = dataList;
		}
	}

	public static <T> List<BatchData<T>> getBatchDataList(int batchSize, List<T> data) {
		int batchIndex = 0;
		int totalBatch = getTotalBatch(batchSize, data);
		
		List<BatchData<T>> batchDataList = new ArrayList<BatchData<T>>(totalBatch);
		for (int total = data.size(); total>0; total -= batchSize) {
			int currentBatchSize = total>=batchSize? batchSize:total;
			List<T> taskData = new ArrayList<T>(currentBatchSize);
			for (int i=0; i< currentBatchSize; i++){
				T t = data.get(total-1-i);
				taskData.add(t);
			}
			
			BatchData<T> batchData = new BatchData<T>();
			batchData.setBatchData(taskData);
			batchData.setBatchIndex(batchIndex);
			batchDataList.add(batchData);
			batchIndex++;
		}
		
		return batchDataList;
	}
	
	/**
	 * 获取总批次数
	 * @param batchSize
	 * @param data
	 * @return
	 */
	private static <T> int getTotalBatch(int batchSize, List<T> data) {
		int toProcessCount = data.size();
		int batchCount = toProcessCount/batchSize;
		int batchMod = toProcessCount%batchSize;
		int totalBatch = batchCount;
		if (batchMod > 0) {
			totalBatch = batchCount + 1;
		}
		return totalBatch;
	}
}
