package com.OoJou.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OoJou.common.Const;
import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.Task;
import com.OoJou.pojo.User;
import com.OoJou.service.ITaskService;
import com.OoJou.service.IUserService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private ITaskService iTaskService;
	
	@Autowired
	private IUserService iUserService;
	
	/**
	 * 发起任务
	 */
	@RequestMapping(value="create_task.do")
	public ServerResponse<Task> createTask(Task task,HttpSession session) {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法发起任务");
		}
		//发起者设为当前用户
		task.setTaskRequester(user.getUserName());
		return iTaskService.createTask(task);
	}
	
	
	/**
	 * “待处理任务”列表，历史处理人为自己的任务
	 */
	@RequestMapping(value="get_task_of_user.do")
	public ServerResponse<PageInfo> getTaskOfUser(
			@RequestParam(value="pageNum",defaultValue="1")int pageNum
			,@RequestParam(value="pageSize",defaultValue="5")int pageSize
			,HttpSession session) {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取当前任务列表");
		}
		String username=user.getUserName();
		return iTaskService.getTaskOfUser(username, pageNum, pageSize);
	}
	
	/**
	 * “发起任务”列表，发起者为自己的任务
	 */
	@RequestMapping(value="get_task_of_user_create.do")
	public ServerResponse<PageInfo> getTaskOfUserCreate(
			@RequestParam(value="pageNum",defaultValue="1")int pageNum
			,@RequestParam(value="pageSize",defaultValue="5")int pageSize
			,HttpSession session) {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取当前任务列表");
		}
		String username=user.getUserName();
		return iTaskService.getTaskOfUserCreate(username, pageNum, pageSize);
	}
	
	
	/**
	 * “已关闭任务”列表，历史处理人或者发起人为自己的，并且已关闭的任务
	 */
	@RequestMapping(value="get_task_of_user_close.do")
	public ServerResponse<PageInfo> getTaskOfUserClose(
			@RequestParam(value="pageNum",defaultValue="1")int pageNum
			,@RequestParam(value="pageSize",defaultValue="5")int pageSize
			,HttpSession session) {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取当前任务列表");
		}
		String username=user.getUserName();
		return iTaskService.getTaskOfUserClose(username, pageNum, pageSize);
	}
	
	/**
	 * 任务详情
	 */
	@RequestMapping(value="get_task_details.do")
	public ServerResponse<Task> getTaskDetails(int taskId,HttpSession session) {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取任务详情");
		}
		return iTaskService.getTaskDetails(taskId);
	}
	
	/**
	 * 处理任务(其实就是修改任务信息)，处理任务前其实是经过查看任务详情这一步的,详情弹框要显示全部信息，除了updatetime之类的
	 */
	@RequestMapping(value="handle_task.do")
	public ServerResponse<Task> handleTask(Task task,HttpSession session) {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法处理任务");
		}
		//前端或controller控制，已关闭的任务不能更改，除非管理
		if(task.getTaskStatus().intValue()==20001 
				&& user.getUserLevel().intValue()==Const.Role.ROLE_YUANGONG
				|| user.getUserLevel().intValue()==Const.Role.ROLE_BUMENZHUGUAN) {
			return ServerResponse.createByErrorMsg("无权限修改任务状态");
		}
		return iTaskService.handleTask(task);
	}
	
	/**
	 * 管理页面-获取全部任务列表
	 */
	@RequestMapping(value="get_all_task.do")
	public ServerResponse<PageInfo> getAllTask(
			@RequestParam(value="pageNum",defaultValue="1")int pageNum
			,@RequestParam(value="pageSize",defaultValue="5")int pageSize
			,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法处理任务");
		}
		if(user.getUserLevel().intValue()==Const.Role.ROLE_YUANGONG
				&& user.getUserLevel().intValue()==Const.Role.ROLE_BUMENZHUGUAN) {
			return ServerResponse.createByErrorMsg("无权限操作");
		}
		return iTaskService.getAllTask(pageNum, pageSize);
	}
	
	/**
	 * 管理页面-删除任务
	 */
	@RequestMapping(value="delete_task.do")
	public ServerResponse<String> deleteTask(int taskId,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法处理任务");
		}
		if(user.getUserLevel().intValue()==Const.Role.ROLE_YUANGONG
				&& user.getUserLevel().intValue()==Const.Role.ROLE_BUMENZHUGUAN) {
			return ServerResponse.createByErrorMsg("无权限操作");
		}
		return iTaskService.deleteTask(taskId);
	}
}
