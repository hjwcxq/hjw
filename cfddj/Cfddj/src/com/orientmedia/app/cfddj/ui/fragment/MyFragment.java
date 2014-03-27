package com.orientmedia.app.cfddj.ui.fragment;

import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.Listener.ShowLoadingTipListener;
import com.orientmedia.app.cfddj.service.UpdateService;
import com.orientmedia.app.cfddj.tool.BaiduStatistics;
import com.orientmedia.app.cfddj.tool.ExcepUtils;
import com.orientmedia.app.cfddj.ui.AboutActivity;
import com.orientmedia.app.cfddj.ui.LoginActivity;
import com.orientmedia.app.cfddj.ui.MainActivity;
import com.orientmedia.app.cfddj.ui.MyStockActivity;
import com.orientmedia.app.cfddj.ui.MyVideoActivity;
import com.orientmedia.app.cfddj.ui.my.BindingAccountActivity;
import com.orientmedia.app.cfddj.ui.my.MyQuestionActivity;
import com.orientmedia.app.cfddj.ui.my.MyTopicActivity;
import com.orientmedia.app.cfddj.ui.my.MyUserInfo;
import com.orientmedia.base.BaseFragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;
import com.tv189.dzlc.adapter.po.version.VersionInfo;
import com.tv189.dzlc.adapter.service.impl.StockServiceImpl;
import com.tv189.dzlc.adapter.service.impl.VersionServiceImpl;
import com.tv189.dzlc.adapter.service.impl.VideoServiceImpl;
import com.tv189.dzlc.adapter.service.inerface.VersionService;
import com.tv189.dzlc.adapter.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.controller.UMWXHandler;
import com.umeng.socialize.media.CircleShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.WeiXinShareContent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateStatus;
import com.umeng.update.UpdateResponse;

public class MyFragment extends BaseFragment implements OnClickListener {

	private ImageView userImage;
	private LinearLayout llUserDesc;
	private TextView loginStatu, userName, userLevel, tv_verision;
	private ImageView vipSign;
	private RelativeLayout userInto;

	private LinearLayout llBindingAccount;
	private TextView bindingAccountNum;

	private LinearLayout  llMyQuestion, llMyVideo, llMyTopic,
			llMyStock;
	private TextView myOrderNum, myVideoNum, myTopicTip, myStockTip;

	private LinearLayout llInviteFriend;
	private TextView inviteFriendNum;

	private LinearLayout llSetting;
	private LinearLayout llFb,llAbout;  //hjw add
	private boolean isVip = true;
	private boolean notVip = false;

	private ShowLoadingTipListener showListener;
	// 首先在您的Activity中添加如下成员变量
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",RequestType.SOCIAL);
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		showListener = (MainActivity) activity;

