package com.orientmedia.app.cfddj.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.teleal.cling.model.meta.Device;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.CallBackListener;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.base.BaseActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.common.TokenException;
import com.tv189.dzlc.adapter.po.repertoire.RepertoireDatailInfo;
import com.tv189.dzlc.adapter.po.repertoire.RepertoireInfo;
import com.tv189.dzlc.adapter.service.impl.RepertoireServiceImpl;
import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
import com.tv189.dzlc.adapter.service.inerface.IUserAccountService;

public class ProgramAdapter extends BaseAdapter {

	private List<RepertoireInfo> list = new ArrayList<RepertoireInfo>();

	private Context context;
	private Button wantRequestBtn;
	private Dialog dialog;
	private String currentPID;

	private int currentListIndex;
	private boolean isNowDate;
	private Date nowDate;

	// private Device mTVDevice;

	public ProgramAdapter(List<RepertoireInfo> infos, boolean isNowDate,
			Context context) {
		this.context = context;
		this.isNowDate = isNowDate;
		this.nowDate = new Date();
		if (infos != null)
			list.addAll(infos);

	}

	public void setItems(List<RepertoireInfo> infos) {
		list.clear();
		if (infos != null) {
			list.addAll(infos);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	void localPlay(final String liveid, final String pid) {
		new AsyncTask<Void, Void, String>() {
			RepertoireInfo repertoireInfo;

			@Override
			protected String doInBackground(Void... params) {
				RepertoireServiceImpl requestService = new RepertoireServiceImpl(
						context);
				try {
					repertoireInfo = requestService.scheduleInfo(pid, liveid);
				} catch (ServiceException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (repertoireInfo != null) {
					List<RepertoireDatailInfo> videos = repertoireInfo
							.getVideos();
					if (videos != null && videos.size() > 0) {
						for (RepertoireDatailInfo info : videos) {
							if (info.getQuality().trim().equals("2")) {
								String playUrl = info.getPlayUrl();
								Log.e("---playUrl----", "---playUrl---"
										+ playUrl);
								Uri uri = Uri.parse(playUrl);
								Intent intent = new Intent(Intent.ACTION_VIEW,
										uri);
								context.startActivity(intent);
								break;
							}
						}
					}
				} else {
					((BaseActivity) context).showCusToast("获得回看信息异常");
				}
			}
		}.execute();

	}

	void DLANPlay(final String liveid, final String pid, final Device device) {

		new AsyncTask<Void, Void, String>() {
			RepertoireInfo repertoireInfo = null;

			@Override
			protected String doInBackground(Void... params) {
				RepertoireServiceImpl requestService = new RepertoireServiceImpl(
						context);
				try {
					repertoireInfo = requestService.scheduleInfo(pid, liveid);
				} catch (ServiceException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (repertoireInfo != null) {
					List<RepertoireDatailInfo> videos = repertoireInfo
							.getVideos();
					if (videos.size() > 0) {
						for (RepertoireDatailInfo info : videos) {
							if (info.getQuality().trim().equals("2")) {

								String playUrl = info.getPlayUrl();
								if (device != null) {

									// mMyUpnpService.setCurrentDevice(device);
									// Intent intent = new Intent(context,
									// DlnaControlActivity.class);
									// intent.putExtra("title", "直播回看");
									// intent.putExtra("Uri", playUrl);
									// context.startActivity(intent);
								} else {
									// 设备突然断开
								}
								break;
							}
						}

					} else {
						AlertDialog.Builder builder = new Builder(context);
						builder.setMessage("该回看没有TV上的流，请选择“本地播放”，谢谢");
						builder.setTitle("提示信息");
						builder.setPositiveButton(
								"确认",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}

								});
						builder.create().show();
					}
				} else {
					((BaseActivity) context).showCusToast("获得回看信息异常");
				}
			}

		}.execute();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = ((Activity) context).getLayoutInflater().inflate(
					R.layout.item_program, null);
		} else {
			view = convertView;
		}
		final RepertoireInfo record = list.get(position);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startTime = format.parse(record.getStarttime());
			Date endTime = format.parse(record.getEndtime());
			SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
			String start = format2.format(startTime);
			String end = format2.format(endTime);
			((TextView) view.findViewById(R.id.time))
					.setText(start + "-" + end);
			String title = record.getTitle();

			((TextView) view.findViewById(R.id.title)).setText(record
					.getTitle());

			Button palyBtn = (Button) view.findViewById(R.id.play);
			boolean isHK;
			if (!this.isNowDate) {// 不是今天
				palyBtn.setText("回看");
				isHK = true;
			} else { // 今天
				if (nowDate.after(endTime))// 现在时间在介绍时间之后
				{
					palyBtn.setVisibility(View.VISIBLE);
					view.findViewById(R.id.liveplay).setVisibility(View.GONE);
					palyBtn.setText("回看");
					isHK = true;
				} else if (nowDate.after(startTime))// 在中间
				{
					// 在播放
					palyBtn.setVisibility(View.GONE);
					view.findViewById(R.id.liveplay)
							.setVisibility(View.VISIBLE);
					isHK = false;
				} else {// 预告
					palyBtn.setVisibility(View.VISIBLE);
					view.findViewById(R.id.liveplay).setVisibility(View.GONE);
					palyBtn.setText("预告");
					palyBtn.setClickable(false);
					palyBtn.setEnabled(false);
					isHK = false;
				}

			}

			if (isHK) {
				palyBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						new ExpertQuestTask(new CallBackListener() {

							@Override
							public void executeSucc() {
								// TODO Auto-generated method stub
								localPlay(record.getLiveid(), record.getPid());
							}

							@Override
							public void executeFail() {
								// TODO Auto-generated method stub

							}

							@Override
							public void postExecute() {
								// TODO Auto-generated method stub

							}
						}).execute("");

					}
				});

			}

			// 1是已经订阅,0是没有定阅
			Button subscribeBtn = (Button) view.findViewById(R.id.subscribe);

			if (!this.isNowDate) {// 不是今天
				subscribeBtn.setVisibility(View.GONE);
			} else {
				subscribeBtn.setVisibility(View.VISIBLE);
			}

			if (record.getIssubscribe() != null
					&& record.getIssubscribe().equals("1")) {
				subscribeBtn.setText("取消");
				subscribeBtn.setTextColor(context.getResources().getColor(
						R.color.color_red));
			} else {
				subscribeBtn.setText("订阅");
				subscribeBtn.setTextColor(context.getResources().getColor(
						R.color.color_black));
			}
			subscribeBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					if (record.getIssubscribe() != null
							&& record.getIssubscribe().equals("1")) {
						new AsyncTask<Void, Void, String>() {
							boolean response;

							@Override
							protected String doInBackground(Void... params) {
								// 取消订阅
								RepertoireServiceImpl requestService = new RepertoireServiceImpl(
										context);
								try {
									response = requestService
											.prgunSubscribe(record.getTitle());
								} catch (ServiceException e) {
									e.printStackTrace();
								}
								return null;
							}

							protected void onPostExecute(String result) {
								if (response) {
									Button subscribeBtn = (Button) v;
									record.setIssubscribe("0");
									subscribeBtn.setText("订阅");
									subscribeBtn.setTextColor(context
											.getResources().getColor(
													R.color.color_black));
									((BaseActivity) context)
											.showCusToast("订阅已取消");
								} else {
									((BaseActivity) context)
											.showCusToast("请求失败");
								}
							};
						}.execute();

					} else {
						new AsyncTask<Void, Void, String>() {
							boolean response;

							@Override
							protected String doInBackground(Void... params) {
								RepertoireServiceImpl requestService = new RepertoireServiceImpl(
										context);
								try {
									response = requestService
											.prgSubscribe(record.getTitle());
								} catch (ServiceException e) {
									e.printStackTrace();
								}
								return null;
							}

							protected void onPostExecute(String result) {
								if (response) {
									Button subscribeBtn = (Button) v;
									record.setIssubscribe("1");
									subscribeBtn.setText("取消");
									subscribeBtn.setTextColor(context
											.getResources().getColor(
													R.color.color_red));
									((BaseActivity) context)
											.showCusToast("订阅成功");
								} else {
									((BaseActivity) context)
											.showCusToast("订阅失败");
								}
							};

						}.execute();
					}
				}
			});
		} catch (Exception e) {
			Log.e("ProgramAdapter", e.getStackTrace().toString());
		}
		return view;
	}

	class ExpertQuestTask extends AsyncTask<String, Void, String> {

		String isvip = "1";

		ProgressDialog progressDialog;

		private CallBackListener call;

		public ExpertQuestTask(CallBackListener call) {
			this.call = call;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("验证您的订购信息...");
			progressDialog.setCancelable(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			return isvip;

		}

		@Override
		protected void onPostExecute(String result) {
			try {
				progressDialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (isvip.equals(result)) {
				call.executeSucc();
			} else {
				Toast.makeText(context, "你不是Vip无法观看", Toast.LENGTH_SHORT)
						.show();
				call.executeFail();
			}

		}
	}

}
