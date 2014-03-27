package com.orientmedia.app.cfddj.ui;

import com.igexin.slavesdk.MessageManager;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.context.SystemContext;

import android.content.Intent;
import android.os.Bundle;
import com.orientmedia.base.BaseActivity;

public class FirstActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		MessageManager.getInstance().initialize(this.getApplicationContext());
		SystemContext.getInstance(FirstActivity.this);
		if (AppSetting.getInstance(this).isNeedGuide()) {
			Intent intent = new Intent(this, GuideActivity.class);
			startActivity(intent);
			this.finish();
		} else {
			Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);
			this.finish();
		}
	}

}
