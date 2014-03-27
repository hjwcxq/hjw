package com.tv189.dzlc.adapter.service.abstractclass;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import mobi.dreambox.frameowrk.core.constant.ErrorCodeConstants;
import mobi.dreambox.frameowrk.core.http.DboxHTTPClientException;
import mobi.dreambox.frameowrk.core.http.DboxHttpClient;
import mobi.dreambox.frameowrk.core.http.DboxStringHttpResponse;
import mobi.dreambox.frameowrk.core.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.exception.ErrorActionConst;
import com.tv189.dzlc.adapter.exception.ErrorCodeConst;
import com.tv189.dzlc.adapter.po.common.ApiResponse;
import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.util.Utils;

public abstract class AbstractDzlcService {

	private static final String JSONObject = null;

	protected static Context context;
	
	

	protected AbstractDzlcService(Context context) {
		this.context = context;
	}

	protected Object callPostApi(String apiUrl, Map<String, String> reqHeader,
			Class classes) throws ServiceException {
		if (!Utils.checkNetWork(context))
			throw new ServiceException(
					ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL,	
					ErrorActionConst.CONNECT_NET_FAIL_MES);
		String respJsonStr = callApiCommon(apiUrl, reqHeader, "post");
		return processResponseObj(classes, respJsonStr);
	}

	protected Object callGetApi(String apiUrl, Map<String, String> paraMap,
			Class classes) throws ServiceException {
		if (!Utils.checkNetWork(context))
			throw new ServiceException(
					ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL,
					ErrorActionConst.CONNECT_NET_FAIL_MES);

		StringBuffer sb = new StringBuffer();
		int i = 0;
		if (paraMap != null) {
			for (Map.Entry<String, String> entry : paraMap.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				if (i == 0)
					sb.append("?");
				else
					sb.append("&");
				sb.append(key + "=" + value);
				i++;
			}
		}
		String respJsonStr;
		respJsonStr = callApiCommon(apiUrl + sb.toString(), null, "get");
		return processResponseObj(classes, respJsonStr);
	}

	private Object processResponseObj(Class classes, String respJsonStr)
			throws ServiceException {
		if (classes.getName().equals(String.class.getName())) {
			return respJsonStr;
		}
		if(respJsonStr == null || "".equals(respJsonStr))
			return null;
		

		if (classes.getName().equals(ApiResponse.class.getName())) {
			try {
				ApiResponse resp = new ApiResponse();
				JSONObject respObj = new JSONObject(respJsonStr);
				if (!respObj.isNull("code")) {
					resp.setCode(respObj.getString("code"));
				}
				if (!respObj.isNull("msg")) {
					resp.setMsg(respObj.getString("msg"));
				}

				if (!respObj.isNull("data")) {
					if (respObj.get("data") instanceof JSONObject) {
						resp.setData(respObj.getJSONObject("data").toString());
					} else {
						resp.setData(respObj.getString("data"));
					}
				}

				if (respObj.isNull("data")) {
					if (respObj instanceof JSONObject) {
						resp(respObj);
					}
				}
				return resp;
			} catch (JSONException e) {
				e.printStackTrace();
				throw new ServiceException(
						ErrorCodeConst.CODE_PARSE_JSON_ERROR, e.getMessage());
			}
		} else {
			if (respJsonStr != null) {//到这时正常
				Log.i(classes.getName(), respJsonStr);
				if (respJsonStr.startsWith("{")) {
 					return new Gson().fromJson(respJsonStr, classes);
				} else {
					throw new ServiceException(
							ErrorCodeConst.CODE_JSON_FORMAT_ERROR, "json格式异常");
				}
			} else
				return null;
		}
	}

	private void resp(org.json.JSONObject respObj) {
		// TODO Auto-generated method stub
	}

	// 语音API
	protected Object callPostApi2(File file, String apiUrl,
			Map<String, String> reqHeader, Class classes)
			throws ServiceException {
		if (!Utils.checkNetWork(context))
			throw new ServiceException(
					ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL,
					ErrorActionConst.CONNECT_NET_FAIL_MES);

		String respJsonStr = uploadFile(file, apiUrl, reqHeader);
		return processResponseObj(classes, respJsonStr);
	}

