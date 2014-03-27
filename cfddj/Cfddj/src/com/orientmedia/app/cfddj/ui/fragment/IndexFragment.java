package com.orientmedia.app.cfddj.ui.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.CallBackListener;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.MyViewPagerFragmentAdapter;
import com.orientmedia.app.cfddj.module.ModuleFactory;
import com.orientmedia.app.cfddj.task.FileDownTask;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.tool.FileOperator;
import com.orientmedia.app.cfddj.ui.MainActivity;
import com.orientmedia.base.BaseActivity;
import com.orientmedia.base.BaseFragment;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.po.base.AbstractModule;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.service.impl.VersionServiceImpl;
import com.umeng.analytics.MobclickAgent;

public class IndexFragment extends BaseFragment {

	public static final String TAG = "IndexFragment";

	private ModuleFactory mFactory;

	public static List<AbstractModule> modules = new ArrayList<AbstractModule>();

	private LinearLayout view_container;

	public static ShowLoadingTipListener showListener;

	PullToRefreshScrollView mPullRefreshScrollView;

	ScrollView mScrollView;

	private Map<Object, View> views = new HashMap<Object, View>();

	public static int refrushCount = 0;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		showListener = (MainActivity) activity;
		BaiduStatistics.onMyEvent(this.getActivity(), "1000", this.getClass()
				.getName() + "进入主页面");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = null;
		if (container == null)
			return null;
		else {
			view = inflater.inflate(R.layout.fragment_index, container, false);
			initView(view);
			loadingView();
			new LoadXmlTask().execute("");
		}
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageStart("indexScreen"); //hjw add for umeng
	    MobclickAgent.onResume(getActivity());
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("indexScreen"); //hjw add for umeng
	    MobclickAgent.onPause(getActivity());
	}

	public InputStream getLocalXmlRes() {
		File index = new File(FileOperator.getCacheIndexXmlFile(), "index.xml");
		try {
			InputStream fis = new FileInputStream(index);
			return fis;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public void initView(View view) {
		view_container = (LinearLayout) view.findViewById(R.id.view_container);

		mPullRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.pull_refresh_scrollview);

		mPullRefreshScrollView
				.setOnRefreshListener(new PullToRefreshScrollView.OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						// TODO Auto-generated method stub
//						String label = DateUtils.formatDateTime(getActivity(),
//								System.currentTimeMillis(),
//								DateUtils.FORMAT_SHOW_TIME
//										| DateUtils.FORMAT_SHOW_DATE
//										| DateUtils.FORMAT_ABBREV_ALL);
//						refreshView.getLoadingLayoutProxy()
//								.setLastUpdatedLabel(label);
						// TODO Auto-generated method stub
						new UpdateIndexTask().execute("");

					}
				});
		mScrollView = mPullRefreshScrollView.getRefreshableView();
	}

	public void loadingView() {
		mFactory = ModuleFactory.newInstance((BaseActivity) this.getActivity());
		InputStream is = getLocalXmlRes();
		if (is != null) {
			mFactory.fromXmlString(is);
			modules = mFactory.getModules();
			if (modules != null && !modules.isEmpty()) {
				for (int i = 0; i < modules.size(); i++) {
					View view = modules.get(i).getView(getActivity());
					views.put(i, view);
					view_container.addView(views.get(i), i);
				}
			}
		} else {
			mFactory.fromXmlString(getResources().openRawResource(R.raw.test));
			modules = mFactory.getModules();
			if (modules != null && !modules.isEmpty())
				view_container.removeAllViews();
			if (modules != null && !modules.isEmpty()) {
				for (int i = 0; i < modules.size(); i++) {
					View view = modules.get(i).getView(getActivity());
					views.put(i, view);
					view_container.addView(views.get(i), i);
				}
			}
		}
	}

	private List<AbstractModule> updateModules = new ArrayList<AbstractModule>();

	public void updateView() {
		refrushCount++;
		InputStream is = getLocalXmlRes();
		if (is != null) {
			mFactory.fromXmlString(is);
			updateModules = mFactory.getModules();
			view_container.removeAllViewsInLayout();
			for (int i = 0; i < updateModules.size(); i++) {
				view_container.addView(
						updateModules.get(i).getView(getActivity()), i);
			}
		}
	}

	class LoadXmlTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showListener.onShowLoadingTip("");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			VersionServiceImpl version = new VersionServiceImpl(getActivity());
			ApiResponse resp = null;
			try {
				String xmlVersion = AppSetting.getInstance(getActivity())
						.getXmlVersion();
				resp = version.getIndexPath(xmlVersion);

			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(), null, e);
			}
			return resp != null ? resp.getData() : null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null && !"".equalsIgnoreCase(result)) {
				new FileDownTask().downloadFile(FileDownTask.XML_TYPE, result,
						new CallBackListener() {
							@Override
							public void executeSucc() {
								showListener.onHideLoadingTip();
								updateView();
							}

							@Override
							public void executeFail() {
								// TODO Auto-generated method stub
								showListener.onHideLoadingTip();

							}

							@Override
							public void postExecute() {
								// TODO Auto-generated method stub

							}
						});
			} else {
				showListener.onHideLoadingTip();
			}
		}
	}

	class UpdateIndexTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			VersionServiceImpl version = new VersionServiceImpl(getActivity());
			ApiResponse resp = null;
			try {
				String xmlVersion = AppSetting.getInstance(getActivity())
						.getXmlVersion();
				resp = version.getIndexPath(xmlVersion);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(), null, e);
			}
			return resp != null ? resp.getData() : null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null && !"".equalsIgnoreCase(result)) {
				new FileDownTask().downloadFile(FileDownTask.XML_TYPE, result,
						new CallBackListener() {
							@Override
							public void executeSucc() {
								mPullRefreshScrollView.onRefreshComplete();
								updateView();
							}

							@Override
							public void executeFail() {
								// TODO Auto-generated method stub
								mPullRefreshScrollView.onRefreshComplete();

							}

							@Override
							public void postExecute() {
								// TODO Auto-generated method stub

							}
						});
			} else {
				mPullRefreshScrollView.onRefreshComplete();
			}
		}
	}

}
