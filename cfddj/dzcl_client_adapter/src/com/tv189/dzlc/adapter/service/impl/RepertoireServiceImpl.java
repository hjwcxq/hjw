package com.tv189.dzlc.adapter.service.impl;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.factory.ClientLogFactory;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.log.OpenContentActionLog;
import com.tv189.dzlc.adapter.po.log.OpenPartActionLog;
import com.tv189.dzlc.adapter.po.repertoire.RepertoireInfo;
import com.tv189.dzlc.adapter.service.abstractclass.AbstractDzlcService;
import com.tv189.dzlc.adapter.service.inerface.RepertoireService;

public class RepertoireServiceImpl extends AbstractDzlcService implements RepertoireService {

	public RepertoireServiceImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 节目介绍
	 */
	@Override
	public ApiResponse repertoireList() throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true);
		ApiResponse collectionInfo=null; 
		OpenPartActionLog log=new OpenPartActionLog(context, OpenPartActionLog.PART_ID_STOCK);
		log.setActionDesc("节目介绍");
		Object respAccountObj = null;
		try {
			respAccountObj=callPostApi(DzlcAndroidConfig.PROGRAM_DESCRIPTION, headerMap, ApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode()+":"+e.getErrMsg());
		}
		if(respAccountObj!=null){
			log.setActionResult("0");
			collectionInfo = (ApiResponse)respAccountObj;
		}else{
			log.setActionResult("1");
		}
			ClientLogFactory.getInstance().addLog(log);
		return collectionInfo;
	}

	/**
	 * 获取直播节目单列表
	 */
	//@Override
	/*public List<RepertoireInfo> liveSchedule() throws GplzServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap();
		RepertoireInfoListApiResponse apiResp = (RepertoireInfoListApiResponse)callPostApi(GplzAndroidConfig.LIVE_SCHEDULE, headerMap,RepertoireInfoListApiResponse.class);
		//if(apiResp.isSuccess()){
			return apiResp.getObj()==null?null:apiResp.getObj(); 			
		//}else{
			//return null;
	//	}
	}*/

	/**
	 * 获取节目单
	 */
	@Override
	public List<RepertoireInfo> schedule(String date) throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true);
		if(!date.equals("")){
			headerMap.put("date", date);
		}
		OpenPartActionLog log=new OpenPartActionLog(context, OpenPartActionLog.PART_ID_PROGRAMLIST);
		log.setActionDesc("获取节目单");
		RepertoireInfoListApiResponse apiResp = null;
		try {
			apiResp=(RepertoireInfoListApiResponse)callPostApi(DzlcAndroidConfig.SCHEDULE, headerMap,RepertoireInfoListApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode()+":"+e.getErrMsg());
		}
		if(apiResp!=null&&apiResp.isSuccess()){
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			return apiResp.getInfo()==null?null:apiResp.getInfo(); 			
		}
		return null;
	}
 
	/**
	 * 获取节目详情
	 */
	@Override
	public RepertoireInfo scheduleInfo(String pcode, String liveid)
			throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true);
		headerMap.put("contentid", pcode);
		headerMap.put("liveid", liveid);
		OpenContentActionLog log=new OpenContentActionLog(context, liveid, liveid);
		log.setActionDesc("获取节目单详情");
		RepertoireDatilInfoListApiResponse apiResp = null;
		try {
			apiResp=(RepertoireDatilInfoListApiResponse)callPostApi(DzlcAndroidConfig.SCHEDULEINFO, headerMap,RepertoireDatilInfoListApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode()+":"+e.getErrMsg());
		}
		if(apiResp!=null&&apiResp.isSuccess()){
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			return apiResp.getInfo()==null?null:apiResp.getInfo(); 			
		}
		return null;
	}

	/**
	 * 直播节目单列表
	 */
	@Override
	public List<RepertoireInfo> liveSchedule() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 订阅节目
	 */
	@Override
	public Boolean prgSubscribe(String prgname) throws ServiceException {
			Map<String, String> headerMap=AppSetting.getInstance(context).getCommonHeaderMap(true);
			headerMap.put("prgname",prgname);
			headerMap.put("uid", headerMap.get("userid"));
			ApiResponse respAccountObj = null;
			respAccountObj=(ApiResponse) callPostApi(DzlcAndroidConfig.PRGSUBSCRIBE, headerMap, ApiResponse.class);
			if(respAccountObj!=null && respAccountObj.getCode().equals("00")){
				return true;
			}else{
				return false;
			} 
		}
	/**
	 * 取消节目订阅
	 */
	@Override
	public Boolean prgunSubscribe(String prgname) throws ServiceException {
		Map<String, String> headerMap=AppSetting.getInstance(context).getCommonHeaderMap(true);
		headerMap.put("prgname",prgname);
		headerMap.put("uid", headerMap.get("userid"));
		ApiResponse respAccountObj = null;
		respAccountObj=(ApiResponse) callPostApi(DzlcAndroidConfig.PRGUNSUBSCRIBE, headerMap, ApiResponse.class);
		if(respAccountObj!=null && respAccountObj.getCode().equals("00")){
			return true;
		}else{
			return false;
		} 
	}
class RepertoireInfoListApiResponse extends AbstractApiResponse{  
	private List<RepertoireInfo> info;
	public List<RepertoireInfo> getInfo() {
		return info;
	}
	public void setInfo(List<RepertoireInfo> info) {
		this.info = info;
	}
} 
/**
 * 获取节目详情
 * @author Administrator
 *
 */
class RepertoireDatilInfoListApiResponse extends AbstractApiResponse{  
	private RepertoireInfo info;

	public RepertoireInfo getInfo() {
		return info;
	}

	public void setInfo(RepertoireInfo info) {
		this.info = info;
	}
  }
}
