package com.tv189.dzlc.adapter.po.sqlpo;

import com.j256.ormlite.field.DatabaseField;

public class ItemNodeInfo {
	
	@DatabaseField(id = true)
	private int id;  
	@DatabaseField
	private int index;   //对应的模块的
	@DatabaseField
	private boolean isShow;   
	@DatabaseField
	private int width;
	@DatabaseField
	private int height;
	@DatabaseField
	private String color ; // 背景色
	@DatabaseField
	private String bgImageSrc;
	@DatabaseField
	private String bgAlt;
	@DatabaseField
	private String catalog;
	@DatabaseField
	private String desc;
	@DatabaseField
	private String iconUrl;
	@DatabaseField
	private String moduleId;
	@DatabaseField
	private String title;
	@DatabaseField
	private String type;
	@DatabaseField
	private String actionType ;
	@DatabaseField
	private String actionUrl;
	@DatabaseField
	private String paramList ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBgImageSrc() {
		return bgImageSrc;
	}

	public void setBgImageSrc(String bgImageSrc) {
		this.bgImageSrc = bgImageSrc;
	}

	public String getBgAlt() {
		return bgAlt;
	}

	public void setBgAlt(String bgAlt) {
		this.bgAlt = bgAlt;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getParamList() {
		return paramList;
	}

	public void setParamList(String paramList) {
		this.paramList = paramList;
	}

	
}
