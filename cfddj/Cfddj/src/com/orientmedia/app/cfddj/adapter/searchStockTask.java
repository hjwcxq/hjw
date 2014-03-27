//package tv.aniu.app.dzlc.adapter;
//import mobi.dreambox.frameowrk.core.util.StringUtil;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//
//import com.tv189.dzlc.adapter.po.stock.StockInfo;
//import com.tv189.dzlc.adapter.service.impl.StockServiceImpl;
//import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
//import com.tv189.dzlc.adapter.service.inerface.IUserAccountService;
//import com.tv189.dzlc.adapter.service.log.ReportLogService;
//import com.tv189.dzlc.adapter.service.log.TyLogInfo;
//
//	/**
//	 * 加载web
//	 * 
//	 */
//	public class searchStockTask extends AsyncTask<String, Void, String> {
//
//		String SUCCESS = "successpre";
//		String ERROR = "errorpre";
//		ProgressDialog progressDialog;
//		StockInfo searchStockDetail;
//		String keyword;
//
//		@Override
//		protected void onPostExecute(String result) {
//			try {
//				progressDialog.dismiss();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			if (result.equalsIgnoreCase(SUCCESS)) {
//				Intent intent = new Intent(mActivity, DataActivity.class);
//				intent.putExtra("url", searchStockDetail.getUrl());
//				intent.putExtra("code", searchStockDetail.getKeyword());
//				intent.putExtra("name", searchStockDetail.getStockName());
//				mActivity.startActivity(intent);
//				ReportLogService.getInstance().addLog(mActivity,
//						TyLogInfo.ACCESSTYPE_CLIENT,
//						TyLogInfo.ACTION_OPEN_STOCK, null, null, result,
//						"success", keyword);
//			} else if (!StringUtil.isEmpty(result)
//					&& result.equals(MyConstant.NOT_VIP)) {
//				new UserPayFlow().startAuth(mActivity, null);
//				ReportLogService.getInstance().addLog(mActivity,
//						TyLogInfo.ACCESSTYPE_CLIENT,
//						TyLogInfo.ACTION_OPEN_STOCK, null, null, result,
//						"fail", keyword);
//			}
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			try {
//				IUserAccountService accountService = new UserAccountServiceImpl(
//						mActivity);
//
//				if (accountService.authorization()) {
//					keyword = params[0];
//					StockServiceImpl requestService = new StockServiceImpl(
//							mActivity);
//					searchStockDetail = requestService.searchStock(keyword);
//					if (searchStockDetail != null) {
//
//						return SUCCESS;
//					} else {
//						return ERROR;
//					}
//				} else {
//					return MyConstant.NOT_VIP;
//				}
//			} catch (Exception e) {
//				return ERROR;
//			}
//		}
//
//		@Override
//		protected void onPreExecute() {
//			try {
//				progressDialog.dismiss();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			progressDialog = new ProgressDialog(mActivity);
//			progressDialog.setMessage("正在获取股票信息");
//			progressDialog.setCancelable(true);
//			progressDialog.show();
//		}
//	}