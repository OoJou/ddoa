package com.OoJou.service.impl;

import static org.assertj.core.api.Assertions.not;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OoJou.common.ServerResponse;
import com.OoJou.dao.NoticeMapper;
import com.OoJou.pojo.Notice;
import com.OoJou.service.INoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("iNoticeService")
public class NoticeServiceImpl implements INoticeService {

	@Autowired
	private NoticeMapper noticeMapper;

	public ServerResponse<PageInfo> getAllNotice(int pageNum, int pageSize, String sortType, Notice notice) {
		PageHelper.startPage(pageNum, pageSize);
		List<Notice> noticeList = null;

		// 模糊查询
		if (!sortType.equals("DESC") && !sortType.equals("ASC")) {
			noticeList = noticeMapper.selectAllNotice();
		}
		if (sortType.equals("DESC")) {
			noticeList = noticeMapper.selectAllNoticeByDESC(notice);
		} else {
			noticeList = noticeMapper.selectAllNoticeByASC(notice);
		}
		
		PageInfo pageResult = new PageInfo(noticeList);
		pageResult.setList(noticeList);
		return ServerResponse.createBySuccess("获取成功", pageResult);
	}

	public ServerResponse<Notice> createNotice(Notice notice) {
		// 校验创建的参数 title、detail
		if (notice.getNoticeTitle() == null || notice.getNoticeTitle().equals("")) {
			return ServerResponse.createByErrorMsg("标题不能为空，请输入后重试");
		}
		if (notice.getNoticeDetails() == null || notice.getNoticeDetails().equals("")) {
			return ServerResponse.createByErrorMsg("内容不能为空，请输入后重试");
		}
//		if (notice.getNoticeImage() == null) {
//			return ServerResponse.createByErrorMsg("图片参数未传");
//		}
		int resultCount = noticeMapper.insert(notice);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMsg("创建失败");
		}
		return ServerResponse.createBySuccess("创建成功", notice);
	}

	public ServerResponse<Notice> getNoticeDetails(int noticeId) {
		Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
		if (notice == null) {
			return ServerResponse.createByErrorMsg("公告不存在");
		}

		return ServerResponse.createBySuccess("获取公告信息成功", notice);
	}

	public ServerResponse<Notice> updateNotice(Notice notice) {
		int resultCount = noticeMapper.updateByPrimaryKeySelective(notice);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMsg("更新数据失败");
		}
		return ServerResponse.createBySuccess("更新数据成功", notice);
	}

	public ServerResponse<String> deleteNotice(int noticeId) {
		int resultCount = noticeMapper.deleteByPrimaryKey(noticeId);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMsg("删除失败");
		}
		return ServerResponse.createBySuccessMsg("删除成功");
	}

}
