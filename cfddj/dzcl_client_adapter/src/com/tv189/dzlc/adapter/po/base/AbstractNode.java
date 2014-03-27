/***
 * class:AbstractDisplay.java
 * create:cavenshen
 * time:2:55:34 PM
 *
 */

package com.tv189.dzlc.adapter.po.base;

/**
 * @author cavenshen
 * 
 */
public class AbstractNode {
	protected boolean isShow;
	protected int width;
	protected int height;
	protected AbstractAction action;

	public AbstractAction getAction() {
		return action;
	}

	public void setAction(AbstractAction action) {
		this.action = action;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
