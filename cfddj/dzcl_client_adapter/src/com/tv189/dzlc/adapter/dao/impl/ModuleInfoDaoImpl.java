package com.tv189.dzlc.adapter.dao.impl;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.sqlpo.ModuleInfo;

public class ModuleInfoDaoImpl {
	
	private Context context ;
	
	public ModuleInfoDaoImpl(Context cont){
		this.context = cont ;
	}
	
	/**
	 * 添加自选股
	 */
	public boolean addModule(ModuleInfo module) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<ModuleInfo, Integer> dao = SystemContext.getInstance(context).getDatabaseHelper().getModuleDao();
			count = dao.create(module);
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
	public boolean deleteModule(ModuleInfo module) throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<ModuleInfo, Integer> dao = SystemContext.getInstance(context).getDatabaseHelper().getModuleDao();
			count = dao.delete(module);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_DELETE_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0 ;
	}
	
	/**
	 * 清空模块的所有数据
	 */
	public void clearAll() throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		try {
			Dao<ModuleInfo, Integer> dao = SystemContext.getInstance(context).getDatabaseHelper().getModuleDao();
			dao.deleteBuilder().clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_DELETE_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
	}
	
	
	/**
	 * 查询我的模块 
	 */
	public List<ModuleInfo> queryModules() throws OrmSqliteException{
		SystemContext.getInstance(context).openDB();
		List<ModuleInfo> list = null;
		try {
			Dao<ModuleInfo, Integer> dao = SystemContext.getInstance(context).getDatabaseHelper().getModuleDao();
			list = dao.queryBuilder().query() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new OrmSqliteException(OrmSqliteException.ERROR_QUERY_CODE_EXCEPTION, e.getMessage());
		}finally{
			SystemContext.getInstance(context).closeDB();
		}
		return list ;
	}
}
