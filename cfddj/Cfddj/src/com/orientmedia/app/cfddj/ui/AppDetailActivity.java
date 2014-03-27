package com.orientmedia.app.cfddj.ui;

import java.util.ArrayList;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.version.ApplicationInfo;
import com.tv189.dzlc.adapter.service.impl.VersionServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.service.UpdateService;
import com.orientmedia.app.cfddj.tool.imageutils.ImageFetcher;
import com.orientmedia.app.cfddj.tool.imageutils.UIUtils;
import com.orientmedia.app.cfddj.ui.fragment.AppDescFragment;
import com.orientmedia.app.cfddj.ui.fragment.AppScreenShotFragment;
import com.orientmedia.base.BaseActivity;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppDetailActivity extends BaseActivity implements OnClickListener,ShowLoadingTipListener {

	private Button btnScreenShot;

	private Button btnAppDesc;

	private RelativeLayout share ;
	
	private String mLastTabTag = null;
	
	private ArrayList<String> images = new ArrayList<String>() ;
	
	private Intent intent = null;
	
	private String appDesc = "";
	
	private String appName = "";
	
	private String appSize = "";
	
	private String appDownLoadUrl = "";
	
//	private String appVersion = "" ;
	
	private String packageName = "";
	
	private String appIconUrl = "" ;
	
	private String appId = "";
	
	
	private ImageView app_icon, anzhunag_icon;
	
	private TextView app_name,app_version_and_size,anzhuang_text;
	
	private LinearLayout llAnzhunag ;
	
	private ImageFetcher mImageFetcher ;
	
//	private static ApplicationInfo applicationInfo ;
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_detail);
		mImageFetcher= new ImageFetcher(this);
		initData();
		initView();
		initListener();
		initGroupBtnBg(true);
		showScreenShotFrag();
	}
	
	public void initData(){
		intent = getIntent();
		if(intent.getStringExtra("pic1")!= null && !"".equals(intent.getStringExtra("pic1")))
			images.add(intent.getStringExtra("pic1"));
		if(intent.getStringExtra("pic2")!= null && !"".equals(intent.getStringExtra("pic2")))
			images.add(intent.getStringExtra("pic2"));
		if(intent.getStringExtra("pic3")!= null && !"".equals(intent.getStringExtra("pic3")))
			images.add(intent.getStringExtra("pic3"));
		if(intent.getStringExtra("pic4")!= null && !"".equals(intent.getStringExtra("pic4")))
			images.add(intent.getStringExtra("pic4"));
		if(intent.getStringExtra("pic5")!= null && !"".equals(intent.getStringExtra("pic5")))
			images.add(intent.getStringExtra("pic5"));
		
		appDesc = intent.getStringExtra("appDesc") ;
		appName = intent.getStringExtra("appName");
		appSize = intent.getStringExtra("appSize");
		appDownLoadUrl = intent.getStringExtra("appDownloadUrl");
//		appVersion = intent.getStringExtra("appVersion");
		packageName = intent.getStringExtra("packageName");
		appIconUrl = intent.getStringExtra("appIconUrl");
		appId = intent.getStringExtra("appId");
		
		getSupportActionBar().setTitle(appName);
	}
	
	public void initView() {
		btnScreenShot = (Button) findViewById(R.id.btnScreenShot);
		btnAppDesc = (Button) findViewById(R.id.btnAppDesc);
		share = (RelativeLayout) findViewById(R.id.rl_share);
		
		
		
		app_icon = (ImageView) findViewById(R.id.app_icon);
		anzhunag_icon = (ImageView) findViewById(R.id.anzhuang_icon);
		app_name = (TextView) findViewById(R.id.app_name);
		app_version_and_size = (TextView) findViewById(R.id.app_size_and_version);
		anzhuang_text = (TextView) findViewById(R.id.anzhuang_text);
		
		llAnzhunag = (LinearLayout) findViewById(R.id.ll_anzhuang);
		
		app_name.setText(appName);
		app_version_and_size.setText("大小："+appSize);
		mImageFetcher.loadThumbnailImage(appIconUrl, app_icon);
		if(Utils.isAppExits(this, packageName)){
			anzhunag_icon.setBackgroundResource(R.drawable.open_app);
			anzhuang_text.setText("打开");
		}else{
			anzhunag_icon.setBackgroundResource(R.drawable.down_app);
			anzhuang_text.setText("下载");
		}
		
	}

	public void initListener() {
		btnScreenShot.setOnClickListener(this);
		btnAppDesc.setOnClickListener(this);
		share.setOnClickListener(this);
		
		llAnzhunag.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnScreenShot:
			initGroupBtnBg(true);
			showScreenShotFrag();
			break;
		case R.id.btnAppDesc:
			initGroupBtnBg(false);
			showAppDescFrag();
			break;
		case R.id.rl_share:
			UIUtils.sendTo(this, "");
			break;
		case R.id.ll_anzhuang:
			if(Utils.isAppExits(this, packageName)){
				Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
				startActivity(intent);
			}else{
				UpdateService.INSTALL_APK_URL = appDownLoadUrl;
				startService(new Intent(this,UpdateService.class));
				showCusToast("下载应用中...");
				
				new StaticsDownCountTask().execute(new String[]{appId});
			}
			break;
		
		default:
			break;
		}
	}
	
	
	public void initGroupBtnBg(boolean isScreenShot){
		if(isScreenShot){
			btnScreenShot.setBackgroundResource(R.drawable.tab_left_hover);
			btnAppDesc.setBackgroundResource(R.drawable.tab_right);
		}else{
			btnScreenShot.setBackgroundResource(R.drawable.tab_left);
			btnAppDesc.setBackgroundResource(R.drawable.tab_right_hover);
		}
	}
	

	public void showScreenShotFrag() {
		if (mLastTabTag != AppScreenShotFragment.TAG) {
			final FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			if (mLastTabTag != null) {
				Fragment fragment = getSupportFragmentManager()
						.findFragmentByTag(mLastTabTag);
				if (fragment != null && !fragment.isDetached()) {
					ft.hide(fragment);
				}
			}
			Fragment newFragment = getSupportFragmentManager()
					.findFragmentByTag(AppScreenShotFragment.TAG);
			if (newFragment == null) {
				newFragment = AppScreenShotFragment.newInstance(images);
				ft.add(R.id.frag_container, newFragment,
						AppScreenShotFragment.TAG);
			} else {
				ft.show(newFragment);
			}
			ft.commit();
			mLastTabTag = AppScreenShotFragment.TAG;
		}
	}

	public void showAppDescFrag() {
		if (mLastTabTag != AppDescFragment.TAG) {
			final FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			if (mLastTabTag != null) {
				Fragment fragment = getSupportFragmentManager()
						.findFragmentByTag(mLastTabTag);
				if (fragment != null && !fragment.isDetached()) {
					ft.hide(fragment);
				}
			}
			Fragment newFragment = getSupportFragmentManager()
					.findFragmentByTag(AppDescFragment.TAG);
			if (newFragment == null) {
				newFragment = AppDescFragment.newInstance(appDesc);
				ft.add(R.id.frag_container, newFragment, AppDescFragment.TAG);
			} else {
				ft.show(newFragment);
			}
			ft.commit();
			mLastTabTag = AppDescFragment.TAG;
		}
	}

	@Override
	public void onShowLoadingTip(String msg) {
		// TODO Auto-generated method stub
		showLoadingIcon();
	}

	@Override
	public void onHideLoadingTip() {
		// TODO Auto-generated method stub
		hideLoadingIcon();
	}
	
	
	class StaticsDownCountTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			boolean flag = false;
			VersionServiceImpl versionService = new VersionServiceImpl(
					AppDetailActivity.this);
			try {
				flag = versionService.statisticsDownloadCount(params[0]) ;
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
}
