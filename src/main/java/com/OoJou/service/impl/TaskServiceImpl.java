package com.OoJou.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OoJou.common.Const;
import com.OoJou.common.ServerResponse;
import com.OoJou.dao.TaskMapper;
import com.OoJou.pojo.Task;
import com.OoJou.service.ITaskService;
import com.OoJou.vo.TaskListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("iTaskService")
public class TaskServiceImpl implements ITaskService {

	@Autowired
	private TaskMapper taskMapper;
	
	public ServerResponse<Task> createTask(Task task) {
		//校验创建的参数 title、detail
		if(task.getTaskTitle()==null || task.getTaskTitle().equals("")) {
			return ServerResponse.createByErrorMsg("标题不能为空，请输入后重试");
		}
		if(task.getTaskDetails()==null || task.getTaskDetails().equals("")) {
			return ServerResponse.createByErrorMsg("内容不能为空，请输入后重试");
		}
		if(task.getTaskResponder()==null || task.getTaskResponder().equals("")) {
			return ServerResponse.createByErrorMsg("处理人不能为空，请选择后重试");
		}
		//创建时，状态设置为 待处理。处理人添加至历史处理人
		task.setTaskStatus(Const.TaskStatus.STATUS_DAICHULI);
		task.setTaskOldResponder(","+task.getTaskResponder());
		task.setTaskMessage("#");
		int resultCount=taskMapper.insert(task);
		if (resultCount==0) {
			return ServerResponse.createByErrorMsg("发起任务失败");
		}
		return ServerResponse.createBySuccess("发起任务成功",task);
	}
	
	public ServerResponse<Task> getTaskDetails(int taskId) {
		//点击处理、查看等按钮
		Task task=taskMapper.selectByPrimaryKey(taskId);
		if(task==null) {
			return ServerResponse.createByErrorMsg("任务不存在");
		}
		return ServerResponse.createBySuccess("获取任务信息成功", task);
	}

	public ServerResponse<Task> handleTask(Task task) {
		//校验创建的参数 title、detail
		if(task.getTaskTitle()==null || task.getTaskTitle().equals("")) {
			return ServerResponse.createByErrorMsg("标题不能为空，请输入后重试");
		}
		if(task.getTaskDetails()==null || task.getTaskDetails().equals("")) {
			return ServerResponse.createByErrorMsg("内容不能为空，请输入后重试");
		}
		//获取旧任务数据
		Task oldtask=taskMapper.selectByPrimaryKey(task.getTaskId());
		//1.前端传来最新一次更改的处理人，check处理人有无变更 2.处理人有变更，则查出历史处理人，用，号隔开，拼接处理人，然后再存入
		//responserCount==0时，当前处理人改变。oldResponserCount==0时，当前处理人不在历史处理人中
		int responserCount=taskMapper.checkResponserIsChange(task.getTaskId(), task.getTaskResponder());
		int oldResponserCount=taskMapper.checkOldResponser(task.getTaskId(), task.getTaskResponder());
		if(responserCount==0 && oldResponserCount==0) {//处理人有变更,且不在历史处理人中,则添加到历史处理人
			task.setTaskOldResponder(oldtask.getTaskOldResponder()+task.getTaskResponder()+",");
		}else {
			task.setTaskOldResponder(oldtask.getTaskOldResponder());
		}
		//前端拼接好的留言，与处理人类似处理，但又有些不同
		//messageCount==0，当前的留言有改变。留言没有历史字段，直接用>>拼接
		if (task.getTaskMessage()!=null) {
			int messageCount=taskMapper.checkMessageIsNew(task.getTaskId(), task.getTaskMessage());
			System.out.println(messageCount);
			if(messageCount==0) {
				task.setTaskMessage(oldtask.getTaskMessage()+task.getTaskMessage()+"#");
			}else {
				task.setTaskMessage(oldtask.getTaskMessage());
			}
		}
		
		//开始更新数据
		int resultCount=taskMapper.updateByPrimaryKeySelective(task);
		if(resultCount==0) {
			return ServerResponse.createBySuccessMsg("更新数据失败");
		}
		//获取最新数据
		Task newtask=taskMapper.selectByPrimaryKey(task.getTaskId());
		return ServerResponse.createBySuccess("更新数据成功", newtask);
	}
	
