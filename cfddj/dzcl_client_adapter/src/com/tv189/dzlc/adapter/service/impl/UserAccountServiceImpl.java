package com.tv189.dzlc.adapter.service.impl;

import java.util.Map;

import mobi.dreambox.frameowrk.core.http.DboxHTTPClientException;

import org.apache.http.conn.ConnectTimeoutException;

import android.content.Context;
import android.os.Build;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.exception.ErrorCodeConst;
import com.tv189.dzlc.adapter.factory.ClientLogFactory;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.common.TokenException;
import com.tv189.dzlc.adapter.po.log.OpenPartActionLog;
import com.tv189.dzlc.adapter.po.log.UserAccountActionLog;
import com.tv189.dzlc.adapter.po.order.AlipayOrderDetailInfo;
import com.tv189.dzlc.adapter.po.user.AuthorizationInfo;
import com.tv189.dzlc.adapter.po.user.UserAccountInfo;
import com.tv189.dzlc.adapter.service.abstractclass.AbstractDzlcService;
import com.tv189.dzlc.adapter.service.inerface.IUserAccountService;
import com.tv189.dzlc.adapter.util.Utils;

public class UserAccountServiceImpl extends AbstractDzlcService implements
		IUserAccountService {

	public UserAccountServiceImpl(Context context) {
		super(context);
	}

	/**
	 * 根据用户名密码登录
	 * 
	 * @throws ServiceException
	 * @throws DboxHTTPClientException
	 */
	@Override
	public UserAccountInfo userLogin(String username, String userpass)
			throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(false);
		headerMap.put("username", username);
		headerMap.put("userpass", userpass);
		UserAccountInfo userInfo = null;
		UserAccountInfoApiResponse respAccountObj = null;
		UserAccountActionLog log = new UserAccountActionLog(context,
				UserAccountActionLog.ACTION_LOGIN);
		log.setActionDesc("根据用户名密码登录");
		try {
			respAccountObj = (UserAccountInfoApiResponse) callPostApiV3(
					DzlcAndroidConfig.LOGIN_GENEREL_URL, headerMap,
					UserAccountInfoApiResponse.class);
		} catch (ServiceException e) {
			throw e;
		}
		if (respAccountObj != null) {
			if ("314".equals(respAccountObj.getCode()))
				throw new ServiceException(
						ErrorCodeConst.CODE_LOGIN_PASSWORD_ERROR, "密码错误");
			if ("313".equals(respAccountObj.getCode())) {
				throw new ServiceException(
						ErrorCodeConst.CODE_LOGIN_PASSWORD_ERROR, "密码错误");
			}

			userInfo = respAccountObj.getData();
			log.setActionResult("0");
			if (userInfo == null || userInfo.getUsername() == null) {
				AppSetting.getInstance(context).userLoginSuccess(false, null);
				throw new ServiceException(ErrorCodeConst.CODE_LOGIN_FAIL,
						"登录失败，请重试!");
			}
			AppSetting.getInstance(context).userLoginSuccess(false, userInfo);
			// 获取token
			if (userInfo.getToken() != null)
				AppSetting.getInstance(context).setToken(userInfo.getToken());
		} else {
			throw new ServiceException(ErrorCodeConst.CODE_LOGIN_FAIL,
					"登录失败，请重试!");
		}
		ClientLogFactory.getInstance().addLog(log);
		return userInfo;
	}

	/**
	 * 通过imsiid登录
	 */
	@Override
	public UserAccountInfo userLogin(String imsiid) throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(false);
		UserAccountInfo userInfo = null;
		headerMap.put("imsiid", imsiid);
		headerMap.put("terminalType", Build.DEVICE);
		headerMap.put("resolution", Utils.getWindowWH(context));
		Object respAccountObj = null;
		UserAccountActionLog log = new UserAccountActionLog(context,
				UserAccountActionLog.ACTION_LOGIN);
		log.setActionDesc("通过imsiid登录");
		try {
			respAccountObj = callPostApi(DzlcAndroidConfig.lOGIN_DEVICE_URI,
					headerMap, UserAccountInfo.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
			throw e;
		}
		if (respAccountObj != null) {
			userInfo = (UserAccountInfo) respAccountObj;
			AppSetting.getInstance(super.context).setToken(userInfo.getToken());
			if (userInfo != null && userInfo.getUsername() != null
					&& !"".equalsIgnoreCase(userInfo.getUsername())) {
				AppSetting.getInstance(super.context).userLoginSuccess(true,
						userInfo);
			} else {
				userInfo = null;
			}
			log.setActionResult("0");
		} else {
			log.setActionResult("1");
		}
		ClientLogFactory.getInstance().addLog(log);
		return userInfo;
	}

	/**
	 * 更改密码
	 */
	@Override
	public Boolean updatePass(String newpass, String oldpass)
			throws ServiceException {
		UserAccountInfo userInfo = null;
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		headerMap.put("newpass", newpass);
		headerMap.put("oldpass", oldpass);
		// 验证用户有没有的登录
		AppSetting.getInstance(super.context).validationUser(headerMap);
		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_USER_PROFILE);
		log.setActionDesc("更改用户密码");
		Object respAccountObj = null;
		try {
			respAccountObj = callPostApi(DzlcAndroidConfig.UPDATE_USER_PASS,
					headerMap, UserAccountInfo.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
			throw e;
		}
		if (respAccountObj != null)
			log.setActionResult("0");
		userInfo = (UserAccountInfo) respAccountObj;
		ClientLogFactory.getInstance().addLog(log);
		if (userInfo != null && userInfo.getCode() != null
				&& userInfo.getCode().equals("00")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 更改用户昵称
	 */
	@Override
	public Boolean updateNickname(String nickname) throws ServiceException {
		UserAccountInfo userInfo = null;
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		headerMap.put("nickname", nickname);
		if (!headerMap.containsKey("username")
				|| headerMap.get("username") == null
				|| headerMap.get("username").equals(""))
			throw new ServiceException(ErrorCodeConst.CODE_ACTION_NOT_LOGIN,
					"当前用户没有登录，请先登录!");
		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_USER_PROFILE);
		log.setActionDesc("修改用户昵称");
		UserAccountInfo respAccountObj = null;
		try {
			respAccountObj = (UserAccountInfo) callPostApi(
					DzlcAndroidConfig.UPDATE_USER_NICKNAME, headerMap,
					UserAccountInfo.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
		}
		;
		if (respAccountObj != null)
			log.setActionResult("0");
		ClientLogFactory.getInstance().addLog(log);
		userInfo = respAccountObj;
		if (userInfo != null && userInfo.getCode() != null
				&& userInfo.getCode().equals("00")) {
			AppSetting.getInstance(context).setNickName(nickname);
			return true;
		} else {
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return false;
		}
	}

	/**
	 * 用户注册
	 */
	@Override
	public UserAccountInfo register(String username, String userpass)
			throws ServiceException {
		UserAccountInfo userInfo = null;
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(false);
		headerMap.put("username", username);
		headerMap.put("userpass", userpass);
		UserAccountActionLog log = new UserAccountActionLog(context,
				UserAccountActionLog.ACTION_RIGISTER);
		log.setActionDesc("用户注册");
		Object respAccountObj = null;
		try {
			respAccountObj = callPostApi(DzlcAndroidConfig.REGISTER, headerMap,
					UserAccountInfo.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
			throw e;
		}
		if (respAccountObj != null) {
			userInfo = (UserAccountInfo) respAccountObj;
			if ("820".equalsIgnoreCase(userInfo.getCode())) {// 账号已经存在
				throw new ServiceException(
						ErrorCodeConst.CODE_EMAIL_REPEAT_REG,
						"该邮箱账号已经注册了，请不要重复注册");
			}
			log.setActionResult("0");
		} else {
			log.setActionResult("1");
		}
		ClientLogFactory.getInstance().addLog(log);
		return userInfo;
	}

	/**
	 * 一键注册
	 */
	@Override
	public String registerOne(String code) throws ServiceException {
		boolean resp = false;
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(false);
		headerMap.put("code", code);
		UserAccountActionLog log = new UserAccountActionLog(context,
				UserAccountActionLog.ACTION_RIGISTER);
		log.setActionDesc("一键注册");
		String respAccountObj = null;
		try {
			respAccountObj = (String) callPostApi(
					DzlcAndroidConfig.REGISTER_ONE, headerMap, String.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
			throw e;
		}
		if (respAccountObj != null) {
			log.setActionResult("0");
		} else {
			log.setActionResult("1");
		}
		ClientLogFactory.getInstance().addLog(log);
		return respAccountObj;
	}

	/**
	 * 退出应用程序
	 */
	public void quits() throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(false);
		UserAccountActionLog log = new UserAccountActionLog(super.context,
				UserAccountActionLog.ACTION_LOGOUT);
		log.setActionDesc("退出应用程序");
		log.setActionResult("0");
		ClientLogFactory.getInstance().addLog(log);
		headerMap.clear();
	}

	/**
	 * 验证是否登录
	 */
	@Override
	public Boolean isLog() throws ServiceException {
		Map<String, String> headMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		if (headMap.get("uid") != null || headMap.get("uid") != "") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取登录用户资料 以及一对一的专家跟排队在第几位
	 * 
	 * @throws DboxHTTPClientException
	 */
	public UserAccountInfo userWaiting(String imsiid, String username,
			String userpass) throws ServiceException, DboxHTTPClientException {
		UserAccountInfo userInfo = null;
		userInfo = this.userLogin(imsiid);
		userInfo.setWaitingcount("4");
		userInfo.setExpert("左安龙");
		if (userInfo.getUid() != null) {
			return userInfo;
		} else {
			return this.userLogin(username, userpass);
		}
	}

}

class AuthorizationInfoApiResponse extends AbstractApiResponse {
	private AuthorizationInfo data;

	public AuthorizationInfo getData() {
		return data;
	}

	public void setData(AuthorizationInfo data) {
		this.data = data;
	}
}

/**
 * 用来收集用户信息
 * 
 * @author Administrator
 * 
 */
class UserAccountInfoApiResponse extends AbstractApiResponse {
	private UserAccountInfo data;

	public UserAccountInfo getData() {
		return data;
	}

	public void setData(UserAccountInfo data) {
		this.data = data;
	}

}
