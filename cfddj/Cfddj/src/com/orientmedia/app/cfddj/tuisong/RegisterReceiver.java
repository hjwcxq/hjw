package com.orientmedia.app.cfddj.tuisong;

import org.json.JSONException;
import org.json.JSONObject;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.MainConst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class RegisterReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		String appid = bundle.getString("appid");
		String userJson = bundle.getString("result");
		saveUserInfo(context, userJson);
	}

	public void saveUserInfo(Context context, String userJson) {
		if (userJson == null || "".equals(userJson))
			return;
		else {
			try {
				JSONObject jsonObj = new JSONObject(userJson);
				String code = jsonObj.getString("code");
				if ("0".equals(code) || "00".equals(code) || "110".equals(code)) {
					String uJson = jsonObj.getString("info");
					JSONObject user = new JSONObject(uJson);
					AppSetting setting = AppSetting.getInstance(context);
					setting.setNickName(user.getString("nickName"));
					setting.setUserName(user.getString("mobile"));
					setting.setPhoneNumber(user.getString("mobile"));
					setting.setUserId(user.getString("uid"));
					setting.setBind(user.getString(MainConst.UserConst.BIND));
					setting.setPhoneStatu(user
							.getString(MainConst.UserConst.PHONE_STATU));
				} else {
					Toast.makeText(context, "一键注册失败", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
