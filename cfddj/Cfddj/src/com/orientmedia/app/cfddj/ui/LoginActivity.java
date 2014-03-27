package com.orientmedia.app.cfddj.ui;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.base.BaseActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.telecomsdk.phpso.TysxOA;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.common.TokenException;
import com.tv189.dzlc.adapter.po.user.UserAccountInfo;
import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
import com.tv189.dzlc.adapter.util.Utils;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText userName, userPw;

	private Button loginButton, emailButton, oneKeyRegBtn;

	private UserAccountInfo newUserInfo;

	private TysxOA tysxOa;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userName = (EditText) findViewById(R.id.user_name);
		userPw = (EditText) findViewById(R.id.user_password);
		loginButton = (Button) findViewById(R.id.login_btn);
		emailButton = (Button) findViewById(R.id.email_reg_ben);
		oneKeyRegBtn = (Button) findViewById(R.id.one_key_reg_ben);

		String phoneNum = AppSetting.getInstance(this).getUserName();
		if (phoneNum != null && !"".equals(phoneNum))
			userName.setText(phoneNum);
		loginButton.setOnClickListener(this);
		emailButton.setOnClickListener(this);
		oneKeyRegBtn.setOnClickListener(this);

	}

	public void login() {
		if (userName.getText().toString().equalsIgnoreCase("")) {
			showCusToast("请输入用户名");
			return;
		} else if (userPw.getText().toString().equalsIgnoreCase("")) {
			showCusToast("请输入密码");
			return;
		}
		// 先隐藏输入法：
		Utils.hideSoftInput(LoginActivity.this);
		new loginTask().execute(new String[] { userName.getText().toString(),
				userPw.getText().toString() });
	}

	public void reg() {
		Intent i_to_reg = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(i_to_reg);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_btn:
			login();
			break;
		case R.id.email_reg_ben:
			reg();
			break;
		case R.id.one_key_reg_ben:
			regByOnkey();
			break;

		default:
			break;
		}
	}

	// 一键注册
	private String onkey_message = "";

	public void regByOnkey() {

		if (!Utils.checkNetWork(LoginActivity.this)) {
			showCusToast("网络连接失败，请检查你的网络");
			return;
		}
		if (!Utils.isSimAvailable(LoginActivity.this)) {
			showCusToast("一键注册失败，请检查你的SIM卡状态");
			return;
		}
		showLoadingIcon();

		onkey_message = getRandomStr();

		SmsManager sms = SmsManager.getDefault();

		String SENT_SMS_ACTION = "SENT_SMS_ACTION";
		String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";

		// create the sentIntent parameter
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent,
				0);

		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0,
				deliverIntent, 0);

		this.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							new OnkeyRegAsyncTask().execute(onkey_message);
						}
					}, 5000);
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:

				case SmsManager.RESULT_ERROR_RADIO_OFF:

				case SmsManager.RESULT_ERROR_NULL_PDU: {
					new AlertDialog.Builder(LoginActivity.this)
							.setTitle("提示信息")
							.setMessage("一键注册失败，是否重试？")
							.setPositiveButton("重试",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											hideLoadingIcon();
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											hideLoadingIcon();
										}
									}).create().show();

					break;
				}

				}
				_context.unregisterReceiver(this);
			}
		}, new IntentFilter(SENT_SMS_ACTION));
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				_context.unregisterReceiver(this);
			}
		}, new IntentFilter(DELIVERED_SMS_ACTION));

		// if message's length more than 70 ,
		// then call divideMessage to dive message into several part ,and call
		// sendTextMessage()
		// else direct call sendTextMessage()
		String msgText = "$.$" + "0001" + "#" + onkey_message + "$.$";
		if (onkey_message.length() > 70) {
			ArrayList<String> msgs = sms.divideMessage(msgText);
			for (String msg : msgs) {
				sms.sendTextMessage(MainConst.MSG_NUMBER, null, msg, sentPI,
						deliverPI);
			}
		} else {
			sms.sendTextMessage(MainConst.MSG_NUMBER, null, msgText, sentPI,
					deliverPI);
		}
	}

	// 获取16位随机字符串
	public String getRandomStr() {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		while (sb.length() < 16) {
			sb.append(Integer.toString(ran.nextInt() & 0x7FFFFFFF, 36));
		}
		return sb.substring(0, 16);
	}

	/**
	 * 用户名密码登录
	 * 
	 * @author EsaFans
	 * 
	 */
	public class loginTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			showLoadingIcon();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean isSuccess = false;
			UserAccountServiceImpl requestService = new UserAccountServiceImpl(
					LoginActivity.this);
			try {
				newUserInfo = requestService.userLogin(params[0], params[1]);
				if (newUserInfo != null) {
					isSuccess = true;
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(LoginActivity.this, null, e);
			}
			if (isSuccess) {
			}
			return Boolean.valueOf(isSuccess);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			hideLoadingIcon();
			if (result.booleanValue()) {
				showCusToast("登陆成功");
				finish();
			}
		}
	}

	/****
	 * 一键注册
	 */
	class OnkeyRegAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

		}

		@Override
		protected String doInBackground(String... params) {
			String respJson = null;
			AppSetting setting = AppSetting.getInstance(LoginActivity.this);
			tysxOa = new TysxOA();
			String initJson = tysxOa.loading(LoginActivity.this,
					Utils.getDeviceid(LoginActivity.this), MainConst.APP_ID,
					MainConst.APP_SECRET,
					DzlcAndroidConfig.ONE_KEY_REG_SDK_URL,
					setting.getChannelid());
			if (parserJson(initJson)) {
				respJson = tysxOa.register(LoginActivity.this,
						setting.getToken(), MainConst.DEVOLOPER_ID,
						MainConst.APP_ID, MainConst.APP_SECRET);

			}
			return respJson;

		}

		@Override
		protected void onPostExecute(String result) {
			try {
				hideLoadingIcon();
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (result != null) {
				try {
					JSONObject json = new JSONObject(result);
					if ("0".equals(json.getString("code"))) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
							}
						}).start();
						showCusToast("一键注册成功");
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						startActivity(intent);
					} else {
						showCusToast("一键注册失败");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				showCusToast("注册失败");
			}
		}
	}

	public boolean parserJson(String initJson) {
		boolean flag = false;
		if (initJson != null && !"".equals(initJson)) {
			try {
				JSONObject json = new JSONObject(initJson);
				String code = json.getString("code");
				if ("0".equals(code) || "00".equals(code)) {
					flag = true;
					String token = json.getString("token");
					if (token != null && !"".equals(token))
						AppSetting.getInstance(LoginActivity.this).setToken(
								token);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

}
