/***
 * class:SystemContext.java
 * create:cavenshen
 * time:3:38:54 PM
 *
 */

package com.tv189.dzlc.adapter.context;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.tv189.dzlc.adapter.dao.DatabaseHelper;
import com.tv189.dzlc.adapter.po.base.AbstractModule;
import com.tv189.dzlc.adapter.po.sqlpo.ExpertsInfo;

/**
 * @author cavenshen
 * 
 */
public class SystemContext {

	private static SystemContext instance;

	private static final String SYNC_KEY = "SYNC_KEY";

	private Context context;

	private ExpertsInfo currentChatExpert;

	private boolean isInit = false;
	
//	private String ua = null;
//	
//	private AppSetting setting ;
	
	int retainCount=0;
	
	private DatabaseHelper databaseHelper;
	
	private List<AbstractModule> modules = new ArrayList<AbstractModule>();

	public List<AbstractModule> getModules() {
		return modules;
	}

	public void setModules(List<AbstractModule> modules) {
		this.modules = modules;
	}

	/**
	 * @return the isInit
	 */
	public boolean isInit() {
		return isInit;
	}

	/**
	 * @param isInit
	 *            the isInit to set
	 */
	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

	private SystemContext(Context context){
		this.context = context ;
	}
	
	public static SystemContext getInstance(Context context) {
		synchronized (SYNC_KEY) {
			if (instance == null)
				instance = new SystemContext(context);
			else
				instance.context = context;
			
			return instance;
		}
	}
	
	/**
	 * 打开数据库
	 */
	public void openDB(){
		synchronized (SYNC_KEY) {
			if(retainCount==0 || databaseHelper==null){
				databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
				
			}
			retainCount++;
		}
	}
	/**
	 * 关闭数据库
	 */
	public void closeDB(){
		synchronized (SYNC_KEY) {
			if(retainCount>0){
				retainCount--;
			}else if(retainCount==0){
				if(databaseHelper!=null){
					OpenHelperManager.releaseHelper();
					databaseHelper = null;
				}
			}
		}
	}
	
	public DatabaseHelper getDatabaseHelper(){
		return databaseHelper;
	}
	
	

	public ExpertsInfo getCurrentExpert() {
		// TODO Auto-generated method stub
		return currentChatExpert;
	}

	public void setCurrentExpert(ExpertsInfo experInfo) {
		this.currentChatExpert = experInfo;
	}


	public static String recode_defult = "901001";
	public static String recode_submit = "901002";
	public static String recode_goto_voice = "901003";
	public static String recode_onebyone = "901004";
	public static String recode_addstock = "901005";
	public static String recode_searchstock= "901006";
	public static String recode_pointvideolist= "901007";
	public static String recode_somedayvideo= "901008";

	public static String rething_defult = "其他";
	public static String rething_submit = "发送文字";
	public static String rething_goto_voice = "发送语音";
	public static String rething_onebyone = "专家一对一咨询";
	public static String rething_addstock = "添加自选股";
	public static String rething_searchstock = "查询股票信息";
	public static String rething_pointvideolist = "点播视频";
	public static String rething_somedayvideo = "回看视频";

	public static String something = null;
	public static String somecode = null;
	
	public static String last_refresh_data ="";//提问列表最后刷新时间
}
