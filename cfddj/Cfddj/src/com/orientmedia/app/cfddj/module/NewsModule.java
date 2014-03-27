package com.orientmedia.app.cfddj.module;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.adapter.NewsItemAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tv189.dzlc.adapter.po.base.AbstractAction;
import com.tv189.dzlc.adapter.po.base.AbstractModule;
import com.tv189.dzlc.adapter.po.base.ItemNode;
import com.tv189.dzlc.adapter.util.Utils;

public class NewsModule extends AbstractModule {

	private ListView listView;
	
	private NewsItemAdapter newsAdapter;
	
	
	public NewsItemAdapter getNewsAdapter() {
		return newsAdapter;
	}


	@Override
	public View getView(Context mContext) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(R.layout.news,null);
		initView(mContext,view);
		return view;
	}

	private List<ItemNode> news_items = new ArrayList<ItemNode>();
	
	@SuppressLint("NewApi")
	public void initView(Context mContext,View view){
		news_items = this.getItems();
		listView = (ListView)view.findViewById(R.id.listView);
		newsAdapter = new NewsItemAdapter(mContext, news_items);
		listView.setAdapter(newsAdapter);
		Utils.setListViewHeightBasedOnChildren(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AbstractAction action = news_items.get(position).getAction() ;
				if(action != null){
					action.jumpByActionType(action.getParaMap());
				}
				
			}
		});
	}

}
