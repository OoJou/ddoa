package com.OoJou.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.SysFile;
import com.github.pagehelper.PageInfo;


public interface ISysFileService {

	//upload-关键方法，存放到指定路径的文件夹中
//	String uploadFile(MultipartFile file, String path);//旧方法，上传到ftp。现在不需要该功能
//	ServerResponse<SysFile> updateFile(MultipartFile file, String path,SysFile sysFile);
	ServerResponse<SysFile> uploadFile(MultipartFile file, String path,SysFile sysFile);
	ServerResponse<SysFile> downloadFile(HttpServletRequest request
			,HttpServletResponse response
			,String downloadPath
			,int fileId);
	ServerResponse<String> deleteFile(int fileId);
	
	ServerResponse<SysFile> getFileDetails(int fileId);
	ServerResponse<PageInfo> getAllFile(int pageNum, int pageSize, String sortType, SysFile sysFile);
	
}
