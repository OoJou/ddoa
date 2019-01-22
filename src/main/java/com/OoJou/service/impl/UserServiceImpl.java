package com.OoJou.service.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OoJou.common.Const;
import com.OoJou.common.ServerResponse;
import com.OoJou.common.TokenCache;
import com.OoJou.dao.UserMapper;
import com.OoJou.pojo.User;
import com.OoJou.service.IUserService;
import com.OoJou.utils.MD5Util;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	public ServerResponse<String> register(User user) {
		// 新写valid处理信息的校验
		ServerResponse validResponse = this.checkValid(user.getUserName(), Const.USERNAME);
		if (!validResponse.isSuccess()) {
			return validResponse;
		}
		validResponse = this.checkValid(user.getUserEmail(), Const.EMAIL);
		if (!validResponse.isSuccess()) {
			return validResponse;
		}
		user.setUserLevel(Const.Role.ROLE_YUANGONG);
		// MD5加密
		user.setUserPassword(MD5Util.MD5EncodeUtf8(user.getUserPassword()));
		// 生成insert是有返回值的，给开发者作判断，等于0时表示插入失败
		int resultCount = userMapper.insert(user);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMsg("注册失败");
		}
		return ServerResponse.createBySuccessMsg("注册成功");
	}

	public ServerResponse<String> checkValid(String str, String type) {
		if (StringUtils.isNotBlank(type)) {
			// 开始校验，判断type是email还是username，然后分别进行check
			if (Const.USERNAME.equals(type)) {
				int resultCount = userMapper.checkUsername(str);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMsg("用户名已存在");
				}
			}
			if (Const.EMAIL.equals(type)) {
				int resultCount = userMapper.checkEmail(str);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMsg("email已存在");
				}
			}
		} else {
			return ServerResponse.createByErrorMsg("参数错误");
		}
		return ServerResponse.createBySuccessMsg("校验成功");
	}

	public ServerResponse<User> login(String username, String password) {
		int resultCount = userMapper.checkUsername(username);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMsg("用户名不存在");
		}
		// 密码登录MD5
		String md5Password = MD5Util.MD5EncodeUtf8(password);
		User user = userMapper.selectLogin(username, md5Password);
		if (user == null) {
			return ServerResponse.createByErrorMsg("密码错误");
		}

		// 处理返回值密码
		user.setUserPassword(StringUtils.EMPTY);
		user.setUserAnswer(StringUtils.EMPTY);
		return ServerResponse.createBySuccess("登录成功", user);
	}

	public ServerResponse<String> selectQuestion(String username) {
		ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
		if (validResponse.isSuccess()) {
			// 用户不存在，因为checkValid里写的是已存在用户时报错，现在不存在是true
			return ServerResponse.createByErrorMsg("用户不存在");
		}
		String question = userMapper.selectQuestionByUsername(username);
		if (StringUtils.isNotBlank(question)) {
			return ServerResponse.createBySuccess(question);
		}
		return ServerResponse.createByErrorMsg("找回密码的问题是空的");
	}

	public ServerResponse<String> checkAnswer(String username, String question, String answer) {
		int resultCount = userMapper.checkAnswer(username, question, answer);
		if (resultCount > 0) {
			// 说明问题及问题答案是这个用户的,并且是正确的
			String forgetToken = UUID.randomUUID().toString();
			// 自定义TokenCache内存块,里面设置了内存块的存活时间,这用于设置回答问题正确后,修改密码的有效时间.
			TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
			return ServerResponse.createBySuccess(forgetToken);
		}
		return ServerResponse.createByErrorMsg("问题的答案错误");
	}

	public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
		if (StringUtils.isBlank(forgetToken)) {
			return ServerResponse.createByErrorMsg("参数错误,token需要传递");
		}
		ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
		if (validResponse.isSuccess()) {
			// 用户不存在
			return ServerResponse.createByErrorMsg("用户不存在");
		}
		// 获取TokenCache获取用户回答问题时设置的值
		// isBlank判断内存块返回的字符串是否为空,为空就表示内存块被清除,回答问题之后超过了重置密码的有效时间
		String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
		if (StringUtils.isBlank(token)) {
			return ServerResponse.createByErrorMsg("token无效或者过期");
		}

		if (StringUtils.equals(forgetToken, token)) {
			String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
			int rowCount = userMapper.updatePasswordByUsername(username, md5Password);

			if (rowCount > 0) {
				return ServerResponse.createBySuccessMsg("修改密码成功");
			}
		} else {
			return ServerResponse.createByErrorMsg("token错误,请重新获取重置密码的token");
		}
		return ServerResponse.createByErrorMsg("修改密码失败");
	}

	public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
		// 登录状态下修改密码
		// 防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
		int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getUserId());
		if (resultCount == 0) {
			return ServerResponse.createByErrorMsg("旧密码错误");
		}

		user.setUserPassword(MD5Util.MD5EncodeUtf8(passwordNew));
		int updateCount = userMapper.updateByPrimaryKeySelective(user);
		if (updateCount > 0) {
			return ServerResponse.createBySuccessMsg("密码更新成功");
		}
		return ServerResponse.createByErrorMsg("密码更新失败");
	}

	public ServerResponse<User> updateInformation(User user) {
		// username是不能被更新的
		// email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
		int resultCount = userMapper.checkEmailByUserId(user.getUserEmail(), user.getUserId());
		if (resultCount > 0) {
			return ServerResponse.createByErrorMsg("email已存在,请更换email再尝试更新");
		}
		User updateUser = new User();
		updateUser.setUserId(user.getUserId());
		updateUser.setUserEmail(user.getUserEmail());
		updateUser.setUserPhone(user.getUserPhone());
		updateUser.setUserQuestion(user.getUserQuestion());
		updateUser.setUserAnswer(user.getUserAnswer());

		int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
		if (updateCount > 0) {
			return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
		}
		return ServerResponse.createByErrorMsg("更新个人信息失败");
	}

	public ServerResponse<User> getInformation(Integer userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			return ServerResponse.createByErrorMsg("找不到当前用户");
		}
		// get信息中，密码是不必要返回的。但json返回一定会有值，所有在这里set为空，返回时显示为“”
		user.setUserPassword(StringUtils.EMPTY);
		return ServerResponse.createBySuccess(user);

	}

	/**
	 * 校验是否是管理员
	 * 
	 * @param user
	 * @return
	 */
	public ServerResponse checkAdminRole(User user) {
		if (user != null && user.getUserLevel().intValue() == Const.Role.ROLE_ZONGJINGLI
				|| user.getUserLevel().intValue() == Const.Role.ROLE_BUMENJINGLI
				|| user.getUserLevel().intValue() == Const.Role.ROLE_XINGZHENGRENYUAN) {
			return ServerResponse.createBySuccess();
		}
		return ServerResponse.createByError();
	}

}
