package com.OoJou.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OoJou.common.Const;
import com.OoJou.common.ServerResponse;
import com.OoJou.common.TokenCache;
import com.OoJou.dao.UserMapper;
import com.OoJou.pojo.Task;
import com.OoJou.pojo.User;
import com.OoJou.service.IUserService;
import com.OoJou.utils.MD5Util;
import com.OoJou.vo.ResponderListVo;
import com.OoJou.vo.TaskListVo;
import com.OoJou.vo.UserListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service("iUserService")//名字对应于controller中要注入的对象名。
public class UserServiceImpl implements IUserService {
	
	//创建的时候没使用vo处理，暂时不理
	
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
		validResponse = this.checkValid(user.getUserPhone(), Const.PHONE);
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
				if(str!=null) {
					if(str.equals("")) {
						return ServerResponse.createByErrorMsg("用户名不能为空");
					}
				}
			}
			if (Const.EMAIL.equals(type)) {
				int resultCount = userMapper.checkEmail(str);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMsg("email已存在");
				}
			}
			if (Const.PHONE.equals(type)) {
				int resultCount = userMapper.checkPhone(str);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMsg("手机号已存在");
				}
				if(str!=null) {
					if(str.equals("")) {
						return ServerResponse.createByErrorMsg("手机号不能为空");
					}
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
			return ServerResponse.createBySuccess("用户："+username,question);
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
			return ServerResponse.createBySuccess("请在12个小时内重置密码",forgetToken);
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

	/**
	 * 用于用户个人修改信息
	 */
	public ServerResponse<User> updateInformation(User user) {
		// 如果修改密码，要MD5加密
		if(user.getUserPassword()!=null&&user.getUserPassword()!="") {
			user.setUserPassword(MD5Util.MD5EncodeUtf8(user.getUserPassword()));
		}
		//用户名相同
		ServerResponse response =this.checkUpdateValid(user.getUserName(), Const.USERNAME, user.getUserId());
		if (!response.isSuccess()) {
			return response;
		}
		// email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
		response =this.checkUpdateValid(user.getUserEmail(), Const.EMAIL, user.getUserId());
		if (!response.isSuccess()) {
			return response;
		}
		//Phone相同
		response =this.checkUpdateValid(user.getUserPhone(), Const.PHONE, user.getUserId());
		if (!response.isSuccess()) {
			return response;
		}
		//创建新的User类对象，把你想要用户能更新的内容set进去。这样保证前端就算传了其他值，也不会更新，只更新当前set的
		//另外Id，已在controller中设置成当前用户Id
		User updateUser = new User();
		updateUser.setUserId(user.getUserId());
		updateUser.setUserName(user.getUserName());
		updateUser.setUserEmail(user.getUserEmail());
		updateUser.setUserPhone(user.getUserPhone());
		updateUser.setUserQuestion(user.getUserQuestion());
		updateUser.setUserAnswer(user.getUserAnswer());
		updateUser.setUserPassword(user.getUserPassword());

		int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
		if (updateCount > 0) {
			User resultUser=userMapper.selectByPrimaryKey(user.getUserId());
			return ServerResponse.createBySuccess("更新个人信息成功", resultUser);
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
	public ServerResponse<String> checkAdminRole(User user) {
		if (user != null && user.getUserLevel().intValue() == Const.Role.ROLE_ZONGJINGLI
				|| user.getUserLevel().intValue() == Const.Role.ROLE_BUMENJINGLI
				|| user.getUserLevel().intValue() == Const.Role.ROLE_XINGZHENGRENYUAN) {
			return ServerResponse.createBySuccess();
		}
		return ServerResponse.createByError();
	}
	
	//后面可以用这个方法加上部门信息，就不用写sql的内连接了
	//好处是方便，不好的地方是for循环和set频繁，效率不高
	private UserListVo assembleUserListVo(User user) {
		UserListVo userListVo = new UserListVo();
		userListVo.setUserId(user.getUserId());
		userListVo.setUserName(user.getUserName());
		userListVo.setUserLevel(user.getUserLevel());
		userListVo.setUserEmail(user.getUserEmail());
		userListVo.setUserPhone(user.getUserPhone());
		userListVo.setUserPassword(user.getUserPassword());
		userListVo.setCreateTime(user.getCreateTime());
		userListVo.setUpdateTime(user.getUpdateTime());
		return userListVo;
	}
	
	/**
	 * 后期加判断，只有10001-10003才有资格获取数据
	 */
	public ServerResponse<PageInfo> getAllUser(int pageNum,int pageSize,String sortType,User user) {
		PageHelper.startPage(pageNum,pageSize);
		List<User> userList = null;
		// 模糊查询
		if(!sortType.equals("DESC")&& !sortType.equals("ASC")) {
			userList=userMapper.selectAllUser();
		}
		if (sortType.equals("DESC")) {
			userList=userMapper.selectAllUserByDESC(user);//pojo
		}else {
			userList=userMapper.selectAllUserByASC(user);//pojo
		}
		
		List<UserListVo> userListVoList=new ArrayList<UserListVo>();//vo
		for(User userItem : userList) {
			UserListVo userListVo = assembleUserListVo(userItem);
			userListVoList.add(userListVo);
		}
		PageInfo pageResult=new PageInfo(userList);
		pageResult.setList(userListVoList);
//		if (userList.size()==0) {
//			return ServerResponse.createByErrorMsg("查无数据");
//		}
		return ServerResponse.createBySuccess("查询成功", pageResult);
	}

	public ServerResponse<User> createUser(User user) {
		// 新写valid处理信息的校验,
		ServerResponse validResponse = this.checkValid(user.getUserName(), Const.USERNAME);
		if (!validResponse.isSuccess()) {
			return validResponse;
		}
		validResponse = this.checkValid(user.getUserEmail(), Const.EMAIL);
		if (!validResponse.isSuccess()) {
			return validResponse;
		}
		validResponse = this.checkValid(user.getUserPhone(), Const.PHONE);
		if (!validResponse.isSuccess()) {
			return validResponse;
		}
		//判断是否为5个等级其中之一
		ServerResponse roleValidResponse = this.checkRoleValid(user.getUserLevel());
		if (!roleValidResponse.isSuccess()) {
			return roleValidResponse;
		}
		// MD5加密
		user.setUserPassword(MD5Util.MD5EncodeUtf8(user.getUserPassword()));
		// 生成insert是有返回值的，给开发者作判断，等于0时表示插入失败
		int resultCount = userMapper.insert(user);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMsg("创建失败");
		}
		user.setUserPassword(StringUtils.EMPTY);//后面可以不返回user，这里方便测试，但返回时一定要置空
		return ServerResponse.createBySuccess("创建成功",user);
	}
	
	
	public ServerResponse<User> getUserDetails(int userId) {
		User user=userMapper.selectByPrimaryKey(userId);
		if (user==null) {
			return ServerResponse.createByErrorMsg("用户不存在");
		}
		user.setUserPassword(StringUtils.EMPTY);
		return ServerResponse.createBySuccess("获取用户信息成功", user);
	}
	
	//这个setRole是修改时候用的，判断后写入数据库。不适合插入时用
	public ServerResponse<User> setRole(int roleId,int userId) {
		if (roleId!=Const.Role.ROLE_YUANGONG
				&&roleId!=Const.Role.ROLE_XINGZHENGRENYUAN
				&&roleId!=Const.Role.ROLE_BUMENZHUGUAN
				&&roleId!=Const.Role.ROLE_BUMENJINGLI
				&&roleId!=Const.Role.ROLE_ZONGJINGLI) {
			return ServerResponse.createByErrorMsg("无此角色");
		}
		User user=userMapper.selectByPrimaryKey(userId);
		user.setUserLevel(roleId);
		int resultCount=userMapper.updateByPrimaryKeySelective(user);
		if(resultCount==0) {
			return ServerResponse.createByErrorMsg("修改失败");
		}
		user.setUserPassword(StringUtils.EMPTY);
		return ServerResponse.createBySuccess("修改成功", user);
	}
	
	/**
	 * updateUser跟updateInfomation不一样！！
	 * 已修改
	 */
	public ServerResponse<User> updateUser(User user) {
		// 如果修改密码，要MD5加密
		if(user.getUserPassword()!=null&&user.getUserPassword()!="") {
			user.setUserPassword(MD5Util.MD5EncodeUtf8(user.getUserPassword()));
		}
		//用户名相同
		ServerResponse response =this.checkUpdateValid(user.getUserName(), Const.USERNAME, user.getUserId());
		if (!response.isSuccess()) {
			return response;
		}
		// email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
		response =this.checkUpdateValid(user.getUserEmail(), Const.EMAIL, user.getUserId());
		if (!response.isSuccess()) {
			return response;
		}
		//Phone相同
		response =this.checkUpdateValid(user.getUserPhone(), Const.PHONE, user.getUserId());
		if (!response.isSuccess()) {
			return response;
		}
		
		//控制修改
		User updateUser = new User();
		updateUser.setUserId(user.getUserId());//这个有风险，但没办法，管理员要这个权限才能更新。不保证前端乱传值
		if(user.getUserLevel()!=null) {
		setRole(user.getUserLevel(), user.getUserId());//等级有方法判断，调用修改即可。这里可用intVlaue转换保证类型为int
		}
		updateUser.setUserPassword(user.getUserPassword());
		updateUser.setUserEmail(user.getUserEmail());
		updateUser.setUserPhone(user.getUserPhone());
//		updateUser.setUserDepartmentId(user.getUserDepartmentId());
//		updateUser.setUserDepartmentName(user.getUserDepartmentName());
//		updateUser.setUserLeaderId(user.getUserLeaderId());
//		updateUser.setUserLeaderName(user.getUserLeaderName());

		int resultCount4=userMapper.updateByPrimaryKeySelective(user);
		if(resultCount4==0) {
			return ServerResponse.createByErrorMsg("修改失败");
		}
		
		User resultUser=userMapper.selectByPrimaryKey(user.getUserId());
		resultUser.setUserPassword(StringUtils.EMPTY);
		resultUser.setUserAnswer(StringUtils.EMPTY);
		return ServerResponse.createBySuccess("修改成功", resultUser);
	}
	
	public ServerResponse<String> checkUpdateValid(String str, String type,int id) {
		if (StringUtils.isNotBlank(type)) {
			// 开始校验，判断type是email还是username，然后分别进行check
			if (Const.USERNAME.equals(type)) {
				int resultCount = userMapper.checkUserNameByUserId(str, id);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMsg("用户名已存在");
				}
				if(str!=null) {
					if(str.equals("")) {
						return ServerResponse.createByErrorMsg("用户名不能为空");
					}
				}
			}
			if (Const.EMAIL.equals(type)) {
				int resultCount = userMapper.checkEmailByUserId(str, id);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMsg("email已存在");
				}
			}
			if (Const.PHONE.equals(type)) {
				int resultCount = userMapper.checkPhoneByUserId(str, id);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMsg("手机号已存在");
				}
				if(str!=null) {
					if(str.equals("")) {
						return ServerResponse.createByErrorMsg("手机号不能为空");
					}
				}
			}
		} else {
			return ServerResponse.createByErrorMsg("参数错误");
		}
		return ServerResponse.createBySuccessMsg("校验成功");
	}

	public ServerResponse<String> deleteUser(int userId) {
		int resultCount=userMapper.deleteByPrimaryKey(userId);
		if(resultCount==0) {
			return ServerResponse.createByErrorMsg("删除失败");
		}
		return ServerResponse.createBySuccessMsg("删除成功");
	}

	//为了查询全部处理人设置的vo类
	private UserListVo assembleResponderListVo(User user) {
		UserListVo ResponderListVo = new UserListVo();
		ResponderListVo.setUserId(user.getUserId());
		ResponderListVo.setUserName(user.getUserName());
		ResponderListVo.setCreateTime(user.getCreateTime());
		ResponderListVo.setUpdateTime(user.getUpdateTime());
		return ResponderListVo;
	}
	
	public ServerResponse<PageInfo> getAllResponder(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<User> userList=userMapper.selectAllUser();//pojo
		
		List<UserListVo> ResponderListVoList=new ArrayList<UserListVo>();//vo
		for(User userItem : userList) {
			UserListVo userListVo = assembleResponderListVo(userItem);
			ResponderListVoList.add(userListVo);
		}
		PageInfo pageResult=new PageInfo(userList);
		pageResult.setList(ResponderListVoList);
		return ServerResponse.createBySuccess("查询成功", pageResult);
	}

	@Override
	public ServerResponse<String> checkRoleValid(int roleId) {
		if (roleId!=Const.Role.ROLE_YUANGONG
				&&roleId!=Const.Role.ROLE_XINGZHENGRENYUAN
				&&roleId!=Const.Role.ROLE_BUMENZHUGUAN
				&&roleId!=Const.Role.ROLE_BUMENJINGLI
				&&roleId!=Const.Role.ROLE_ZONGJINGLI) {
			return ServerResponse.createByError();
		}
		return ServerResponse.createBySuccess();
	}



}
