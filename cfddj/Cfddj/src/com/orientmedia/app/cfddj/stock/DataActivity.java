package com.orientmedia.app.cfddj.stock;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.base.BaseActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DataActivity extends BaseActivity {

	public final static String TAG = "DataActivity";
	private InputMethodManager imm;
	private TextView titlebartitle4DataActivity;
	private WebView webView4DataActivity;
	private String dataUrl = "http://www.51value.com/public/product_list_yc.aspx?gpdm=000002&key=tysx";

	public void backClick(View v) {
		finish();
	}


	@Override
	public void finish() {
		super.finish();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// SystemContext.getInstance().restore(getApplicationContext(),this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data);
		dataUrl = getIntent().getStringExtra("url");
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		setView();
		setEvent();
	}

	@Override
	public void onStop() {
		if (getParent() != null) {
			getParent().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		try {
			webView4DataActivity.stopLoading();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onStop();
	}

	@Override
	public void onPause() {
		super.onPause();
	}


	@Override
	public void onResume() {
		super.onResume();
		// StatService.onResume(this);
		if (getParent() != null) {
			getParent().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		try {
			if (dataUrl != null) {
				if (webView4DataActivity.getUrl() == null
						|| !webView4DataActivity.getUrl().equalsIgnoreCase(
								dataUrl)) {
					webView4DataActivity.loadUrl(dataUrl);
				} else {
					webView4DataActivity.reload();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.e(TAG, "onResume");
	}

	/**
	 * @method setView()
	 * @brief 设置视图，初始化组件
	 * 
	 * @retval void
	 * @param
	 * @return 无返回值
	 */
	@SuppressLint("ResourceAsColor")
	private void setView() {
		Intent intent = this.getIntent();
		String name = intent.getStringExtra("name");
		String code = intent.getStringExtra("code");
		if (name != null && code != null) {
			getSupportActionBar().setTitle(name + "(" + code + ")");
		}
		webView4DataActivity = (WebView) findViewById(R.id.webView4DataActivity);
	}

	/**
	 * @method setEvent()
	 * @brief 设置事件监听
	 * 
	 * @retval void
	 * @param
	 * @return 无返回值
	 */
	@SuppressLint("ResourceAsColor")
	private void setEvent() {
		webView4DataActivity.getSettings().setJavaScriptEnabled(true);
		webView4DataActivity.getSettings().setUseWideViewPort(true);
		webView4DataActivity.getSettings().setLoadWithOverviewMode(true);
		webView4DataActivity.getSettings().setBuiltInZoomControls(true);
		webView4DataActivity.getSettings().setLayoutAlgorithm(
				LayoutAlgorithm.NARROW_COLUMNS);
		webView4DataActivity.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				showLoadingIcon();
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				hideLoadingIcon();
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				hideLoadingIcon();
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

		});

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int mDensity = metrics.densityDpi;
		if (mDensity == 240) {
			webView4DataActivity.getSettings().setDefaultZoom(
					ZoomDensity.MEDIUM);
		} else if (mDensity == 160) {
			webView4DataActivity.getSettings().setDefaultZoom(
					ZoomDensity.MEDIUM);
		} else if (mDensity == 120) {
			webView4DataActivity.getSettings().setDefaultZoom(
					ZoomDensity.MEDIUM);
		} else {
			webView4DataActivity.getSettings().setDefaultZoom(
					ZoomDensity.MEDIUM);
		}

	}

	// 重写返回键，让网页页面可以“后退”
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (webView4DataActivity.canGoBack()) {
				webView4DataActivity.goBack();
			} else {
				finish();
			}
		}
		return true;

	}

}
