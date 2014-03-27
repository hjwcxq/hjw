package com.tv189.dzlc.adapter.thread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import mobi.dreambox.frameowrk.core.util.StringUtil;
import android.content.Context;

import com.google.gson.Gson;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.factory.ClientLogFactory;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;


public class GplzClientLogUploadThread extends Thread{
	private Context context;
	private int bufferSizeUpload = 1024*64;
	private boolean runningStatus = false;
	//运行周期，默认为1秒
	private long runningPeriod = 60*1000;
	public GplzClientLogUploadThread(Context context,long runningPeriod){
		this.runningPeriod = runningPeriod;
		this.context = context;
	} 
	@Override
	public void run() {
		while(true){
			if(runningStatus){
				List<File> uplodLogFileList = ClientLogFactory.getInstance().generateUploadFile();
				if(uplodLogFileList.size()>0){
					for(File logFile : uplodLogFileList){
						uplodFile(logFile);
					}
				}
			}
			this.waitingPeriod();
		}
		
	}
	
	private void waitingPeriod(){
		try {
			Thread.sleep(this.runningPeriod);
		} catch (InterruptedException e) {
		}
	}
	
	public void startThread(){
		this.runningStatus = true;
		this.start();
	}
	public void stopThread(){
		this.runningStatus = false;
	}
	
	private void uplodFile(File logFile) {
		Map<String, String> headerMap = null ;
		try {
			headerMap = AppSetting.getInstance(context).getCommonHeaderMap(false);
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String respString = null;
		HttpURLConnection conn = null;
		BufferedInputStream fin = null;
		BufferedOutputStream out = null;
		URL reqUrl;
		try {
			reqUrl = new URL(DzlcAndroidConfig.URL_UPLOADLOGFILE);
			conn = (HttpURLConnection) reqUrl.openConnection();
			conn.setConnectTimeout(3000);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "binary/octet-stream");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {  
				   String key = entry.getKey().toString();  
				   String value = entry.getValue().toString();  
				   if(value!=null&&!value.equals(""))
				   conn.addRequestProperty(key, URLEncoder.encode(value));
			 } 
			// 1M的chunk缓冲
			conn.setChunkedStreamingMode(1024*1024);
			out = new BufferedOutputStream(conn.getOutputStream());
			fin = new BufferedInputStream(new FileInputStream(logFile));
			byte[] buf = new byte[bufferSizeUpload];
			int len = -1;
			while ((len = fin.read(buf)) != -1) {
				out.write(buf, 0, len);
				out.flush();
			}
			respString = StringUtil.toString(conn.getInputStream());
			ApiResponse respObj = new Gson().fromJson(respString, ApiResponse.class);
			if(respObj.isSuccess())
				logFile.delete();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			try {
				if (fin != null) {
					fin.close();
					fin = null;
				}
				if (out != null) {
					out.close();
					out = null;
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw new RuntimeException("Release resource failed.");
			}
		}
	}
}
