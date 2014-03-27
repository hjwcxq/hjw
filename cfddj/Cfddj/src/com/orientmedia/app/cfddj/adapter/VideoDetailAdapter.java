package com.orientmedia.app.cfddj.adapter;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;

public class VideoDetailAdapter extends BaseAdapter {

	private List<VideoDetails> items = new ArrayList<VideoDetails>();

	private Context context;

	public VideoDetailAdapter(Context context, List<VideoDetails> list) {
		this.context = context;
		if (list != null)
			items.addAll(list);
	}

	public void setItems(List<VideoDetails> list) {
		items.clear();
		if (list != null) {
			items.addAll(list);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public VideoDetails getItem(int position) {
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
				view = LayoutInflater.from(context).inflate(
						R.layout.item_my_video, null);
				holder = new ViewHolder();
				holder.videoTitle = (TextView) view
						.findViewById(R.id.video_title);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			VideoDetails video = items.get(position);
			holder.videoTitle.setText(video.getTitle());
			return view;

		}
		return null;
	}

	class ViewHolder {
		TextView videoTitle;
	}

}
