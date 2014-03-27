package com.tv189.dzlc.adapter.service.impl;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.exception.ErrorCodeConst;
import com.tv189.dzlc.adapter.factory.ClientLogFactory;
import com.tv189.dzlc.adapter.po.collection.CollectionList;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.log.OpenContentActionLog;
import com.tv189.dzlc.adapter.po.log.OpenPartActionLog;
import com.tv189.dzlc.adapter.po.user.UserAccountInfo;
import com.tv189.dzlc.adapter.service.abstractclass.AbstractDzlcService;
import com.tv189.dzlc.adapter.service.inerface.CollectionService;

public class CollectionServiceImpl extends AbstractDzlcService implements CollectionService {

	public CollectionServiceImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 添加收藏
	 */
	@Override
	public Boolean collectionAdd(String ptype, String pcode,
			String contentid) throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true);
		headerMap.put("ptype", ptype);
		headerMap.put("pcode", pcode);
		headerMap.put("contentid", contentid);
		ApiResponse info=null;
		Object respAccountObj = null;
		OpenContentActionLog log=new OpenContentActionLog(context, contentid, contentid);
		log.setActionDesc("添加收藏");
		try {
			respAccountObj=callPostApi(DzlcAndroidConfig.ADD_COLLECTION, headerMap, ApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode()+":"+e.getErrMsg());
		}
		if(respAccountObj!=null)
			log.setActionResult("0");
		ClientLogFactory.getInstance().addLog(log);
			info = (ApiResponse)respAccountObj;
		if(info!=null&&info.getCode()!=null&&info.getCode().equals("00")){
			return true;
		}
		return false;
	}

	/**
	 * 我的收藏
	 */
	@Override
	public List<CollectionList> collectionList(String pagenum, String pagesize)
			throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true); 
		headerMap.put("pagenum", pagenum);
		headerMap.put("pagesize", pagesize); 
		CollectionListApiResponse apiResp = null;
		OpenPartActionLog log=new OpenPartActionLog(context, OpenPartActionLog.PART_ID_USER_MY_COLLECT);
		log.setActionDesc("我的收藏");
		try {
				apiResp=(CollectionListApiResponse)callPostApi(DzlcAndroidConfig.LIST_COLLECTION, headerMap,CollectionListApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode()+":"+e.getErrMsg());
		}
		if(apiResp!=null&&apiResp.isSuccess()){
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			return  (List<CollectionList>) (apiResp.getData().getContent()==null||apiResp.getData().getContent()==null?null:apiResp.getData().getContent());
		}else{
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return null;
		}   
	}

	/**
	 * 取消收藏
	 */
	@Override
	public ApiResponse collectionDelete(String contentid)
			throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true);
		ApiResponse collectionList=null;
		headerMap.put("contentid", contentid); 
		Object respAccountObj = null;
		OpenContentActionLog log=new OpenContentActionLog(context, contentid, contentid);
		log.setActionDesc("取消收藏");
		try {
			respAccountObj=callPostApi(DzlcAndroidConfig.DELETE_COLLECTION, headerMap, ApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode()+":"+e.getErrMsg());
		}
		if(respAccountObj!=null){
			log.setActionResult("0");
			collectionList = (ApiResponse)respAccountObj;
		}else{
			log.setActionResult("1");
		}
		ClientLogFactory.getInstance().addLog(log);
		return collectionList;
	}
}

class CollectionListApiResponse extends AbstractApiResponse{
	private CollectionInfoList data;

	public CollectionInfoList getData() {
		return data;
	}

	public void setData(CollectionInfoList data) {
		this.data = data;
	}

	 

	 
}

class CollectionInfoList{
	private List<CollectionList> content;

	public List<CollectionList> getContent() {
		return content;
	}

	public void setContent(List<CollectionList> content) {
		this.content = content;
	} 
}
