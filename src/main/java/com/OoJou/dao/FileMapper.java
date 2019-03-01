package com.OoJou.dao;

import java.util.List;

import com.OoJou.pojo.File;

public interface FileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);
    
    //新增方法
    //管理页面-查
    List<File> selectAllFile();
}