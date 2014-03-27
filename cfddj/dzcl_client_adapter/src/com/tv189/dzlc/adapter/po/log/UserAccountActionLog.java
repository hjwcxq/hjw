package com.tv189.dzlc.adapter.po.log;

import android.content.Context;


public class UserAccountActionLog extends AbstractLogInfo {
	public static final String ACTION_OPEN_APP="100";
	public static final String ACTION_LOGIN="101";
	public static final String ACTION_RIGISTER="102";
	public static final String ACTION_LOGOUT="103";
	
	public UserAccountActionLog(Context context,String actionCode) {
		super(context,actionCode);
	}
}
