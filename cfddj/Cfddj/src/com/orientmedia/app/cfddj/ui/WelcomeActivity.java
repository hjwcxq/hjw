package com.orientmedia.app.cfddj.ui;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.CallBackListener;
import com.orientmedia.app.cfddj.task.LoginTask;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.tool.imageutils.ImageFetcher;
import com.orientmedia.app.cfddj.tool.imageutils.UIUtils;
import com.orientmedia.base.BaseActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.news.WelcomePageInfo;
import com.tv189.dzlc.adapter.service.impl.NewsServiceImpl;
import com.umeng.analytics.MobclickAgent;

public class WelcomeActivity extends BaseActivity{
	
	private boolean isFirstIn = true;

	private ImageView welc_image;
	
	private ImageFetcher mImageFetcher;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MobclickAgent.updateOnlineConfig(this);  //hjw add for umeng
		getSupportActionBar().hide();
		setContentView(R.layout.activity_welcome);
		mImageFetcher = UIUtils.getImageFetcher(this);
		initView();
		checkUserStatus();
		
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mImageFetcher.closeCache();
	}
	
	public void initView(){
		welc_image = (ImageView) findViewById(R.id.wel_image);
//		new GetWelcomePageInfoTask().execute("");
	}
	
	
	
	private void getoMainActivity() {		
		new Handler(getMainLooper()).postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,
						MainActivity.class);
				startActivity(intent);

				finish();
			}
		}, 1500);
	}

	/****
	 * imsi码登录
	 */
	private AppSetting setting = null;
	
	private LoginTask loginTask = null ;
	
	private void checkUserStatus() {
		if(setting == null)
			setting = AppSetting.getInstance(this);
		if(loginTask == null)
			loginTask = new LoginTask(this);
		
		if (setting.isNeedGuide()) {//第一次登陆
			setting.setNeedGuide(false);
			loginTask.loginByImsi(new CallBackListener() {
				
				@Override
				public void executeSucc() {
					// TODO Auto-generated method stub
					
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
		} else {
			if(!setting.isLoginOn()){
				loginTask.loginByImsi(new CallBackListener() {
					
					@Override
					public void executeSucc() {
						// TODO Auto-generated method stub
						
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
		}
		getoMainActivity();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		 if (KeyEvent.KEYCODE_BACK == keyCode) {
			finish();
			new Thread(new Runnable() {
				public void run() {
					System.exit(0);
				}
			}).start();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private WelcomePageInfo info = null ;
	
	class GetWelcomePageInfoTask extends AsyncTask<String, Void, String>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			NewsServiceImpl newsService = new NewsServiceImpl(WelcomeActivity.this);
			try {
				info = newsService.getWelcomePageInfo() ;
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(WelcomeActivity.this, null, e);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(info != null){
				mImageFetcher.loadImage(info.getAndroidd(), welc_image);
			}
			getoMainActivity();
		}
		
	}
	
}
