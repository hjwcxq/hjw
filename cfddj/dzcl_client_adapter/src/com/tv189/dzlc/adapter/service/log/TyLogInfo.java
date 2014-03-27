/***
 * class:TyLogInfo.java
 * create:cavenshen
 * time:12:07:20 PM
 *
 */

package com.tv189.dzlc.adapter.service.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cavenshen
 * 
 */
public class TyLogInfo {
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final String ACTION_OPEN_APP = "100";
	public static final String ACTION_LOGIN = "101";
	public static final String ACTION_RIGISTER = "102";
	public static final String ACTION_LOGOUT = "103";
	// 打开栏目
	public static final String ACTION_OPEN_PART = "104";
	// 查看具体的内容
	public static final String ACTION_OPEN_CONTENT = "105";
	// 发表留言
	public static final String ACTION_INSTALL = "106";
	// 查询股票
	public static final String ACTION_OPEN_STOCK = "108";
	// 成为包月用户_成功
	public static final String ACTION_BUY_MONTH_USER_SUCCESS = "109";
	// 退订成功
	public static final String ACTION_TUIDING_SUCCESS = "110";
	// 请求成为包年用户
	public static final String ACTION_BUY_YEAR_USER = "111";
	// 成为包月用户_失败
	public static final String ACTION_BUY_MONTH_USER_FAIL = "112";

	// 退订_失败
	public static final String ACTION_TUIDING_FAIL = "114";

	// 直播频道
	public static final String PART_ID_LIVE = "200";
	// 股市宝典频道
	public static final String PART_ID_GSBD = "201";
	// 数据频道
	public static final String PART_ID_STOCK = "202";
	// 说吧频道
	public static final String PART_ID_BBS = "203";

	// 用户中心频道
	public static final String PART_ID_USER_CENTER = "204";
	// 用户中心，个人信息频道
	public static final String PART_ID_USER_PROFILE = "205";
	// 用户中心，我的订购频道
	public static final String PART_ID_USER_BUY = "206";
	// 用户中心，我的提问频道
	public static final String PART_ID_USER_MY_QUESTION = "207";
	// 用户中心，我的帖子频道
	public static final String PART_ID_USER_MY_POSTS = "208";
	// 用户中心，节目介绍
	public static final String PART_ID_USER_PROGRAMINTRODUCTION = "209";
	// 用户中心，我的收藏频道
	public static final String PART_ID_USER_MY_COLLECT = "210";
	// 节目单
	public static final String PART_ID_PROGRAMLIST = "211";

	// 股市宝典频道 -个股
	public static final String PART_ID_GSBD_GUFX = "212";
	// 股市宝典频道 -大盘
	public static final String PART_ID_GSBD_DPFX = "213";
	// 股市宝典频道
	public static final String PART_ID_GSBD_ZJYC = "214";
	// 股市宝典频道
	public static final String PART_ID_GSBD_GSBK = "215";
	// 股市宝典频道
	public static final String PART_ID_GSBD_LZQAA = "216";
	// 专家一对一
	public static final String PART_ID_VCHAT = "217";
	// 添加自选股
	public static final String PART_ID_ADD_STOCK = "218";

	// 查看热门股票
	public static final String PART_ID_STOCK_HOT = "219";

	// 最近查询股票
	public static final String PART_ID_LAST_SEARCH_STOCK = "220";
	// 自选股
	public static final String PART_ID_MY_STOCK = "221";
	public static final String ACCESSTYPE_WAP = "1";// 访问类型(1：WAP，2：客户端，9：富媒体客户端
													// 10：客户端4.0)
	public static final String ACCESSTYPE_CLIENT = "2";
	public static final String ACCESSTYPE_RICHMEDIA = "9";
	public static final String ACCESSTYPE_CLIENT40 = "10";

	public static final String ACCESSTYPE_SENDTV = "3";

	// imsi码
	private String imsiid;
	// 用户登陆账号，邮箱或者手机号码
	private String userName;
	// 用户手机号码，如果是手机号码用户登陆，则返回，否则作为预留属性
	private String phoneNum;
	// 用户昵称
	private String nickName;
	// 视讯返回的uid
	private String uid;
	// log类型
	private String actionType;
	// 记录时间
	private Date actionTime;
	// 描述，预留属性
	private String actionDesc;
	// 终端型号 例如：Iphone，HUAWEI8812
	private String ua;
	// 访问类型(1：WAP，2：客户端，9：富媒体客户端 10：客户端4.0)
	private String accessType;
	// 处理结果(0：成功，1：失败)
	private String actionResult;
	// 处理失败原因
	private String failCause;
	// 用户自己的ip
	private String loginIp;
	// 客户端版本号
	private String version;
	// 网络类型
	private String netType;
	// 栏目ID
	private String partId;
	// 内容类型
	private String contentId;

	public String getImsiid() {
		return imsiid;
	}

	public void setImsiid(String imsiid) {
		this.imsiid = imsiid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getActionResult() {
		return actionResult;
	}

	public void setActionResult(String actionResult) {
		this.actionResult = actionResult;
	}

	public String getFailCause() {
		return failCause;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String toString() {
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
		sb.append(sdf.format(actionTime));
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

	public TyLogInfo(String imsiid, String userName, String phoneNum,
			String nickName, String uid, String actionType, Date actionTime,
			String actionDesc, String ua, String accessType,
			String actionResult, String failCause, String loginIp,
			String version, String netType, String partId, String contentId) {
		this.imsiid = imsiid;
		this.userName = userName;
		this.phoneNum = phoneNum;
		this.nickName = nickName;
		this.uid = uid;
		this.actionType = actionType;
		this.actionTime = actionTime;
		this.actionDesc = actionDesc;
		this.ua = ua;
		this.accessType = accessType;
		this.actionResult = actionResult;
		this.failCause = failCause;
		this.loginIp = loginIp;
		this.version = version;
		this.netType = netType;
		this.partId = partId;
		this.contentId = contentId;

	}

}
