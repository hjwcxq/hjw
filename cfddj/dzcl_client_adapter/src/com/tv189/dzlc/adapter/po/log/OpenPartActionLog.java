package com.tv189.dzlc.adapter.po.log;

import android.content.Context;

public class OpenPartActionLog extends AbstractLogInfo {
		//直播频道
		public static final String PART_ID_LIVE="200";
		//股市宝典频道
		public static final String PART_ID_GSBD="201";
		//数据频道
		public static final String PART_ID_STOCK="202";
		//说吧频道
		public static final String PART_ID_BBS="203";
		//用户中心频道
		public static final String PART_ID_USER_CENTER="204";
		//用户中心，个人信息频道
		public static final String PART_ID_USER_PROFILE="205";
		//用户中心，我的订购频道
		public static final String PART_ID_USER_BUY="206";
		//用户中心，我的提问频道
		public static final String PART_ID_USER_MY_QUESTION="207";
		//用户中心，我的帖子频道
		public static final String PART_ID_USER_MY_POSTS="208";
		//用户中心，节目介绍
		public static final String PART_ID_USER_PROGRAMINTRODUCTION ="209";
		//用户中心，我的收藏频道
		public static final String PART_ID_USER_MY_COLLECT="210";
		//节目单
		public static final String PART_ID_PROGRAMLIST = "211" ;
		//股市宝典频道 -个股
		public static final String PART_ID_GSBD_GUFX="212";
		//股市宝典频道 -大盘
		public static final String PART_ID_GSBD_DPFX="213";
		//股市宝典频道 
		public static final String PART_ID_GSBD_ZJYC="214";
		//股市宝典频道
		public static final String PART_ID_GSBD_GSBK="215";
		//股市宝典频道
		public static final String PART_ID_GSBD_LZQAA="216";	
	public OpenPartActionLog(Context context, String partId) {
		super(context,ACTION_OPEN_PART, partId);
	}

}
