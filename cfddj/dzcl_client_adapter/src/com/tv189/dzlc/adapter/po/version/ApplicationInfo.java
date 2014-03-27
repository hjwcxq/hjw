package com.tv189.dzlc.adapter.po.version;



/**
 * 存放推荐应用对象
 * @author pengcong
 *
 */
public class ApplicationInfo {
	private int id;
	private int logo;
	private String applicationname;
	private String applicationsize;
	private String applicationapk;
	private String insertdate;
	private String picone;
	private String pictwo;
	private String picthere;
	private String picfour;
	private String picfive;
	private String applicationdesc;
	private int downloads ;
	private String applicationapkurl ;
	private String packagename;
	
	
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public String getApplicationapkurl() {
		return applicationapkurl;
	}
	public void setApplicationapkurl(String applicationapkurl) {
		this.applicationapkurl = applicationapkurl;
	}
	public String getApplicationdesc() {
		return applicationdesc;
	}
	public void setApplicationdesc(String applicationdesc) {
		this.applicationdesc = applicationdesc;
	}
	public String getPicone() {
		return picone;
	}
	public void setPicone(String picone) {
		this.picone = picone;
	}
	public String getPictwo() {
		return pictwo;
	}
	public void setPictwo(String pictwo) {
		this.pictwo = pictwo;
	}
	public String getPicthere() {
		return picthere;
	}
	public void setPicthere(String picthere) {
		this.picthere = picthere;
	}
	public String getPicfour() {
		return picfour;
	}
	public void setPicfour(String picfour) {
		this.picfour = picfour;
	}
	public String getPicfive() {
		return picfive;
	}
	public void setPicfive(String picfive) {
		this.picfive = picfive;
	}
	public String getInsertdate() {
		return insertdate;
	}
	public void setInsertdate(String insertdate) {
		this.insertdate = insertdate;
	}
	public int getDownloads() {
		return downloads;
	}
	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLogo() {
		return logo;
	}
	public void setLogo(int logo) {
		this.logo = logo;
	}
	public String getApplicationname() {
		return applicationname;
	}
	public void setApplicationname(String applicationname) {
		this.applicationname = applicationname;
	}
	public String getApplicationsize() {
		return applicationsize;
	}
	public void setApplicationsize(String applicationsize) {
		this.applicationsize = applicationsize;
	}
	public String getApplicationapk() {
		return applicationapk;
	}
	public void setApplicationapk(String applicationapk) {
		this.applicationapk = applicationapk;
	}
}
