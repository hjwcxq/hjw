package com.orientmedia.app.cfddj.ui.find.video;

import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.GeguAdapter;
import com.orientmedia.app.cfddj.task.StockTask;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.ui.BaodianActivity;
import com.orientmedia.app.cfddj.ui.TestActivity;
import com.orientmedia.base.BaseFragment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.StockVideo;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;
import com.tv189.dzlc.adapter.po.stock.StockVideoPageContent;
import com.tv189.dzlc.adapter.service.impl.StockServiceImpl;
import com.tv189.dzlc.adapter.service.impl.VideoServiceImpl;

public class VideoFragment extends BaseFragment {

	public static final String STOCK_GEGU_TYPE = "gufx";

	public static String STOCK_DAPAN_TYPE = "dpfx";

	public static String STOCK_EXCEPT_TYPE = "zjyc";// 热点模块

	public static String STOCK_LAOZUO_TYPE = "lzqaa";// 老左

	public static String STOCK_DATA_ONE_TO_ONE_TYPE = "sjydy";// 数据一对一

	public static String STOCK_MANAGER_MONEY_TYPE = "lcqm";// 理财敲门

	public static String STOCK_QLHM_TYPE = "gszl ";// 股市诊疗

	public static String STOCK_GSZL_TYPE = "qlhm";// 潜力黑名

	public static final String TAG = "VideoFragment";

	private ListView listView;

	private GeguAdapter adapter;

	private int currentPage = 0;

	private int totalPage = 1;

	private int page_size = 20;

	private List<StockVideo> stockVideos = null;

	private StockVideoPageContent videoPageContent = null;

	public String videoIntro, playNum;

	private ShowLoadingTipListener showLoadingListener;

	private PullToRefreshListView mPullRefreshListView;

	public VideoFragment() {

	}

