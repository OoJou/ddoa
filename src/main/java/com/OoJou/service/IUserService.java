package com.OoJou.service;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.User;
import com.github.pagehelper.PageInfo;

public interface IUserService {
	//登录、注册、忘记密码
	ServerResponse<User> login(String username, String password);
	ServerResponse<String> register(User user);
	ServerResponse<String> checkValid(String str, String type);
	ServerResponse<String> checkAnswer(String username, String question, String answer);
	ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);
	ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);
	
	//查、改用户信息
	ServerResponse<String> selectQuestion(String username);
	ServerResponse<User> getInformation(Integer userId);
	ServerResponse<User> updateInformation(User user);
	ServerResponse<String> checkAdminRole(User user);
	
	//管理页面- 增删查改
	ServerResponse<PageInfo> getAllUser(int pageNum,int pageSize);
	ServerResponse<User> getUserDetails(int userId);
	ServerResponse<User> createUser(User user);
	ServerResponse<User> setRole(int roleId,int userId);
	ServerResponse<User> updateUser(User user);
	ServerResponse<String> deleteUser(int userId);
}
