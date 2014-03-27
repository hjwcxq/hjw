package com.tv189.dzlc.adapter.service.inerface;

import java.util.List;

import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.repertoire.RepertoireInfo;

public interface RepertoireService {
	ApiResponse repertoireList() throws ServiceException;
	//获取直播节目单
	List<RepertoireInfo> liveSchedule() throws ServiceException;
	//获取节目单
	List<RepertoireInfo> schedule(String date) throws ServiceException;
	//获取节目详情
	RepertoireInfo scheduleInfo(String pcode,String liveid) throws ServiceException;
	//订阅节目
	Boolean prgSubscribe(String prgname) throws ServiceException;
	//取消节目订阅
	Boolean prgunSubscribe(String prgname)throws ServiceException;
}
