package com.orientmedia.app.cfddj.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.question.QuestionInfo;
import com.tv189.dzlc.adapter.po.question.QuestionPageContent;
import com.tv189.dzlc.adapter.service.impl.QuestionServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AbsListView.OnScrollListener;
import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.QuestionAdapter;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.ui.LivingActivity;
import com.orientmedia.app.cfddj.ui.fragment.QuestionFragment.QuestQuestionTask;
import com.orientmedia.base.BaseFragment;

public class MyQuestionFragment extends BaseFragment implements OnClickListener{


	private Button refrushLoadBtn;

	private ListView myQuestionListView;

	private List<QuestionInfo> allQuestions = new ArrayList<QuestionInfo>();

	private QuestionAdapter questionAdapter;

	private QuestionPageContent questionContent = null;

	private int currentPage = 1;

	private int totalPage = 1;

	private int pageSize = 20;

	private ShowLoadingTipListener myListener;

	private PullToRefreshListView mMyPullRefreshListView;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		myListener = (LivingActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_all_question, null);
		Utils.hideSoftInput(this.getActivity());
		initView(view);
		initListener();
		getActivity().registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				new QuestQuestionTask(true,false).execute();
			}
		}, new IntentFilter(LivingActivity.REFRESH_QUESTION_ACTION));
		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (currentPage == 1)
				new QuestQuestionTask(false,false).execute();
		}
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.refrushLoad:
			new QuestQuestionTask(true,false).execute();
			break;

		default:
			break;
		}
		
	}

	public void initView(View view) {
		refrushLoadBtn = (Button) view.findViewById(R.id.refrushLoad);
		refrushLoadBtn.setOnClickListener(this);

		mMyPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		
		mMyPullRefreshListView.setEmptyView(view.findViewById(R.id.refrushLoadBorder));
		
		myQuestionListView = mMyPullRefreshListView.getRefreshableView();

		questionAdapter = new QuestionAdapter(getActivity(), allQuestions);
		myQuestionListView.setAdapter(questionAdapter);
	}

	public void initListener() {
		// Set a listener to be invoked when the list should be refreshed.
		mMyPullRefreshListView
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
						new QuestQuestionTask(true,true).execute();
					}
				});

		// Add an end-of-list listener
		mMyPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						// 加载更多
						if (allQuestions != null && allQuestions.size() > 0) {
							// 加载下一页
							new QuestQuestionTask(false,false).execute();
						}
					}
				});

	}

	/**
	 * 获取提问专区内容
	 * 
	 */
	class QuestQuestionTask extends AsyncTask<Void, Void, Boolean> {

		private boolean isClear = false;
		
		private boolean isPullRefush = false;

		public QuestQuestionTask(boolean isClear,boolean isPullRerush) {
			this.isClear = isClear;
			this.isPullRefush = isPullRerush ;
		}

		@Override
		protected void onPreExecute() {
			if(!isPullRefush)
				myListener.onShowLoadingTip("加载中...");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean flag = false;
			if (isClear) {
				currentPage = 1;
			}
			if (currentPage <= totalPage) {
				QuestionServiceImpl questionService = new QuestionServiceImpl(
						getActivity());
				try {
					questionContent = questionService.questionMy("001",
							String.valueOf(currentPage),
							String.valueOf(pageSize));
					if (questionContent != null) {
						totalPage = questionContent.getTotalPages();
						currentPage++;
						allQuestions = questionContent.getContent();
						flag = true;
					}

				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					ExcepUtils.showImpressiveException(getActivity(), null, e);
				}
			}
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(isPullRefush)
				mMyPullRefreshListView.onRefreshComplete();
			else
				myListener.onHideLoadingTip();
			
			myListener.onHideLoadingTip();
			if (questionContent != null) {
				questionAdapter.setItem(allQuestions, isClear);
			}
		}
	}

}
