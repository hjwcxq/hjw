package com.tv189.dzlc.adapter.dao.impl;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;

public class VideoDetailsDaoImpl {
	
	private Context context ;
	
	public VideoDetailsDaoImpl(Context cont){
		this.context = cont ;
	}
	
	/**
	 * 添加我的视频下载
	 */
	public boolean addVideoDetails(VideoDetails video) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<VideoDetails, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getVideoDetailsDao();
			count = dao.create(video);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_ADD_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0 ;
	}
	
	/**
	 * 删除我的视频下载
	 */
	public boolean deleteVideoDetails(VideoDetails stock) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<VideoDetails, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getVideoDetailsDao();
			count = dao.delete(stock);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_DELETE_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0 ;
	}
	/**
	 * 查询我的视频收藏 通过uid
	 */
	public List<VideoDetails> queryVideoDetails(String uid) throws OrmSqliteException{
		if(uid == null || "".equals(uid))
			return  null ;
		SystemContext.getInstance(context).openDB();
		List<VideoDetails> list = null;
		try {
			Dao<VideoDetails, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getVideoDetailsDao();
			list = dao.queryBuilder().where().eq("uid", uid).query() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_QUERY_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return list ;
	}
	/**
	 * 查询我的视频收藏 通过uid
	 */
	public boolean isExits(VideoDetails video,String uid) throws OrmSqliteException{
		if(uid == null || "".equals(uid))
			return  false ;
		SystemContext.getInstance(context).openDB();
		List<VideoDetails> list = null;
		try {
			Dao<VideoDetails, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getVideoDetailsDao();
			list = dao.queryBuilder().where().eq("uid", uid).and().eq("vid", video.getVid()).query() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_QUERY_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return list !=null ?list.size() >0 :false;
	}
}
