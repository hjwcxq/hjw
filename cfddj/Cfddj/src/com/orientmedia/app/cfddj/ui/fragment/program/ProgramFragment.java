package com.orientmedia.app.cfddj.ui.fragment.program;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.ProgramAdapter;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.ui.ProgramActivity;
import com.orientmedia.base.BaseFragment;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.repertoire.RepertoireInfo;
import com.tv189.dzlc.adapter.service.impl.RepertoireServiceImpl;

public class ProgramFragment extends BaseFragment {

	private ListView listView;

	private List<RepertoireInfo> programs = new ArrayList<RepertoireInfo>();

	private ProgramAdapter adapter;

	private ShowLoadingTipListener showListener;

	private PullToRefreshListView mPullRefreshListView;

	public ProgramFragment() {

	}

	public static ProgramFragment newInstance(String date) {
		ProgramFragment frag = new ProgramFragment();
		Bundle b = new Bundle();
		if (date == null || "".equals(date)) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			date = sdf.format(now);
		}
		b.putString("date", date);
		frag.setArguments(b);
		return frag;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		showListener = (ProgramActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_choose_stock, null);
		initView(view);
		initListener();
		new LoadProgramTask(true, false).execute();
		return view;
	}

	public void initListener() {
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getActivity(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						new LoadProgramTask(true, true).execute();
					}
				});

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void initView(View view) {
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new ProgramAdapter(null, true, getActivity());
		listView.setAdapter(adapter);
	}

	class LoadProgramTask extends AsyncTask<Void, Void, Boolean> {

		private boolean isClear = false;

		private boolean isPullRefrush = false;

		public LoadProgramTask(boolean isClear, boolean isPullRefresh) {
			this.isClear = isClear;
			this.isPullRefrush = isPullRefresh;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!isPullRefrush)
				showListener.onShowLoadingTip("");
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			boolean flag = false;
			RepertoireServiceImpl requestService = new RepertoireServiceImpl(
					getActivity());

			try {
				programs = requestService.schedule(getArguments().getString(
						"date"));
				if (programs != null && !programs.isEmpty()) {
					flag = true;
				}

			} catch (ServiceException e) {
				ExcepUtils.showImpressiveException(getActivity(), null, e);
			}
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (isPullRefrush)
				mPullRefreshListView.onRefreshComplete();
			else
				showListener.onHideLoadingTip();

			if (result.booleanValue()) {
				adapter.setItems(programs);
			} else {
				showCusToast("加载节目列表失败");
			}

		}

	}

}
