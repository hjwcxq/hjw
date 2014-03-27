/***
* class:IDzcjAction.java
* create:cavenshen
* time:3:36:43 PM
*
*/


package com.tv189.dzlc.adapter.po.base;

import java.util.Map;

/**
 * @author cavenshen
 *
 */
public abstract class AbstractAction {
	protected String type;
	protected String url;
	protected Map<String,String> paraMap;
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the paraMap
	 */
	public Map<String, String> getParaMap() {
		return paraMap;
	}
	/**
	 * @param paraMap the paraMap to set
	 */
	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}
	
	public abstract void jumpByActionType(Map<String, String> paraMap);
	
	
}
