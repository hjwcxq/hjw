package com.orientmedia.app.cfddj.ui;

import java.io.IOException;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.tool.HttpUtil;
import com.orientmedia.base.BaseActivity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;


public class TestActivity extends BaseActivity implements OnClickListener , ShowLoadingTipListener{

	private RelativeLayout rlSurfaceView;
	private SurfaceView mSurfaceView;
	private ImageView playControlBtn;

	private MediaPlayer mediaPlayer;
	private SurfaceHolder surfaceHolder;

	private boolean playerIsReady = false;
	private boolean isSurCreated = false;

	private int playPosition = 0;

	private int videoWidth;
	private int videoHeight;
	private int videoPortraitMaxWidth = 0;
	private int videoPortraitMaxHeight = 0;
	private int videoLandscapeMaxWidth = 0;
	private int videoLandscapeMaxHeight = 0;

	private ScrollView scrollView;

	private ImageView fullScreen;

	private TextView tital, time, playNum, intro;

	private SeekBar mSeekBar;

	private ImageView ivDownload, ivCollect, ivShare;

	public static VideoDetails mVideoDetails;
	
	boolean initSeekPosition = true;

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				if(mediaPlayer != null)
					mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		mediaPlayer = new MediaPlayer();
		initView();
		initListener();
		setMediaPlayer();

