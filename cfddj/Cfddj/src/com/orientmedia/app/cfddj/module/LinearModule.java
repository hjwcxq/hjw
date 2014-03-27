//package tv.aniu.app.dzlc.module;
//
//import tv.aniu.app.dzlc.R;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.tv189.dzlc.adapter.po.base.AbstractModule;
//import com.tv189.dzlc.adapter.util.Utils;
//
//public class LinearModule extends AbstractModule{
//
//	private TextView linearTitle;
//	
//	private TextView linearDesc;
//	
//	private RelativeLayout findItem ;
//	
//	
//	@Override
//	public View getView(Context cont) {
//		// TODO Auto-generated method stub
//		View view = LayoutInflater.from(cont).inflate(R.layout.module_linear, null);
//		
//		initView(cont, view);
//		return view;
//	}
//
//	
//	public void initView(Context cont,View view){
//		findItem = (RelativeLayout) view.findViewById(R.id.rl_find_item);
//		linearTitle = (TextView) view.findViewById(R.id.linear_title);
//		linearDesc = (TextView) view.findViewById(R.id.linear_desc);
//		linearTitle.setText(getTitleNode().getText());
//		if(getDescNode().isShow())
//			linearDesc.setText(getDescNode().getText());
//		else
//			linearDesc.setVisibility(View.GONE);
//		findItem.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				getItems().get(0).getAction().jumpByActionType(getItems().get(0).getAction().getParaMap());
//			}
//		});
//	}
//	
//}
