package com.tv189.dzlc.adapter.po.order;

public class OrderInfo {
	private String productName;
	private String productId;
	private String pstart;
	private String pend;
	private String subId;
	private String fee;
	private String unsubscribed;
	private String purchaseType;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPstart() {
		return pstart;
	}
	public void setPstart(String pstart) {
		this.pstart = pstart;
	}
	public String getPend() {
		return pend;
	}
	public void setPend(String pend) {
		this.pend = pend;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getUnsubscribed() {
		return unsubscribed;
	}
	public void setUnsubscribed(String unsubscribed) {
		this.unsubscribed = unsubscribed;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
}
