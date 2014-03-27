//package tv.aniu.app.dzlc.ui;
//
//import tv.aniu.app.dzlc.R;
//import tv.aniu.app.dzlc.tool.ExcepUtils;
//import tv.base.BaseActivity;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.tv189.dzlc.adapter.po.common.ApiResponse;
//import com.tv189.dzlc.adapter.po.common.ServiceException;
//import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
//
//public class CardOrderActivity extends BaseActivity implements OnClickListener {
//	
//	private EditText cardNumber,cardPassword;
//	
//	private Button cardOrder ;
//	
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_card_order);
//		initView();
//		initListener();
//	}
//	
//	public void initView(){
//		cardNumber = (EditText) findViewById(R.id.card_number);
//		cardPassword = (EditText) findViewById(R.id.card_pass);
//		cardOrder = (Button) findViewById(R.id.card_order);
//	}
//	
//	public void initListener(){
//		cardOrder.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.card_order:
//			String number = cardNumber.getText().toString();
//			String password = cardPassword.getText().toString();
//			if(number == null || "".equals(number)){
//				showCusToast("激活卡号不能为空");
//				return ;
//			}
//			if(password == null || "".equals(password)){
//				showCusToast("激活卡密码不能为空");
//				return ;
//			}
//			
//			new PayByCardTask().execute(new String[]{number,password}) ;
//			break;
//
//		default:
//			break;
//		}
//	}
//	
//	class PayByCardTask extends AsyncTask<String, String, Boolean> {
//	
//		ApiResponse response = null ;
//		
//		@Override
//		protected void onPreExecute() {
//			showLoadingIcon();
//		}
//
//		@Override
//		protected Boolean doInBackground(String... params) {
//			boolean flag = false;
//			UserAccountServiceImpl cardService = new UserAccountServiceImpl(CardOrderActivity.this);
//			try {
//				response = cardService.cardActive("", params[1],
//						params[0]);
//				if (response.getData() != null && response.getData().equals("0")) {
//					flag = true;
//				}
//			} catch (ServiceException e) {
//				ExcepUtils
//						.showImpressiveException(CardOrderActivity.this, "激活码支付失败", e);
//			}
//			return Boolean.valueOf(flag);
//
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			super.onPostExecute(result);
//			try {
//				hideLoadingIcon();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			if (result.booleanValue()) {
//				setResult(RESULT_OK);
//				finish();
//			} else {
//				setResult(RESULT_CANCELED);
//				if(response != null){
//					showCusToast(response.getMsg());
//				}else{
//					showCusToast("卡激活失败");
//				}
//			}
//		}
//	}
//
//
//}
