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

public class AboutActivity extends BaseActivity{
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
			
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//MobclickAgent.onResume(this);   //hjw add umeng
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//MobclickAgent.onPause(this);    //hjw add umeng
	}
	
}
