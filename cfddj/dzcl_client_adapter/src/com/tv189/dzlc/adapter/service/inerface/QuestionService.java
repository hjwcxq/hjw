package com.tv189.dzlc.adapter.service.inerface;

import java.io.File;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.question.QuestionInfo;
import com.tv189.dzlc.adapter.po.question.QuestionPageContent;

public interface QuestionService {
		QuestionPageContent	 questionMy(String deviceid,String pagenumber ,String pagesize) throws ServiceException;
		QuestionPageContent questionList(String prgId,String pagenumber,String pagesize) throws ServiceException;
		QuestionInfo questionAdd(String commentType,String deviceType,String questionContent) throws ServiceException;
//		QuestionInfo questionAdd(String deviceType,String questionContent) throws GplzServiceException;
		QuestionInfo upload(File file)throws ServiceException;
}
