package com.orientmedia.app.cfddj.module.action;

import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.tv189.dzlc.adapter.po.base.AbstractAction;

public class ApiAction extends AbstractAction {

	private Context context;

	public ApiAction(Context context) {
		this.context = context;
	}

	@Override
	public void jumpByActionType(Map<String, String> paraMap) {
		// TODO Auto-generated method stub
		Class  cls =  getJumpClass();
		Intent it = new Intent();
		if(cls != null){
			it.setClass(context, cls);
			if (paraMap != null && !paraMap.isEmpty()) {
				Set<String> keys = paraMap.keySet();
				for (String string : keys) {
					it.putExtra(string, paraMap.get(string));
				}
			}
			context.startActivity(it);
		}
		
	}

	public Class<?> getJumpClass() {
		try {
			String url = getUrl();
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String packageName = info.packageName;
			String activityName = packageName + ".ui." + tofirstLowerCase(url)+"Activity";
			return Class.forName(activityName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public static String tofirstLowerCase(String str) {
		if (str != null && str.length() > 0){
			
			return str.substring(0, 1).toUpperCase()
					+ str.substring(1, str.length());
		}
		else
			return null;
	}
}
