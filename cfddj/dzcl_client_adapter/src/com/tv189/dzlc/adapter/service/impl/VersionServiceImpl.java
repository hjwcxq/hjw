package com.tv189.dzlc.adapter.service.impl;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.version.ApplicationInfoContent;
import com.tv189.dzlc.adapter.po.version.VersionInfo;
import com.tv189.dzlc.adapter.service.abstractclass.AbstractDzlcService;
import com.tv189.dzlc.adapter.service.inerface.VersionService;

public class VersionServiceImpl extends AbstractDzlcService implements
		VersionService {

	public VersionServiceImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 版本更新
	 */
	@Override
	public VersionInfo updateVersion(String currentVersion)
			throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(false);
		headerMap.put("pagenumber", "1");
		headerMap.put("pagesize", "100");
		headerMap.put("version", currentVersion);
		VersionInfoResponse respAccountObj = (VersionInfoResponse) callPostApi(
				DzlcAndroidConfig.DZ_APP_INFO, headerMap,
				VersionInfoResponse.class);
		if (respAccountObj != null && respAccountObj.isSuccess()) {
			return respAccountObj.getData();
		} else {
			return null;
		}
	}

	@Override
	public ApplicationInfoContent getRecoAppList(String pagenumber,
			String pagesize) throws ServiceException {
		// TODO Auto-generated method stub
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("pagenumber", pagenumber);
		headerMap.put("pagesize", pagesize);
		headerMap.put("type", "001");

		ApplicationInfoResponse respApp = (ApplicationInfoResponse) callPostApi(
				DzlcAndroidConfig.URL_RECOMMEND_APP, headerMap,
				ApplicationInfoResponse.class);
		if (respApp != null && respApp.isSuccess()) {
			return respApp.getData();
		} else {
			return null;
		}
	}

	@Override
	public boolean statisticsDownloadCount(String id) throws ServiceException {
		// TODO Auto-generated method stub
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("id", id);
		headerMap.put("type", "001");
		ApiResponse resp = (ApiResponse) callPostApi(
				DzlcAndroidConfig.URL_RECOMMEND_APP_DOWNLOAD_CONUT, headerMap,
				ApiResponse.class);
		if (resp != null)
			return resp.isSuccess();
		else
			return false;
	}

	class VersionInfoResponse extends AbstractApiResponse {
		private VersionInfo data;

		public VersionInfo getData() {
			return data;
		}

		public void setData(VersionInfo data) {
			this.data = data;
		}
	}

	class ApplicationInfoResponse extends AbstractApiResponse {
		private ApplicationInfoContent data;

		public ApplicationInfoContent getData() {
			return data;
		}

		public void setData(ApplicationInfoContent data) {
			this.data = data;
		}

	}

	@Override
	public ApiResponse getIndexPath(String version) throws ServiceException {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("versionnum", version);
		headerMap.put("type", "003");  //hjw modify 001->003 
		ApiResponse resp = (ApiResponse) callPostApi(
				DzlcAndroidConfig.INDEX_XML_URL, headerMap, ApiResponse.class);
		if (resp != null)
			return resp;
		else
			return null;
	}

}
