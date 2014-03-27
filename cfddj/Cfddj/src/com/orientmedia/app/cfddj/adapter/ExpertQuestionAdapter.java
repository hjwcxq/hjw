package com.orientmedia.app.cfddj.adapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.tool.FileDownloader;
import com.orientmedia.app.cfddj.tool.StringUtil;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tv189.dzlc.adapter.po.sqlpo.VideoChatInfo;

/****
 * 
 * @author hujunjing 2013-7-15 视频一对一模块，专家和用户互动留言
 */
public class ExpertQuestionAdapter extends BaseAdapter {

	public ExpertQuestionAdapter(Context ctx, List<VideoChatInfo> infoList) {
		this.ctx = ctx;
		mInflater = LayoutInflater.from(ctx);
		this.infoList = infoList;
	}

	@Override
	public int getCount() {
		return infoList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		VideoChatInfo info = infoList.get(position);
		// 找了半天，你在这里啊!!!
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.item_question_online, null);
			viewHolder = new ViewHolder();
			viewHolder.question = (TextView) convertView
					.findViewById(R.id.question_tv);
			viewHolder.ima_voice = (ImageView) convertView
					.findViewById(R.id.ima_voice);

			viewHolder.left_img = (ImageView) convertView
					.findViewById(R.id.left_img);
			viewHolder.left_img_2 = (ImageView) convertView
					.findViewById(R.id.left_img_2);

			viewHolder.name = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.time = (TextView) convertView.findViewById(R.id.time_tv);
			viewHolder.name2 = (TextView) convertView
					.findViewById(R.id.name_tv_2);
			viewHolder.time2 = (TextView) convertView
					.findViewById(R.id.time_tv_2);

			viewHolder.below_layout_2 = (LinearLayout) convertView
					.findViewById(R.id.below_layout_2);
			viewHolder.below_layout = (LinearLayout) convertView
					.findViewById(R.id.below_layout);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (info.getMsgType().equals("01")) {
			// setVoiceBg(viewHolder.ima_voice,info.getType());
			viewHolder.question.setVisibility(View.GONE);
			viewHolder.left_img.setVisibility(View.GONE);
			viewHolder.below_layout.setVisibility(View.GONE);

			viewHolder.ima_voice.setVisibility(View.VISIBLE);
			viewHolder.left_img_2.setVisibility(View.VISIBLE);
			viewHolder.below_layout_2.setVisibility(View.VISIBLE);

			setName(viewHolder.name2, info);
			final String get_url = info.getContent();
			// final String aaa = info.getQuestionContent();
			viewHolder.ima_voice.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// 要下载的文件路径
					String urlDownload = get_url;
					// String urlDownload;
					// urlDownload =
					// "http://5.26923.com/download/ring/000/097/29cd31194920c7b7f078abf3b54a3da9.m4a";

					// 获得存储卡路径，构成 保存文件的目标路径
					String dirName = "";
					dirName = Environment.getExternalStorageDirectory()
							+ "/gplz/my/";
					File f = new File(dirName);
					if (!f.exists()) {
						f.mkdir();
					}
					// 启动文件下载线程
					new FileDownloader(urlDownload, dirName, handler).start();

				}
			});
			// setTextContent(viewHolder.name, info.getUserNickname());
			setTextContent(viewHolder.time2, info.getChatDate() + "");
		} else {
			setContentBg(viewHolder.question, info.getType());

			viewHolder.question.setVisibility(View.VISIBLE);
			viewHolder.left_img.setVisibility(View.VISIBLE);
			viewHolder.below_layout.setVisibility(View.VISIBLE);

			viewHolder.ima_voice.setVisibility(View.GONE);
			viewHolder.left_img_2.setVisibility(View.GONE);
			viewHolder.below_layout_2.setVisibility(View.GONE);

			setTextContent(viewHolder.question, info.getContent());
			setName(viewHolder.name, info);
			// setTextContent(viewHolder.name, info.getUserNickname());
			setTextContent(viewHolder.time, info.getChatDate() + "");
		}

		return convertView;
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.arg1 == 1) {
				playRing("mnt/sdcard/gplz/my/voice.m4a", "正在播放");
			}
		}

	};

	static class ViewHolder {
		TextView question, name, time, name2, time2;
		ImageView ima_voice, left_img, left_img_2;
		LinearLayout below_layout, below_layout_2;
	}

	private void setContentBg(TextView tv, String type) {
		if (!StringUtil.isEmpty(type)) {
			int bg = type.equals(VideoChatInfo.TYPE_EXPERT_SAY) ? R.drawable.bg_textbox
					: R.drawable.bg_textbox_white;
			tv.setBackgroundResource(bg);
		}
	}

	// private void setVoiceBg(ImageView ima, String type) {
	// if (!StringUtil.isEmpty(type)) {
	// int bg = type.equals(VideoChatInfo.TYPE_EXPERT_SAY) ?
	// R.drawable.bg_textbox
	// : R.drawable._sound;
	// ima.setBackgroundResource(bg);
	// // int
	// //
	// bg=type.equals(VideoChatInfo.TYPE_EXPERT_SAY)?R.drawable._sound:R.drawable._sound;
	// // tv.setBackgroundResource(bg);
	// }
	// }

	private void setName(TextView tv, VideoChatInfo info) {
		String type = info.getType();
		if (!StringUtil.isEmpty(type)) {
			String name = type.equals(VideoChatInfo.TYPE_EXPERT_SAY) ? info
					.getExpertName() : info.getUserNickname();
			tv.setText(name);
		}
	}

	private void setTextContent(TextView tv, String content) {
		if (!StringUtil.isEmpty(content)) {
			tv.setText(content);
		}
	}

	// 删除老文件
	void scanOldFile() {
		// String uid = SystemContext.getInstance((BaseActivity) mActivity)
		// .getUid();
		// File file = new File(Environment.getExternalStorageDirectory(),
		// "/gplz/my/"+uid+"_voice.amr");
		File file = new File(Environment.getExternalStorageDirectory(),
				"/gplz/my/myvoice.m4a");
		if (file.exists()) {
			file.delete();
		}
	}

	// 播放录音
	public void playRing(String filePath, String msg) {
		// scanOldFile();
		if (pgPlayDialog == null) {
			pgPlayDialog = new ProgressDialog(ctx);
		}
		pgPlayDialog.setButton("结束", onDialogPlayButtonClickListener);
		pgPlayDialog.setCancelable(false);
		pgPlayDialog.setMessage(msg);
		pgPlayDialog.show();

		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// dialog.setContentView(R.layout.my_dialog_sound);
		// dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
		//
		// dialog.show();

		if (null == mMediaPlayer) {
			mMediaPlayer = new MediaPlayer();
		}
		try {
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(filePath);
			/* 准备播放 */
			mMediaPlayer.prepare();
			/* 开始播放 */
			mMediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			dialog.dismiss();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			dialog.dismiss();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dialog.dismiss();
			e.printStackTrace();
		}
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer arg0) {
				try {
					pgPlayDialog.dismiss();
					mMediaPlayer.stop();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
	}

	// 播放dialog停止
	public DialogInterface.OnClickListener onDialogPlayButtonClickListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			try {
				mMediaPlayer.stop();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};
	private Context ctx;
	private LayoutInflater mInflater;
	private List<VideoChatInfo> infoList;
	private ProgressDialog pgPlayDialog;
	private MediaPlayer mMediaPlayer;
	private Dialog dialog;
}
