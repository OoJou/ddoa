package com.OoJou.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OoJou.common.Const;
import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.File;
import com.OoJou.pojo.User;
import com.OoJou.service.IFileService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private IFileService iFileService;
	
	/**
	 * 管理页面-获取全部文件列表
	 */
	@RequestMapping(value="get_all_file.do")
	public ServerResponse<PageInfo> getAllFile(
			@RequestParam(value="pageNum",defaultValue="1")int pageNum
			,@RequestParam(value="pageSize",defaultValue="5")int pageSize
			,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取公告详情");
		}
		return iFileService.getAllFile(pageNum, pageSize);
	}
	
	/**
	 * 获取文件信息详情
	 */
	@RequestMapping(value="get_file_details.do")
	public ServerResponse<File> getFileDetails(int fileId,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取公告详情");
		}
		return iFileService.getFileDetails(fileId);
	}
	
	/**
	 * 管理页面-上传文件
	 */
	@RequestMapping(value="upload_file.do")
	public ServerResponse<File> uploadFile(){
		return null;
	}
	
	/**
	 * 管理页面-下载文件
	 */
	@RequestMapping(value="download_file.do")
	public ServerResponse<File> downloadFile(){
		return null;
	}
	
	/**
	 * 管理页面-修改文件
	 */
	@RequestMapping(value="update_file.do")
	public ServerResponse<File> updateFile(){
		return null;
	}
	
	/**
	 * 管理页面-修改文件
	 */
	@RequestMapping(value="delete_file.do")
	public ServerResponse<String> deleteFile(){
		return null;
	}
}
