//package tv.aniu.app.dzlc.task;
//
//import tv.aniu.app.dzlc.tool.ExcepUtils;
//import tv.aniu.app.dzlc.ui.find.VideoIntroActivity;
//import tv.aniu.app.dzlc.ui.find.video.BaikeFragment;
//import tv.aniu.app.dzlc.ui.find.video.BaikeFragment.playVideoTask;
//
//import com.tv189.dzlc.adapter.po.common.ServiceException;
//import com.tv189.dzlc.adapter.po.common.TokenException;
//import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
//import com.tv189.dzlc.adapter.service.impl.VideoServiceImpl;
//import com.tv189.dzlc.adapter.service.inerface.IUserAccountService;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.opengl.GLException;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//
//public class PlayVideoTask {
//	
//	private Context mContext ;
//	
//	public PlayVideoTask(Context cont){
//		this.mContext = cont ;
//	}
//	
//	
//	
//	
//	/**
//	 * 获取视频详细信息并跳转
//	 * 
//	 * @author EsaFans
//	 * 
//	 */
//	class playVideoTask extends AsyncTask<String, Void, String> {
//
//		String SUCCESS = "success";
//		String ERROR = "error";
//		ProgressDialog progressDialog;
//		String id = "";
//
//		@Override
//		protected void onPostExecute(String result) {
//			try {
//				progressDialog.dismiss();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			if (result.equalsIgnoreCase(SUCCESS)) {
//				// log
//				// 处理结果(0：成功，1：失败)
//				String actionResult = "0";
//				// 处理失败原因
//				String failCause = null;
//				final String rtspUrl = videoDetail.getPlayUrl();
//				final String videoTital = videoDetail.getTitle();
//				final String videoLength = videoDetail.getLength();
//				Log.e("-----rtspUrl-----", "-----rtspUrl------" + rtspUrl);
//				if (rtspUrl != null) {
//					try {
//						if (true) {
//							Uri uri = Uri.parse(rtspUrl);
//							// http://vod02.v.vnet.mobi/mobi/vod/st02/2013/11/20/Q600_2010501380.3gp?spid=&sid=32153441&msisdn=18101649867&timestamp=20131120171715&H=0009&channelid=01111621&nodeid=&videotype=2&encrypt=0ba4d8099763af65fad71f7467ada9f3&ua=30&imsi88888888888
//							Log.i("dzcj", "播放地址：" + rtspUrl);
//							// Intent intent = new Intent(Intent.ACTION_VIEW);
//							// String type = "video/3gpp";
//							// intent.setDataAndType(uri, type);
//							// startActivity(intent);
//							Intent intent = new Intent();
//							intent.setClass(BaikeFragment.this.getActivity(),
//									VideoIntroActivity.class);
//							Bundle videobundle = new Bundle();
//							videobundle.putCharSequence("vuri", uri.toString());
//							videobundle.putCharSequence("vtital", videoTital);
//							videobundle.putCharSequence("vlength", videoLength);
//							videobundle.putCharSequence("vintro", videoIntro);
//							intent.putExtras(videobundle);
//							startActivity(intent);
//
//						}
//					} catch (GLException e) {
//						Log.e("e----", "e----" + e.getMessage());
//						e.printStackTrace();
//						showCusToast("无法处理当前媒体");
//					}
//				} else {
//					showCusToast("无法播放，地址为空");
//				}
//
//			}
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			Log.e("-----doInBackground-----", "-----doInBackground------");
//			try {
//				id = params[0];
//				if (id != null) {
//					VideoServiceImpl requestService = new VideoServiceImpl(
//							BaikeFragment.this.getActivity());
//					videoDetail = requestService.videoDetails(id);
//					Log.e("id---", "id---" + id);
//					if (videoDetail == null) {
//						Log.e("----videoDetail----null",
//								"----videoDetail----null");
//					} else {
//						Log.e("----videoDetail--not--null",
//								"----videoDetail-not---null");
//					}
//				} else {
//					return ERROR;
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//
//			return SUCCESS;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			try {
//				progressDialog.dismiss();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			progressDialog = new ProgressDialog(BaikeFragment.this.getActivity());
//			progressDialog.setMessage("正在获取视频信息并启动");
//			progressDialog.setCancelable(false);
//			progressDialog.show();
//		}
//	}
//
//	class PayMoneyTask extends AsyncTask<String, Void, String> {
//		String isvip = "1";
//		String notvip = "2";
//		ProgressDialog progressDialog;
//		private String id;
//
//		@Override
//		protected void onPreExecute() {
//			progressDialog = new ProgressDialog(BaikeFragment.this.getActivity());
//			progressDialog.setMessage("正在获取视频信息并启动");
//			progressDialog.setCancelable(false);
//			progressDialog.show();
//			super.onPreExecute();
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			id = params[0];
//			Log.e("---PayMoneyTask--", "--PayMoneyTask--");
//			// 说明视频是收费视频，需要先进行鉴权
//			// 鉴权
//			IUserAccountService accountService = new UserAccountServiceImpl(
//					BaikeFragment.this.getActivity());
//			try {
//				if (accountService.authorization()) {
//					// 说明是vip用户
//					return isvip;
//				} else {
//					// 不是vip用户，弹出购买框
//					return notvip;
//				}
//			} catch (ServiceException e) {
//				ExcepUtils.showImpressiveException(BaikeFragment.this.getActivity(), null, e);
//			} catch (TokenException e) {
//				// TODO Auto-generated catch block
//				ExcepUtils.showImpressiveException(BaikeFragment.this.getActivity(), null, e);
//			}
//			return notvip;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			try {
//				progressDialog.dismiss();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//
//			new playVideoTask().execute(new String[] { id });
//
//			super.onPostExecute(result);
//		}
//	}
//
//}
