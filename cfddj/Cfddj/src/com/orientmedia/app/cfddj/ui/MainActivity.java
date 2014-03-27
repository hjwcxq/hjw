package com.orientmedia.app.cfddj.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.adapter.MyViewPagerFragmentAdapter;
import com.orientmedia.app.cfddj.service.UpdateService;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.ui.fragment.DiscussFragment;
import com.orientmedia.app.cfddj.ui.fragment.FindFragment;
import com.orientmedia.app.cfddj.ui.fragment.IndexFragment;
import com.orientmedia.app.cfddj.ui.fragment.MyFragment;
import com.orientmedia.base.BaseActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.version.VersionInfo;
import com.tv189.dzlc.adapter.service.impl.VersionServiceImpl;
import com.tv189.dzlc.adapter.service.inerface.VersionService;
import com.tv189.dzlc.adapter.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

@SuppressLint({ "NewApi", "ResourceAsColor" })
public class MainActivity extends BaseActivity implements
		ShowLoadingTipListener {

	public static final String INDEX = "INDEX";

	public static final String LIVING = "LIVING";

	public static final String FIND = "FIND";

	public static final String MY = "MY";

	TabHost mTabHost;

	TabManager mTabManager;

	TabWidget mTabWidger;

	private Map<String, View> tabWidgetViews = new HashMap<String, View>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UmengUpdateAgent.update(this);   //hjw add umeng 
		//MobclickAgent.openActivityDurationTrack(false);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setContentView(R.layout.activity_tab);

		View indexTab = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, null);
		TextView text0 = (TextView) indexTab.findViewById(R.id.title);
		ImageView image0 = (ImageView) indexTab.findViewById(R.id.icon);
		text0.setText("主页");
		image0.setImageDrawable(getResources().getDrawable(
				R.drawable.tab_index_style));
		tabWidgetViews.put(INDEX, indexTab);

		View liveTab = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, null);
		TextView text3 = (TextView) liveTab.findViewById(R.id.title);
		ImageView image3 = (ImageView) liveTab.findViewById(R.id.icon);
		text3.setText("直播");
		image3.setImageDrawable(getResources().getDrawable(
				R.drawable.tab_living_style));
		tabWidgetViews.put(LIVING, liveTab);

		View findTab = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, null);
		TextView text1 = (TextView) findTab.findViewById(R.id.title);
		ImageView image1 = (ImageView) findTab.findViewById(R.id.icon);
		text1.setText("聚焦");
		image1.setImageDrawable(getResources().getDrawable(
				R.drawable.tab_find_style));
		tabWidgetViews.put(FIND, findTab);

		View myTab = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, null);
		TextView text2 = (TextView) myTab.findViewById(R.id.title);
		ImageView image2 = (ImageView) myTab.findViewById(R.id.icon);
		text2.setText("我");
		image2.setImageResource(R.drawable.tab_my_style);
		tabWidgetViews.put(MY, myTab);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabWidger = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost.setup();
		mTabManager = new TabManager(this, mTabHost, R.id.frag_container);
		mTabManager.addTab(mTabHost.newTabSpec(INDEX).setIndicator(indexTab),
				IndexFragment.class, null);
		mTabManager.addTab(mTabHost.newTabSpec(LIVING).setIndicator(liveTab),
				null, null);
		mTabManager.addTab(mTabHost.newTabSpec(FIND).setIndicator(findTab),
				null, null);
		mTabManager.addTab(mTabHost.newTabSpec(MY).setIndicator(myTab),
				MyFragment.class, null);

		//checkVersion();   //hjw del 

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);    
		mTabHost.getTabWidget().setVisibility(View.VISIBLE);
		if (currentTag == null || LIVING.equalsIgnoreCase(currentTag))
			mTabHost.setCurrentTabByTag(INDEX);
		else if (currentTag == null || FIND.equalsIgnoreCase(currentTag))
			mTabHost.setCurrentTabByTag(INDEX);
		else
			mTabHost.setCurrentTabByTag(currentTag);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//MobclickAgent.onPageEnd("livingScreen"); //hjw add for umeng
	    MobclickAgent.onPause(this); 
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Utils.showCusAlertDialog(MainActivity.this, R.string.prompt_info,
					R.string.sure_quit, "取消", "确定", null, backPosListener);

			break;
		case KeyEvent.KEYCODE_HOME:
			break;
		default:
			break;

		}
		return super.onKeyDown(keyCode, event);
	}

	private DialogInterface.OnClickListener backPosListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			finishAll();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.exit(0);
				}
			}).start();
		}
	};

	//hjw del
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case android.R.id.home:
			return true;
		case R.id.progream_item:
			Intent itP = new Intent(MainActivity.this, ProgramActivity.class);
			startActivity(itP);
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}*/

	private View currentView = null;

	private String currentTag = null;

	/**
	 * This is a helper class that implements a generic mechanism for
	 * associating fragments with the tabs in a tab host. It relies on a trick.
	 * Normally a tab host has a simple API for supplying a View or Intent that
	 * each tab will show. This is not sufficient for switching between
	 * fragments. So instead we make the content part of the tab host 0dp high
	 * (it is not shown) and the TabManager supplies its own dummy view to show
	 * as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct fragment shown in a separate content area whenever
	 * the selected tab changes.
	 */
	public class TabManager implements TabHost.OnTabChangeListener {
		private final FragmentActivity mActivity;
		private final TabHost mTabHost;
		private final int mContainerId;
		private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
		TabInfo mLastTab;

		class TabInfo {
			private String tag;
			private Class<?> clss;
			private Bundle args;
			private Fragment fragment;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabManager(FragmentActivity activity, TabHost tabHost,
				int containerId) {
			mActivity = activity;
			mTabHost = tabHost;
			mContainerId = containerId;
			mTabHost.setOnTabChangedListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mActivity));

			String tag = tabSpec.getTag();
			// Check to see if we already have a fragment for this tab, probably
			// from a previously saved state. If so, deactivate it, because our
			// initial state is that a tab isn't shown.
			TabInfo info = new TabInfo(tag, clss, args);
			if (!LIVING.equals(tag)&&!FIND.equals(tag)) {
				info.fragment = mActivity.getSupportFragmentManager()
						.findFragmentByTag(tag);
				mTabs.put(tag, info);
			}
			mTabHost.addTab(tabSpec);
		}

		@Override
		public void onTabChanged(String tabId) {

			initWidgetBg(tabId, tabWidgetViews);

			if (tabId.equals(LIVING)) {
				mTabHost.getTabWidget().setVisibility(View.GONE);
				intentTo(MainActivity.this, LivingActivity.class);
			}else if (tabId.equals(FIND)) {
				mTabHost.getTabWidget().setVisibility(View.GONE);
				intentTo(MainActivity.this, DiscussActivity.class);
			}else {
				currentTag = tabId;
				TabInfo newTab = mTabs.get(tabId);
				if (mLastTab != newTab) {
					FragmentTransaction ft = mActivity
							.getSupportFragmentManager().beginTransaction();
					if (mLastTab != null) {
						if (mLastTab.fragment != null) {
							ft.hide(mLastTab.fragment);
						}
					}
					if (newTab != null) {
						if (newTab.fragment == null) {
							newTab.fragment = Fragment.instantiate(mActivity,
									newTab.clss.getName(), newTab.args);
							ft.add(mContainerId, newTab.fragment, newTab.tag);
						} else {
							ft.show(newTab.fragment);
						}
					}
					mLastTab = newTab;
					ft.commit();
				}
			}

		}
	}

	private TextView widgetTitle;
	private ImageView widgetIcon;

	public void initWidgetBg(String currentkey, Map<String, View> tabMap) {
		if (!tabMap.isEmpty()) {
			Set<String> keys = tabMap.keySet();
			for (String string : keys) {
				if (string.equalsIgnoreCase(currentkey)) {
					View current = tabMap.get(string);
					widgetTitle = (TextView) current.findViewById(R.id.title);
					widgetTitle.setTextColor(0xff990000);
				} else {
					View current = tabMap.get(string);
					widgetTitle = (TextView) current.findViewById(R.id.title);
					widgetTitle.setTextColor(Color.BLACK);
				}
			}
		}
	}

	public void checkVersion() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				PackageInfo packageInfo = null;

				PackageManager packageManager;
				String versionName = "1.0";// 默认0

				packageManager = getPackageManager();
				try {
					packageInfo = packageManager.getPackageInfo(
							getPackageName(), 0);
					versionName = packageInfo.versionName;
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				VersionService version = new VersionServiceImpl(
						MainActivity.this);
				try {

					final VersionInfo versionInfo = version
							.updateVersion(versionName);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// 增加比较当前软件版本是否比服务器上返回的版本新，如果新的话，就不升级
							if (versionInfo != null
									&& versionInfo.getId() != null
									&& versionInfo.getVersion() != null)

							{
								AlertDialog.Builder alBuilder = null;
								try {
									alBuilder = new AlertDialog.Builder(
											MainActivity.this);
									alBuilder
											.setCancelable(false)
											.setIcon(
													android.R.drawable.ic_dialog_alert)
											.setTitle("发现新版本")
											.setMessage(
													"发现新版本"
															+ MainActivity.this
																	.getResources()
																	.getString(
																			R.string.app_name)
															+ "，版本号 "
															+ versionInfo
																	.getVersion()
															+ " ，是否立即更新？"
															+ "\n\n"
															+ "更新内容："
															+ "\n\n"
															+ versionInfo
																	.getAppContent());
								} catch (Exception e) {
								}
								if (versionInfo.getUpgradFlag() != null
										&& versionInfo.getUpgradFlag()
												.equalsIgnoreCase("1")) {
									alBuilder.setCancelable(true);
									alBuilder
											.setOnCancelListener(new DialogInterface.OnCancelListener() {

												@Override
												public void onCancel(
														DialogInterface dialog) {
													(MainActivity.this)
															.onBackPressed();
												}
											});
								} else {
									alBuilder
											.setNegativeButton(
													"下次再说",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {

														}

													});
								}
								alBuilder.setNeutralButton("立即更新",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												UpdateService.INSTALL_APK_URL = versionInfo
														.getAppPath();
												Log.e("---getAppPath---",
														"---"
																+ versionInfo
																		.getAppPath());
												MainActivity.this
														.startService(new Intent(
																MainActivity.this,
																UpdateService.class));
												dialog.dismiss();

											}
										});

								AlertDialog dialog = alBuilder.create();
								// dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
								dialog.show();
							} else {

							}
						}
					});
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					ExcepUtils.showImpressiveException(MainActivity.this,
							"获取版本号失败", e);
				}

			}
		}).start();

	}

	@Override
	public void onShowLoadingTip(String msg) {
		// TODO Auto-generated method stub
		try {
			showLoadingIcon();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onHideLoadingTip() {
		// TODO Auto-generated method stub
		try {
			hideLoadingIcon();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
