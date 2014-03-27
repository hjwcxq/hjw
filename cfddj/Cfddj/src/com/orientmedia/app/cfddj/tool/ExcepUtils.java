package com.orientmedia.app.cfddj.tool;

import mobi.dreambox.frameowrk.core.constant.ErrorCodeConstants;
import com.orientmedia.app.cfddj.ui.LoginActivity;
import com.orientmedia.app.cfddj.ui.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.tv189.dzlc.adapter.exception.AndroidClientBaseException;
import com.tv189.dzlc.adapter.exception.ErrorCodeConst;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.common.TokenException;

public class ExcepUtils {

	/**
	 * 
	 * @param context
	 * @param exception
	 */
	public static void showException(final Activity context,
			final AndroidClientBaseException e) {
		context.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (e != null) {
					if (ErrorCodeConstants.HTTP_SOCKET_TIMEOUT
							.equalsIgnoreCase(e.getErrCode())) {
						Toast.makeText(context, "连接超时,请检查你的网络",
								Toast.LENGTH_SHORT).show();
					} else if (ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL
							.equalsIgnoreCase(e.getErrCode())) {
						Toast.makeText(context, "网络连接失败，请检查你的网络",
								Toast.LENGTH_SHORT).show();
					} else if (ErrorCodeConst.CODE_EMAIL_REPEAT_REG
							.equalsIgnoreCase(e.getErrCode())) {
						Toast.makeText(context, "该邮箱已经注册，请不要重复注册",
								Toast.LENGTH_SHORT).show();
					} else if (ErrorCodeConst.CODE_ACTION_NOT_LOGIN
							.equalsIgnoreCase(e.getErrCode())) {
						Toast.makeText(context, "你还没有登录，请先登录",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(context, LoginActivity.class);
						context.startActivity(intent);
						if (context instanceof MainActivity) {

						} else {
							context.finish();
						}

					} else if (ErrorCodeConst.CODE_LOGIN_USERNAME_NOT_EXIT
							.equalsIgnoreCase(e.getErrCode())) {
						Toast.makeText(context, "用户名不存在", Toast.LENGTH_SHORT)
								.show();
					} else if (ErrorCodeConst.CODE_LOGIN_PASSWORD_ERROR
							.equalsIgnoreCase(e.getErrCode())) {
						Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT)
								.show();
					} else if (ErrorCodeConst.CODE_TOKEN_DISABLE
							.equalsIgnoreCase(e.getErrCode())) {
						Toast.makeText(context, "你的用户信息已经失效，请重新登录",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(context, LoginActivity.class);
						context.startActivity(intent);

						if (context instanceof MainActivity) {

						} else {
							context.finish();
						}
					} else if (ErrorCodeConst.CODE_AUTH_FAIL.equalsIgnoreCase(e
							.getErrCode())) {
					}
				}
			}
		});
	}

	/**
	 * 把可视的异常信息展示出来
	 * 
	 * @param msg
	 * @param exception
	 */
	public static void showImpressiveException(Activity activity, String msg,
			Exception exception) {
		if (exception != null
				&& (exception instanceof ServiceException || exception instanceof TokenException))
			showException(activity, (AndroidClientBaseException) exception);
		else {
			if (msg != null)
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
		}
	}

}
