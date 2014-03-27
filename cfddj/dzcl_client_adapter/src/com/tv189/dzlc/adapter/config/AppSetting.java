package com.tv189.dzlc.adapter.config;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceActivity;

import com.tv189.dzlc.adapter.exception.ErrorCodeConst;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.user.UserAccountInfo;
import com.tv189.dzlc.adapter.util.Utils;

public class AppSetting {

	private static AppSetting instance;

	private Context context;

	public static AppSetting getInstance(Context context) {
		if (instance == null) {
			instance = new AppSetting(context);
		} else {
			instance.context = context;
		}
		return instance;
	}

	private AppSetting(Context context) {
		this.context = context;
	}

	/***
	 * ========================================================================
	 * ========= 所有配置项的基本信息
	 * 
	 * @return
	 */
	public boolean isNeedGuide() {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.SettingConst.SETTING_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);

		return sp.getBoolean(MainConst.SettingConst.NEED_GUIDE_KEY, true);
	}

	public void setNeedGuide(boolean needGuide) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(MainConst.SettingConst.NEED_GUIDE_KEY, needGuide);
		editor.commit();
	}

	/**
	 * 用户的基本信息 ================================================================
	 * 
	 * 
	 * ================================================================
	 * 
	 * @return
	 */

	/**
	 * 初始化基本的请求头部文件的参数
	 */
	public Map<String, String> getCommonHeaderMap(boolean isNeedCheckLogin)
			throws ServiceException {
		if (isNeedCheckLogin) {
			if (!isLoginOn()) {
				throw new ServiceException(
						ErrorCodeConst.CODE_ACTION_NOT_LOGIN, "你还没有登录");
			}
		}
		Map<String, String> commonHeaderMap = new HashMap<String, String>();
		commonHeaderMap.put(MainConst.UserConst.TOKEN, getToken());
		commonHeaderMap.put("userid", getUserId());
		commonHeaderMap.put("username", getUserName());
		commonHeaderMap.put("nickname", getNickName());
		commonHeaderMap.put("deviceid", Utils.getDeviceid(context));
		commonHeaderMap.put("devicetype", getDeviceType());
		commonHeaderMap.put("phonenum", getPhoneNumber());
		commonHeaderMap.put("channelid", DzlcAndroidConfig.CHANNEL_ID);
		commonHeaderMap.put("clientid", getClientId());
		commonHeaderMap.put("imsiid", Utils.getImsiid(context));
		commonHeaderMap.put("clienttype",
				DzlcAndroidConfig.API_PARA_CLIENT_TYPE);
		commonHeaderMap.put("loginbyimsiid", isLoginByImsi());
		return commonHeaderMap;
	}

	public String getUserName() {
		SharedPreferences prefers = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME, Activity.MODE_PRIVATE);
		return prefers.getString(MainConst.UserConst.USER_NAME, "");
	}

	public void setUserName(String userName) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.USER_NAME, userName);
		editor.commit();
	}

	public String getToken() {
		SharedPreferences prefers = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME, Activity.MODE_PRIVATE);
		return prefers.getString(MainConst.UserConst.TOKEN, "");
	}

	public void setToken(String token) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.TOKEN, token);
		editor.commit();
	}

	public String getNickName() {
		SharedPreferences prefers = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME, Activity.MODE_PRIVATE);
		return prefers.getString(MainConst.UserConst.NICKNAME, "");
	}

	public void setNickName(String nickName) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.NICKNAME, nickName);
		editor.commit();
	}

	public String getPhoneNumber() {
		SharedPreferences prefers = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME, Activity.MODE_PRIVATE);
		return prefers.getString(MainConst.UserConst.PHONENUM, "");
	}

	public void setPhoneNumber(String phoneNumb) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.PHONENUM, phoneNumb);
		editor.commit();
	}

	public String getUserId() {
		SharedPreferences prefers = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME, Activity.MODE_PRIVATE);
		return prefers.getString(MainConst.UserConst.USER_ID, "");
	}

	public void setUserId(String userId) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.USER_ID, userId);
		editor.commit();
	}

	// 判断是否通过IMSI登陆
	public String isLoginByImsi() {
		SharedPreferences prefers = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME, Activity.MODE_PRIVATE);
		return prefers.getString(MainConst.UserConst.LOGIN_BY_ISMI, "0");
	}

	public void setLoginByImsiFlag(String flag) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.LOGIN_BY_ISMI, flag);
		editor.commit();
	}

	public String getDeviceType() {
		SharedPreferences prefers = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME, Activity.MODE_PRIVATE);
		return prefers.getString(MainConst.UserConst.DEVICE_TYPE, "3");
	}

	public void setDeviceType(String deviceType) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.DEVICE_TYPE, deviceType);
		editor.commit();
	}

	public String getClientType() {
		return DzlcAndroidConfig.API_PARA_CLIENT_TYPE;
	}

	public static String getChannelid() {
		return DzlcAndroidConfig.CHANNEL_ID;
	}

	public void setClientId(String clientId) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.CLIENT_ID, clientId);
		editor.commit();
	}

	public String getClientId() {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		return sp.getString(MainConst.UserConst.CLIENT_ID, "");
	}

	public boolean isLoginOn() {
		if (getUserId() == null || "".equals(getUserId()))
			return false;
		return true;

	}

	/**
	 * 清除用户的信息
	 */
	public void userLogout() {
		SharedPreferences appReadablePreferences = context
				.getSharedPreferences(MainConst.UserConst.USER_FILE_NAME,
						PreferenceActivity.MODE_PRIVATE);
		Editor edit = appReadablePreferences.edit();

		edit.remove(MainConst.UserConst.BIND);
		edit.remove(MainConst.UserConst.CLIENT_ID);
		edit.remove(MainConst.UserConst.LOGIN_BY_ISMI);
		edit.remove(MainConst.UserConst.NICKNAME);
		edit.remove(MainConst.UserConst.PASSWORD);
		edit.remove(MainConst.UserConst.PHONE_STATU);
		edit.remove(MainConst.UserConst.PHONENUM);
		edit.remove(MainConst.UserConst.USER_ID);
		edit.remove(MainConst.UserConst.USER_NAME);
		edit.remove(MainConst.UserConst.DEVICE_ID);
		edit.remove(MainConst.UserConst.DEVICE_TYPE);
		edit.commit();
	}

	/**
	 * 用户登录成功后
	 * 
	 * @param logininByImsiid
	 * @param userInfo
	 */
	public void userLoginSuccess(Boolean logininByImsiid,
			UserAccountInfo userInfo) {
		SharedPreferences appReadablePreferences = context
				.getSharedPreferences(MainConst.UserConst.USER_FILE_NAME,
						PreferenceActivity.MODE_PRIVATE);
		Editor edit = appReadablePreferences.edit();
		if (logininByImsiid)
			edit.putString(MainConst.UserConst.LOGIN_BY_ISMI, "1");
		else
			edit.putString(MainConst.UserConst.LOGIN_BY_ISMI, "0");
		if (userInfo != null) {
			edit.putString(MainConst.UserConst.NICKNAME, userInfo.getNickname());
			edit.putString(MainConst.UserConst.PHONENUM, userInfo.getUsername());
			edit.putString(MainConst.UserConst.TOKEN, userInfo.getToken());
			edit.putString(MainConst.UserConst.USER_ID, userInfo.getUid());
			edit.putString(MainConst.UserConst.USER_NAME,
					userInfo.getUsername());
		} else {
			edit.putString(MainConst.UserConst.NICKNAME, "");
			edit.putString(MainConst.UserConst.PHONENUM, "");
			edit.putString(MainConst.UserConst.TOKEN, "");
			edit.putString(MainConst.UserConst.USER_ID, "");
			edit.putString(MainConst.UserConst.USER_NAME, "");
		}

		edit.commit();
	}

	/***
	 * 验证用户有没有登录
	 * 
	 * @param headerMap
	 * @throws ServiceException
	 */
	public void validationUser(Map<String, String> headerMap)
			throws ServiceException {
		if (getUserName() == null || "".equals(getUserName()))
			throw new ServiceException(ErrorCodeConst.CODE_ACTION_NOT_LOGIN,
					"当前用户没有登录，请先登录!");
	}

	public void setPhoneStatu(String phoneStatu) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.PHONE_STATU, phoneStatu);
		editor.commit();
	}

	public void setBind(String bind) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.BIND, bind);
		editor.commit();
	}

	public String getPhoneStatu() {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		return sp.getString(MainConst.UserConst.PHONE_STATU, "0");
	}

	public String getBind() {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		return sp.getString(MainConst.UserConst.BIND, "0");
	}

	public void setXmlVersion(String version) {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(MainConst.UserConst.VERSION, version);
		editor.commit();
	}

	public String getXmlVersion() {
		SharedPreferences sp = context.getSharedPreferences(
				MainConst.UserConst.USER_FILE_NAME,
				PreferenceActivity.MODE_PRIVATE);
		return sp.getString(MainConst.UserConst.VERSION, "");
	}

}
