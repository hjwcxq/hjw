package com.orientmedia.app.cfddj.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.orientmedia.app.cfddj.tool.FileOperator;

import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;

/**
 * 下载视频的 线程
 * @author mac
 *
 */
public class VideoDownloader extends Thread {

	private VideoDetails videoDetails ;

	public VideoDownloader(VideoDetails videoDetails) {
		this.videoDetails = videoDetails ;
	}

	@Override
	public void run() {
		String newFilename = videoDetails.getTitle() + ".3gp";
		
		File file = new File(FileOperator.geCacheVideoFile(),newFilename);
		
		// 如果目标文件已经存在，则删除。产生覆盖旧文件的效果
		if (file.exists()) {
			
		} else {
			try {
				// 构造URL
				URL url = new URL(videoDetails.getPlayUrl());
				// 打开连接
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				// 获得文件的长度
				int contentLength = con.getContentLength();
				con.getInputStream();
				// 输入流
				InputStream is = con.getInputStream();
				// 1K的数据缓冲
				byte[] bs = new byte[1024 * 128];
				// 读取到的数据长度
				int len;
				// 输出的文件流
				OutputStream os = new FileOutputStream(file);
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
		}
	}
}
