package com.OoJou.service;

import org.assertj.core.condition.Not;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.Notice;
import com.github.pagehelper.PageInfo;

public interface INoticeService {
	//管理页面，增删查改
	ServerResponse<PageInfo> getAllNotice(int pageNum,int pageSize);
	ServerResponse<Notice> createNotice(Notice notice);
	ServerResponse<Notice> getNoticeDetails(int noticeId);
	ServerResponse<Notice> updateNotice(Notice notice);
	ServerResponse<String> deleteNotice(int noticeId);
}
