package com.tv189.dzlc.adapter.service.inerface;

import java.util.List;

import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.StockInfo;
import com.tv189.dzlc.adapter.po.stock.NewStockInfo;
import com.tv189.dzlc.adapter.po.stock.NewStockInfoContent;
import com.tv189.dzlc.adapter.po.stock.StockMysearchPageContent;
import com.tv189.dzlc.adapter.po.stock.StockVideoPageContent;

public interface StockService {
	//股票搜索
	ApiResponse sousStock(String keyword,String id) throws ServiceException;
	//热门股票
	List<StockInfo> stockListHot() throws ServiceException;
	//查看自选股
	List<StockInfo> favoriteList(String stockcode,String uid) throws ServiceException,OrmSqliteException;
	//股票频道页面
	StockVideoPageContent blocklist(String id,String pagenum,String pagesize) throws ServiceException;
	//浏览股票记录
	StockMysearchPageContent mysearchlist(String pagenum,String pagesize) throws ServiceException;
	//个性股票查询
	List<StockInfo> dispositionStock(String stockCode) throws ServiceException;
	//添加自选股
	ApiResponse addDispositionStock(String uid,StockInfo stock,String sign) throws ServiceException,OrmSqliteException;
	//股票详细信息
	StockInfo searchStock(String keyword) throws ServiceException;
	//自选股的删除收藏cancelfavorite
	Boolean cancelFavorite(StockInfo stock,String sign) throws ServiceException,OrmSqliteException;
	Boolean deleteCancelFavorite() throws ServiceException,OrmSqliteException;
	
	//清除股票历史记录
	Boolean cleanStock() throws ServiceException;
	//清除股票历史记录
	NewStockInfoContent getNewsStockInfoList(String pagenum,String pagesize) throws ServiceException;
}
