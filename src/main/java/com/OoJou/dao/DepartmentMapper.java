package com.OoJou.dao;

import java.util.List;

import com.OoJou.pojo.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    
    //新增方法
    //管理页面-查全部
    List<Department> selectAllDepartment();
}