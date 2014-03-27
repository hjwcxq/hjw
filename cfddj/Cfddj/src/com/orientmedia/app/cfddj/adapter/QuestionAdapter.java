package com.orientmedia.app.cfddj.adapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.CallBackListener;
import com.orientmedia.app.cfddj.stock.DataActivity;
import com.orientmedia.app.cfddj.task.FileDownTask;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.tool.FileDownloader;
import com.orientmedia.app.cfddj.tool.FileOperator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.common.TokenException;
import com.tv189.dzlc.adapter.po.question.AnswerInfo;
import com.tv189.dzlc.adapter.po.question.QuestionInfo;
import com.tv189.dzlc.adapter.po.question.ReplaceKeywordInfo;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;
import com.tv189.dzlc.adapter.service.impl.StockServiceImpl;
import com.tv189.dzlc.adapter.service.impl.UserAccountServiceImpl;
import com.tv189.dzlc.adapter.service.inerface.IUserAccountService;
import com.tv189.dzlc.adapter.service.log.ReportLogService;
import com.tv189.dzlc.adapter.service.log.TyLogInfo;

public class QuestionAdapter extends BaseAdapter {

	/**
	 * 每次刷新留言时判断用户是否是VIP
	 */

	public List<QuestionInfo> items = new ArrayList<QuestionInfo>();

	private Context mContext;
	/**
	 * @Fields mInflater : 布局填充器
	 */

	private ProgressDialog pgPlayDialog;

	private MediaPlayer mMediaPlayer;

	private boolean is_vip = false;

	public QuestionAdapter(Context cont, List<QuestionInfo> list) {
		this.mContext = cont;
		if (list != null)
			items.addAll(list);
	}

	// 刷新暂时不加
	// public void refresh() {}

