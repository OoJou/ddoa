package com.OoJou.common;

public class Const {
	public static final String CURRENT_USER = "currentUser"; //当前用户
	public static final String EMAIL = "email";//邮箱
	public static final String USERNAME = "username";//用户名
	public interface Role{//角色
		int ROLE_ZONGJINGLI=10001;
		int ROLE_BUMENJINGLI=10002;
		int ROLE_XINGZHENGRENYUAN=10003;
		int ROLE_BUMENZHUGUAN=10004;
		int ROLE_YUANGONG=10005;
	}
	
	//任务状态
	public interface TaskStatus{
		int STATUS_YIGUANBI=20001;
		int STATUS_YICHULI=20002;
		int STATUS_CHULIZHONG=20003;
		int STATUS_DAICHULI=20004;
	}
}
