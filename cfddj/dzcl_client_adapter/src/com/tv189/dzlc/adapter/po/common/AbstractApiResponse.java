package com.tv189.dzlc.adapter.po.common;

public abstract class AbstractApiResponse {
	private String code;
	private String msg;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Boolean isSuccess(){
		return code!=null&&(code.equals("00")||code.equals("0"));
	}
	
}
