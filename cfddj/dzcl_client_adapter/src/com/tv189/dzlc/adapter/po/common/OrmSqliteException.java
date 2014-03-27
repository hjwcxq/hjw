package com.tv189.dzlc.adapter.po.common;

import com.tv189.dzlc.adapter.exception.AndroidClientBaseException;

public class OrmSqliteException extends AndroidClientBaseException{
	
	private static final long serialVersionUID = 1L;
	
	public static final String ERROR_ADD_CODE_EXCEPTION = "3000";
	
	public static final String ERROR_DELETE_CODE_EXCEPTION = "3001";
	
	public static final String ERROR_UPDATE_CODE_EXCEPTION = "3002";
	
	public static final String ERROR_QUERY_CODE_EXCEPTION = "3003";
	
	

	public OrmSqliteException(String errCode, String errMsg) {
		super(errCode, errMsg);
		// TODO Auto-generated constructor stub
	}

}
