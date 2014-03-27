package com.orientmedia.app.cfddj.module;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.adapter.MyViewPagerFragmentAdapter;
import com.orientmedia.app.cfddj.ui.fragment.IndexFragment;
import com.orientmedia.app.cfddj.widget.CircleFlowIndicator;
import com.orientmedia.app.cfddj.widget.IndicatorViewPager;
import com.orientmedia.base.BaseActivity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.tv189.dzlc.adapter.po.base.AbstractModule;

public class FocusModule extends AbstractModule {

	private MyViewPagerFragmentAdapter adapter = null;

	@Override
	public View getView(Context cont) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(cont).inflate(R.layout.module_focus,
				null);
		initView(cont, view);
		return view;
	}

	private CircleFlowIndicator mImagePointsGroup;

	private  ViewPager viewPager;

	public ViewPager getViewPager() {
		return viewPager;
	}

	public void setViewPager(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	public void initView(Context cont, View view) {
		mImagePointsGroup = (CircleFlowIndicator) view
				.findViewById(R.id.imagePointsGroup);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		/*viewPager.setFocusable(true);
		viewPager.setFocusableInTouchMode(true);
		viewPager.requestFocus();*/
		adapter = new MyViewPagerFragmentAdapter(
				((BaseActivity) cont).getSupportFragmentManager(),getItems(),IndexFragment.refrushCount);
		viewPager.setAdapter(adapter);
		viewPager.setId(viewPager.getId());
		Log.i("viewPager Id ===", ""+viewPager.getId());
		viewPager.setOnPageChangeListener(mPageChangeListener);
		viewPager.setCurrentItem(0);
		if (this.getItems() != null) {
			mImagePointsGroup.setCount(this.getItems().size());
		} else {
			mImagePointsGroup.setCount(0);
		}
		((IndicatorViewPager) viewPager).setFlowIndicator(mImagePointsGroup);
		
	}

	private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {
			viewPager
					.requestDisallowInterceptTouchEvent(state != ViewPager.SCROLL_STATE_IDLE);
		}
	};

	public MyViewPagerFragmentAdapter getAdapter() {
		return adapter;
	}


}
