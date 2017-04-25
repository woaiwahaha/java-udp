package com.etrol.quartz;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.etrol.main.NxqMain;
import com.etrol.main.tvlDataUtil;
import com.etrol.socket.SocketUdpClient;

/**
 * quartz示例定时器类
 * 
 * @author Administrator
 *
 */
public class QuartzJobExample implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(new Date()) + "-----");
		String result = tvlDataUtil.readData();
		
		String len[]=result.split(",");
		for (int i = 0; i < 7; i++) {
		System.out.println(len[i]);
		String hexresult;
		try {
			hexresult = tvlDataUtil.tlvData(len[i],i+1);
			System.out.println("hexresult:"+hexresult);
			NxqMain.textArea.setText(result);
			System.out.println("byte[]:"+tvlDataUtil.hexStringToByte(tvlDataUtil.removeTrim(hexresult)));
			SocketUdpClient.send(tvlDataUtil.hexStringToByte(tvlDataUtil.removeTrim(hexresult)));//指定要发送的IP和端口
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
//		SerialListener.serialManager().WritePort(
//				tvlDataUtil.hexStringToByte(hexresult));//写入
//		SerialListener.serialManager().ReadPort();//读取
	}
	
//	public static void main(String[] args) {
//		QuartzJobExample job = new QuartzJobExample();
//		try {
//			job.execute(null);
//		} catch (JobExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
