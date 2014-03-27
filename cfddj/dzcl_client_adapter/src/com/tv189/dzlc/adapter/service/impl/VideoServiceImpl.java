package com.tv189.dzlc.adapter.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tv189.dzlc.adapter.config.APIConfig;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.DzlcAndroidConfig;
import com.tv189.dzlc.adapter.dao.impl.VideoDetailsDaoImpl;
import com.tv189.dzlc.adapter.factory.ClientLogFactory;
import com.tv189.dzlc.adapter.po.common.AbstractApiResponse;
import com.tv189.dzlc.adapter.po.common.OrmSqliteException;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.log.OpenContentActionLog;
import com.tv189.dzlc.adapter.po.log.OpenPartActionLog;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;
import com.tv189.dzlc.adapter.po.video.LiveVideoInfo;
import com.tv189.dzlc.adapter.service.abstractclass.AbstractDzlcService;
import com.tv189.dzlc.adapter.service.impl.LiveVideoInfoResponse.VideoDetailsInfoResponse;
import com.tv189.dzlc.adapter.service.inerface.VideoService;

public class VideoServiceImpl extends AbstractDzlcService implements VideoService{ 
	private static final String ACTION_OPEN_CONTENT = null;
	public static String URL_ENCODEING = "utf-8";

	public static String GET_METHOD = "GET";
	public VideoServiceImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 获取本地视频的列表
	 */
	public List<VideoDetails> getVideosFromDb(String uid) throws OrmSqliteException{
		VideoDetailsDaoImpl dao = new VideoDetailsDaoImpl(context);
		return dao.queryVideoDetails(uid);
	}

	/**
	 * 获取直播列表
	 */
	@Override
	public LiveVideoInfo getVideo() throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(false);  
		LiveVideoInfoResponse apiResp = null;
		OpenPartActionLog log=new OpenPartActionLog(context, OpenPartActionLog.PART_ID_LIVE);
		log.setActionDesc("直播列表");
		try {
				apiResp=(LiveVideoInfoResponse)callPostApi(DzlcAndroidConfig.LIVE_VIDEO, headerMap,LiveVideoInfoResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode()+"："+e.getErrMsg());
		}
		if(apiResp!=null && apiResp.isSuccess()){
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			return apiResp.getData()==null?null:apiResp.getData();
		}else{
			return null;
		}
	}

	/**
	 * 获取直播详情
	 */
	@Override
	public VideoDetails videoDetails(String contentid) throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true); 
		headerMap.put("contentid", contentid);
		OpenContentActionLog log=new OpenContentActionLog(context,OpenPartActionLog.PART_ID_STOCK, contentid);
		VideoDetailsInfoResponse apiResp = null;
		log.setActionDesc("获取直播详情");
		try {
			apiResp=(VideoDetailsInfoResponse)callPostApi(DzlcAndroidConfig.CONTENTDATAIL, headerMap,VideoDetailsInfoResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode()+":"+e.getErrMsg());
		}
		if(apiResp!=null&&apiResp.isSuccess()){
			log.setActionResult("0");
			ClientLogFactory.getInstance().addLog(log);
			return apiResp.getData()==null?null:apiResp.getData();
		}else{
			log.setActionResult("1");
			ClientLogFactory.getInstance().addLog(log);
			return null;
		}
	}

	/**
	 * TV列表
	 */
	@Override
	public String TVVideo(boolean bool) throws ServiceException {
		Map<String,String> headerMap = AppSetting.getInstance(context).getCommonHeaderMap(true);  
		headerMap.put("clienttype", DzlcAndroidConfig.TV_LIVE);
		LiveVideoInfoResponse apiResp = null;
		OpenPartActionLog log=new OpenPartActionLog(context, OpenPartActionLog.PART_ID_LIVE);
		String URL=null;
		log.setActionDesc("TV列表");
		try {
				apiResp=(LiveVideoInfoResponse)callPostApi(DzlcAndroidConfig.LIVE_VIDEO, headerMap,LiveVideoInfoResponse.class);
		} catch (ServiceException e) {
			log.setActionResult("1");
			log.setFailCause(e.getErrCode()+"："+e.getErrMsg());
		}
		if(apiResp!=null&&apiResp.isSuccess()){
			log.setActionResult("0");
			if(bool){ 
				 URL=this.getStringFromURLWithErrorHandler(apiResp.getData().getHighQuality());					
			}else{
				 URL=this.getStringFromURLWithErrorHandler(apiResp.getData().getLowQuality());										
			}
			ClientLogFactory.getInstance().addLog(log);
			 return URL;
		}else{
			 return null;
		}
	}
	public static String getStringFromURLWithErrorHandler(String uri){
		String returnString = null; 
			try {	
				Map<String, String> headerPropertys=new HashMap<String, String>();
				returnString = getStringFromURL(uri
						, URL_ENCODEING
						, headerPropertys
						, null
						, "GET");
			} catch (Exception e) {
			} 
		//若返回为null,则是网络错误 
		return returnString;
	}


