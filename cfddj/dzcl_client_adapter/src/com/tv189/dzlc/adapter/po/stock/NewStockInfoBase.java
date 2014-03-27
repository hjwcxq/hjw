package com.tv189.dzlc.adapter.po.stock;

import java.util.Date;
import java.util.List;

public class NewStockInfoBase {

	/*
	 * "size": 20, "number": 0, "sort": null, "totalPages": 5, "firstPage":
	 * true, "lastPage": false, "numberOfElements": 20, "totalElements": 98
	 */
	private List<NewStockInfo> content;

	public List<NewStockInfo> getContent() {
		return content;
	}

	public void setContent(List<NewStockInfo> content) {
		this.content = content;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public String getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(String numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public String getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
	}

	public String getNetprofit() {
		return netprofit;
	}

	public void setNetprofit(String netprofit) {
		this.netprofit = netprofit;
	}

	public String getProfitmargin() {
		return profitmargin;
	}

	public void setProfitmargin(String profitmargin) {
		this.profitmargin = profitmargin;
	}

	public String getGrowth() {
		return growth;
	}

	public void setGrowth(String growth) {
		this.growth = growth;
	}

	public String getInsertdate() {
		return insertdate;
	}

	public void setInsertdate(String insertdate) {
		this.insertdate = insertdate;
	}

	private String number;
	private String sort;
	private String totalPages;
	private boolean firstPage;
	private boolean lastPage;
	private String numberOfElements;
	private String totalElements;
	private String netprofit;
	private String profitmargin;
	private String growth;
	private String insertdate;

}
