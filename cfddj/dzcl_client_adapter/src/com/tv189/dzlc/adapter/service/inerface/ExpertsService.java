package com.tv189.dzlc.adapter.service.inerface;

import java.util.List;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.ExpertsInfo;
import com.tv189.dzlc.adapter.po.sqlpo.VideoChatInfo;

public interface ExpertsService {
	//获取一对一专家列表
	List<ExpertsInfo> expertsList() throws ServiceException;
	List<VideoChatInfo> myAllQuestions(String userUid,String chatDate) throws ServiceException;
	//获取专家一对一详情
	ExpertsInfo expertsDetails (String userUid,String expertId) throws ServiceException;
	//向专家发出一对一请求
	ExpertsInfo requestvideochat (String userUid,String expertId) throws ServiceException;
	//接收页面返回的留言数据 存入到数据库
	Boolean addExperteMessage(VideoChatInfo info) throws ServiceException;
	List<VideoChatInfo> searchByexpert(String expertUid,String userUid,String pagesize,String pagenum,String chatDate,String isAuditPass) throws ServiceException;
	
	List<VideoChatInfo> expertRepeatChatInfos(String expertUid) throws ServiceException;

}
