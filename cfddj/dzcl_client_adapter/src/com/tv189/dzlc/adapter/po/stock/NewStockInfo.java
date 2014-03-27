package com.tv189.dzlc.adapter.po.stock;


public class NewStockInfo {
	private int id;
	private String code;
	private String referred;//简称
	private String industry;//所属行业
	private String theme;
	private String basicdate;
	private Double closing; //收盘
	private String trading;
	private String major;//主营同比
	private String netprofit;//净利润同比
	private String profitmargin;//毛利率
	private String growth;
	private String insertdate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReferred() {
		return referred;
	}

	public void setReferred(String referred) {
		this.referred = referred;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}


	public String getBasicdate() {
		return basicdate;
	}

	public void setBasicdate(String basicdate) {
		this.basicdate = basicdate;
	}

	public Double getClosing() {
		return closing;
	}

	public void setClosing(Double closing) {
		this.closing = closing;
	}

	public String getTrading() {
		return trading;
	}

	public void setTrading(String trading) {
		this.trading = trading;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
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

}
