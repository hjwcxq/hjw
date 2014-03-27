package com.orientmedia.app.cfddj.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.orientmedia.app.cfddj.Listener.CallBackListener;
import com.orientmedia.app.cfddj.tool.FileOperator;
import android.os.AsyncTask;

public class FileDownTask {
	
	
	public static final String IMAGE_TYPE = "image";
	
	public static final String VOICE_TYPE = "voice";
	
	public static final String XML_TYPE = "xml";
	
	
	
	public void downloadFile(String fileType,String urlString,CallBackListener call){
		new DownLoadFile(fileType, urlString, call).execute("");
	}
	
	
	private class DownLoadFile extends AsyncTask<String, Void, String>{

		private String fileType ;
		
		private String urlString ;
		
		private CallBackListener callback ;
		
		String SUCCESS = "success" ;
		
		private File file ;
		
		
		public DownLoadFile(String fileType,String urlString,CallBackListener call){
			this.fileType = fileType ;
			this.callback = call ;
			this.urlString = urlString;
		}
		
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			FileOutputStream fos = null ;
			InputStream is = null ;
			file = FileOperator.getFileByUrl(urlString, fileType);
			if(file == null)
				return null;
			
			try {
				URL url = new URL(urlString);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				is = con.getInputStream();
				byte[] bs = new byte[32*1024];
				// 读取到的数据长度
				int len = -1;
				fos = new FileOutputStream(file);
				// 开始读取
				while ((len = is.read(bs)) != -1) {
					fos.write(bs, 0, len);
					fos.flush();
				}
				return SUCCESS ;
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					fos.close();
					is.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					fos = null ;
					is = null;
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(SUCCESS.equalsIgnoreCase(result)){
				if(XML_TYPE.equalsIgnoreCase(fileType)){
					File xmlFolder = FileOperator.getCacheIndexXmlFile();
					File xmlFile = new File(xmlFolder, "index.xml");
					if(xmlFile.exists()){
						xmlFile.delete() ;
					}
					file.renameTo(xmlFile);
				}
				if(callback != null )
					callback.executeSucc();
			}else{
				if(callback != null )
					callback.executeFail();
			}
			
			
		}
		
	}
}
