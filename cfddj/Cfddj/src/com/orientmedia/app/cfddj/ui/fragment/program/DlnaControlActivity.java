//package tv.aniu.app.dzlc.ui.fragment.program;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import org.teleal.cling.support.model.TransportState;
//import org.teleal.cling.support.service.MyUpnpService;
//
//import tv.aniu.app.dzlc.R;
//import tv.aniu.app.dzlc.widget.MyVerticalSeekBar;
//import android.app.Activity;
//import android.content.res.Configuration;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.PopupWindow;
//import android.widget.SeekBar;
//import android.widget.SeekBar.OnSeekBarChangeListener;
//import android.widget.TextView;
//
//public class DlnaControlActivity extends Activity implements
//		View.OnClickListener {
//
//	private static final String LOGTAG = DlnaControlActivity.class.getName();
//	private Button dlna_back;
//	private TextView dlna_title;
//	private TextView dlna_clock;
//	private TextView dlna_play_statu;
//	private ImageButton dlna_seek_back;
//	private ImageButton dlna_play_pause;
//	private ImageButton dlna_seek_pre;
//	private ImageButton dlna_volume;
//	private SeekBar dlna_play_seekbar;
//	TextView dlna_cur_total_time;
//	
//	PopupWindow mPopupWindow;
//	View volume_popunwindwow ;
//	
//	private boolean isPlayButtonShow =true;
//
//	private MyVerticalSeekBar dlna_vol_seekbar;
//
//	//int moviceLength;
//	//String str_moviceLength;
//
//	// int currentPos=0;
//	int seekPos = 20;
//	int totalLength=0;
//	int currentPos =0 ;
//	int currentVolume = 0;
//	
//	int STATUS_PLAY = 0;
//	int STATUS_PAUSE = 1;
//	int STATUS_STOP = 2;
//	int STATUS_BUFFER = 3 ;
//
//	boolean isSeeking = false;
//
//	private MyUpnpService mMyUpnpService;
//
//	private final Timer timer = new Timer();
//	private TimerTask task;
//
//	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
//	Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case 1:// 每分钟
//				dlna_clock.setText(formatter.format(new Date()));
//				break;
//			default:
//			}
//			super.handleMessage(msg);
//		}
//	};
//
//	Handler updateHandler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			
//			case 2:// 更新当前时长
//			{
//				currentPos =msg.arg1;
//				dlna_play_seekbar.setProgress(currentPos);
//				if( msg.arg2!= totalLength)
//				{
//					totalLength = msg.arg2;
//					dlna_play_seekbar.setMax(totalLength);
//				}
//				dlna_cur_total_time.setText(formatTime(currentPos)+"/"+formatTime(totalLength));
//				break;
//			}
//			case 3://更新当前的状态
//			{
//				TransportState currentState =(TransportState)msg.obj;
//				if (currentState ==TransportState.PLAYING)
//				{
//					 dlna_play_statu.setText("DLNA 正在播放");
//					 isPlayButtonShow = true ;
//					 dlna_play_pause.setBackgroundResource(R.drawable.btn_dlna_pause_bg); 
//				 }
//				else if (currentState ==TransportState.TRANSITIONING)
//				{
//					dlna_play_statu.setText("DLNA 正在缓冲");
//				}
//				else if  (currentState ==TransportState.PAUSED_PLAYBACK)
//				{
//					dlna_play_statu.setText("DLNA 暂停播放");
//				}
//				else if  (currentState ==TransportState.STOPPED)
//				{
//					dlna_play_statu.setText("DLNA 停止播放");
//					
//					if(mMyUpnpService.getMyUpnpControl().getUpdateThread() != null)
//					{
//						mMyUpnpService.getMyUpnpControl().getUpdateThread().stopThread();
//					}
//					DlnaControlActivity.this.finish();
//					
//				} 
//				else
//				{
//					
//				}
//				break;
//			}
//			case 4 ://声音
//			{
//				currentVolume =msg.arg1;
//				dlna_vol_seekbar.setProgress(currentVolume);
//				break;
//			}
//			default:
//			}
//
//			super.handleMessage(msg);
//		}
//	};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		setContentView(R.layout.activity_dlna_control);
//		setView();
//		setEvent();
//
//		String sUri = getIntent().getStringExtra("Uri");
//		String sTitle = getIntent().getStringExtra("title");
//
//		dlna_title.setText(sTitle);
//
//		mMyUpnpService = MyUpnpService.getInstance();
//		mMyUpnpService.getMyUpnpControl().setUrlAndPlay(sUri,updateHandler);
//
//		// moviceLength = getIntent().getIntExtra("moviceLength",0);
//		// str_moviceLength =
//		// (moviceLength/60<10?"0"+(moviceLength/60):""+(moviceLength/60))+":"+(moviceLength%60<10?"0"+(moviceLength%60):(moviceLength%60));
//
//		dlna_clock.setText(formatter.format(new Date()));
//
//		dlna_play_statu.setText("DLNA 正在加载");
//		dlna_play_seekbar.setMax(100);
//		dlna_play_seekbar.setProgress(0);
//
//		dlna_cur_total_time.setText("00:00:00/00:00:00");
//		task = new TimerTask() {
//			int time = 0;
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//					Message message = new Message();
//					message.what = 1;// 每分钟
//					handler.sendMessage(message);
//
//
//			}
//		};
//		 timer.schedule(task, 60000, 60000); //每分钟执行一次，
//		//timer.schedule(task, 1000, 1000); // 每秒钟执行一次，
//	}
//
//	private void setView() {
//		dlna_back = ((Button) findViewById(R.id.dlna_back));
//		dlna_title = ((TextView) findViewById(R.id.dlna_title));
//		dlna_clock = ((TextView) findViewById(R.id.dlna_clock));
//		dlna_play_statu = ((TextView) findViewById(R.id.dlna_play_statu));
//		dlna_seek_back = ((ImageButton) findViewById(R.id.dlna_seek_back));
//		dlna_play_pause = ((ImageButton) findViewById(R.id.dlna_play_pause));
//		dlna_seek_pre = ((ImageButton) findViewById(R.id.dlna_seek_pre));
//		dlna_volume = ((ImageButton) findViewById(R.id.dlna_volume));
//		dlna_play_seekbar = ((SeekBar) findViewById(R.id.dlna_play_seekbar));
//		dlna_cur_total_time = ((TextView) findViewById(R.id.dlna_cur_total_time));
//		
//		LayoutInflater mLayoutInflater = (LayoutInflater) this.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
//        volume_popunwindwow = mLayoutInflater.inflate(R.layout.popup_dlna_volume, null);  
//         dlna_vol_seekbar = ((MyVerticalSeekBar)volume_popunwindwow.findViewById(R.id.dlna_vol_seekbar));
//       
//		
//	}
//
//	private void setEvent() {
//		dlna_back.setOnClickListener(this);
//		dlna_seek_back.setOnClickListener(this);
//		dlna_play_pause.setOnClickListener(this);
//		dlna_seek_pre.setOnClickListener(this);
//		dlna_volume.setOnClickListener(this);
//		dlna_play_seekbar.setOnSeekBarChangeListener(playSeekBarcli);
//		dlna_vol_seekbar.setOnSeekBarChangeListener(volSeekBarcli);
//	}
//
//	@Override
//	public void onClick(View view) {
//		// TODO Auto-generated method stub
//		if (view == dlna_back) {
//			if(mMyUpnpService.getMyUpnpControl().getUpdateThread() != null)
//			{
//				mMyUpnpService.getMyUpnpControl().getUpdateThread().stopThread();
//			}
//			this.finish();
//		} else if (view == dlna_play_pause) {
//			 if(isPlayButtonShow ) 
//			 {
//				 isPlayButtonShow = false ;
//				 dlna_play_pause.setBackgroundResource(R.drawable.btn_dlna_play_bg); 
//				 mMyUpnpService.getMyUpnpControl().pause();
//			 }
//			 else 
//			 {
//				 isPlayButtonShow = true;
//				 dlna_play_pause.setBackgroundResource(R.drawable.btn_dlna_pause_bg); 
//				 mMyUpnpService.getMyUpnpControl().play();
//			 }
//			 
//		} else if (view == dlna_seek_back) {
//			
//			int tmp = (dlna_play_seekbar.getProgress() - seekPos)>0?dlna_play_seekbar.getProgress() - seekPos:0;
//			currentPos = tmp ;
//			dlna_play_seekbar.setProgress(tmp);
//			dlna_cur_total_time.setText(formatTime(currentPos)+"/"+formatTime(totalLength)); 
//			mMyUpnpService.getMyUpnpControl().seek(formatTime(tmp));
//
//		}
//
//		else if (view == dlna_seek_pre) {
//			int tmp = (dlna_play_seekbar.getProgress() + seekPos)>0?dlna_play_seekbar.getProgress() + seekPos:0;
//			currentPos = tmp ;
//			dlna_play_seekbar.setProgress(tmp);
//			dlna_cur_total_time.setText(formatTime(currentPos)+"/"+formatTime(totalLength)); 
//			mMyUpnpService.getMyUpnpControl().seek(formatTime(tmp));
//		}
//		else if (view == dlna_volume)
//		{
//			if(mPopupWindow == null)
//			   mPopupWindow = new PopupWindow(volume_popunwindwow, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);  
//		     
//			
//            //int[] location = new int[2];
//            //dlna_volume.getLocationOnScreen(location);
//            //int x = location[0];
//            //int y = location[1];  
//            // 设置点击返回键使其消失，且不影响背景，此时setOutsideTouchable函数即使设置为false  
//            // 点击PopupWindow 外的屏幕，PopupWindow依然会消失；相反，如果不设置BackgroundDrawable  
//            // 则点击返回键PopupWindow不会消失，同时，即时setOutsideTouchable设置为true  
//            // 点击PopupWindow 外的屏幕，PopupWindow依然不会消失  
//            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));   
//            mPopupWindow.setOutsideTouchable(false); // 设置是否允许在外点击使其消失，到底有用没？
//            mPopupWindow.setTouchable(true);
//            mPopupWindow.showAsDropDown(dlna_volume,20,-315);//(dlna_volume,Gravity.LEFT|Gravity.TOP, x, y-155); 
//            //mPopupWindow.showAtLocation(dlna_volume,Gravity.LEFT|Gravity.TOP, x, y-155); 
//            mPopupWindow.update();
//            dlna_vol_seekbar.setProgress(currentVolume);
//			
//            
//            
//		}
//
//	}
//	
//	@Override
//	public void onBackPressed() {
//		if(mPopupWindow != null && mPopupWindow.isShowing())
//		{
//			mPopupWindow.dismiss();
//			return ;
//		}
//		if(mMyUpnpService.getMyUpnpControl().getUpdateThread() != null)
//		{
//			mMyUpnpService.getMyUpnpControl().getUpdateThread().stopThread();
//		}
//		this.finish();
//	}
//	
//
//	private OnSeekBarChangeListener volSeekBarcli = new OnSeekBarChangeListener() {
//
//		@Override
//		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
//			// TODO Auto-generated method stub
//			int currentprogress = arg0.getProgress();
//			// 改变音量
//			currentVolume = currentprogress;
//			Log.i(LOGTAG,"vol:"+currentprogress);
//			mMyUpnpService.getMyUpnpControl().setVolume(currentVolume);
//		}
//
//		@Override
//		public void onStartTrackingTouch(SeekBar seekBar) {
//			// TODO Auto-generated method stub
//			Log.i(LOGTAG,"vol start change");
//		}
//
//		@Override
//		public void onStopTrackingTouch(SeekBar seekBar) {
//			// TODO Auto-generated method stub
//			Log.i(LOGTAG,"vol end change");
//			mMyUpnpService.getMyUpnpControl().setVolume(currentVolume);
//		}
//
//	};
//
//	private OnSeekBarChangeListener playSeekBarcli = new OnSeekBarChangeListener() {
//
//		@Override
//		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
//			// TODO Auto-generated method stub
//			int currentprogress = arg0.getProgress();
//			// String str_current =
//			// (currentprogress/60<10?"0"+(currentprogress/60):""+(currentprogress/60))+":"+(currentprogress%60<10?"0"+(currentprogress%60):(currentprogress%60));
//
//			currentPos = currentprogress ;
//			dlna_cur_total_time.setText(formatTime(currentprogress)+"/"+formatTime(totalLength));
//			
//		}
//
//		@Override
//		public void onStartTrackingTouch(SeekBar seekBar) {
//			// TODO Auto-generated method stub
//			isSeeking = true;
//
//		}
//
//		@Override
//		public void onStopTrackingTouch(SeekBar seekBar) {
//			// TODO Auto-generated method stub
//
//			mMyUpnpService.getMyUpnpControl().seek(formatTime(currentPos));
//			isSeeking = false;
//		}
//
//	};
//
//	private String formatTime(int secondCount) {
//		int hh = secondCount / 3600;
//		int mm = (secondCount - hh * 3600) / 60;
//		int ss = secondCount - hh * 3600 - mm * 60;
//		String tmp = (hh < 10 ? ("0" + hh) : ("" + hh) )+ ":" + ((mm < 10 ? ("0" + mm) : ("" + mm))) + ":" + ((ss < 10 ? ("0" + ss) : ("" + ss)));
//	    return tmp;
//	}
//
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		// TODO Auto-generated method stub
//		super.onConfigurationChanged(newConfig);
//	}
//	
//	
//}
