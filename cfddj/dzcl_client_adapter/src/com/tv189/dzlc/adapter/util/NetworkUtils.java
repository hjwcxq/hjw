package com.tv189.dzlc.adapter.util;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
 * 网络
 */
public class NetworkUtils
{
	public static int NETTYPE_NONET = 0 ;
	public static int NETTYPE_WIFI = 1 ;
	public static int NETTYPE_CMWAP = 2 ;
	public static int NETTYPE_CMNET = 3 ;
	
	
    /*
     * Returns whether the network is available,当前网络是否存在
     * @return true is Available; false is not  available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
        {
            LogUtils.error("couldn't get connectivity manager");
            return false ;
        }
        else
        {
        	//NetworkInfo ni = cm.getActiveNetworkInfo();
            //return ni != null && ni.isConnectedOrConnecting();
        	NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null){
                for (int i = 0; i < info.length; i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
            return false ;
        }
    }
    
    /**
     * 获取当前网络类型
     * @return  0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
    	
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (extraInfo!=null && !extraInfo.equals("")) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;

                } else {

                    netType = NETTYPE_CMWAP;

                }

            }

        } else if (nType == ConnectivityManager.TYPE_WIFI) {

            netType = NETTYPE_WIFI;

        }

        return netType;

    }
    
    public static String getNetworkTypeString(Context context)
    {
    	int netType = getNetworkType(context);
    	String str ="NONET";
    	switch (netType)
    	{
    		case 0:
    			str = "NONET";
    			break ;
    		case 1:
    			str = "WIFI";
    			break ;
    			
    		case 2:
    			str ="WAP";
    			break;
    			
    		case 3:
    			str ="NET" ;
    			break ;
    	}
    	return str ;
    }
    
    
    /*
     * 获得本地ip地址
     */
    public static String getLocalIpAddress() {  
        try {  
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {  
                NetworkInterface intf = en.nextElement();  
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                    InetAddress inetAddress = enumIpAddr.nextElement();  
                    if (!inetAddress.isLoopbackAddress()) {  
                        return inetAddress.getHostAddress().toString();  
                    }  
                }  
            }  
        } catch (SocketException ex) {  
        	LogUtils.error(ex.getMessage());
        }  
        return null;  
    } 



}
