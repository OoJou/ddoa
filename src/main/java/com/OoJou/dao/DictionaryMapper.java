package com.OoJou.dao;

import com.OoJou.pojo.Dictionary;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Integer key);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Integer key);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);
}