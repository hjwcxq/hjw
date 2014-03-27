package com.orientmedia.app.cfddj.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.MyFragmentPagerAdapter;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.ui.fragment.program.ProgramFragment;
import com.orientmedia.app.cfddj.widget.LazyViewPager;
import com.orientmedia.base.BaseActivity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;

public class ProgramActivity extends BaseActivity implements OnClickListener,ShowLoadingTipListener{

	private LazyViewPager viewPager;

	private int currentIndex = 0;

	private ActionBar mActionBar = null;

	private ImageView cursor;// 动画图片

	private int offset = 0;// 动画图片偏移量

	private int bmpW;// 动画图片宽度

	private RelativeLayout today, yesToday, beforeYesToday;
	
	private List<Fragment> fragments = new ArrayList<Fragment>();
	
	private MyFragmentAdapter myAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program_item);
		BaiduStatistics.onMyEvent(this, "1400", "进入节目单列表");
		loadFragment();
		initView();
	}
	
	public void loadFragment(){
		Date now = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(now);
		String yestoday = sdf.format(new Date(now.getTime()
				- ((long) 1 * 24 * 60 * 60 * 1000)));
		String beforeDay = sdf.format(new Date(now.getTime()
				- ((long) 2 * 24 * 60 * 60 * 1000)));
		
		fragments.add(ProgramFragment.newInstance(today));
		fragments.add(ProgramFragment.newInstance(yestoday));
		fragments.add(ProgramFragment.newInstance(beforeDay));
	}

	public void initView() {
		today = (RelativeLayout) findViewById(R.id.today);
		yesToday = (RelativeLayout) findViewById(R.id.yestoday);
		beforeYesToday = (RelativeLayout) findViewById(R.id.today_before_yestoday);
		viewPager = (LazyViewPager) findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(0);
		myAdapter = new MyFragmentAdapter(getSupportFragmentManager(),1);
		viewPager.setAdapter(myAdapter);
		viewPager.setOnPageChangeListener(pageChangerListener);
		initAnimView();
		today.setOnClickListener(this);
		yesToday.setOnClickListener(this);
		beforeYesToday.setOnClickListener(this);
	}

	// 初始化动画
	private void initAnimView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (int) ((screenW / 3 - bmpW) / 2);// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.today:
			viewPager.setCurrentItem(0);
			break;
		case R.id.yestoday:
			viewPager.setCurrentItem(1);
			break;
		case R.id.today_before_yestoday:
			viewPager.setCurrentItem(2);
			break;

		default:
			break;
		}
	}
	
	class MyFragmentAdapter extends MyFragmentPagerAdapter{

		public MyFragmentAdapter(FragmentManager fm, int count) {
			super(fm, count);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
	}

	

	private LazyViewPager.OnPageChangeListener pageChangerListener = new LazyViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			
			int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量  
	        int two = one * 2;// 页卡1 -> 页卡3 偏移量  
	        Animation animation = new TranslateAnimation(one*currentIndex, one*position, 0, 0);//显然这个比较简洁，只有一行代码。  
            animation.setFillAfter(true);// True:图片停在动画结束位置  
            animation.setDuration(300);  
            cursor.startAnimation(animation);  
			currentIndex = position;
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

}
