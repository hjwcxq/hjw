package com.orientmedia.app.cfddj.module.action;

import java.util.Map;
import java.util.Set;

import com.orientmedia.app.cfddj.ui.BaodianActivity;
import com.orientmedia.app.cfddj.ui.expert.ExpertOneToOneActivity;
import com.orientmedia.app.cfddj.ui.find.ChooseStockActivity;
import com.orientmedia.app.cfddj.ui.find.PrivateDataActivity;
import android.content.Context;
import android.content.Intent;

import com.tv189.dzlc.adapter.po.base.AbstractAction;

public class MenuAction extends AbstractAction {

	private static final String ZJYDY = "zjydy"; // 专家一对一

	private static final String ZQBD = "video"; // 赚钱宝典

	private static final String SJSJ = "djsj"; // 私家数据

	private static final String YJXG = "yjxg"; // 一键选股

	private Context context;

	public MenuAction(Context context) {
		this.context = context;
	}

	@Override
	public void jumpByActionType(Map<String, String> paraMap) {
		// TODO Auto-generated method stub
		if (ZJYDY.equalsIgnoreCase(getUrl())) {
			jump(ExpertOneToOneActivity.class);
		} else if (ZQBD.equalsIgnoreCase(getUrl())) {
			jump(BaodianActivity.class);
		} else if (SJSJ.equalsIgnoreCase(getUrl())) {
			jump(PrivateDataActivity.class);
		} else if (YJXG.equalsIgnoreCase(getUrl())) {
			jump(ChooseStockActivity.class);
		}
	}

	public void jump(Class cls) {
		Intent it = new Intent(context, cls);
		if (paraMap != null && !paraMap.isEmpty()) {
			Set<String> keys = paraMap.keySet();
			for (String string : keys) {
				it.putExtra(string, paraMap.get(string));
			}
		}
		context.startActivity(it);
	}
}
