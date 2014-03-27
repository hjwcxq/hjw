package com.orientmedia.app.cfddj.adapter;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tv189.dzlc.adapter.po.sqlpo.ExpertsInfo;

public class ExpertListAdapter extends BaseAdapter {

	private List<ExpertsInfo> items = new ArrayList<ExpertsInfo>();

	private Context cont;
	
	private ImageLoader mImageLoader ;

	public ExpertListAdapter(Context cont, List<ExpertsInfo> list,ImageLoader mImageLoader) {
		this.cont = cont;
		this.mImageLoader = mImageLoader;
		if (list != null)
			items.addAll(list);
	}

	public void setItems(List<ExpertsInfo> list, boolean isClear) {
		if (isClear)
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
	public ExpertsInfo getItem(int position) {
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
		View view = convertView ;
		ViewHolder holder = null ;
		
		if(items != null && items.size() > 0 && position < items.size()){
			if(view  == null){
				view = LayoutInflater.from(cont).inflate(R.layout.item_expert, null);
				holder = new ViewHolder();
				holder.expertIcon = (ImageView) view.findViewById(R.id.img_expert_head) ;
				holder.expertName = (TextView) view.findViewById(R.id.expert_name) ;
				holder.expertIntro = (TextView) view.findViewById(R.id.expert_intro) ;
				view.setTag(holder);
			}else{
				holder = (ViewHolder) view.getTag();
			}
			ExpertsInfo expert = items.get(position);
			
			holder.expertName.setText(expert.getExpertName());
			holder.expertIntro.setText(expert.getExpertDetail());
			mImageLoader.displayImage(expert.getExpert_thumb_url(), holder.expertIcon);
			return view ;
		}
		
		return null;
	}
	
	
	class ViewHolder {
		ImageView expertIcon ;
		TextView expertName;
		TextView expertIntro;
	}

}
