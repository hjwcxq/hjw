package com.orientmedia.app.cfddj.ui;

import com.actionbarsherlock.app.ActionBar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.orientmedia.app.cfddj.R;
import com.orientmedia.base.BaseActivity;

/**
 * 内容详情页
 * 
 * @author chuanglong
 * 
 */
public class ContentActivity extends BaseActivity {
	private View mainActionBarView;

	private ActionBar mActionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_detail);

		mActionBar = getSupportActionBar();
		mActionBar.hide();
	}
}
