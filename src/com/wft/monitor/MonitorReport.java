package com.wft.monitor;

import com.wft.model.MybasicBank;

/**
 * @author admin
 * 健康上报
 */
public interface MonitorReport {

	public void report(MybasicBank mybasicBank,String message);
}
