package com.OoJou.dao;

import java.util.List;

import com.OoJou.pojo.SysFile;

public interface SysFileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    SysFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);

    //新增方法
    //管理页面-查
    List<SysFile> selectAllFile();
    List<SysFile> selectAllFileByDESC(SysFile sysFile);
    List<SysFile> selectAllFileByASC(SysFile sysFile);
}