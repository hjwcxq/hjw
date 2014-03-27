package com.tv189.dzlc.adapter.dao.impl;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.sqlpo.ItemNodeInfo;

public class ItemNodeInfoDaoImpl {
	
	private Context context ;
	
	public ItemNodeInfoDaoImpl(Context cont){
		this.context = cont ;
	}
	
	/**
	 * 添加自选股
	 */
	public boolean addItemNode(ItemNodeInfo item) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<ItemNodeInfo, Integer> dao = SystemContext.getInstance(context).getDatabaseHelper().getItemDao();
			count = dao.create(item);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_ADD_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0 ;
	}
	
	/**
	 * 删除模块
	 */
	public boolean deleteItemNode(ItemNodeInfo item) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<ItemNodeInfo, Integer> dao = SystemContext.getInstance(context).getDatabaseHelper().getItemDao();
			count = dao.delete(item);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_DELETE_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0 ;
	}

	
	/**
	 * 查询我的node
	 */
	public List<ItemNodeInfo> queryItemNode() throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		List<ItemNodeInfo> list = null;
		try {
			Dao<ItemNodeInfo, Integer> dao = SystemContext.getInstance(context).getDatabaseHelper().getItemDao();
			list = dao.queryBuilder().query() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_QUERY_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return list ;
	}
	
	/**
	 * 更新items
	 */
	public boolean updateItemNodeInfos(List<ItemNodeInfo> items) throws OrmSqliteException{
		if(items == null || items.isEmpty())
			return false ;
		SystemContext.getInstance(context).openDB();
		int count = 0 ;
		try {
			Dao<ItemNodeInfo, Integer> dao = SystemContext.getInstance(context).getDatabaseHelper().getItemDao();
			for (int i = 0; i < items.size(); i++) {
				int index = dao.update(items.get(i));
				if(index >0)
					count ++ ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_QUERY_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return count == items.size();
	}
	
}
