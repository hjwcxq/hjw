package com.tv189.dzlc.adapter.service.inerface;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.news.LiveRollingInfo;
import com.tv189.dzlc.adapter.po.news.NewsInfo;
import com.tv189.dzlc.adapter.po.news.WelcomePageInfo;

public interface NewsService {
		NewsInfo getNews(int id) throws ServiceException;
		Object regPushClient(String nickName,String deviceId,String deviceType,String phonenumm,String channelid,String getuiDeviceId) throws ServiceException;
		LiveRollingInfo getRollingTitleInfo() throws ServiceException;
		WelcomePageInfo getWelcomePageInfo() throws ServiceException ;
}
