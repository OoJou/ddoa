package com.OoJou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.OoJou.pojo.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer userId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer userId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	//新增方法，使用sql语句的count()函数返回值进行判断。用于判断新增和注册时候检查是否已经存在
	int checkUsername(String userName);
	int checkEmail(String userEmail);
	int checkPhone(String userPhone);

	// @param,在写sql语句且声明参数parameterTyp=map时使用.声明的“名字”能给sql调用.
	User selectLogin(@Param("userName") String userName, @Param("userPassword") String userPassword);
	String selectQuestionByUsername(String userName);
	int checkAnswer(@Param("userName") String userName, @Param("userQuestion") String userQuestion,
			@Param("userAnswer") String userAnswer);
	int updatePasswordByUsername(@Param("userName") String userName, @Param("passwordNew") String passwordNew);
	int checkPassword(@Param(value = "userPassword") String userPassword, @Param("userId") Integer userId);
	
	//用于判断修改的时候检查是否和除自己的账号外的账号重复
	int checkUserNameByUserId(@Param(value = "userName") String userName, @Param(value = "userId") Integer userId);
	int checkEmailByUserId(@Param(value = "userEmail") String userEmail, @Param(value = "userId") Integer userId);
	int checkPhoneByUserId(@Param(value = "userPhone") String userPhone, @Param(value = "userId") Integer userId);
	
	//管理页面,一个查所有处理人，其他两个为模糊查询的倒序和正序查取方法
	List<User> selectAllUser();
	List<User> selectAllUserByASC(User user);
	List<User> selectAllUserByDESC(User user);
}