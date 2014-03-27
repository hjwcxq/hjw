package com.tv189.dzlc.adapter.po.stock;

import java.util.List;

public class NewStockInfoContent{ 
	private String firstPage;
	private String lastPage;
	private String number;
	private String size;
	private String totalPages;
	private String totalElements; 
	
	List<NewStockInfo>  content; 
	
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

	public String getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
	}
	public List<NewStockInfo> getContent() {
		return content;
	}
	public void setContent(List<NewStockInfo> content) {
		this.content = content;
	}
	
}
