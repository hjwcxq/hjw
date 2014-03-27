package com.tv189.dzlc.adapter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.dao.impl.StockInfoDaoImpl;
import com.tv189.dzlc.adapter.factory.ClientLogFactory;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.log.OpenPartActionLog;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;
import com.tv189.dzlc.adapter.po.stock.NewStockInfoContent;
import com.tv189.dzlc.adapter.po.stock.StockMysearchPageContent;
import com.tv189.dzlc.adapter.po.stock.StockVideoPageContent;
import com.tv189.dzlc.adapter.service.abstractclass.AbstractDzlcService;
import com.tv189.dzlc.adapter.service.inerface.StockService;

public class StockServiceImpl extends AbstractDzlcService implements
		StockService {

	private static final String ACTION_OPEN_CONTENT = null;

	public StockServiceImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * 搜索股票
	 */
	@Override
	public ApiResponse sousStock(String keyword, String id)
			throws ServiceException {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("keyword", keyword);
		headerMap.put("uid", id);
		ApiResponse gplzStock = null;
		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_GSBD);
		log.setActionDesc("搜索股票");
		Object respAccountObj = null;
		try {
			respAccountObj = callGetApi(DzlcAndroidConfig.SEARCH_STOCK,
					headerMap, ApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
		}
		if (respAccountObj != null)
			log.setActionResult("0");
		ClientLogFactory.getInstance().addLog(log);
		gplzStock = (ApiResponse) respAccountObj;
		return gplzStock;
	}

	/**
	 * 热门股票
	 */
	@Override
	public List<StockInfo> stockListHot() throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_GSBD);
		log.setActionDesc("热门股票");
		StockInfoListApiResponse apiResp = null;
		try {
			apiResp = (StockInfoListApiResponse) callGetApi(
					DzlcAndroidConfig.HOT_STOCK, headerMap,
					StockInfoListApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
		}
		if (apiResp != null && apiResp.isSuccess()) {
			log.setActionResult("0");
			return apiResp.getData() == null ? null : apiResp.getData();
		} else {
			return null;
		}
	}

	/**
	 * 查看自选股
	 */
	@Override
	public List<StockInfo> favoriteList(String sign, String uid)
			throws ServiceException, OrmSqliteException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		headerMap.put("sign", sign); // 签名
		headerMap.put("uid", uid);
		StockInfoListApiResponse apiResp = (StockInfoListApiResponse) callGetApi(
				DzlcAndroidConfig.ZIXUAN_STOCK, headerMap,
				StockInfoListApiResponse.class);
		if (apiResp != null && apiResp.isSuccess()) {
			// 把列表保存到本地 中去
			List<StockInfo> list = apiResp.getData();
			StockInfoDaoImpl dao = new StockInfoDaoImpl(context);
			if (list != null && !list.isEmpty()) {
				for (StockInfo stockInfo : list) {
					stockInfo.uid = uid;
					if (!dao.isExits(stockInfo, uid)) {
						dao.addStock(stockInfo);
					}
				}
			}
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 股票频道页面
	 */
	@Override
	public StockVideoPageContent blocklist(String id, String pagesize,
			String pagenum) throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		headerMap.put("id", id);
		headerMap.put("pagesize", pagesize);
		headerMap.put("pagenum", pagenum);
		StockVideoPageContentApiResponse apiResp = (StockVideoPageContentApiResponse) callPostApi(
				DzlcAndroidConfig.ID_STOCK_VIDEO, headerMap,
				StockVideoPageContentApiResponse.class);
		if (apiResp != null && apiResp.isSuccess()) {
			return apiResp.getData() == null ? null : apiResp.getData();
		} else {
			return null;
		}
	}

	/**
	 * 个性股票查询
	 */
	@Override
	public List<StockInfo> dispositionStock(String stockCode)
			throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		headerMap.put("stockCode", stockCode);
		StockInfoListApiResponse apiResp = (StockInfoListApiResponse) callGetApi(
				DzlcAndroidConfig.GEXING_STOCK, headerMap,
				StockInfoListApiResponse.class);
		if (apiResp != null && apiResp.isSuccess()) {
			return apiResp.getData() == null ? null : apiResp.getData();
		} else {
			return null;
		}
	}

	/**
	 * 添加自选股
	 * 
	 * @throws OrmSqliteException
	 */
	@Override
	public ApiResponse addDispositionStock(String uid, StockInfo stock,
			String sign) throws ServiceException, OrmSqliteException {
		if (stock == null || stock.getStockCode() == null)
			return null;

		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		ApiResponse stockInfo = null;
		headerMap.put("uid", uid);
		headerMap.put("stockcode", stock.getStockCode());
		headerMap.put("sign", sign);
		Object respAccountObj = callGetApi(DzlcAndroidConfig.ADD_STOCK,
				headerMap, ApiResponse.class);
		if (respAccountObj != null) {
			stockInfo = (ApiResponse) respAccountObj;
			if (stockInfo.isSuccess()) {
				// 把自选股保存到本地中去
				boolean flag = false ;
				StockInfoDaoImpl dao = new StockInfoDaoImpl(context);
				stock.uid = uid;
				if (!dao.isExits(stock, uid)){
					flag = dao.addStock(stock);
					
				}
				if(flag)
					return stockInfo ;
				else 
					return null;

			}
		}
		return stockInfo;
	}

	/**
	 * 股票浏览记录
	 */
	@Override
	public StockMysearchPageContent mysearchlist(String pagenum, String pagesize)
			throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		headerMap.put("pagesize", pagesize);
		headerMap.put("pagenum", pagenum);
		StockMysearchPageContentApiResponse apiResp = null;
		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_STOCK);
		log.setActionDesc("股票浏览记录");
		try {
			apiResp = (StockMysearchPageContentApiResponse) callPostApi(
					DzlcAndroidConfig.MYSEACHLIST, headerMap,
					StockMysearchPageContentApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
		}
		if (apiResp != null && apiResp.isSuccess()) {
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			return apiResp.getData() == null ? null : apiResp.getData();
		} else {
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return null;
		}
	}

	/**
	 * 股票详细信息
	 */
	@Override
	public StockInfo searchStock(String keyword) throws ServiceException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		headerMap.put("keyword", keyword);
		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_STOCK);
		log.setActionDesc("股票详细信息");
		searchStockApiResponse respAccountObj = null;
		try {
			respAccountObj = (searchStockApiResponse) callPostApi(
					DzlcAndroidConfig.SEACHSTOCK, headerMap,
					searchStockApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrCode());
			// TODO: handle exception
		}
		if (respAccountObj != null && respAccountObj.isSuccess()) {
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			return respAccountObj.getData() == null ? null : respAccountObj
					.getData();
		} else {
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return null;
		}
	}

	/**
	 * 删除自选股
	 */
	@Override
	public Boolean cancelFavorite(StockInfo stock, String sign)
			throws ServiceException, OrmSqliteException {
		if (stock == null || stock.getStockCode() == null)
			return false;

		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		headerMap.put("stockcode", stock.getStockCode());
		headerMap.put("sign", sign);
		headerMap.put("uid", AppSetting.getInstance(context).getUserId());
		ApiResponse apiResp = null;
		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_STOCK);
		log.setActionDesc("删除自选股");
		try {
			apiResp = (ApiResponse) callGetApi(
					DzlcAndroidConfig.CANCELFAVORITE, headerMap,
					ApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
		}
		if (apiResp != null && apiResp.isSuccess()) {
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			// 删除本地的自选股记录
			StockInfoDaoImpl dao = new StockInfoDaoImpl(context);
			stock.uid = AppSetting.getInstance(context).getUserId() ;
			dao.deleteStock(stock);
			return true;
		} else {
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return false;
		}
	}

	/**
	 * 清除股票历史记录
	 */
	@Override
	public Boolean cleanStock() throws ServiceException {
		Map<String, String> headMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		ApiResponse apiResp = null;
		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_STOCK);
		log.setActionDesc("清除股票历史记录");
		try {
			apiResp = (ApiResponse) callPostApi(DzlcAndroidConfig.CLEANSEACH,
					headMap, ApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
		}
		if (apiResp != null && apiResp.isSuccess()) {
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			return true;
		} else {
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return false;
		}
	}

	/**
	 * 最新股票 一键选股的功能
	 */
	@Override
	public NewStockInfoContent getNewsStockInfoList(String pageNum,
			String pageSize) throws ServiceException {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("pagenumber", pageNum);
		headerMap.put("pagesize", pageSize);
		headerMap.put("time", "2014-02-19");

		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_GSBD);
		log.setActionDesc("最新股票");
		NewStockInfoListApiResponse apiResp = null;
		apiResp = (NewStockInfoListApiResponse) callPostApi(
				DzlcAndroidConfig.URL_ONE_KEY_CHOOSE_STOCK, headerMap,
				NewStockInfoListApiResponse.class);
		if (apiResp != null && apiResp.isSuccess()) {
			return apiResp.getData() == null ? null : apiResp.getData();
		} else {
			return null;
		}
	}

	@Override
	public Boolean deleteCancelFavorite() throws ServiceException,
			OrmSqliteException {
		Map<String, String> headerMap = AppSetting.getInstance(context)
				.getCommonHeaderMap(true);
		if (headerMap.get("userid") != "") {
			headerMap.put("uid", headerMap.get("userid"));
		}
		ApiResponse apiResp = null;
		OpenPartActionLog log = new OpenPartActionLog(context,
				OpenPartActionLog.PART_ID_STOCK);
		log.setActionDesc("删除自选股");
		try {
			apiResp = (ApiResponse) callGetApi(DzlcAndroidConfig.DELETE_STOCK,
					headerMap, ApiResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode() + ":" + e.getErrMsg());
		}
		if (apiResp != null && apiResp.isSuccess()) {
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			StockInfoDaoImpl dao = new StockInfoDaoImpl(context);
			dao.deleteAllStock(AppSetting.getInstance(context).getUserId());
			return true;
		} else {
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return false;
		}
	}
	
	
	
	
	/**
	 * 通过uid  获取股票的信息
	 * @param uid
	 * @return
	 * @throws OrmSqliteException
	 */
	public List<StockInfo> queryStockInfoFromDb(String uid) throws OrmSqliteException{
		if(uid == null || "".equals(uid))
			return null;
		StockInfoDaoImpl dao = new StockInfoDaoImpl(context);
		List<StockInfo> list = dao.queryStocks(uid);
		return list ;
	}
	
	
}

