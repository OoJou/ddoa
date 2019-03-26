package com.OoJou.controller;

import javax.servlet.http.HttpSession;

import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OoJou.common.Const;
import com.OoJou.common.ServerResponse;
import com.OoJou.dao.NoticeMapper;
import com.OoJou.pojo.Notice;
import com.OoJou.pojo.User;
import com.OoJou.service.INoticeService;
import com.OoJou.service.IUserService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private INoticeService iNoticeService;
	
	/**
	 * 获取公告详情
	 */
	@RequestMapping(value="get_notice_details.do")
	public ServerResponse<Notice> getNoticeDetails(int noticeId,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取公告详情");
		}
		return iNoticeService.getNoticeDetails(noticeId);
	}
	
	/**
	 * 管理页面-获取全部公告列表
	 */
	@RequestMapping(value="get_all_notice.do")
	public ServerResponse<PageInfo> getAllNotice(
			@RequestParam(value="pageNum",defaultValue="1")int pageNum
			,@RequestParam(value="pageSize",defaultValue="5")int pageSize
			,@RequestParam(value="sortType",defaultValue="DESC")String sortType 
			,Notice notice
			,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取公告列表");
		}
		return iNoticeService.getAllNotice(pageNum, pageSize,sortType,notice);
	}
	
	/**
	 * 管理页面-新增公告
	 */
	@RequestMapping(value="create_notice.do")
	public ServerResponse<Notice> createNotice(Notice notice,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法创建公告");
		}
		notice.setNoticePubilsher(user.getUserName());//强制为当前用户上传
		return iNoticeService.createNotice(notice);
		
	}
	
	/**
	 * 管理页面-修改公告
	 */
	@RequestMapping(value="update_notice.do")
	public ServerResponse<Notice> updateNotice(Notice notice,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法修改公告");
		}
		if(!iUserService.checkAdminRole(user).isSuccess()){
	        return ServerResponse.createByErrorMsg("无权限操作");
	    }
		return iNoticeService.updateNotice(notice);
	}
	
	
	/**
	 * 管理页面-删除公告
	 */
	@RequestMapping(value="delete_notice.do")
	public ServerResponse<String> deleteNotice(int noticeId,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法删除公告");
		}
		if(!iUserService.checkAdminRole(user).isSuccess()){
	        return ServerResponse.createByErrorMsg("无权限操作");
	    }
		return iNoticeService.deleteNotice(noticeId);
	}
	
}
