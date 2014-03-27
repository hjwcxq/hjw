package com.tv189.dzlc.adapter.service.inerface;

import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.order.OrderInfo;
import com.tv189.dzlc.adapter.po.order.OrderInfoListApiResponse;

public interface OrderService {
	OrderInfoListApiResponse myOrderList() throws ServiceException;
	OrderInfo orderDelete(String subid) throws ServiceException;
	//订购
	ApiResponse subscribe() throws ServiceException; 
	 
}
