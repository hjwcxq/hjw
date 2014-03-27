package com.orientmedia.app.cfddj.ui;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.base.BaseActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tv189.dzlc.adapter.util.Utils;

public class SearchActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initActionBar();
		
	}
	
	private ActionBar mAcitonBar ;
	
	private EditText mEditText;
	
	public void initActionBar(){
		mAcitonBar = getSupportActionBar();
		mAcitonBar.setDisplayHomeAsUpEnabled(true);
		mAcitonBar.setDisplayShowTitleEnabled(false);
		mAcitonBar.setDisplayShowCustomEnabled(true);
		mEditText = new EditText(this);
		mEditText.setWidth(Utils.getWindowWidth(this) - Utils.dip2px(this, 96));
		mEditText.setHint("搜索");
		mAcitonBar.setCustomView(mEditText);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.search, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.search:
			Toast.makeText(this, "你搜索的关键字"+mEditText.getText().toString(), 1000).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
