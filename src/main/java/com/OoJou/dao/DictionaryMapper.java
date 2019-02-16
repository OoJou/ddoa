package com.OoJou.dao;

import java.util.List;

import com.OoJou.pojo.Dictionary;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Integer key);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Integer key);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);
    
    //新增方法
    //管理页面-获取全部
    List<Dictionary> selectAllDictionary();
}