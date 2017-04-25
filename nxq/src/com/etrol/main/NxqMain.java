package com.etrol.main;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.etrol.quartz.QuartzJobExample;
import com.etrol.quartz.QuartzManager;

public class NxqMain
{
	public static JTextArea textArea;
  public static void main(String[] args)
  {
	  SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			String jobName = "动态任务调度";
			System.out.println("【系统启动】开始，每三十秒输出一次...");
			QuartzManager.addJob(scheduler, jobName, QuartzJobExample.class, "*/30 * * * * ?");//10/2 * * * * ?
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	  
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    textArea = new JTextArea();
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setColumns(40);
    textArea.setRows(25);
    textArea.setText("【系统启动】开始，每三十秒输出一次...");
//    label.setText("<html><p>每30秒读取数据库一次....<p><br/><p>0/30 * * * * ?<p></html>");
    panel.add(textArea);

    frame.setBounds(400, 200, 500, 500);
    frame.add(panel);
    frame.setTitle("TLV");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(3);
  }
}