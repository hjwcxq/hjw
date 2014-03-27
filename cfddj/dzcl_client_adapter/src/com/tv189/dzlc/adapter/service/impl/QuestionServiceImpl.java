package com.tv189.dzlc.adapter.service.impl;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import android.content.Context;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.exception.ErrorCodeConst;
import com.tv189.dzlc.adapter.factory.ClientLogFactory;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.log.OpenContentActionLog;
import com.tv189.dzlc.adapter.po.log.OpenPartActionLog;
import com.tv189.dzlc.adapter.po.question.QuestionInfo;
import com.tv189.dzlc.adapter.po.question.QuestionPageContent;
import com.tv189.dzlc.adapter.service.abstractclass.AbstractDzlcService;
import com.tv189.dzlc.adapter.service.inerface.QuestionService;

public class QuestionServiceImpl extends AbstractDzlcService implements
		QuestionService {
	public QuestionServiceImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 上传语音
	 */
	public QuestionInfo upload(File questionContent) throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(false);
		headerMap.put("commenttype", "3");
		headerMap.put("nickname", URLEncoder.encode(headerMap.get("nickname")));
		headerMap.put("deviceType", "3");
		headerMap.put("questionContent", "123");// 123无意义，占位用，（不能为空）
		headerMap.put("filename", questionContent.getName());
		// headerMap.put("questionContent", questionContent);
		QuestionInfoResponse apiResp = (QuestionInfoResponse) callPostApi2(
				questionContent, DzlcAndroidConfig.QUEST_ADD, headerMap,
				QuestionInfoResponse.class);

		if (apiResp != null && apiResp.isSuccess()) {
			return apiResp.getData() == null ? null : apiResp.getData();
		} else {
			return null;
		}
	}

	/**
	 * 添加提问
	 */

	public QuestionInfo questionAdd(String commentType, String deviceType,
			String questionContent) throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		headerMap.put("commenttype", commentType);
		headerMap.put("deviceType", deviceType);
		headerMap.put("questionContent", questionContent);
		QuestionInfoResponse apiResp = (QuestionInfoResponse) callPostApi(
				DzlcAndroidConfig.QUEST_ADD, headerMap,
				QuestionInfoResponse.class);
		if (apiResp != null && apiResp.isSuccess()) {
			return apiResp.getData() == null ? null : apiResp.getData();
		} else if(apiResp != null && "01".equalsIgnoreCase(apiResp.getCode())){
			throw new ServiceException(ErrorCodeConst.CODE_AUTH_FAIL, "");
		}else{
			return null;
		}
	}

	@Override
	public QuestionPageContent questionMy(String prgId, String pagenumber,
			String pagesize) throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		// 我的提问
		headerMap.put("pagenumber", pagenumber);
		headerMap.put("pagesize", pagesize);

		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_USER_MY_QUESTION);
		log.setActionDesc("我的提问");
		QuestionContentListApiResponse apiResp = null;
		try {
			apiResp = (QuestionContentListApiResponse) callPostApi(
					DzlcAndroidConfig.QUEST_MY, headerMap,
					QuestionContentListApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrCode());
		}
		if (apiResp != null && apiResp.isSuccess()) {
			log.setFailCause("0");
			ClientLogFactory.getInstance().addLog(log);
			return (apiResp.getData() == null ? null : apiResp.getData());
		} else {
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return null;
		}
	}

	/**
	 * 提问专区
	 */
	@Override
	public QuestionPageContent questionList(String prgId, String pagenumber,
			String pagesize) throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(false);
		headerMap.put("prgid", prgId);
		headerMap.put("pagenumber", pagenumber);
		headerMap.put("pagesize", pagesize);
		headerMap.put("lastupdatetime", SystemContext.last_refresh_data);
		OpenContentActionLog log = new OpenContentActionLog(context, prgId,
				prgId);
		log.setActionDesc("提问专区");
		QuestionContentListApiResponse apiResp = null;
		try {
			apiResp = (QuestionContentListApiResponse) callPostApi(
					DzlcAndroidConfig.QUEST_LIST, headerMap,
					QuestionContentListApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
		}
		if (apiResp != null && apiResp.isSuccess()) {
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			return (apiResp.getData() == null ? null : apiResp.getData());
		} else {
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return null;
		}
	}

}

class QuestionContentListApiResponse extends AbstractApiResponse {
	private QuestionPageContent data;

	public QuestionPageContent getData() {
		return data;
	}

	public void setData(QuestionPageContent data) {
		this.data = data;
	}
}

class QuestionInfoResponse extends AbstractApiResponse {
	private QuestionInfo data;

	public QuestionInfo getData() {
		return data;
	}

	public void setData(QuestionInfo data) {
		this.data = data;
	}
}
