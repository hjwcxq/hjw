package com.orientmedia.app.cfddj.adapter;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.tool.imageutils.ImageFetcher;
import com.orientmedia.app.cfddj.tool.imageutils.UIUtils;
import com.orientmedia.app.cfddj.ui.fragment.IndexFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.po.base.AbstractAction;
import com.tv189.dzlc.adapter.po.base.ItemNode;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.news.LiveRollingInfo;
import com.tv189.dzlc.adapter.po.video.LiveVideoInfo;
import com.tv189.dzlc.adapter.service.impl.NewsServiceImpl;
import com.tv189.dzlc.adapter.service.impl.VideoServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;

public class MyViewPagerFragmentAdapter extends MyFragmentPagerAdapter1 {

	private List<ItemNode> items = new ArrayList<ItemNode>();

	private String videoUrl = null;

	private TextView image_intro;

	public MyViewPagerFragmentAdapter(FragmentManager fm, List<ItemNode> list,
			int refrushCount) {
		super(fm, refrushCount);
		// TODO Auto-generated constructor stub
		if (list != null) {
			items.addAll(list);
		}
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public void setItems(List<ItemNode> list) {
		if (list != null && !list.isEmpty()) {
			items.clear();
			items.addAll(list);
			notifyDataSetChanged();
		}
	}

	@Override
	public Fragment getItem(int position) {
		PagerFragment f = new PagerFragment();
		Bundle b = new Bundle();
		b.putInt("num", position);
		f.setArguments(b);
		return f;
	}

	@SuppressLint("ValidFragment")
	public class PagerFragment extends Fragment {
		int mIndex;

		public PagerFragment(){
			
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mIndex = getArguments() != null ? getArguments().getInt("num") : 1;
		}

		/**
		 * The Fragment's UI is just a simple text view showing its instance
		 * number.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			ItemNode item = items.get(mIndex);
			if (item.getType() != null & item.getType().equals("live")) {
				View pageView = inflater.inflate(R.layout.focus_videos, null);
				initVideoPageView(this.getActivity(), pageView, item);
				return pageView;
			} else {
				View pageView = inflater.inflate(R.layout.focus_image, null);
				initPageView(this.getActivity(), pageView, item);
				return pageView;
			}
		}

		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			stopPlay();

		}

		@Override
		public void onStop() {
			super.onStop();
			stopPlay();
		}

		/****
		 * 当前fragment销毁时，停止搜索设备的服务，停止mediaplayer
		 */
		@Override
		public void onDestroy() {
			super.onDestroy();
			stopPlay();
		}

		public void initPageView(FragmentActivity cont, View root,
				final ItemNode item) {
			if (item == null)
				return;
			ImageFetcher mImageFetcher = UIUtils.getImageFetcher(cont);
			ImageView focus_image = (ImageView) root
					.findViewById(R.id.focus_image);
			int width = Utils.getWindowHeight(cont);
			int height = width * 9 / 160;
			focus_image.measure(width, height);
			mImageFetcher.loadThumbnailImage(item.getBackimg().getSrc(),
					focus_image);
			mImageFetcher.setLoadingImage(R.drawable.defult_bg);
			image_intro = (TextView) root.findViewById(R.id.image_intro);
			image_intro.setText(item.getTitle().getText());

			focus_image.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AbstractAction action = item.getAction();
					if (action != null) {
						action.jumpByActionType(action.getParaMap());
					}
				}
			});
		}

		class LoadLivingTitleTask extends AsyncTask<String, Void, String> {

			private Context cont;

			private TextView title;

			public LoadLivingTitleTask(Context cont, TextView title) {
				this.cont = cont;
				this.title = title;
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				NewsServiceImpl newsService = new NewsServiceImpl(cont);
				try {
					LiveRollingInfo rollingInfo = newsService
							.getRollingTitleInfo();
					if (rollingInfo != null)
						return rollingInfo.getContent();

				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					ExcepUtils
							.showImpressiveException((Activity) cont, null, e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null)
					title.setText(result);
			}

		}

		public boolean isPlaying = false;
		private RelativeLayout rlSurfaceView;
		private SurfaceView mSurfaceView;
		private ImageView playControlBtn;

		private MediaPlayer mediaPlayer;
		private SurfaceHolder surfaceHolder;

		private boolean playerIsReady = false;
		private boolean isSurCreated = false;
		private int playPosition = 0;

		private TextView livingTitle;

		private LoadLivingTitleTask livingTitleTask;

		// 加载焦点图位置视频
		public void initVideoPageView(final FragmentActivity cont, View root,
				final ItemNode item) {
			if (item == null)
				return;
			mSurfaceView = (SurfaceView) root
					.findViewById(R.id.videoSurfaceView);
			setSurfaceView();
			mSurfaceView.getHolder().addCallback(surfaceHolderCallback);
			rlSurfaceView = (RelativeLayout) root
					.findViewById(R.id.surfaceViewBorder);
			livingTitle = (TextView) root.findViewById(R.id.image_intro);

			playControlBtn = (ImageView) root.findViewById(R.id.playControlBtn);

			livingTitle.setText(item.getTitle().getText());

			playControlBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 直播开始加载
					new LiveVideoPathTask(cont).execute("");
				}
			});

			mediaPlayer = new MediaPlayer();
			setMediaPlayer(cont);

		}

		public void setSurfaceView() {
			mSurfaceView.setBackgroundColor(Color.TRANSPARENT);
			mSurfaceView.getHolder().setType(
					SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			mSurfaceView.getHolder().setKeepScreenOn(true);
			mSurfaceView.setOnClickListener(surfaceClickListener);
			mSurfaceView.getHolder().addCallback(surfaceHolderCallback);
		}

		/**
		 * 获取播放地址进行播放
		 * 
		 * @author EsaFans
		 * 
		 */
		class LiveVideoPathTask extends AsyncTask<String, Void, String> {

			String SUCCESSPRE = "successpre";
			String ERRORPRE = "errorpre";
			ProgressDialog progressDialog;

			private Context context;

			public LiveVideoPathTask(Context context) {
				this.context = context;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);

				if (result.equalsIgnoreCase(ERRORPRE)) {
					try {
						IndexFragment.showListener.onHideLoadingTip();
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else {
					if (result != null) {
						videoPlay(result, context);
					} else {
						try {
							IndexFragment.showListener.onHideLoadingTip();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}

			@Override
			protected String doInBackground(String... params) {
				synchronized (MainConst.SYNC) {
					VideoServiceImpl videoService = new VideoServiceImpl(
							context);
					LiveVideoInfo videoInfoT = null;
					try {
						if (videoUrl == null) {
							videoInfoT = videoService.getVideo();
							if (videoInfoT == null) {
								return ERRORPRE;
							} else {
								return videoInfoT.getLowQuality();
							}
						}
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return SUCCESSPRE;
				}
			}

			@Override
			protected void onPreExecute() {
				IndexFragment.showListener.onShowLoadingTip("");
			}

		}

		/****
		 * surfaceview的监听事件
		 */
		private OnClickListener surfaceClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopPlay();
			}
		};

		/****
		 * 暂停直播
		 */
		public void stopPlay() {
			if (playControlBtn != null
					&& playControlBtn.getVisibility() != View.VISIBLE) {
				try {
					IndexFragment.showListener.onHideLoadingTip();
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (mediaPlayer != null) {
					if (mediaPlayer.isPlaying()) {
						try {
							playPosition = mediaPlayer.getCurrentPosition();
							mediaPlayer.stop();
						} catch (Exception e) {
						}
					}
					mediaPlayer.reset();
					if (playTask != null
							&& playTask.getStatus() == AsyncTask.Status.RUNNING) {

						playTask.cancel(true);
					}
					mSurfaceView.setVisibility(View.GONE);
					playControlBtn.setVisibility(View.VISIBLE);
				}
			}
		}

		private int videoWidth;
		private int videoHeight;
		private int videoPortraitMaxWidth = 0;
		private int videoPortraitMaxHeight = 0;

		SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				Log.e("surfaceDestroyed", "surfaceDestroyed");
				isSurCreated = false;
				if (mediaPlayer != null) {
					try {
						if (playerIsReady) {
							playerIsReady = false;
							if (mediaPlayer.isPlaying()) {
								try {
									playPosition = mediaPlayer
											.getCurrentPosition();
									mediaPlayer.stop();
								} catch (Exception e) {
								}
							}
							mediaPlayer.reset();
							playControlBtn.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				Log.e("surfaceCreated", "surfaceCreated");
				if (!isSurCreated) {
					isSurCreated = true;
					if (videoPortraitMaxWidth <= 0) {
						videoPortraitMaxWidth = rlSurfaceView.getWidth();
						// 手动定义屏幕比例
						// videoPortraitMaxHeight = rlSurfaceView.getHeight();
						videoPortraitMaxHeight = rlSurfaceView.getWidth() * 9 / 16;
					}
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {

			}
		};

		private void setMediaPlayer(final Context context) {
			mediaPlayer.setScreenOnWhilePlaying(true);
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					if (playerIsReady) {
						playerIsReady = false;
						playControlBtn.setVisibility(View.VISIBLE);
						mSurfaceView.setVisibility(View.GONE);
					}
				}
			});
			mediaPlayer.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					try {
						IndexFragment.showListener.onHideLoadingTip();
					} catch (Exception e) {
						// TODO: handle exception
					}

					return true;
				}
			});

			mediaPlayer
					.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

						@Override
						public void onPrepared(MediaPlayer mp) {
							if (!playerIsReady) {
								try {
									IndexFragment.showListener
											.onHideLoadingTip();
								} catch (Exception e) {
									// TODO: handle exception
								}

								playerIsReady = true;
								mediaPlayer.start();
								if (playPosition > 0) {
									mediaPlayer.seekTo(playPosition);
								}
							}
						}
					});
			mediaPlayer
					.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {

						@Override
						public void onVideoSizeChanged(MediaPlayer mp,
								int width, int height) {
							if (videoPortraitMaxWidth <= 0) {
								videoPortraitMaxWidth = rlSurfaceView
										.getWidth();
								// 手动定义屏幕比例
								// videoPortraitMaxHeight =
								// rlSurfaceView.getHeight();
								videoPortraitMaxHeight = rlSurfaceView
										.getWidth() * 9 / 16;
							}

							resetVideoPort(mp, videoPortraitMaxWidth,
									videoPortraitMaxHeight, false);
						}
					});
			mediaPlayer
					.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

						@Override
						public void onBufferingUpdate(MediaPlayer mp,
								int percent) {
						}
					});
		}

		private void resetVideoPort(MediaPlayer mp, int videoWidthMax,
				int videoWidthHeight, boolean isBorderBigger) {
			try {
				videoWidth = mp.getVideoWidth();
			} catch (Exception e) {
			}
			try {
				videoHeight = mp.getVideoHeight();
			} catch (Exception e) {
			}
			float widthRatio = (float) videoWidth / videoWidthMax;
			float heightRatio = (float) videoHeight / videoWidthHeight;
			if (heightRatio > widthRatio) {
				videoHeight = (int) Math.ceil((float) videoHeight
						/ (float) heightRatio);
				videoWidth = (int) Math.ceil((float) videoWidth
						/ (float) heightRatio);
			} else {
				videoHeight = (int) Math.ceil((float) videoHeight
						/ (float) widthRatio);
				videoWidth = (int) Math.ceil((float) videoWidth
						/ (float) widthRatio);
			}
			if (isBorderBigger) { // 横屏
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlSurfaceView
						.getLayoutParams();
				layoutParams.width = videoWidthMax;
				layoutParams.height = videoWidthHeight;
				rlSurfaceView.setLayoutParams(layoutParams);
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSurfaceView
						.getLayoutParams();
				params.width = videoWidthMax;
				params.height = videoWidthHeight;
				mSurfaceView.setLayoutParams(params);
				mSurfaceView.requestLayout();
				rlSurfaceView.requestLayout();
			} else {
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlSurfaceView
						.getLayoutParams();
				layoutParams.width = videoWidth == 0 ? videoPortraitMaxWidth
						: videoWidth;
				layoutParams.height = videoHeight == 0 ? videoPortraitMaxHeight
						: videoHeight;
				rlSurfaceView.setLayoutParams(layoutParams);
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSurfaceView
						.getLayoutParams();
				params.width = videoWidthMax;
				params.height = videoWidthHeight;
				mSurfaceView.setLayoutParams(params);
				// mSurfaceView.requestLayout();
				// rlSurfaceView.requestLayout();
			}

		}

		/**
		 * 播放前准备
		 * 
		 * @author EsaFans
		 * 
		 */

		class playStreamTask extends AsyncTask<String, Void, String> {

			String SUCCESSPRE = "successpre";
			String ERRORPRE = "errorpre";
			ProgressDialog progressDialog;

			private Context context;

			public playStreamTask(Context context) {
				this.context = context;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);

				if (result.equalsIgnoreCase(ERRORPRE)) {
					try {
						IndexFragment.showListener.onHideLoadingTip();
					} catch (Exception e) {
						// TODO: handle exception
					}
					surfaceClickListener.onClick(mSurfaceView);
					playerIsReady = false;
				}

			}

			@Override
			protected String doInBackground(String... params) {
				try {
					mediaPlayer.reset();
					if (isSurCreated) {
						surfaceHolder = mSurfaceView.getHolder();
						surfaceHolder.addCallback(surfaceHolderCallback);
						mediaPlayer.setDisplay(surfaceHolder);
						mediaPlayer.setDataSource(params[0]);
						mediaPlayer.prepareAsync();
					}
					// else{
					// setSurfaceView();
					// surfaceHolder = mSurfaceView.getHolder();
					// surfaceHolder.addCallback(surfaceHolderCallback);
					// mediaPlayer.setDisplay(surfaceHolder);
					// mediaPlayer.setDataSource(params[0]);
					// mediaPlayer.prepareAsync();
					// }
				} catch (Exception e) {
					return ERRORPRE;
				}
				return SUCCESSPRE;
			}

			@Override
			protected void onCancelled() {
				super.onCancelled();
			}

			@Override
			protected void onProgressUpdate(Void... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);

			}

			@Override
			protected void onPreExecute() {
			}
		}

		playStreamTask playTask;

		private void playVideoStream(final String url, final Context context) {
			if (url != null && !url.equalsIgnoreCase("")) {
				((Activity) context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						playTask = new playStreamTask(context);
						playTask.execute(new String[] { url });

					}
				});
			} else {
				try {
					IndexFragment.showListener.onHideLoadingTip();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

		public void videoPlay(String videoUrl, Context context) {
			mSurfaceView.setVisibility(View.VISIBLE);
			if (mediaPlayer != null) {
				// showLoadingIcon();
				if (playerIsReady) {
					mediaPlayer.start();
				} else {
					playVideoStream(videoUrl, context);
				}
			} else {
				try {
					IndexFragment.showListener.onHideLoadingTip();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			playControlBtn.setVisibility(View.GONE);
		}
	}
}
