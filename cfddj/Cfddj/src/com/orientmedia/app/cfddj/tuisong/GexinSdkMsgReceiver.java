package com.orientmedia.app.cfddj.tuisong;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.igexin.sdk.Consts;
import com.tv189.dzlc.adapter.config.APIConfig;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.service.impl.NewsServiceImpl;

public class GexinSdkMsgReceiver extends BroadcastReceiver {
	
	private static Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		this.context = context;
		switch (bundle.getInt(Consts.CMD_ACTION)) {
		case Consts.GET_MSG_DATA:
			// 获取透传数据
			// String appid = bundle.getString("appid");
			byte[] payload = bundle.getByteArray("payload");

			if (payload != null) {

				String data = new String(payload);
				parseContent(data);

			}
			break;
		case Consts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			final String cid = bundle.getString("clientid");
			AppSetting.getInstance(context).setClientId(cid);
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					NewsServiceImpl requestService = new NewsServiceImpl(
							GexinSdkMsgReceiver.context);
					String nickName = AppSetting.getInstance(GexinSdkMsgReceiver.context).getNickName();
					String deviceId = ((TelephonyManager) GexinSdkMsgReceiver.context
							.getSystemService(Context.TELEPHONY_SERVICE))
							.getDeviceId();
					String deviceType = APIConfig.DEVICETYPE;
					String phonenumm = AppSetting.getInstance(GexinSdkMsgReceiver.context).getPhoneNumber();
					String channelid = DzlcAndroidConfig.CHANNEL_ID;
					String getuiDeviceId = cid;
					try {
						requestService
								.regPushClient(nickName, deviceId, deviceType,
										phonenumm, channelid, getuiDeviceId);
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

			}.execute();
			break;
		default:
			break;
		}
	}

	/****
	 * 解析透传过来的消息
	 * 
	 * @param data
	 */
	public void parseContent(String data) {
		JSONObject obj;
		try {
			obj = new JSONObject(data);
			if (obj.get("type").equals("news")) {
				new NewsNotifier(context).notifyMessage(obj.getString("title"),
						obj.getString("id"));
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}
}
