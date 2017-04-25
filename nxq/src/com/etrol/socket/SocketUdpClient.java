package com.etrol.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import sun.net.InetAddressCachePolicy;

public class SocketUdpClient {
    final private static String TAG = "udp客户端: ";
	private static DatagramSocket getSocket;
    public static void main(String[] args) {
//		sendDataByUdp("aa".getBytes(),"192.168.8.66",9999);
		try {
//			send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
   
    
    public static void sendDataByUdp(byte[] bytes,String hostName,int port){
        try {
            getSocket = new DatagramSocket();
            getSocket.bind(new InetSocketAddress(9990));
            DatagramPacket datapacket = null;
            InetSocketAddress toAddress = new InetSocketAddress(hostName, port);
            byte[] buf = new byte[1024];
            datapacket = new DatagramPacket(bytes, bytes.length);
            datapacket.setSocketAddress(toAddress);
            
            //发送数据
            getSocket.send(datapacket);
            System.out.println("客户端发送数据完成...");
            
            //获取服务器返回的数据
            //getSocket.receive(datapacket);
            //buf = datapacket.getData();
            System.out.println("服务器返回数据: " + new String(buf));
        } catch (SocketException e) {
            System.out.println(TAG + e.getMessage());
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.out.println(TAG + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(TAG + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    
    
//    public static void send(byte[] bytes,String hostName,int port) throws Exception{
//        DatagramSocket s = new DatagramSocket(null);
//        s.setReuseAddress(true);
//        s.bind(new InetSocketAddress(8888));
//        byte[] b = new byte[1024];
//        
//        DatagramPacket p = new DatagramPacket(bytes, b.length, new InetSocketAddress(hostName, port));
//        s.send(p);
//        System.out.println("发送成功!");
//      }
    
    public static void send(byte[] data) throws Exception{
    	DatagramSocket s = new DatagramSocket(null);
    	s.setReuseAddress(true);
    	s.bind(new InetSocketAddress(9001));
    	
    	DatagramPacket p = new DatagramPacket(data,0,data.length, new InetSocketAddress("192.168.1.10", 9090));
    	s.send(p);
    }
    
}
    
   
    
    
    
    
    
    

