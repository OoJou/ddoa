package com.OoJou.dao;

import org.apache.ibatis.annotations.Param;

import com.OoJou.pojo.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer userId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer userId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	//新增方法，使用sql语句的count()函数返回值进行判断
	int checkUsername(String userName);

	int checkEmail(String userEmail);

	// @param,在声明mapper写sql语句且参数为map时使用.声明的名字给sql调用.
	User selectLogin(@Param("userName") String userName, @Param("userPassword") String userPassword);

	String selectQuestionByUsername(String userName);

	int checkAnswer(@Param("userName") String userName, @Param("userQuestion") String userQuestion,
			@Param("userAnswer") String userAnswer);

	int updatePasswordByUsername(@Param("userName") String userName, @Param("passwordNew") String passwordNew);

	int checkPassword(@Param(value = "userPassword") String userPassword, @Param("userId") Integer userId);

	int checkEmailByUserId(@Param(value = "userEmail") String userEmail, @Param(value = "userId") Integer userId);
}