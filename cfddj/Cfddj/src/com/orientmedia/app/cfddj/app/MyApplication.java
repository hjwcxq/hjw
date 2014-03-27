package com.orientmedia.app.cfddj.app;

import java.util.ArrayList;

import android.app.Application;
import android.content.IntentFilter;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tv189.dzlc.adapter.po.order.AlipayOrderDetailInfo;
import com.tv189.dzlc.adapter.po.user.UserAccountInfo;

//检测用户各种信息
public class MyApplication extends Application {

	public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

	public static MyApplication application;

	private static final String SYNC_TAG = "SYNC_TAG";

	public UserAccountInfo userInfo;

	public AlipayOrderDetailInfo orderDetatilInfo;
	/****
	 * 是否是电信用户
	 */
	public boolean isChinaTelcomUser = false;
	/****
	 * 是否是VIP用户
	 */
	public boolean netStatus = true;

	/****
	 * 状态栏高度
	 */
	public static int statusBarHeight = 25;

	/****
	 * 是否更新版本
	 */
	public boolean isUpdate = false;

	@Override
	public void onCreate() {
		super.onCreate();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();

		ImageLoader.getInstance().init(config);
		application = this;
		// CrashHandler crashHandler = CrashHandler.getInstance();
		// crashHandler.init(this);
	}

	public static MyApplication getInstance() {
		synchronized (SYNC_TAG) {
			if (null == application) {
				application = new MyApplication();
			}
			return application;
		}
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
