package com.orientmedia.app.cfddj.ui.expert;

import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.adapter.ExpertListAdapter;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.base.BaseActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.ExpertsInfo;
import com.tv189.dzlc.adapter.service.impl.ExpertsServiceImpl;

public class ExpertOneToOneActivity extends BaseActivity {

	private List<ExpertsInfo> expertList;

	private PullToRefreshListView mPullRefreshListView;

	private ListView listView;

	private Button refrushBtn;

	private ExpertListAdapter expertAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expertlist);
		BaiduStatistics.onMyEvent(this, "1010", this.getClass().getName()
				+ "进入专家一对一页面");
		initView();
		new ExpertListTask(true,false).execute();
	}

	public void initView() {
		refrushBtn = (Button) findViewById(R.id.refrush_btn);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				new ExpertListTask(true,true).execute();
			}
		});
		listView = mPullRefreshListView.getRefreshableView();
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ExpertsInfo expertsInfo = expertAdapter.getItem(arg2-1) ;
				if(expertsInfo != null){
					toExpertInfo(expertsInfo);
				}
			}
		});
		expertAdapter = new ExpertListAdapter(this, expertList, imageLoader);
		listView.setAdapter(expertAdapter);
	}

	/*****
	 * 调转到专家详情
	 * 
	 * @param expertsInfo
	 */
	private void toExpertInfo(ExpertsInfo expertsInfo) {
		SystemContext.getInstance(ExpertOneToOneActivity.this)
				.setCurrentExpert(expertsInfo);
		intentTo(ExpertOneToOneActivity.this, QueueActivity.class);
	}

	/****
	 * 获取专家列表任务
	 * 
	 * @author hh 2013-7-8 16:00
	 * 
	 */
	class ExpertListTask extends AsyncTask<Void, Void, Boolean> {
		private boolean isClear ;
		private boolean isPullRefrush ;

		public ExpertListTask(boolean isClear,boolean isPullRefrush){
			this.isClear = isClear ;
			this.isPullRefrush = isPullRefrush ;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!isPullRefrush)
				showLoadingIcon();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			ExpertsServiceImpl requestService = new ExpertsServiceImpl(
					ExpertOneToOneActivity.this);
			boolean flag = false ;
			try {
				List<ExpertsInfo> expertListT;
				expertListT = requestService.expertsList();
				if (expertListT != null) {
					expertList = expertListT;
					flag = true;
				}
			} catch (ServiceException e) {
				ExcepUtils.showImpressiveException(ExpertOneToOneActivity.this, null, e);
			}
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(isPullRefrush)
				mPullRefreshListView.onRefreshComplete();
			else
				hideLoadingIcon();
			if (result.booleanValue()) 
				expertAdapter.setItems(expertList, isClear);
			else
				showCusToast("加载失败");
			
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
