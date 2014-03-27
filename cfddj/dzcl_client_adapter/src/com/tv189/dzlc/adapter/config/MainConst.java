package com.tv189.dzlc.adapter.config;

/**
 * @author chuanglong
 * 
 */
public interface MainConst {
	
	public static String APP_KEY="40beb8d53d559b07a055665f9bae93b7";
	

	String version = "1.01";

	public static String IS_VIP = "0";

	public static String NOT_VIP = "1";

	public static String SUCCESS = "0";

	public static String ERROR = "1";

	public static String EXPERT_ID = "expert_id";

	public static String ONLINE = "ONLINE";
	public static String REFRESH = "REFRESH";
	public static String ONLINE_STATUS = "01";

	// 注册用随机字符串
	public static final String RAD_NUMBER = "";

	// 注册短信地址

	public static final String FOCUS_IMAGE = "image";

	public static final String FOCUS_HTML = "html";

	public static final String FOCUS_VIDEO = "video";

	public static final String SYNC = "sync";

	public static final String TAG = "TAG";

	public static final String CHANNELID = "111111";

	public static final String MSG_NUMBER = "02155344253";

	public static final String DEVOLOPER_ID = "000002";
	
	public static final String APP_ID = "10160006000";
	
	public static final String APP_SECRET = "B60C5E5D513542CA9977830ED928CE91";

	interface KeyConst {

		public static final String WEBVIEW_URL_KEY = "html_url";

		public static final String MODULE_ITEM_KEY = "module_item";

	}

	/**
	 * 所有与用户相关的信息存放在这里
	 * 
	 * @author chuanglong
	 * 
	 */
	interface UserConst {
		public static final String USER_FILE_NAME = "user";// 本地用户信息
		public static final String CLIENT_ID = "clientid";
		public static final String TOKEN = "token";
		public static final String USER_ID = "userid";
		public static final String USER_NAME = "username";
		public static final String NICKNAME = "nickname";

		public static final String PHONENUM = "phonenum";
		public static final String PASSWORD = "password";

		public static final String LOGIN_BY_ISMI = "loginbyimsi";
		public static final String DEVICE_ID = "deviceid";
		public static final String DEVICE_TYPE = "devicetype";
		
		public static final String PHONE_STATU = "phoneStatus";
		public static final String BIND = "bind";
		public static final String VERSION = "version";
		
		public static final String IS_VIP = "isVip";
		
		
		

	}

	/**
	 * 所有配置项的文件信息存放在这里
	 * 
	 * @author chuanglong
	 * 
	 */
	interface SettingConst {
		public static final String SETTING_FILE_NAME = "setting"; // 本地设置项的文件名称

		public static final String NEED_GUIDE_KEY = "need_guide";

	}

	interface ModuleConst {

		public static final String MODULE_TYPE_FOCUS = "focus";
		public static final String MODULE_TYPE_APP_RECO = "app_reco";
		public static final String MODULE_TYPE_GO_ORDER = "go_order";
		public static final String MODULE_TYPE_NEWS_RECO = "news_reco";
		public static final String MODULE_TYPE_VIDEO = "video";
		public static final String MODULE_TYPE_FINDITEM = "finditem";

		public static final String ACTION_TYPE_API = "api";
		public static final String ACTION_TYPE_URL = "url";
		public static final String ACTION_TYPE_MENU = "menu";
		public static final String ACTION_TYPE_VIDEO = "video";
	}

	interface StockConst {
		
		public static String STOCK_BAODIAN_TYPE = "gsbk";
		
		public static final String STOCK_GEGU_TYPE = "gufx";
		
		public static String STOCK_DAPAN_TYPE = "dpfx";
		
		public static String STOCK_EXCEPT_TYPE = "zjyc";//热点模块
		
		public static String STOCK_LAOZUO_TYPE= "lzqaa";//老左
		
		public static String STOCK_DATA_ONE_TO_ONE_TYPE= "sjydy";//数据一对一
		
		public static String STOCK_MANAGER_MONEY_TYPE= "lcqm";//理财敲门
		
		public static String STOCK_QLHM_TYPE= "gszl ";//股市诊疗
		
		public static String STOCK_GSZL_TYPE= "qlhm";//潜力黑名
		
		
		
//		public static String TYPE_5 = "gszl";
//		public static String TYPE_7 = "lcqm";
//		public static String TYPE_8 = "lzqaa";
//		public static String TYPE_9 = "mfty";
	}
}
