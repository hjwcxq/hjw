//package tv.aniu.app.dzlc.ui;
//
//import tv.aniu.app.dzlc.R;
//import tv.aniu.app.dzlc.Listener.CallBackListener;
//import tv.aniu.app.dzlc.task.OrderTask;
//import tv.aniu.app.dzlc.tool.BaiduStatistics;
//import tv.aniu.app.dzlc.tool.ExcepUtils;
//import tv.base.BaseActivity;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import com.tv189.dzlc.adapter.config.AppSetting;
//import com.tv189.dzlc.adapter.po.common.ServiceException;
//import com.tv189.dzlc.adapter.po.common.TokenException;
//import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
//import com.tv189.dzlc.adapter.util.Utils;
//
//public class VipOrderActivity extends BaseActivity implements OnClickListener {
//
//	private Button becomeVip;
//
//	private LinearLayout orderTip;
//
//	private LinearLayout orserByZfb, orderByCard;
//
//	private AppSetting setting = null;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_vip_order);
//		BaiduStatistics.onMyEvent(this, "1100", "进入订购导航页面");
//		setting = AppSetting.getInstance(this);
//		initView();
//		initListener();
//	}
//
//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//
//		if (getIntent().getBooleanExtra("isVip", false)) {
//			becomeVip.setText("已订购");
//			becomeVip.setEnabled(false);
//			orserByZfb.setVisibility(View.GONE);
//			orderByCard.setVisibility(View.GONE);
//		} else {
//			becomeVip.setEnabled(true);
//			becomeVip.setText("立即订购");
//			orserByZfb.setVisibility(View.VISIBLE);
//			orderByCard.setVisibility(View.VISIBLE);
//		}
//
//	}
//
//	public void initView() {
//		becomeVip = (Button) findViewById(R.id.become_vip_btn);
//		orderTip = (LinearLayout) findViewById(R.id.ll_order_tip);
//		orserByZfb = (LinearLayout) findViewById(R.id.ll_pay_by_zfb);
//		orderByCard = (LinearLayout) findViewById(R.id.ll_pay_by_card);
//
//	}
//
//	public void initListener() {
//		becomeVip.setOnClickListener(this);
//		orserByZfb.setOnClickListener(this);
//		orderByCard.setOnClickListener(this);
//	}
//
//	private CallBackListener callBack = new CallBackListener() {
//		@Override
//		public void executeSucc() {
//			// TODO Auto-generated method stub
//			showCusToast("订购成功");
//		}
//
//		@Override
//		public void executeFail() {
//			// TODO Auto-generated method stub
//			showCusToast("订购失败");
//		}
//
//		@Override
//		public void postExecute() {
//			// TODO Auto-generated method stub
//
//		}
//	};
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.become_vip_btn:// 走账单支付
//			if (setting.isVip()) {
//				becomeVip.setText("已定购");
//			} else {
//				if ("1".equals(setting.getPhoneStatu())) {
//					new OrderTask(VipOrderActivity.this).userBillPay(callBack);
//				} else {
//					if (Utils.isTelecomByNumber(setting.getPhoneNumber())) {
//						new OrderTask(VipOrderActivity.this)
//								.userBillPay(callBack);
//					} else {
//						showCusToast("你不是电信用户，无法使用账单支付");
//					}
//				}
//			}
//			break;
//		case R.id.ll_pay_by_card:
//			Intent cardOrder = new Intent(VipOrderActivity.this,
//					CardOrderActivity.class);
//			startActivityForResult(cardOrder, 10);
//			break;
//		case R.id.ll_pay_by_zfb:
//			new OrderTask(VipOrderActivity.this).userZfbPay(callBack);
//			break;
//		default:
//			break;
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(arg0, arg1, arg2);
//		switch (arg0) {
//		case 10:
//			if (arg1 == RESULT_OK) {
//				showCusToast("订购成功");
//				becomeVip.setText("已订购");
//				becomeVip.setEnabled(false);
//				orserByZfb.setVisibility(View.GONE);
//				orderByCard.setVisibility(View.GONE);
//			} else {
//				becomeVip.setEnabled(true);
//				becomeVip.setText("立即订购");
//				orserByZfb.setVisibility(View.VISIBLE);
//				orderByCard.setVisibility(View.VISIBLE);
//			}
//
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	/**
//	 * 判断当前用户是不是Vip用户
//	 * 
//	 * @author chuanglong
//	 * 
//	 */
//	class AuthUserTask extends AsyncTask<String, Void, Boolean> {
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			showNetLoadingProgressDialog("正在鉴权...");
//		}
//
//		@Override
//		protected Boolean doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			boolean flag = false;
//			UserAccountServiceImpl userService = new UserAccountServiceImpl(
//					VipOrderActivity.this);
//			try {
//				flag = userService.authorization();
//			} catch (ServiceException e) {
//				ExcepUtils.showImpressiveException(VipOrderActivity.this,
//						"签权失败", e);
//			} catch (TokenException e) {
//				ExcepUtils.showImpressiveException(VipOrderActivity.this,
//						"签权失败", e);
//			}
//			return Boolean.valueOf(flag);
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			hideNetLoadingProgressDialog();
//			if (result.booleanValue()) {
//				orderTip.setVisibility(View.GONE);
//			} else {
//				showCusToast("签权失败");
//			}
//		}
//	}
//
//}
