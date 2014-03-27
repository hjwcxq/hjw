package com.tv189.dzlc.adapter.po.sqlpo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;

/**
 * 视频详情 主要是缓存我的下载列表
 */

@DatabaseTable(tableName = "my_download")
public class VideoDetails extends AbstractApiResponse {
	@DatabaseField
	private String id;
	@DatabaseField
	private String channelId;
	@DatabaseField
	private String eachPrice;
	@DatabaseField
	private String eachVipCode;
	@DatabaseField
	private String detail;
	@DatabaseField
	private String length;
	@DatabaseField
	private String marketPrice;
	@DatabaseField
	private String monthPrice;
	@DatabaseField
	private String monthPriceName;
	@DatabaseField
	private String monthVipCode;
	@DatabaseField
	private String onlyppv;
	@DatabaseField
	private String payLimit;
	@DatabaseField
	private String playLimit;
	@DatabaseField
	private String playUrl;
	@DatabaseField
	private String ppvpriority;
	@DatabaseField
	private String quality;
	@DatabaseField
	private String sign;
	@DatabaseField
	private String tags;
	@DatabaseField
	private String title;
	@DatabaseField
	private String userName;
	@DatabaseField(id = true)
	private String vid;

	// ==================== 后面添加的
	@DatabaseField
	public String uid;


	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getEachPrice() {
		return eachPrice;
	}

	public void setEachPrice(String eachPrice) {
		this.eachPrice = eachPrice;
	}

	public String getEachVipCode() {
		return eachVipCode;
	}

	public void setEachVipCode(String eachVipCode) {
		this.eachVipCode = eachVipCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getMonthPrice() {
		return monthPrice;
	}

	public void setMonthPrice(String monthPrice) {
		this.monthPrice = monthPrice;
	}

	public String getMonthPriceName() {
		return monthPriceName;
	}

	public void setMonthPriceName(String monthPriceName) {
		this.monthPriceName = monthPriceName;
	}

	public String getMonthVipCode() {
		return monthVipCode;
	}

	public void setMonthVipCode(String monthVipCode) {
		this.monthVipCode = monthVipCode;
	}

	public String getOnlyppv() {
		return onlyppv;
	}

	public void setOnlyppv(String onlyppv) {
		this.onlyppv = onlyppv;
	}

	public String getPayLimit() {
		return payLimit;
	}

	public void setPayLimit(String payLimit) {
		this.payLimit = payLimit;
	}

	public String getPlayLimit() {
		return playLimit;
	}

	public void setPlayLimit(String playLimit) {
		this.playLimit = playLimit;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public String getPpvpriority() {
		return ppvpriority;
	}

	public void setPpvpriority(String ppvpriority) {
		this.ppvpriority = ppvpriority;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
