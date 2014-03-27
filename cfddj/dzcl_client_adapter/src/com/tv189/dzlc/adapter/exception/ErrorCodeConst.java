package com.tv189.dzlc.adapter.exception;

public class ErrorCodeConst {
	
	/***
	 *  1开头 都是客户端可以看见的异常
	 */
	
	public static final String CODE_CONNECT_NETWORK_FAIL = "1000";

	public static final String CODE_CONNECT_NETWORK_OUT_TIME = "1001";

	public static final String CODE_LOGIN_PASSWORD_ERROR = "1002";
	
	public static final String CODE_LOGIN_USERNAME_NOT_EXIT = "10021";//用户名不存在
	
	public static final String CODE_LOGIN_FAIL = "1003";
	
	public static final String CODE_ACTION_NOT_LOGIN = "1004";  //还没有登录

	public static final String CODE_EMAIL_REG_FAIL = "1005";

	public static final String CODE_EMAIL_REPEAT_REG = "1006";
	
	
	
	public static final String CODE_TOKEN_DISABLE = "1101";//token失效
	
	public static final String CODE_AUTH_FAIL = "1102";//签权师表
	
	
	
	
	/**
	 *   2开头的客户端  不可见的 
	 */
	public static final String CODE_PARSE_JSON_ERROR = "2000";
	
	public static final String CODE_JSON_FORMAT_ERROR = "2001";//json格式异常code
	

}
