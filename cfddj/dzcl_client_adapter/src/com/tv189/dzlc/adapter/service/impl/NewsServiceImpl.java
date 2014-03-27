/***
* class:NewsServiceImpl.java
* create:cavenshen
* time:6:54:18 PM
*
*/


package com.tv189.dzlc.adapter.service.impl;

import java.util.HashMap;
import java.util.Map;

import mobi.dreambox.frameowrk.core.util.StringUtil;

import android.content.Context;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.news.LiveRollingInfo;
import com.tv189.dzlc.adapter.po.news.NewsInfo;
import com.tv189.dzlc.adapter.po.news.WelcomePageInfo;
import com.tv189.dzlc.adapter.service.abstractclass.AbstractDzlcService;
import com.tv189.dzlc.adapter.service.inerface.NewsService;

/**
 * @author cavenshen
 *
 */
public class NewsServiceImpl extends AbstractDzlcService implements NewsService {
	public NewsServiceImpl(Context context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.tv189.gplz.adapter.service.inerface.NewsService#getNews(int)
	 */
	@Override
	public NewsInfo getNews(int id) throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true);  
		headerMap.put("id", StringUtil.toString(id));
		NewsInfoResponse apiResp = apiResp=(NewsInfoResponse)callPostApi(DzlcAndroidConfig.API_NEWS_INFO, headerMap,NewsInfoResponse.class);
		if(apiResp!=null&&apiResp.isSuccess())
			return apiResp.getData();
		else
			return null;
	}
	
	class NewsInfoResponse extends AbstractApiResponse{
		private NewsInfo data;
		/**
		 * @return the data
		 */
		public NewsInfo getData() {
			return data;
		}
		/**
		 * @param data the data to set
		 */
		public void setData(NewsInfo data) {
			this.data = data;
		}
	}
	public ApiResponse regPushClient(String nickName,String deviceId,String deviceType,String phonenumm,String channelid,String getuiDeviceId) throws ServiceException
	{
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(false);
		 headerMap.put("deviceId", deviceId);
		 headerMap.put("deviceType", deviceType);
		 headerMap.put("phonenumm", phonenumm);
		 headerMap.put("nickName", nickName);
		 headerMap.put("getuiDeviceId", getuiDeviceId);
		 headerMap.put("channelid", channelid); 
		 headerMap.put("clientId", channelid); 
		 /*headerMap.put("imsiId", channelid); */
		 Object respAccountObj = null;
		 try {
			respAccountObj=(ApiResponse) callPostApi(DzlcAndroidConfig.URL_REGPUSHCLIENT, headerMap, ApiResponse.class);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		 if(respAccountObj!=null){
			return (ApiResponse) respAccountObj;
		 }
		return null;
	}

	@Override
	public LiveRollingInfo getRollingTitleInfo() throws ServiceException{
		// TODO Auto-generated method stub
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("channelid", "001");//直播的频道ID
		LiveRollingInfoResponse respAccountObj = null;
		 try {
			respAccountObj=(LiveRollingInfoResponse) callPostApi(DzlcAndroidConfig.FOCUS_LIVEROLING_TITLE_, headerMap, LiveRollingInfoResponse.class);
		} catch (ServiceException e) {
			throw e ;
		}
		 if(respAccountObj!=null){
			return respAccountObj.getData() ;
		 }
		return null;
	}
	
	class LiveRollingInfoResponse extends AbstractApiResponse {
		
		private LiveRollingInfo data;
		/**
		 * @return the data
		 */
		public LiveRollingInfo getData() {
			return data;
		}
		/**
		 * @param data the data to set
		 */
		public void setData(LiveRollingInfo data) {
			this.data = data;
		}
	}
	@Override
	public WelcomePageInfo getWelcomePageInfo() throws ServiceException {
		// TODO Auto-generated method stub
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("channelid", "001");//直播的频道ID
		headerMap.put("logo", "01");  //发布状态   01 已经发布
		headerMap.put("pagenum", "0");  //
		headerMap.put("pagesize", "200");  //
		
		WelcomePageInfoResponse respAccountObj = null;
		 try {
			respAccountObj=(WelcomePageInfoResponse) callPostApi(DzlcAndroidConfig.WELCOME_API_URL, headerMap, WelcomePageInfoResponse.class);
		} catch (ServiceException e) {
			throw e ;
		}
		 if(respAccountObj!=null){
			return respAccountObj.getData() ;
		 }
		return null;
	}
	
	class WelcomePageInfoResponse extends AbstractApiResponse {
		
		private WelcomePageInfo data;
		/**
		 * @return the data
		 */
		public WelcomePageInfo getData() {
			return data;
		}
		/**
		 * @param data the data to set
		 */
		public void setData(WelcomePageInfo data) {
			this.data = data;
		}
	}
}
