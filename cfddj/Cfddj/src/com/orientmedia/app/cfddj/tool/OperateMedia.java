package com.orientmedia.app.cfddj.tool;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class OperateMedia implements OnBufferingUpdateListener,
		OnCompletionListener, OnErrorListener {
	private int position = 0;// 保存播放的的位置
	private SurfaceView surfaceView;// surfaceVie对象
	private Context context;// 上下文对象
	private MediaPlayer mediaPlayer;// mediaplayer对象
	private boolean justBack = false;// 是否刚才另外一个界面跳回，fasle 表示不是
	
	private String currentUrl = "";

	public OperateMedia(Context context, MediaPlayer mediaPlayer,
			SurfaceView surfaceView) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mediaPlayer = mediaPlayer;
		this.surfaceView = surfaceView;
		// mediaPlayer的设置
		this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		this.mediaPlayer.setOnBufferingUpdateListener(this);
		this.mediaPlayer.setOnCompletionListener(this);
		// surfaceView的设置
		this.surfaceView.getHolder().setKeepScreenOn(true);
		this.surfaceView.getHolder().setType(
				SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this.surfaceView.getHolder().addCallback(new SurfaceCallback()); // surfaceView的回调

	}

	
	
	// 设置播放不同的视频
	public void loadSrc(String videoUrl) {
		// 如果在次播放的是不同的视频，那么就将position初始化,并且reset,重新设置视频源
		if (videoUrl != currentUrl) {
			position = 0;
			try {
				currentUrl = videoUrl;
				mediaPlayer.reset();
				
				mediaPlayer.setDataSource(videoUrl);
				play();
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
			return;// 中断当前程序
		}
		// 如果点击的是同一个视频。那么就不用reset了
		if (videoUrl == currentUrl) {
			if (justBack) {
				play();
				return;
			} else {// 如果不是从另一个activity切换回来，那么，就直接设置到0，开始播放
				mediaPlayer.seekTo(0);
				mediaPlayer.start();
				mediaPlayer.setDisplay(surfaceView.getHolder());// 设置屏幕
			}
		}

	}

	// 播放视频
	public void play() {
		mediaPlayer.prepareAsync();
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				if (position > 0) {
					mediaPlayer.seekTo(position);
					if (justBack) {
						justBack = false;
						position = 0;
					}
				}
				mediaPlayer.start();
			}
		});
		mediaPlayer.setDisplay(surfaceView.getHolder());// 设置屏幕
	}

	// 视频播放完成的回调方法
	public void onCompletion(MediaPlayer mp) throws IllegalStateException {
		if (currentUrl != null || !"".equals(currentUrl)) {
//			MediaVideo.justPlay = false;
		}
	}

	//
	public void onBufferingUpdate(MediaPlayer mp, int percent)
			throws IllegalStateException {
	}

	// SurfaceView的callBack
	private class SurfaceCallback implements SurfaceHolder.Callback {
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		public void surfaceCreated(SurfaceHolder holder) {
			if (position > 0) {
				loadSrc(currentUrl);
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			// 界面销毁，即将跳转到另外一个界面
			if (mediaPlayer.isPlaying()) {
				justBack = true;
				position = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
			}
		}

	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		return false;
	}

}