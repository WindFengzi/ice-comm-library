package com.winso.comm_library;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class GetIP {

	public static String getLocalIP(Context ac) {
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) ac.getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			// wifiManager.setWifiEnabled(true);
			
			return getLocalIpAddress();
			
		}

		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		
		return ip;
	}

	private static String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	//获取GPRS IP地址
	public static String getLocalIpAddress()  
    {  
        try  
        {  
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)  
            {  
               NetworkInterface intf = en.nextElement();  
               for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)  
               {  
                   InetAddress inetAddress = enumIpAddr.nextElement();  
                   if (!inetAddress.isLoopbackAddress())  
                   {  
                       return inetAddress.getHostAddress().toString();  
                   }  
               }  
           }  
        }  
        catch (SocketException ex)  
        {  
            Log.e("WifiPreference IpAddress", ex.toString());  
            return "";
        }  
        return "";  
    }  
	
}
