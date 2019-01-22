package com.OoJou.common;

import static org.mockito.Mockito.timeout;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
//返回过滤掉属性为null的值,key和value都不会序列化
public class ServerResponse<T> implements Serializable {
	//状态，信息，数据
	private int status;
	private String msg;
	private T data;
	
	/**
	 * 私有构造
	 * T表示的泛型能代表各种参数类型，当传入值为String时，优先调用对应的构造，而不是泛型构造。
	 * 当然，现在是私有 访问不了
	 * @param status
	 */
	private  ServerResponse(int status) {
		this.status=status;
	}
	private  ServerResponse(int status,T data) {
		this.status=status;
		this.data=data;
	}
	private  ServerResponse(int status,String msg) {
		this.status=status;
		this.msg=msg;
	}
	private  ServerResponse(int status,String msg,T data) {
		this.status=status;
		this.msg=msg;
		this.data=data;
	}
	
	/**
	 * 判断是否成功，这个方法序列化时忽略，不需要显示
	 * @return
	 */
	@JsonIgnore
	public boolean isSuccess() {
		return this.status == ResponseCode.SUCCESS.getCode();
	}
	/**
	 * 设置get方法获取服务器返回的参数
	 * 不用设置set方法，开放获取，禁止修改
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	public T getData() {
		return data;
	}

	public String getMsg() {
		return msg;
	}
	
	/**
	 * 创建成功状态的返回，通过私有的构造注入success状态，信息，数据
	 * 返回的就是封装过后的数据
	 * 常用第三个
	 * @return
	 */
	public static <T> ServerResponse<T> createBySuccess(){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
	}
	
	public static <T> ServerResponse<T> createBySuccessMsg(String msg){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
	}
	
	public static <T> ServerResponse<T> createBySuccess(T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
	}
	
	public static <T> ServerResponse<T> createBySuccess(String msg,T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
	}
	
	/**
	 * 创建错误状态的返回，通过私有的构造注入error状态，信息
	 * 开发者可以传新的code和msg描述错误信息
	 * 常用第一个
	 * @return
	 * 
	 */
	public static <T> ServerResponse<T> createByError(){
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
	}
	public static <T> ServerResponse<T> createByErrorMsg(String errorMsg){
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMsg);
	}
	public static <T> ServerResponse<T> createByErrorCodeMsg(int errorCode,String errorMsg){
		return new ServerResponse<T>(errorCode,errorMsg);
	}
}
