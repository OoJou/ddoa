package com.OoJou.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.OoJou.pojo.Task;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
    
    //新增方法
    //检查处理人有没有变更、留言有没有新增，等于0时表示有变更
    int checkResponserIsChange(@Param("taskId")int taskId,@Param("currentResponder")String currentResponder);
    int checkMessageIsNew(@Param("taskId")int taskId,@Param("newMessage")String newMessage);
    //检查历史处理人包含了当前处理人没有，等于0时表示没有
    int checkOldResponser(@Param("taskId")int taskId,@Param("currentResponder")String currentResponder);
    
    //待处理任务、发起任务、已关闭任务三个列表
    List<Task> selectTaskOfUserByUsername(String userName);
    List<Task> selectTaskOfUserCreateByUsername(String userName);
    List<Task> selectTaskOfUserCloseByUsername(String userName);
    
    //管理界面-查询全部
    List<Task> selectAllTask();
    
    
    
}