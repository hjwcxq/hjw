package com.tv189.dzlc.adapter.dao.impl;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.Where;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.dao.interf.StockInfoDao;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;

public class StockInfoDaoImpl implements StockInfoDao{
	
	private Context context ;
	
	public StockInfoDaoImpl(Context cont){
		this.context = cont ;
	}
	
	/**
	 * 添加自选股
	 */
	public boolean addStock(StockInfo stock) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<StockInfo, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getStockInfoDao();
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
	 * 删除自选股
	 */
	public boolean deleteStock(StockInfo stock) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<StockInfo, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getStockInfoDao();
			
			List<StockInfo> list = dao.queryBuilder().where().eq("uid", stock.uid).and().eq("displayCode", stock.getDisplayCode()).query() ;
			if(list != null && !list.isEmpty())
				count = dao.delete(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_DELETE_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0 ;
	}
	
	/**
	 * 删除本地该人所有的自选股列表
	 * @param uid
	 * @return
	 * @throws OrmSqliteException
	 */
	public boolean deleteAllStock(String uid) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		List<StockInfo> list = null ;
		try {
			Dao<StockInfo, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getStockInfoDao();
			list = dao.queryBuilder().where().eq("uid", uid).query() ;
			count = dao.delete(list) ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_DELETE_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return count == (list != null ? list.size() : 0);
	}
	
	/**
	 * 查询我的自选股 通过uid
	 */
	public List<StockInfo> queryStocks(String uid) throws OrmSqliteException{
		if(uid == null || "".equals(uid))
			return null ;
		SystemContext.getInstance(context).openDB();
		List<StockInfo> list = null;
		try {
			Dao<StockInfo, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getStockInfoDao();
			list = dao.queryBuilder().where().eq("uid", uid).query() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_QUERY_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return list ;
	}
	
	
	public boolean isExits(StockInfo stock,String uid) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		List<StockInfo> list = null;
		try {
			Dao<StockInfo, String> dao = SystemContext.getInstance(context).getDatabaseHelper().getStockInfoDao();
			list = dao.queryBuilder().where().eq("displayCode", stock.getDisplayCode()).and().eq("uid", uid).query() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_QUERY_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return list != null? list.size() >0 : false  ;
	}
	

}
