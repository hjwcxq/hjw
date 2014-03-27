package com.tv189.dzlc.adapter.service.inerface;

import java.util.List;
 
import com.tv189.dzlc.adapter.po.collection.CollectionList;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;

public interface CollectionService {
	Boolean collectionAdd(String ptype,String pcode,String contentid) throws ServiceException;
	List<CollectionList> collectionList(String pagenum,String pagesize) throws ServiceException;
	ApiResponse collectionDelete(String contentid) throws ServiceException;
}
