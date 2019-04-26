package com.OoJou.service;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.Department;
import com.github.pagehelper.PageInfo;

public interface IDepartmentService {

	//管理页面-增删查改
	ServerResponse<PageInfo> getAllDepartment(int pageNum,int pageSize);
	ServerResponse<Department> createDepartment(Department department);
	ServerResponse<Department> getDepartmentDetails(int departmentId);
	ServerResponse<Department> updateDepartment(Department department);
	ServerResponse<String> deleteDepartment(int departmentId);
	
}
