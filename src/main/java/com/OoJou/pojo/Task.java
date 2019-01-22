package com.OoJou.pojo;

import java.util.Date;

public class Task {
    private Integer taskId;

    private String taskTitle;

    private String taskDetails;

    private Integer taskStatus;

    private String taskRequester;

    private String taskResponder;

    private Date taskStartTime;

    private Date taskEndTime;

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
        this.taskTitle = taskTitle == null ? null : taskTitle.trim();
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails == null ? null : taskDetails.trim();
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
        this.taskRequester = taskRequester == null ? null : taskRequester.trim();
    }

    public String getTaskResponder() {
        return taskResponder;
    }

    public void setTaskResponder(String taskResponder) {
        this.taskResponder = taskResponder == null ? null : taskResponder.trim();
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
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