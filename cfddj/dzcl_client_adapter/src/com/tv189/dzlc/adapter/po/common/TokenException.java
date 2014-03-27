package com.tv189.dzlc.adapter.po.common;

import com.tv189.dzlc.adapter.exception.AndroidClientBaseException;

public class TokenException extends AndroidClientBaseException {
	
	private static final long serialVersionUID = 1L;
	
	
	public static final String ERROR_CODE_TOKEN_DISABLE = "Token失效";
	
	
	public TokenException(String errCode, String errMsg) {
		super(errCode, errMsg);
		// TODO Auto-generated constructor stub
	}
}
