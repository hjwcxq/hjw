package com.orientmedia.app.cfddj.module;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.orientmedia.app.cfddj.module.action.ApiAction;
import com.orientmedia.app.cfddj.module.action.MenuAction;
import com.orientmedia.app.cfddj.module.action.UrlAction;
import com.orientmedia.app.cfddj.module.action.VideoAction;
import android.content.Context;
import android.util.Xml;

import com.tv189.dzlc.adapter.config.AppSetting;
import com.tv189.dzlc.adapter.config.MainConst;
import com.tv189.dzlc.adapter.context.SystemContext;
import com.tv189.dzlc.adapter.po.base.AbstractAction;
import com.tv189.dzlc.adapter.po.base.AbstractModule;
import com.tv189.dzlc.adapter.po.base.ColorNode;
import com.tv189.dzlc.adapter.po.base.ImgNode;
import com.tv189.dzlc.adapter.po.base.ItemNode;
import com.tv189.dzlc.adapter.po.base.TextNode;

public class ModuleFactory {

	private static ModuleFactory instance;

	private Context mContext;

	private ModuleFactory(Context mContext) {
		this.mContext = mContext;
	}

	public static ModuleFactory newInstance(Context context) {
		if (instance == null) {
			instance = new ModuleFactory(context);
		}
		instance.mContext = context;
		return instance;
	}

	
	public AbstractModule getModuleByType(String type) {
		if (MainConst.ModuleConst.MODULE_TYPE_FOCUS.equalsIgnoreCase(type))
			return new FocusModule();
		else if (MainConst.ModuleConst.MODULE_TYPE_APP_RECO.equalsIgnoreCase(type))
			return new NavModule();
		else if (MainConst.ModuleConst.MODULE_TYPE_NEWS_RECO.equalsIgnoreCase(type))
			return new NewsModule();
		else if (MainConst.ModuleConst.MODULE_TYPE_GO_ORDER.equalsIgnoreCase(type))
			return new SpaceModule();
		else if (MainConst.ModuleConst.MODULE_TYPE_VIDEO.equalsIgnoreCase(type))
			return new RecommendModule();
//		else if (MainConst.ModuleConst.MODULE_TYPE_FINDITEM.equalsIgnoreCase(type))
//			return new LinearModule();
		else
			return null;
	}
	
	public AbstractAction getActionByType(String type) {
		if (MainConst.ModuleConst.ACTION_TYPE_API.equalsIgnoreCase(type))
			return new ApiAction(mContext);
		else if (MainConst.ModuleConst.ACTION_TYPE_VIDEO.equalsIgnoreCase(type))
			return new VideoAction(mContext);
		else if (MainConst.ModuleConst.ACTION_TYPE_MENU.equalsIgnoreCase(type))
			return new MenuAction(mContext);
		else if (MainConst.ModuleConst.ACTION_TYPE_URL.equalsIgnoreCase(type))
			return new UrlAction(mContext);
		else
			return null;

	}

	private List<AbstractModule> modules;

	public List<AbstractModule> getModules() {
		return modules;
	}

	public void setModules(List<AbstractModule> modules) {
		this.modules = modules;
	}

	private AbstractModule module = null;
	
	private ImgNode imgNode = null;
	
	private TextNode textNode = null;
	
	private ColorNode colorNode = null ;
	
	private ItemNode itemNode = null ;
	
	private List<ItemNode> items = null;
	
	private AbstractAction action = null;
	
	private Map<String,String> paraMap = null;
	
	private String paramKey = "";
	
	private String paramValue = "" ;
	
