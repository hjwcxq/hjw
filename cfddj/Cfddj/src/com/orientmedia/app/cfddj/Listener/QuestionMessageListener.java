//package tv.aniu.app.dzlc.Listener;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Timer;
//
//import tv.aniu.app.dzlc.R;
//import tv.aniu.app.dzlc.adapter.QuestionAdapter;
//import tv.aniu.app.dzlc.tool.audio.AudioRecorder;
//import tv.aniu.app.dzlc.ui.MainActivity;
//import tv.base.BaseActivity;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.graphics.Color;
//import android.media.AudioRecord;
//import android.media.MediaPlayer;
//import android.media.MediaRecorder;
//import android.os.AsyncTask;
//import android.os.CountDownTimer;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.view.GestureDetector;
//import android.view.Gravity;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.AbsListView;
//import android.widget.AbsListView.OnScrollListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.HorizontalScrollView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.tv189.dzlc.adapter.context.SystemContext;
//import com.tv189.dzlc.adapter.po.question.QuestionInfo;
//import com.tv189.dzlc.adapter.po.question.QuestionPageContent;
//import com.tv189.dzlc.adapter.service.impl.QuestionServiceImpl;
//import com.tv189.dzlc.adapter.service.inerface.QuestionService;
//
//@SuppressLint("ResourceAsColor")
//public class QuestionMessageListener implements OnClickListener {
//
//	public boolean isVip;
//	private Activity act;
//	private Fragment fragment;
//	private String SUCCESSPRE = "successpre";
//	private String SUCCESSPRE_END = "successpre_end";
//	private String ERRORPRE = "errorpre";
//
//	private RelativeLayout surfaceViewBorder;
//	private LinearLayout questionBorder, myQuestionBorder, questErrorBorder,
//			myQuestErrorBorder, questionLayout, myQuestionLayout, expertList;
//	private ProgressBar questionPb, myQuestionPb;
//	private ListView questionListView, myQuestionListView;
//	private QuestionPageContent questionDataInfo, myQuestionDataInfo,
//			questionDataInfo_lastdata;
//	private QuestionAdapter questionAdapter, myQuestionAdapter;
//	private int currentShowId;
//	// 语音留言相关 9.28
//	private Button submitBtn, questErrorBtn, myQuestErrorBtn, gotoVoice,
//			gotoMsg, sayVoice;
//	private boolean isRecording = false;
//	private boolean ulawRunning = false;
//	private AudioRecord mRecorder;
//	private CountDownTimer mCountDownTimer;
//	private int voiceLen = 0;
//	private int mDuration = 30 * 1000;
//	boolean isLongClick = false;
//	private String voice_string = "";
//	private Thread recordThread;
//	private AudioRecorder mr;
//	private static int MAX_TIME = 15; // 最长录制时间，单位秒，0为无时间限制
//	private static int MIX_TIME = 1; // 最短录制时间，单位秒，0为无时间限制，建议设为1
//
//	private static int RECORD_NO = 0; // 不在录音
//	private static int RECORD_ING = 1; // 正在录音
//	private static int RECODE_ED = 2; // 完成录音
//
//	private static int RECODE_STATE = 0; // 录音的状态
//
//	private static float recodeTime = 0.0f; // 录音的时间
//	private static double voiceValue = 0.0; // 麦克风获取的音量值
//
//	private ImageView dialog_img;
//	private static boolean playState = false; // 播放状态
//	final MediaRecorder recorder = new MediaRecorder();
//	// private RecordData recordData;
//	// ------
//	private EditText inputET;
//	public boolean threadRunable = true;
//	public Thread refreshThread;
//	private int REFRESH_DELAY = 2 * 60 * 1000;
//	private LinearLayout refreshLayout, fullScreemLayout, VoiceLayout,
//			MsgLayout;
//	private TextView questionTv, myQuestionTv;
//	private HorizontalScrollView expertListScroll;
//	private String sendMsg = "";
//	private Dialog subcribleDialog;
//	private Dialog dialog;
//	private InputMethodManager imm;
//	private GestureDetector detector;
//	private ProgressDialog pgPlayDialog;
//	private MediaPlayer mMediaPlayer;
//
//	public QuestionMessageListener() {
//		init();
//		// if (!SystemContext.getInstance(act).isUserLogon()) {
//		// questionBorder.setVisibility(View.VISIBLE);
//		// myQuestionBorder.setVisibility(View.GONE);
//		// questionListView.setVisibility(View.GONE);
//		// questErrorBorder.setVisibility(View.GONE);
//		// questionPb.setVisibility(View.VISIBLE);
//		// questionLayoutClick(questionLayout);
//		//
//		// }
//
//		new QuestQuestionTask(false).execute();
//
//		// imm.hideSoftInputFromWindow(inputET.getWindowToken(),
//		// InputMethodManager.HIDE_NOT_ALWAYS);
//		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//		// imm.toggleSoftInputFromWindow(inputET.getWindowToken(), 0, 0);
//	}
//
//	private void init() {
//		surfaceViewBorder = (RelativeLayout) fragment.getView().findViewById(
//				R.id.surfaceViewBorder);
//		questionBorder = (LinearLayout) fragment.getView().findViewById(
//				R.id.questionBorder);
//		myQuestionBorder = (LinearLayout) fragment.getView().findViewById(
//				R.id.myQuestionBorder);
//		questErrorBorder = (LinearLayout) fragment.getView().findViewById(
//				R.id.questErrorBorder);
//		myQuestErrorBorder = (LinearLayout) fragment.getView().findViewById(
//				R.id.myQuestErrorBorder);
//		questionLayout = (LinearLayout) fragment.getView().findViewById(
//				R.id.questionLayout);
//		myQuestionLayout = (LinearLayout) fragment.getView().findViewById(
//				R.id.myQuestionLayout);
//		expertList = (LinearLayout) fragment.getView().findViewById(
//				R.id.expert_list);
//		fullScreemLayout = (LinearLayout) fragment.getView().findViewById(
//				R.id.fullScreemLayout);
//		refreshLayout = (LinearLayout) fragment.getView().findViewById(
//				R.id.refreshLayout);
//		questionTv = (TextView) fragment.getView()
//				.findViewById(R.id.questionTv);
//		myQuestionTv = (TextView) fragment.getView().findViewById(
//				R.id.myQuestionTv);
//		questionPb = (ProgressBar) fragment.getView().findViewById(
//				R.id.questionPb);
//		myQuestionPb = (ProgressBar) fragment.getView().findViewById(
//				R.id.myQuestionPb);
//		questionListView = (ListView) fragment.getView().findViewById(
//				R.id.questionListView);
//		myQuestionListView = (ListView) fragment.getView().findViewById(
//				R.id.myQuestionListView);
//		questErrorBtn = (Button) fragment.getView().findViewById(
//				R.id.questErrorBtn);
//		myQuestErrorBtn = (Button) fragment.getView().findViewById(
//				R.id.myQuestErrorBtn);
//		expertListScroll = (HorizontalScrollView) fragment.getView()
//				.findViewById(R.id.expertListScroll);
//		inputET = (EditText) fragment.getView().findViewById(R.id.inputET);
//		inputET.clearFocus();
//		// 12.5 创建界面时设置焦点（我认为是listview在和edit在抢焦点）
//		inputET.setFocusableInTouchMode(true);
//		submitBtn = (Button) fragment.getView().findViewById(R.id.submitBtn);
//		// 语音留言相关 9.28————————————————————————
//		gotoMsg = (Button) fragment.getView().findViewById(R.id.btn_goto_msg);
//		gotoVoice = (Button) fragment.getView().findViewById(
//				R.id.btn_goto_voice);
//		// if (SystemContext.VOICESIGN=="voicesign") {
//		// gotoVoice.setVisibility(View.VISIBLE);
//		// }
//
//		sayVoice = (Button) fragment.getView().findViewById(R.id.btn_say_voice);
//		VoiceLayout = (LinearLayout) fragment.getView().findViewById(
//				R.id.inputVoiceLayout);
//		MsgLayout = (LinearLayout) fragment.getView().findViewById(
//				R.id.inputMsgLayout);
//
//		// Q： 9.5 得问问//A：11.27显示隐藏软键盘
//		// 老方法
//		imm = ((InputMethodManager) act
//				.getSystemService(Context.INPUT_METHOD_SERVICE));
//
//		// 新方法有问题
//		// imm = (InputMethodManager) act
//		// .getSystemService(Context.INPUT_METHOD_SERVICE);
//		// imm.hideSoftInputFromWindow(inputET.getWindowToken(), 0);
//
//		// 12.19手动加入软键盘焦点
//		inputET.setFocusableInTouchMode(true);
//
//		questionAdapter = new QuestionAdapter(act, this.isVip);
//		myQuestionAdapter = new QuestionAdapter(act, this.isVip);
//		questionListView.setAdapter(questionAdapter);
//		myQuestionListView.setAdapter(myQuestionAdapter);
//		setOnClickListener();
//		setListViewListener();
//		startTimer();
//	}
//
//	private void setOnClickListener() {
//		questionLayout.setOnClickListener(this);
//		myQuestionLayout.setOnClickListener(this);
//		expertList.setOnClickListener(this);
//		submitBtn.setOnClickListener(this);
//		fullScreemLayout.setOnClickListener(this);
//		refreshLayout.setOnClickListener(this);
//		questErrorBtn.setOnClickListener(this);
//		myQuestErrorBtn.setOnClickListener(this);
//		// 语音留言相关 9.28
//		gotoMsg.setOnClickListener(this);
//		gotoVoice.setOnClickListener(this);
//		// 录音功能
//		sayVoice.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					if (RECODE_STATE != RECORD_ING) {
//						scanOldFile();
//						// String uid_mic = SystemContext.getInstance(act)
//						// .getUid();
//						mr = new AudioRecorder("myvoice");
//						// mr = new AudioRecorder(uid_mic + "_voice");
//						RECODE_STATE = RECORD_ING;
//						showVoiceDialog();
//						System.out.println("长按。。。开始录音");
//						try {
//							mr.start();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						mythread();
//					}
//
//					break;
//				case MotionEvent.ACTION_UP:
//					if (RECODE_STATE == RECORD_ING) {
//						RECODE_STATE = RECODE_ED;
//						if (dialog.isShowing()) {
//							dialog.dismiss();
//						}
//						try {
//							mr.stop();
//							System.out.println("抬起。。。停止录音并储存" + getAmrPath());
//							voiceValue = 0.0;
//							sendvoice();
//							// 不得已而为之，语音发送没有成功与否的返回值，只能在这里强制刷新列表
//							// new QuestMyQuestionTask().execute();
//							if (questionBorder.getVisibility() == View.VISIBLE) {
//								SystemContext.last_refresh_data = "";
//								new QuestQuestionTask(false).execute();
//
//							} else if (myQuestionBorder.getVisibility() == View.VISIBLE) {
//								new QuestMyQuestionTask().execute();
//							}
//							// new MoreQuestionTask().execute();
//							// Timer timer_re = new Timer();
//							// timer_re.schedule(new TimerTask() {
//							//
//							// @Override
//							// public void run() {
//							// new MoreQuestionTask().execute();
//							// }
//							// }, 4000);
//
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//
//						if (recodeTime < MIX_TIME) {
//							showWarnToast();
//							RECODE_STATE = RECORD_NO;
//						}
//					}
//
//					break;
//				}
//				return false;
//			}
//		});
//		// 软键盘控制
//		inputET.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				inputET.clearFocus();
//				inputET.requestFocus();
//				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
//						InputMethodManager.HIDE_IMPLICIT_ONLY);
//				// 试验12.4 手动添加edit焦点，解决输入框不能输入文字问题
//				// inputET.setFocusableInTouchMode(true);
//			}
//		});
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.questionLayout:
//			questionLayoutClick(v);
//			break;
//		case R.id.myQuestionLayout:
//			questionLayoutClick(v);
//			break;
//		case R.id.expert_list:
//			// questionLayoutClick(v);
//			// ((BaseActivity) act).toIntent((BaseActivity)act,
//			// UserCenter.class);
//			((MainActivity) act).updateCenterView(4, "");
//			break;
//		case R.id.questErrorBtn:
//			SystemContext.last_refresh_data = "";
//			new QuestQuestionTask(false).execute();
//			break;
//		case R.id.myQuestErrorBtn:
//			new QuestMyQuestionTask().execute();
//			break;
//		case R.id.submitBtn: // 点击发送文字
//			SystemContext.somecode = SystemContext.recode_submit;
//			SystemContext.something = SystemContext.rething_submit;
//
//			hideInputMethod();
//			submitMessage();
//			break;
//		case R.id.fullScreemLayout:
//			msgFullScreen(v);
//			break;
//		case R.id.refreshLayout:
//			refreshClick(v);
//			break;
//		// 语音留言相关 9.28
//		case R.id.btn_goto_msg:
//			goto_msg(v);
//			break;
//		case R.id.btn_goto_voice:
//			SystemContext.somecode = SystemContext.recode_submit;
//			SystemContext.something = SystemContext.rething_submit;
//			goto_voice(v);
//			break;
//		default:
//			break;
//		}
//	}
//
//	public double getAmplitude() {
//		if (recorder != null) {
//			return (recorder.getMaxAmplitude());
//		} else
//			return 0;
//	}
//
//	/**
//	 * @param 切换到文字输入界面
//	 */
//	private void goto_msg(View v) {
//		// TODO Auto-generated method stub
//		MsgLayout.setVisibility(View.VISIBLE);
//		VoiceLayout.setVisibility(View.GONE);
//	}
//
//	/**
//	 * @param 切换到语音输入界面
//	 */
//	private void goto_voice(View v) {
//		// TODO Auto-generated method stub
//		if (!SystemContext.getInstance(act).isUserLogon()) {
//			((BaseActivity) act).login(loginCallback);
//			return;
//		}
//		if (!this.isVip) {
//			if (subcribleDialog == null || !subcribleDialog.isShowing()) {
//				subcribleDialog = UserPayFlow.startAuth(act, null);
//				MsgLayout.setVisibility(View.VISIBLE);
//				VoiceLayout.setVisibility(View.GONE);
//			}
//		}
//		if (this.isVip) {
//			// new SubmitMyMessageTask().execute(sendMsg);
//			// new SaveRecordAsyncTask().execute(target);
//			MsgLayout.setVisibility(View.GONE);
//			VoiceLayout.setVisibility(View.VISIBLE);
//		}
//
//	}
//
//	private void setListViewListener() {
//		questionListView.setOnScrollListener(new OnScrollListener() {
//			@Override
//			public void onScrollStateChanged(final AbsListView view,
//					int scrollState) {
//				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
//						View lastView = view.getChildAt(view.getChildCount() - 1);
//						if (lastView != null) {
//							if ((lastView.getHeight() + lastView.getTop()) == questionListView
//									.getHeight()) {
//								if (questionDataInfo != null) {
//									new MoreQuestionTask().execute();
//								}
//							}
//						}
//					}
//				}
//			}
//
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, final int totalItemCount) {
//				if (totalItemCount == 0) {
//					return;
//				}
//
//				if (firstVisibleItem + visibleItemCount >= totalItemCount) {
//				}
//			}
//		});
//		myQuestionListView.setOnScrollListener(new OnScrollListener() {
//
//			@Override
//			public void onScrollStateChanged(final AbsListView view,
//					int scrollState) {
//				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
//						View lastView = view.getChildAt(view.getChildCount() - 1);
//						if ((lastView.getHeight() + lastView.getTop()) == myQuestionListView
//								.getHeight()) {
//							if (myQuestionDataInfo != null) {
//								new MoreMyQuestionTask().execute();
//							}
//						}
//					}
//				}
//			}
//
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, final int totalItemCount) {
//				if (totalItemCount == 0) {
//					return;
//				}
//
//				if (firstVisibleItem + visibleItemCount >= totalItemCount) {
//				}
//			}
//		});
//	}
//
//
//	/***
//	 * 点击发送，用户登录后进行重新鉴权
//	 */
//	CallBackListener loginCallback = new CallBackListener() {
//		@Override
//		public void execute() {
//			CallBackListener successCallback = new CallBackListener() {
//				@Override
//				public void execute() {
//					QuestionMessageListener.this.isVip = true;
//				}
//			};
//			CallBackListener failCallBack = new CallBackListener() {
//				@Override
//				public void execute() {
//					QuestionMessageListener.this.isVip = false;
//				}
//			};
//			new AuthAsyncRequest(act, successCallback, failCallBack);
//		}
//	};
//
//	/**
//	 * 发送语音
//	 */
//	private void sendvoice() {
//		// String uid = SystemContext.getInstance((MainActivity) act).getUid();
//		// TODO Auto-generated method stub
//		File target = new File("mnt/sdcard/gplz/my/myvoice.m4a");
//		// File target = new File("mnt/sdcard/gplz/my/" + uid + "_voice.m4a");
//		new SaveRecordAsyncTask().execute(target);
//
//	}
//
//	/**
//	 * 发送文字
//	 */
//	private String oldSendMsg = "";
//
//	private void submitMessage() {
//		sendMsg = inputET.getText().toString().trim();// ps：trim()去空格
//		if (!SystemContext.getInstance(act).isUserLogon()) {
//			((BaseActivity) act).login(loginCallback);
//			return;
//		}
//		if (!this.isVip) {
//			if (subcribleDialog == null || !subcribleDialog.isShowing()) {
//				subcribleDialog = UserPayFlow.startAuth(act, null);
//			}
//		}
//
//		if (chencMsg(sendMsg) && this.isVip) {
//			if (oldSendMsg.equals(sendMsg)) {
//				((MainActivity) act).ToastCheese("请勿重复发送");
//				return;
//			}
//			oldSendMsg = sendMsg;
//			new SubmitMyMessageTask().execute(sendMsg);
//		}
//	}
//
//	private boolean chencMsg(String msg) {
//		if (msg == null || msg.equals("")) {
//			((MainActivity) act).ToastCheese("未输入内容");
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * 每隔两分钟刷新一次提问的列表
//	 */
//	private void startTimer() {
//		threadRunable = true;
//		if (refreshThread == null) {
//			refreshThread = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					while (threadRunable) {
//						if (questionListView.getVisibility() == View.VISIBLE) {
//							act.runOnUiThread(new Runnable() {
//								public void run() {
//									SystemContext.last_refresh_data = "";
//									new QuestQuestionTask(true).execute();
//									// ((VideoInteractionFragment) fragment).new
//									// ExpertListTask()
//									// .execute();
//								}
//							});
//						}
//						try {
//							Thread.sleep(REFRESH_DELAY);
//						} catch (Exception e) {
//						}
//					}
//				}
//			});
//		}
//		refreshThread.start();
//
//	}
//
//	// 录音时显示Dialog
//	void showVoiceDialog() {
//		dialog = new Dialog((MainActivity) act, R.style.DialogStyle);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		dialog.setContentView(R.layout.my_dialog);
//		dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
//		dialog.show();
//	}
//
//	// 录音Dialog图片随声音大小切换
//	void setDialogImage() {
//		if (voiceValue < 200.0) {
//			dialog_img.setImageResource(R.drawable.record_animate_01);
//		} else if (voiceValue > 200.0 && voiceValue < 400) {
//			dialog_img.setImageResource(R.drawable.record_animate_02);
//		} else if (voiceValue > 400.0 && voiceValue < 800) {
//			dialog_img.setImageResource(R.drawable.record_animate_03);
//		} else if (voiceValue > 800.0 && voiceValue < 1600) {
//			dialog_img.setImageResource(R.drawable.record_animate_04);
//		} else if (voiceValue > 1600.0 && voiceValue < 3200) {
//			dialog_img.setImageResource(R.drawable.record_animate_05);
//		} else if (voiceValue > 3200.0 && voiceValue < 5000) {
//			dialog_img.setImageResource(R.drawable.record_animate_06);
//		} else if (voiceValue > 5000.0 && voiceValue < 7000) {
//			dialog_img.setImageResource(R.drawable.record_animate_07);
//		} else if (voiceValue > 7000.0 && voiceValue < 10000.0) {
//			dialog_img.setImageResource(R.drawable.record_animate_08);
//		} else if (voiceValue > 10000.0 && voiceValue < 14000.0) {
//			dialog_img.setImageResource(R.drawable.record_animate_09);
//		} else if (voiceValue > 14000.0 && voiceValue < 17000.0) {
//			dialog_img.setImageResource(R.drawable.record_animate_10);
//		} else if (voiceValue > 17000.0 && voiceValue < 20000.0) {
//			dialog_img.setImageResource(R.drawable.record_animate_11);
//		} else if (voiceValue > 20000.0 && voiceValue < 24000.0) {
//			dialog_img.setImageResource(R.drawable.record_animate_12);
//		} else if (voiceValue > 24000.0 && voiceValue < 28000.0) {
//			dialog_img.setImageResource(R.drawable.record_animate_13);
//		} else if (voiceValue > 28000.0) {
//			dialog_img.setImageResource(R.drawable.record_animate_14);
//		}
//	}
//
//	// 录音时间太短时Toast显示
//	void showWarnToast() {
//		Toast toast = new Toast((MainActivity) act);
//		LinearLayout linearLayout = new LinearLayout((MainActivity) act);
//		linearLayout.setOrientation(LinearLayout.VERTICAL);
//		linearLayout.setPadding(20, 20, 20, 20);
//
//		// 定义一个ImageView
//		ImageView imageView = new ImageView((MainActivity) act);
//		imageView.setImageResource(R.drawable.voice_to_short); // 图标
//
//		TextView mTv = new TextView((MainActivity) act);
//		mTv.setText("时间太短   录音失败");
//		mTv.setTextSize(14);
//		mTv.setTextColor(Color.WHITE);// 字体颜色
//		// mTv.setPadding(0, 10, 0, 0);
//
//		// 将ImageView和ToastView合并到Layout中
//		linearLayout.addView(imageView);
//		linearLayout.addView(mTv);
//		linearLayout.setGravity(Gravity.CENTER);// 内容居中
//		linearLayout.setBackgroundResource(R.drawable.record_bg);// 设置自定义toast的背景
//
//		toast.setView(linearLayout);
//		toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间 100为向下移100dp
//		toast.show();
//	}
//
//	// 获取文件手机路径
//	private String getAmrPath() {
//		File file = new File(Environment.getExternalStorageDirectory(),
//				"gplz/my/voice.m4a");
//		return file.getAbsolutePath();
//	}
//
//	// 录音计时线程
//	void mythread() {
//		recordThread = new Thread(ImgThread);
//		recordThread.start();
//	}
//
//	// 录音线程
//	private Runnable ImgThread = new Runnable() {
//
//		@Override
//		public void run() {
//			recodeTime = 0.0f;
//			while (RECODE_STATE == RECORD_ING) {
//				if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
//					imgHandle.sendEmptyMessage(0);
//				} else {
//					try {
//						Thread.sleep(200);
//						recodeTime += 0.2;
//						if (RECODE_STATE == RECORD_ING) {
//							voiceValue = mr.getAmplitude();
//							imgHandle.sendEmptyMessage(1);
//						}
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//
//		Handler imgHandle = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//
//				switch (msg.what) {
//				case 0:
//					// 录音超过15秒自动停止
//					if (RECODE_STATE == RECORD_ING) {
//						RECODE_STATE = RECODE_ED;
//						if (dialog.isShowing()) {
//							dialog.dismiss();
//						}
//						try {
//							mr.stop();
//							voiceValue = 0.0;
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//
//						if (recodeTime < 1.0) {
//							showWarnToast();
//							RECODE_STATE = RECORD_NO;
//						} else {
//							// record.setText("录音完成!点击重新录音");
//							// luyin_txt.setText("录音时间："+((int)recodeTime));
//							// luyin_path.setText("文件路径："+getAmrPath());
//						}
//					}
//					break;
//				case 1:
//					setDialogImage();
//					break;
//				default:
//					break;
//				}
//
//			}
//		};
//	};
//
//	// 删除老文件
//	void scanOldFile() {
//		// String uid = SystemContext.getInstance((MainActivity) act).getUid();
//		// File file = new File(Environment.getExternalStorageDirectory(),
//		// "/gplz/my/"+uid+"_voice.amr");
//		File file = new File(Environment.getExternalStorageDirectory(),
//				"/gplz/my/myvoice.m4a");
//		if (file.exists()) {
//			file.delete();
//		}
//	}
//
//	/**
//	 * 点击刷新
//	 * 
//	 * @param v
//	 */
//	public void refreshClick(View v) {
//		if (questionBorder.getVisibility() == View.VISIBLE) {
//
//			new QuestQuestionTask(false).execute();
//
//		} else if (myQuestionBorder.getVisibility() == View.VISIBLE) {
//			new QuestMyQuestionTask().execute();
//		}
//	}
//
//	/**
//	 * 留言分类点击
//	 * 
//	 * @param v
//	 */
//	// houdepeng 提问专区 和 我的提问 切换
////	@SuppressLint("ResourceAsColor")
////	public void questionLayoutClick(View v) {
////		if (R.id.questionLayout == v.getId()) {
////			if (questionLayout.getId() != currentShowId) {
////				currentShowId = questionLayout.getId();
////				questionLayout.setBackgroundDrawable(null);
////				myQuestionLayout.setBackgroundResource(R.drawable.btn_white);
////				questionTv.setTextColor(Color.parseColor(act
////						.getString(R.color.tv_red)));
////				myQuestionTv.setTextColor(Color.parseColor(act
////						.getString(R.color.tv_gray)));
////				if (questionDataInfo == null) {
////					new QuestQuestionTask(false).execute();
////				} else {
////					questionBorder.setVisibility(View.VISIBLE);
////					questionListView.setVisibility(View.VISIBLE);
////					myQuestionBorder.setVisibility(View.GONE);
////					new QuestQuestionTask(true).execute();
////				}
////			}
////		} else if (myQuestionLayout.getId() == v.getId()) {
////			if (!SystemContext.getInstance(act).isUserLogon()) {
////				((BaseActivity) act).login();
////				return;
////			}
////			if (myQuestionLayout.getId() != currentShowId) {
////				currentShowId = myQuestionLayout.getId();
////				questionLayout.setBackgroundResource(R.drawable.btn_white);
////				myQuestionLayout.setBackgroundDrawable(null);
////				myQuestionTv.setTextColor(Color.parseColor(act
////						.getString(R.color.tv_red)));
////				questionTv.setTextColor(Color.parseColor(act
////						.getString(R.color.tv_gray)));
////				if (myQuestionDataInfo == null) {
////					new QuestMyQuestionTask().execute();
////				} else {
////					questionBorder.setVisibility(View.GONE);
////					myQuestionBorder.setVisibility(View.VISIBLE);
////					myQuestionListView.setVisibility(View.VISIBLE);
////					// 测试刷新
////					new QuestMyQuestionTask().execute();
////
////				}
////			}
////		}
////		// else if (expertList.getId() == v.getId()) {
////		// if (!SystemContext.getInstance(act).isUserLogon()) {
////		// ((BaseActivity) act).login();
////		// return;
////		// }
////		// ((BaseActivity) act).toIntent(act, UserCenter.class);
////		// }
////	}
//
//	/****
//	 * 隐藏输入法.
//	 */
//	public void hideInputMethod() {
//		try {
//			imm.hideSoftInputFromWindow(inputET.getWindowToken(), 0);
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 获取提问专区内容
//	 * 
//	 * @author hujunjng 2013-6-25下午14:00
//	 * 
//	 */
//	// （刷新调用）
//	class QuestQuestionTask extends AsyncTask<Void, Void, String> {
//		boolean isFromRT = false;
//
//		public QuestQuestionTask(boolean isFromRefreshThread) {
//			isFromRT = isFromRefreshThread;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			// synchronized (SYNC_KEY) {
//			questionPb.setVisibility(View.GONE);
//			if (result.equalsIgnoreCase(SUCCESSPRE)) {
//				questionListView.setVisibility(View.VISIBLE);
//			} else if (result.equalsIgnoreCase(SUCCESSPRE_END)) {
//				((MainActivity) act).ToastCheese("已是最后一页");
//				questionListView.setVisibility(View.VISIBLE);
//			} else {
//				questErrorBorder.setVisibility(View.VISIBLE);
//			}
//			// }
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//			synchronized (MyConstant.SYNC) {
//				try {
//					int currentPage = 0;
//					int totalPage = 1;
//					if (currentPage < totalPage) {// HDMARK
//						QuestionServiceImpl questionService = new QuestionServiceImpl(
//								act);
//						if (SystemContext.last_refresh_data != "") {
//							// 按时间查询
//							// System.out.print("测试增量刷新|"
//							// + SystemContext.last_refresh_data + "|");
//							// 按照时间查询出的list
//							QuestionPageContent questionInfo_lastdata = questionService
//									.questionList("001",
//											String.valueOf(currentPage + 1),
//											"20");
//
//							questionDataInfo_lastdata = questionInfo_lastdata;
//							// 合并两个list
//							if (questionDataInfo != null
//									&& questionDataInfo_lastdata != null
//									&& questionDataInfo_lastdata.getContent()
//											.size() != 0) {
//
//								questionDataInfo_lastdata.getContent().addAll(
//										questionDataInfo.getContent());
//
//								SystemContext.last_refresh_data = questionDataInfo_lastdata
//										.getContent().get(0).getDate();
//								// 加载合并后的
//								questionAdapter
//										.setItems(questionDataInfo_lastdata
//												.getContent());
//							} else {
//								SystemContext.last_refresh_data = "";
//								QuestionPageContent questionDataInfoT = questionService
//										.questionList(
//												"001",
//												String.valueOf(currentPage + 1),
//												"20");
//
//								if (questionDataInfoT != null) {
//									questionDataInfo = questionDataInfoT;
//									// 获取最后一条消息的时间
//									SystemContext.last_refresh_data = questionDataInfo
//											.getContent().get(0).getDate();
//
//									questionAdapter.setItems(questionDataInfo
//											.getContent());
//								} else {
//									questionDataInfo = null;
//									return ERRORPRE;
//								}
//							}
//							// 按时间查询结束
//						} else {
//
//							QuestionPageContent questionDataInfoT = questionService
//									.questionList("001",
//											String.valueOf(currentPage + 1),
//											"20");
//
//							if (questionDataInfoT != null) {
//								questionDataInfo = questionDataInfoT;
//								// 获取最后一条消息的时间
//								SystemContext.last_refresh_data = questionDataInfo
//										.getContent().get(0).getDate();
//
//								questionAdapter.setItems(questionDataInfo
//										.getContent());
//							} else {
//								questionDataInfo = null;
//								return ERRORPRE;
//							}
//						}
//					} else {
//						return SUCCESSPRE_END;
//					}
//				} catch (GplzServiceException e) {
//					e.getErrCode();
//					e.getErrMsg();
//
//				}
//				return SUCCESSPRE;
//			}
//		}
//
//		@Override
//		protected void onPreExecute() {
//
//			// synchronized (SYNC_KEY) {
//			if (!isFromRT) {
//				questionListView.setVisibility(View.VISIBLE);
//				myQuestionListView.setVisibility(View.GONE);
//			}
//			questionListView.setVisibility(View.GONE);
//			questErrorBorder.setVisibility(View.GONE);
//			questionPb.setVisibility(View.VISIBLE);
//			questionAdapter.setItems(new ArrayList<QuestionInfo>());
//			// }
//
//		}
//	}
//
//	/**
//	 * 加载评论
//	 * 
//	 * @author EsaFans
//	 * 
//	 */
//	class MoreQuestionTask extends AsyncTask<Void, Void, String> {
//
//		@Override
//		protected void onPostExecute(String result) {
//			// synchronized (SYNC_KEY) {
//			questionPb.setVisibility(View.GONE);
//			if (result.equalsIgnoreCase(SUCCESSPRE)) {
//				questionListView.setVisibility(View.VISIBLE);
//			} else if (result.equalsIgnoreCase(SUCCESSPRE_END)) {
//				((MainActivity) act).ToastCheese("已是最后一页");
//				questionListView.setVisibility(View.VISIBLE);
//			} else {
//				questErrorBorder.setVisibility(View.VISIBLE);
//			}
//			// }
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//			// synchronized (SYNC_KEY) {
//			// 加载更多
//			try {
//				int currentPage = 1;// 为什么是1啊？？？
//				int totalPage = 1;
//				if (questionDataInfo != null) {
//					currentPage = questionDataInfo.getNumber() + 1;
//					totalPage = questionDataInfo.getTotalPages();
//				}
//				if (currentPage < totalPage) {// HDP 加一下清空时间
//					SystemContext.last_refresh_data = "";
//					QuestionServiceImpl questionService = new QuestionServiceImpl(
//							act);
//					QuestionPageContent questionDataInfoT = questionService
//							.questionList("001",
//									String.valueOf(currentPage + 1), "20");
//					if (questionDataInfoT != null) {
//						questionDataInfo = questionDataInfoT;
//						SystemContext.last_refresh_data = questionDataInfo
//								.getContent().get(0).getDate();
//						questionAdapter.addItems(questionDataInfo.getContent());
//					} else {
//						return ERRORPRE;
//					}
//				} else {
//					return SUCCESSPRE_END;
//				}
//
//			} catch (Exception e) {
//				return ERRORPRE;
//			}
//			return SUCCESSPRE;
//			// }
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// synchronized (SYNC_KEY) {
//			questionBorder.setVisibility(View.VISIBLE);
//			myQuestionBorder.setVisibility(View.GONE);
//			questionListView.setVisibility(View.GONE);
//			questErrorBorder.setVisibility(View.GONE);
//			questionPb.setVisibility(View.VISIBLE);
//			// }
//		}
//	}
//
//	/**
//	 * 加载我的留言
//	 * 
//	 * @author EsaFans
//	 * 
//	 */
//	class QuestMyQuestionTask extends AsyncTask<Void, Void, String> {
//
//		@Override
//		protected void onPostExecute(String result) {
//			myQuestionPb.setVisibility(View.GONE);
//			if (result.equalsIgnoreCase(SUCCESSPRE)) {
//				myQuestionListView.setVisibility(View.VISIBLE);
//			} else if (result.equalsIgnoreCase(SUCCESSPRE_END)) {
//				((MainActivity) act).ToastCheese("已是最后一页");
//				myQuestionListView.setVisibility(View.VISIBLE);
//			} else {
//				myQuestErrorBorder.setVisibility(View.VISIBLE);
//			}
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//			try {
//				int currentPage = 0;
//				int totalPage = 1;
//				if (currentPage < totalPage) {
//					// 首页的我的提问会跳转到这里
//					QuestionServiceImpl questionService = new QuestionServiceImpl(
//							act);
//					QuestionPageContent questionDataInfoT = questionService
//							.questionMy("001", String.valueOf(currentPage + 1),
//									"20");
//					if (questionDataInfoT != null) {
//						myQuestionDataInfo = questionDataInfoT;
//						myQuestionAdapter.setItems(myQuestionDataInfo
//								.getContent());
//
//					} else {
//						myQuestionDataInfo = null;
//						return ERRORPRE;
//					}
//				} else {
//					return SUCCESSPRE_END;
//				}
//
//			} catch (GplzServiceException e) {
//				e.printStackTrace();
//				return ERRORPRE;
//			}
//			return SUCCESSPRE;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			questionBorder.setVisibility(View.GONE);
//			myQuestionBorder.setVisibility(View.VISIBLE);
//			questionListView.setVisibility(View.GONE);
//			myQuestErrorBorder.setVisibility(View.GONE);
//			myQuestionPb.setVisibility(View.VISIBLE);
//			myQuestionAdapter.setItems(new ArrayList<QuestionInfo>());
//		}
//	}
//
//	/**
//	 * 请求下一页---我的留言
//	 * 
//	 * @author EsaFans
//	 * 
//	 */
//	class MoreMyQuestionTask extends AsyncTask<Void, Void, String> {
//		@Override
//		protected void onPostExecute(String result) {
//			myQuestionPb.setVisibility(View.GONE);
//			if (result.equalsIgnoreCase(SUCCESSPRE)) {
//				myQuestionListView.setVisibility(View.VISIBLE);
//			} else if (result.equalsIgnoreCase(SUCCESSPRE_END)) {
//				((MainActivity) act).ToastCheese("已是最后一页");
//				myQuestionListView.setVisibility(View.VISIBLE);
//			} else {
//				myQuestErrorBorder.setVisibility(View.VISIBLE);
//			}
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//			try {
//				int currentPage = 1;
//				int totalPage = 1;
//				if (myQuestionDataInfo != null) {
//					currentPage = myQuestionDataInfo.getNumber() + 1;
//					totalPage = myQuestionDataInfo.getTotalPages();
//				}
//				if (currentPage < totalPage) {
//					QuestionServiceImpl questionService = new QuestionServiceImpl(
//							act);
//					// 首页我的留言跳过来的
//					QuestionPageContent questionDataInfoT = questionService
//							.questionMy("001", String.valueOf(currentPage + 1),
//									"20");
//
//					if (questionDataInfoT != null) {
//						myQuestionDataInfo = questionDataInfoT;
//						myQuestionAdapter.addItems(myQuestionDataInfo
//								.getContent());
//					} else {
//						return ERRORPRE;
//					}
//				} else {
//					return SUCCESSPRE_END;
//				}
//
//			} catch (Exception e) {
//				return ERRORPRE;
//			}
//			return SUCCESSPRE;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			questionBorder.setVisibility(View.GONE);
//			myQuestionBorder.setVisibility(View.VISIBLE);
//			myQuestionListView.setVisibility(View.GONE);
//			myQuestErrorBorder.setVisibility(View.GONE);
//			myQuestionPb.setVisibility(View.VISIBLE);
//		}
//	}
//
//	/**
//	 * 发送我的留言
//	 * 
//	 * @author EsaFans
//	 * 
//	 */
//
//	class SubmitMyMessageTask extends AsyncTask<String, Void, String> {
//
//		ProgressDialog progressDialog;
//		String errmsg = "提交失败,请稍候重试";
//
//		@Override
//		protected void onPostExecute(String result) {
//			try {
//				progressDialog.dismiss();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			if (result.equalsIgnoreCase("1")) {
//				((MainActivity) act).ToastCheese("提交成功");
//				inputET.setText("");
//				if (currentShowId == myQuestionLayout.getId()) {
//					new QuestMyQuestionTask().execute();
//				} else {
//					new QuestQuestionTask(false).execute();
//				}
//			} else if (result.equalsIgnoreCase("0")) {
//				((MainActivity) act).ToastCheese("提交成功，您的发言将在通过审核后显示");
//				inputET.setText("");
//				if (currentShowId == myQuestionLayout.getId()) {
//					new QuestMyQuestionTask().execute();
//				} else {
//					new QuestQuestionTask(false).execute();
//				}
//			} else {
//				((MainActivity) act).ToastCheese(errmsg);
//			}
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			try {
//				QuestionService questionService = new QuestionServiceImpl(act);
//				QuestionInfo question = questionService.questionAdd("2", "3",
//						params[0]);
//				return question == null ? ERRORPRE : question.getIsPass();
//
//			} catch (Exception e) {
//				return ERRORPRE;
//			}
//		}
//
//		@Override
//		protected void onPreExecute() {
//			try {
//				progressDialog.dismiss();
//			} catch (Exception e) {
//			}
//			progressDialog = new ProgressDialog(act);
//			progressDialog.setMessage("正在提交...");
//			progressDialog.show();
//		}
//
//	}
//
//	/*
//	 * 发送语音
//	 */
//	class SaveRecordAsyncTask extends AsyncTask<File, Void, String> {
//		ProgressDialog progressDialog;
//		String errmsg = "提交失败,请稍候重试";
//
//		protected void onPreExecute(String result) {
//			// try {
//			// progressDialog.dismiss();
//			// } catch (Exception e) {
//			// // TODO: handle exception
//			// }
//
//			// if (result.equalsIgnoreCase("1")) {
//			// ((MainActivity) act).ToastCheese("提交成功");
//			// new QuestMyQuestionTask().execute();
//			//
//			// } else if (result.equalsIgnoreCase("0")) {
//			((MainActivity) act).ToastCheese("提交成功，您的发言将在通过审核后显示");
//			// if (currentShowId == myQuestionLayout.getId()) {
//			// new QuestMyQuestionTask().execute();
//			// } else {
//			// ((MainActivity) act).ToastCheese(errmsg);
//			// }
//			// }
//		}
//
//		@Override
//		protected String doInBackground(File... params) {
//			try {
//				// File file = new
//				// File(Environment.getExternalStorageDirectory()
//				// + "gplz/tempG711Voice.wav");
//				QuestionService questionService = new QuestionServiceImpl(act);
//				QuestionInfo uploadVoice = questionService.upload(params[0]);
//				return uploadVoice == null ? ERRORPRE : uploadVoice.getIsPass();
//
//			} catch (Exception e) {
//				return ERRORPRE;
//			}
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// try {
//			// progressDialog.dismiss();
//			// } catch (Exception e) {
//			// }
//			// progressDialog = new ProgressDialog(act);
//			// progressDialog.setMessage("正在提交...");
//			// progressDialog.show();
//		}
//	}
//
//}
