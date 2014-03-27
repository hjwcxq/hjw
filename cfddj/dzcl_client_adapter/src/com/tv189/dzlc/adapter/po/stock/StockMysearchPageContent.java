package com.tv189.dzlc.adapter.po.stock;

import java.util.List;

import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;



/**
 * 我的股票记录
 */
public class StockMysearchPageContent {
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
	public String getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(String numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
	}
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}
	private String firstPage;
	private String lastPage;
	private String number;
	private String numberOfElements;
	private String size;
	private String sort;
	private String totalElements;
	private String totalPages; 
	private List<StockInfo> content;
	public List<StockInfo> getContent() {
		return content;
	}
	public void setContent(List<StockInfo> content) {
		this.content = content;
	}
}