		BaiduStatistics.onMyEvent(this.getActivity(), "4000", this.getClass()
				.getName() + "进入发现页面");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = null;
		if (container == null)
			return null;
		else {
			view = inflater.inflate(R.layout.fragment_my, container, false);
			initView(view);
			initListener();
		}
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("myScreen"); //hjw add for umeng
	    MobclickAgent.onResume(getActivity());
		initUserInfo();
		new LoadItemTipsTask().execute("");

	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageEnd("myScreen"); //hjw add for umeng
	    MobclickAgent.onPause(getActivity());
		initUserInfo();
		new LoadItemTipsTask().execute("");

	}

	private AppSetting setting = null;

	public void initUserInfo() {
		if (setting == null)
			setting = AppSetting.getInstance(getActivity());

		if (setting.isLoginOn()) {
			loginStatu.setVisibility(View.GONE);
			llUserDesc.setVisibility(View.VISIBLE);
			String useName = setting.getNickName();
			if (useName == null || "".equals(useName)) {
				userName.setText("未命名");
			} else {
				userName.setText(setting.getNickName());
			}
			llMyStock.setVisibility(View.GONE);  //hjw modify VISIBLE to GONE
			llMyVideo.setVisibility(View.GONE);  //hjw modify VISIBLE to GONE

			userLevel.setText("普通用户");
			vipSign.setVisibility(View.GONE);
		} else {
			llUserDesc.setVisibility(View.GONE);
			loginStatu.setVisibility(View.VISIBLE);
			llMyStock.setVisibility(View.GONE);
			llMyVideo.setVisibility(View.GONE);
		}
	}

	private List<VideoDetails> videoDetails = null;
	private List<StockInfo> stockInfos = null;

	class LoadItemTipsTask extends AsyncTask<String, Void, String> {

		private int myStockCount = 0;
		private int videoCount = 0;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showListener.onShowLoadingTip("");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				stockInfos = new StockServiceImpl(getActivity())
						.queryStockInfoFromDb(AppSetting.getInstance(
								getActivity()).getUserId());
				if (stockInfos != null && !stockInfos.isEmpty()) {
					myStockCount = stockInfos.size();
				}
			} catch (OrmSqliteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				videoDetails = new VideoServiceImpl(getActivity())
						.getVideosFromDb(AppSetting.getInstance(getActivity())
								.getUserId());
				if (videoDetails != null && !videoDetails.isEmpty()) {
					videoCount = videoDetails.size();
				}
			} catch (OrmSqliteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			showListener.onHideLoadingTip();

			if (myStockCount > 0) {
				myStockTip.setVisibility(View.VISIBLE);
				myStockTip.setText("" + myStockCount);
			} else {
				myStockTip.setVisibility(View.INVISIBLE);
			}

			if (videoCount > 0) {
				myVideoNum.setVisibility(View.VISIBLE);
				myVideoNum.setText("" + videoCount);
			} else {
				myVideoNum.setVisibility(View.INVISIBLE);
			}

		}

	}

	public void initView(View view) {
		userImage = (ImageView) view.findViewById(R.id.user_image);
		llUserDesc = (LinearLayout) view.findViewById(R.id.ll_user_desc);
		loginStatu = (TextView) view.findViewById(R.id.not_login);
		userName = (TextView) view.findViewById(R.id.user_name);
		userLevel = (TextView) view.findViewById(R.id.user_level);
		vipSign = (ImageView) view.findViewById(R.id.vip_sign);
		userInto = (RelativeLayout) view.findViewById(R.id.rl_user);

		llBindingAccount = (LinearLayout) view
				.findViewById(R.id.ll_binding_account);
		bindingAccountNum = (TextView) view
				.findViewById(R.id.binding_account_num);

		llMyQuestion = (LinearLayout) view.findViewById(R.id.ll_my_question);
		llMyStock = (LinearLayout) view.findViewById(R.id.ll_my_stock);
		llMyVideo = (LinearLayout) view.findViewById(R.id.ll_my_video);
		llMyTopic = (LinearLayout) view.findViewById(R.id.ll_my_topic);
		// myQuestionNum = (TextView) view.findViewById(R.id.my_question_tip);
		myStockTip = (TextView) view.findViewById(R.id.my_stock_tip);
		tv_verision = (TextView) view.findViewById(R.id.tv_verision);
		myTopicTip = (TextView) view.findViewById(R.id.my_topic_tip);
		myVideoNum = (TextView) view.findViewById(R.id.my_video_tip);

		llInviteFriend = (LinearLayout) view
				.findViewById(R.id.ll_invite_friend);
		inviteFriendNum = (TextView) view.findViewById(R.id.invite_friend_num);

		llSetting = (LinearLayout) view.findViewById(R.id.ll_setting);
		//hjw add
		llFb = (LinearLayout) view.findViewById(R.id.ll_fb); 
		llAbout = (LinearLayout) view.findViewById(R.id.ll_about);
	}

	public void initListener() {
		userInto.setOnClickListener(this);
		llBindingAccount.setOnClickListener(this);

		llMyQuestion.setOnClickListener(this);
		llMyStock.setOnClickListener(this);
		llMyTopic.setOnClickListener(this);
		llMyVideo.setOnClickListener(this);

		llInviteFriend.setOnClickListener(this);
		llSetting.setOnClickListener(this);
		
		//hjw add
		llFb.setOnClickListener(this);
		llAbout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_user:
			if (AppSetting.getInstance(getActivity()).getUserName() != null
					& AppSetting.getInstance(getActivity()).getUserName() != "") {
				intentTo(getActivity(), MyUserInfo.class);
			} else {
				intentTo(getActivity(), LoginActivity.class);
			}
			break;
		case R.id.ll_binding_account:
			Intent itBind = new Intent(getActivity(),
					BindingAccountActivity.class);
			startActivity(itBind);
			break;
		case R.id.ll_my_question:
			Intent itQuestion = new Intent(getActivity(),
					MyQuestionActivity.class);
			startActivity(itQuestion);
			break;
		case R.id.ll_my_stock:
			Intent itStock = new Intent(getActivity(), MyStockActivity.class);
			startActivity(itStock);
			break;
		case R.id.ll_my_video:
			Intent itVidoe = new Intent(getActivity(), MyVideoActivity.class);
			startActivity(itVidoe);
			break;
		case R.id.ll_my_topic:
			Intent itTopic = new Intent(getActivity(), MyTopicActivity.class);
			startActivity(itTopic);
			break;
		case R.id.ll_invite_friend:
			/*Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, "分享给好友");
			// intent.putExtra(Intent.EXTRA_TEXT,Html.fromHtml(getResources().getString(R.string.demoStr)));
			intent.putExtra(Intent.EXTRA_TEXT,
					getResources().getText(R.string.invite_intro));
			startActivity(Intent.createChooser(intent, "推荐给朋友"));*/
			umengShare();
			break;
		case R.id.ll_setting:
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			    @Override
			    public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
			        switch (updateStatus) {
			        case UpdateStatus.Yes: // has update
			            UmengUpdateAgent.showUpdateDialog(getActivity(), updateInfo);
			            break;
			        case UpdateStatus.No: // has no update
			        	new AlertDialog.Builder(getActivity()).setTitle("提示信息").setMessage(getString(R.string.string_no_update))
				  	      .setNegativeButton("确定", new DialogInterface.OnClickListener()
				  	      {
				  	        @Override
				  			public void onClick(DialogInterface paramDialogInterface, int paramInt)
				  	        {

				  	        }
				  	      }).create().show();
			            break;
			        case UpdateStatus.Timeout: // time out
			        	new AlertDialog.Builder(getActivity()).setTitle("提示信息").setMessage(getString(R.string.string_timeout))
				  	      .setNegativeButton("确定", new DialogInterface.OnClickListener()
				  	      {
				  	        @Override
				  			public void onClick(DialogInterface paramDialogInterface, int paramInt)
				  	        {

				  	        }
				  	      }).create().show();
			            break;
			        }
			    }
			});
			UmengUpdateAgent.forceUpdate(getActivity());
			break;
		case R.id.ll_fb:
			FeedbackAgent agent = new FeedbackAgent(getActivity());
		    agent.startFeedbackActivity();		
			break;
		case R.id.ll_about:
			Intent about = new Intent(getActivity(), AboutActivity.class);
			startActivity(about);
			break;
		default:
			break;
		}
	}

	public void umengShare(){
	
		// appID是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appID = "wx967daebe835fbeac";
		// 微信图文分享必须设置一个url 
		String contentUrl = "http://www.umeng.com/social";
		// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
		UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(getActivity(),appID, contentUrl);
		wxHandler.setWXTitle("财富大当家上线啦...");
		// 支持微信朋友圈
		UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(getActivity(),appID, contentUrl) ;
		circleHandler.setCircleTitle("财富大当家上线啦...");

		// 支持QQ好友
		mController.getConfig().supportQQPlatform(getActivity(), "http://www.umeng.com/social"); 
		// 支持免登陆分享到QQ空间
		mController.getConfig().setSsoHandler( new QZoneSsoHandler(getActivity()) );
		
		// 支持新浪微博 SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		
		// 视频分享，并且设置了腾讯微博平台的文字内容
		UMVideo umVedio = new UMVideo(
		                "http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
		umVedio.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
		umVedio.setTitle("友盟社会化组件视频");	
		TencentWbShareContent tencentContent = new TencentWbShareContent(umVedio);
		// 设置分享到腾讯微博的文字内容
		tencentContent.setShareContent("财富大当家，直播有关房地产与理财的节目，用户可以在线互动，可以向嘉宾咨询相关房产以及理财产品信息，同时开放用户之间的讨论。");
		// 设置分享到腾讯微博的多媒体内容
		mController.setShareMedia(tencentContent);
		//设置腾讯微博SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

		// ** 其他平台的分享内容.除了上文中已单独设置了分享内容的微信、朋友圈、腾讯微博平台，
		// 剩下的其他平台的分享内容都为如下文字和UMImage  **
		mController.setShareContent("财富大当家，直播有关房地产与理财的节目，用户可以在线互动，可以向嘉宾咨询相关房产以及理财产品信息，同时开放用户之间的讨论。");
		// 设置分享图片，参数2为图片的url.  	
		mController.setShareMedia(new UMImage(getActivity(), 
		                                "http://www.umeng.com/images/pic/banner_module_social.png"));
		
		
		mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN,SHARE_MEDIA.EMAIL,SHARE_MEDIA.SMS);
		mController.openShare(getActivity(), false);
	}
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode,resultCode,data);
	    /**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
}
