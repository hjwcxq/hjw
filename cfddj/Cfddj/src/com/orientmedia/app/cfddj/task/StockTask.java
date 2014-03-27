package com.orientmedia.app.cfddj.task;

import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.base.BaseActivity;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.dao.impl.StockVideoDaoImpl;
import com.tv189.dzlc.adapter.dao.impl.VideoDetailsDaoImpl;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.StockVideo;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;
import com.tv189.dzlc.adapter.service.impl.CollectionServiceImpl;
import com.tv189.dzlc.adapter.service.impl.VideoServiceImpl;

public class StockTask {

	Context mContext;

	public StockTask(Context mContext) {
		this.mContext = mContext;
	}

	public void addCoolect(StockVideo video, ShowLoadingTipListener showListener) {
		new addFavVideoTask(showListener,video).execute();
	}

	public void addDownload(StockVideo video, ShowLoadingTipListener showListener) {
		new AddDownloadTask(showListener,video).execute();
	}

	/**
	 * 加入收藏
	 * 
	 * @author EsaFans
	 * 
	 */
	class addFavVideoTask extends AsyncTask<String, Void, Boolean> {

		private ShowLoadingTipListener showListener;
		
		private StockVideo video ;

		public addFavVideoTask(ShowLoadingTipListener showListener,StockVideo video) {
			this.showListener = showListener;
			this.video = video ;
		}

		@Override
		protected void onPreExecute() {
			showListener.onShowLoadingTip("");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			CollectionServiceImpl requestService = new CollectionServiceImpl(
					mContext);
			// 这边有问题
			boolean response = false;
			try {
				response = requestService.collectionAdd("11", "11", video.getProductID());
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException((BaseActivity) mContext,
						null, e);
			}
			return Boolean.valueOf(response);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			showListener.onHideLoadingTip();
			if (result.booleanValue())
				((BaseActivity) mContext).showCusToast("添加收藏成功");
			else
				((BaseActivity) mContext).showCusToast("添加收藏失败");
		}
	}

	/**
	 * 添加下载任务
	 */

	private VideoDetails videoDetail = null;

	private class AddDownloadTask extends AsyncTask<String, Void, Boolean> {

		private ShowLoadingTipListener showListener;
		
		private StockVideo video ;

		public AddDownloadTask(ShowLoadingTipListener showListener,StockVideo video) {
			this.showListener = showListener;
			this.video = video;
		}

		@Override
		protected void onPreExecute() {
			showListener.onShowLoadingTip("");
		}

		@Override
		protected Boolean doInBackground(String... param) {
			boolean flag = false;
			VideoServiceImpl requestService = new VideoServiceImpl(mContext);
			try {
				videoDetail = requestService.videoDetails(video.getId());
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException((Activity)mContext, null, e);
			}
			if (videoDetail != null)
				flag = true;
			else
				flag = false;

			return Boolean.valueOf(flag);

		}

		@Override
		protected void onPostExecute(Boolean result) {
			showListener.onHideLoadingTip();
			if (result.booleanValue()) {
				if (videoDetail == null) {
					((BaseActivity) mContext).showCusToast("添加到我的下载失败");
					return;
				}
				((BaseActivity) mContext).showCusToast("添加到我的下载成功");
				// 启动下载
				StockVideoDaoImpl videoDao = new StockVideoDaoImpl(mContext);
				VideoDetailsDaoImpl detailDao = new VideoDetailsDaoImpl(mContext);
				String uid  = AppSetting.getInstance(mContext).getUserId();
				try {
					if(!videoDao.isExits(video, uid)){
						video.uid = uid ;
						videoDao.addStockVideo(video);
					}
				} catch (OrmSqliteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					if(!detailDao.isExits(videoDetail, uid)){
						videoDetail.uid = uid;
						detailDao.addVideoDetails(videoDetail);
					}
				} catch (OrmSqliteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				new VideoDownloader(videoDetail).start();
			} else {
				((BaseActivity) mContext).showCusToast("添加到我的下载失败");
			}
		}
	}

	/**
	 * 获取视频详细信息并跳转
	 * @author EsaFans
	 * 
	 */
	/*class playVideoTask extends AsyncTask<String, Void, Boolean> {

		String id = "";

		@Override
		protected void onPreExecute() {
			((BaseActivity) mContext)
					.showNetLoadingProgressDialog("正在获取视频信息并启动");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			id = params[0];
			boolean flag = false;
			if (id != null) {
				VideoServiceImpl requestService = new VideoServiceImpl(mContext);
				videoDetail = null;
				try {
					videoDetail = requestService.videoDetails(id);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (videoDetail != null) {
					flag = true;
				}
			}
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			((BaseActivity) mContext).hideNetLoadingProgressDialog();
			if (result.booleanValue()) {
				try {
					final String rtspUrl = videoDetail.getPlayUrl();
					if (rtspUrl != null) {
						try {
							Uri uri = Uri.parse(rtspUrl);
							Intent intent = new Intent(Intent.ACTION_VIEW);
							String type = "video/3gpp";
							intent.setDataAndType(uri, type);
							mContext.startActivity(intent);

						} catch (Exception e) {
							((BaseActivity) mContext).showCusToast("无法处理当前媒体");
						}

					} else {
						((BaseActivity) mContext).showCusToast("无法播放，地址为空");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			} else
				((BaseActivity) mContext).showCusToast("该视频暂时无法播放");
		}
	}*/

	

}
