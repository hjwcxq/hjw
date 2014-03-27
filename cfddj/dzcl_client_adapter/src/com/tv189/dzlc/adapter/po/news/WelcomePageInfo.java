package com.tv189.dzlc.adapter.po.news;

import java.util.Date;
/**
 * 首页欢迎页面图片展示
 * @author pengcong
 *
 */
public class WelcomePageInfo {
	private int id;
	private String channelid;
	private String androidx;
	private String androidz;
	private String androidd;
	private String iphonex;
	private String iphonez;
	private String iphoned;
	private String insertuser;
	private String updateuser;
	private String insertdate;
	private String title;
	private String logo;//01发布  00未发布
	private int look;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public int getLook() {
		return look;
	}
	public void setLook(int look) {
		this.look = look;
	}
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
	public String getAndroidx() {
		return androidx;
	}
	public void setAndroidx(String androidx) {
		this.androidx = androidx;
	}
	public String getAndroidz() {
		return androidz;
	}
	public void setAndroidz(String androidz) {
		this.androidz = androidz;
	}
	public String getAndroidd() {
		return androidd;
	}
	public void setAndroidd(String androidd) {
		this.androidd = androidd;
	}
	public String getIphonex() {
		return iphonex;
	}
	public void setIphonex(String iphonex) {
		this.iphonex = iphonex;
	}
	public String getIphonez() {
		return iphonez;
	}
	public void setIphonez(String iphonez) {
		this.iphonez = iphonez;
	}
	public String getIphoned() {
		return iphoned;
	}
	public void setIphoned(String iphoned) {
		this.iphoned = iphoned;
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
