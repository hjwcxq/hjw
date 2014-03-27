package com.orientmedia.app.cfddj.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.tv189.dzlc.adapter.config.MainConst;


public class HttpUtil{
	public static String Tag = "HttpUtil_W3";
	public static String GET_METHOD = "GET";
	public static String POST_METHOD = "POST";
	public static int NET_WIFI = 1;
	public static int NET_MOBILENET = 2;
	public static int NET_NULL = 3;
	public static String URL_ENCODEING = "utf-8";
	
	/**
	 * 获取网络状态
	 * @param context
	 * @return
	 */
	public static int getNetWorkType(Context context) {
		try {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
			if (info == null) {
				return NET_NULL;
			}
			int netType = info.getType();
			if (netType == ConnectivityManager.TYPE_WIFI
				) {
				return NET_WIFI;
			} else if (netType == ConnectivityManager.TYPE_MOBILE
					|| netType == ConnectivityManager.TYPE_MOBILE_DUN
					|| netType == ConnectivityManager.TYPE_MOBILE_HIPRI
					|| netType == ConnectivityManager.TYPE_MOBILE_MMS
					|| netType == ConnectivityManager.TYPE_MOBILE_SUPL
					) {
				return NET_MOBILENET;
			} else {
				return NET_NULL;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return NET_NULL;
		}
		
	}
	
	/**
	 * 判断网络是否连接
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
//		return true;
		int netType = getNetWorkType(context);
		if (netType == NET_NULL) {
			return false;
		}else {
			return true;
		}
	}
	
//	/**
//	 * 带有网络错误提示的请求方法
//	 * @param uri
//	 * @param headerPropertys
//	 * @param par
//	 * @param setType
//	 * @return
//	 */
//	public static String getStringFromURLWithErrorHandler(String uri, 
//			Map<String, String> headerPropertys,
//			Map<String, String> par, String setType){
//		String returnString = null;
//		if(SystemContext.getInstance().getContext()!=null && HttpUtil.isNetworkAvailable(SystemContext.getInstance().getContext())){
//			try {
//				returnString = HttpUtil.getStringFromURL(uri
//						, HttpUtil.URL_ENCODEING
//						, headerPropertys
//						, par
//						, setType);
//			} catch (Exception e) {
//				
//			}
//		}
//		else {
//			CommonErrorHandler.getInstance().netError();
//		}
//		//若返回为null,则是网络错误
//		if (returnString == null || returnString.equalsIgnoreCase("")) {
//			CommonErrorHandler.getInstance().requestError();
//			Log.e(Tag, "return:null");
//		}else {
//			Log.e(Tag, "return:" + returnString);
//		}
//		return returnString;
//	}
	
	
	
	
	
	/**
	 * 访问url，获取返回string,默认参数进行utf-8编码
	 * @param uri
	 * @param encoding
	 * @param par
	 * @param setType
	 * @return
	 */
	public static String getStringFromURL(String uri, 
			String encoding,
			Map<String, String> par, String setType) {
		 return getStringFromURL(uri,encoding,null,par,setType);
	}
	
	/**
	 * 访问url，获取返回string,默认参数进行utf-8编码
	 * @param uri
	 * @param encoding
	 * @param propertys
	 * @param par
	 * @param setType
	 * @return
	 */
	public static String getStringFromURL(String uri, 
										String encoding,
										Map<String, String> headerPropertys,
										Map<String, String> par, String setType) {
		
		StringBuffer buffer = new StringBuffer();
		HttpURLConnection uc = null;
		InputStream content = null;
		String parString = "";
		try {
			//将par解析成parString字符串
			if (par != null && par.size() > 0) {			
				for(Iterator<String> it = par.keySet().iterator(); it.hasNext(); ) {
					String key = (String)it.next();
					if (parString.equalsIgnoreCase("")) {
						try {
							parString = key.toLowerCase() + "=" + URLEncoder.encode(par.get(key), "utf-8");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else {						
						try {
							parString =parString + "&" + key.toLowerCase() + "=" + URLEncoder.encode(par.get(key), "utf-8");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} 
			}
			
			
			boolean isGet = false;
			if(setType == null || setType.toUpperCase().equalsIgnoreCase(GET_METHOD)){
				isGet = true;
				if (parString != null && !parString.equalsIgnoreCase("")) {
					uri = uri + "?" + parString;
				}
			}
			
			uc = getHttpURLConnection(uri);
			if(!isGet && setType != null && !setType.equals(""))
				uc.setRequestMethod(setType.toUpperCase());
			uc.setConnectTimeout(30 * 1000);
			uc.setDoInput(true);
			if ("post".equalsIgnoreCase(setType)) {
				uc.setDoOutput(true);
			}
			uc.setUseCaches(false);			
			//增加头属性property
			if (headerPropertys != null && headerPropertys.size() > 0) {
				if(!headerPropertys.containsKey("channelid")){
					headerPropertys.put("channelid", MainConst.CHANNELID);
				}
				String[] keysStrings = headerPropertys.keySet().toArray(new String[]{});
				for (String key:keysStrings) {
					try {

						if(headerPropertys.get(key) !=null)
						{
							uc.addRequestProperty(key.toLowerCase(), URLEncoder.encode(headerPropertys.get(key),"utf-8"));
						}
						else
						{
							uc.addRequestProperty(key.toLowerCase(), "");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}	
			if(!isGet){
				if (parString != null && !parString.equalsIgnoreCase("")) {
					uc.getOutputStream().write(parString.getBytes());
					uc.getOutputStream().flush();
					uc.getOutputStream().close();
				}	
			}
			
			uc.connect();
			Log.e(Tag, "getContentType() : " + uc.getContentType());
			Log.e(Tag, "getContentEncoding() : " + uc.getContentEncoding());
			Log.e(Tag, "getResponseCode() : " + uc.getResponseCode());
			Log.e(Tag, "getResponseMessage() : " + uc.getResponseMessage());
			
			
			try {
				if (uc.getResponseCode() == 200) {
					if(uc.getContent() != null)
						content = (InputStream)uc.getContent();
					else
						content = uc.getInputStream();
					
					BufferedReader in = new BufferedReader(new InputStreamReader(content, encoding));
					
					String line;
					while ((line = in.readLine()) != null) {
						buffer.append(line);
					}
					String result = buffer.toString().trim();
					return result;
				}else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}	
			
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally{
			try{
				if (content != null) {
					content.close();
				}
				if (uc != null) {
					uc.disconnect();
				}
			}
			catch(Exception exClose){
				exClose.printStackTrace();
			}
		}
	}
	 
	/**
	 * 获取有代理参数的HttpUrlConnection
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	 public static HttpURLConnection getHttpURLConnection(String url) throws MalformedURLException, IOException{ 
		return (HttpURLConnection) new URL(url).openConnection(); 
//		try{
//			String proxyHost = android.net.Proxy.getDefaultHost(); 
//			if (proxyHost != null) { 
//				java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP, 
//				new InetSocketAddress(android.net.Proxy.getDefaultHost(), 
//				android.net.Proxy.getDefaultPort())); 		
//				return (HttpURLConnection) new URL(url).openConnection(p); 
//	
//			} 
//			else { 
//				return (HttpURLConnection) new URL(url).openConnection(); 
//			} 
//		}
//		catch(Exception ex){
//			return (HttpURLConnection) new URL(url).openConnection();
//		}
	}
}
