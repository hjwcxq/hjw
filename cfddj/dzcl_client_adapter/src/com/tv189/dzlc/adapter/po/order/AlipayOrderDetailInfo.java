package com.tv189.dzlc.adapter.po.order;

import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;

public class AlipayOrderDetailInfo extends AbstractApiResponse{  
		//第三方支付
		private String out_trade_no;
		private String partner;
		private String price;
		private String productDesc;
		private String productName;
		private String seller;
		public String getOut_trade_no() {
			return out_trade_no;
		}
		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}
		public String getPartner() {
			return partner;
		}
		public void setPartner(String partner) {
			this.partner = partner;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getProductDesc() {
			return productDesc;
		}
		public void setProductDesc(String productDesc) {
			this.productDesc = productDesc;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getSeller() {
			return seller;
		}
		public void setSeller(String seller) {
			this.seller = seller;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		private String sign;
}
