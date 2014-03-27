package com.tv189.dzlc.adapter.po.sqlpo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 专家咨询时间列表
 * 
 * @author Administrator
 */

@DatabaseTable(tableName = "experts")
public class ExpertsInfo {
	@DatabaseField(id = true)
	private String id;
	// 专家uid
	@DatabaseField
	private String uid;
	// 专家名字
	@DatabaseField
	private String expertName;
	// 专家详情
	@DatabaseField
	private String expertDetail;
	// 专家头像url，type=vcs
	@DatabaseField
	private String expert_thumb_url;
	// 专家大图url
	@DatabaseField
	private String expert_bg_url;
	
	
	private String videoChatUrl;
	// 直播连接
	private String fmsLiveStreamUrl;
	// 当前专家排队数
	private int wattingQueueCount;
	// 我在排队中的位数
	private int myQueueIndex;
	// 剩余时间
	private String timeLest;
	// 本次咨询时间
	private String currentChatTime;

	// 本次咨询开始时间
	private String startDate;
	// 本次咨询结束时间
	private String endDate;
	// 是否在线
	private String online;
	// 排序
	private String indexFlag;
	// 最后一位咨询用户id
	private String lastGuestUid;
	// 最后一位咨询用户昵称
	private String lastGuestNickname;
	// 最后一位咨询用户咨询开始时间
	private String lastGuestStartTime;
	// 当前咨询状态
	private String status;// 01在线
	// fmsEventid
	private String fms_eventid;
	// 专家title
	private String expertTitle;

	public String getExpert_thumb_url() {
		return expert_thumb_url;
	}

	public void setExpert_thumb_url(String expert_thumb_url) {
		this.expert_thumb_url = expert_thumb_url;
	}

	public String getExpert_bg_url() {
		return expert_bg_url;
	}

	public void setExpert_bg_url(String expert_bg_url) {
		this.expert_bg_url = expert_bg_url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpertTitle() {
		return expertTitle;
	}

	public void setExpertTitle(String expertTitle) {
		this.expertTitle = expertTitle;
	}

	public String getExpertDetail() {
		return expertDetail;
	}

	public void setExpertDetail(String expertDetail) {
		this.expertDetail = expertDetail;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getIndexFlag() {
		return indexFlag;
	}

	public void setIndexFlag(String indexFlag) {
		this.indexFlag = indexFlag;
	}

	/**
	 * @return the lastGuestUid
	 */
	public String getLastGuestUid() {
		return lastGuestUid;
	}

	/**
	 * @param lastGuestUid
	 *            the lastGuestUid to set
	 */
	public void setLastGuestUid(String lastGuestUid) {
		this.lastGuestUid = lastGuestUid;
	}

	public String getLastGuestNickname() {
		return lastGuestNickname;
	}

	public void setLastGuestNickname(String lastGuestNickname) {
		this.lastGuestNickname = lastGuestNickname;
	}

	public String getLastGuestStartTime() {
		return lastGuestStartTime;
	}

	public void setLastGuestStartTime(String lastGuestStartTime) {
		this.lastGuestStartTime = lastGuestStartTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFms_eventid() {
		return fms_eventid;
	}

	public void setFms_eventid(String fms_eventid) {
		this.fms_eventid = fms_eventid;
	}

	public String getVideoChatUrl() {
		return videoChatUrl;
	}

	public void setVideoChatUrl(String videoChatUrl) {
		this.videoChatUrl = videoChatUrl;
	}

	/**
	 * @return the fmsLiveStreamUrl
	 */
	public String getFmsLiveStreamUrl() {
		return fmsLiveStreamUrl;
	}

	/**
	 * @param fmsLiveStreamUrl
	 *            the fmsLiveStreamUrl to set
	 */
	public void setFmsLiveStreamUrl(String fmsLiveStreamUrl) {
		this.fmsLiveStreamUrl = fmsLiveStreamUrl;
	}

	/**
	 * @return the wattingQueueCount
	 */
	public int getWattingQueueCount() {
		return wattingQueueCount;
	}

	/**
	 * @param wattingQueueCount
	 *            the wattingQueueCount to set
	 */
	public void setWattingQueueCount(int wattingQueueCount) {
		this.wattingQueueCount = wattingQueueCount;
	}

	/**
	 * @return the myQueueIndex
	 */
	public int getMyQueueIndex() {
		return myQueueIndex;
	}

	/**
	 * @param myQueueIndex
	 *            the myQueueIndex to set
	 */
	public void setMyQueueIndex(int myQueueIndex) {
		this.myQueueIndex = myQueueIndex;
	}

	/**
	 * @return the timeLest
	 */
	public String getTimeLest() {
		return timeLest;
	}

	/**
	 * @param timeLest
	 *            the timeLest to set
	 */
	public void setTimeLest(String timeLest) {
		this.timeLest = timeLest;
	}

	/**
	 * @return the currentChatTime
	 */
	public String getCurrentChatTime() {
		return currentChatTime;
	}

	/**
	 * @param currentChatTime
	 *            the currentChatTime to set
	 */
	public void setCurrentChatTime(String currentChatTime) {
		this.currentChatTime = currentChatTime;
	}

}