/**
	 * 访问url，获取返回string,默认参数进行utf-8编码
	 * @param uri
	 * @param encoding
	 * @param propertys
	 * @param par
	 * @param setType
	 * @return
	 */
	public static String getStringFromURL(String uri, 
										String encoding,
										Map<String, String> headerPropertys,
										Map<String, String> par, String setType) {
		
		StringBuffer buffer = new StringBuffer();
		HttpURLConnection uc = null;
		InputStream content = null;
		String parString = "";
		try {
			//将par解析成parString字符串
			if (par != null && par.size() > 0) {			
				for(Iterator<String> it = par.keySet().iterator(); it.hasNext(); ) {
					String key = (String)it.next();
					if (parString.equalsIgnoreCase("")) {
						try {
							parString = key.toLowerCase() + "=" + URLEncoder.encode(par.get(key), "utf-8");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}else {						
						try {
							parString =parString + "&" + key.toLowerCase() + "=" + URLEncoder.encode(par.get(key), "utf-8");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} 
			} 
			boolean isGet = false;
			if(setType == null || setType.toUpperCase().equalsIgnoreCase(GET_METHOD)){
				isGet = true;
				if (parString != null && !parString.equalsIgnoreCase("")) {
					uri = uri + "?" + parString;
				}
			}
			
			uc = getHttpURLConnection(uri);
			if(!isGet && setType != null && !setType.equals(""))
				uc.setRequestMethod(setType.toUpperCase());
			uc.setConnectTimeout(30 * 1000);
			uc.setDoInput(true);
			if ("post".equalsIgnoreCase(setType)) {
				uc.setDoOutput(true);
			}
			uc.setUseCaches(false);			
			//增加头属性property
			if (headerPropertys != null && headerPropertys.size() > 0) {
				if(!headerPropertys.containsKey("channelid")){
					headerPropertys.put("channelid", APIConfig.CHANNELID);
				}
				String[] keysStrings = headerPropertys.keySet().toArray(new String[]{});
				for (String key:keysStrings) {
					try {

						if(headerPropertys.get(key) !=null)
						{
							uc.addRequestProperty(key.toLowerCase(), URLEncoder.encode(headerPropertys.get(key),"utf-8"));
						}
						else
						{
							uc.addRequestProperty(key.toLowerCase(), "");
						}

						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}	
			
			if(!isGet){
				if (parString != null && !parString.equalsIgnoreCase("")) {
					uc.getOutputStream().write(parString.getBytes());
					uc.getOutputStream().flush();
					uc.getOutputStream().close();
				}	
			}
			uc.connect(); 
			try {
				if (uc.getResponseCode() == 200) {
					if(uc.getContent() != null)
						content = (InputStream)uc.getContent();
					else
						content = uc.getInputStream();
					
					BufferedReader in = new BufferedReader(new InputStreamReader(content, encoding));
					
					String line;
					while ((line = in.readLine()) != null) {
						buffer.append(line);
					}
					String result = buffer.toString().trim();
					return result;
				}else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}	
			
			
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally{
			try{
				if (content != null) {
					content.close();
				}
				if (uc != null) {
					uc.disconnect();
				}
			}
			catch(Exception exClose){
				exClose.printStackTrace();
			}
		}
	}

private static HttpURLConnection getHttpURLConnection(String uri) throws MalformedURLException, IOException {
	// TODO Auto-generated method stub
	return (HttpURLConnection) new URL(uri).openConnection(); 
}
}


class LiveVideoInfoResponse extends AbstractApiResponse{
	private LiveVideoInfo data;

	public LiveVideoInfo getData() {
		return data;
	}

	public void setData(LiveVideoInfo data) {
		this.data = data;
	} 
	class VideoDetailsInfoResponse extends AbstractApiResponse{
		private VideoDetails data;

		public VideoDetails getData() {
			return data;
		}

		public void setData(VideoDetails data) {
			this.data = data;
		}
	}
	
	
}
