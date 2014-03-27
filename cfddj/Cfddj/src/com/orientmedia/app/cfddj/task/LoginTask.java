package com.orientmedia.app.cfddj.task;

import com.orientmedia.app.cfddj.Listener.CallBackListener;
import android.content.Context;
import android.os.AsyncTask;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.common.TokenException;
import com.tv189.dzlc.adapter.po.user.UserAccountInfo;
import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
import com.tv189.dzlc.adapter.service.inerface.IUserAccountService;
import com.tv189.dzlc.adapter.util.Utils;

public class LoginTask {

	private Context cont;

	public LoginTask(Context cont) {
		this.cont = cont;
	}

	public void loginByImsi(CallBackListener back) {
		String imsi = Utils.getImsiid(cont);
		new LoginByIMSITask(back).execute(imsi);
	}

	class LoginByIMSITask extends AsyncTask<String, Void, Boolean> {
		private CallBackListener back;

		public LoginByIMSITask(CallBackListener back) {
			this.back = back;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean flag = false;
			UserAccountServiceImpl requestService = new UserAccountServiceImpl(
					cont);
			@SuppressWarnings("unused")
			UserAccountInfo userInfo = null;
			try {
				userInfo = requestService.userLogin(params[0]);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			if (userInfo != null) {
				flag = true;
			}
			return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result.booleanValue()) {
				back.executeSucc();
			} else {
				back.executeFail();
			}
		}
	}

}
