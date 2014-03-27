package com.tv189.dzlc.adapter.po.common;

import com.tv189.dzlc.adapter.exception.AndroidClientBaseException;

public class ServiceException extends AndroidClientBaseException {
	private static final long serialVersionUID = 1L;
	
	
	public ServiceException(String errCode, String errMsg) {
		super(errCode, errMsg);
		// TODO Auto-generated constructor stub
	}
}