		new Thread() {

			@Override
			public void run() {
				try {
					while(true){
						while (mediaPlayer != null && mediaPlayer.isPlaying()) {
							myHandler.sendEmptyMessage(100);
							sleep(500);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void initListener() {
		playControlBtn.setOnClickListener(this);
		fullScreen.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(change);

		ivDownload.setOnClickListener(this);
		ivShare.setOnClickListener(this);
		ivCollect.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ivFullScreen:
			turnOriClick(v);
			break;
		case R.id.playControlBtn:
			videoPlay();
			break;
		case R.id.img_download:
//			 new StockTask(this).addDownload(mVideoDetails, this);
			break;
		case R.id.img_share:
			break;
		case R.id.img_collect:
			break;

		default:
			break;
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			stopPlay();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		// SystemContext.last_refresh_data = "";
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	/****
	 * 当前fragment销毁时，停止搜索设备的服务，停止mediaplayer
	 */
	@Override
	public void onDestroy() {
		// SystemContext.last_refresh_data = "";

		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		super.onDestroy();
	}

	/**
	 * rt全屏
	 * 
	 * @param v
	 */
	public void turnOriClick(View v) {
		DisplayMetrics dm = new DisplayMetrics();
		TestActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		if (screenHeight > screenWidth) {
			try {
				(this).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} catch (Exception e) {
			}
		} else if (screenWidth > screenHeight) {
			try {
				(this).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} catch (Exception e) {
			}
		}
	}

	public void playUrl(String videoUrl) {
		playControlBtn.setVisibility(View.GONE);
		mSurfaceView.setVisibility(View.VISIBLE);
		try {
			showLoadingIcon();
			if (playerIsReady) {
				mediaPlayer.start();
			} else {
				mediaPlayer.reset();
				if (isSurCreated) {
					surfaceHolder = mSurfaceView.getHolder();
					surfaceHolder.addCallback(surfaceHolderCallback);
					mediaPlayer.setDisplay(surfaceHolder);
					mediaPlayer.setDataSource(videoUrl);
					mediaPlayer.prepare();
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initView() {
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);

		rlSurfaceView = (RelativeLayout) findViewById(R.id.surfaceViewBorder);
		mSurfaceView = (SurfaceView) findViewById(R.id.videoSurfaceView);

		setSurfaceView();
		playControlBtn = (ImageView) findViewById(R.id.playControlBtn);

		scrollView = (ScrollView) findViewById(R.id.sv_video);
		fullScreen = (ImageView) findViewById(R.id.ivFullScreen);

		tital = (TextView) findViewById(R.id.tv_vtital);
		time = (TextView) findViewById(R.id.vtime);
		playNum = (TextView) findViewById(R.id.vnum);
		intro = (TextView) findViewById(R.id.tv_vintro);

		ivDownload = (ImageView) findViewById(R.id.img_download);
		ivCollect = (ImageView) findViewById(R.id.img_collect);
		ivShare = (ImageView) findViewById(R.id.img_share);

		if (mVideoDetails != null) {
			String video_uri = mVideoDetails.getPlayUrl();
			String video_tital = mVideoDetails.getTitle();// 读出数据
			String video_length = mVideoDetails.getLength();// 读出数据
			String video_intro = mVideoDetails.getDetail();// 读出数据
			Uri real_video_uri = Uri.parse(video_uri);
			tital.setText(video_tital);
			int timer_m = Integer.parseInt(video_length) / 60;
			int timer_s = Integer.parseInt(video_length) % 60;
			time.setText(String.valueOf(timer_m) + ":"
					+ String.valueOf(timer_s));
			playNum.setText("");
			intro.setText(video_intro);
		}

	}

	private void setSurfaceView() {
		mSurfaceView.getHolder().setType(
				SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceView.getHolder().setKeepScreenOn(true);
		mSurfaceView.setOnClickListener(surfaceClickListener);
		mSurfaceView.getHolder().addCallback(surfaceHolderCallback);
	}

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
								playPosition = mediaPlayer.getCurrentPosition();
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
				if (videoPortraitMaxWidth <= 0) {
					videoPortraitMaxWidth = rlSurfaceView.getWidth();
					// 手动定义屏幕比例
					// videoPortraitMaxHeight = rlSurfaceView.getHeight();
					videoPortraitMaxHeight = rlSurfaceView.getWidth() * 9 / 16;
				}
				Display display = TestActivity.this.getWindowManager()
						.getDefaultDisplay();
				if (TestActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					videoLandscapeMaxWidth = display.getHeight();
					videoLandscapeMaxHeight = display.getWidth();
				} else {
					videoLandscapeMaxWidth = display.getWidth();
					videoLandscapeMaxHeight = display.getHeight();
				}

				isSurCreated = true;
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}
	};

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
		if (playControlBtn.getVisibility() != View.VISIBLE) {
			try {
				hideLoadingIcon();
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

	private void setMediaPlayer() {
		mediaPlayer.setScreenOnWhilePlaying(true);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				try {
					hideLoadingIcon();
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (playerIsReady) {
					playerIsReady = false;
					playControlBtn.setVisibility(View.VISIBLE);
				}

				playPosition = 0;
				mSeekBar.setProgress(playPosition);
			}
		});
		mediaPlayer.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				try {
					hideLoadingIcon();
				} catch (Exception e) {
					// TODO: handle exception
				}

				return true;
			}
		});

		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				if (!playerIsReady) {
					mSeekBar.setMax(mediaPlayer.getDuration());

					try {
						hideLoadingIcon();
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
					public void onVideoSizeChanged(MediaPlayer mp, int width,
							int height) {
						DisplayMetrics dm = new DisplayMetrics();
						TestActivity.this.getWindowManager()
								.getDefaultDisplay().getMetrics(dm);
						int screenWidth = dm.widthPixels;
						int screenHeight = dm.heightPixels;
						if (videoPortraitMaxWidth <= 0) {
							videoPortraitMaxWidth = rlSurfaceView.getWidth();
							// 手动定义屏幕比例
							// videoPortraitMaxHeight =
							// rlSurfaceView.getHeight();
							videoPortraitMaxHeight = rlSurfaceView.getWidth() * 9 / 16;
						}

						if (screenHeight > screenWidth) {
							resetVideoPort(mp, videoPortraitMaxWidth,
									videoPortraitMaxHeight, false);
						} else {
							resetVideoPort(mp, videoLandscapeMaxWidth,
									videoLandscapeMaxHeight, true);
						}
					}
				});
		mediaPlayer
				.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

					@Override
					public void onBufferingUpdate(MediaPlayer mp, int percent) {
					}
				});
	}

	public void setFullScreamView(boolean shouldHide) {
		if (shouldHide) {
			resetVideoPort(mediaPlayer, videoLandscapeMaxWidth,
					videoLandscapeMaxHeight, true);
			scrollView.setVisibility(View.GONE);
			getSupportActionBar().hide();
		} else {
			resetVideoPort(mediaPlayer, videoPortraitMaxWidth,
					videoPortraitMaxHeight, false);
			scrollView.setVisibility(View.VISIBLE);
			getSupportActionBar().show();
		}
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
			// layoutParams.width = videoWidth == 0 ? videoPortraitMaxWidth
			// : videoWidth;
			// layoutParams.height = videoHeight == 0 ? videoPortraitMaxHeight
			// : videoHeight;

			layoutParams.width = videoPortraitMaxWidth;
			layoutParams.height = videoPortraitMaxWidth * 9 / 16;
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

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (result.equalsIgnoreCase(ERRORPRE)) {
				try {
					hideLoadingIcon();
				} catch (Exception e) {
					// TODO: handle exception
				}
				int wifiType = HttpUtil.getNetWorkType(TestActivity.this);
				surfaceClickListener.onClick(mSurfaceView);
				showCusToast("加载直播流出错");
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
			Log.e("----onprogressupdate----", "----onprogressupdate----"
					+ values.toString());
			if (isCancelled())
				return;

		}

		@Override
		protected void onPreExecute() {

		}

	}

	playStreamTask playTask;

	private void playVideoStream(final String url) {
		if (url != null && !url.equalsIgnoreCase("")) {
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					playTask = new playStreamTask();
					playTask.execute(new String[] { url });

				}
			});
		} else {
			hideLoadingIcon();
		}
	}

	public void videoPlay() {
		mSurfaceView.setVisibility(View.VISIBLE);
		if (mediaPlayer != null) {
			showLoadingIcon();
			if (playerIsReady) {
				mediaPlayer.start();
			} else {
				if (mVideoDetails != null)
					playVideoStream(mVideoDetails.getPlayUrl());
			}
		} else {
			showCusToast("媒体加载出错");
		}
		playControlBtn.setVisibility(View.GONE);
	}

	private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// 当进度条停止修改的时候触发
			// 取得当前进度条的刻度
			int progress = seekBar.getProgress();
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				// 设置当前播放的位置
				mediaPlayer.seekTo(progress);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

		}
	};

	@Override
	public void onShowLoadingTip(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHideLoadingTip() {
		// TODO Auto-generated method stub
		
	}

}
