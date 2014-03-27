/***
* class:ModuleInfo.java
* create:cavenshen
* time:3:32:33 PM
*
*/


package com.tv189.dzlc.adapter.po.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author cavenshen
 *
 */
public abstract class AbstractModule {
	
	private int index;
	
	public TextNode getDescNode() {
		return descNode;
	}

	public void setDescNode(TextNode descNode) {
		this.descNode = descNode;
	}

	private String type;
	
	private int width;
	
	private int height;
	
	private List<ItemNode> items;
	
	private ImgNode imageNote;
	
	private ColorNode colorNode;
	
	private TextNode titleNode;
	
	private TextNode descNode;
	
	private TextNode catalogIdNode;
	

	public TextNode getCatalogIdNode() {
		return catalogIdNode;
	}

	public void setCatalogIdNode(TextNode catalogIdNode) {
		this.catalogIdNode = catalogIdNode;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ImgNode getImageNote() {
		return imageNote;
	}

	public void setImageNote(ImgNode imageNote) {
		this.imageNote = imageNote;
	}

	public ColorNode getColorNode() {
		return colorNode;
	}

	public void setColorNode(ColorNode colorNode) {
		this.colorNode = colorNode;
	}

	public TextNode getTitleNode() {
		return titleNode;
	}

	public void setTitleNode(TextNode titleNode) {
		this.titleNode = titleNode;
	}

	public List<ItemNode> getItems() {
		return items;
	}

	public void setItems(List<ItemNode> items) {
		this.items = items;
	}
	
	public abstract View getView(Context cont);
	
}
