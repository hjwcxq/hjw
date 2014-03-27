package com.orientmedia.app.cfddj.module;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.base.AbstractModule;
import com.tv189.dzlc.adapter.po.base.ItemNode;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.VideoChatInfo;
import com.tv189.dzlc.adapter.service.impl.ExpertsServiceImpl;

public class NavModule extends AbstractModule {

	private LinearLayout product_container;

	@Override
	public View getView(Context mContext) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.module_product, null);
		initView(mContext, view);
		return view;
	}

	private List<ItemNode> navs = null;
	
	public void initView(final Context cont, View view) {
		navs = this.getItems();
		product_container = (LinearLayout) view
				.findViewById(R.id.product_container);
		
		if(navs != null && !navs.isEmpty()){
			for (int i =0 ;i<navs.size(); i++) {
				product_container.addView(getNavItem(cont, navs.get(i)),i);
			}
		}
		cont.registerReceiver(new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				product_container.removeViewAt(0);
				product_container.addView(getNavItem(cont, navs.get(0)),0);
			}
		}, new IntentFilter("RED_ICON_ZJYDY")) ;
	}

	/**
	 * 功能按钮的跳转
	 * 
	 * @param root
	 * @param moduleItem
	 * @return
	 */
	private ImageView redIcon ;
	int count = 0 ;
	
	public View getNavItem(final Context mContext, final ItemNode item) {
		FrameLayout itemV = (FrameLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.item_product, null);
		TextView pName = (TextView) itemV.findViewById(R.id.product_name);
		ImageView pImg = (ImageView) itemV.findViewById(R.id.img_product);
		
		redIcon = (ImageView) itemV.findViewById(R.id.red_icon);
		itemV.setBackgroundResource(R.color.color_white);
		pName.setText(item.getTitle().getText());
		if ("zjydy".equalsIgnoreCase(item.getAction().getUrl())) {
			pImg.setImageResource(R.drawable.icon_expert);
			if(count == 0){
				new GetExpertRepertChatInfoTask(mContext, redIcon).execute("");
				count ++ ;
			}
		} else if ("video".equalsIgnoreCase(item.getAction().getUrl())) {
			pImg.setImageResource(R.drawable.icon_getmoney);
		} else if ("djsj".equalsIgnoreCase(item.getAction().getUrl())) {
			pImg.setImageResource(R.drawable.icon_private);
		} else if ("yjxg".equalsIgnoreCase(item.getAction().getUrl())) {
			pImg.setImageResource(R.drawable.icon_newstock);
		}

		itemV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("zjydy".equalsIgnoreCase(item.getAction().getUrl())) {
					mContext.sendBroadcast(new Intent("RED_ICON_ZJYDY"));
				}
				item.getAction().jumpByActionType(item.getAction().getParaMap());
			}
		});
		return itemV;
	}
	
	private List<VideoChatInfo> chatInfos = null ;
	
	class GetExpertRepertChatInfoTask extends AsyncTask<String, Void, Boolean>{
		private Context context ;
		
		private ImageView redIcon ;
		
		public GetExpertRepertChatInfoTask(Context context,ImageView redIcon){
			this.context = context ;
			this.redIcon = redIcon ;
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			boolean flag = false;
			ExpertsServiceImpl expertService = new ExpertsServiceImpl(context);
			try {
				chatInfos = expertService.expertRepeatChatInfos(null);
				if(chatInfos != null && !chatInfos.isEmpty())
					flag = true ;
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
			}
			return Boolean.valueOf(flag);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.booleanValue()){
				redIcon.setVisibility(View.VISIBLE);
			}else{
				redIcon.setVisibility(View.GONE);
			}
		}
		
	}

}
