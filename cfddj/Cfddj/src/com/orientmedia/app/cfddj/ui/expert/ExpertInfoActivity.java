//package tv.aniu.app.dzlc.ui.expert;
//
//import tv.aniu.app.dzlc.R;
//import tv.aniu.app.dzlc.tool.StringUtil;
//import tv.aniu.app.dzlc.tool.imageutils.AsyncTask;
//import tv.base.BaseActivity;
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.opengl.GLException;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.tv189.dzlc.adapter.config.MainConst;
//import com.tv189.dzlc.adapter.context.SystemContext;
//import com.tv189.dzlc.adapter.po.common.ServiceException;
//import com.tv189.dzlc.adapter.po.sqlpo.ExpertsInfo;
//import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
//import com.tv189.dzlc.adapter.service.inerface.IUserAccountService;
//
///****
// * 专家一对一详情界面
// * 
// */
//@SuppressLint("SimpleDateFormat")
//public class ExpertInfoActivity extends BaseActivity implements OnClickListener {
//	private Button wantRequestBtn;
//	private Dialog dialog;
//	private String eid ;
//	private ExpertsInfo expertInfo;
//	private ImageView head_img;
//	private TextView name_tv, position_tv, status_tv, introduction_tv,
//			alltime_tv, remaintime_tv, waitnum_tv, consult_time_tv;
//	private LinearLayout alltime_ll, remaintime_ll, waitnum_ll,
//			offline_time_ll, right_layout;
//	
//
//	@Override
//	public void onCreate(Bundle arg0) {
//		super.onCreate(arg0);
//		setContentView(R.layout.activity_expertinfo);
//		this.expertInfo = SystemContext.getInstance(ExpertInfoActivity.this)
//				.getCurrentExpert();
//		getIntentData();
//		initView();
//		initData();
//
//		// new ExpertInfoTask().execute(eid);
//	}
//
//	private void getIntentData() {
//		if (getIntent().hasExtra(MainConst.EXPERT_ID)) {
//			eid = getIntent().getStringExtra(MainConst.EXPERT_ID);
//		}
//
//	}
//
//	private void initView() {
//		head_img = (ImageView) findViewById(R.id.head_img);
//		wantRequestBtn = (Button) findViewById(R.id.wantRequestBtn);
//		name_tv = (TextView) findViewById(R.id.name_tv);
//		position_tv = (TextView) findViewById(R.id.position_tv);
//		status_tv = (TextView) findViewById(R.id.status_tv);
//		introduction_tv = (TextView) findViewById(R.id.introduction_tv);
//		alltime_tv = (TextView) findViewById(R.id.alltime_tv);
//		remaintime_tv = (TextView) findViewById(R.id.remaintime_tv);
//		waitnum_tv = (TextView) findViewById(R.id.waitnum_tv);
//		alltime_ll = (LinearLayout) findViewById(R.id.alltime_ll);
//		remaintime_ll = (LinearLayout) findViewById(R.id.remaintime_ll);
//		waitnum_ll = (LinearLayout) findViewById(R.id.waitnum_ll);
//		offline_time_ll = (LinearLayout) findViewById(R.id.offline_time_ll);
//		consult_time_tv = (TextView) findViewById(R.id.consult_time_tv);
//		right_layout = (LinearLayout) findViewById(R.id.right_layout);
//		wantRequestBtn.setOnClickListener(this);
//	}
//
//	private void initData() {
//		// setImageView(head_img, expertInfo.getExpert_thumb_url().trim());
//		setImageView(head_img, expertInfo.getExpert_thumb_url());
//		setTextContent(name_tv, expertInfo.getExpertName());
//		setTextContent(position_tv, expertInfo.getExpertTitle());
//		setTextContent(introduction_tv, expertInfo.getExpertDetail());
//		setStatus(status_tv, expertInfo.getStatus());
//		setTime();
//		setTextContent(waitnum_tv, expertInfo.getWattingQueueCount() + "");
//		setViewByStatus();
//	}
//
//	private void setViewByStatus() {
//		if (!StringUtil.isEmpty(expertInfo.getStatus())
//				&& !expertInfo.getStatus().equals(MainConst.ONLINE_STATUS)) {
//			alltime_ll.setVisibility(View.GONE);
//			remaintime_ll.setVisibility(View.GONE);
//			waitnum_ll.setVisibility(View.GONE);
//			offline_time_ll.setVisibility(View.VISIBLE);
//			consult_time_tv.setText(expertInfo.getStartDate() + " 至 "
//					+ expertInfo.getEndDate());
//			wantRequestBtn.setText(getString(R.string.offline_request));
//			right_layout.setVisibility(View.GONE);
//		} else {
//			right_layout.setVisibility(View.VISIBLE);
//			alltime_ll.setVisibility(View.VISIBLE);
//			remaintime_ll.setVisibility(View.VISIBLE);
//			waitnum_ll.setVisibility(View.VISIBLE);
//			offline_time_ll.setVisibility(View.GONE);
//			wantRequestBtn.setText(R.string.want_request);
//		}
//	}
//
//	private void setImageView(ImageView iv, String url) {
//		if (!StringUtil.isEmpty(url)) {
//			imageLoader.displayImage(url, iv);
//		}
//	}
//
//	private void setTextContent(TextView tv, String content) {
//		if (!StringUtil.isEmpty(content)) {
//			tv.setText(content.trim());
//		}
//	}
//
//	@SuppressLint("ResourceAsColor")
//	private void setStatus(TextView tv, String isOnline) {
//		if (isOnline != null && isOnline.equals(MainConst.ONLINE_STATUS)) {
//			tv.setVisibility(View.VISIBLE);
//			tv.setText(getString(R.string.online));
//			tv.setBackgroundResource(R.drawable.bg_online);
//		} else if (isOnline != null
//				&& !isOnline.equals(MainConst.ONLINE_STATUS)) {
//			tv.setVisibility(View.GONE);
//			// tv.setText(expertInfo.getStartDate());
//			// tv.setBackgroundColor(R.color.bg_gray);
//		}
//	}
//
//	@SuppressLint("SimpleDateFormat")
//	private void setTime() {
//		alltime_tv.setText(expertInfo.getCurrentChatTime());
//		remaintime_tv.setText(expertInfo.getTimeLest());
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.wantRequestBtn:
//
//			wantRequest();
//			break;
////		case R.id.subcribleBtn:
////			SystemContext.somecode = SystemContext.recode_onebyone;
////			SystemContext.something = SystemContext.rething_onebyone;
////			startPay();
////			break;
//		default:
//			break;
//		}
//	}
//
//	private void wantRequest() {
//		// toQueueActivity();
////		if (!SystemContext.getInstance(this).isUserLogon()) {
////			login();
////			return;
////		}
//		new ExpertQuestTask().execute();
//	}
//
//	class ExpertQuestTask extends AsyncTask<String, Void, String> {
//		String isvip = "1";
//		String notvip = "2";
//		String tokenTimeOut = "917";
//		String tokenTimeOutMsg = "token失效，请重新登陆!";
//		ProgressDialog progressDialog;
//
//		@Override
//		protected void onPreExecute() {
//			progressDialog = new ProgressDialog(ExpertInfoActivity.this);
//			progressDialog.setMessage("验证您的订购信息...");
//			progressDialog.setCancelable(false);
//			progressDialog.show();
//			super.onPreExecute();
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			IUserAccountService accountService = new UserAccountServiceImpl(
//					ExpertInfoActivity.this);//登陆时少传了个token  /linshi
////			try {
////				if (accountService.authorization()) {
////					// 说明是vip用户
////					return isvip;
////				} else {
////					// 不是vip用户，弹出购买框
////					return notvip;
////				}
////			} catch (GLException e) {
////				e.printStackTrace();
////			} catch (ServiceException e) {
////				tokenTimeOutMsg = e.getErrMsg();
////				return tokenTimeOut;
////
////			}
////			return notvip;
//			return isvip;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			try {
//				progressDialog.dismiss();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			if (result.equals(tokenTimeOut)) {
////				ExpertInfoActivity.this.tokenTimeout(tokenTimeOutMsg);
//			} else {
//				if (result.endsWith(isvip)) {
//					toQueueActivity();
//				} else {
//					// Toast.makeText(ExpertInfoActivity.this, "不是啊亲",
//					// Toast.LENGTH_SHORT).show();
////					showSubDialog();
//				}
//			}
//
//			super.onPostExecute(result);
//		}
//	}
//
//	/****
//	 * 
//	 * @author hujunjing 2013-7-9 显示订购提示框
//	 * 
//	 */
////	private void showSubDialog() {
////		if (dialog == null || !dialog.isShowing()) {
////			dialog = showSubcribleDialog();
////			initDialog();
////		}
////	}
//
//	private void initDialog() {
////		dialog.findViewById(R.id.subcribleBtn).setOnClickListener(this);
//	}
//
//	private void startPay() {
//		if (dialog.isShowing()) {
//			dialog.dismiss();
//		}
////		UserPayFlow.startAuth(this, null);
//	}
//
//	/***
//	 * 调转到咨询页面
//	 */
//	private void toQueueActivity() {
//		Intent intent = new Intent(this, QueueActivity.class);
//		intent.putExtra(MainConst.EXPERT_ID, expertInfo.getUid());
//		intent.putExtra(MainConst.ONLINE, expertInfo.getStatus());
//		startActivity(intent);
//		finish();
//	}
//
//}
