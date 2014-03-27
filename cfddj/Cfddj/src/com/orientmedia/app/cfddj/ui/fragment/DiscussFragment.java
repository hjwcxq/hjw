package com.orientmedia.app.cfddj.ui.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.CallBackListener;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.module.ModuleFactory;
import com.orientmedia.app.cfddj.task.QuestionTask;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.FileOperator;
import com.orientmedia.app.cfddj.tool.HttpUtil;
import com.orientmedia.app.cfddj.tool.audio.AudioRecorder;
import com.orientmedia.app.cfddj.ui.AppRecommendActivity;
import com.orientmedia.app.cfddj.ui.BaodianActivity;
import com.orientmedia.app.cfddj.ui.LivingActivity;
import com.orientmedia.app.cfddj.ui.expert.ExpertOneToOneActivity;
import com.orientmedia.app.cfddj.ui.find.ChooseStockActivity;
import com.orientmedia.app.cfddj.ui.find.PrivateDataActivity;
import com.orientmedia.base.BaseFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
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

import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.po.base.AbstractModule;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.video.LiveVideoInfo;
import com.tv189.dzlc.adapter.service.impl.VideoServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;

@SuppressLint("ResourceAsColor")
public class DiscussFragment extends BaseFragment implements OnClickListener,
	ShowLoadingTipListener{
	
	public static final String REFRESH_MY_QUESTION_ACTION = "REFRESH_MY_QUESTION_ACTION";

	public static final String REFRESH_QUESTION_ACTION = "REFRESH_QUESTION_ACTION";

	private boolean isVoice = true;
	private Button btnSwitchVoiceOrText;
	private Button btnSend;
	private Button btnVoice;
	private EditText inputText;


	private LinearLayout mAllQuestion;
	private LinearLayout mMyQuestion;

	private LinearLayout mFullScreen;
	private LinearLayout ll_questionlist, ll_chat, midBorderLayout;
	private ViewPager mViewPager;

	private ImageView cursor;// 动画图片
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度


	private List<Fragment> items = new ArrayList<Fragment>();

	public static String QUALITY_NORMAL = "normal";
	public static String QUALITY_HD = "hd";

	private LinearLayout llLoading;
	private ImageView loadingIcon;
	private TextView loadingTip;
	private Animation anim = null;

	// ======

	boolean isLongClick = false;
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
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		BaiduStatistics.onMyEvent(this.getActivity(), "3000", this.getClass().getName()+"进入聚焦页面");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = null;
		if (container == null)
			return null;
		else {
			view = inflater.inflate(R.layout.activity_discuss, container, false);
			anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_loading);
			initView(view);
			initListener();
		}
		return view;
	}
	
	public void initView(View view){

		btnSwitchVoiceOrText = (Button) view.findViewById(R.id.btn_switch_voice_or_text);
		btnSend = (Button) view.findViewById(R.id.btn_send);
		btnVoice = (Button) view.findViewById(R.id.btn_voice);
		inputText = (EditText) view.findViewById(R.id.input_content);

		mAllQuestion = (LinearLayout) view.findViewById(R.id.ll_all_question);
		mMyQuestion = (LinearLayout) view.findViewById(R.id.ll_my_question);
		mFullScreen = (LinearLayout) view.findViewById(R.id.ll_question_full_screen);

		cursor = (ImageView) view.findViewById(R.id.cursor);
		
		initAnimView();
		
		mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		//FragmentManager fm = getFragmentManager();
		//fm.beginTransaction();
		//fm.beginTransaction().replace(R.id.viewPager, new QuestionFragment()).commit();
		MyPagerAdapter1 mAdapter = new MyPagerAdapter1(getChildFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(pageChangerListener);
		

		midBorderLayout = (LinearLayout) view.findViewById(R.id.midBorderLayout);
		ll_questionlist = (LinearLayout) view.findViewById(R.id.ll_questionlist);
		ll_chat = (LinearLayout) view.findViewById(R.id.ll_chat);


		llLoading = (LinearLayout) view.findViewById(R.id.ll_loading);
		loadingIcon = (ImageView) view.findViewById(R.id.loading_icon);
		loadingTip = (TextView) view.findViewById(R.id.loading_tip);
		
	}
	
	public void initListener() {
		mAllQuestion.setOnClickListener(this);
		mMyQuestion.setOnClickListener(this);
		btnSwitchVoiceOrText.setOnClickListener(this);
		mFullScreen.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		ll_questionlist.setOnClickListener(this);
		ll_chat.setOnClickListener(this);
		// tvScreenLayout.setOnClickListener(this);

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

			new QuestionTask(getActivity()).sendMsg(sentMsg, this,
					new CallBackListener() {
						@Override
						public void executeSucc() {
							// TODO Auto-generated method stub
							inputText.setText("");
							Utils.hideSoftInput(getActivity());
							getActivity().sendBroadcast(new Intent(REFRESH_QUESTION_ACTION));
						}

						@Override
						public void executeFail() {
							// TODO Auto-generated method stub
							inputText.setText("");
							Utils.hideSoftInput(getActivity());
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
		dialog = new Dialog(getActivity(), R.style.DialogStyle);
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
		new QuestionTask(getActivity()).sendVoiceMsg(target, this,
				new CallBackListener() {

					@Override
					public void executeSucc() {
						// TODO Auto-generated method stub
						getActivity().sendBroadcast(new Intent(REFRESH_QUESTION_ACTION));
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
		Toast toast = new Toast(getActivity());
		LinearLayout linearLayout = new LinearLayout(getActivity());
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setPadding(20, 20, 20, 20);

		// 定义一个ImageView
		ImageView imageView = new ImageView(getActivity());
		imageView.setImageResource(R.drawable.voice_to_short); // 图标

		TextView mTv = new TextView(getActivity());
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


	/***
	 * fragment启动时----提问列表开始刷新 视频开始播放
	 */
	@Override
	public void onResume() {
		super.onResume();
		//MobclickAgent.onResume(this);  //hjw add umeng
	}

	/****
	 * fragment暂停时---提问列表停止刷新 视频暂停播放
	 */
	@Override
	public void onPause() {
		super.onPause();
		//MobclickAgent.onPause(this);  //hjw add umeng
		// SystemContext.last_refresh_data = "";
	}

	@Override
	public void onStop() {
		super.onStop();
		// SystemContext.last_refresh_data = "";
	}

	/****
	 * 当前fragment销毁时，停止搜索设备的服务，停止mediaplayer
	 */
	@Override
	public void onDestroy() {
		// SystemContext.last_refresh_data = "";
		super.onDestroy();
	}

	// 初始化动画
	private void initAnimView() {
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
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

		if (midBorderLayout.getVisibility() == View.VISIBLE) {
			midBorderLayout.setVisibility(View.GONE);
			((LinearLayout) v).getChildAt(0).setBackgroundResource(
					R.drawable.btn_down_style);
		} else {
			midBorderLayout.setVisibility(View.VISIBLE);
			((LinearLayout) v).getChildAt(0).setBackgroundResource(
					R.drawable.btn_up_style);
		}
	}

	public class MyPagerAdapter1 extends FragmentStatePagerAdapter {


	    public MyPagerAdapter1(FragmentManager fm) {
	        super(fm);
	    }

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


	}
	
	private FragmentPagerAdapter mViewPagerAdapter = new FragmentPagerAdapter(
			getChildFragmentManager()) {

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
