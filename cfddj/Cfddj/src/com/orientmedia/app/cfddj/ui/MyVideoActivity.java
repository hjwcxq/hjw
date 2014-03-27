package com.orientmedia.app.cfddj.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.adapter.VideoDetailAdapter;
import com.orientmedia.app.cfddj.tool.FileOperator;
import com.orientmedia.base.BaseActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;
import com.tv189.dzlc.adapter.service.impl.VideoServiceImpl;
/**
 * 我的本地视频
 * @author mac
 */

public class MyVideoActivity extends BaseActivity implements OnClickListener{
	
	private PullToRefreshListView mPullRefreshListView;
	
	private List<VideoDetails> videos = new ArrayList<VideoDetails>();
	
	private VideoDetailAdapter videoAdapter = null ;
	
	private ListView listView;
	
	private Button addVideo ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_video);
		initView();
		loadData();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_video:
			Intent it = new Intent(this,BaodianActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}
	}

	
	public void initView(){
		addVideo = (Button) findViewById(R.id.add_video);
		addVideo.setOnClickListener(this);
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setEmptyView(findViewById(R.id.ll_emptyView));
		listView = mPullRefreshListView.getRefreshableView();
		videoAdapter = new VideoDetailAdapter(this, videos);
		listView.setAdapter(videoAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				VideoDetails video = videoAdapter.getItem(arg2-1);
				if(video != null){
					String  path  = FileOperator.geCacheVideoFile() + File.separator + video.getTitle() + ".3gp";
					playVideo(path);
					
				}
			}
		});
		
	}
	
	private void playVideo(String videoPath){
		   Intent intent = new Intent(Intent.ACTION_VIEW);
		   intent.setDataAndType(Uri.parse(videoPath), "video/3gp");
		   startActivity(intent);
	   }
	
	public void loadData(){
		VideoServiceImpl videoServie = new VideoServiceImpl(this);
		try {
			videos = videoServie.getVideosFromDb(AppSetting.getInstance(this).getUserId()) ;
			videoAdapter.setItems(videos);
		} catch (OrmSqliteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
