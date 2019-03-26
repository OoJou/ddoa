package com.OoJou.dao;

import java.util.List;

import com.OoJou.pojo.Notice;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer noticeId);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer noticeId);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);
    
    //新增方法
    //管理页面-查
    List<Notice> selectAllNotice();
	List<Notice> selectAllNoticeByASC(Notice notice);
	List<Notice> selectAllNoticeByDESC(Notice notice);
}