	public static VideoFragment newInstance(String stockType) {
		VideoFragment frag = new VideoFragment();
		Bundle b = new Bundle();
		if (stockType == null || "".equals(stockType)) {
			stockType = MainConst.StockConst.STOCK_DAPAN_TYPE;
		}
		b.putString("stock_type", stockType);
		frag.setArguments(b);
		return frag;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		showLoadingListener = (BaodianActivity) activity;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_choose_stock, null);
		initView(view);
		initListener();
		addBaiduStatics();
		new RefreshVideoListTask(false, false).execute("");
		return view;
	}

	public void addBaiduStatics() {
		String type = getArguments().getString("stock_type");

		if (MainConst.StockConst.STOCK_DAPAN_TYPE.equals(type)) {
			BaiduStatistics.onMyEvent(getActivity(), "1210", "进入大盘解读");
		} else if (MainConst.StockConst.STOCK_GEGU_TYPE.equals(type)) {
			BaiduStatistics.onMyEvent(getActivity(), "1250", "进入个股导航");
		} else if (MainConst.StockConst.STOCK_QLHM_TYPE.equals(type)) {
			BaiduStatistics.onMyEvent(getActivity(), "1230", "进入潜力黑马");
		} else if (MainConst.StockConst.STOCK_EXCEPT_TYPE.equals(type)) {
			BaiduStatistics.onMyEvent(getActivity(), "1240", "进入热点模块");
		} else if (MainConst.StockConst.STOCK_GSZL_TYPE.equals(type)) {
			// BaiduStatistics.onMyEvent(getActivity(), "1280", "进入老左");
		} else if (MainConst.StockConst.STOCK_LAOZUO_TYPE.equals(type)) {
			BaiduStatistics.onMyEvent(getActivity(), "1280", "进入老左");
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		showLoadingListener.onHideLoadingTip();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		unregisterForContextMenu(listView);
	}

	public final int MENU_GEGU_GROUP = 10;
	public final int MENU_GEGU_COLLECT = 11;
	public final int MENU_GEGU_DOWNLOAD = 12;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.add(MENU_GEGU_GROUP, MENU_GEGU_COLLECT, 1, "收藏");
		menu.add(MENU_GEGU_GROUP, MENU_GEGU_DOWNLOAD, 2, "下载");
	}

	private StockVideo clickStock = null;

	public boolean onContextItemSelected(MenuItem item) {
		if (item.getGroupId() != MENU_GEGU_GROUP)
			return super.onContextItemSelected(item);
		AdapterContextMenuInfo adapterContextMenuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		View view = adapterContextMenuInfo.targetView;
		clickStock = (StockVideo) view.getTag(R.id.stock_video_item);

		switch (item.getItemId()) {
		case MENU_GEGU_COLLECT:
			if (clickStock != null)
				new StockTask(this.getActivity()).addCoolect(clickStock,
						showLoadingListener);
			return true;
		case MENU_GEGU_DOWNLOAD:
			if (clickStock != null)
				new StockTask(this.getActivity()).addDownload(clickStock,
						showLoadingListener);
			return true;
		default:
			break;
		}
		return true;
	}

	public void initView(View view) {
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setEmptyView(view.findViewById(R.id.emptyText));
		listView = mPullRefreshListView.getRefreshableView();
		adapter = new GeguAdapter(this.getActivity(), null);
		listView.setAdapter(adapter);
		registerForContextMenu(listView);

	}

	private long tempCurrent = 0;

	public void initListener() {

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final StockVideo stockVideo = adapter.getItem(position - 1);
				if (stockVideo != null) {
					videoIntro = stockVideo.getDetail();// 详情
					new PayMoneyTask().execute(new String[] { stockVideo
							.getId() });
				}
			}
		});

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
						new RefreshVideoListTask(true, true).execute();
					}
				});

		// Add an end-of-list listener
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						// 加载更多
						if (stockVideos != null && stockVideos.size() > 0) {
							// 加载下一页
							new RefreshVideoListTask(false, false).execute();
						}
					}
				});

	}

	/**
	 * 获取视频信息列表
	 * 
	 */
	class RefreshVideoListTask extends AsyncTask<String, Void, Boolean> {

		private boolean isClear;

		private boolean isPullRefresh;

		public RefreshVideoListTask(boolean isClear, boolean isPullRefresh) {
			this.isClear = isClear;
			this.isPullRefresh = isPullRefresh;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!isPullRefresh)
				showLoadingListener.onShowLoadingTip("");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean flag = false;
			StockServiceImpl requestService = new StockServiceImpl(
					getActivity());
			if (videoPageContent == null) {
				currentPage = 1;
			} else {
				totalPage = Integer.parseInt(videoPageContent.getTotalPages());
			}

			if (currentPage <= totalPage) {
				try {
					videoPageContent = requestService.blocklist(getArguments()
							.getString("stock_type"),
							String.valueOf(page_size), String
									.valueOf(currentPage));
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					ExcepUtils
							.showImpressiveException(getActivity(), "获取失败", e);
				}
				if (videoPageContent != null) {
					stockVideos = videoPageContent.getContent();
					if (stockVideos != null && !stockVideos.isEmpty())
						currentPage++;
					flag = true;
				}
				return Boolean.valueOf(flag);
			} else
				return Boolean.valueOf(false);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (isPullRefresh)
				mPullRefreshListView.onRefreshComplete();
			else
				showLoadingListener.onHideLoadingTip();

			if (result.booleanValue()) {
				adapter.setItems(stockVideos);
			}
		}
	}

	/**
	 * 获取视频详细信息并跳转
	 * 
	 * @author EsaFans
	 * 
	 */
	class playVideoTask extends AsyncTask<String, Void, String> {

		String SUCCESS = "success";
		String ERROR = "error";
		String id = "";

		private VideoDetails videoDetails;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				id = params[0];
				if (id != null) {
					VideoServiceImpl requestService = new VideoServiceImpl(
							getActivity());
					videoDetails = requestService.videoDetails(id);
					if (videoDetails != null)
						return SUCCESS;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(String result) {
			showLoadingListener.onHideLoadingTip();
			if (SUCCESS.equalsIgnoreCase(result)) {
				Intent intent = new Intent(getActivity(), TestActivity.class);
				TestActivity.mVideoDetails = videoDetails;
				startActivity(intent);

			} else {
				showCusToast("无法播放，地址为空");

			}
		}

	}

	class PayMoneyTask extends AsyncTask<String, Void, String> {
		String isvip = "1";
		String notvip = "2";
		ProgressDialog progressDialog;
		private String id;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingListener.onShowLoadingTip("");
		}

		@Override
		protected String doInBackground(String... params) {
			id = params[0];
			Log.e("---PayMoneyTask--", "--PayMoneyTask--");

			return isvip;

		}

		@Override
		protected void onPostExecute(String result) {

			if (isvip.equals(result)) {
				new playVideoTask().execute(new String[] { id });
			} else {
				showLoadingListener.onHideLoadingTip();
				showCusToast("签权失败");
			}

			super.onPostExecute(result);
		}
	}

}
