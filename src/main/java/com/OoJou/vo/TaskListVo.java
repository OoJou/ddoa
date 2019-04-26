package com.OoJou.vo;

import java.util.Date;

public class TaskListVo {
    private Integer taskId;

    private String taskTitle;

    private Integer taskStatus;

    private String taskRequester;

    private String taskResponder;

    private Date createTime;

    private Date updateTime;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskRequester() {
		return taskRequester;
	}

	public void setTaskRequester(String taskRequester) {
		this.taskRequester = taskRequester;
	}

	public String getTaskResponder() {
		return taskResponder;
	}

	public void setTaskResponder(String taskResponder) {
		this.taskResponder = taskResponder;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    
}
