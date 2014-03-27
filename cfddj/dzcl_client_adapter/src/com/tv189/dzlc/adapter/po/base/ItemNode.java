/***
* class:AbstractModuleInfo.java
* create:cavenshen
* time:2:44:41 PM
*
*/


package com.tv189.dzlc.adapter.po.base;


/**
 * @author cavenshen
 *
 */
public class ItemNode extends AbstractNode{
	private int index;
	private String type;
	private TextNode catalog;
	private TextNode title;
	private TextNode desc;
	private ImgNode backimg;
	private ImgNode icon;
	private ColorNode backcolor;
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	
	/**
	 * @return the catalog
	 */
	public TextNode getCatalog() {
		return catalog;
	}
	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(TextNode catalog) {
		this.catalog = catalog;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the title
	 */
	public TextNode getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(TextNode title) {
		this.title = title;
	}
	/**
	 * @return the desc
	 */
	public TextNode getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(TextNode desc) {
		this.desc = desc;
	}
	/**
	 * @return the backimg
	 */
	public ImgNode getBackimg() {
		return backimg;
	}
	/**
	 * @param backimg the backimg to set
	 */
	public void setBackimg(ImgNode backimg) {
		this.backimg = backimg;
	}
	/**
	 * @return the icon
	 */
	public ImgNode getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(ImgNode icon) {
		this.icon = icon;
	}
	/**
	 * @return the backcolor
	 */
	public ColorNode getBackcolor() {
		return backcolor;
	}
	/**
	 * @param backcolor the backcolor to set
	 */
	public void setBackcolor(ColorNode backcolor) {
		this.backcolor = backcolor;
	}
	
}
