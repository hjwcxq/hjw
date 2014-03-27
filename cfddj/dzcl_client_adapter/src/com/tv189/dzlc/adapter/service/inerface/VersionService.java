package com.tv189.dzlc.adapter.service.inerface;

import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.version.ApplicationInfoContent;
import com.tv189.dzlc.adapter.po.version.VersionInfo;

public interface VersionService {
	
	VersionInfo updateVersion(String currentVersion) throws ServiceException;
	
	ApplicationInfoContent getRecoAppList(String pagenumber,String pagesize) throws ServiceException;
	
	boolean statisticsDownloadCount(String id) throws ServiceException;
	
	ApiResponse getIndexPath(String version) throws ServiceException ;
	
}
