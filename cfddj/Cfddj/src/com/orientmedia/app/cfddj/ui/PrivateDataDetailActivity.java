package com.orientmedia.app.cfddj.ui;

import java.math.BigDecimal;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.base.BaseActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.common.TokenException;
import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
import com.tv189.dzlc.adapter.service.inerface.IUserAccountService;

public class PrivateDataDetailActivity extends BaseActivity {

	private ActionBar mActionBar;

	private String stockCode = "";

	private String stockName = "";

	private String priceYes = "";

	private String priceNow = "";
	private LinearLayout vipOrder, toOrder;
	private RelativeLayout rlstockinfo;

	private Intent intent = null;

	private boolean isVip = true;
	private boolean notVip = false;

	private TextView stockPrice, stockRange, stockRangePercent, stockDesc;

	private ImageView stockImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_private_data);
		initData();
		initActionBar();
		initView();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		vipOrder.setVisibility(View.VISIBLE);
	}

	public void initData() {
		intent = getIntent();
		stockName = intent.getStringExtra("stock_name");
		stockCode = intent.getStringExtra("stock_code");
		priceYes = intent.getStringExtra("price_yes");
		priceNow = intent.getStringExtra("price_now");

	}

	public void initActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle(stockName);
		mActionBar.setSubtitle(stockCode);
	}

	public void initView() {
		stockPrice = (TextView) findViewById(R.id.stock_price);
		stockRange = (TextView) findViewById(R.id.stock_range);
		stockRangePercent = (TextView) findViewById(R.id.stock_range_percent);
		stockImage = (ImageView) findViewById(R.id.stock_image);
		stockDesc = (TextView) findViewById(R.id.stock_desc);
		vipOrder = (LinearLayout) findViewById(R.id.ll_vip_order);
		toOrder = (LinearLayout) findViewById(R.id.ll_toorder);
		rlstockinfo = (RelativeLayout) findViewById(R.id.rlstockinfo);

		toOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});

		float range = Float.parseFloat(priceNow) - Float.parseFloat(priceYes);

		BigDecimal bigDecimal_2 = new BigDecimal(range);
		float range_2 = bigDecimal_2.setScale(2, BigDecimal.ROUND_HALF_UP)
				.floatValue();

		BigDecimal bigDecimal = new BigDecimal(range
				/ Float.parseFloat(priceYes));
		float rangePercent = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)
				.floatValue();
		if (range <= 0.0) {// 股票跌
			rlstockinfo.setBackgroundResource(R.drawable.bj);
		} else {// 股票涨
			rlstockinfo.setBackgroundResource(R.drawable.bj2);
		}
		stockPrice.setText(Float.parseFloat(priceNow) + "");
		stockRange.setText(range_2 + "");
		stockRangePercent.setText(rangePercent + "%");

	}

	// private class CheckIsVip extends AsyncTask<Void, Void, Boolean> {
	//
	// @Override
	// protected Boolean doInBackground(Void... arg0) {
	// // 鉴权
	// IUserAccountService accountService = new UserAccountServiceImpl(
	// PrivateDataDetailActivity.this);
	// try {
	// if (accountService.authorization()) {
	//
	// return isVip;
	// } else {
	// return notVip;
	// }
	// } catch (ServiceException e) {
	// // ExcepUtils.showImpressiveException(PrivateDataDetailActivity.this,
	// // null, e);
	// } catch (TokenException e) {
	// // TODO Auto-generated catch block
	// // ExcepUtils.showImpressiveException(PrivateDataDetailActivity.this,
	// // null, e);
	// }
	// return notVip;
	// }
	//
	// @Override
	// protected void onPostExecute(Boolean result) {
	// if (result == isVip) {
	// AppSetting.getInstance(PrivateDataDetailActivity.this).setVip(
	// isVip);
	// vipOrder.setVisibility(View.GONE);
	// } else {
	// AppSetting.getInstance(PrivateDataDetailActivity.this).setVip(
	// notVip);
	// vipOrder.setVisibility(View.VISIBLE);
	// }
	// super.onPostExecute(result);
	// }
	//
	// }

}
