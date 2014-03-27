package com.tv189.dzlc.adapter.po.order;

import java.util.List;

import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;

public class OrderInfoListApiResponse extends AbstractApiResponse{
	private List<OrderInfo> info;

	public List<OrderInfo> getInfo() {
		return info;
	} 
	public void setInfo(List<OrderInfo> info) {
		this.info = info;
	}
}
