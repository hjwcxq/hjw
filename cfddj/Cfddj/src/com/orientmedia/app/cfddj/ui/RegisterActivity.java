package com.orientmedia.app.cfddj.ui;


import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.CallBackListener;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.base.BaseActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.user.UserAccountInfo;
import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;

public class RegisterActivity extends BaseActivity {
	
	private EditText userName, userPw, userPwReinput;
	
	private Button checkButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		userName = (EditText) findViewById(R.id.editTextUserName4ItemUserReg);
		userPw = (EditText) findViewById(R.id.editTextUserPW4ItemUserReg);
		userPwReinput = (EditText) findViewById(R.id.editTextUserPWReInput4ItemUserReg);
		checkButton = (Button) findViewById(R.id.buttonYes4ItemUserReg);

		checkButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (userName.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(RegisterActivity.this, "请输入用户名",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (userPw.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(RegisterActivity.this, "请输入密码",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (!userPw.getText().toString()
						.equals(userPwReinput.getText().toString())) {
					Toast.makeText(RegisterActivity.this, "两次输入密码不一致",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (userPw.getText().toString().length() < 6
						|| userPw.getText().toString().length() > 18) {
					Toast.makeText(RegisterActivity.this, "密码长度不符合，请输入6-18位密码",
							Toast.LENGTH_SHORT).show();
					return;
				}
				new regTask().execute(new String[] {
						userName.getText().toString(),
						userPw.getText().toString() });
			}
		});

	}

	/**
	 * 注册User
	 * 
	 * @author EsaFans
	 * 
	 */
	public class regTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			showLoadingIcon();
		}
		

		@Override
		protected Boolean doInBackground(String... params) {
			boolean flag = false ;
			UserAccountServiceImpl requestService = new UserAccountServiceImpl(
					RegisterActivity.this);
			UserAccountInfo userInfo = null;
			try {
				userInfo = requestService.register(params[0], params[1]);
			} catch (ServiceException e) {
				ExcepUtils.showImpressiveException(RegisterActivity.this, null, e);
			}

			if (userInfo != null) {
				if (userInfo.getCode().equalsIgnoreCase("0")
						|| userInfo.getCode().equalsIgnoreCase("00")) {
					flag = true ;
				}
			}
			
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			hideLoadingIcon();

			if (result.booleanValue()) {
				Toast.makeText(RegisterActivity.this, "注册成功!",
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(RegisterActivity.this, "注册失败",
						Toast.LENGTH_LONG).show();
			}
		}
	}

}
