package com.orientmedia.app.cfddj.ui;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.widget.CircleFlowIndicator;
import com.orientmedia.app.cfddj.widget.IndicatorViewPager;
import com.orientmedia.base.BaseActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 索引页面
 * 
 * @author chuanglong
 * 
 */
@SuppressLint("ValidFragment")
public class GuideActivity extends BaseActivity implements OnClickListener {

	private IndicatorViewPager mViewPager;

	private CircleFlowIndicator mFlowIndicator;

	private Button startManagerMoney;

	int[] ids = { R.drawable.welcome1, R.drawable.welcome2,
			R.drawable.welcome3, R.drawable.welcome4 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_guide);
		initView();
		initListener();

	}

	public void initView() {
		mViewPager = (IndicatorViewPager) findViewById(R.id.viewPager);
		mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.imagePointsGroup);
		startManagerMoney = (Button) findViewById(R.id.startManagerMoney);
		mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		mFlowIndicator.setCount(ids.length);
		mViewPager.setFlowIndicator(mFlowIndicator);
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(new PageChangeListener());
	}

	public void initListener() {
		startManagerMoney.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.startManagerMoney:
			Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	private class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public int getCount() {
			return ids.length;
		}

		@Override
		public Fragment getItem(int position) {
			PagerFragment f = new PagerFragment();
			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", position);
			f.setArguments(args);
			return f;
		}
	}

	private class PagerFragment extends Fragment {
		int mIndex;

		public PagerFragment() {

		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mIndex = getArguments() != null ? getArguments().getInt("num") : 1;
			if (mIndex == ids.length)
				startManagerMoney.setVisibility(View.VISIBLE);
		}

		/**
		 * The Fragment's UI is just a simple text view showing its instance
		 * number.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.item_guide, null);
			ImageView image = (ImageView) view.findViewById(R.id.guide_image);
			image.setBackgroundResource(ids[mIndex]);
			return view;
		}
	}

	class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			if (arg0 == ids.length - 1) {
				startManagerMoney.setVisibility(View.VISIBLE);
			}
		}

	}

}
