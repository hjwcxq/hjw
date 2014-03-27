package com.orientmedia.app.cfddj.tool;

import com.baidu.mobstat.StatService;

import android.content.Context;


public class BaiduStatistics {
	/**
	 * 
	 * @param context
	 * @param appid
	 * @param desc 对该id的描述
	 */
	public static void onMyEvent(Context context,String appid,String desc){
		try {
			StatService.onEvent(context, appid, desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
