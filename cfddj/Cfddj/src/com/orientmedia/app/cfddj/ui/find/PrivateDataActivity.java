package com.orientmedia.app.cfddj.ui.find;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.FragmentDataNotifyListener;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.MyFragmentPagerAdapter;
import com.orientmedia.app.cfddj.stock.DataActivity;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.ui.fragment.HotFragment;
import com.orientmedia.app.cfddj.ui.fragment.MyStockFragment;
import com.orientmedia.app.cfddj.widget.LazyViewPager;
import com.orientmedia.base.BaseActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;
import com.tv189.dzlc.adapter.service.impl.StockServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;

public class PrivateDataActivity extends BaseActivity implements
		OnClickListener, ShowLoadingTipListener ,FragmentDataNotifyListener{

	private LazyViewPager viewPager;

	private int currentIndex = 0;

	private ImageView cursor;// 动画图片

	private int offset = 0;// 动画图片偏移量

	private int bmpW;// 动画图片宽度

	private RelativeLayout myStock;

	private RelativeLayout hotStock;
	
	private Animation anim ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_private_data);
		BaiduStatistics.onMyEvent(this, "1030", this.getClass().getName()+"进入私募数据页面");
		anim = AnimationUtils.loadAnimation(this, R.anim.rotate_loading);  
		initSearchView();
		initView();
		initAnimView();
	}

	public void initView() {
		myStock = (RelativeLayout) findViewById(R.id.my_stock);
		hotStock = (RelativeLayout) findViewById(R.id.hot_stock);

		cursor = (ImageView) findViewById(R.id.cursor);
		viewPager = (LazyViewPager) findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(mViewPagerAdapter);
		viewPager.setOnPageChangeListener(pageChangerListener);

		myStock.setOnClickListener(this);
		hotStock.setOnClickListener(this);
	}

	private ActionBar mAcitonBar;

	private EditText mEditText;

	private ImageView loadingView;

	private View searchView;

	public void initSearchView() {
		mAcitonBar = getSupportActionBar();
		mAcitonBar.setDisplayHomeAsUpEnabled(true);
		mAcitonBar.setDisplayShowTitleEnabled(false);
		mAcitonBar.setDisplayShowCustomEnabled(true);
		searchView = (View) LayoutInflater.from(this).inflate(
				R.layout.search_view, null);
		mEditText = (EditText) searchView.findViewById(R.id.search_view);
		loadingView = (ImageView) searchView.findViewById(R.id.loading_image);
		mAcitonBar.setCustomView(searchView);
		Utils.hideSoftInput(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_stock:
			viewPager.setCurrentItem(1);
			break;
		case R.id.hot_stock:
			viewPager.setCurrentItem(0);
			break;

		default:
			break;
		}
	}

	@Override
	public void onShowLoadingTip(String msg) {
		// TODO Auto-generated method stub
		loadingView.setVisibility(View.VISIBLE);
		loadingView.startAnimation(anim);
	}

	@Override
	public void onHideLoadingTip() {
		// TODO Auto-generated method stub
		loadingView.clearAnimation();
		loadingView.setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.private_stock, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.search_stock:
			String searchMsg = mEditText.getText().toString();
			new searchStockTask().execute(searchMsg);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	

	// 初始化动画
	private void initAnimView() {
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置

	}

	public void translateCursor(boolean is1To2) {
		if (is1To2) {
			TranslateAnimation animation = new TranslateAnimation(offset,
					offset * 2 + bmpW, 0, 0);
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		} else {
			TranslateAnimation animation = new TranslateAnimation(offset * 2
					+ bmpW, 0, 0, 0);
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}
	}

	private MyFragmentPagerAdapter mViewPagerAdapter = new MyFragmentPagerAdapter(
			getSupportFragmentManager(), 1) {

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:// 自选股
				return new HotFragment();
			case 1:// 热门股
				return new MyStockFragment();
			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	private LazyViewPager.OnPageChangeListener pageChangerListener = new LazyViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			if (position == 1) {
				if (currentIndex == 0) {
					translateCursor(true);
				}
			} else if (position == 0) {
				if (currentIndex == 1) {
					translateCursor(false);
				}
			}
			currentIndex = position;
			supportInvalidateOptionsMenu();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};
	

	/**
	 * 加载web
	 * 
	 * @author EsaFans
	 * 
	 */

	private StockInfo searchStockDetail = null;

	public class searchStockTask extends AsyncTask<String, Void, String> {

		String SUCCESS = "successpre";
		String ERROR = "errorpre";

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			loadingView.setVisibility(View.VISIBLE);
			loadingView.startAnimation(anim);
		}

		@Override
		protected String doInBackground(String... params) {

			String keyword = params[0];
			StockServiceImpl requestService = new StockServiceImpl(
					PrivateDataActivity.this);
			try {
				searchStockDetail = requestService.searchStock(keyword);
				if (searchStockDetail != null) {
					return SUCCESS;
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(PrivateDataActivity.this,
						null, e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			loadingView.clearAnimation();
			loadingView.setVisibility(View.GONE);
			if (SUCCESS.equalsIgnoreCase(result)) {
				Intent intent = new Intent(PrivateDataActivity.this,
						DataActivity.class);
				intent.putExtra("url", searchStockDetail.getUrl());
				intent.putExtra("code", searchStockDetail.getKeyword());
				intent.putExtra("name", searchStockDetail.getStockName());
				startActivity(intent);
				mEditText.setText("");

			} else {
				showCusToast("查询失败");
			}
		}

	}

	@Override
	public void notifyFragment() {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(1);
		sendBroadcast(new Intent("ADD_MY_STOCK_SUCCESS"));
	}

}