	public String uploadFile(File soundQuestionFile, String url,
			Map<String, String> reqHeader) throws ServiceException {
		if (!Utils.checkNetWork(context))
			throw new ServiceException(
					ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL,
					ErrorActionConst.CONNECT_NET_FAIL_MES);

		String respString = null;
		HttpURLConnection conn = null;
		BufferedInputStream fin = null;
		BufferedOutputStream out = null;
		URL reqUrl;
		try {
			reqUrl = new URL(url);
			conn = (HttpURLConnection) reqUrl.openConnection();
			conn.setConnectTimeout(60000);
			Set<String> key = reqHeader.keySet();
			Iterator it = key.iterator();
			while (it.hasNext()) {
				String next = (String) it.next();
				conn.setRequestProperty(next, reqHeader.get(next));
			}
			conn.setRequestMethod("POST");
			// conn.setUseCaches(true);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// conn.setChunkedStreamingMode(1024*128);
			out = new BufferedOutputStream(conn.getOutputStream());
			fin = new BufferedInputStream(new FileInputStream(
					(File) soundQuestionFile));
			byte[] buf = new byte[1024 * 128];
			int len = -1;
			while ((len = fin.read(buf)) != -1) {
				out.write(buf, 0, len);
				out.flush();
			}
			respString = StringUtil.toString(conn.getInputStream());
		} catch (SocketTimeoutException e) {
			throw new ServiceException(ErrorCodeConstants.HTTP_SOCKET_TIMEOUT,
					e.getMessage());
		} catch (IOException e) {
			throw new ServiceException(ErrorCodeConstants.HTTP_IO_ERROR,
					e.getMessage());
		} finally {
			try {
				if (fin != null) {
					fin.close();
					fin = null;
				}
				if (out != null) {
					out = null;
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (IOException ioe) {
				throw new ServiceException(ErrorCodeConstants.HTTP_IO_ERROR,
						ioe.getMessage());
			}
		}
		return respString;
	}

	/**
	 * 用户名密码登�?大写全部转成小写
	 * 
	 * @param apiUrl
	 * @param reqHeader
	 * @param classes
	 * @return
	 * @throws ServiceException
	 * @throws DboxHTTPClientException
	 */
	protected Object callPostApiV3(String apiUrl,
			Map<String, String> reqHeader, Class classes)
			throws ServiceException {

		if (!Utils.checkNetWork(context))
			throw new ServiceException(
					ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL,
					ErrorActionConst.CONNECT_NET_FAIL_MES);

		String respJsonStr = callApiCommon(apiUrl, reqHeader, "post");
		String phonenum = respJsonStr.replaceAll("phoneNum", "phonenum");
		String username = phonenum.replaceAll("userName", "username");
		String replRespJsonStr = username.replaceAll("nickName", "nickname");
		return processResponseObj(classes, replRespJsonStr);
	}

	private String callApiCommon(String apiUrl, Map<String, String> reqHeader,
			String requestType) throws ServiceException {

		if (!Utils.checkNetWork(context))
			throw new ServiceException(
					ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL,
					ErrorActionConst.CONNECT_NET_FAIL_MES);
		if (reqHeader == null)
			reqHeader = AppSetting.getInstance(context).getCommonHeaderMap(false);
		String phonenum = AppSetting.getInstance(context).getPhoneNumber();
		if (phonenum != null)
			reqHeader.put("phonenum", phonenum);
		reqHeader.put("reqtype", requestType);
		reqHeader.put("networktype", Utils.getAPNType(context));
		reqHeader.put("requrl", apiUrl);
		reqHeader.put("gateway", "");
		reqHeader.put("clientip", "");
		reqHeader.put("ua", Build.DEVICE);
		DboxHttpClient httpClient = new DboxHttpClient(
				DboxStringHttpResponse.class);
		Map<String, String> reqHeaderMap = new HashMap<String, String>();
		if (reqHeader != null) {
			for (Map.Entry<String, String> entry : reqHeader.entrySet()) {
				String key = entry.getKey().toString();
				if (entry.getValue() != null) {
					String value = entry.getValue().toString();
					if (!value.equals(""))
						reqHeaderMap.put(key, URLEncoder.encode(value));
				}
			}
		}
		DboxStringHttpResponse resp = null;
		try {
			if (requestType.equals("post")) {
				resp = (DboxStringHttpResponse) httpClient.post(apiUrl,
						reqHeaderMap, null, "utf-8", 10000, 10000);
			} else if (requestType.equals("get")) {
				resp = (DboxStringHttpResponse) httpClient.get(apiUrl,
						reqHeaderMap, "utf-8", 10000, 10000);
			}
		} catch (DboxHTTPClientException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(e.getMessage(), e.getMessage());
		}

 		if (resp != null && resp.getHttpStatus() == 200) {
			return resp.getResponseData();
		} else {
			return null;
		}
	}
}
