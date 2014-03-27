package com.orientmedia.app.cfddj.module.action;

import java.util.Map;

import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.ui.TestActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.opengl.GLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.tv189.dzlc.adapter.po.base.AbstractAction;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;
import com.tv189.dzlc.adapter.service.impl.VideoServiceImpl;

public class VideoAction extends AbstractAction {

	private Context context;

	public VideoAction(Context context) {
		this.context = context;
	}

	@Override
	public void jumpByActionType(Map<String, String> paraMap) {
		// TODO Auto-generated method stub
		String url = getUrl();
		if (url != null) {
			String video_id = url.toString().replace("[", "").replace("]", "");// 获取视频地址
			new playVideoTask(context).execute(new String[] { video_id });
		}
	}

	/**
	 * 获取视频详细信息并跳转
	 * 
	 * @author EsaFans
	 * 
	 */
	class playVideoTask extends AsyncTask<String, Void, String> {
		private Context context;
		String SUCCESS = "success";
		String ERROR = "error";
		ProgressDialog progressDialog;
		String id = "";

		private VideoDetails video;

		public playVideoTask(Context cont) {
			this.context = cont;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				progressDialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (SUCCESS.equalsIgnoreCase(result)) {
				Intent intent = new Intent();
				intent.setClass(context, TestActivity.class);
				TestActivity.mVideoDetails = video;
				context.startActivity(intent);
			}
		}

		@Override
		protected String doInBackground(String... params) {
			id = params[0];
			if (id != null) {
				try {
					VideoServiceImpl requestService = new VideoServiceImpl(
							context);
					video = requestService.videoDetails(id);
					if (video != null) {
						return SUCCESS;
					}
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					ExcepUtils.showImpressiveException((Activity) context,
							null, e);
				}
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			try {
				progressDialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("正在获取视频信息并启动");
			progressDialog.setCancelable(true);
			progressDialog.show();
		}
	}

}
