package com.tv189.dzlc.adapter.po.http;

import java.util.Map;


public class DzlcRequest {
	public static enum METHOD_TYPE{
		GET,POST,PUT,DELETE
	}
	private String url;
	private METHOD_TYPE methodType;
	private Map<String,String> headerMap;
	private int connectTimeOut;
	private int readTimeOut;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public METHOD_TYPE getMethodType() {
		return methodType;
	}
	public void setMethodType(METHOD_TYPE methodType) {
		this.methodType = methodType;
	}
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}
	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}
	public int getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public int getReadTimeOut() {
		return readTimeOut;
	}
	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}
}
