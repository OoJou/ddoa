package com.OoJou.service;

import java.awt.List;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.Task;
import com.OoJou.pojo.User;
import com.OoJou.vo.TaskListVo;
import com.github.pagehelper.PageInfo;


public interface ITaskService {
	//发起任务、处理任务(处理之前要查到任务)、任务详情
	ServerResponse<Task> createTask(Task task);
	ServerResponse<Task> handleTask(Task task);
	ServerResponse<Task> getTaskDetails(int taskId);
	
	//查看当前用户任务{1.处理人为自己的任务 2.发起者为自己的任务 3.处理人或者发起者为自己且已关闭的任务}
	//分别对应“待处理任务”“发起任务”“发起任务”“已关闭任务”三个栏目页
	ServerResponse<PageInfo> getTaskOfUser(String username,int pageNum,int pageSize);
	ServerResponse<PageInfo> getTaskOfUserCreate(String username,int pageNum,int pageSize);
	ServerResponse<PageInfo> getTaskOfUserClose(String username,int pageNum,int pageSize);
	
	//管理页方法，新增和修改在前面，功能一直不再重复
	ServerResponse<PageInfo> getAllTask(int pageNum,int pageSize);
	ServerResponse<String> deleteTask(int taskId);
}
