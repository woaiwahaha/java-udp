package com.etrol.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {
	public static void main(String[] args)throws Exception {

		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		String jobName = "动态任务调度";
		System.out.println("【系统启动】开始，每秒输出二次...");
		QuartzManager.addJob(scheduler, jobName, QuartzJobExample.class, "10/2 * * * * ?");
		
		
		
		Thread.sleep(10000);
		System.out.println("【十秒后开始更新任务，每秒打印一次...】");
		QuartzManager.modifyJobTime(scheduler, jobName, "0/1 * * * * ?");
//		Thread.sleep(3000);
		
		
//		System.out.println("【修改时间】开始(每2秒输出一次)...");
//        QuartzManager.modifyJobTime(scheduler, jobName, "10/2 * * * * ?");
//        Thread.sleep(4000);
//        System.out.println("【移除定时】开始...");
//        QuartzManager.removeJob(scheduler, jobName);
//        System.out.println("【移除定时】成功");
	
	}


}
