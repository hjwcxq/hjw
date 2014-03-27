package com.tv189.dzlc.adapter.po.repertoire;

import java.util.List;

public class RepertoireInfo {
	 private String duration;
	 private String endtime;
	 private String isedit;
	 private String isrecord;
	 private String issubscribe;
	 private String liveid;
	 private String pid;
	 private String starttime;
	 private String state;
	 private String title;
	 private List<RepertoireDatailInfo> videos;
	 
	 
	public List<RepertoireDatailInfo> getVideos() {
		return videos;
	}
	public void setVideos(List<RepertoireDatailInfo> videos) {
		this.videos = videos;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getIsedit() {
		return isedit;
	}
	public void setIsedit(String isedit) {
		this.isedit = isedit;
	}
	public String getIsrecord() {
		return isrecord;
	}
	public void setIsrecord(String isrecord) {
		this.isrecord = isrecord;
	}
	public String getIssubscribe() {
		return issubscribe;
	}
	public void setIssubscribe(String issubscribe) {
		this.issubscribe = issubscribe;
	}
	public String getLiveid() {
		return liveid;
	}
	public void setLiveid(String liveid) {
		this.liveid = liveid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	} 
}
