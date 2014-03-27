package com.tv189.dzlc.adapter.po.question;

import java.util.ArrayList;

/**
 * 留言
 * @author Administrator
 *
 */
public class QuestionInfo {
	private String contact;
	private String date;
	private String commentType  ;//留言类型
	private String deviceId;
	private String deviceType;
	private String displayName;
	private String displayName2;
	private String id;
	private String isOnline;
	private String isPass;
	private String isTop;
	private String isVip;
	private String orderIndex;
	private String outOfLength;
	private String prgId;
	private String questionContent;
	private String refuseMemo;
	private String userId;
	private ArrayList<AnswerInfo> replyList;//用户名    
	 
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCommentType() {
		return commentType;
	}
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDisplayName2() {
		return displayName2;
	}
	public void setDisplayName2(String displayName2) {
		this.displayName2 = displayName2;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public String getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getOutOfLength() {
		return outOfLength;
	}
	public void setOutOfLength(String outOfLength) {
		this.outOfLength = outOfLength;
	}
	public String getPrgId() {
		return prgId;
	}
	public void setPrgId(String prgId) {
		this.prgId = prgId;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public String getRefuseMemo() {
		return refuseMemo;
	}
	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ArrayList<AnswerInfo> getReplyList() {
		return replyList;
	}
	public void setReplyList(ArrayList<AnswerInfo> replyList) {
		this.replyList = replyList;
	}
	
	 
	
}
