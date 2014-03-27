package com.orientmedia.app.cfddj.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orientmedia.app.cfddj.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;

public class StockAdapter extends BaseAdapter {

	private List<StockInfo> items = new ArrayList<StockInfo>();

	private String price_now = "";

	private String price_yesterday = "";

	private String stock_AD = "";

	private Context context;

	public StockAdapter(Context context, List<StockInfo> list) {
		this.context = context;
		if (list != null)
			items.addAll(list);
	}

	public void setItems(List<StockInfo> list, boolean isRefresh) {
		if (isRefresh) {
			items.clear();
		}
		if (list != null) {
			items.addAll(list);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public StockInfo getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private Map<String, HashMap<String, String>> stockPriceInfos = new HashMap<String, HashMap<String, String>>();

	public void setStockPriceInfo(Map<String, HashMap<String, String>> map,
			boolean isClear) {
		if (isClear)
			stockPriceInfos.clear();
		stockPriceInfos.putAll(map);
	}

	public Map<String, HashMap<String, String>> getStockPriceInfo() {
		return stockPriceInfos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (items != null && items.size() > 0 && position < items.size()) {
			View view = convertView;
			ViewHolder holder = null;
			if (view == null) {
				view = LayoutInflater.from(context).inflate(
						R.layout.item_stocklist, null);
				holder = new ViewHolder();
				holder.stockCode = (TextView) view
						.findViewById(R.id.stock_code);
				holder.stockName = (TextView) view
						.findViewById(R.id.stock_name);
				holder.stockPrice = (TextView) view
						.findViewById(R.id.stock_price);
				holder.stockRange = (TextView) view
						.findViewById(R.id.stock_range);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			StockInfo stock = items.get(position);
			if (stock != null) {
				holder.stockName.setText(stock.getStockName());
				holder.stockCode.setText(stock.getStockCode());
				HashMap<String, String> map = getStockPriceInfo().get(
						stock.getStockCode());
				if (map != null && !map.isEmpty()) {
					price_yesterday = map.get("price_yes");
					price_now = map.get("price_now");
				}

				holder.stockPrice.setText(price_now);

				if (price_yesterday != null && !"".equals(price_yesterday)
						&& price_now != null && !"".equals(price_now)) {
					// 涨跌幅=（今日现价-昨日收盘价）/昨日收盘价
					float f_price_yesterday = Float.parseFloat(price_yesterday);
					float f_price_now = Float.parseFloat(price_now);
					BigDecimal bigDecimal = new BigDecimal(
							(f_price_now - f_price_yesterday)
									/ f_price_yesterday);
					float f_stock_AD = bigDecimal.setScale(4,
							BigDecimal.ROUND_HALF_UP).floatValue() * 100;
					// bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)
					// 表明四舍五入，保留两位小数
					stock_AD = String.valueOf(f_stock_AD);
					// holder.label2.setText(price_yesterday);
					if (stock_AD.length() > 4) {
						stock_AD = stock_AD.substring(0, 4);
					}

					if (f_stock_AD > 0.0) {
						holder.stockRange.setText("  " + stock_AD + "%");
						holder.stockPrice.setTextColor(0xffe73838);// 红
						holder.stockRange.setTextColor(0xffe73838);// 红
					} else if (f_stock_AD < 0.0) {
						holder.stockRange.setText(stock_AD + "%");
						holder.stockPrice.setTextColor(0xff1f991f);// 绿
						holder.stockRange.setTextColor(0xff1f991f);// 绿
					} else if (f_stock_AD == 0.0) {
						holder.stockRange.setText("  " + stock_AD + "%");
						holder.stockPrice.setTextColor(0xff404040);// 黑
						holder.stockRange.setTextColor(0xff404040);// 黑
					}
				}
			}
			view.setTag(R.id.ll_stock_item, stock);
			return view;
		}
		return null;
	}

	class ViewHolder {
		TextView stockCode;
		TextView stockPrice;
		TextView stockRange;
		TextView stockName;
	}

}