	public void fromXmlString(InputStream is) {
		modules = new ArrayList<AbstractModule>();
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					break;
				case XmlPullParser.START_TAG:// 开始元素事件
					String name = parser.getName();
					if(name.equalsIgnoreCase("dzcj")){
						AppSetting.getInstance(mContext).setXmlVersion(parser.getAttributeValue(null, "version"));
					}else if(name.equalsIgnoreCase("moduleList")){
						modules = new ArrayList<AbstractModule>();
					}else if(name.equalsIgnoreCase("itemList")){
						items = new ArrayList<ItemNode>();
					}else if (name.equalsIgnoreCase("module")) {
						String type = parser.getAttributeValue(null, "type");
						String index = parser.getAttributeValue(null, "index");
						String widht = parser.getAttributeValue(null,"width");
						String height = parser.getAttributeValue(null,"height");
						module = getModuleByType(type);
						if(module != null){
							if (index != null && !"".equals(index)) {
								module.setIndex(Integer.parseInt(index));
							} else {
								module.setIndex(10);
							}
							if (widht != null && !"".equals(widht)) {
								module.setWidth(Integer.parseInt(widht));
							} else {
								module.setWidth(0);
							}
							if (height != null && !"".equals(height)) {
								module.setHeight(Integer.parseInt(height));
							} else {
								module.setHeight(0);
							}
							module.setType(type);
						}
					} else if (name.equalsIgnoreCase("title")) {//===============节点title
						textNode = new TextNode();
						String isShow = parser.getAttributeValue(null, "show");
						if(isShow == null || "".equals(isShow))
							isShow = "false" ;
						textNode.setShow(Boolean.parseBoolean(isShow));
					} else if (name.equalsIgnoreCase("text")) {
						textNode.setText(parser.nextText());
					} else if(name.equalsIgnoreCase("action")){
						String aType = parser.getAttributeValue(null, "type");
						action = getActionByType(aType);
						if(action != null)
							action.setType(aType);
					}else if (name.equalsIgnoreCase("paraList")) {
						paraMap = new HashMap<String, String>();
					}else if(name.equalsIgnoreCase("url")){
						if(action != null)	
							action.setUrl(parser.nextText());
					}else if(name.equalsIgnoreCase("para")){
						paramKey = parser.getAttributeValue(null, "name");
					}else if(name.equalsIgnoreCase("value")){
						paramValue = parser.nextText() ;
					}else if(name.equalsIgnoreCase("desc")){//===============节点desc
						textNode = new TextNode();
						String isShow = parser.getAttributeValue(null, "show");
						if(isShow == null || "".equals(isShow))
							isShow = "false" ;
						textNode.setShow(Boolean.parseBoolean(isShow));
					}else if(name.equalsIgnoreCase("backimg")){
						imgNode = new ImgNode();
						String isShow = parser.getAttributeValue(null, "show");
						if(isShow == null || "".equals(isShow))
							isShow = "false" ;
						imgNode.setShow(Boolean.parseBoolean(isShow));
						String width = parser.getAttributeValue(null, "width");
						String height = parser.getAttributeValue(null, "height");
						if(width == null || "".equalsIgnoreCase(width))
							width = "0";
						if(height == null || "".equalsIgnoreCase(height))
							height = "0";
						imgNode.setHeight(Integer.parseInt(height));
						imgNode.setWidth(Integer.parseInt(width));
						
					}else if(name.equalsIgnoreCase("src")){
						imgNode.setSrc(parser.nextText());
					}else if(name.equalsIgnoreCase("alt")){
						imgNode.setAlt(parser.nextText());
					}else if(name.equalsIgnoreCase("backcolor")){
						colorNode = new ColorNode();
						String isShow = parser.getAttributeValue(null, "show");
						if(isShow == null || "".equals(isShow))
							isShow = "false" ;
						colorNode.setShow(Boolean.parseBoolean(isShow));
					}else if(name.equalsIgnoreCase("value")){
						colorNode.setValue(parser.nextText());
					}else if(name.equalsIgnoreCase("item")){
						paraMap = new HashMap<String, String>();
						itemNode = new ItemNode();
						String index = parser.getAttributeValue(null, "index");
						if(index == null)
							itemNode.setIndex(items != null ? items.size():10);
						else
							itemNode.setIndex(Integer.parseInt(index));
						itemNode.setType(parser.getAttributeValue(null, "type"));
						String width = parser.getAttributeValue(null, "width");
						String height = parser.getAttributeValue(null, "height");
						if(width == null || "".equalsIgnoreCase(width))
							width = "0";
						if(height == null || "".equalsIgnoreCase(height))
							height = "0";
						itemNode.setHeight(Integer.parseInt(height));
						itemNode.setWidth(Integer.parseInt(width));
					}else if(name.equalsIgnoreCase("catalog")){
						textNode = new TextNode();
						String isShow = parser.getAttributeValue(null, "show");
						if(isShow == null || "".equals(isShow))
							isShow = "false" ;
						textNode.setShow(Boolean.parseBoolean(isShow));
					}else if(name.equalsIgnoreCase("catalogId")){
						textNode = new TextNode();
						textNode.setText(parser.nextText());
					}else if(name.equalsIgnoreCase("icon")){
						imgNode = new ImgNode();
						String isShow = parser.getAttributeValue(null, "show");
						if(isShow == null || "".equals(isShow))
							isShow = "false" ;
						imgNode.setShow(Boolean.parseBoolean(isShow));
						imgNode.setSrc(parser.getAttributeValue(null, "src"));
						imgNode.setAlt(parser.getAttributeValue(null, "alt"));
						String width = parser.getAttributeValue(null, "width");
						String height = parser.getAttributeValue(null, "height");
						if(width == null || "".equalsIgnoreCase(width))
							width = "0";
						if(height == null || "".equalsIgnoreCase(height))
							height = "0";
						imgNode.setHeight(Integer.parseInt(height));
						imgNode.setWidth(Integer.parseInt(width));
					}
					break;
				case XmlPullParser.END_TAG:// 结束元素事件
					String name2 = parser.getName();
					if (name2.equalsIgnoreCase("title")) {
						if(itemNode == null){
							if(action != null){
								action.setParaMap(paraMap);
								textNode.setAction(action);
								paraMap = null ;
							}
							if(module != null)
								module.setTitleNode(textNode);
						}else{
							itemNode.setTitle(textNode);
						}
					}else if(name2.equalsIgnoreCase("desc")){
						if(itemNode == null){
							if(module != null)
								module.setDescNode(textNode);
						}else
							itemNode.setDesc(textNode);
					}else if(name2.equalsIgnoreCase("backimg")){
						if(itemNode == null){
							if(module != null)
								module.setImageNote(imgNode);
						} else
							itemNode.setBackimg(imgNode);
					}else if(name2.equalsIgnoreCase("backcolor")){
						if(itemNode == null){
							if(module != null)
								module.setColorNode(colorNode);
						}else
							itemNode.setBackcolor(colorNode);
					}else if(name2.equalsIgnoreCase("item")){
						if(items != null)
							items.add(itemNode);
						itemNode = null ;
					}else if(name2.equalsIgnoreCase("catalog")){
						if(itemNode != null)
							itemNode.setCatalog(textNode);
					}else if(name2.equalsIgnoreCase("icon")){
						if(itemNode != null)
							itemNode.setIcon(imgNode);
					}else if(name2.equalsIgnoreCase("action")){
						if(itemNode != null){
							itemNode.setAction(action);
							paraMap = null ;
						}
					}else if(name2.equalsIgnoreCase("module")){
						modules.add(module);
					}else if(name2.equalsIgnoreCase("moduleList")){
						setModules(modules);
						SystemContext.getInstance(mContext).setModules(modules);
					}else if (name2.equalsIgnoreCase("paraList")) {
						if(action != null ){
							action.setParaMap(paraMap);
						}
					}else if (name2.equalsIgnoreCase("para")) {
						if(paraMap != null ){
							paraMap.put(paramKey, paramValue);
						}
					}else if(name2.equalsIgnoreCase("itemList")){
						if(module != null)
							module.setItems(items);
					}else if(name2.equalsIgnoreCase("catalogId")){
						if(module != null)
							module.setCatalogIdNode(textNode);
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {

		} finally {

		}

	}

}
