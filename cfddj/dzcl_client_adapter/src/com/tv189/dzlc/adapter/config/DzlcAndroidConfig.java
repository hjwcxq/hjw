package com.tv189.dzlc.adapter.config;

import java.io.File;

public class DzlcAndroidConfig {
	//测试平台
//	private static final String API_SERVER_HOST="http://test2.zuoanlong.com:8083";//正式API（盒子测试地址）
	
//	private static final String API_SERVER_HOST="http://192.168.25.71:8080";//正式API（盒子测试地址）
	
	
//	private static final String API_SERVER_HOST="http://test.zuoanlong.com:8083";//测试API
	
//	private static final String API_SERVER_HOST="http://api.zuoanlong.com:8082";//正式API
	
	//本地测试 
//	private static final String API_SERVER_HOST ="http://192.168.26.185:8080";
	public static String  BBS_URL = "http://mobileapi.zuoanlong.com:8082/tysx/bbs";
	public static String  BBS_MYSUBMIT = "http://bbs.zuoanlong.com:8081/home.php?mod=space&do=thread&view=me&mobile=yes"; // 我的帖子
	public static String  BBS_MYJOIN = "http://mobileapi.zuoanlong.com:8082/tysx/bbs";
	public static String BBS_SUBMITNEW = "http://bbs.zuoanlong.com:8081/forum.php?mod=post&action=newthread&fid=2"; //发帖
	public static String BBS_MYMESSAGE = "http://bbs.zuoanlong.com:8081/home.php?mod=space&d=pm&mobile=yes"; //站内消息
	//正式平台
	private static final String API_SERVER_HOST="http://mobileapi.zuoanlong.com:8082"; 

	public static final String lOGIN_DEVICE_URI=API_SERVER_HOST+"/tysx/api/v1/tysx/devicelogin";
	public static final String LOGIN_GENEREL_URL=API_SERVER_HOST+"/tysx/api/v1/tysx/userlogin_v2";
	//测试平台临时使用登录api
	//public static final String LOGIN_GENEREL_URL="http://mobileapi.zuoanlong.com:8082/tysx/api/v1/tysx/userlogin_v2";
	
