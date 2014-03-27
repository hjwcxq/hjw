package com.tv189.dzlc.adapter.service.impl;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.question.QuestionInfo;
import com.tv189.dzlc.adapter.po.sqlpo.ExpertsInfo;
import com.tv189.dzlc.adapter.po.sqlpo.VideoChatInfo;
import com.tv189.dzlc.adapter.service.abstractclass.AbstractDzlcService;
import com.tv189.dzlc.adapter.service.inerface.ExpertsService;

public class ExpertsServiceImpl extends AbstractDzlcService implements
		ExpertsService {
	public ExpertsServiceImpl(Context context) {
		super(context);
	}

	/**
	 * 专家列表
	 */
	@Override
	public List<ExpertsInfo> expertsList() throws ServiceException {
		Map<String, String> headerMap = new HashMap<String, String>();
		ExpertsInfoResponse respAccountObj = (ExpertsInfoResponse) callPostApi(
				DzlcAndroidConfig.EXPERTS_LISTS, headerMap,
				ExpertsInfoResponse.class);
		if (respAccountObj != null && respAccountObj.isSuccess()) {
			return respAccountObj.getData() == null ? null : respAccountObj
					.getData();
		} else {
			return null;
		}
	}

	/**
	 * 获取专家详情
	 */
	@Override
	public ExpertsInfo expertsDetails(String userUid, String expertId)
			throws ServiceException {
		Map<String, String> headerMap = new HashMap<String, String>();
		String starpath = "hls-live/livepkgr/_definst_/";
		String endpath = "/livestream.m3u8";
		headerMap.put("nickname", AppSetting.getInstance(context).getNickName());
		headerMap.put("useruid", userUid);
		headerMap.put("expertid", expertId);
		GuestInfoResponse respAccountObj = (GuestInfoResponse) callPostApi(
				DzlcAndroidConfig.EXPERTS_DETAILS, headerMap,
				GuestInfoResponse.class);
		if (respAccountObj != null && respAccountObj.isSuccess()) {
			respAccountObj.getData().setVideoChatUrl(
					DzlcAndroidConfig.FMS_MTOMATO + starpath + userUid
							+ endpath);
			return respAccountObj.getData();
		}
		return null;
	}

	/**
	 * 用于查看专家详细信息
	 * 
	 * @author Administrator
	 * 
	 */
	class GuestInfoResponse extends AbstractApiResponse {
		private ExpertsInfo data;

		public ExpertsInfo getData() {
			return data;
		}

		public void setData(ExpertsInfo data) {
			this.data = data;
		}

	}

	/**
	 * 添加专家与用户的留言信息
	 */
	@Override
	public Boolean addExperteMessage(VideoChatInfo info)
			throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true);
		headerMap.put("expertuid", info.getExpertUid() + "");
		headerMap.put("useruid", AppSetting.getInstance(context).getUserId());
		headerMap.put("expertname", info.getExpertName());
		headerMap.put("usernickname", AppSetting.getInstance(context).getNickName());
		headerMap.put("userphonenum", AppSetting.getInstance(context).getPhoneNumber());
		headerMap.put("content", info.getContent());
		headerMap.put("type", info.getType());
		headerMap.put("msgtype", info.getMsgType());
		headerMap.put("isauditpass", info.getIsAuditPass());
		ApiResponse respAccountObj = (ApiResponse) callPostApi(
				DzlcAndroidConfig.ADD_EXPERTE_MESSAGE, headerMap,
				ApiResponse.class);
		if (respAccountObj.isSuccess() && respAccountObj != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 上传语音
	 */
	public Boolean upload(VideoChatInfo info) throws ServiceException {

		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("expertuid", info.getExpertUid() + "");
		headerMap.put("useruid", info.getUserUid());
		headerMap.put("expertname", URLEncoder.encode(info.getExpertName()));
		headerMap.put("usernickname", URLEncoder.encode(info.getUserNickname()));
		headerMap.put("userphonenum", info.getUserPhonenum());
		headerMap.put("content", "123");//暂用123
		headerMap.put("type", info.getType());
		headerMap.put("msgtype", info.getMsgType());
		headerMap.put("isauditpass", info.getIsAuditPass());
		headerMap.put("filename", info.getfile().getName());// 暂用
		// QuestionInfoResponse apiResp = (QuestionInfoResponse) callPostApi(
		// info.getContent(), GplzAndroidConfig.ADD_EXPERTE_MESSAGE,
		// headerMap, QuestionInfoResponse.class);
		//
		ApiResponse respAccountObj = (ApiResponse) callPostApi2(
				info.getfile(), DzlcAndroidConfig.ADD_EXPERTE_MESSAGE,
				headerMap, ApiResponse.class);
		if (respAccountObj.isSuccess() && respAccountObj != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得 专家和用户的留言
	 */
	@Override
	public List<VideoChatInfo> searchByexpert(String expertUid, String userUid,
			String pagesize, String pagenum, String charDate, String isAuditPass)
			throws ServiceException {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("expertuid", expertUid);
		headerMap.put("useruid", userUid);
		headerMap.put("pagesize", pagesize);
		headerMap.put("pagenum", pagenum);
		headerMap.put("chatdate", charDate);
		headerMap.put("isauditpass", isAuditPass);

		VideoChatInfoResponse respAccountObj = (VideoChatInfoResponse) callPostApi(
				DzlcAndroidConfig.SEARCH_BY_EXPERT, headerMap,
				VideoChatInfoResponse.class);
		if (respAccountObj != null && respAccountObj.isSuccess()) {
			return respAccountObj.getData();
		}
		return null;
	}

	/**
	 * 向专家发出一对一请求
	 */
	@Override
	public ExpertsInfo requestvideochat(String userUid, String expertId)
			throws ServiceException {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("useruid", userUid);
		headerMap.put("expertid", expertId);
		headerMap.put("nickname", AppSetting.getInstance(context).getNickName());
		GuestInfoResponse respAccountObj = (GuestInfoResponse) callPostApi(
				DzlcAndroidConfig.REQUEST_VIDEO_CHAT, headerMap,
				GuestInfoResponse.class);
		if (respAccountObj != null && respAccountObj.isSuccess()) {
			return respAccountObj.getData();
		}
		return null;
	}

	/**
	 * 查询一对一 我跟所有专家的留言
	 */
	@Override
	public List<VideoChatInfo> myAllQuestions(String userUid, String chatDate)
			throws ServiceException {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("useruid", userUid);
		headerMap.put("chatdate", chatDate);
		VideoChatInfoResponse respAccountObj = (VideoChatInfoResponse) callPostApi(
				DzlcAndroidConfig.MY_ALLQUESTIONS, headerMap,
				VideoChatInfoResponse.class);
		if (respAccountObj != null && respAccountObj.isSuccess()) {
			return respAccountObj.getData() == null ? null : respAccountObj
					.getData();
		} else {
			return null;
		}
	}

	@Override
	public List<VideoChatInfo> expertRepeatChatInfos(String expertUid) throws ServiceException {
		// TODO Auto-generated method stub
		Map<String, String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(false);
		if(expertUid != null && !"".equals(expertUid))
			headerMap.put("expertid", expertUid) ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String chatdate = sdf.format(new Date());
//		headerMap.put("chatdate", URLDecoder.decode(chatdate));
		VideoChatInfoResponse respAccountObj = (VideoChatInfoResponse) callPostApi(
				DzlcAndroidConfig.RED_CIRCLE_XML_URL, headerMap,
				VideoChatInfoResponse.class);
		if (respAccountObj != null && respAccountObj.isSuccess()) {
			return respAccountObj.getData() == null ? null : respAccountObj
					.getData();
		} else {
			return null;
		}
	}
}

/**
 * 获取专家留言跟用户留言
 * 
 * @author Administrator
 * 
 */
class VideoChatInfoResponse extends AbstractApiResponse {
	private List<VideoChatInfo> data;

	public List<VideoChatInfo> getData() {
		return data;
	}

	public void setData(List<VideoChatInfo> data) {
		this.data = data;
	}

}

/**
 * 获取专家列表某天全部数据
 * 
 * @author Administrator
 * 
 */
class ExpertsInfoResponse extends AbstractApiResponse {
	private List<ExpertsInfo> data;

	public List<ExpertsInfo> getData() {
		return data;
	}

	public void setData(List<ExpertsInfo> data) {
		this.data = data;
	}
}
