package com.orientmedia.app.cfddj.adapter;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.tool.imageutils.ImageFetcher;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.base.ItemNode;
import com.tv189.dzlc.adapter.util.Utils;

public class NewsItemAdapter extends BaseAdapter {

	private Context mContext;

	private List<ItemNode> items = new ArrayList<ItemNode>();
	
	private ImageFetcher mImageFetcher ;

	public NewsItemAdapter(Context cont, List<ItemNode> list) {
		this.mContext = cont;
		mImageFetcher = new ImageFetcher(mContext);
		if (list != null)
			items.addAll(list);
	}

	public void setItems(List<ItemNode> list) {
		items.clear();
		if (list != null)
			items.addAll(list);
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public ItemNode getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (items != null && items.size() > 0 && position < items.size()) {
			View view = convertView;
			ViewHolder holder = null;
			if (view == null) {
				view = LayoutInflater.from(mContext).inflate(
						R.layout.item_news, null);
				holder = (ViewHolder) view.getTag();
				if (holder == null) {
					holder = new ViewHolder();
					// holder.newIntro = (TextView) view
					// .findViewById(R.id.news_intro);
					holder.newTitle = (TextView) view
							.findViewById(R.id.news_title);
					holder.newsDesc = (TextView) view
							.findViewById(R.id.news_desc);
					holder.icon = (ImageView) view
							.findViewById(R.id.icon);
					holder.iconType = (ImageView) view
							.findViewById(R.id.icon_type);
					holder.view1 = (View) view.findViewById(R.id.view1);
					// holder.newCatalog = (TextView) view
					// .findViewById(R.id.news_catalog);
					view.setTag(holder);
				} else {
					holder = (ViewHolder) view.getTag();
				}
			}
			ItemNode item = items.get(position);
			if (item.getCatalog().getText() != null
					&& item.getCatalog().getText() != "") {
				holder.newTitle.setText("【" + item.getCatalog().getText() + "】"
						+ item.getTitle().getText());
			} else {
				holder.newTitle.setText(item.getTitle().getText());
			}

			if (items.size() <= 1) {
				holder.view1.setVisibility(View.GONE);
			} else if (items.size() - 1 == position) {
				holder.view1.setVisibility(View.GONE);
			} else {
				holder.view1.setVisibility(View.VISIBLE);
			}
			
			if(item.getBackimg().isShow() && item.getBackimg().getSrc() != null && !"".equals(item.getBackimg().getSrc())){
				holder.icon.setVisibility(View.VISIBLE);
				mImageFetcher.loadThumbnailImage(item.getBackimg().getSrc(), holder.icon);
				holder.newsDesc.setVisibility(View.VISIBLE);
				holder.newsDesc.setText(Utils.toDbc(item.getDesc().getText()));
			}else{
				holder.icon.setVisibility(View.GONE);
				holder.newsDesc.setVisibility(View.GONE);
			}
			if(item.getIcon().isShow() && item.getIcon().getSrc() != null && !"".equals(item.getIcon().getSrc())){
				holder.iconType.setVisibility(View.VISIBLE);
//				RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)holder.icon.getLayoutParams();
//				param.width = item.getBackimg().getWidth();
//				param.height = item.getBackimg().getHeight();
//				holder.icon.setLayoutParams(param);
				mImageFetcher.loadThumbnailImage(item.getIcon().getSrc(), holder.iconType);
			}else{
				holder.iconType.setVisibility(View.GONE);
			}
			

			return view;
		}
		return null;
	}

	class ViewHolder {
		TextView newTitle;
		TextView newsDesc;
		ImageView icon;
		ImageView iconType;
		View view1;
	}

}
