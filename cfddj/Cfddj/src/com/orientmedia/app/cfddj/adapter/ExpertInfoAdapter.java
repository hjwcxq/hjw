//package tv.aniu.app.dzlc.adapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import tv.aniu.app.dzlc.R;
//import tv.aniu.app.dzlc.tool.imageutils.ImageFetcher;
//
//import com.tv189.dzlc.adapter.po.sqlpo.ExpertsInfo;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class ExpertInfoAdapter extends BaseAdapter {
//
//	private Context ctx;
//
//	private List<ExpertsInfo> expertList = new ArrayList<ExpertsInfo>();
//	
//	private ImageFetcher mImageFetcher ;
//
//	public ExpertInfoAdapter(Context ctx, List<ExpertsInfo> list) {
//		this.ctx = ctx;
//		if (list != null)
//			expertList.addAll(list);
//		mImageFetcher = new ImageFetcher(ctx);
//	}
//
//	@Override
//	public int getCount() {
//		return expertList.size();
//	}
//
//	@Override
//	public ExpertsInfo getItem(int arg0) {
//		// TODO Auto-generated method stub
//		return expertList.get(arg0);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		if (expertList != null && expertList.size() > 0
//				&& position < expertList.size()) {
//			ViewHolder viewHolder;
//			ExpertsInfo expertInfo = expertList.get(position);
//			if (convertView == null) {
//				convertView = LayoutInflater.from(ctx).inflate(R.layout.item_expert, null);
//				viewHolder = new ViewHolder();
//				viewHolder.expert_head_img = (ImageView) convertView
//						.findViewById(R.id.img_expert_head);
//				
//				viewHolder.expert_name = (TextView) convertView
//						.findViewById(R.id.expert_name);
////				viewHolder.expert_title = (TextView) convertView
////						.findViewById(R.id.expert_title);
//				viewHolder.expert_intro = (TextView) convertView
//						.findViewById(R.id.expert_intro);
//				
//				convertView.setTag(viewHolder);
//			} else {
//				viewHolder = (ViewHolder) convertView.getTag();
//			}
//			
//			viewHolder.expert_name.setText(expertInfo.getExpertName());
//			viewHolder.expert_intro.setText(expertInfo.getExpertDetail());
////			viewHolder.expert_title.setText(expertInfo.getExpertTitle());
////			setStatus(viewHolder.expert_status,  viewHolder.wantRequestBtn, expertInfo.getOnline(), expertInfo);
//			mImageFetcher.loadThumbnailImage(expertInfo.getExpert_thumb_url(),viewHolder.expert_head_img);
//			return convertView;
//		}
//		return null;
//
//	}
//
////	@SuppressLint("ResourceAsColor")
////	private void setStatus(TextView tv, Button btn, String isOnline,
////			ExpertsInfo expertInfo) {
////		if (isOnline != null && isOnline.equals("true")) {
////			tv.setText(ctx.getString(R.string.online));
////			tv.setBackgroundResource(R.drawable.bg_online);
////			btn.setVisibility(View.VISIBLE);
////		} else if (isOnline != null && isOnline.equals("false")
////				&& expertInfo.getStartDate() != null) {
////			tv.setText(expertInfo.getStartDate());
////			tv.setBackgroundColor(R.color.bg_gray);
////			btn.setVisibility(View.GONE);
////		}
////	}
//
//
//	class ViewHolder {
//		TextView expert_name,expert_intro;
//		ImageView expert_head_img;
//	}
//
//}
