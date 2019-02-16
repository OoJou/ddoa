package com.OoJou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OoJou.common.ServerResponse;
import com.OoJou.dao.DepartmentMapper;
import com.OoJou.pojo.Department;
import com.OoJou.service.IDepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("iDepartmentService")
public class DepartmentServiceImpl implements IDepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;
	
	public ServerResponse<PageInfo> getAllDepartment(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Department> departmentList=departmentMapper.selectAllDepartment();
		
		PageInfo pageResult=new PageInfo(departmentList);
		return ServerResponse.createBySuccess("获取成功", pageResult);
	}

	public ServerResponse<Department> createDepartment(Department department) {
		int resultCount=departmentMapper.insert(department);
		if (resultCount==0) {
			return ServerResponse.createByErrorMsg("创建失败");
		}
		return ServerResponse.createBySuccess("创建成功", department);
	}

	public ServerResponse<Department> getDepartmentDetails(int departmentId) {
		Department department =departmentMapper.selectByPrimaryKey(departmentId);
		if (department==null) {
			return ServerResponse.createByErrorMsg("部门不存在");
		}
		return ServerResponse.createBySuccess("获取部门信息成功", department);
	}

	public ServerResponse<Department> updateDepartment(Department department) {
		int resultCount=departmentMapper.updateByPrimaryKeySelective(department);
		if (resultCount==0) {
			return ServerResponse.createByErrorMsg("更新数据失败");
		}
		return ServerResponse.createBySuccess("更新数据成功", department);
	}

	public ServerResponse<String> deleteDepartment(int departmentId) {
		int resultCount=departmentMapper.deleteByPrimaryKey(departmentId);
		if (resultCount==0) {
			return ServerResponse.createByErrorMsg("删除失败");
		}
		return ServerResponse.createBySuccessMsg("删除成功");
	}

}
