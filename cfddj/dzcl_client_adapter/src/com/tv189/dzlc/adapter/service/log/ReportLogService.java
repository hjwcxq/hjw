package com.tv189.dzlc.adapter.service.log;

import java.io.IOException;
import java.util.Date;

import android.content.Context;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.util.FileUtils;
import com.tv189.dzlc.adapter.util.LogUtils;
import com.tv189.dzlc.adapter.util.NetworkUtils;
import com.tv189.dzlc.adapter.util.Utils;

public class ReportLogService {

	private String filename;
	
	private static ReportLogService instance;

	// private Handler mHandler;
	// private UploadLogFileRunnable mUploadLogFileRunnable;

	private boolean isFirst = true;

	public static ReportLogService getInstance() {
		if (instance == null) {
			instance = new ReportLogService();
		}
		return instance;
	}

	private ReportLogService() {
		// mHandler =new Handler();
		// mHandler.postDelayed(mUploadLogFileRunnable,0);
	}

	public void addLog(Context context, String accessType, String actionType,
			String actionDesc, String actionResult, String failCause,
			String partId, String contentId) {
		if (isFirst) {
			filename = "Log_" + Utils.getImsiid(context);
					
		}
		String imsiid = Utils.getImsiid(context);
		String ua = Utils.getWindowWH(context);
		String version = Utils.getVersion(context);
		String userName = AppSetting.getInstance(context).getUserName();
		String phoneNum =AppSetting.getInstance(context).getPhoneNumber();
		String nickName = AppSetting.getInstance(context).getNickName();
		String uid = AppSetting.getInstance(context).getUserId();
		String ip = NetworkUtils.getLocalIpAddress();
		String netType = NetworkUtils.getNetworkTypeString(context);

		Date actionTime = new Date();

		TyLogInfo info = new TyLogInfo(imsiid, userName, phoneNum, nickName,
				uid, actionType, actionTime, actionDesc, ua, accessType,
				actionResult, failCause, ip, version, netType, partId,
				contentId);

		try {
			if (FileUtils.getFileLength(context, filename) > 2 * 1024 * 1024) {
				// 改名
				String newName = filename + "_" + Utils.Date2String(new Date());
				FileUtils.reFileName(context, filename, newName);
				FileUtils.writeFile(context, filename, info.toString() + "\n");
			} else {
				FileUtils.writeFile(context, filename, info.toString() + "\n");
			}

		} catch (IOException e) {
			LogUtils.error(e.getMessage());
		}
	}

	// private class UploadLogFileRunnable implements Runnable{
	// private Context context;
	// private String exceptFileName;
	// private String imsiid;
	// public UploadLogFileRunnable(Context context,String exceptFileName,String
	// imsiid){
	// this.context = context;
	// this.exceptFileName = exceptFileName ;
	// this.imsiid = imsiid ;
	// }
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// //当前文件重命名，然后再上传所有log文件，再删除这些文件
	// //改名
	// String newName = exceptFileName+"_"+Utils.Date2String(new Date());
	// FileUtils.reFileName(context, exceptFileName , newName);
	//
	// File[] files = FileUtils.getFileLists(context);
	// ArrayList<FormFile> arr = new ArrayList<FormFile>();
	//
	// for(int i = 0 ; i < files.length ; i ++)
	// {
	// File uploadFile = files[i];
	// if(uploadFile.getName().startsWith("Log_") &&
	// !uploadFile.getName().equals(exceptFileName))
	// {
	// FormFile formfile =new FormFile(uploadFile.getName(), uploadFile, null,
	// null);
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("imsiid", imsiid);
	// try
	// {
	// String ret =HttpUtils.put(APIConfig.URL_UPLOADLOGFILE, params, formfile);
	// //删除文件
	// FileUtils.delFile(context,uploadFile.getName());
	// }
	// catch(Exception e)
	// {
	// e.printStackTrace();
	// LogUtils.error(e.getMessage());
	// }
	//
	// }
	// }
	// mHandler.postDelayed(mUploadLogFileRunnable,APIConfig.UPLOADLOGFILE_INTERVAL);
	//
	// }
	//
	// }

}
