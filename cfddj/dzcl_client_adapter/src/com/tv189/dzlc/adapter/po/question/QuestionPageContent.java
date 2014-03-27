package com.tv189.dzlc.adapter.po.question;

import java.util.List;

public class QuestionPageContent{
	private List<QuestionInfo> content; 
	private int size;
	private int number;
	private Boolean firstPage;
	private Boolean lastPage;
	private int numberOfElements;
	private int totalElements;
	private int totalPages;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Boolean getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(Boolean firstPage) {
		this.firstPage = firstPage;
	}
	public Boolean getLastPage() {
		return lastPage;
	}
	public void setLastPage(Boolean lastPage) {
		this.lastPage = lastPage;
	}
	public int getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<QuestionInfo> getContent() {
		return content;
	} 
	public void setContent(List<QuestionInfo> content) {
		this.content = content;
	} 
}