	//第三方支付 
	public static final String ALIPAY =API_SERVER_HOST+"/tysx/api/v1/tysx/alipay";
	//用户鉴权 authorization
	public static final String USER_AUTHORIZATION=API_SERVER_HOST+"/tysx/api/v1/tysx/auth";
	//获取直播列表
	public static final String LIVE_VIDEO=API_SERVER_HOST+"/tysx/api/v1/tysx/live";
	//我的留言
	public static final String QUEST_MY=API_SERVER_HOST+"/tysx/api/v1/question/001/user";
	//用户注册
	public static final String REGISTER=API_SERVER_HOST+"/tysx/api/v1/tysx/register_v2";
	//一键注册
	public static final String REGISTER_ONE=API_SERVER_HOST+"/tysx/api/v1/tysx/register_one";
	//添加留言
	public static final String QUEST_ADD=API_SERVER_HOST+"/tysx/api/v1/question/001/add";
	//留言专区
	public static final String QUEST_LIST=API_SERVER_HOST+"/tysx/api/v1/question/001";
	//修改用户密码
	public static final String UPDATE_USER_PASS=API_SERVER_HOST+"/tysx/api/v1/tysx/changepass_v2";
	//修改用户名称
	public static final String UPDATE_USER_NICKNAME=API_SERVER_HOST+"/tysx/api/v1/tysx/modifynickname_v2";
	//添加收藏
	public static final String ADD_COLLECTION=API_SERVER_HOST+"/tysx/api/v1/tysx/addfavourite_v2";
	//删除收藏
	public static final String DELETE_COLLECTION=API_SERVER_HOST+"/tysx/api/v1/tysx/delfavourite_v2";
	//获取收藏列表 
	public static final String LIST_COLLECTION=API_SERVER_HOST+"/tysx/api/v1/tysx/favouritelist_v2";
	//节目介绍 Program description
	public static final String PROGRAM_DESCRIPTION=API_SERVER_HOST+"/tysx/api/v1/tysx/appprofile";
	//节目订阅 prgsubscribe
	public static final String PRGSUBSCRIBE=API_SERVER_HOST+"/tysx/api/v1/tysx/prgsubscribe"; 
	//取消节目录订阅 
	public  static final String PRGUNSUBSCRIBE=API_SERVER_HOST+"/tysx/api/v1/tysx/prgunsubscribe";
	//退订
	public static final String DELETE_ORDER=API_SERVER_HOST+"/tysx/api/v1/tysx/unsubscribe";
	//个性股票查询
	public static final String GEXING_STOCK=API_SERVER_HOST+"/tysx/api/v1/stock/getchartdata";
	//自选股查看
	public static final String ZIXUAN_STOCK=API_SERVER_HOST+"/tysx/api/v1/stock/myfavorite";
	//添加自选股
	public static final String ADD_STOCK=API_SERVER_HOST+"/tysx/api/v1/stock/addfavorite";
	//删除所有自选股
	public static final String DELETE_STOCK=API_SERVER_HOST+"/tysx/api/v1/stock/deletecancelfavorite";
	//热门股票查询
	public static final String HOT_STOCK=API_SERVER_HOST+"/tysx/api/v1/stock/hotstocks"; 
	//股票代码搜索
	public static final String SEARCH_STOCK=API_SERVER_HOST+"/tysx/api/v1/stock/search";
	//股票分支查询  例如个股、大盘、专家、百科、老左、等
	public static final String ID_STOCK_VIDEO=API_SERVER_HOST+"/tysx/api/v1/tysx/gsbdpdmore";

	public static final String API_PARA_CLIENT_TYPE = "3";
	//用户鉴权时的两个参数
	public static final String PURCHASETYPE = "1";
	public static final String PRODUCTID = "1000000097";
	//订购
	public static final String PURCHASETYPE_SUBSCRIBE = "0";
	//获取直播节目单列表
	public static final String LIVE_SCHEDULE = API_SERVER_HOST+"/tysx/api/v1/ty24h/schedule";
	//获取节目单列表
	public static final String SCHEDULE = API_SERVER_HOST+"/tysx/api/v1/tysx/schedule";
	//获取直播节目详情
	public static final String SCHEDULEINFO = API_SERVER_HOST+"/tysx/api/v1/tysx/scheduleinfo";
	
	public static final String CHANNEL_ID = "01111621";
	
//	public static final String CHANNEL_ID = "01112021";
	
	public static final String TV_LIVE= "1";
	// android 端 日志目录
	private static String GPLZ_CLIENT_LOG_FOLDER = "/mnt/sdcard/gplz/clientlog";
	//手机点播协议、用于推送 mobilePlayInfo
 	private static String MOBILE_PLAY=API_SERVER_HOST+"/tysx/api/v1/ty24h/mobilePlayInfo";
 	//获取视频详细信息
 	public static String CONTENTDATAIL=API_SERVER_HOST+"/tysx/api/v1/tysx/contentdetail";
	//日志上传路径
	public static String URL_UPLOADLOGFILE = API_SERVER_HOST+"/tysx/api/v1/tysx/uploadlog";
	//日志上传
	public static String UPLOAD=API_SERVER_HOST+"/api/v1/tysx/uploadlog";
	//专家一对一专家列表
	public static String EXPERTS_LISTS=API_SERVER_HOST+"/tysx/api/v1/exterps/expertsList"; 
	//专家一对一专家详细信息
	public static String EXPERTS_DETAILS=API_SERVER_HOST+"/tysx/api/v1/exterps/expertsdetails";
	//专家一对一 发表留言addExperteMessage
	public static String ADD_EXPERTE_MESSAGE=API_SERVER_HOST+"/tysx/api/v1/exterps/addexpertemessage";
	//专家一对一   某一个专家和某一个用户 对话的 默认返回前50条数据searchbyexpert
	public static String SEARCH_BY_EXPERT=API_SERVER_HOST+"/tysx/api/v1/exterps/searchbyexpert";
	//向专家发出一对一请求requestvideochat
	public static String REQUEST_VIDEO_CHAT=API_SERVER_HOST+"/tysx/api/v1/exterps/requestvideochat";
	//一对一 我跟所有专家的留言
	public static String MY_ALLQUESTIONS=API_SERVER_HOST+"/tysx/api/v1/exterps/myAllQuestions";
	//专家一对一的流
	public static String  FMS_MTOMATO="http://fms.mtomato.cn/";
	 //自动更新 /api/v1/appinfo
	public static String APPINFO=API_SERVER_HOST+"/tysx/api/v1/appinfo/1";
	 //版本更新 
	public static String DZ_APP_INFO=API_SERVER_HOST+"/tysx/api/v2/dzlc/appversiondzcj";
	
