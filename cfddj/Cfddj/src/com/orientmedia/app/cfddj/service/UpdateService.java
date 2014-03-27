package com.orientmedia.app.cfddj.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.tool.FileOperator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.widget.RemoteViews;

//import com.ct.sx.android.R;
//import com.ct.sx.android.config.APIConfig;
//import com.ct.sx.android.service.CommonErrorHandler;

public class UpdateService extends Service {

	private NotificationManager notificationManager;
	private Notification notification;
	private Notification spNotification;
	private String fileName = "";
	private int mFileSize;
	private int mFileSizeKb;
	private static final String TAG = "UpdateService";
	private final int DOWNLOAD_READY = 0x1;
	private final int DOWNLOAD_RUNNING = 0x2;
	private final int DOWNLOAD_FINISHED = 0x3;
	private final int DOWNLOAD_ERROR = 0x4;
	private static final int NOTIFICATION_ID = 0x8;
	private int mProgress = 0;
	private double mInterval = 0;
	private DownloadHandler mDownloadHandler;
	private String tipReady;
	private String tipRunning;
	private String tipError;
//	private String installTitle;
//	private String installTip;
	public static String INSTALL_APK_URL = "";
	public static boolean isForced = false;
	public static boolean serviceRunning = false;
//	private String dirName="Laoz";
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.e("UpdateService", "onStart");
	}
	
	@Override
	public void onCreate() {
		
//		DaoUtil.getInstance().addUpdateTbInfo(newestVersion, newestMsg, apkUrl, shouldForceUpdate)
		Log.e("UpdateService", "onCreate");
		UpdateService.serviceRunning = true;
		initialWidgets();
		notification = new Notification(android.R.drawable.stat_sys_download,
				"", System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView = new RemoteViews(getPackageName(),
				R.layout.notification_gpzl);
		notification.contentView.setImageViewResource(
				R.id.notification_imageview,
				android.R.drawable.stat_sys_download);
		notification.contentIntent = PendingIntent
				.getActivity(this, 0, new Intent(), 0);

		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mDownloadHandler = new DownloadHandler();

		//get apkURL
//		INSTALL_APK_URL = HttpUtil.getApkUrl(getApplicationContext());
		if (INSTALL_APK_URL == null || !INSTALL_APK_URL.contains("http://") || !INSTALL_APK_URL.contains(".apk")) {
			return;
		}
		
		
		HttpThread thread = new HttpThread(mDownloadHandler);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();

	}

	private void initialWidgets(){
		tipReady = getResources().getString(R.string.app_name) + " 正在准备下载...";
		tipRunning = getResources().getString(R.string.app_name) + " 已下载：";
		tipError = getResources().getString(R.string.app_name) + " 下载失败，请尝试重新检查更新！";
//		installTitle = "安装" + getResources().getString(R.string.app_name);
//		installTip = getResources().getString(R.string.app_name) + " 下载完毕，点击进行安装";
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private final IBinder mBinder = new Binder() {
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,
				int flags) throws RemoteException {
			return super.onTransact(code, data, reply, flags);
		}
	};

	@Override
	public void onDestroy() {
		notificationManager.cancel(NOTIFICATION_ID);
//		Log.v(TAG, "-------Service(service=" + TAG + ") on Destroy!");
		Log.e("UpdateService", "onDestroy");
		super.onDestroy();
	}

	private class DownloadHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (!Thread.currentThread().isInterrupted()) {//如果线程没有中断
				switch (msg.what) {
					case DOWNLOAD_READY: 
						Log.v(TAG, "------DownloadHandler:DOWNLOAD_READY!");
//						notification.contentView.setProgressBar(
//								R.id.notification_pb, mFileSize, mProgress, false);
						notification.contentView.setTextViewText(
								R.id.notification_textview, tipReady);
						notificationManager.notify(NOTIFICATION_ID, notification);
						break;
					case DOWNLOAD_RUNNING:
						int progress = (int) Math.floor(mProgress / 1024);
						notification.contentView.setProgressBar(
								R.id.notification_pb, mFileSize, mProgress, false);
						notification.contentView.setTextViewText(
								R.id.notification_textview, tipRunning + progress
										+ "/" + mFileSizeKb + " KB");
						notificationManager.notify(NOTIFICATION_ID, notification);
						break;
					case DOWNLOAD_FINISHED: //通过Intent机制，调出系统安装应用，重新安装应用的话，会保留原应用的数据。
						Log.v(TAG, "------DownloadHandler:DOWNLOAD_FINISHED!");
						notificationManager.cancel(NOTIFICATION_ID);
						
						Uri uri = Uri.fromFile(new File(FileOperator.getCacheApkFile()+File.separator+fileName));
						Intent intent = new Intent(Intent.ACTION_VIEW); 
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setDataAndType(uri,"application/vnd.android.package-archive");  
						startActivity(intent);  
	
						break;
					case DOWNLOAD_ERROR: 
						Log.v(TAG, "------DownloadHandler:DOWNLOAD_ERROR!");
						notificationManager.cancel(NOTIFICATION_ID);
						spNotification = new Notification(
								android.R.drawable.stat_sys_warning, "",
								System.currentTimeMillis());
						spNotification.flags = Notification.FLAG_AUTO_CANCEL;
						spNotification.contentIntent = PendingIntent.getActivity(
								UpdateService.this, 0, new Intent(), 0);
						spNotification.contentView = new RemoteViews(
								getPackageName(), R.layout.notification_gpzl);
						spNotification.contentView.setProgressBar(
								R.id.notification_pb, mFileSize, mProgress, false);
						spNotification.contentView.setTextViewText(
								R.id.notification_textview, tipError);
						spNotification.contentView.setImageViewResource(
								R.id.notification_imageview,
								android.R.drawable.stat_sys_warning);
						notificationManager.notify(NOTIFICATION_ID, spNotification);
						try {
							new File(FileOperator.getCacheApkFile()+File.separator+fileName).delete();
						} catch (Exception e) {
						}
						break;
				}
			}
		}
	};

	class HttpThread extends Thread {
		private Handler mHandler;

		public HttpThread(Handler handler) {
			mHandler = handler;
		}

		private void sendMsg(int flag) {
			Message msg = new Message();
			msg.what = flag;
			mHandler.sendMessage(msg);
		}

		@Override
		public void run() {
			try {
				URL sourceUrl;
				try {
					sendMsg(DOWNLOAD_READY);
					sourceUrl = new URL(INSTALL_APK_URL);
					fileName = sourceUrl.getFile();
					fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
					File fileDir = new File(FileOperator.getCacheApkFile() , fileName);
					
					if (fileDir.exists()) {
						fileDir.delete();
					}
//					Log.v(TAG, "------File(file=" + fileName + ")");
					FileOutputStream fos = new FileOutputStream(fileDir);
					int read = 0;
					byte[] buffer = new byte[32];
					HttpURLConnection conn = (HttpURLConnection) sourceUrl
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setDoInput(true);
					conn.connect();
					mFileSize = conn.getContentLength();
//					Log.v(TAG, "------File(fileZize=" + mFileSize + ")");
					if (mFileSize == -1) {
						sendMsg(DOWNLOAD_ERROR);
						return;
					}
					mFileSizeKb = (int) Math.floor(mFileSize / 1024);
					mInterval = Math.floor(mFileSize / 10);
//					sendMsg(DOWNLOAD_READY);
					InputStream is = conn.getInputStream();
					final long startTime = System.currentTimeMillis();
					mProgress = 0;
					int level = 0;
					do {
						read = is.read(buffer);
						if (read > 0) {
							fos.write(buffer, 0, read);
							mProgress += read;
							if (mProgress > level || mProgress == 0) {
								sendMsg(DOWNLOAD_RUNNING);
								level += mInterval;
								Log.v(TAG, "----Reader:level" + level);
							}

						}
					} while (read != -1);
					sendMsg(DOWNLOAD_FINISHED);
					fos.close();
					is.close();
					conn.disconnect();
					mInterval = 0;
					Log.d(TAG,
							"-----load apk package data took "
									+ (System.currentTimeMillis() - startTime)
									+ "ms");

					if (mProgress != mFileSize) {
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMsg(DOWNLOAD_ERROR);
					return;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				UpdateService.serviceRunning = false;
				UpdateService.this.stopSelf();
			}
		}
	}
}