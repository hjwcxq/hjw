package com.orientmedia.app.cfddj.module.action;

import java.util.Map;
import java.util.Set;

import com.orientmedia.app.cfddj.ui.WebActivity;
import android.content.Context;
import android.content.Intent;

import com.tv189.dzlc.adapter.po.base.AbstractAction;

public class UrlAction extends AbstractAction {

	private Context context;

	public UrlAction(Context context) {
		this.context = context;
	}

	@Override
	public void jumpByActionType(Map<String, String> paraMap) {
		// TODO Auto-generated method stub
		
		Intent it = new Intent(context, WebActivity.class);
		if (paraMap != null && !paraMap.isEmpty()) {
			Set<String> keys = paraMap.keySet();
			for (String string : keys) {
				it.putExtra(string, paraMap.get(string));
			}
		}
		it.putExtra("url", getUrl());
		context.startActivity(it);
	}
}
