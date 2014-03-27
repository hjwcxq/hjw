//package tv.aniu.app.dzlc.module.action;
//
//import java.util.Map;
//import java.util.Set;
//
//import tv.aniu.app.dzlc.ui.ContentActivity;
//
//import android.content.Context;
//import android.content.Intent;
//
//import com.tv189.dzlc.adapter.po.base.AbstractAction;
//
//public class ContentAction extends AbstractAction{
//	
//	private Context context;
//	
//	public  ContentAction(Context context){
//		this.context = context;
//	}
//	@Override
//	public void jumpByActionType(Map<String, String> paraMap) {
//		// TODO Auto-generated method stub
//		Intent it = new Intent(context, ContentActivity.class);
//		if(paraMap != null && !paraMap.isEmpty()){
//			Set<String> keys = paraMap.keySet();
//			for (String string : keys) {
//				it.putExtra(string, paraMap.get(string));
//			}
//		}
//		context.startActivity(it);
//	}
//	
//}
