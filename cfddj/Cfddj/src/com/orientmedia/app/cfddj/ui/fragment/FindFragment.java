package com.orientmedia.app.cfddj.ui.fragment;

import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.module.ModuleFactory;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.ui.AppRecommendActivity;
import com.orientmedia.app.cfddj.ui.BaodianActivity;
import com.orientmedia.app.cfddj.ui.expert.ExpertOneToOneActivity;
import com.orientmedia.app.cfddj.ui.find.ChooseStockActivity;
import com.orientmedia.app.cfddj.ui.find.PrivateDataActivity;
import com.orientmedia.base.BaseFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.base.AbstractModule;
import com.tv189.dzlc.adapter.util.Utils;

@SuppressLint("ResourceAsColor")
public class FindFragment extends BaseFragment implements OnClickListener{
	
	private RelativeLayout expertOneToOne,privateData,allPersonDiscuss,chooseStock,baodianStock;
	private TextView expertOnlineNum,privateDataDesc,allPersonDiscussDesc,chooseStockDesc;
	//
	private RelativeLayout jijin,gegu,dapan,expert,laozuo,baike;
	//
	private RelativeLayout appRecmomend;
	private TextView taskGold;
	
	
	private LinearLayout itemContainer;
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		BaiduStatistics.onMyEvent(this.getActivity(), "3000", this.getClass().getName()+"进入发现页面");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = null;
		if (container == null)
			return null;
		else {
			view = inflater.inflate(R.layout.fragment_find, container, false);
			initView(view);
			initListener();
		}
		return view;
	}
	
	public void initView(View view){
		itemContainer = (LinearLayout) view.findViewById(R.id.item_view_container);
		
		expertOneToOne = (RelativeLayout) view.findViewById(R.id.rl_expert_ono_to_one);
		privateData = (RelativeLayout) view.findViewById(R.id.rl_private_data);
		allPersonDiscuss = (RelativeLayout) view.findViewById(R.id.rl_all_person_discuss);
		chooseStock = (RelativeLayout) view.findViewById(R.id.rl_choose_stock);
		baodianStock = (RelativeLayout) view.findViewById(R.id.rl_baodian_stock);
		
		expertOnlineNum = (TextView) view.findViewById(R.id.expert_online_num);
		privateDataDesc = (TextView) view.findViewById(R.id.private_data_desc);
		allPersonDiscussDesc = (TextView) view.findViewById(R.id.all_person_discuss_desc);
		chooseStockDesc = (TextView) view.findViewById(R.id.choose_stock_desc);
		
		jijin = (RelativeLayout) view.findViewById(R.id.rl_jijin);
		gegu = (RelativeLayout) view.findViewById(R.id.rl_gegu);
		dapan = (RelativeLayout) view.findViewById(R.id.rl_dapan);
		baike = (RelativeLayout) view.findViewById(R.id.rl_baike);
		laozuo = (RelativeLayout) view.findViewById(R.id.rl_laozuo);
		expert = (RelativeLayout) view.findViewById(R.id.rl_expert);
		
		appRecmomend = (RelativeLayout) view.findViewById(R.id.rl_sign_in);
		taskGold = (TextView) view.findViewById(R.id.task_gold);
//		initItemView();
		
	}
	
	private ModuleFactory mModuleFactory = null;
	
	private List<AbstractModule> modules = null;
	
	/*public void initItemView(){
		mModuleFactory = ModuleFactory.newInstance(this.getActivity());
		mModuleFactory.fromXmlString(getResources().openRawResource(R.raw.find));
		modules = mModuleFactory.getModules();
		if(modules != null && !modules.isEmpty()){
			for (AbstractModule module : modules) {
				itemContainer.addView(module.getView(getActivity()));
				itemContainer.addView(newDividerView());
			}
		}
	}*/
	
	
	public View newDividerView(){
		View view = new View(getActivity());
		view.setLayoutParams(new LayoutParams(Utils.getWindowWidth(getActivity()), Utils.dip2px(getActivity(), 10)));
		view.setBackgroundResource(R.color.bg_color);
		return view;
	}
	
	public void initListener(){
		expertOneToOne.setOnClickListener(this);
		privateData.setOnClickListener(this);
		allPersonDiscuss.setOnClickListener(this);
		chooseStock.setOnClickListener(this);
		baodianStock.setOnClickListener(this);
		
		jijin.setOnClickListener(this);
		dapan.setOnClickListener(this);
		baike.setOnClickListener(this);
		laozuo.setOnClickListener(this);
		expert.setOnClickListener(this);
		gegu.setOnClickListener(this);
		
		appRecmomend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_expert_ono_to_one:
			Intent itExp = new Intent(getActivity(), ExpertOneToOneActivity.class);
			startActivity(itExp);
			break;
		case R.id.rl_all_person_discuss:
//			Intent itApd = new Intent(getActivity(), ExpertOneToOneActivity.class);
//			startActivity(itExp);
			break;
		case R.id.rl_private_data:
			Intent itPd = new Intent(getActivity(), PrivateDataActivity.class);
			startActivity(itPd);
			break;
		case R.id.rl_choose_stock://一键选股
			Intent itCs = new Intent(getActivity(), ChooseStockActivity.class);
			startActivity(itCs);
			break;
		case R.id.rl_baodian_stock://赚钱宝典
			Intent itDd = new Intent(getActivity(), BaodianActivity.class);
			startActivity(itDd);
			break;
		case R.id.rl_sign_in:
			Intent itSi = new Intent(getActivity(), AppRecommendActivity.class);
			startActivity(itSi);
			break;
		default:
			break;
		}
	}
}
