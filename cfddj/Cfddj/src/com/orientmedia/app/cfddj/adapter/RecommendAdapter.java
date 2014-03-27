package com.orientmedia.app.cfddj.adapter;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.tool.imageutils.ImageFetcher;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.base.ItemNode;

public class RecommendAdapter extends BaseAdapter {

	private List<ItemNode> items = new ArrayList<ItemNode>();

	private Context mContext;

	private ImageFetcher mImageFetcher;

	public RecommendAdapter(Context cont, List<ItemNode> list) {
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
						R.layout.item_recommend, null);
				holder = new ViewHolder();
				holder.reco_title = (TextView) view
						.findViewById(R.id.reco_title);
				holder.recoDesc = (TextView) view
						.findViewById(R.id.reco_desc);
				holder.icon = (ImageView) view.findViewById(R.id.icon);
				holder.iconType = (ImageView) view.findViewById(R.id.icon_type);
				holder.view1 = (View) view.findViewById(R.id.view1);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			ItemNode item = items.get(position);

			// String html = "<div style='text-align:center;' >"+"<span>" +
			// item.getTitle().getText() + "</span>";
			// if (item.getIcon().isShow() && item.getIcon().getSrc() != null
			// && !"".equals(item.getIcon().getSrc())) {
			// html = html + "<img src='" + item.getIcon().getSrc() +
			// "' align='top'/></div>";
			// URLImageParser p = new URLImageParser(holder.reco_title,
			// mContext);
			// Spanned htmlSpan = Html.fromHtml(html, p, null);
			// holder.reco_title.setText(htmlSpan);
			// } else {
			holder.reco_title.setText(item.getTitle().getText());
			// }

			if (item.getBackimg().isShow()
					&& item.getBackimg().getSrc() != null
					&& !"".equals(item.getBackimg().getSrc())) {
				holder.icon.setVisibility(View.VISIBLE);
				
				// RelativeLayout.LayoutParams param =
				// (RelativeLayout.LayoutParams)holder.icon.getLayoutParams();
				// param.width = item.getBackimg().getWidth();
				// param.height = item.getBackimg().getHeight();
				// holder.icon.setLayoutParams(param);
				mImageFetcher.loadThumbnailImage(item.getBackimg().getSrc(),
						holder.icon);
			} else {
				holder.icon.setVisibility(View.GONE);
				holder.recoDesc.setVisibility(View.GONE);
			}
			
			holder.recoDesc.setVisibility(View.VISIBLE);
			holder.recoDesc.setText(item.getDesc().getText());
			

			if (item.getIcon().isShow() && item.getIcon().getSrc() != null
					&& !"".equals(item.getIcon().getSrc())) {
				holder.iconType.setVisibility(View.VISIBLE);
//				RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) holder.icon
//						.getLayoutParams();
//				param.width = item.getBackimg().getWidth();
//				param.height = item.getBackimg().getHeight();
//				holder.icon.setLayoutParams(param);
				mImageFetcher.loadThumbnailImage(item.getIcon().getSrc(),
						holder.iconType);
			} else {
				holder.iconType.setVisibility(View.GONE);
			}

			if (items.size() <= 1) {
				holder.view1.setVisibility(View.GONE);
			} else if (items.size() - 1 == position) {
				holder.view1.setVisibility(View.GONE);
			} else {
				holder.view1.setVisibility(View.VISIBLE);
			}
			return view;
		}
		return null;
	}

	class ViewHolder {
		TextView reco_title;
		TextView recoDesc;
		ImageView icon;
		ImageView iconType;
		View view1;

	}

	class URLDrawable extends BitmapDrawable {
		// the drawable that you need to set, you could set the initial drawing
		// with the loading image if you need to
		protected Drawable drawable;

		@Override
		public void draw(Canvas canvas) {
			// override the draw to facilitate refresh function later
			if (drawable != null) {
				drawable.draw(canvas);
			}
		}
	}

	class URLImageParser implements ImageGetter {
		Context c;
		TextView container;

		/***
		 * Construct the URLImageParser which will execute AsyncTask and refresh
		 * the container
		 * 
		 * @param t
		 * @param c
		 */
		public URLImageParser(TextView t, Context c) {
			this.c = c;
			this.container = t;
		}

		public Drawable getDrawable(String source) {
			URLDrawable urlDrawable = new URLDrawable();

			// get the actual source
			ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(
					urlDrawable);

			asyncTask.execute(source);

			// return reference to URLDrawable where I will change with actual
			// image from
			// the src tag
			return urlDrawable;
		}

		class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
			URLDrawable urlDrawable;

			public ImageGetterAsyncTask(URLDrawable d) {
				this.urlDrawable = d;
			}

			@Override
			protected Drawable doInBackground(String... params) {
				String source = params[0];
				return fetchDrawable(source);
			}

			@Override
			protected void onPostExecute(Drawable result) {
				// set the correct bound according to the result from HTTP call
				if (result != null) {
					urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(),
							0 + result.getIntrinsicHeight());

					// change the reference of the current drawable to the
					// result
					// from the HTTP call
					urlDrawable.drawable = result;
					// redraw the image by invalidating the container
					URLImageParser.this.container.invalidate();

					// For ICS
					URLImageParser.this.container
							.setHeight((URLImageParser.this.container
									.getHeight() + result.getIntrinsicHeight()));

					// Pre ICS
					URLImageParser.this.container.setEllipsize(null);

				}
			}

			/***
			 * Get the Drawable from URL
			 * 
			 * @param urlString
			 * @return
			 */
			public Drawable fetchDrawable(String urlString) {
				try {
					URL aURL = new URL(urlString);
					final URLConnection conn = aURL.openConnection();
					conn.connect();
					final BufferedInputStream bis = new BufferedInputStream(
							conn.getInputStream());
					final Bitmap bm = BitmapFactory.decodeStream(bis);
					Drawable drawable = new BitmapDrawable(bm);
					drawable.setBounds(0, 0, bm.getWidth(), bm.getHeight());
					return drawable;
				} catch (Exception e) {
					return null;
				}
			}
		}
	}

}
