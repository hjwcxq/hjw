package com.orientmedia.app.cfddj.ui.expert;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.service.EventHandler;
import com.orientmedia.app.cfddj.tool.StringUtil;
import com.orientmedia.base.BaseActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.ExpertsInfo;
import com.tv189.dzlc.adapter.service.impl.ExpertsServiceImpl;

public class QueueActivity extends BaseActivity implements EventHandler {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_queue);
		getData();
		initView();
		queueQuestionListener = new QueueQuestionListener(this, expertInfo);
		// new ConnectMM(this);
		// startVideoChat();

	}

	private void getData() {
		expertInfo = SystemContext.getInstance(this).getCurrentExpert();
		uid = AppSetting.getInstance(this).getUserId();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	private void initView() {
		// setTitle();
		onlineLayout = (LinearLayout) findViewById(R.id.onlineLayout);
		offlineLayout = (LinearLayout) findViewById(R.id.offlineLayout);
		videoFrameLayout = (RelativeLayout) findViewById(R.id.videoFrameLayout);
		online_waitnum = (TextView) findViewById(R.id.online_waitnum);
		if (!StringUtil.isEmpty(expertInfo.getStatus())
				&& !expertInfo.getStatus().equals(MainConst.ONLINE_STATUS)) {
			setMidLayout(View.VISIBLE, View.GONE, View.GONE);
		} else {
			setTextContent(online_waitnum, expertInfo.getMyQueueIndex() + "位");
		}
	}

	/****
	 * 请求视频聊天
	 */
	// public void requestVideoChat() {
	// // 发送视频聊天请求
	// if (firstRequest) {
	// new RequestVideoChatTask().execute();
	// } else {
	// new ExpertInfoTask().execute();
	// }
	// // 判断咨询时间是否结束
	// if ((expertInfo.getLastGuestUid() == null && startChat)
	// || (expertInfo.getLastGuestUid() != null
	// && !expertInfo.getLastGuestUid().equals(uid) && startChat)) {
	// // showToast("您的咨询时间已经结束");
	//
	// DisplayMetrics dm = new DisplayMetrics();
	// getWindowManager().getDefaultDisplay().getMetrics(dm);
	// int screenWidth = dm.widthPixels;
	// int screenHeight = dm.heightPixels;
	// if (screenHeight > screenWidth) {
	// new AlertDialog.Builder(QueueActivity.this)
	// .setTitle("提示信息")
	// .setMessage("您的咨询时间已经结束")
	// .setPositiveButton("确定",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// SystemContext.getInstance(
	// QueueActivity.this).setInit(
	// false);
	// finish();
	// }
	// }).create().show();
	// } else {
	// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	// }
	//
	// // finish();
	// }
	// // 能否开始视频聊天
	// if (expertInfo.getLastGuestUid() != null
	// && expertInfo.getLastGuestUid().equals(uid) && !startChat
	// && expertInfo.getFmsLiveStreamUrl() != null) {
	// startChat = true;
	// // startVideoChat();
	// }
	// // 专家掉线异常判断
	// if (startChat && expertInfo.getOnline().equals("false")) {
	// if (firstOfflineHint) {
	// firstOfflineHint = false;
	// expertOfflineHint();
	// }
	// } else {
	// firstOfflineHint = true;
	// }
	//
	// }

	/****
	 * 专家离线提醒.
	 */
	private void expertOfflineHint() {
		if (expertOfflineDialog != null && !expertOfflineDialog.isShowing()) {

			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			int screenWidth = dm.widthPixels;
			int screenHeight = dm.heightPixels;
			if (screenHeight > screenWidth) {
				new AlertDialog.Builder(QueueActivity.this)
						.setTitle("提示信息")
						.setMessage("您的咨询时间已经结束")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										SystemContext.getInstance(
												QueueActivity.this).setInit(
												false);
										finish();
									}
								}).create().show();
			} else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}

			AlertDialog.Builder expertOfflineHint = new AlertDialog.Builder(
					QueueActivity.this);
			expertOfflineHint.setTitle("提示");
			expertOfflineHint.setMessage("专家" + expertInfo.getExpertName()
					+ "掉线了！");
			expertOfflineHint.setPositiveButton("等待", null);
			expertOfflineHint.setNegativeButton("离开",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			expertOfflineDialog = expertOfflineHint.create();
			expertOfflineDialog.show();
		}
	}

	/****
	 * 
	 * 排队结束，开始视频聊天
	 * 
	 */
	// private void startVideoChat() {
	// expertVideo = new ExpertVideo(this, expertInfo.getFmsLiveStreamUrl());
	// setMidLayout(View.GONE, View.GONE, View.VISIBLE);
	// }

	private void initWaitNum() {
		if (checkNumChange()) {
			setTextContent(online_waitnum, expertInfo.getMyQueueIndex() + "位");
			startNumAnim();
			lastWaitNum = expertInfo.getMyQueueIndex();
		}
	}

	private boolean checkNumChange() {
		return lastWaitNum != expertInfo.getMyQueueIndex();
	}

	private void startNumAnim() {
		final Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.anim_waitnum);
		online_waitnum.startAnimation(anim);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
	}

	private void setTextContent(TextView tv, String content) {
		if (!StringUtil.isEmpty(content)) {
			tv.setText(content);
		}
	}

	private void setMidLayout(int offlineVisibility, int onlineVisbility,
			int videoFrameVisibility) {

		offlineLayout.setVisibility(offlineVisibility);
		onlineLayout.setVisibility(onlineVisbility);
		videoFrameLayout.setVisibility(videoFrameVisibility);
	}

	/***
	 * 设置标题
	 */
	// private void setTitle() {
	// if (!StringUtil.isEmpty(expertInfo.getExpertName())) {
	//
	// ((TextView) findViewById(R.id.title_tv)).setText(expertInfo
	// .getExpertName() + " 专家");
	// }
	// }

	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.backLayout:
	// finish();
	// break;
	//
	// default:
	// break;
	// }
	//
	// }

	@Override
	public void onMessage(String data) {

	}

	/****
	 * 客户端网络异常
	 */
	@Override
	public void netInfo(boolean netStatus) {
		if (netStatus) {
			showCusToast("网络已恢复");
		} else {
			new AlertDialog.Builder(this).setMessage("你的网络出问题了！")
					.setPositiveButton("去看看", null).create().show();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@SuppressWarnings("static-access")
	@Override
	public void onResume() {
		super.onResume();
		queueQuestionListener.startTimer();
//		myApp.ehList.add(this);
//		if (expertVideo != null) {
//			expertVideo.videoWebView.resumeTimers();
//			expertVideo.callHiddenWebViewMethod("onResume");
//		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void onPause() {
		super.onPause();
//		myApp.ehList.remove(this);
//		if (expertVideo != null) {
//			expertVideo.videoWebView.pauseTimers();
//			expertVideo.callHiddenWebViewMethod("onPause");
//		}
	}

	@Override
	public void onStop() {
		super.onStop();
		// queueQuestionListener.stopTimer();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		queueQuestionListener.stopTimer();
//		if (expertVideo != null) {
//			expertVideo.videoWebView.destroy();
//		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
//		if (arg0 == expertVideo.installFalshRequest) {
//			expertVideo.startCheck();
//		}
	}

	/****
	 * 获取专家详细信息的任务
	 * 
	 * @author hujunjing 2013-7-10
	 * 
	 */
	class ExpertInfoTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// showProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			ExpertsServiceImpl requestService = new ExpertsServiceImpl(
					QueueActivity.this);
			try {
				ExpertsInfo expertInfoT = requestService.expertsDetails(uid,
						expertInfo.getUid());
				if (expertInfoT != null) {
					expertInfo = expertInfoT;
					Log.e("---expertInfoT---!null-222-",
							"---expertInfoT---!null---waitnum-"
									+ expertInfoT.getMyQueueIndex());
					Log.e("---expertInfoT---!null-222-",
							"---expertInfoT---!null---getLastGuestId-"
									+ expertInfoT.getLastGuestUid());
					Log.e("---expertInfoT---!null-222-",
							"---expertInfoT---!null---uid-" + uid);
					return MainConst.SUCCESS;
				}
				Log.e("expertInfoT--222--null", "expertinfo---222-null");
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			return MainConst.ERROR;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// dismissProgressDialog();
			if (result.equals(MainConst.SUCCESS)) {
				initWaitNum();
			}

		}

	}

	/****
	 * 请求视频聊天
	 * 
	 * @author hujunjing 2013-7-10
	 * 
	 */
	// class RequestVideoChatTask extends AsyncTask<String, Void, String> {
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// // showProgressDialog();
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	// ExpertsServiceImpl requestService = new ExpertsServiceImpl(
	// QueueActivity.this);
	// try {
	// ExpertsInfo expertInfoT = requestService.requestvideochat(uid,
	// expertInfo.getUid());
	// if (expertInfoT != null) {
	// Log.e("---expertInfoT---!null--",
	// "---expertInfoT---!null---waitnum-"
	// + expertInfoT.getMyQueueIndex());
	// Log.e("---expertInfoT---!null--",
	// "---expertInfoT---!null---getLastGuestId-"
	// + expertInfoT.getLastGuestUid());
	// Log.e("---expertInfoT---!null--",
	// "---expertInfoT---!null---uid-" + uid);
	// expertInfo = expertInfoT;
	// return MyConstant.SUCCESS;
	// }
	// Log.e("expertInfoT----null", "expertinfo----null");
	// } catch (GplzServiceException e) {
	// Log.e("expertinfo----eeee", "expertinfo----eeee");
	// e.printStackTrace();
	// }
	// return MyConstant.ERROR;
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	// // dismissProgressDialog();
	// if (result.equals(MyConstant.SUCCESS)) {
	// firstRequest = false;
	// initWaitNum();
	// if (expertInfo.getLastGuestUid() == null
	// || !expertInfo.getLastGuestUid().equals(uid)) {
	// setMidLayout(View.GONE, View.VISIBLE, View.GONE);
	// }
	// }
	// }
	// }

	// private ExpertVideo expertVideo;
	public static ExpertsInfo expertInfo;
	private LinearLayout onlineLayout, offlineLayout;
	private RelativeLayout videoFrameLayout;
	private TextView online_waitnum;
	private QueueQuestionListener queueQuestionListener;
	private String uid;
	private boolean startChat = false;
	private boolean firstRequest = true;
	private int lastWaitNum = 1000;
	private Dialog expertOfflineDialog;
	private boolean firstOfflineHint = true;
}
