package com.tv189.dzlc.adapter.service.inerface;

import mobi.dreambox.frameowrk.core.http.DboxHTTPClientException;

import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.common.TokenException;
import com.tv189.dzlc.adapter.po.order.AlipayOrderDetailInfo;
import com.tv189.dzlc.adapter.po.user.UserAccountInfo;

public interface IUserAccountService {
	UserAccountInfo userLogin(String username, String userpass)
			throws ServiceException, DboxHTTPClientException;

	UserAccountInfo userLogin(String imsiid) throws ServiceException;

	Boolean updatePass(String newpass, String oldpass) throws ServiceException;

	Boolean updateNickname(String nickname) throws ServiceException;

	UserAccountInfo register(String username, String userpass)
			throws ServiceException;

	// 退出应用程序
	void quits() throws ServiceException;

	// 判断是不是登录
	Boolean isLog() throws ServiceException;

	// 卡号激活
	// ApiResponse cardActive(String productId,String carPass,String carNum)
	// throws ServiceException;
	// 专家一对一用户信息 及其排队
	UserAccountInfo userWaiting(String imsiid, String username, String userpass)
			throws ServiceException, DboxHTTPClientException;

	// 一键注册
	String registerOne(String code) throws ServiceException;
}