	//我的股票数据搜素记录
	public static String MYSEACHLIST=API_SERVER_HOST+"/tysx/api/v1/tysx/mysearchlist";
	//查看股票详情 searchstock
	public static String SEACHSTOCK = API_SERVER_HOST+"/tysx/api/v1/tysx/searchstock";
	//自选股的取消收藏cancelfavorite
	public static String CANCELFAVORITE=API_SERVER_HOST+"/tysx/api/v1/stock/cancelfavorite";
	//清除历史记录
	public static String CLEANSEACH=API_SERVER_HOST+"/tysx/api/v1/tysx/cleansearch";
		 
	//获取新闻详细信息
	public static final String API_NEWS_INFO=API_SERVER_HOST+"/tysx/api/v1/tysx/newsinfo";
	//注册个推的clientid
	public static String URL_REGPUSHCLIENT = API_SERVER_HOST+"/tysx/api/v1/tysx/regpushclient";
	
	//安装信息
	public static String URL_INSTALL_APK = API_SERVER_HOST+"/tysx/api/v1/appinfo/opensApplicationCapture/";
	//卸载
	public static String URL_UNINSTALL_APK=API_SERVER_HOST+"/tysx/api/v1/appinfo/uninstallApplicationCapture/";
	//一键选股
	public static String URL_ONE_KEY_CHOOSE_STOCK = API_SERVER_HOST + "/tysx/api/v2/dzlc/newstock" ;
	//推荐应用
	public static String URL_RECOMMEND_APP = API_SERVER_HOST + "/tysx/api/v2/dzlc/application" ;
	
	public static String URL_RECOMMEND_APP_DOWNLOAD_CONUT = API_SERVER_HOST + "/tysx/api/v2/dzlc/applicationlook" ;

	public static String ONE_KEY_REG_SDK_URL = "https://api.tv189.com/v2";
	
	public static String FOCUS_LIVEROLING_TITLE_ = API_SERVER_HOST + "/tysx/api/v2/dzlc/liverolling";
	
	public static String WELCOME_API_URL = API_SERVER_HOST + "/tysx/api/v2/dzlc/welcomepage";
	
	//public static String INDEX_XML_URL = API_SERVER_HOST + "/tysx/api/v2/dzlc/index";
	public static String INDEX_XML_URL = "http://test2.zuoanlong.com:8083/tysx/api/v2/dzcj/index";
	//
	public static String RED_CIRCLE_XML_URL = API_SERVER_HOST + "/tysx/api/v1/exterps/experquestionlist";
	
	public static File getClientLogFolder(){
		File respFolder = new File(GPLZ_CLIENT_LOG_FOLDER);
		if(!respFolder.exists())
			respFolder.mkdirs();
		return respFolder;
	}
}
