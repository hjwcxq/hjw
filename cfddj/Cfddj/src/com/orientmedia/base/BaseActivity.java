package com.orientmedia.base;

import java.util.LinkedList;

import com.orientmedia.app.cfddj.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tv189.dzlc.adapter.util.Utils;

/**
 * @author andy
 * 
 */

@SuppressLint("NewApi")
public abstract class BaseActivity extends SherlockFragmentActivity {

	public static LinkedList<Activity> sAllActivitys = new LinkedList<Activity>();
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	private View mainActionBarView;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		sAllActivitys.add(this);
		super.onCreate(savedInstanceState);
		// actionbar
		/** 隐藏/显示返回箭头 */
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	
	
	
	protected ImageView loadingIcon ;
	
	
	protected Animation anim = null;
	
	private long tempTime = 0 ;
	
	public void showLoadingIcon(){
		loadingIcon = (ImageView) LayoutInflater.from(this).inflate(R.layout.loading_icno, null);
		loadingIcon.setVisibility(View.VISIBLE);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		LayoutParams params = new LayoutParams(Gravity.RIGHT);
		params.setMargins(0, 0, Utils.dip2px(this, 15), 0);
		getSupportActionBar().setCustomView(loadingIcon,params) ;
		hideLoadingIcon();
		anim = AnimationUtils.loadAnimation(this, R.anim.rotate_loading);  
		tempTime = System.currentTimeMillis();
		loadingIcon.startAnimation(anim);
	}
	
	
	public void hideLoadingIcon(){
		loadingIcon.clearAnimation() ;
		loadingIcon.setVisibility(View.GONE);
	}
	
	
	

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}


	public void onStart() {
		super.onStart();
	}

	@Override
	public void onPause() {
		super.onPause();
		//StatService.onPause(this);
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
		//StatService.onResume(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public static void finishAll() {
		for (Activity activity : sAllActivitys) {
			activity.finish();
		}
		sAllActivitys.clear();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private ProgressDialog mProgressDialog = null;

	public void showNetLoadingProgressDialog(String loadingMsg) {
			mProgressDialog = new ProgressDialog(BaseActivity.this);
//			mProgressDialog.setTitle("提示信息");
			mProgressDialog.setMessage(loadingMsg);
			mProgressDialog.setCancelable(true);
			mProgressDialog
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							// TODO Auto-generated method stub
							if (mProgressDialog.isShowing()) {
								mProgressDialog.dismiss();
							}
						}
					});
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	
	public boolean isProgressDialogShowing(){
		return mProgressDialog != null && mProgressDialog.isShowing();
	}

	public void hideNetLoadingProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	

	/****
	 * 公用跳转方法
	 */
	public void intentTo(Context packageContext, Class cls) {
		Intent i = new Intent();
		i.setClass(packageContext, cls);
		startActivity(i);
	}

	public void showCusToast(String msg) {
		Toast.makeText(this, msg, 1000).show();
	}

}
