package com.tv189.dzlc.adapter.po.news;

import java.util.Date;

/**
 * 处理直播滚动条数据
 * @author pengcong
 *
 */
public class LiveRollingInfo {
	private int id;
	private String channelid;
	private String content;
	private String insertuser;
	private String updateuser;
	private String insertdate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChannelid() {
		return channelid;
	}
	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInsertuser() {
		return insertuser;
	}
	public void setInsertuser(String insertuser) {
		this.insertuser = insertuser;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	
	public String getInsertdate() {
		return insertdate;
	}
	public void setInsertdate(String insertdate) {
		this.insertdate = insertdate;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	private String updatedate;
}
