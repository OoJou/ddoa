package com.OoJou.common;

/**
 * 枚举结构
 * 定义各种状态码
 * @author www34
 *
 */
public enum ResponseCode {
	SUCCESS(200,"SUCCESS"),
	ERROR(201,"ERROR"),
	NEED_LOGIN(10,"NEED_LOGIN"),
	ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");
	
	private final int code;
	private final String desc;
	
	private ResponseCode(int code,String desc) {
		this.code=code;
		this.desc=desc;
	}
	
	/**
	 * 设置get方法获取状态码
	 * 不用设置set方法
	 * @return
	 */
	public int getCode() {
		return code;
	}
	
	public String getDesc() {
		return desc;
	}
	
}
