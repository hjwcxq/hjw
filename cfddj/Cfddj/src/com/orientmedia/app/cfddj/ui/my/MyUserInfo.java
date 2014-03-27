package com.orientmedia.app.cfddj.ui.my;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.service.ModifyTaskInterface;
import com.orientmedia.app.cfddj.ui.MainActivity;
import com.orientmedia.base.BaseActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
import com.tv189.dzlc.adapter.service.inerface.IUserAccountService;

//用户资料
public class MyUserInfo extends BaseActivity {

	public final static String TAG = "MyUserInfo";
//	private UserAccountInfo userInfo;
	public RelativeLayout userinfoBorder, rl_item_nickname;
	public LinearLayout ll_all_item;
	public TextView tv_nickname;

	public void logoutClick(View v) {
		new AlertDialog.Builder(MyUserInfo.this)
				.setTitle("提示信息")
				.setMessage(
						"您要注销当前用户，并重新登录" + getString(R.string.app_name) + "吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// new UserInfo().resume().remove();
						AppSetting.getInstance(MyUserInfo.this).userLogout();
						Intent i_home = new Intent(MyUserInfo.this,
								MainActivity.class);
						startActivity(i_home);
						finish();

					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).create().show();

	}

	public void resetNickNameClick(View v) {
		final AlertDialog dialog = new AlertDialog.Builder(MyUserInfo.this)
				.create();
		final View dialogView = getLayoutInflater().inflate(
				R.layout.item_modify, null);
		TextView title = (TextView) dialogView
				.findViewById(R.id.title4ItemModify);
		title.setText("修改昵称");

		final EditText editText1 = (EditText) dialogView
				.findViewById(R.id.editText14ItemModify);
		editText1.setTransformationMethod(HideReturnsTransformationMethod
				.getInstance());
		editText1.setVisibility(View.VISIBLE);
		editText1.setHint("输入昵称");
//		userInfo = FileUtil.getUserInfo(this);

//		if (userInfo.getNickname() != null) {
//			editText1.setText(userInfo.getNickname());
//		}

		Button buttonYes = (Button) dialogView
				.findViewById(R.id.buttonYes4ItemModify);
		buttonYes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

//				final String oldnick = userInfo.getNickname();
				final String newnick = editText1.getText().toString();

				if (newnick.equalsIgnoreCase("")) {
					showCusToast("请输入昵称");
					return;
				} else if (chineseLength(newnick) > 14) {
					// 不超过7个汉字或14个英文，数字或下划线
					showCusToast("昵称长度请不要超过7个汉字或14个英文，数字或下划线");
					return;
				} 
//				else if (oldnick != null && newnick.equalsIgnoreCase(oldnick)) {
//					showCusToast("未更改昵称");
//					try {
//						dialog.dismiss();
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					return;
//				}

				ModifyTaskInterface modifyTaskInterface = new ModifyTaskInterface() {

					@Override
					public String getTitle() {
						// TODO Auto-generated method stub
						return "正在修改昵称...";
					}

					@Override
					public String excute(String successStr, String errorStr) {
						IUserAccountService requestService = new UserAccountServiceImpl(
								MyUserInfo.this);
						boolean response;
						try {
							response = requestService.updateNickname(newnick);
							if (!response) {
								return errorStr;
							}
							return successStr;
						} catch (ServiceException e) {
							e.printStackTrace();
						}
						return errorStr;
					}
				};
				new ModifyTask(dialog, modifyTaskInterface).execute();
			}
		});
		Button buttonNo = (Button) dialogView
				.findViewById(R.id.buttonNo4ItemModify);
		buttonNo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					dialog.dismiss();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		});
		dialog.setView(dialogView);
		dialog.show();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		setView();
	}

	@Override
	public void onStop() {
		if (getParent() != null) {
			getParent().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onStop();
		Log.e(TAG, "onStop");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.e(TAG, "onPause");
		// StatService.onPause(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.e(TAG, "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		if (getParent() != null) {
			getParent().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 refreshInfoField();

		// StatService.onResume(this);
	}

	/**
	 * @method setView()
	 * @brief 设置视图，初始化组件
	 * 
	 * @retval void
	 * @param
	 * @return 无返回值
	 */
	private void setView() {
		userinfoBorder = (RelativeLayout) findViewById(R.id.userinfoBorder);
		ll_all_item = (LinearLayout) findViewById(R.id.ll_all_item);
		rl_item_nickname = (RelativeLayout) findViewById(R.id.rl_item_nickname);
		tv_nickname = (TextView) findViewById(R.id.tv_nickname);
	}

	private void refreshInfoField() {
		AppSetting sContext = AppSetting.getInstance(this);

		// userInfo = FileUtil.getUserInfo(UserInfoACT.this);
		if (sContext.getNickName() != null) {
			tv_nickname.setText("手机号:" + sContext.getPhoneNumber());
		} else {
			if (sContext.getUserName() != null) {
				Log.e("---username----",
						"---username----" + sContext.getUserName());
				tv_nickname.setText("用户:" + sContext.getUserName());
				// userInfo.setNickname(a);
			} else {
				Log.e("---无法识别当前账户----", "---无法识别当前账户----");
				tv_nickname.setText("无法识别当前账户");
			}

		}

		if (sContext.getNickName() != null) {
			Log.e("---nickname----", "---nickname----" + sContext.getNickName());
			tv_nickname.setText("昵称:" + sContext.getNickName());
		} else {
			Log.e("---未设置昵称----", "---未设置昵称----");
			tv_nickname.setText("未设置昵称");
		}

	}

	/**
	 * 修改密码
	 * 
	 * @author EsaFans
	 * 
	 */
	class ModifyTask extends AsyncTask<Void, Void, String> {

		String SUCCESS = "succes";
		String ERROR = "error";
		ProgressDialog progressDialog;
		Dialog dialog;
		ModifyTaskInterface modifyTaskInterface;

		public ModifyTask(Dialog dialog, ModifyTaskInterface modifyTaskInterface) {
			this.dialog = dialog;
			this.modifyTaskInterface = modifyTaskInterface;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				progressDialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (result.equalsIgnoreCase(SUCCESS)) {
				try {
					dialog.dismiss();
				} catch (Exception e) {
					// TODO: handle exception
				}
				refreshInfoField();
			} else {

			}
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				modifyTaskInterface.excute(SUCCESS, ERROR);
			} catch (Exception e) {
				return ERROR;
			}
			return SUCCESS;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MyUserInfo.this);
			progressDialog.setMessage(modifyTaskInterface.getTitle());
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public int chineseLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

}
