package com.tv189.dzlc.adapter.po.question;

import java.util.List;

public class AnswerInfo {
	private String id;
	private String questionId;
	private String replyContent;
	private String Date;
	private String replyUserName;
	private String replyUserId;
	private String authTag;
	private String displayName;
	private List<ReplaceKeywordInfo> replaceList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	public String getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getAuthTag() {
		return authTag;
	}
	public void setAuthTag(String authTag) {
		this.authTag = authTag;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the replaceList
	 */
	public List<ReplaceKeywordInfo> getReplaceList() {
		return replaceList;
	}
	/**
	 * @param replaceList the replaceList to set
	 */
	public void setReplaceList(List<ReplaceKeywordInfo> replaceList) {
		this.replaceList = replaceList;
	} 
	
	
}
