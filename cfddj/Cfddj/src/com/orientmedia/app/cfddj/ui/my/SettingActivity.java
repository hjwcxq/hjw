package com.orientmedia.app.cfddj.ui.my;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.service.UpdateService;
import com.orientmedia.base.BaseActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.version.VersionInfo;
import com.tv189.dzlc.adapter.service.impl.VersionServiceImpl;
import com.tv189.dzlc.adapter.service.inerface.VersionService;

public class SettingActivity extends BaseActivity {
	public Button check_version;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		// getview();
		check_version = (Button) findViewById(R.id.btn_check_version);
		check_version.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						PackageInfo packageInfo = null;

						PackageManager packageManager;
						String versionName = "1.0";// 默认0

						Context ctx = SettingActivity.this;
						packageManager = ctx.getPackageManager();
						try {
							packageInfo = packageManager.getPackageInfo(
									ctx.getPackageName(), 0);
							versionName = packageInfo.versionName;
						} catch (NameNotFoundException e) {
							e.printStackTrace();
						}
						VersionService version = new VersionServiceImpl(ctx);
						try {

							// new Handler(getMainLooper()).postDelayed(new
							// Runnable() {
							// @Override
							// public void run() {
							// Toast.makeText(SettingActivity.this, "111",
							// Toast.LENGTH_SHORT).show();
							// }
							// }, 3000);

							final VersionInfo versionInfo = version
									.updateVersion(versionName);

							((Activity) ctx).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// 增加比较当前软件版本是否比服务器上返回的版本新，如果新的话，就不升级
									if (versionInfo != null
											&& versionInfo.getId() != null
											&& versionInfo.getVersion() != null)

									{
										AlertDialog.Builder alBuilder = null;
										try {
											alBuilder = new AlertDialog.Builder(
													SettingActivity.this);
											alBuilder
													.setCancelable(false)
													.setIcon(
															android.R.drawable.ic_dialog_alert)
													.setTitle("发现新版本")
													.setMessage(
															"发现新版本"
																	+ SettingActivity.this
																			.getResources()
																			.getString(
																					R.string.app_name)
																	+ "，版本号 "
																	+ versionInfo
																			.getVersion()
																	+ " ，是否立即更新？"
																	+ "\n\n"
																	+ "更新内容："
																	+ "\n\n"
																	+ versionInfo
																			.getAppContent());
										} catch (Exception e) {
										}
										if (versionInfo.getUpgradFlag() != null
												&& versionInfo.getUpgradFlag()
														.equalsIgnoreCase("1")) {
											alBuilder.setCancelable(true);
											alBuilder
													.setOnCancelListener(new DialogInterface.OnCancelListener() {

														@Override
														public void onCancel(
																DialogInterface dialog) {
															((Activity) SettingActivity.this)
																	.onBackPressed();
														}
													});
										} else {
											alBuilder
													.setNegativeButton(
															"下次再说",
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	// checkUserStatus();???
																}

															});
										}
										alBuilder
												.setNeutralButton(
														"立即更新",
														new DialogInterface.OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																UpdateService.INSTALL_APK_URL = versionInfo
																		.getAppPath();
																Log.e("---getAppPath---",
																		"---"
																				+ versionInfo
																						.getAppPath());
																SettingActivity.this
																		.startService(new Intent(
																				SettingActivity.this,
																				UpdateService.class));
																dialog.dismiss();

															}
														});

										AlertDialog dialog = alBuilder.create();
										// dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
										dialog.show();
										// }
										// });
									} else {
										// checkUserStatus();？？？
									}
								}
							});
						} catch (ServiceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}).start();
				// TODO Auto-generated method stub

			}
		});

	}

	private void getview() {
		// TODO Auto-generated method stub

	}

	class CheckVersion extends AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showNetLoadingProgressDialog("正在检查版本号...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			return null;
			// // TODO Auto-generated method stub
			// boolean flag = false;
			// StockServiceImpl stockService = new StockServiceImpl(
			// ChooseStockActivity.this);
			// try {
			// stockContent = stockService.getNewsStockInfoList("1", "20");
			// if (stockContent != null) {
			// flag = true;
			// newsStocks = stockContent.getContent();
			// }
			// } catch (ServiceException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// return Boolean.valueOf(flag);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			hideNetLoadingProgressDialog();

		}
	}

}
