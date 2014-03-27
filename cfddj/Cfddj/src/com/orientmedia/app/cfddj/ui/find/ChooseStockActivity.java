package com.orientmedia.app.cfddj.ui.find;

import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.adapter.ChooseStockAdapter;
import com.orientmedia.app.cfddj.stock.DataActivity;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.base.BaseActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;
import com.tv189.dzlc.adapter.po.stock.NewStockInfo;
import com.tv189.dzlc.adapter.po.stock.NewStockInfoContent;
import com.tv189.dzlc.adapter.service.impl.StockServiceImpl;

/**
 * 一键选股
 * 
 * @author chuanglong
 * 
 */
public class ChooseStockActivity extends BaseActivity {

	private ListView listView;

	private List<NewStockInfo> newsStocks = null;

	private NewStockInfoContent stockContent = null;

	private ChooseStockAdapter stockAdapter = null;

	private PullToRefreshListView mPullRefreshListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_stock);
		BaiduStatistics.onMyEvent(this, "1040", this.getClass().getName()
				+ "进入一键选股");
		initView();
		new GetNewStockTask(false, false).execute();

	}

	public void initView() {

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						new GetNewStockTask(true, true).execute();
					}
				});
		
		
		mPullRefreshListView.setEmptyView(findViewById(R.id.emptyText));

		// Add an end-of-list listener
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						// 加载更多
						if (newsStocks != null && newsStocks.size() > 0) {
							// 加载下一页
							new GetNewStockTask(false, false).execute();
						}
					}
				});

		listView = mPullRefreshListView.getRefreshableView();
		stockAdapter = new ChooseStockAdapter(ChooseStockActivity.this,
				newsStocks);
		listView.setAdapter(stockAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				NewStockInfo stock = stockAdapter.getItem(arg2 - 1);
				if (stock != null) {
					new searchStockTask().execute(stock.getCode());
				}
			}
		});
	}

	private int currentPage = 1;
	private int totlaPage = 1;
	private int pageSize = 20;

	class GetNewStockTask extends AsyncTask<String, Void, Boolean> {

		boolean isClear = false;

		boolean isPullRefesh = false;

		public GetNewStockTask(boolean isClear, boolean isPullRefesh) {
			this.isClear = isClear;
			this.isPullRefesh = isPullRefesh;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (!isPullRefesh)
				showLoadingIcon();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (isClear) {
				currentPage = 1;
			}

			boolean flag = false;
			StockServiceImpl stockService = new StockServiceImpl(
					ChooseStockActivity.this);
			if (currentPage <= totlaPage) {
				try {
					stockContent = stockService.getNewsStockInfoList(
							String.valueOf(currentPage),
							String.valueOf(pageSize));
					if (stockContent != null) {
						currentPage++;
						totlaPage = Integer.parseInt(stockContent
								.getTotalPages());
						flag = true;
						newsStocks = stockContent.getContent();
					}
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					ExcepUtils.showImpressiveException(
							ChooseStockActivity.this, null, e);
				}
			}
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (isPullRefesh)
				mPullRefreshListView.onRefreshComplete();
			else
				hideLoadingIcon();
			if (result.booleanValue()) {
				stockAdapter.setItems(newsStocks, isClear);
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
			showLoadingIcon();
		}

		@Override
		protected String doInBackground(String... params) {

			String keyword = params[0];
			StockServiceImpl requestService = new StockServiceImpl(ChooseStockActivity.this);
			try {
				searchStockDetail = requestService.searchStock(keyword);
				if (searchStockDetail != null) {
					return SUCCESS;
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(ChooseStockActivity.this,
						null, e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				hideLoadingIcon();
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (SUCCESS.equalsIgnoreCase(result)) {
				Intent intent = new Intent(ChooseStockActivity.this,
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
