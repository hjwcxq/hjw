package com.tv189.dzlc.adapter.dao.impl;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.sqlpo.StockVideo;

public class StockVideoDaoImpl{
	
	private Context context ;
	
	public StockVideoDaoImpl(Context cont){
		this.context = cont ;
	}
	
	/**
	 * 添加我的视频收藏
	 */
	public boolean addStockVideo(StockVideo stock) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<StockVideo, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getStockVideoDao();
			count = dao.create(stock);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_ADD_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0 ;
	}
	
	/**
	 * 删除我的视频收藏
	 */
	public boolean deleteStockVideo(StockVideo stock) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<StockVideo, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getStockVideoDao();
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
	public List<StockVideo> queryStockVideos(String uid) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		List<StockVideo> list = null;
		try {
			Dao<StockVideo, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getStockVideoDao();
			list = dao.queryBuilder().where().eq("uid", uid).query() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_QUERY_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return list ;
	}
	
	public boolean isExits(StockVideo video,String uid) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		List<StockVideo> list = null;
		try {
			Dao<StockVideo, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getStockVideoDao();
			list = dao.queryBuilder().where().eq("uid", uid).and().eq("id", video.getId()).query() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_QUERY_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return list != null ? list.size() > 0 :false ;
	}
}