	private TaskListVo assembleTaskListVo(Task task) {
		TaskListVo taskListVo = new TaskListVo();
		taskListVo.setTaskId(task.getTaskId());
		taskListVo.setTaskTitle(task.getTaskTitle());
		taskListVo.setTaskRequester(task.getTaskRequester());
		taskListVo.setTaskResponder(task.getTaskResponder());
		taskListVo.setTaskStatus(task.getTaskStatus());
		taskListVo.setCreateTime(task.getCreateTime());
		taskListVo.setUpdateTime(task.getUpdateTime());
		return taskListVo;
	}
	
	public ServerResponse<PageInfo> getTaskOfUser(String username,int pageNum,int pageSize) {
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾	
		PageHelper.startPage(pageNum, pageSize);
		List<Task> taskList=taskMapper.selectTaskOfUserByUsername(username);//Task-pojo的列表
		
		List<TaskListVo> taskListVoList =new ArrayList<TaskListVo>();//存转化成TaskListvo-vo的列表
		for(Task taskItem : taskList) {//循环转换
			TaskListVo taskListVo = assembleTaskListVo(taskItem);
			taskListVoList.add(taskListVo);
		}
		PageInfo pageResult=new PageInfo(taskList);
		pageResult.setList(taskListVoList);
		return ServerResponse.createBySuccess("查询成功",pageResult);
	}

	public ServerResponse<PageInfo> getTaskOfUserCreate(String username,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Task> taskList=taskMapper.selectTaskOfUserCreateByUsername(username);//Task-pojo的列表
		
		List<TaskListVo> taskListVoList =new ArrayList<TaskListVo>();//存转化成TaskListvo-vo的列表
		for(Task taskItem : taskList) {//循环转换
			TaskListVo taskListVo = assembleTaskListVo(taskItem);
			taskListVoList.add(taskListVo);
		}
		PageInfo pageResult=new PageInfo(taskList);
		pageResult.setList(taskListVoList);
		return ServerResponse.createBySuccess("查询成功",pageResult);
	}

	public ServerResponse<PageInfo> getTaskOfUserClose(String username,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Task> taskList=taskMapper.selectTaskOfUserCloseByUsername(username);//Task-pojo的列表
		
		List<TaskListVo> taskListVoList =new ArrayList<TaskListVo>();//存转化成TaskListvo-vo的列表
		for(Task taskItem : taskList) {//循环转换
			TaskListVo taskListVo = assembleTaskListVo(taskItem);
			taskListVoList.add(taskListVo);
		}
		PageInfo pageResult=new PageInfo(taskList);
		pageResult.setList(taskListVoList);
		return ServerResponse.createBySuccess("查询成功",pageResult);
	}
	
	public ServerResponse<PageInfo> getTaskOfUserNow(String username,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Task> taskList=taskMapper.selectTaskOfUserNowByUsername(username);//Task-pojo的列表
		
		List<TaskListVo> taskListVoList =new ArrayList<TaskListVo>();//存转化成TaskListvo-vo的列表
		for(Task taskItem : taskList) {//循环转换
			TaskListVo taskListVo = assembleTaskListVo(taskItem);
			taskListVoList.add(taskListVo);
		}
		PageInfo pageResult=new PageInfo(taskList);
		pageResult.setList(taskListVoList);
		return ServerResponse.createBySuccess("查询成功",pageResult);
	}

	public ServerResponse<PageInfo> getAllTask(int pageNum,int pageSize,String sortType,Task task) {
		PageHelper.startPage(pageNum, pageSize);
		List<Task> taskList=null;//Task-pojo的列表
		
		// 模糊查询
		if(!sortType.equals("DESC")&& !sortType.equals("ASC")) {
			return ServerResponse.createByErrorMsg("无此排序");
		}
		if (sortType.equals("DESC")) {
			taskList=taskMapper.selectAllTaskByDESC(task);//pojo
		}else {
			taskList=taskMapper.selectAllTaskByASC(task);//pojo
		}
		
		List<TaskListVo> taskListVoList =new ArrayList<TaskListVo>();//存转化成TaskListvo-vo的列表
		for(Task taskItem : taskList) {//循环转换
			TaskListVo taskListVo = assembleTaskListVo(taskItem);
			taskListVoList.add(taskListVo);
		}
		PageInfo pageResult=new PageInfo(taskList);
		pageResult.setList(taskListVoList);
		return ServerResponse.createBySuccess("查询成功",pageResult);
	}
	
	public ServerResponse<String> deleteTask(int taskId) {
		int resultCount=taskMapper.deleteByPrimaryKey(taskId);
		if (resultCount==0) {
			return ServerResponse.createByErrorMsg("删除失败");
		}
		return ServerResponse.createBySuccessMsg("删除成功");
	}

	
}
