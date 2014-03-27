package com.tv189.dzlc.adapter.po.stock;

import java.util.List;

import com.tv189.dzlc.adapter.po.sqlpo.StockVideo;

public class StockVideoPageContent { 
	private String firstPage;
	private String lastPage;
	private String number;
	private String size;
	private String totalPages;
	private String totalSize; 
	List<StockVideo>  content; 
	public String getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}
	public String getLastPage() {
		return lastPage;
	}
	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}
	public String getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}
	public List<StockVideo> getContent() {
		return content;
	}
	public void setContent(List<StockVideo> content) {
		this.content = content;
	}
	
}
