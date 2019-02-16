package com.OoJou.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.OoJou.common.Const;
import com.OoJou.common.ResponseCode;
import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.User;
import com.OoJou.service.IUserService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService iUserService;

    
    /**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
    
	@RequestMapping(value = "register.do", method = RequestMethod.POST)
	public ServerResponse<String> register(User user) {
		return iUserService.register(user);
	}
	//下面的check是username还是email注册不用了，前端已写死为username注册
	@RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
	public ServerResponse<String> checkValid(String str, String type) {
		return iUserService.checkValid(str, type);
	}
	
	/**
	 * 用户登录
	 */
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public ServerResponse<User> login(String username, String password, HttpSession session) {
		ServerResponse<User> response = iUserService.login(username, password);
		if (response.isSuccess()) {
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		return response;
	}
	
	/**
	 * 用户注销
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "logout.do", method = RequestMethod.POST)
	public ServerResponse<String> logout(HttpSession session) {
		session.removeAttribute(Const.CURRENT_USER);
		return ServerResponse.createBySuccess();
	}
	
	/**
	 * 点击用户信息时
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
	public ServerResponse<User> getUserInfo(HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户的信息");
		}
		return ServerResponse.createBySuccess(user);
	}
	
	/**
	 * 用户点击忘记密码后，进入忘记密码的问题页
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
	public ServerResponse<String> forgetGetQuestion(String username) {
		return iUserService.selectQuestion(username);
	}
	
	/**
	 * 检查用户回答忘记密码问题是否正确
	 * 
	 * @param username
	 * @param question
	 * @param answer
	 * @return
	 */
	@RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
	public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
		return iUserService.checkAnswer(username, question, answer);
	}

	/**
	 * 回答正确问题后，设置新密码
	 * 
	 * @param username
	 * @param passwordNew
	 * @param forgetToken
	 * @return
	 */
	@RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
	public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
		return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
	}
	
	/**
	 * 登录状态下修改密码
	 * 
	 * @param session
	 * @param passwordOld
	 * @param passwordNew
	 * @return
	 */
	@RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
	public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		return iUserService.resetPassword(passwordOld, passwordNew, user);
	}
	
	/**
	 * 修改信息
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "update_information.do", method = RequestMethod.POST)
	public ServerResponse<User> update_information(HttpSession session, User user) {
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if (currentUser == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		//Id和UserName是不能更改的，在控制层就set当前用户进去，逻辑层就不要set了
		user.setUserId(currentUser.getUserId());
		user.setUserName(currentUser.getUserName());
		ServerResponse<User> response = iUserService.updateInformation(user);
		if (response.isSuccess()) {
			response.getData().setUserName(currentUser.getUserName());
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		return response;
	}
	
	@RequestMapping(value = "get_information.do", method = RequestMethod.POST)
	public ServerResponse<User> get_information(HttpSession session) {
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if (currentUser == null) {
			return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
		}
		return iUserService.getInformation(currentUser.getUserId());
	}
	
	
	
	/**
	 * 管理页面-获取用户信息列表
	 */
	@RequestMapping(value = "get_all_user.do")
	public ServerResponse<PageInfo> getAllUser(
			@RequestParam(value="pageNum",defaultValue="1")int pageNum
			,@RequestParam(value="pageSize",defaultValue="5")int pageSize
			,HttpSession session) {
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if (currentUser == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		return iUserService.getAllUser(pageNum, pageSize);
	}
	
	/**
	 * 管理页面-新增用户
	 */
	@RequestMapping(value = "create_user.do")
	public ServerResponse<User> createUser(User user,HttpSession session) {
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if (currentUser == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		return iUserService.createUser(user);
	}
	
	/**
	 * 查看用户详情
	 */
	
	@RequestMapping(value = "get_user_details.do")
	public ServerResponse<User> getUserDetails(int userId,HttpSession session) {
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if (currentUser == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		return iUserService.getUserDetails(userId);
	}
	/**
	 * 管理页面-修改用户信息
	 */
	@RequestMapping(value = "update_user.do")
	public ServerResponse<User> updateUser(User user,HttpSession session) {
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if (currentUser == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		return iUserService.updateUser(user);
	}
	
	/**
	 * 管理页面-删除用户
	 */
	@RequestMapping(value = "delete_user.do")
	public ServerResponse<String> deleteUser(int userId,HttpSession session) {
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if (currentUser == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		return iUserService.deleteUser(userId);
	}
	
	/**
	 * 管理页面-修改角色权限
	 */
	@RequestMapping(value = "set_role.do")
	public ServerResponse<User> setRole(int roleId,int userId,HttpSession session) {
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if (currentUser == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		return iUserService.setRole(roleId, userId);
	}
	
}
