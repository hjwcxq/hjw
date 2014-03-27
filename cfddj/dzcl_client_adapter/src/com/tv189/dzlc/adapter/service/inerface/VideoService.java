package com.tv189.dzlc.adapter.service.inerface;

import com.tv189.dzlc.adapter.po.common.ServiceException;
import com.tv189.dzlc.adapter.po.sqlpo.VideoDetails;
import com.tv189.dzlc.adapter.po.video.LiveVideoInfo;

public interface VideoService {
		LiveVideoInfo getVideo() throws ServiceException;
		String TVVideo(boolean bool) throws ServiceException;
		VideoDetails videoDetails(String contentid) throws ServiceException;
 
}
