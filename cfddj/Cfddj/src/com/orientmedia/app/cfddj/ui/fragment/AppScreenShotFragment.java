package com.orientmedia.app.cfddj.ui.fragment;

import java.util.ArrayList;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.MyFragmentPagerAdapter;
import com.orientmedia.app.cfddj.widget.CircleFlowIndicator;
import com.orientmedia.app.cfddj.widget.IndicatorViewPager;
import com.orientmedia.base.BaseFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

@SuppressLint("ValidFragment")
public class AppScreenShotFragment extends BaseFragment {

	public static final String TAG = "AppScreenShotFragment";

	private IndicatorViewPager mViewPager;

	private CircleFlowIndicator mFlowIndicator;


	// int[] ids = {R.drawable.g1,R.drawable.g2,R.drawable.g3};

	private ArrayList<String> images = new ArrayList<String>();
	
	private ImageLoader mImageLoader = null ;
	
	private DisplayImageOptions options ;
	
	private ShowLoadingTipListener showListener ;

	public static AppScreenShotFragment newInstance(ArrayList<String> images) {
		AppScreenShotFragment fragment = new AppScreenShotFragment();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("images", images);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		showListener = (ShowLoadingTipListener)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_guide, null);
		mImageLoader = ImageLoader.getInstance();
		
		options = new DisplayImageOptions.Builder()
	    .cacheInMemory()
	    .cacheOnDisc()
	    .build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();
		
		mImageLoader.init(config);
		initView(view);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		return view;
	}

	public void initView(View view) {
		images = getArguments().getStringArrayList("images");
		mViewPager = (IndicatorViewPager) view.findViewById(R.id.viewPager);
		mViewPager.setOffscreenPageLimit(images.size());
		mFlowIndicator = (CircleFlowIndicator) view
				.findViewById(R.id.imagePointsGroup);
		mViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
		mFlowIndicator.setCount(images.size());
		mViewPager.setFlowIndicator(mFlowIndicator);
		mViewPager.setCurrentItem(0);
	}

	private class MyAdapter extends MyFragmentPagerAdapter {

		public MyAdapter(FragmentManager fragmentManager) {
			super(fragmentManager,1);
		}

		@Override
		public int getCount() {
			return images.size();
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
			showListener.onShowLoadingTip("");
			mImageLoader.displayImage(images.get(mIndex), image,options ,new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
					// TODO Auto-generated method stub
					showListener.onHideLoadingTip();
				}
				
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					// TODO Auto-generated method stub
					showListener.onHideLoadingTip();
				}
				
				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub
					showListener.onHideLoadingTip();
				}
			});
			return view;
		}
		
		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			mImageLoader.clearMemoryCache();
		}
	}
	
	
	
}
