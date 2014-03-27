package com.orientmedia.app.cfddj.adapter;

import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.stock.NewStockInfo;

/**
 * 一键选股适配器
 * 
 * @author chuanglong
 * 
 */
public class ChooseStockAdapter extends BaseAdapter {

	private Context mContext;
	
	private List<NewStockInfo> items = new ArrayList<NewStockInfo>();

	public ChooseStockAdapter(Context context,List<NewStockInfo> list) {
		this.mContext = context;
		if(list != null)
			items.addAll(list);
	}
	
	public void setItems(List<NewStockInfo> list,boolean isClear){
		if(isClear)
			items.clear();
		if(list != null)
			items.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public NewStockInfo getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_choose_stock, null);
			holder = new ViewHolder();
			holder.baseDate = (TextView) view.findViewById(R.id.base_date);
			holder.jinProfitPercentNum = (TextView) view
					.findViewById(R.id.jin_profit_percent_num);
			holder.maoProfitPercentNum = (TextView) view
					.findViewById(R.id.mao_profit_percent_num);
			holder.shoupanNum = (TextView) view.findViewById(R.id.shoupan_num);
			holder.stockCatogery = (TextView) view
					.findViewById(R.id.stock_category);
			holder.stockNameAndCode = (TextView) view
					.findViewById(R.id.stock_name_and_code);
			holder.themeName = (TextView) view.findViewById(R.id.theme_name);
			holder.tongbiNum = (TextView) view.findViewById(R.id.tongbi_num);
			holder.yearsAddRange = (TextView) view
					.findViewById(R.id.years_add_range);
			
			view.setTag(holder);
			
		}else{
			holder = (ViewHolder) view.getTag();
		}
		
		NewStockInfo stock = items.get(position);
		if(stock != null) {
			holder.baseDate.setText(stock.getBasicdate());
			holder.jinProfitPercentNum.setText(stock.getNetprofit());
			holder.stockNameAndCode.setText(stock.getReferred()+"("+stock.getCode().toString()+")");
			holder.stockCatogery.setText(stock.getIndustry());
			holder.themeName.setText(stock.getTheme());
			holder.maoProfitPercentNum.setText(stock.getProfitmargin());
			holder.shoupanNum.setText(""+stock.getClosing());
			holder.yearsAddRange.setText(stock.getGrowth());
			holder.tongbiNum.setText(stock.getMajor());
		}
		return view;
	}

	class ViewHolder {

		TextView stockNameAndCode;

		TextView stockCatogery;

		TextView themeName;

		TextView baseDate;

		TextView shoupanNum;

		TextView tongbiNum;

		TextView jinProfitPercentNum;

		TextView maoProfitPercentNum;

		TextView yearsAddRange;

	}

}
