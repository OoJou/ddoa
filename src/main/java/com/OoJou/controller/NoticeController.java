package com.OoJou.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OoJou.common.Const;
import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.Notice;
import com.OoJou.pojo.User;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/notice")
public class NoticeController {

	
	/**
	 * 管理页面-获取全部公告列表
	 */
	@RequestMapping(value="get_all_notice.do")
	public ServerResponse<Notice> getAllNotice(){
		return null;
	}
	
	/**
	 * 管理页面-新增公告
	 */
	@RequestMapping(value="create_notice.do")
	public ServerResponse<Notice> createNotice(){
		return null;
	}
	
	/**
	 * 管理页面-修改公告
	 */
	@RequestMapping(value="update_notice.do")
	public ServerResponse<Notice> updateNotice(){
		return null;
	}
	
	
	/**
	 * 管理页面-删除公告
	 */
	@RequestMapping(value="delete_notice.do")
	public ServerResponse<String> deleteNotice(){
		return null;
	}
	
}
