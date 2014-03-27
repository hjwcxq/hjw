package com.tv189.dzlc.adapter.config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

import android.os.Environment;

import com.tv189.dzlc.adapter.context.SystemContext;


public class APIConfig {
	/**
	 * 正式
	 */
	public static String  BASE_URL = "http://mobileapi.zuoanlong.com:8082/tysx/api/v1";
	public static String  PLANTFORM_URL = "http://mobileapi.zuoanlong.com:8082/tysx/api/v1/tysx";
	public static String  API_HOME = "http://mobileapi.zuoanlong.com:8082";
	public final static String  APPKEY = "12345";
	public static String  DEVICETYPE = "1";
	public static String  BBS_URL = "http://mobileapi.zuoanlong.com:8082/tysx/bbs";
	public static String  BBS_MYSUBMIT = "http://bbs.zuoanlong.com:8081/home.php?mod=space&do=thread&view=me&mobile=yes"; // 我的帖子
	public static String  BBS_MYJOIN = "http://mobileapi.zuoanlong.com:8082/tysx/bbs";

	//卡激活接口
	public static String URL_CARDACTIVE = API_HOME+"/tysx/api/v1/tysx/cardActive";
	
	 //注意，目前用的：“1,32” 去取 ,返回结果中过滤 只显示33
	public static int PLAT_PC = 1 ;
	public static int PLAT_TV = 4 ;
	public static int PLAT_MOBILE = 32;
	public static int PLAT_MOBILEANDTV=36 ;
	public static int PLAT_MOBILEANDTVANDPC =37 ;
	public static int PLAT_MOBILEANDPC =33 ;
	
	public static int UPLOADLOGFILE_INTERVAL = 30*60*1000;
	
	//上传log日志的地址
	public static String URL_UPLOADLOGFILE = API_HOME+"/tysx/api/v1/tysx/uploadlog";
	
	//增加订阅节目
	public static String URL_ADDSUBSCRIBEPRG = API_HOME+"/tysx/api/v1/tysx/prgsubscribe";
	//取消订阅节目
	
	public static String URL_DELSUBSCRIBEPRG = API_HOME+"/tysx/api/v1/tysx/prgunsubscribe";
	
	
	
	
	public static String BBS_SUBMITNEW = "http://bbs.zuoanlong.com:8081/forum.php?mod=post&action=newthread&fid=2"; //发帖
	public static String BBS_MYMESSAGE = "http://bbs.zuoanlong.com:8081/home.php?mod=space&d=pm&mobile=yes"; //站内消息
//	public static String  NOTIFYURL = "http://180.153.149.178:9107/bmspay/msp/alipay/alipayMspNotify.action";
	public static String  NOTIFYURL = "http://paymentgw.tv189.com/bmspay/msp/alipay/alipayMspNotify.action";
	
	public static String  GUESTURL = "http://v.vnet.mobi/portal/480/kpcp/gpzal/whq/jrjbxq/index.jsp";
	private static String BASE_APK_DIR = "Laoz";
	private static String BASE_DOWNLOAD_DIR = BASE_APK_DIR + File.separator + "downloaded";
	
	/**
	 * 头部参数
	 */
	private final static String  HEADER_TOKEN = "token";
	private final static String  HEADER_USERID = "userId";
	private final static String  HEADER_USERNAME = "userName";
	private final static String  HEADER_DEVICEID = "deviceId";
	private final static String  HEADER_DEVICETYPE = "deviceType";
	private final static String  HEADER_PAGESIZE = "pageSize";
	private final static String  HEADER_PHONENUM = "phoneNum";
	private final static String  HEADER_NICKNAME = "nickName";
	private final static String HEADER_GETUI_CLIENT_ID="clientId";
	private final static String  HEADER_CHANNELID ="channelid";
	public static final String CLIENTTYPE = "3";
	
	
	public static final int CLIENTTYPE_TV = 1;
	
	
	
	//渠道编号，打版本时必须更改
	public static final  String CHANNELID = "01111621" ;
	

	private static HeaderParInfo HEADER_PARINFO = null;
	private static LinkedHashMap<String, String> HEADER_MAP;
	
	public static int ERROR_TRY_TIME = 5;//请求失败尝试次数

	
	
	public static String getBaseApkDir(){
		String dir = null;
		if (Environment.getExternalStorageState()
				.equalsIgnoreCase(Environment.MEDIA_MOUNTED
			)) {
			dir = Environment.getExternalStorageDirectory() + File.separator
				   + BASE_APK_DIR;
		}
		return dir;
	}
	
	public static String getDownloadedDir(){
		String dir = null;
		if (Environment.getExternalStorageState()
				.equalsIgnoreCase(Environment.MEDIA_MOUNTED
			)) {
			dir = Environment.getExternalStorageDirectory() + File.separator
				   + BASE_DOWNLOAD_DIR;
		}
		return dir;
	}
	
	
	/**
	 * 初始化请求头
	 * @param tokenT
	 * @param deviceidT
	 * @param uidT
	 * @param typeT
	 */
	public static void initHeaderPar(String tokenT,String userId,String userName,String deviceId,String deviceType,String phoneNum,String channelid ){
		if (tokenT == null && userId == null && deviceId == null) {
			HEADER_PARINFO = null;
		}else{
			HEADER_PARINFO = new HeaderParInfo(tokenT, userId, userName, deviceId,deviceType,phoneNum,channelid);
		}
		HEADER_MAP = null;
	}
	
	
}

class HeaderParInfo{
	private String token;
	private String userId;
	private String userName;
	private String deviceId;
	private String deviceType;
	private String phoneNum;
	private SimpleDateFormat sfDateFormat;
	
	private String channelid;
	
	public HeaderParInfo(String tokenT,String userIdT,String userNameT,String deviceIdT,String deviceTypeT, String phoneNumT,String channelid){
		setToken(tokenT);
		setUserId(userIdT);
		setUserName(userNameT);
		setDeviceId(deviceIdT);
		setDeviceType(deviceTypeT);
		setPhoneNum(phoneNumT);
		sfDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.channelid = channelid ;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	
	
	
}
