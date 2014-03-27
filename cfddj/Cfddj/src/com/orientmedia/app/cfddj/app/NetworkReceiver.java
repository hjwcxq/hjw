///*******************************************************************************
// * Copyright (c) 2012 IBM Corporation and others.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// *
// * Contributors:
// *     IBM Corporation - initial API and implementation
// *******************************************************************************/
//package tv.aniu.app.dzlc.app;
//
//import java.util.ArrayList;
//
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//
//public class NetworkReceiver extends BroadcastReceiver {
//
//	protected Context mContext;
//	public void onReceive(Context context, Intent intent) {
//		mContext = context;
//		boolean isBreak = intent.getBooleanExtra(
//				ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
//		Bundle bundle = new Bundle();
//		if (isBreak) {
//			bundle.putBoolean("isconnect", false);
//			mContext.stopService(intent);
//		} 
//		if(!isNetworkConnected(context)){
//			MyApplication.getInstance().netStatus=false;
//			notifyActivity();
//		}else{
//			if(!MyApplication.getInstance().netStatus){//网络恢复
//				MyApplication.getInstance().netStatus=true;
//				notifyActivity();
//			}
//		}
//	}
//	
//	/*****
//	 * 判断网络是否连接
//	 * 
//	 * @param context
//	 * @return
//	 */
//	public boolean isNetworkConnected(Context context) {
//		if (context != null) {
//			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//					.getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo mNetworkInfo = mConnectivityManager
//					.getActiveNetworkInfo();
//			if (mNetworkInfo != null) {
//				return mNetworkInfo.isAvailable();
//			}
//		}
//		return false;
//	}
//	
//	private void notifyActivity(){
//		ArrayList<EventHandler> ehList=MyApplication.getInstance().ehList;
//		if(ehList!=null&&ehList.size()>0){
//			for (int i = 0; i < ehList.size(); i++)
//				((EventHandler) ehList.get(i)).netInfo(MyApplication.getInstance().netStatus);
//		}
//	}
//}
