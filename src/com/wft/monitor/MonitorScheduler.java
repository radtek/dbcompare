package com.wft.monitor;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wft.service.MyBasicBankService;
import com.wft.util.SystemMessage;

/**
 * @author admin 定时任务提醒
 */
@Component
public class MonitorScheduler {
	
	@Autowired
	private MonitorEmailReport monitorEmailReport;
	@Autowired
	private MyBasicBankService myBasicBankService;
	
	@PostConstruct
	public void init() {
		// 通过schedulerFactory获取一个调度器
		SchedulerFactory schedulerfactory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			// 通过schedulerFactory获取一个调度器
			scheduler = schedulerfactory.getScheduler();

			// 创建jobDetail实例，绑定Job实现类
			// 指明job的名称，所在组的名称，以及绑定job类
			 
			JobDetail jobDetail = new JobDetail("job1", "jgroup1",Monitorjob.class);
			jobDetail.getJobDataMap().put("monitorEmailReport", monitorEmailReport);
			jobDetail.getJobDataMap().put("myBasicBankService", myBasicBankService);
			// 定义调度触发规则，每天上午10：15执行
			CronTrigger cornTrigger = new CronTrigger("cronTrigger","triggerGroup");
			// 执行规则表达式
			 String emailCrontime = SystemMessage.getString("emailCrontime");
			cornTrigger.setCronExpression(emailCrontime);
			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(jobDetail, cornTrigger);

			// 启动调度
			scheduler.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
