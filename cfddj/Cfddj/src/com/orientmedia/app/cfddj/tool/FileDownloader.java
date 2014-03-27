package com.orientmedia.app.cfddj.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class FileDownloader extends Thread {
	String _urlStr;// 要下载的路径
	String _dirName;// 要保存的目标文件夹
	Handler handler;
	public FileDownloader(String urlStr, String dirName,Handler handler) {
		_urlStr = urlStr;
		_dirName = dirName;
		this.handler = handler;
	}

	@Override
	public void run() {
		// 准备拼接新的文件名（保存在存储卡后的文件名）
		// String newFilename = _urlStr.substring(_urlStr.lastIndexOf("/") + 1);
		//
		String newFilename = "myvoice.m4a";
		newFilename = _dirName + File.separator + newFilename;
		File file = new File(newFilename);
		// 如果目标文件已经存在，则删除。产生覆盖旧文件的效果
		if (file.exists()) {
			file.delete();
		}
		try {
			// 构造URL
			URL url = new URL(_urlStr);
			// 打开连接
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			System.out.println("长度 :" + contentLength);
			con.getInputStream();
			// 输入流
			InputStream is = con.getInputStream();

			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(newFilename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		Message m = new Message();
		m.arg1=1;
		handler.sendMessage(m);
	}
}
