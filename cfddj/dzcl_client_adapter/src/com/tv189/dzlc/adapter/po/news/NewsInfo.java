package com.tv189.dzlc.adapter.po.news;


/**
 * 新闻
 * 
 * @author pengcong
 * 
 */
public class NewsInfo {
	// 新闻标题
	private String newsTitle;
	// 新闻图片
	private String newsFilePicture;

	private String inertDate;
	// 新闻简介
	private String newsDescribe;

	private String detailUrl;
	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsFilePicture() {
		return newsFilePicture;
	}

	public void setNewsFilePicture(String newsFilePicture) {
		this.newsFilePicture = newsFilePicture;
	}


	
	public String getInertDate() {
		return inertDate;
	}

	public void setInertDate(String inertDate) {
		this.inertDate = inertDate;
	}

	public String getNewsDescribe() {
		return newsDescribe;
	}

	public void setNewsDescribe(String newsDescribe) {
		this.newsDescribe = newsDescribe;
	}

	/**
	 * @return the detailUrl
	 */
	public String getDetailUrl() {
		return detailUrl;
	}

	/**
	 * @param detailUrl the detailUrl to set
	 */
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	
}
