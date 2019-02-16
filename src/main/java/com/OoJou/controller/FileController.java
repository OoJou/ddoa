package com.OoJou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.File;

@RestController
@RequestMapping("/file")
public class FileController {

	
	/**
	 * 管理页面-获取全部文件列表
	 */
	@RequestMapping(value="get_all_file.do")
	public ServerResponse<File> getAllFile(){
		return null;
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
