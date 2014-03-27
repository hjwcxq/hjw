package com.orientmedia.app.cfddj.ui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.FragmentDataNotifyListener;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.StockAdapter;
import com.orientmedia.app.cfddj.stock.DataActivity;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.ui.find.PrivateDataActivity;
import com.orientmedia.base.BaseFragment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;
import com.tv189.dzlc.adapter.service.impl.StockServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;

/**
 * 热门股票
 * 
 * @author chuanglong
 */

public class HotFragment extends BaseFragment {

	private List<StockInfo> listHot = new ArrayList<StockInfo>();

	private Map<String, HashMap<String, String>> stockPriceInfos = new HashMap<String, HashMap<String, String>>();

	private StockAdapter hotAdapter = null;

	private ListView hotStockListView;

	private URL url = null;

	private String price_now = "";

	private String price_yesterday = "";

	private StockInfo mStockInfo = null;
	
	private ShowLoadingTipListener showListener ;
	
	private FragmentDataNotifyListener notidyListener ;
	
	private PullToRefreshListView mPullRefreshListView;


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		showListener = (PrivateDataActivity) activity ;
		notidyListener = (PrivateDataActivity) activity ;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_choose_stock, null);
		initView(view);
		BaiduStatistics.onMyEvent(this.getActivity(), "1031", "进入热门股票页面");
		new GetHotStockTask(false,false).execute();
		return view;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		showListener.onHideLoadingTip();
	}
	
	
	
	
	public void initView(View view) {
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getActivity(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						new GetHotStockTask(true,true).execute();
					}
				});
		hotStockListView = mPullRefreshListView.getRefreshableView();
		
		hotAdapter = new StockAdapter(getActivity(), listHot);
		hotStockListView.setAdapter(hotAdapter);
		hotStockListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						StockInfo info = hotAdapter.getItem(arg2-1);
						if(info != null){
							new searchStockTask().execute(info.getStockCode());
						}
					}
				});
		
		registerForContextMenu(hotStockListView);
		
		
	}

	private int count = 0;

	
	public final int MENU_GEGU_GROUP = 61;
	public final int MENU_STOCK_ADD = 51;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.add(MENU_GEGU_GROUP, MENU_STOCK_ADD, 1, "添加自选股");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getGroupId() != MENU_GEGU_GROUP)
			return super.onContextItemSelected(item);
		else {
			AdapterContextMenuInfo adapterContextMenuInfo = (AdapterContextMenuInfo) item
					.getMenuInfo();
			View view = adapterContextMenuInfo.targetView;
			mStockInfo = (StockInfo) view.getTag(R.id.ll_stock_item);
			switch (item.getItemId()) {
			case MENU_STOCK_ADD:
				addStock(mStockInfo);
				break;
			default:
				break;
			}
			return true;
		}
	}
	
	
	public void addStock(StockInfo info){
		if(info == null)
			showCusToast("添加失败");
		
		new AddOptionalStockTask(info).execute();
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterForContextMenu(hotStockListView);
	}

	

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		showListener.onHideLoadingTip();
		showListener = null ;
	}

	public HashMap<String, String> getStockPriceInfoByCod(String code) {
		if (code == null || "".equals(code))
			return null;
		try {
			if (code.startsWith("6") || code.startsWith("7")
					|| code.startsWith("9")) {

				url = new URL("http://hq.sinajs.cn/list=sh" + code);

			} else if (code.startsWith("0") || code.startsWith("2")
					|| code.startsWith("3")) {

				url = new URL("http://hq.sinajs.cn/list=sz" + code);

			}
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			int count = -1;
			StringBuffer sb = new StringBuffer();

			byte[] buff = new byte[1024];
			while ((count = is.read(buff)) != -1) {
				sb.append(new String(buff, 0, count, "UTF-8"));
			}
			is.close();
			HashMap<String, String> map = new HashMap<String, String>();
			String[] datas = sb.toString().split(",");
			price_yesterday = datas[2];// sina股票，昨日收盘价
			price_now = datas[3];// sina股票，当前股价
			map.put("price_yes", price_yesterday);
			map.put("price_now", price_now);
			return map;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	class GetHotStockTask extends AsyncTask<Void, Void, Boolean> {

		boolean isRefresh = false ;
		
		boolean isPullRefrush = false ;
		
		public GetHotStockTask(boolean isRefresh,boolean isPullRefrush){
			this.isRefresh = isRefresh ;
			this.isPullRefrush = isPullRefrush;
		}
		
		@Override
		protected void onPreExecute() {
			if(!isPullRefrush)
				showListener.onShowLoadingTip("");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean flag = false;
			try {
				StockServiceImpl requestService = new StockServiceImpl(
						getActivity());
				listHot = requestService.stockListHot();
				if (listHot != null && !listHot.isEmpty()) {
					flag = true;
					for (StockInfo stockInfo : listHot) {
						stockPriceInfos
								.put(stockInfo.getStockCode(),
										getStockPriceInfoByCod(stockInfo
												.getStockCode()));
					}
				}
			} catch (ServiceException e) {
				ExcepUtils.showImpressiveException(getActivity(), null, e);
			}
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(isPullRefrush)
				mPullRefreshListView.onRefreshComplete();
			else{
				try {
					showListener.onHideLoadingTip();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			if (result.booleanValue()) {
				hotAdapter.setStockPriceInfo(stockPriceInfos,isRefresh);
				hotAdapter.setItems(listHot,isRefresh);
			}
		}

	}

	/**
	 * 添加自选股异步任务
	 * 
	 * @author jack
	 * 
	 */
	class AddOptionalStockTask extends AsyncTask<String, Integer, Boolean> {
		
		ApiResponse response = null ;
		
		private StockInfo info ;
		
		public AddOptionalStockTask( StockInfo info){
			this.info = info ;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showListener.onShowLoadingTip("");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean flag = false;
			StockServiceImpl requestService = new StockServiceImpl(
					getActivity());
			try {
				String str = MainConst.APP_KEY
						+ AppSetting.getInstance(getActivity()).getUserId()
						+ info.getStockCode();
				String newstr = Utils.getMD5(str);
				response = requestService.addDispositionStock(
						AppSetting.getInstance(getActivity()).getUserId(),
						info, newstr);
				if(response != null)
					flag = response.isSuccess();
			} catch (ServiceException e) {
				ExcepUtils.showImpressiveException(getActivity(), null, e);
			} catch (OrmSqliteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			showListener.onHideLoadingTip();
			if (result.booleanValue()) {
				Toast.makeText(getActivity(), "添加成功", 1000).show();
				notidyListener.notifyFragment();  //通知fragment
			} else {
				if(response != null)
					Toast.makeText(getActivity(), response.getMsg(), 1000).show();
				else
					Toast.makeText(getActivity(), "添加失败", 1000).show();
			}
		}
	}
	
	
	
	private StockInfo searchStockDetail = null;

	public class searchStockTask extends AsyncTask<String, Void, String> {

		String SUCCESS = "successpre";
		String ERROR = "errorpre";

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			showListener.onShowLoadingTip("");
		}

		@Override
		protected String doInBackground(String... params) {

			String keyword = params[0];
			StockServiceImpl requestService = new StockServiceImpl(
					getActivity());
			try {
				searchStockDetail = requestService.searchStock(keyword);
				if (searchStockDetail != null) {
					return SUCCESS;
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(),
						null, e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			showListener.onHideLoadingTip();
			if (SUCCESS.equalsIgnoreCase(result)) {
				Intent intent = new Intent(getActivity(),
						DataActivity.class);
				intent.putExtra("url", searchStockDetail.getUrl());
				intent.putExtra("code", searchStockDetail.getKeyword());
				intent.putExtra("name", searchStockDetail.getStockName());
				startActivity(intent);

			} else {
				showCusToast("查询失败");
			}
		}

	}
}
