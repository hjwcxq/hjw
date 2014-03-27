package com.orientmedia.app.cfddj.service;
public interface EventHandler {
	public abstract void onMessage(String data);
	public abstract void netInfo(boolean netBreak);
}