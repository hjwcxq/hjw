package com.orientmedia.app.cfddj.adapter;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.tool.imageutils.ImageFetcher;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.version.ApplicationInfo;
import com.tv189.dzlc.adapter.util.Utils;

public class AppRecommendAdapter extends BaseAdapter {

	public OnInstallListener listener;

	private List<ApplicationInfo> items = new ArrayList<ApplicationInfo>();

	private Context cont;

	private ImageFetcher mImageFetcher = null;

	public AppRecommendAdapter(Context cont, List<ApplicationInfo> list) {
		this.cont = cont;
		mImageFetcher = new ImageFetcher(cont);
		if (list != null)
			items.addAll(list);
	}

	public void setItems(List<ApplicationInfo> list) {
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
	public ApplicationInfo getItem(int position) {
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
		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(cont).inflate(
					R.layout.item_app_recommend, null);
			if (holder == null) {
				holder = new ViewHolder();
				holder.app_icon = (ImageView) view.findViewById(R.id.app_icon);
				holder.app_new_flag = (ImageView) view
						.findViewById(R.id.app_new_flag);
				holder.app_name = (TextView) view.findViewById(R.id.app_name);
				holder.app_size = (TextView) view.findViewById(R.id.app_size);
				holder.anzhuang = (LinearLayout) view
						.findViewById(R.id.ll_anzhuang);
				holder.anzhuangIcon = (ImageView) view
						.findViewById(R.id.anzhuang_icon);
				holder.anzhuangText = (TextView) view
						.findViewById(R.id.anzhuang_text);
				view.setTag(holder);
			}
		} else {
			holder = (ViewHolder) view.getTag();
		}
		final ApplicationInfo appInfo = items.get(position);
		
		if (appInfo != null) {
			if (appInfo.getApplicationapkurl() != null)
				mImageFetcher.loadImage(appInfo.getApplicationapkurl(),
						holder.app_icon);
			holder.app_name.setText(appInfo.getApplicationname());
			holder.app_size.setText(appInfo.getApplicationsize());
			if(Utils.isAppExits(cont,appInfo.getPackagename())){
				holder.anzhuangIcon.setBackgroundResource(R.drawable.open_app);
				holder.anzhuangText.setText("打开");
			}else{
				holder.anzhuangIcon.setBackgroundResource(R.drawable.down_app);
				holder.anzhuangText.setText("下载");
			}
			
			holder.anzhuang.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					listener.install(appInfo);
				}
			});
		}

		return view;
	}

	class ViewHolder {
		ImageView app_icon;
		ImageView app_new_flag;
		TextView app_name;
		TextView app_size;
		ImageView anzhuangIcon ;
		TextView anzhuangText;
		LinearLayout anzhuang;
	}

	public interface OnInstallListener {
		void install(ApplicationInfo info);
	}

	

	// 已安装，打开程序，需传入参数包名："com.skype.android.verizon"

	public void intentTo(String packageName) {
		if (Utils.isAppExits(cont,packageName)) {
			Intent i = new Intent();
			ComponentName cn = new ComponentName("com.skype.android.verizon",
					"com.skype.android.verizon.SkypeActivity");
			i.setComponent(cn);
			cont.startActivity(i);
		}else {
		}

	}
}
