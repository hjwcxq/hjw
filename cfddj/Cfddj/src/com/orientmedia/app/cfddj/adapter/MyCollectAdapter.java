//package tv.aniu.app.dzlc.adapter;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import tv.aniu.app.dzlc.R;
//import tv.aniu.app.dzlc.widget.AlwaysMarQueeTextView;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.Animation.AnimationListener;
//import android.view.animation.AnimationUtils;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.tv189.dzlc.adapter.po.collection.CollectionList;
//
//public class MyCollectAdapter extends BaseAdapter {
//
//	/**
//	 * @Fields TAG : Log 标签
//	 */
//	private final static String TAG = "MyCollectAdapter";
//
//	/**
//	 * @Fields mInflater : 布局填充器
//	 */
//	private LayoutInflater mInflater;
//
//	/**
//	 * @Fields items : 列表数据集合
//	 */
//	private List<CollectionList> items = new ArrayList<CollectionList>();
//
//	/**
//	 * @Fields mContext : 上下文
//	 */
//	private Activity mActivity;
//
//	private ArrayList<PhotoInfo> mPhotoInfos = new ArrayList<PhotoInfo>();
//
//	public class PhotoInfo {
//
//		private String url = "";
//		private ImageView imageView = null;
//
//		public String getUrl() {
//			return url;
//		}
//
//		public void setUrl(String url) {
//			this.url = url;
//		}
//
//		public ImageView getImageView() {
//			return imageView;
//		}
//
//		public void setImageView(ImageView imageView) {
//			this.imageView = imageView;
//		}
//	}
//
//	private void asyncLoadImage(String url, ImageView imageView) {
//
//		PhotoInfo photoInfo = new PhotoInfo();
//		photoInfo.setUrl(url);
//		photoInfo.setImageView(imageView);
//		synchronized (mPhotoInfos) {
//			for (int i = 0; i < mPhotoInfos.size(); i++) {
//				if (mPhotoInfos.get(i).getImageView() == photoInfo
//						.getImageView()) {
//					mPhotoInfos.remove(i);
//					break;
//				}
//			}
//			mPhotoInfos.add(photoInfo);
//			mPhotoInfos.notifyAll();
//		}
//	}
//
//	private Thread mGetPhotoThread = new Thread() {
//		@Override
//		public void run() {
//
//			while (true) {
//				if (mPhotoInfos.size() > 0) {
//					final PhotoInfo photoInfo;
//					synchronized (mPhotoInfos) {
//						photoInfo = mPhotoInfos.get(0);
//						mPhotoInfos.remove(0);
//						mPhotoInfos.notifyAll();
//					}
//
//					Drawable drawable = null;
//					try {
//						drawable = Drawable.createFromStream(
//								new URL(photoInfo.getUrl()).openStream(),
//								"image.png");
//					} catch (Exception e) {
//						drawable = null;
//						e.printStackTrace();
//					}
//
//					final Drawable drawableTemp = drawable;
//					if (drawable != null) {
//						boolean isReAdded = false;
//						synchronized (mPhotoInfos) {
//							for (int i = 0; i < mPhotoInfos.size(); i++) {
//								if (mPhotoInfos.get(i).getImageView() == photoInfo
//										.getImageView()) {
//									isReAdded = true;
//									break;
//								}
//							}
//						}
//						if (!isReAdded) {
//							mActivity.runOnUiThread(new Runnable() {
//
//								@Override
//								public void run() {
//									Animation mImageShowAnim = AnimationUtils
//											.loadAnimation(
//													mActivity,
//													R.anim.anim_sub_pointlist_titleimage);
//									mImageShowAnim
//											.setAnimationListener(new AnimationListener() {
//
//												@Override
//												public void onAnimationStart(
//														Animation animation) {
//													photoInfo
//															.getImageView()
//															.setImageDrawable(
//																	drawableTemp);
//												}
//
//												@Override
//												public void onAnimationRepeat(
//														Animation animation) {
//													// TODO Auto-generated
//													// method stub
//
//												}
//
//												@Override
//												public void onAnimationEnd(
//														Animation animation) {
//													// TODO Auto-generated
//													// method stub
//
//												}
//											});
//									photoInfo.getImageView().startAnimation(
//											mImageShowAnim);
//								}
//							});
//						}
//
//					} else {
//						mActivity.runOnUiThread(new Runnable() {
//
//							@Override
//							public void run() {
//								photoInfo.getImageView().setImageDrawable(
//										mActivity.getResources().getDrawable(
//												R.drawable.z_error_blank));
//
//							}
//						});
//					}
//				}
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//	};
//
//	public void refresh() {
//		mActivity.runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				MyCollectAdapter.this.notifyDataSetChanged();
//			}
//		});
//	}
//
//	public MyCollectAdapter(Activity activity) {
//
//		mActivity = activity;
//		mInflater = (LayoutInflater) mActivity
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		mGetPhotoThread.start();
//	}
//
//	/**
//	 * @Title: setItems
//	 * @Description: 设置数据
//	 * 
//	 * @param items
//	 */
//	public void setItems(List<CollectionList> items) {
//		synchronized (items) {
//			this.items = items;
//		}
//		refresh();
//	}
//
//	/**
//	 * @Title: addItem
//	 * @Description: 添加单个数据
//	 * 
//	 * @param items
//	 */
//	public void addItem(CollectionList item) {
//		synchronized (items) {
//			this.items.add(item);
//		}
//		refresh();
//	}
//
//	/**
//	 * @Title: addItems
//	 * @Description: 添加数据
//	 * 
//	 * @param items
//	 */
//	public void addItems(List<CollectionList> items) {
//		synchronized (items) {
//			this.items.addAll(items);
//		}
//		refresh();
//	}
//
//	/*
//	 * (非 Javadoc)
//	 * 
//	 * @Title: getCount
//	 * 
//	 * @Description: 获取列表中文件个数
//	 * 
//	 * @return
//	 * 
//	 * @see android.widget.Adapter#getCount()
//	 */
//	public int getCount() {
//		return items.size();
//	}
//
//	/*
//	 * (非 Javadoc)
//	 * 
//	 * @Title: getItem
//	 * 
//	 * @Description: 获得特定位置的Item
//	 * 
//	 * @param position
//	 * 
//	 * @return
//	 * 
//	 * @see android.widget.Adapter#getItem(int)
//	 */
//	public CollectionList getItem(int position) {
//		return items.get(position);
//	}
//
//	/*
//	 * (非 Javadoc)
//	 * 
//	 * @Title: getItemId
//	 * 
//	 * @Description: 获取特定位置Item的ID
//	 * 
//	 * @param position
//	 * 
//	 * @return
//	 * 
//	 * @see android.widget.Adapter#getItemId(int)
//	 */
//	public long getItemId(int position) {
//		return position;
//	}
//
//	/**
//	 * @ClassName: ViewHolder
//	 * @Description:Item的视图结构
//	 * 
//	 * @author wonders
//	 * @date 2011-12-20 上午11:13:12
//	 * 
//	 */
//	static class ViewHolder {
//		// ImageView iconImageView;
//		AlwaysMarQueeTextView titleTextView;
//		TextView detailTextView;
//		ImageView rightIconImageView, iconImageView;
//	}
//
//	/*
//	 * (非 Javadoc)
//	 * 
//	 * @Title: getView
//	 * 
//	 * @Description: 获取视图
//	 * 
//	 * @param position
//	 * 
//	 * @param convertView
//	 * 
//	 * @param parent
//	 * 
//	 * @return
//	 * 
//	 * @see android.widget.Adapter#getView(int, android.view.View,
//	 * android.view.ViewGroup)
//	 */
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View view = convertView;
//		ViewHolder holder = null;
//		if (view == null) {
//			holder = new ViewHolder();
//			// view = mInflater.inflate(R.layout.item_pointed_video, null);
//			view = mInflater.inflate(R.layout.item_pointvideo, null);
//			holder.iconImageView = (ImageView) view
//					.findViewById(R.id.icon4PointVideoItem);
//			holder.titleTextView = (AlwaysMarQueeTextView) view
//					.findViewById(R.id.title4PointVideoItem);
//			holder.detailTextView = (TextView) view
//					.findViewById(R.id.detail4PointVideoItem);
//			holder.iconImageView.setVisibility(View.GONE);
//			view.setTag(holder);
//		} else {
//			holder = (ViewHolder) view.getTag();
//		}
//		CollectionList info = null;
//		synchronized (items) {
//			info = items.get(position);
//		}
//		if (info != null) {
//			// holder.iconImageView.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.z_trans_blank));
//			holder.titleTextView.setText(info.getTitle());
//			holder.detailTextView.setText(info.getDetail());
//			// asyncLoadImage(info.getImg(), holder.iconImageView);
//		} else {
//
//		}
//		return view;
//	}
//}