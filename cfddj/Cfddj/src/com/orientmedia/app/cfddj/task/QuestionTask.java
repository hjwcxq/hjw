package com.orientmedia.app.cfddj.task;

import java.io.File;

import com.orientmedia.app.cfddj.Listener.CallBackListener;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.base.BaseActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.question.QuestionInfo;
import com.tv189.dzlc.adapter.service.impl.QuestionServiceImpl;
import com.tv189.dzlc.adapter.service.inerface.QuestionService;

public class QuestionTask {

	private Context act;

	public QuestionTask(Context mContext) {
		this.act = mContext;
	}

	/**
	 * 发送短信
	 * 
	 * @param sentMsg
	 * @param showListener
	 * @param callBack
	 */
	public void sendMsg(String sentMsg, ShowLoadingTipListener showListener,
			CallBackListener callBack) {
		new SubmitMyMessageTask(showListener, callBack).execute(sentMsg);
	}
	/**
	 * 发送语音
	 * 
	 * @param sentMsg
	 * @param showListener
	 * @param callBack
	 */
	public void sendVoiceMsg(File file, ShowLoadingTipListener showListener,
			CallBackListener callBack) {
		new SaveRecordAsyncTask(showListener, callBack).execute(file);
	}

	/**
	 * 发送我的留言
	 * 
	 * @author EsaFans
	 */

	class SubmitMyMessageTask extends AsyncTask<String, Void, String> {

		private ShowLoadingTipListener showListener;

		private CallBackListener callBack;

		public SubmitMyMessageTask(ShowLoadingTipListener showListener,
				CallBackListener callBack) {
			this.showListener = showListener;
			this.callBack = callBack;
		}

		@Override
		protected void onPreExecute() {
			showListener.onShowLoadingTip("发送中...");
		}

		@Override
		protected String doInBackground(String... params) {
			QuestionService questionService = new QuestionServiceImpl(act);
			QuestionInfo question;
			try {
				question = questionService.questionAdd("2", "3", params[0]);
				return question == null ? null : question.getIsPass();
			} catch (ServiceException e) {
				ExcepUtils.showImpressiveException((Activity) act, null, e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			showListener.onHideLoadingTip();
			if (result != null) {
				if ("1".equalsIgnoreCase(result)) {
					((BaseActivity) act).showCusToast("提交成功");
					callBack.executeSucc();
				} else if ("0".equalsIgnoreCase(result)) {
					((BaseActivity) act).showCusToast("提交成功，您的发言将在通过审核后显示");
					callBack.executeSucc();
				} else {
					((BaseActivity) act).showCusToast("提交失败");
					callBack.executeFail();
				}
			}else{
				((BaseActivity) act).showCusToast("提交失败");
				callBack.executeFail();
			}
		}
	}

	/*
	 * 发送语音
	 */
	class SaveRecordAsyncTask extends AsyncTask<File, Void, String> {
		private ShowLoadingTipListener showListener;

		private CallBackListener callBack;

		public SaveRecordAsyncTask(ShowLoadingTipListener showListener,
				CallBackListener callBack) {
			this.showListener = showListener;
			this.callBack = callBack;
		}

		@Override
		protected void onPreExecute() {
			showListener.onShowLoadingTip("发送中...");
		}

		@Override
		protected String doInBackground(File... params) {
			QuestionService questionService = new QuestionServiceImpl(act);
			QuestionInfo uploadVoice = null;
			try {
				uploadVoice = questionService.upload(params[0]);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException((Activity) act, null, e);
			}
			return uploadVoice == null ? null : uploadVoice.getIsPass();

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			showListener.onHideLoadingTip();
			if ("1".equalsIgnoreCase(result)) {
				((BaseActivity) act).showCusToast("提交成功");
				callBack.executeSucc();
			} else if ("0".equalsIgnoreCase(result)) {
				((BaseActivity) act).showCusToast("提交成功，您的发言将在通过审核后显示");
				callBack.executeSucc();
			} else {
				((BaseActivity) act).showCusToast("提交失败");
				callBack.executeFail();
			}
		}

	}

}
