package com.OoJou.vo;

import java.util.Date;

public class UserListVo {
    private Integer userId;

    private String userName;

    private Integer userLevel;

    private String userEmail;

    private String userPhone;

    private String userPassword;

    private Integer userDepartmentId;

    private Integer userLeaderId;

    private Date createTime;

    private Date updateTime;
    
    private String departmentName;
    
    private String LeaderName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getUserDepartmentId() {
		return userDepartmentId;
	}

	public void setUserDepartmentId(Integer userDepartmentId) {
		this.userDepartmentId = userDepartmentId;
	}

	public Integer getUserLeaderId() {
		return userLeaderId;
	}

	public void setUserLeaderId(Integer userLeaderId) {
		this.userLeaderId = userLeaderId;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getLeaderName() {
		return LeaderName;
	}

	public void setLeaderName(String leaderName) {
		LeaderName = leaderName;
	}
    
    
}
