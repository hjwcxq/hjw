package com.tv189.dzlc.adapter.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import mobi.dreambox.frameowrk.core.constant.ErrorCodeConstants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tv189.dzlc.adapter.exception.AndroidClientBaseException;
import com.tv189.dzlc.adapter.exception.ErrorCodeConst;
import com.tv189.dzlc.adapter.po.base.ItemNode;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.common.TokenException;

public class Utils {
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String Date2String(Date date) {
		return sdf.format(date);
	}

	public static boolean checkNetWork(Context context) {
		if (!NetworkUtils.isNetworkAvailable(context)) {
			return false;
		}
		return true;
	}

	/**
	 * @param number
	 * @return
	 */
	public static boolean isTelecomByNumber(String number) {

		List<String> list = Arrays.asList(new String[] { "189", "180", "181",
				"153", "133" });
		if (number != null && number.length() > 4) {
			return list.contains(number.substring(0, 3));
		}
		return false;
	}

	public static int setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			ItemNode node = (ItemNode) listAdapter.getItem(i);
//			if (node.getBackimg() != null && node.getBackimg().isShow()) {
				totalHeight += Utils.dip2px(listView.getContext(), 80);
			// } else {
			// View listItem = listAdapter.getView(i, null, listView);
			// listItem.measure(0, 0);
			// totalHeight += listItem.getMeasuredHeight();
			// }

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		return totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getWindowWidth(Activity cont) {
		DisplayMetrics metrics = new DisplayMetrics();
		cont.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;// 屏幕的宽dp
	}

	public static int getWindowHeight(Activity cont) {
		DisplayMetrics metrics = new DisplayMetrics();
		cont.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;// 屏幕的宽dp
	}

	/**
	 * 显示输入法
	 * 
	 * @param cont
	 */
	public static void showSoftInput(Activity cont) {
		cont.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}

	/**
	 * 隐藏输入法
	 * 
	 * @param cont
	 */
	public static void hideSoftInput(Activity cont) {
		cont.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
						| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	public static void showCusAlertDialog(Context activity, int title_id,
			int message_id, String negativeBtn, String positiveBtn,
			DialogInterface.OnClickListener negativeListener,
			DialogInterface.OnClickListener positiveListener) {
		AlertDialog mAlertDialog = null;
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			if (positiveBtn == null) {
				mAlertDialog = new AlertDialog.Builder(activity)
						.setTitle(title_id).setMessage(message_id)
						.setNegativeButton(negativeBtn, negativeListener)
						.create();
			} else if (negativeBtn == null) {
				mAlertDialog = new AlertDialog.Builder(activity)
						.setTitle(title_id).setMessage(message_id)
						.setPositiveButton(positiveBtn, positiveListener)
						.create();
			} else {
				mAlertDialog = new AlertDialog.Builder(activity)
						.setTitle(title_id).setMessage(message_id)
						.setPositiveButton(positiveBtn, positiveListener)
						.setNegativeButton(negativeBtn, negativeListener)
						.create();
			}
		} else {
			if (positiveBtn == null) {
				mAlertDialog = new AlertDialog.Builder(activity)
						.setTitle(title_id).setMessage(message_id)
						.setNegativeButton(negativeBtn, negativeListener)
						.create();
			} else if (negativeBtn == null) {
				mAlertDialog = new AlertDialog.Builder(activity)
						.setTitle(title_id).setMessage(message_id)
						.setPositiveButton(positiveBtn, positiveListener)
						.create();
			} else {
				mAlertDialog = new AlertDialog.Builder(activity)
						.setTitle(title_id).setMessage(message_id)
						.setPositiveButton(positiveBtn, positiveListener)
						.setNegativeButton(negativeBtn, negativeListener)
						.create();
			}
		}
		if (mAlertDialog.isShowing())
			mAlertDialog.dismiss();
		else
			mAlertDialog.show();
	}

	public static String getCurrentVersion(Context cont) {
		String versionName = null;
		PackageManager pm = cont.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(cont.getPackageName(), 0);
			if (packageInfo != null)
				versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public static String getImsiid(Context cont) {
		TelephonyManager tphoneMamager = (TelephonyManager) cont
				.getSystemService(Context.TELEPHONY_SERVICE);

		return tphoneMamager.getSubscriberId() != null ? tphoneMamager
				.getSubscriberId() : "88888888888";
	}

	public static String getDeviceid(Context cont) {
		TelephonyManager tphoneMamager = (TelephonyManager) cont
				.getSystemService(Context.TELEPHONY_SERVICE);

		return tphoneMamager.getDeviceId() != null ? tphoneMamager
				.getDeviceId() : "88888888888";
	}

	public static String getWindowWH(Context cont) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) cont).getWindowManager().getDefaultDisplay()
				.getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		int height = metric.heightPixels; // 屏幕高度（像素）
		return width + "*" + height;
	}

	public static String getVersion(Context cont) {
		PackageManager packageManager = cont.getPackageManager();
		try {
			PackageInfo info = packageManager.getPackageInfo(
					cont.getPackageName(), 0);
			return info != null ? info.versionName : "";
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	// 访问类型(1：WAP，2：客户端，9：富媒体客户端 10：客户端4.0)
	public static String getNetworkType(Context cont) {
		ConnectivityManager cm = (ConnectivityManager) cont
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isAvailable() ? ni.getTypeName() : "NULL";
	}

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {

		}
		return "";
	}

	public static String getAPNType(Context cont) {
		String netType = "NONE";
		ConnectivityManager connMgr = (ConnectivityManager) cont
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null) {
			int nType = networkInfo.getType();
			if (nType == ConnectivityManager.TYPE_MOBILE) {
				if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
					netType = "CMNET";
				} else {
					netType = "CMWAP";
				}
			} else if (nType == ConnectivityManager.TYPE_WIFI) {
				netType = "WIFI";
			}
		}
		return netType;
	}

	public static boolean isSimAvailable(Context context) {
		TelephonyManager mTelephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		int simState = mTelephonyManager.getSimState();
		if (simState == TelephonyManager.SIM_STATE_READY) {
			return true;
		}
		return false;
	}

	public static String getMD5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			// System.out.println("result: " + buf.toString());//32位的加密

			// System.out.println("result: " +
			// buf.toString().substring(8,24));//16位的加密
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isAppExits(Context cont, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;

		final PackageManager packageManager = cont.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(packageName);
	}

	public static String toDbc(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

}
