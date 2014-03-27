/***
 * class:TyLogInfo.java
 * create:cavenshen
 * time:12:07:20 PM
 *
 */

package com.tv189.dzlc.adapter.po.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import android.content.Context;
import android.os.Build;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.util.Utils;
 
public abstract class AbstractLogInfo {
	protected AbstractLogInfo( Context context,String actionCode){
		this.context = context;
		this.actionType = actionCode;
	}
	
	protected AbstractLogInfo( Context context,String actionCode,String partId){
		this.context = context;
		this.accessType = actionCode;
		this.partId = partId;
	}
	
	protected AbstractLogInfo( Context context,String actionCode,String partId,String contentId){
		this.context = context;
		this.accessType = actionCode;
		this.partId = partId;
		this.contentId = contentId;
	}
	protected Context context;
	
	//打开栏目
	protected static final String ACTION_OPEN_PART="104";
	//查看具体的内容
	public static final String ACTION_OPEN_CONTENT="105";
	
	public static final String ACCESSTYPE_WAP = "1" ;//访问类型(1：WAP，2：客户端，9：富媒体客户端 10：客户端4.0)
	public static final String ACCESSTYPE_CLIENT = "2";
	public static final String ACCESSTYPE_RICHMEDIA = "9";
	public static final String ACCESSTYPE_CLIENT40 = "10";
	public static final String ACCESSTYPE_SENDTV = "3";
	//imsi码
	private String imsiid;
	//用户登陆账号，邮箱或者手机号码
	private String userName;
	//用户手机号码，如果是手机号码用户登陆，则返回，否则作为预留属性
	private String phoneNum;
	//用户昵称
	private String nickName;
	//视讯返回的uid
	private String uid;
	//log类型
	private String actionType;
	//记录时间
	private Date actionTime;
	//描述，预留属性
	private String actionDesc;
	//终端型号 例如：Iphone，HUAWEI8812
	private String ua;
	//访问类型(1：WAP，2：客户端，9：富媒体客户端 10：客户端4.0)
	private String accessType;
	//处理结果(0：成功，1：失败)
	private String actionResult;
	//处理失败原因
	private String failCause;
	//用户自己的ip
	private String loginIp;
	//客户端版本号
	private String version;
	//网络类型
	private String netType;
	//栏目ID
	private String partId;
	//内容类型
	private String contentId;
//	//请求URL
//	private String reqUrl;
//	//http响应状态
//	private String httpRespCode;
//	//响应字节数
//	private String respSize;
//	//响应请求时间
//	private String respTime;
//	//网络类型
//	private String networkType;
	
	
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}


	public void setActionResult(String actionResult) {
		this.actionResult = actionResult;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}

	


	public String toString() {
		try {
			fillCommonPara();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		sb.append(imsiid == null ? "" : imsiid);
		sb.append("|");
		sb.append(phoneNum == null ? "" : phoneNum);
		sb.append("|");
		sb.append(userName == null ? "" : userName);
		sb.append("|");
		sb.append(nickName == null ? "" : nickName);
		sb.append("|");
		sb.append(uid == null ? "" : uid);
		sb.append("|");
		sb.append(actionType == null ? "" : actionType);
		sb.append("|");
		sb.append(sdf.format(Calendar.getInstance().getTime()));
		sb.append("|");
		sb.append(actionDesc == null ? "" : actionDesc);
		sb.append("|");
		sb.append(ua == null ? "" : ua);
		sb.append("|");
		sb.append(accessType == null ? "" : accessType);
		sb.append("|");
		sb.append(actionResult == null ? "" : actionResult);
		sb.append("|");
		sb.append(version == null ? "" : version);
		sb.append("|");
		sb.append(failCause == null ? "" : failCause);
		sb.append("|");
		sb.append(loginIp == null ? "" : loginIp);
		sb.append("|");
		sb.append(netType == null ? "" : netType);
		sb.append("|");
		sb.append(version == null ? "" : version);
		sb.append("|");
		sb.append(contentId == null ? "" : contentId);
		sb.append("|");
		sb.append(partId == null ? "" : partId);
		return sb.toString();
	}

	private void fillCommonPara() throws ServiceException{
		SystemContext sysContext = SystemContext.getInstance(context);
		this.imsiid = getLogPara("imsiid");
		this.phoneNum = getLogPara("phonenum");
		this.userName = getLogPara("username");
		this.uid = getLogPara("userid");
		this.nickName = getLogPara("nickname");
		this.actionTime = Calendar.getInstance().getTime();
		this.version = Utils.getVersion(context);
		this.ua = Build.DEVICE;
		this.accessType = "2";
		this.netType = Utils.getNetworkType(context);
		this.loginIp = Utils.getLocalIpAddress();
	}
	
	private String getLogPara(String key) throws ServiceException{
		Map<String, String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(false);
		if(headerMap.containsKey(key)&&headerMap.get(key)!=null){
			return headerMap.get(key);
		}else{
			return "";
		}
	}
}
