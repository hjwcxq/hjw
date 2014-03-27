package com.orientmedia.app.cfddj.ui;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.MyFragmentPagerAdapter;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.ui.find.video.VideoFragment;
import com.orientmedia.app.cfddj.widget.LazyViewPager;
import com.orientmedia.base.BaseActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.tv189.dzlc.adapter.config.MainConst;


public class BaodianActivity extends BaseActivity implements ShowLoadingTipListener,ActionBar.TabListener{
	
	public static final String DAPAN_JIEDU = "dapx" ;
	
	public static final String LAO_ZUO = "lzqaa" ;
	
	public static final String BAIKE = "baike" ;
	
	public static final String GEGU = "gufx" ;
	
	String catalogid = null ;
	
	private String mLastTabTag = null;
	
	private LazyViewPager viewPager ;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baodian);
		BaiduStatistics.onMyEvent(this, "1020", this.getClass().getName()+"进入赚钱宝典");
		catalogid = getIntent().getStringExtra("catalogId") ;
		initFragments();
		viewPager = (LazyViewPager) findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(mPageChangeListener);
		viewPager.setCurrentItem(0);
		initActionTab();
	}
	
	public void initFragments(){
		fragments.add(VideoFragment.newInstance(MainConst.StockConst.STOCK_DAPAN_TYPE));
		fragments.add(VideoFragment.newInstance(MainConst.StockConst.STOCK_GEGU_TYPE));
		fragments.add(VideoFragment.newInstance(MainConst.StockConst.STOCK_QLHM_TYPE));
		fragments.add(VideoFragment.newInstance(MainConst.StockConst.STOCK_EXCEPT_TYPE));
		fragments.add(VideoFragment.newInstance(MainConst.StockConst.STOCK_GSZL_TYPE));
		fragments.add(VideoFragment.newInstance(MainConst.StockConst.STOCK_LAOZUO_TYPE));
	}
	
	
	private ActionBar mActionBar ;
	
	public void initActionTab(){
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.addTab(mActionBar.newTab().setCustomView(getTabTile("大盘解读")).setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setCustomView(getTabTile("个股点评")).setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setCustomView(getTabTile("潜力黑马")).setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setCustomView(getTabTile("热点模块")).setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setCustomView(getTabTile("股市诊疗")).setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setCustomView(getTabTile("老左Q&A")).setTabListener(this));
		
	}
	
	public TextView getTabTile(String title){
		LayoutInflater inflater = LayoutInflater.from(this);
		TextView view = (TextView) inflater.inflate(R.layout.tab_only_text_indicator, null);
		view.setText(title);
		return view ;
	}
	
	
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	

	@Override
	public void onShowLoadingTip(String msg) {
		// TODO Auto-generated method stub
		showLoadingIcon() ;
	}



	@Override
	public void onHideLoadingTip() {
		// TODO Auto-generated method stub
		hideLoadingIcon() ;
	}



	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (viewPager.getCurrentItem() != tab.getPosition()){
			viewPager.setCurrentItem(tab.getPosition());
		}
	}



	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	
	private LazyViewPager.OnPageChangeListener mPageChangeListener = new LazyViewPager.SimpleOnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			getSupportActionBar().setSelectedNavigationItem(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			viewPager.requestDisallowInterceptTouchEvent(state != ViewPager.SCROLL_STATE_IDLE);
		}
	};

	
	private List<Fragment> fragments = new ArrayList<Fragment>();
	

	class MyViewPagerAdapter extends MyFragmentPagerAdapter {

		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm,1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			return super.instantiateItem(container, position);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			super.destroyItem(container, position, object);
		}
		
	}



}
