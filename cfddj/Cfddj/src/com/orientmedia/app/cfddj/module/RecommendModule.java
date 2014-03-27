package com.orientmedia.app.cfddj.module;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.adapter.RecommendAdapter;
import com.orientmedia.app.cfddj.ui.BaodianActivity;
import com.orientmedia.app.cfddj.widget.NoScrollListView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.base.AbstractAction;
import com.tv189.dzlc.adapter.po.base.AbstractModule;
import com.tv189.dzlc.adapter.po.base.ItemNode;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;
import com.tv189.dzlc.adapter.util.Utils;

public class RecommendModule extends AbstractModule {

	private RelativeLayout more;
	private TextView title;
	private NoScrollListView listView;

	private static VideoDetails videoDetail;
	
	private List<ItemNode> items = new ArrayList<ItemNode>();

	@Override
	public View getView(Context mContext) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.module_recommend, null);
		initView(mContext, view);
		return view;
	}

	public void initView(final Context mContext, View view) {
		items = getItems();
		title = (TextView) view.findViewById(R.id.general_title);
		listView = (NoScrollListView) view.findViewById(R.id.general_listview);
		more = (RelativeLayout) view.findViewById(R.id.rl_general_more);
		title.setText(getTitleNode().getText());
		listView.setAdapter(new RecommendAdapter(mContext, items));
		Utils.setListViewHeightBasedOnChildren(listView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AbstractAction action = items.get(position).getAction() ;
				if (action != null) {
					action.jumpByActionType(action.getParaMap());
				}
			}
		});
		more.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(mContext, BaodianActivity.class);
				// it.putExtra("catalogId", getCatalogIdNode().getText());
				mContext.startActivity(it);
			}
		});
	}

	
}
