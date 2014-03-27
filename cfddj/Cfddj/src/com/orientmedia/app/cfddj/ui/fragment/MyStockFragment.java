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
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.StockAdapter;
import com.orientmedia.app.cfddj.stock.DataActivity;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.ui.find.PrivateDataActivity;
import com.orientmedia.base.BaseFragment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;
import com.tv189.dzlc.adapter.service.impl.StockServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;

/**
 * 自选股
 * 
 * @author chuanglong
 */
public class MyStockFragment extends BaseFragment {

	private List<StockInfo> listOption = new ArrayList<StockInfo>();

	private Map<String, HashMap<String, String>> stockPriceInfos = new HashMap<String, HashMap<String, String>>();

	private StockAdapter stockAdapter = null;

	private URL url = null;

	private String price_now = "";

	private String price_yesterday = "";

	private StockInfo mStockInfo = null;

	private ListView myStockListView;
	
	private PullToRefreshListView mPullRefreshListView;
	
	private ShowLoadingTipListener showListener ;
	
	private BroadcastReceiver broadcast ;
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		showListener = (PrivateDataActivity)activity ;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_choose_stock, null);
		broadcast = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				loadData();
			}
		};
		initView(view);
		
		BaiduStatistics.onMyEvent(this.getActivity(), "1032", "进入自选股页面");
		getActivity().registerReceiver(broadcast, new IntentFilter("ADD_MY_STOCK_SUCCESS"));
		loadData();
		return view;
	}
	
	

	public void loadData() {
		StockServiceImpl stockService = new StockServiceImpl(this.getActivity());
		try {
			listOption = stockService.queryStockInfoFromDb(AppSetting.getInstance(this.getActivity()).getUserId()) ;
			if(listOption != null && !listOption.isEmpty()){
				new LoadStockPriceTask(listOption).execute("");
			}else{
				new RefreshListTask(true, false).execute();
			}
		} catch (OrmSqliteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		showListener.onHideLoadingTip();
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		getActivity().unregisterReceiver(broadcast);
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
						new RefreshListTask(true,true).execute();
					}
				});

		// Add an end-of-list listener

		myStockListView = mPullRefreshListView.getRefreshableView();
		
		stockAdapter = new StockAdapter(getActivity(), listOption);
		myStockListView.setAdapter(stockAdapter);
		registerForContextMenu(myStockListView);
		
		myStockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				StockInfo info = stockAdapter.getItem(arg2-1);
				if(info != null){
					new searchStockTask().execute(info.getStockCode());
				}
			}
		});
	}

	public final int MENU_GEGU_GROUP = 51;
	public final int MENU_STOCK_DELETE = 51;
	public final int MENU_STOCK_SELECT = 52;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.add(MENU_GEGU_GROUP, MENU_STOCK_DELETE, 1, "删除");
		menu.add(MENU_GEGU_GROUP, MENU_STOCK_SELECT, 2, "查询");
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
			case MENU_STOCK_DELETE:
				new DeleteStockTask(mStockInfo).execute("");
				break;
			case MENU_STOCK_SELECT:
				break;
			default:
				break;
			}
			return true;
		}
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

	/****
	 * 自选股列表
	 */
	class RefreshListTask extends AsyncTask<Void, Void, Boolean> {

		boolean isRefresh = false;
		
		private boolean isPullRefrush = false;

		public RefreshListTask(boolean isRefresh,boolean isPullRefrush) {
			this.isRefresh = isRefresh;
			this.isPullRefrush = isPullRefrush ;
		}

		@Override
		protected void onPreExecute() {
			if(!isPullRefrush)
				showListener.onShowLoadingTip("");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			boolean flag = false;
			StockServiceImpl requestService = new StockServiceImpl(
					getActivity());
			String str = "40beb8d53d559b07a055665f9bae93b7"
					+ AppSetting.getInstance(getActivity()).getUserId();

			String newstr = Utils.getMD5(str);

			try {
				listOption = requestService.favoriteList(newstr, AppSetting.getInstance(getActivity()).getUserId());
				if (listOption != null && !listOption.isEmpty()) {
					flag = true;
					for (StockInfo stockInfo : listOption) {
						stockPriceInfos.put(stockInfo.getStockCode(),
										getStockPriceInfoByCod(stockInfo
												.getStockCode()));
					}
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(), null, e);
			} catch (OrmSqliteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(isPullRefrush)
				mPullRefreshListView.onRefreshComplete();
			else
				showListener.onHideLoadingTip();
			if (result.booleanValue()) {
				if(stockPriceInfos != null && !stockPriceInfos.isEmpty())
					stockAdapter.setStockPriceInfo(stockPriceInfos,isRefresh);
				stockAdapter.setItems(listOption,isRefresh);
			}
		}
	}

	class DeleteStockTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		private StockInfo info;

		public DeleteStockTask(StockInfo info) {
			this.info = info;
		}

		@Override
		protected void onPreExecute() {
			showListener.onShowLoadingTip("");
		};

		@Override
		protected Boolean doInBackground(String... params) {
			StockServiceImpl requestService = new StockServiceImpl(
					getActivity());
			String str = "40beb8d53d559b07a055665f9bae93b7"
					+ AppSetting.getInstance(getActivity()).getUserId()
					+ info.getStockCode();
			String newstr = Utils.getMD5(str);
			boolean flag = false;
			try {
				// 删除自选股
				flag = requestService.cancelFavorite(info,
						newstr);
			} catch (ServiceException e) {
				ExcepUtils.showImpressiveException(getActivity(), "删除自选股失败", e);
			} catch (OrmSqliteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			showListener.onHideLoadingTip();
			if (result.booleanValue()) {
				listOption.remove(info);
				stockAdapter.setItems(listOption, true);
				Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT)
						.show();
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
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("正在获取股票信息");
			progressDialog.setCancelable(true);
			progressDialog.show();
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
			try {
				progressDialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}
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
	
	
	class LoadStockPriceTask  extends AsyncTask<String, Void, String>{
		
		private List<StockInfo> stocks ;
		
		public LoadStockPriceTask(List<StockInfo> stocks){
			this.stocks = stocks ;
		}
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			showListener.onShowLoadingTip("");
		}
		
		
		@Override
		protected String doInBackground(String... params)  {
			// TODO Auto-generated method stub
			if(stocks != null && !stocks.isEmpty()){
				for (StockInfo stock : stocks) {
					stockPriceInfos.put(stock.getStockCode(),
							getStockPriceInfoByCod(stock.getStockCode()));
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			showListener.onHideLoadingTip();
			stockAdapter.setStockPriceInfo(stockPriceInfos,true);
			stockAdapter.setItems(stocks, true);
		}
		
	}

}
