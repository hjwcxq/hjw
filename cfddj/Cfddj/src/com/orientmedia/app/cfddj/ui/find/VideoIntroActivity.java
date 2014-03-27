//package tv.aniu.app.dzlc.ui.find;
//
//import tv.aniu.app.dzlc.R;
//import tv.aniu.app.dzlc.tool.BaiduStatistics;
//import tv.base.BaseActivity;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.SeekBar;
//import android.widget.SeekBar.OnSeekBarChangeListener;
//import android.widget.TextView;
//import android.widget.VideoView;
//
//public class VideoIntroActivity extends BaseActivity implements OnClickListener{
//	
//	public VideoView videoView;
//	
//	private Uri mUri;
//	
//	private TextView tital, time, playNum, intro;
//	
//	private ImageView vplay;
//	
//	private ScrollView sv_video;
//	
//	private SeekBar seek;
//	
//	private boolean isPlaying;
//	
//	private int playVideoPosition = 0 ;
//	
//	private RelativeLayout videoBorder ;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_video_detial);
//		BaiduStatistics.onMyEvent(this, "1300", "进入视频详细页");
//		initView();
//		initListener();
//	}
//	
//	
//	public void initView(){
//		videoView = (VideoView) findViewById(R.id.video_stock);
//		tital = (TextView) findViewById(R.id.tv_vtital);
//		time = (TextView) findViewById(R.id.vtime);
//		playNum = (TextView) findViewById(R.id.vnum);
//		intro = (TextView) findViewById(R.id.tv_vintro);
//		vplay = (ImageView) findViewById(R.id.playControlBtn);
//		sv_video = (ScrollView) findViewById(R.id.sv_video);
//		seek = (SeekBar) findViewById(R.id.seekBar);
//		videoBorder = (RelativeLayout)findViewById(R.id.videoBorder);
//		
//		Bundle bundle = getIntent().getExtras();
//		String video_uri = bundle.getString("vuri");// 读出数据
//		String video_tital = bundle.getString("vtital");// 读出数据
//		String video_length = bundle.getString("vlength");// 读出数据
//		String video_intro = bundle.getString("vintro");// 读出数据
//		Uri real_video_uri = Uri.parse(video_uri);
//		videoView.setVideoURI(real_video_uri);
//		tital.setText(video_tital);
//		int timer_m = Integer.parseInt(video_length) / 60;
//		int timer_s = Integer.parseInt(video_length) % 60;
//		time.setText(String.valueOf(timer_m) + ":" + String.valueOf(timer_s));
//		playNum.setText("");
//		intro.setText(video_intro);
//	}
//	
//	public void initListener(){
//		vplay.setOnClickListener(this);
//		videoBorder.setOnClickListener(this);
//		seek.setOnSeekBarChangeListener(change);
//	}
//
//	
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.playControlBtn:
//			isPlaying  = true ;
//			play(playVideoPosition);
//			vplay.setVisibility(View.GONE);
//			break;
//		case R.id.videoBorder:
//			if(isPlaying){
//				isPlaying = false ;
//				videoView.pause();
//				vplay.setBackgroundResource(R.drawable.backgroup_pause_btn);
//				vplay.setVisibility(View.VISIBLE);
//			}
//			break;
//		default:
//			break;
//		}
//	}
//	
//	
//	
//
//	private void play(int msec) {
//		videoView.start();
//		// 按照初始位置播放
//		videoView.seekTo(msec);
//		// 设置进度条的最大进度为视频流的最大播放时长
//		seek.setMax(videoView.getDuration());
//
//		// 开始线程，更新进度条的刻度
//		new Thread() {
//
//			@Override
//			public void run() {
//				try {
//					isPlaying = true;
//					while (isPlaying) {
//						// 如果正在播放，没0.5.毫秒更新一次进度条
//						playVideoPosition = videoView.getCurrentPosition();
//						seek.setProgress(playVideoPosition);
//						sleep(500);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();
//
//	}
//
//	private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {
//
//		@Override
//		public void onStopTrackingTouch(SeekBar seekBar) {
//			// 当进度条停止修改的时候触发
//			// 取得当前进度条的刻度
//			playVideoPosition = seekBar.getProgress();
//			if (videoView != null && videoView.isPlaying()) {
//				// 设置当前播放的位置
//				videoView.seekTo(playVideoPosition);
//			}
//		}
//		@Override
//		public void onStartTrackingTouch(SeekBar seekBar) {
//
//		}
//		@Override
//		public void onProgressChanged(SeekBar seekBar, int progress,
//				boolean fromUser) {
//
//		}
//	};
//
//	
//}