/**
 * 我的股票浏览记录
 * 
 * @author Administrator
 * 
 */
class StockMysearchPageContentApiResponse extends AbstractApiResponse {
	private StockMysearchPageContent data;

	public StockMysearchPageContent getData() {
		return data;
	}

	public void setData(StockMysearchPageContent data) {
		this.data = data;
	}

}

class StockVideoPageContentApiResponse extends AbstractApiResponse {
	private StockVideoPageContent data;

	public StockVideoPageContent getData() {
		return data;
	}

	public void setData(StockVideoPageContent data) {
		this.data = data;
	}
}

class StockInfoListApiResponse extends AbstractApiResponse {
	private List<StockInfo> data;

	public List<StockInfo> getData() {
		return data;
	}

	public void setData(List<StockInfo> data) {
		this.data = data;
	}
}

class NewStockInfoListApiResponse extends AbstractApiResponse {
	private NewStockInfoContent data;

	public NewStockInfoContent getData() {
		return data;

	}

	public void setData(NewStockInfoContent data) {
		this.data = data;

	}
}

/**
 * 股票详细信息
 * 
 * @author Administrator
 * 
 */
class searchStockApiResponse extends AbstractApiResponse {
	private StockInfo data;

	public StockInfo getData() {
		return data;
	}

	public void setData(StockInfo data) {
		this.data = data;
	}

}
