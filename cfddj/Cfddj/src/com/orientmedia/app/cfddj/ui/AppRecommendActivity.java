package com.orientmedia.app.cfddj.ui;

import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.adapter.AppRecommendAdapter;
import com.orientmedia.app.cfddj.adapter.AppRecommendAdapter.OnInstallListener;
import com.orientmedia.app.cfddj.service.UpdateService;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.base.BaseActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.version.ApplicationInfo;
import com.tv189.dzlc.adapter.po.version.ApplicationInfoContent;
import com.tv189.dzlc.adapter.service.impl.VersionServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;
//精品推荐
public class AppRecommendActivity extends BaseActivity {

	private ListView listView;

	private AppRecommendAdapter recAdapter;

	private List<ApplicationInfo> appLists = null;

	private ApplicationInfoContent appContent = null;

	private int pageSize = 20;

	private int pageNumber = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_recommend);
		listView = (ListView) findViewById(R.id.listView);
		recAdapter = new AppRecommendAdapter(this, appLists);
		recAdapter.listener = onInstall;
		listView.setAdapter(recAdapter);
		new GetAppInfoTask().execute("");

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent it = new Intent(AppRecommendActivity.this,
						AppDetailActivity.class);
				ApplicationInfo info = recAdapter.getItem(position);
				if(info  != null){
					it.putExtra("pic1", info.getPicone());
					it.putExtra("pic2", info.getPictwo());
					it.putExtra("pic3", info.getPicthere());
					it.putExtra("pic4", info.getPicfour());
					it.putExtra("pic5", info.getPicfive());
					it.putExtra("appName", info.getApplicationname());
//					it.putExtra("appVersion", info.getInsertdate());
					it.putExtra("appDownloadUrl", info.getApplicationapk());
					it.putExtra("appIconUrl", info.getApplicationapkurl());
					it.putExtra("appSize", info.getApplicationsize());
					it.putExtra("appDesc", info.getApplicationdesc());
					it.putExtra("packageName", info.getPackagename());
					it.putExtra("appId", ""+info.getId());
					startActivity(it);
				}
			}
		});

	}

	private OnInstallListener onInstall = new OnInstallListener() {
		@Override
		public void install(ApplicationInfo info) {
			// TODO Auto-generated method stub
			if(info == null)
				return ;
			if(Utils.isAppExits(AppRecommendActivity.this, info.getPackagename())){
				Intent intent = getPackageManager().getLaunchIntentForPackage(info.getPackagename());
				startActivity(intent);
			}else{
				UpdateService.INSTALL_APK_URL = info.getApplicationapk();
				startService(new Intent(AppRecommendActivity.this,UpdateService.class));
				showCusToast("下载应用中...");
				new StaticsDownCountTask().execute(new String[]{""+info.getId()});
			}
		}
	};

	class GetAppInfoTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showLoadingIcon();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			boolean flag = false;
			VersionServiceImpl versionService = new VersionServiceImpl(
					AppRecommendActivity.this);
			try {
				appContent = versionService.getRecoAppList(
						String.valueOf(pageNumber), String.valueOf(pageSize));
				if (appContent != null) {
					flag = true;
					appLists = appContent.getContent() ;
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(AppRecommendActivity.this, null, e);
			}

			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			hideLoadingIcon();
			if (result.booleanValue()) 
				recAdapter.setItems(appLists);
			else
				showCusToast("加载失败");
		}

	}
	
	
	class StaticsDownCountTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			boolean flag = false;
			VersionServiceImpl versionService = new VersionServiceImpl(
					AppRecommendActivity.this);
			try {
				flag = versionService.statisticsDownloadCount(params[0]) ;
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
}
