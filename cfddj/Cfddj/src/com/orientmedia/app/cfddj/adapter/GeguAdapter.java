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
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.sqlpo.StockVideo;

public class GeguAdapter extends BaseAdapter {

	private Context mContext;

	private List<StockVideo> items = new ArrayList<StockVideo>();
	
	private ImageFetcher mImageFetcher ;

	public GeguAdapter(Context context, List<StockVideo> list) {
		this.mContext = context;
		mImageFetcher = new ImageFetcher(mContext);
		if (list != null)
			items.addAll(list);
	}

	public void setItems(List<StockVideo> list) {
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
	public StockVideo getItem(int position) {
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
		
		if (items != null && items.size() > 0 && position < items.size()) {
			View view = convertView;
			ViewHolder holder = null;
			if (view == null) {
				view = LayoutInflater.from(mContext).inflate(
						R.layout.item_pointvideo, null);
				if (holder == null) {
					holder = new ViewHolder();
					holder.iconImageView = (ImageView) view
							.findViewById(R.id.icon4PointVideoItem);
					holder.titleTextView = (TextView) view
							.findViewById(R.id.title4PointVideoItem);
					holder.detailTextView = (TextView) view
							.findViewById(R.id.detail4PointVideoItem);
					view.setTag(holder);
				}
			} else {
				holder = (ViewHolder) view.getTag();
			}
			StockVideo info = items.get(position);
			holder.titleTextView.setText(info.getTitle());
			holder.detailTextView.setText("" + info.getDetail());
			mImageFetcher.loadThumbnailImage(info.getImg(),
					holder.iconImageView);
			view.setTag(R.id.stock_video_item, info);
			return view;
		} else
			return null;

	}

	class ViewHolder {
		TextView titleTextView;
		TextView detailTextView;
//		ImageView rightIconImageView;
		ImageView iconImageView;
	}
}
