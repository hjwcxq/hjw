package com.tv189.dzlc.adapter.factory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.po.log.AbstractLogInfo;


/**
 * 线程上传类
 * 
 * @author PC
 * 
 */
public class ClientLogFactory  {
	
	private static ClientLogFactory instance;
	private static final String SYNC_KEY="SYNC_KEY";
	private File tmpLogFile;
	private ClientLogFactory(){
		
	}
	public static ClientLogFactory getInstance(){
		synchronized (SYNC_KEY) {
			if(instance==null){
				instance = new ClientLogFactory();
			}
			return instance;
		}
	}
	
	/**
	 * 写入日志
	 * @param logObj 写入内容
	 * @throws IOException
	 */
	public  void addLog(AbstractLogInfo logObj) {
		synchronized (SYNC_KEY) {
			FileWriter fw = null;
			try {
				fw = new FileWriter(getTmpLigFile(),true);
				fw.append(logObj.toString()+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//刷新并关闭
				try {
					if(fw!=null){
						fw.flush();
						fw.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
	
	
	
	private File getTmpLigFile(){
		File logFolder = DzlcAndroidConfig.getClientLogFolder();
		if(tmpLogFile==null||!tmpLogFile.exists()){
			
			File[] logFileList = logFolder.listFiles();
			if(logFileList!=null&&logFileList.length>0){
				for(int i=0;i<logFileList.length;i++){
					File tmpFile = logFileList[i];
					String tmpFileName = tmpFile.getName();
					if(tmpFileName.endsWith(".log.tmp")){
						tmpLogFile = tmpFile;
					}
				}
			}
		}
		if(tmpLogFile==null||!tmpLogFile.exists()){
			SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmSSS"); 
			tmpLogFile = new File(logFolder.getAbsoluteFile()+File.separator+SDF.format(Calendar.getInstance().getTime())+".log.tmp");
			try {
				tmpLogFile.createNewFile();
			} catch (IOException e) {
				
			}
		}
		
		return tmpLogFile;
			
	}

	public List<File> generateUploadFile(){
		synchronized (SYNC_KEY) {
			List<File> uploadLogFileList = new ArrayList<File>();
			File logFolder = DzlcAndroidConfig.getClientLogFolder();
			File[] logFileList = logFolder.listFiles();
			if(logFileList.length>0){
				for(int i=0;i<logFileList.length;i++){
					File tmpFile = logFileList[i];
					String tmpFileName = tmpFile.getName();
					if(tmpFileName.endsWith(".log.tmp")){
						String srcFilePath = tmpFile.getAbsolutePath();
						String destFilePath = srcFilePath.substring(0,srcFilePath.lastIndexOf(".tmp"));
						File destFile = new File(destFilePath);
						tmpFile.renameTo(destFile);
						if(destFile.exists())
							uploadLogFileList.add(destFile);
					}else if(tmpFileName.endsWith(".log")){
						uploadLogFileList.add(tmpFile);
					}else{
						tmpFile.delete();
					}
				}
			}
			return uploadLogFileList;
		}
	}
	
}