	public void setItem(List<QuestionInfo> list, boolean is_clear) {
		if (is_clear)
			items.clear();
		if (list != null)
			items.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public QuestionInfo getItem(int position) {
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
		if (items != null && items.size() > 0 && position < items.size()) {
			if (view == null) {
				holder = new ViewHolder();
				view = LayoutInflater.from(mContext).inflate(
						R.layout.item_question_new, null);
				// 用户提问控件
				holder.vip_flag = (ImageView) view.findViewById(R.id.vip_flag);
				holder.user_name = (TextView) view
						.findViewById(R.id.question_name);
				holder.question_date = (TextView) view
						.findViewById(R.id.question_date);
				holder.question_content = (TextView) view
						.findViewById(R.id.question_content);
				holder.questtion_voice = (ImageView) view
						.findViewById(R.id.btn_voice);
				// 自动回复控件
				holder.llAutoReply = (LinearLayout) view
						.findViewById(R.id.ll_auto_reply);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			QuestionInfo questionInfo = items.get(position);
			if (questionInfo != null) {
				String isVip = questionInfo.getIsVip();
				String voice_sign = null;
				if (questionInfo.getCommentType() != null) {
					voice_sign = questionInfo.getCommentType();// 提问的语音类型
					if (voice_sign.equals("3")) {
						final String voice_url = questionInfo
								.getQuestionContent();
						holder.question_content.setVisibility(View.GONE);
						holder.questtion_voice.setVisibility(View.VISIBLE);
						holder.questtion_voice
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										File voiceFile = FileOperator
												.getFileByUrl(voice_url,
														FileDownTask.VOICE_TYPE);
										if (voiceFile.exists()) {
											playRing(
													voiceFile.getAbsolutePath(),
													"");
										} else {
											new FileDownTask().downloadFile(
													FileDownTask.VOICE_TYPE,
													voice_url,
													new CallBackListener() {

														@Override
														public void postExecute() {
															// TODO
															// Auto-generated
															// method stub
															Toast.makeText(
																	mContext,
																	"播放失败",
																	1000)
																	.show();
														}

														@Override
														public void executeSucc() {
															// TODO
															// Auto-generated
															// method stub
															playRing(
																	FileOperator
																			.getFileByUrl(
																					voice_url,
																					FileDownTask.VOICE_TYPE)
																			.getAbsolutePath(),
																	"");
														}

														@Override
														public void executeFail() {
															// TODO
															// Auto-generated
															// method stub

														}
													});
										}
									}
								});
					} else {
						holder.question_content.setVisibility(View.VISIBLE);
						holder.questtion_voice.setVisibility(View.GONE);
						holder.question_content.setText(questionInfo
								.getQuestionContent());
					}

				} else {
					holder.question_content.setVisibility(View.VISIBLE);
					holder.questtion_voice.setVisibility(View.GONE);
					holder.question_content.setText(questionInfo
							.getQuestionContent());
				}

				if (isVip != null && isVip.equals("1")) {
					holder.vip_flag.setVisibility(View.VISIBLE);
				} else {
					holder.vip_flag.setVisibility(View.GONE);
				}

				String userInfo = questionInfo.getDisplayName2();
				holder.user_name.setText(userInfo);
				holder.question_date.setText(questionInfo.getDate());

				holder.llAutoReply.removeAllViews();
				if (questionInfo.getReplyList() != null
						&& questionInfo.getReplyList().size() > 0) {
					showAnswers(holder, questionInfo, isVip);
				} else {
					holder.llAutoReply.setVisibility(View.GONE);
				}
			}
		}
		return view;
	}

	// 回复判断
	private void showAnswers(ViewHolder holder, QuestionInfo questionInfo,
			String quesIsVip) {
		for (int i = 0; i < questionInfo.getReplyList().size(); i++) {
			AnswerInfo answerInfo = questionInfo.getReplyList().get(i);
			String authTag = answerInfo.getAuthTag();
			// authTag 0 所有用户看，1vip用户看
			if (authTag != null) {
				if (authTag.trim().equals("0")) {
					showAnswer(holder, answerInfo);
				} else if (authTag.trim().equals("1")) {
					showAnswer(holder, answerInfo);
				}
			}

		}
	}

	class ViewHolder {
		ImageView vip_flag;
		TextView user_name;
		TextView question_date;
		TextView question_content;
		ImageView questtion_voice;

		LinearLayout llAutoReply;
		TextView autoReplyName;
		TextView autoReplyContent;

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.arg1 == 1) {
				playRing("mnt/sdcard/dzlc/voice/myvoice.m4a", "正在播放");

			}
		}

	};

	// 点击播放录音
	public void playRing(String filePath, String msg) {

		if (null == mMediaPlayer) {
			mMediaPlayer = new MediaPlayer();
		}
		try {
			mMediaPlayer.reset();
			if (filePath == null)
				return;
			mMediaPlayer.setDataSource(filePath);
			/* 准备播放 */
			mMediaPlayer.prepare();
			/* 开始播放 */
			mMediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			pgPlayDialog.dismiss();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			pgPlayDialog.dismiss();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			pgPlayDialog.dismiss();
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

	// 管理员和自动回复的item houdepeng 测试
	private void hideAnswer(ViewHolder holder, QuestionInfo questionInfo,
			AnswerInfo answerInfo) {

		// holder.tipForNotVIP.setVisibility(View.GONE);
		holder.llAutoReply.setVisibility(View.VISIBLE);

		View anserBorderImpl = LayoutInflater.from(mContext).inflate(
				R.layout.item_listview_answerborder_openvip, null);

		TextView displayNameTxtView = (TextView) anserBorderImpl
				.findViewById(R.id.textViewName4Tip);
		String displayName = answerInfo.getReplyUserName();
		displayNameTxtView.setText(displayName);
		displayNameTxtView.setTextColor(Color.BLUE);

		TextView txtViewOpenVIP = (TextView) anserBorderImpl
				.findViewById(R.id.textViewOpenVIP);
		txtViewOpenVIP.setText(Html.fromHtml("<u>>>立即开通VIP</u>"));
		txtViewOpenVIP
				.setOnClickListener(new ClickListenr(holder, questionInfo));
		holder.llAutoReply.addView(anserBorderImpl);
	}

	private void showAnswer(ViewHolder holder, AnswerInfo answerInfo) {
		// holder.tipForNotVIP.setVisibility(View.GONE);
		holder.llAutoReply.setVisibility(View.VISIBLE);

		// TextView displayNameTxtView = (TextView) holder.tipForNotVIP
		// .findViewById(R.id.textViewName4Tip);
		String displayName = answerInfo.getReplyUserName();
		// displayNameTxtView.setText(displayName);
		View anserBorderImpl = LayoutInflater.from(mContext).inflate(
				R.layout.item_listview_answerborder, null);
		TextView answerUserInfoText = (TextView) anserBorderImpl
				.findViewById(R.id.textViewAnswerUserInfo4ItemListView);
		TextView answerDateText = (TextView) anserBorderImpl
				.findViewById(R.id.textViewAnswerDate4ItemListView);
		TextView answerText = (TextView) anserBorderImpl
				.findViewById(R.id.textViewAnswer4ItemListView);

		answerUserInfoText.setText(answerInfo.getReplyUserName());

		// SimpleDateFormat format1 = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date dateTime = new Date( Long.parseLong(answerInfo.getDate()));

		answerDateText.setText(answerInfo.getDate());
		// answerText.setText(answerInfo.getReplyContent());
		// setURLText里面放了自动回答的信息，地址，和表 houdepeng 测试
		setURLText(answerText, answerInfo.getReplyContent(),
				answerInfo.getReplaceList());
		holder.llAutoReply.addView(anserBorderImpl);
	}

	/****
	 * 检查关键字，增加点击效果
	 * 
	 * @param tv
	 * @param content
	 * @param replaceList
	 */
	private void setURLText(TextView tv, String content,
			List<ReplaceKeywordInfo> replaceList) {
		if (replaceList != null && replaceList.size() > 0) {
			String s = content;
			List<String> keys = new ArrayList<String>();
			for (ReplaceKeywordInfo keyword : replaceList) {
				// 判断是否有重复关键字.
				if (keys.contains(keyword.getKeyWord()))
					continue;
				keys.add(keyword.getKeyWord());
				s = s.replaceAll(
						keyword.getKeyWord(),
						"<a  href=" + keyword.getKeyWord() + ">"
								+ keyword.getReplaceWord() + "</a>");
			}
			Spannable sp1 = (Spannable) Html.fromHtml(s);
			tv.setText(sp1);
			tv.setMovementMethod(LinkMovementMethod.getInstance());
			CharSequence text = tv.getText();
			if (text instanceof Spannable) {
				int end = text.length();
				Spannable sp = (Spannable) tv.getText();
				// 得到其中所有的关键字的数组
				URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
				// 其中关键字的样式
				SpannableStringBuilder style = new SpannableStringBuilder(text);
				// 清除掉所有的关键字标志
				style.clearSpans();
				for (URLSpan url : urls) {
					// 将关键字数组中的文字添加到新生成的style中去
					MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
					style.setSpan(myURLSpan, sp.getSpanStart(url),
							sp.getSpanEnd(url),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				// 将新生成的放置到TextView上
				tv.setText(style);
			}

		} else {
			tv.setText(content);
		}
	}

	/**
	 * 继承于可点击的标签
	 * 
	 */
	private class MyURLSpan extends ClickableSpan {
		private String mUrl;

		MyURLSpan(String url) {
			mUrl = url;
		}

		@Override
		public void onClick(View widget) {
			new searchStockTask().execute(mUrl);
		}
	}

	/**
	 * 加载web
	 * 
	 * @author EsaFans
	 * 
	 */
	public class searchStockTask extends AsyncTask<String, Void, String> {

		String SUCCESS = "successpre";
		String ERROR = "errorpre";
		ProgressDialog progressDialog;
		StockInfo searchStockDetail;
		String keyword;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setMessage("正在获取股票信息");
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			IUserAccountService accountService = new UserAccountServiceImpl(
					mContext);

			try {
				if (true) {
					keyword = params[0];
					StockServiceImpl requestService = new StockServiceImpl(
							mContext);
					searchStockDetail = requestService.searchStock(keyword);
					if (searchStockDetail != null) {
						return SUCCESS;
					} else {
						return ERROR;
					}
				} else {
					return MainConst.NOT_VIP;
				}
			} catch (ServiceException e) {
				ExcepUtils
						.showImpressiveException((Activity) mContext, null, e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				progressDialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (SUCCESS.equalsIgnoreCase(result)) {
				Intent intent = new Intent(mContext, DataActivity.class);
				intent.putExtra("url", searchStockDetail.getUrl());
				intent.putExtra("code", searchStockDetail.getKeyword());
				intent.putExtra("name", searchStockDetail.getStockName());
				mContext.startActivity(intent);
				ReportLogService.getInstance().addLog(mContext,
						TyLogInfo.ACCESSTYPE_CLIENT,
						TyLogInfo.ACTION_OPEN_STOCK, null, null, result,
						"success", keyword);
			} else if (MainConst.NOT_VIP.equalsIgnoreCase(result)) {
				Toast.makeText(mContext, "你不是Vip", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(mContext, "查看失败", Toast.LENGTH_SHORT).show();
			}
		}

	}

	class ClickListenr implements OnClickListener {

		private ViewHolder mHolder;
		private QuestionInfo mQuestionInfo;

		public ClickListenr(ViewHolder holder, QuestionInfo questionInfo) {
			this.mHolder = holder;
			this.mQuestionInfo = questionInfo;
		}

		@Override
		public void onClick(View v) {
			// CallBackListener callback = new CallBackListener() {
			// @Override
			// public void execute() {
			// // showAnswers(mHolder, mQuestionInfo);
			// }
			// };
			// UserPayFlow.startAuth(mContext, callback);
		}

	}
}
