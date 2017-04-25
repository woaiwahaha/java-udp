package com.etrol.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketUdp {
    final private static String TAG = "SocketUdp: ";
    
    public static void main(String args[]) {
        DatagramSocket socket = null;
        DatagramPacket datapacket = null;
        InetSocketAddress address = null;
        
        try {
            address = new InetSocketAddress(InetAddress.getLocalHost(), 9090);
            socket = new DatagramSocket(address);
            // socket.bind(address);
            
            byte buf[] = new byte[1024];
            datapacket = new DatagramPacket(buf, buf.length);
            System.out.println("等待接收客户端数据．．．");
            socket.receive(datapacket);
            buf = datapacket.getData();
            InetAddress addr = datapacket.getAddress();
            int port = datapacket.getPort();
            System.out.println("客户端发送的数据: " + new String(buf) );
            System.out.println("数据来源 " + addr + ":" + port);
            
            SocketAddress toAddress = datapacket.getSocketAddress();
            String sendStr = "server return ok";
            buf = sendStr.getBytes();
            datapacket = new DatagramPacket(buf, buf.length);
            datapacket.setSocketAddress(toAddress);
            socket.send(datapacket);
            System.out.println("发送结束");
            
        } catch (UnknownHostException e) {
            System.out.println(TAG + e.getMessage());
            e.printStackTrace();
        } catch (SocketException e) {
            System.out.println(TAG + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(TAG + e.getMessage());
            e.printStackTrace();
        }
    }

}