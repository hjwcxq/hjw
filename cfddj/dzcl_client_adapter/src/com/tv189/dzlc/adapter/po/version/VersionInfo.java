package com.tv189.dzlc.adapter.po.version;

/**
 * 自动更新
 * @author PC
 *
 */
public class VersionInfo {
	private String appContent;
	private String appPath;
	private String date;
	private String deviceType;
	private String id;
	private String upgradFlag;
	private String version;
	public String getAppContent() {
		return appContent;
	}
	public void setAppContent(String appContent) {
		this.appContent = appContent;
	}
	public String getAppPath() {
		return appPath;
	}
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUpgradFlag() {
		return upgradFlag;
	}
	public void setUpgradFlag(String upgradFlag) {
		this.upgradFlag = upgradFlag;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
