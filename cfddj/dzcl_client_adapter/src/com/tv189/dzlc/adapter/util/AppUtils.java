package com.tv189.dzlc.adapter.util;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;

/*
 * 应用相关方法
 * @author chuanglong 
 */
public class AppUtils {

	/*
	 * 桌面是否存在本应用的快捷方式ShortCut
	 * @return true:存在，false：不存在
	 */
	public static boolean isExistShortCut(Context cx)
	{ 
	        boolean result = false;
	        // 获取当前应用名称
	        String title = null;
	        try {
	            final PackageManager pm = cx.getPackageManager();
	            title = pm.getApplicationLabel(
	                    pm.getApplicationInfo(cx.getPackageName(),
	                            PackageManager.GET_META_DATA)).toString();
	        } catch (Exception e) {
	        }

	        final String uriStr;
	        if (android.os.Build.VERSION.SDK_INT < 8) {
	            uriStr = "content://com.android.launcher.settings/favorites?notify=true";
	        } else {
	            uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
	        }
	        final Uri CONTENT_URI = Uri.parse(uriStr);
	        final Cursor c = cx.getContentResolver().query(CONTENT_URI, null,
	                "title=?", new String[] { title }, null);
	        if (c != null && c.getCount() > 0) {
	            result = true;
	        }
	        return result;
		
	}
	
	/*
	 * 增加本应用的快捷方式到桌面
	 */
	public static void addShortCut(Context cx ,int resourceId)
	{
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

	    Intent shortcutIntent = cx.getPackageManager()
	            .getLaunchIntentForPackage(cx.getPackageName());
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	    // 获取当前应用名称
	    String title = null;
	    try {
	        final PackageManager pm = cx.getPackageManager();
	        title = pm.getApplicationLabel(
	                pm.getApplicationInfo(cx.getPackageName(),
	                        PackageManager.GET_META_DATA)).toString();
	    } catch (Exception e) {
	    }
	    // 快捷方式名称
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
	    // 不允许重复创建（不一定有效）
	    shortcut.putExtra("duplicate", false);
	    // 快捷方式的图标
	    Parcelable iconResource = Intent.ShortcutIconResource.fromContext(cx,
	    		resourceId);
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

	    cx.sendBroadcast(shortcut);
	}
	
	/*
	 * 获得本应用的版本名称
	 */
	public static String getLocalAppVersionName(Context context)
	{
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo;
		String localVersionName = "0.0";//默认0
		try {
			packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			localVersionName = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			LogUtils.error(e.getMessage());
		}
		return localVersionName;
	}
	
	/*
	 * 获得本应用的版本号
	 */
	public static int getLocalAppVersionCode(Context context)
	{
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo;
		int localVersionCode = 0 ;
		try {
			packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			localVersionCode = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			LogUtils.error(e.getMessage());
		}
		return localVersionCode;
	}
	
	
}
