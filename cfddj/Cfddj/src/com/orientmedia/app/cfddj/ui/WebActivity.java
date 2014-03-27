package com.orientmedia.app.cfddj.ui;

import com.handmark.pulltorefresh.library.PullToRefreshWebView;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

public class WebActivity extends BaseActivity {

	private WebView mWebView;

	private String html_url = "";
	
	PullToRefreshWebView mPullRefreshWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		html_url = getIntent().getStringExtra("url");
		if (html_url != null && !html_url.startsWith("http")) {
			html_url = "http://" + html_url;
		}
		
		mPullRefreshWebView = (PullToRefreshWebView) findViewById(R.id.pull_refresh_webview);
		mWebView = mPullRefreshWebView.getRefreshableView();
		
//		mWebView.getSettings().setJavaScriptEnabled(true);
//		mWebView.getSettings().setUseWideViewPort(true);
//		mWebView.getSettings().setLoadWithOverviewMode(true);
//		mWebView.getSettings().setBuiltInZoomControls(true);
//		mWebView.getSettings().setLayoutAlgorithm(
//				LayoutAlgorithm.NARROW_COLUMNS);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				showLoadingIcon();
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				hideLoadingIcon();
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				hideLoadingIcon();
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);     //hjw add for umeng   
		if (getParent() != null) {
			getParent().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		try {
			if (html_url != null && !"".equals(html_url)) {
				if (mWebView.getUrl() == null
						|| !mWebView.getUrl().equalsIgnoreCase(html_url)) {
					mWebView.loadUrl(html_url);
				} else {
					mWebView.reload();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
	    MobclickAgent.onPause(this);  		 //hjw add for umeng
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			mWebView.stopLoading();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 重写返回键，让网页页面可以“后退”
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWebView.canGoBack()) {
				mWebView.goBack();
			} else {
				finish();
			}
		}
		return true;

	}

}
