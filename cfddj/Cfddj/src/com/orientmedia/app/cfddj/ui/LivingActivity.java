package com.orientmedia.app.cfddj.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.CallBackListener;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.task.QuestionTask;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.FileOperator;
import com.orientmedia.app.cfddj.tool.HttpUtil;
import com.orientmedia.app.cfddj.tool.audio.AudioRecorder;
import com.orientmedia.app.cfddj.ui.fragment.MyQuestionFragment;
import com.orientmedia.app.cfddj.ui.fragment.QuestionFragment;
import com.orientmedia.base.BaseActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.video.LiveVideoInfo;
import com.tv189.dzlc.adapter.service.impl.VideoServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;
import com.umeng.analytics.MobclickAgent;

public class LivingActivity extends BaseActivity implements OnClickListener,
		ShowLoadingTipListener {

	private static final String TAG = null;

	public static final String REFRESH_MY_QUESTION_ACTION = "REFRESH_MY_QUESTION_ACTION";

	public static final String REFRESH_QUESTION_ACTION = "REFRESH_QUESTION_ACTION";

	private boolean isVoice = true;
	private Button btnSwitchVoiceOrText;
	private Button btnSend;
	private Button btnVoice;
	private EditText inputText;

	private RelativeLayout rlSurfaceView;
	private SurfaceView mSurfaceView;
	private ImageView playControlBtn;
	private LinearLayout llNormal;
	private LinearLayout llHigh;
	private LinearLayout llFullScreen;

	private LinearLayout mAllQuestion;
	private LinearLayout mMyQuestion;

	private LinearLayout mFullScreen;
	private LinearLayout tvScreenLayout, ll_questionlist, ll_high, ll_chat,
			ll_normal, ll_full_screen;
	private ViewPager mViewPager;

	private String currentQuality = "normal";

	private ImageView cursor;// 动画图片
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度

	private MediaPlayer mediaPlayer;
	private Timer timer;
	private boolean playerIsReady = false;
	private boolean isSurCreated = false;
	private int playPosition = 0;
	private int videoWidth;
	private int videoHeight;
	private int videoPortraitMaxWidth = 0;
	private int videoPortraitMaxHeight = 0;
	private int videoLandscapeMaxWidth = 0;
	private int videoLandscapeMaxHeight = 0;

	private playStreamTask playTask;
	private LiveVideoInfo videoInfo;
	private String videoUrl = null;
	private List<Fragment> items = new ArrayList<Fragment>();

	private SurfaceHolder surfaceHolder;

	public static String QUALITY_NORMAL = "normal";
	public static String QUALITY_HD = "hd";

	private LinearLayout llLoading;
	private ImageView loadingIcon;
	private TextView loadingTip;
	private Animation anim = null;

	// ======

	private boolean isRecording = false;
	private boolean ulawRunning = false;
	private AudioRecord mRecorder;
	private CountDownTimer mCountDownTimer;
	private int voiceLen = 0;
	private int mDuration = 30 * 1000;
	boolean isLongClick = false;
	private String voice_string = "";
	private Thread recordThread;
	private AudioRecorder mr;
	private static int MAX_TIME = 15; // 最长录制时间，单位秒，0为无时间限制
	private static int MIX_TIME = 1; // 最短录制时间，单位秒，0为无时间限制，建议设为1

	private static int RECORD_NO = 0; // 不在录音
	private static int RECORD_ING = 1; // 正在录音
	private static int RECODE_ED = 2; // 完成录音

	private static int RECODE_STATE = 0; // 录音的状态

	private static float recodeTime = 0.0f; // 录音的时间
	private static double voiceValue = 0.0; // 麦克风获取的音量值

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_living);		
		anim = AnimationUtils.loadAnimation(this, R.anim.rotate_loading);
		initView();
		initListener();
	}

	//hjw del
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case R.id.progream_item:
				Intent itP = new Intent(LivingActivity.this, ProgramActivity.class);
				startActivity(itP);
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}*/
	
	
	
	private ActionBar actionBar = null;

	private ImageView refreshImg = null;

	public void initView() {
		rlSurfaceView = (RelativeLayout) findViewById(R.id.surfaceViewBorder);
		mSurfaceView = (SurfaceView) findViewById(R.id.videoSurfaceView);
		playControlBtn = (ImageView) findViewById(R.id.playControlBtn);
		llNormal = (LinearLayout) findViewById(R.id.ll_normal);
		llHigh = (LinearLayout) findViewById(R.id.ll_high);
		llFullScreen = (LinearLayout) findViewById(R.id.ll_full_screen);

		btnSwitchVoiceOrText = (Button) findViewById(R.id.btn_switch_voice_or_text);
		btnSend = (Button) findViewById(R.id.btn_send);
		btnVoice = (Button) findViewById(R.id.btn_voice);
		inputText = (EditText) findViewById(R.id.input_content);

		mAllQuestion = (LinearLayout) findViewById(R.id.ll_all_question);
		mMyQuestion = (LinearLayout) findViewById(R.id.ll_my_question);
		mFullScreen = (LinearLayout) findViewById(R.id.ll_question_full_screen);

		cursor = (ImageView) findViewById(R.id.cursor);

		// midBorderLayout = (LinearLayout) findViewById(R.id.midBorderLayout);
		ll_questionlist = (LinearLayout) findViewById(R.id.ll_questionlist);
		ll_chat = (LinearLayout) findViewById(R.id.ll_chat);
		playControlBtn = (ImageView) findViewById(R.id.playControlBtn);
		tvScreenLayout = (LinearLayout) findViewById(R.id.tvScreenLayout);

		initAnimView();
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(pageChangerListener);

		mediaPlayer = new MediaPlayer();
		timer = new Timer();

		setSurfaceView();
		setMediaPlayer();

		llLoading = (LinearLayout) findViewById(R.id.ll_loading);
		loadingIcon = (ImageView) findViewById(R.id.loading_icon);
		loadingTip = (TextView) findViewById(R.id.loading_tip);
		videoPlay();
	}

	public void initListener() {
		mAllQuestion.setOnClickListener(this);
		mMyQuestion.setOnClickListener(this);
		btnSwitchVoiceOrText.setOnClickListener(this);
		mFullScreen.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		ll_questionlist.setOnClickListener(this);
		ll_chat.setOnClickListener(this);
		playControlBtn.setOnClickListener(this);
		// tvScreenLayout.setOnClickListener(this);
		llHigh.setOnClickListener(this);
		llNormal.setOnClickListener(this);
		llFullScreen.setOnClickListener(this);

		btnVoice.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					btnVoice.setText("松开发送");

					if (RECODE_STATE != RECORD_ING) {
						scanOldFile();
						mr = new AudioRecorder("myvoice");
						RECODE_STATE = RECORD_ING;
						showVoiceDialog();
						try {
							mr.start();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mythread();
					}

					break;
				case MotionEvent.ACTION_UP:
					if (RECODE_STATE == RECORD_ING) {
						RECODE_STATE = RECODE_ED;
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
						try {
							mr.stop();
							voiceValue = 0.0;
							sendvoice();

						} catch (IOException e) {
							e.printStackTrace();
						}

						if (recodeTime < MIX_TIME) {
							showWarnToast();
							RECODE_STATE = RECORD_NO;
						}
					}

					break;
				}
				return false;
			}
		});

	}

	public void initChatView() {
		if (isVoice) {
			isVoice = false;
			btnSwitchVoiceOrText.setBackgroundResource(R.drawable.keyboard);
			inputText.setVisibility(View.GONE);
			btnSend.setVisibility(View.GONE);
			btnVoice.setVisibility(View.VISIBLE);
		} else {
			isVoice = true;
			btnSwitchVoiceOrText
					.setBackgroundResource(R.drawable.chatting_setmode_voice_btn_normal);
			inputText.setVisibility(View.VISIBLE);
			btnSend.setVisibility(View.VISIBLE);
			btnVoice.setVisibility(View.GONE);
		}
	}

	private String mLastSentMsg = "";

	private int count = 0;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_all_question:// 文字/语音切换
			mViewPager.setCurrentItem(0);
			break;
		case R.id.ll_my_question:// 文字/语音切换
			mViewPager.setCurrentItem(1);
			break;
		case R.id.btn_switch_voice_or_text:// 文字/语音切换
			initChatView();
			break;
		case R.id.ll_question_full_screen:// 提问列表全屏切换
			msgFullScreen(v);
			break;
		case R.id.btn_send:// 提交文字
			String sentMsg = inputText.getText().toString();
			if (sentMsg == null || "".equals(sentMsg)) {
				showCusToast("发送的内容不能为空");
				return;
			}
			if (mLastSentMsg != null && sentMsg.equals(mLastSentMsg)) {
				count++;
				if (count >= 3) {
					showCusToast("请不要发送重复的内容");
					count = 0;
					return;
				}

			}
			mLastSentMsg = sentMsg;

			new QuestionTask(LivingActivity.this).sendMsg(sentMsg, this,
					new CallBackListener() {
						@Override
						public void executeSucc() {
							// TODO Auto-generated method stub
							inputText.setText("");
							Utils.hideSoftInput(LivingActivity.this);
							sendBroadcast(new Intent(REFRESH_QUESTION_ACTION));
						}

						@Override
						public void executeFail() {
							// TODO Auto-generated method stub
							inputText.setText("");
							Utils.hideSoftInput(LivingActivity.this);
						}

						@Override
						public void postExecute() {
							// TODO Auto-generated method stub
							
						}
					});

			break;

		case R.id.ll_questionlist:// 全部提问列表
			break;
		case R.id.ll_chat:// 全部输入栏
			break;
		case R.id.playControlBtn:// 播放
			videoPlay();
			break;
		case R.id.surfaceViewBorder:// 全部视频界面
			break;
		case R.id.tvScreenLayout:// 推送大屏
			// sendTVClick(v);
			tvScreenLayout.setVisibility(View.GONE);
			break;
		case R.id.ll_normal:// 普清流
			normalQualityClick(v);
			break;
		case R.id.ll_high:// 高清流
			highQualityClick(v);
			break;
		case R.id.ll_full_screen:// 全屏直播切换
			turnOriClick(v);
			break;
		default:
			break;
		}
	}

	// 删除老文件
	private void scanOldFile() {
		File file = new File(FileOperator.getCacheVoiceFile(), "myvoice.m4a");
		if (file.exists()) {
			file.delete();
		}
	}

	// 录音时显示Dialog
	private Dialog dialog;
	private ImageView dialog_img;

	private void showVoiceDialog() {
		dialog = new Dialog(this, R.style.DialogStyle);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.my_dialog);
		dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
		dialog.show();
	}

	// 录音计时线程
	void mythread() {
		recordThread = new Thread(ImgThread);
		recordThread.start();
	}

	// 录音线程
	private Runnable ImgThread = new Runnable() {

		@Override
		public void run() {
			recodeTime = 0.0f;
			while (RECODE_STATE == RECORD_ING) {
				if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
					imgHandle.sendEmptyMessage(0);
				} else {
					try {
						Thread.sleep(200);
						recodeTime += 0.2;
						if (RECODE_STATE == RECORD_ING) {
							voiceValue = mr.getAmplitude();
							imgHandle.sendEmptyMessage(1);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		Handler imgHandle = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				switch (msg.what) {
				case 0:
					// 录音超过15秒自动停止
					if (RECODE_STATE == RECORD_ING) {
						RECODE_STATE = RECODE_ED;
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
						try {
							mr.stop();
							voiceValue = 0.0;
						} catch (IOException e) {
							e.printStackTrace();
						}

						if (recodeTime < 1.0) {
							showWarnToast();
							RECODE_STATE = RECORD_NO;
						} else {
							// record.setText("录音完成!点击重新录音");
							// luyin_txt.setText("录音时间："+((int)recodeTime));
							// luyin_path.setText("文件路径："+getAmrPath());
						}
					}
					break;
				case 1:
					setDialogImage();
					break;
				default:
					break;
				}

			}
		};
	};

	// 录音Dialog图片随声音大小切换
	void setDialogImage() {
		if (voiceValue < 200.0) {
			dialog_img.setImageResource(R.drawable.record_animate_01);
		} else if (voiceValue > 200.0 && voiceValue < 400) {
			dialog_img.setImageResource(R.drawable.record_animate_02);
		} else if (voiceValue > 400.0 && voiceValue < 800) {
			dialog_img.setImageResource(R.drawable.record_animate_03);
		} else if (voiceValue > 800.0 && voiceValue < 1600) {
			dialog_img.setImageResource(R.drawable.record_animate_04);
		} else if (voiceValue > 1600.0 && voiceValue < 3200) {
			dialog_img.setImageResource(R.drawable.record_animate_05);
		} else if (voiceValue > 3200.0 && voiceValue < 5000) {
			dialog_img.setImageResource(R.drawable.record_animate_06);
		} else if (voiceValue > 5000.0 && voiceValue < 7000) {
			dialog_img.setImageResource(R.drawable.record_animate_07);
		} else if (voiceValue > 7000.0 && voiceValue < 10000.0) {
			dialog_img.setImageResource(R.drawable.record_animate_08);
		} else if (voiceValue > 10000.0 && voiceValue < 14000.0) {
			dialog_img.setImageResource(R.drawable.record_animate_09);
		} else if (voiceValue > 14000.0 && voiceValue < 17000.0) {
			dialog_img.setImageResource(R.drawable.record_animate_10);
		} else if (voiceValue > 17000.0 && voiceValue < 20000.0) {
			dialog_img.setImageResource(R.drawable.record_animate_11);
		} else if (voiceValue > 20000.0 && voiceValue < 24000.0) {
			dialog_img.setImageResource(R.drawable.record_animate_12);
		} else if (voiceValue > 24000.0 && voiceValue < 28000.0) {
			dialog_img.setImageResource(R.drawable.record_animate_13);
		} else if (voiceValue > 28000.0) {
			dialog_img.setImageResource(R.drawable.record_animate_14);
		}
	}

	/**
	 * 发送语音
	 */
	private void sendvoice() {
		File target = new File(FileOperator.getCacheVoiceFile(), "myvoice.m4a");
		new QuestionTask(this).sendVoiceMsg(target, this,
				new CallBackListener() {

					@Override
					public void executeSucc() {
						// TODO Auto-generated method stub
						sendBroadcast(new Intent(REFRESH_QUESTION_ACTION));
					}

					@Override
					public void executeFail() {
						// TODO Auto-generated method stub

					}

					@Override
					public void postExecute() {
						// TODO Auto-generated method stub
						
					}
				});

	}

	// 录音时间太短时Toast显示
	void showWarnToast() {
		Toast toast = new Toast(this);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setPadding(20, 20, 20, 20);

		// 定义一个ImageView
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.voice_to_short); // 图标

		TextView mTv = new TextView(this);
		mTv.setText("时间太短   录音失败");
		mTv.setTextSize(14);
		mTv.setTextColor(Color.WHITE);// 字体颜色
		// mTv.setPadding(0, 10, 0, 0);

		// 将ImageView和ToastView合并到Layout中
		linearLayout.addView(imageView);
		linearLayout.addView(mTv);
		linearLayout.setGravity(Gravity.CENTER);// 内容居中
		linearLayout.setBackgroundResource(R.drawable.record_bg);// 设置自定义toast的背景

		toast.setView(linearLayout);
		toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间 100为向下移100dp
		toast.show();
	}

	/****
	 * 直播开始加载
	 * 
	 */
	public void videoPlay() {
		Log.e(TAG, "---videoPlay----");
		mSurfaceView.setVisibility(View.VISIBLE);
		if (mediaPlayer != null) {
			if (playerIsReady) {
				mediaPlayer.start();
			} else {
				new LiveVideoPathTask().execute();
			}
		} else {
			showCusToast("媒体加载出错");
		}
		playControlBtn.setVisibility(View.GONE);
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

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (result.equalsIgnoreCase(ERRORPRE)) {
				try {
					hideLoadingIcon();
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {

				if (videoUrl != null) {
					playVideoStream(videoUrl);
				} else {
					if (currentQuality.equalsIgnoreCase(QUALITY_NORMAL)) {
						playVideoStream(videoInfo.getLowQuality());
					} else {
						playVideoStream(videoInfo.getHighQuality());
					}

				}
			}

		}

		@Override
		protected String doInBackground(String... params) {
			synchronized (MainConst.SYNC) {
				VideoServiceImpl videoService = new VideoServiceImpl(
						LivingActivity.this);
				LiveVideoInfo videoInfoT = null;
				try {
					if (videoUrl == null) {
						videoInfoT = videoService.getVideo();
						if (videoInfoT == null) {
							return ERRORPRE;
						}
						videoInfo = videoInfoT;
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
			showLoadingIcon();
		}

	}

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

	/****
	 * 暂停直播
	 */
	public void stopPlay() {
		if (playControlBtn.getVisibility() != View.VISIBLE) {
			if (mediaPlayer != null) {
				playerIsReady = false;
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
	 * 高清播放
	 * 
	 * @param v
	 */
	public void highQualityClick(View v) {
		// tvScreenLayout.setBackgroundResource(R.color.color_trans);
		llNormal.setBackgroundResource(R.drawable.btn_video_bg);
		llHigh.setBackgroundResource(R.drawable.btn_video_bg_selected);

		if (!currentQuality.equalsIgnoreCase(QUALITY_HD)) {
			currentQuality = QUALITY_HD;
			surfaceClickListener.onClick(mSurfaceView);
			videoPlay();
		}
	}

	/****
	 * 标清播放
	 * 
	 * @param v
	 */
	public void normalQualityClick(View v) {
		// tvScreenLayout.setBackgroundResource(R.color.color_trans);
		llNormal.setBackgroundResource(R.drawable.btn_video_bg_selected);
		llHigh.setBackgroundResource(R.drawable.btn_video_bg);

		if (!currentQuality.equalsIgnoreCase(QUALITY_NORMAL)) {
			currentQuality = QUALITY_NORMAL;
			surfaceClickListener.onClick(mSurfaceView);
			videoPlay();
		}
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
				Display display = LivingActivity.this.getWindowManager()
						.getDefaultDisplay();
				if (LivingActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
					videoLandscapeMaxWidth = display.getHeight();
					videoLandscapeMaxHeight = display.getWidth();
				} else {
					videoLandscapeMaxWidth = display.getWidth();
					videoLandscapeMaxHeight = display.getHeight();
				}

				Log.e(TAG,
						"maxSizeChange:"
								+ String.valueOf(videoPortraitMaxWidth) + ","
								+ String.valueOf(videoPortraitMaxHeight)
								+ "   "
								+ String.valueOf(videoLandscapeMaxWidth) + ","
								+ String.valueOf(videoLandscapeMaxHeight));
				isSurCreated = true;
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}
	};

	private void setSurfaceView() {
		mSurfaceView.setBackgroundColor(Color.TRANSPARENT);
		mSurfaceView.setOnClickListener(surfaceClickListener);
		mSurfaceView.getHolder().addCallback(surfaceHolderCallback);
		mSurfaceView.getHolder().setType(
				SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	private void setMediaPlayer() {
		mediaPlayer.setScreenOnWhilePlaying(true);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				if (playerIsReady) {
					playerIsReady = false;
					playControlBtn.setVisibility(View.VISIBLE);
				}
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
				Log.e("--mediaPlayer--onPrepared--start--",
						"--mediaPlayer----onPrepared--start--");
				if (!playerIsReady) {
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
						LivingActivity.this.getWindowManager()
								.getDefaultDisplay().getMetrics(dm);
						int screenWidth = dm.widthPixels;
						int screenHeight = dm.heightPixels;
						if (screenHeight > screenWidth) { //
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
						// ((MainActivity) act).showToast("已缓冲:"
						// + String.valueOf(percent) + "%");
					}
				});
	}

	public void setFullScreamView(boolean shouldHide) {

		// 横屏
		if (shouldHide) {
			resetVideoPort(mediaPlayer, videoLandscapeMaxWidth,
					videoLandscapeMaxHeight, true);
			ll_chat.setVisibility(View.GONE);
			ll_questionlist.setVisibility(View.GONE);
			// ((VideoInteractionFragment) fragment).expertListContainer
			// .setVisibility(View.GONE);
		}
		// 竖屏
		else {
			resetVideoPort(mediaPlayer, videoPortraitMaxWidth,
					videoPortraitMaxHeight, false);
			ll_chat.setVisibility(View.VISIBLE);
			ll_questionlist.setVisibility(View.VISIBLE);
			// ((VideoInteractionFragment) fragment).expertListContainer
			// .setVisibility(View.VISIBLE);
		}
	}

	/**
	 * rt全屏
	 * 
	 * @param v
	 */
	public void turnOriClick(View v) {
		DisplayMetrics dm = new DisplayMetrics();
		LivingActivity.this.getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
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
		RelativeLayout.LayoutParams inputMsgLayoutLp = (RelativeLayout.LayoutParams) ll_chat
				.getLayoutParams();
		if (isBorderBigger) {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rlSurfaceView
					.getLayoutParams();
			layoutParams.width = videoWidthMax;
			layoutParams.height = videoWidthHeight - inputMsgLayoutLp.height;
			rlSurfaceView.setLayoutParams(layoutParams);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSurfaceView
					.getLayoutParams();
			params.width = videoWidthMax;
			params.height = videoWidthHeight - inputMsgLayoutLp.height;
			mSurfaceView.setLayoutParams(params);
			mSurfaceView.requestLayout();
			rlSurfaceView.requestLayout();
		} else {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rlSurfaceView
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

	/***
	 * fragment启动时----提问列表开始刷新 视频开始播放
	 */
	@Override
	public void onResume() {
		super.onResume();
		//MobclickAgent.onPageStart("livingScreen"); //hjw add for umeng
	    MobclickAgent.onResume(this);          
	}

	/****
	 * fragment暂停时---提问列表停止刷新 视频暂停播放
	 */
	@Override
	public void onPause() {
		super.onPause();
		// SystemContext.last_refresh_data = "";
		//MobclickAgent.onPageEnd("livingScreen"); //hjw add for umeng
	    MobclickAgent.onPause(this); 
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
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

	// 初始化动画
	private void initAnimView() {
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置

	}

	public void translateCursor(boolean is1To2) {
		if (is1To2) {
			TranslateAnimation animation = new TranslateAnimation(offset,
					offset * 2 + bmpW, 0, 0);
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		} else {
			TranslateAnimation animation = new TranslateAnimation(offset * 2
					+ bmpW, 0, 0, 0);
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}
	}

	/**
	 * 留言全屏显示
	 * 
	 * @param v
	 */
	public void msgFullScreen(View v) {

		if (rlSurfaceView.getVisibility() == View.VISIBLE) {
			rlSurfaceView.setVisibility(View.GONE);
			((LinearLayout) v).getChildAt(0).setBackgroundResource(
					R.drawable.btn_down_style);
		} else {
			rlSurfaceView.setVisibility(View.VISIBLE);
			((LinearLayout) v).getChildAt(0).setBackgroundResource(
					R.drawable.btn_up_style);
		}
	}

	private FragmentPagerAdapter mViewPagerAdapter = new FragmentPagerAdapter(
			getSupportFragmentManager()) {

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new QuestionFragment();// 全部提问
			case 1:
				return new MyQuestionFragment();// 我的提问
			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	private int currentIndex = 0;

	private ViewPager.OnPageChangeListener pageChangerListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			if (position == 1) {
				if (currentIndex == 0) {
					translateCursor(true);
				}
			} else if (position == 0) {
				if (currentIndex == 1) {
					translateCursor(false);
				}
			}
			currentIndex = position;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

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
				int wifiType = HttpUtil.getNetWorkType(LivingActivity.this);
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
			if (isCancelled())
				return;

		}

		@Override
		protected void onPreExecute() {
			
		}

	}

	@Override
	public void onShowLoadingTip(String loadingMsd) {
		// TODO Auto-generated method stub
		
		
		llLoading.setVisibility(View.VISIBLE);
		loadingIcon.startAnimation(anim);
		loadingTip.setText(loadingMsd);
		loadingTip.setVisibility(View.VISIBLE);
	}

	@Override
	public void onHideLoadingTip() {
		// TODO Auto-generated method stub
		llLoading.setVisibility(View.INVISIBLE);
		loadingIcon.clearAnimation();
		loadingTip.setVisibility(View.INVISIBLE);
	}

}
