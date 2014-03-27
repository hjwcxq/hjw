package com.tv189.dzlc.adapter.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tv189.dzlc.adapter.po.sqlpo.ItemNodeInfo;
import com.tv189.dzlc.adapter.po.sqlpo.ModuleInfo;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;
import com.tv189.dzlc.adapter.po.sqlpo.StockVideo;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


	private static final String DATABASE_NAME = "dzcj1.01.db";
	
	private static final int DATABASE_VERSION = 1;


	// the DAO object we use to access the SimpleData table
	private Dao<ModuleInfo, Integer> moduleDao = null;
	private Dao<ItemNodeInfo, Integer> itemDao = null;
	private Dao<StockInfo, String> stockInfoDao = null;
	private Dao<StockVideo, String> stockVideoDao = null;
	private Dao<VideoDetails, String> videoDetailsDao = null;
	
	

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		Log.i(DatabaseHelper.class.getName(), "onCreate");
		createTable();

	}

	private void createTable() {
		try {

			TableUtils.createTableIfNotExists(connectionSource,
					ModuleInfo.class);
			TableUtils.createTableIfNotExists(connectionSource,
					ItemNodeInfo.class);
			TableUtils.createTableIfNotExists(connectionSource,
					StockInfo.class);
			TableUtils.createTableIfNotExists(connectionSource,
					StockVideo.class);
			TableUtils.createTableIfNotExists(connectionSource,
					VideoDetails.class);
			
			
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

	}

	public void delTables() {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.clearTable(connectionSource, ModuleInfo.class);
			TableUtils.clearTable(connectionSource, ItemNodeInfo.class);
			TableUtils.clearTable(connectionSource, StockInfo.class);
			TableUtils.clearTable(connectionSource, StockVideo.class);
			TableUtils.clearTable(connectionSource, VideoDetails.class);

			// after we drop the old databases, we create the new ones

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, ModuleInfo.class, true);
			TableUtils.dropTable(connectionSource, ItemNodeInfo.class, true);
			TableUtils.dropTable(connectionSource, StockInfo.class, true);
			TableUtils.dropTable(connectionSource, StockVideo.class, true);
			TableUtils.dropTable(connectionSource, VideoDetails.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		moduleDao = null;
		itemDao = null;
		stockInfoDao = null ;
		stockVideoDao = null ;
		videoDetailsDao = null ;
	}

	/**
	 * Returns the Database Access Object (DAO)
	 */
	public Dao<ModuleInfo, Integer> getModuleDao() throws SQLException {
		if (moduleDao == null) {
			moduleDao = getDao(ModuleInfo.class);
		}
		return moduleDao;
	}
	
	public Dao<ItemNodeInfo, Integer> getItemDao() throws SQLException {
		if (itemDao == null) {
			itemDao = getDao(ItemNodeInfo.class);
		}
		return itemDao;
	}
	
	public Dao<StockInfo, String> getStockInfoDao() throws SQLException {
		if (stockInfoDao == null) {
			stockInfoDao = getDao(StockInfo.class);
		}
		return stockInfoDao;
	}
	
	public Dao<StockVideo, String> getStockVideoDao() throws SQLException {
		if (stockVideoDao == null) {
			stockVideoDao = getDao(StockVideo.class);
		}
		return stockVideoDao;
	}
	
	public Dao<VideoDetails, String> getVideoDetailsDao() throws SQLException {
		if (videoDetailsDao == null) {
			videoDetailsDao = getDao(VideoDetails.class);
		}
		return videoDetailsDao;
	}
	
	public void clearModuleInfo() {
		try {
			TableUtils.clearTable(connectionSource, ModuleInfo.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void clearItemNodeInfo() {
		try {
			TableUtils.clearTable(connectionSource, ItemNodeInfo.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
