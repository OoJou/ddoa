package com.OoJou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.Department;
import com.OoJou.pojo.Dictionary;

@RestController
@RequestMapping("/department")
public class DepartmentController {
//	部门昨晚全部的增删改，即管理页面对接完后，新增自定义类进行内连接操作查找
	
	/**
	 * 管理页面-获取全部部门列表
	 */
	@RequestMapping(value="get_all_department.do")
	public ServerResponse<Department> getAllDepartment(){
		return null;
	}
	
	/**
	 * 管理页面-新增部门
	 */
	@RequestMapping(value="create_department.do")
	public ServerResponse<Department> createDepartment(){
		return null;
	}
	
	
	/**
	 * 管理页面-修改部门
	 */
	@RequestMapping(value="update_department.do")
	public ServerResponse<Department> updateDepartment(){
		return null;
	}
	
	/**
	 * 管理页面-删除部门
	 */
	@RequestMapping(value="delete_department.do")
	public ServerResponse<Department> deleteDepartment(){
		return null;
	}
	
	
}
