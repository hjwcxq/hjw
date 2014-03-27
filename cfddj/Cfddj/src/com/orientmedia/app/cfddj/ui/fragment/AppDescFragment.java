package com.orientmedia.app.cfddj.ui.fragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.base.BaseFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;

public class AppDescFragment extends BaseFragment{
	
	public static final String TAG = "AppDescFragment" ;

	private TextView appDesc ;
	
	public AppDescFragment(){
		
	}
	
	public static AppDescFragment newInstance(String appDesc){
		AppDescFragment fragment = new AppDescFragment();
		Bundle b = new Bundle();
		b.putString("appDesc", appDesc);
		fragment.setArguments(b);
		return fragment;
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_desc, null);
		initView(view);
		return view;
	}
	
	public void initView(View view){
		appDesc = (TextView) view.findViewById(R.id.app_desc);
		appDesc.setText(getArguments().getString("appDesc"));
	}
	
}
