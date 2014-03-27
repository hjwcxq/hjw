package com.tv189.dzlc.adapter.po.video;
  
/**
 * 标清和高清
 * @author Administrator
 *
 */
public class LiveVideoInfo {
	private String highQuality;
	public String getHighQuality() {
		return highQuality;
	}
	public void setHighQuality(String highQuality) {
		this.highQuality = highQuality;
	}
	public String getLowQuality() {
		return lowQuality;
	}
	public void setLowQuality(String lowQuality) {
		this.lowQuality = lowQuality;
	}
	private String lowQuality;
}
