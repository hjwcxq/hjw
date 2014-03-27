package com.tv189.dzlc.adapter.exception;

import mobi.dreambox.frameowrk.core.exception.DboxFrameworkException;

public class AndroidClientBaseException extends DboxFrameworkException {
	private static final long serialVersionUID = 1L;
	
	protected AndroidClientBaseException(String errCode, String errMsg) {
		super(errCode, errMsg);
	}
	
	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}
}
