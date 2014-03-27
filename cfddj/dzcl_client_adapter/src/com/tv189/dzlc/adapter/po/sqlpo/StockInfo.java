package com.tv189.dzlc.adapter.po.sqlpo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "my_stock")
public class StockInfo {

	// 股票记录
	
	@DatabaseField
	private String id;
	@DatabaseField
	private String keyword;
	@DatabaseField
	private String phoneNum;
	@DatabaseField
	private String removed;
	@DatabaseField
	private String search_count;
	@DatabaseField
	private String search_time;

	// 热门
	@DatabaseField(id = true)
	private String displayCode;
	@DatabaseField
	private String isDelete;
	@DatabaseField
	private String searchNum;
	@DatabaseField
	private String searchTime;
	@DatabaseField
	private String stockCode;
	@DatabaseField
	private String stockId;
	@DatabaseField
	private String stockName;
	@DatabaseField
	private String stockPinyin;

	@DatabaseField
	private String content;// 个性股票属性
	@DatabaseField
	private String imgUrl;
	@DatabaseField
	private String memo;
	@DatabaseField
	private String picLx;
	@DatabaseField
	private String picsl;
	@DatabaseField
	private String title;

	// ==================== 后面添加的
	@DatabaseField
	public String uid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getRemoved() {
		return removed;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}

	public String getSearch_count() {
		return search_count;
	}

	public void setSearch_count(String search_count) {
		this.search_count = search_count;
	}

	public String getSearch_time() {
		return search_time;
	}

	public void setSearch_time(String search_time) {
		this.search_time = search_time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String url;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPicLx() {
		return picLx;
	}

	public void setPicLx(String picLx) {
		this.picLx = picLx;
	}

	public String getPicsl() {
		return picsl;
	}

	public void setPicsl(String picsl) {
		this.picsl = picsl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getSearchNum() {
		return searchNum;
	}

	public void setSearchNum(String searchNum) {
		this.searchNum = searchNum;
	}

	public String getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}

	public String getStockCode() {
		return displayCode != null ? displayCode : stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockPinyin() {
		return stockPinyin;
	}

	public void setStockPinyin(String stockPinyin) {
		this.stockPinyin = stockPinyin;
	}
}
