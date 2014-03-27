package com.orientmedia.app.cfddj.module;

import com.orientmedia.app.cfddj.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.base.AbstractModule;

/**
 * 订购引导模块
 * 
 * @author chuanglong
 */

public class SpaceModule extends AbstractModule {

	@Override
	public View getView(final Context mContext) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.module_space, null);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(getTitleNode().getText());

		// 引导项的调转
		LinearLayout llGuide = (LinearLayout) view.findViewById(R.id.ll_guide);
		llGuide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AuthUserTask(mContext).execute("");
			}
		});
		return view;
	}

	/**
	 * 判断当前用户是不是Vip用户
	 * 
	 * @author chuanglong
	 * 
	 */
	class AuthUserTask extends AsyncTask<String, Void, String> {

		private Context context;

		private ProgressDialog mProgress;

		public AuthUserTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgress = new ProgressDialog(context);
			mProgress.setTitle("提示信息");
			mProgress.setMessage("正在鉴权...");
			mProgress.setCancelable(true);
			mProgress.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			return "isvip";

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mProgress != null && mProgress.isShowing())
				mProgress.dismiss();

		}
	}

}
