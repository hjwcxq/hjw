/***
 * class:VideoChatInfo.java
 * create:cavenshen
 * time:4:19:52 PM
 *
 */

package com.tv189.dzlc.adapter.po.sqlpo;

import java.io.File;
import java.util.Date;

/**
 * @author cavenshen
 * 
 */
public class VideoChatInfo {
	public String getChatDate() {
		return chatDate;
	}

	public void setChatDate(String chatDate) {
		this.chatDate = chatDate;
	}

	// 专家
	public static final String TYPE_EXPERT_SAY = "00";
	// 用户
	public static final String TYPE_USER_SAY = "01";

	public static final String MSG_TYPE_STRING = "00";
	public static final String MSG_TYPE_SOUNT = "01";
	public File file;

	/**
	 * @return the upfile
	 */
	public File getfile() {
		return file;
	}

	/**
	 * @param upfile
	 *            the upfile to set
	 */
	public void setfile(File file) {
		this.file = file;
	}

	private long id;
	// 专家id
	private String expertUid;
	// 用户Id
	private String userUid;
	// 专家名字
	private String expertName;
	// 用户昵称
	private String userNickname;
	// 用户电话
	private String userPhonenum;
	// 留言内容、或者录音 地址
	private String content;
	// 留言日期
	private String chatDate;
	// 是谁说的
	private String type;
	// 留言的类型 可以是语音01 文字00
	private String msgType;
	// 审核是否通过
	private String isAuditPass;

	public String getExpertUid() {
		return expertUid;
	}

	public void setExpertUid(String expertUid) {
		this.expertUid = expertUid;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getUserNickname() {
		return userNickname != null ? userNickname : userUid;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserPhonenum() {
		return userPhonenum;
	}

	public void setUserPhonenum(String userPhonenum) {
		this.userPhonenum = userPhonenum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIsAuditPass() {
		return isAuditPass;
	}

	public void setIsAuditPass(String isAuditPass) {
		this.isAuditPass = isAuditPass;
	}
}
