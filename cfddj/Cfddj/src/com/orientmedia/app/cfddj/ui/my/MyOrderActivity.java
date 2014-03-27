//package tv.aniu.app.dzlc.ui.my;
//
//import java.util.List;
//
//import org.apache.http.util.ExceptionUtils;
//
//import tv.aniu.app.dzlc.R;
//import tv.aniu.app.dzlc.adapter.MyOrderAdapter;
//import tv.aniu.app.dzlc.tool.BaiduStatistics;
//import tv.aniu.app.dzlc.tool.ExcepUtils;
//import tv.base.BaseActivity;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.ListView;
//
//import com.tv189.dzlc.adapter.po.common.ServiceException;
//import com.tv189.dzlc.adapter.po.order.OrderInfo;
//import com.tv189.dzlc.adapter.po.order.OrderInfoListApiResponse;
//import com.tv189.dzlc.adapter.service.impl.OrderServiceImpl;
//import com.tv189.dzlc.adapter.service.inerface.OrderService;
//
//public class MyOrderActivity extends BaseActivity {
//
//	private ListView listView;
//
//	private List<OrderInfo> orderList = null;
//
//	private MyOrderAdapter orderAdapter = null;
//
//	
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_listview);
//		BaiduStatistics.onMyEvent(this, "4100", "我的订单");
//		initView();
//	}
//
//	public void initView() {
//		listView = (ListView) findViewById(R.id.listView);
//		orderAdapter = new MyOrderAdapter(this, orderList);
//		listView.setAdapter(orderAdapter);
//		new GetMyOrderInfo().execute();
//	}
//
//	
//	/**
//	 * 获取我的订购
//	 * 
//	 * @author EsaFans
//	 */
//	class GetMyOrderInfo extends AsyncTask<Void, Void, Boolean> {
//		
//		private OrderInfoListApiResponse response;
//		
//		@Override
//		protected void onPreExecute() {
//			showLoadingIcon();
//		}
//
//		@Override
//		protected Boolean doInBackground(Void... params) {
//			boolean flag = false;
//			OrderService orderService = new OrderServiceImpl(
//					MyOrderActivity.this);
//			try {
//				response = orderService.myOrderList();
//				if (response != null && response.isSuccess()) {
//					flag = true;
//					orderList = response.getInfo() ;
//				}
//			} catch (ServiceException e) {
//				// TODO Auto-generated catch block
//				ExcepUtils.showImpressiveException(MyOrderActivity.this,
//						"加载我的订单列表失败", e);
//			}
//			return Boolean.valueOf(flag);
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			hideLoadingIcon();
//			if (result.booleanValue()) {
//				orderAdapter.setItems(orderList);
//			} else {
//				if(response != null)
//					showCusToast(response.getMsg());
//				else
//					showCusToast("加载失败");
//			}
//		}
//	}
//}
