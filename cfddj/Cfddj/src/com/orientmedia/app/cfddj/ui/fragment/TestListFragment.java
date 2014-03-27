package com.orientmedia.app.cfddj.ui.fragment;

import com.orientmedia.app.cfddj.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

import com.actionbarsherlock.app.SherlockListFragment;

public class TestListFragment extends Fragment {
	
	private String[] adapterData;
	
	private ListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =  inflater.inflate(R.layout.fragment_listview, container ,false);
		listView = (ListView)view.findViewById(R.id.listView);
		adapterData = new String[] { "Afghanistan", "Albania",  "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia","Aruba", 
				"Australia", "Austria", "Azerbaijan", "Bahrain","Bangladesh", "Barbados",
				"Belarus", "Belgium", "Belize","Benin", "Bermuda", "Bhutan", "Bolivia", 
				"Bosnia and Herzegovina", "Botswana", "Bouvet Island" };
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, adapterData);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(mScrollListener);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	private OnScrollListener mScrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			view.requestDisallowInterceptTouchEvent(scrollState != OnScrollListener.SCROLL_STATE_IDLE);
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			
		}
	};
}
