package com.OoJou.service;

import org.springframework.web.multipart.MultipartFile;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.SysFile;
import com.github.pagehelper.PageInfo;


public interface ISysFileService {

	//file的比较麻烦，自创了File类，会有冲突，最后做，做的时候改下名
	//upload-关键方法，存放到指定路径的文件夹中
//	String uploadFile(MultipartFile file, String path);//旧方法，上传到ftp。现在不需要该功能
	ServerResponse<SysFile> uploadFile(MultipartFile file, String path,SysFile sysFile);
	ServerResponse<PageInfo> getAllFile(int pageNum,int pageSize);
	ServerResponse<String> downloadFile(int fileId);
//	ServerResponse<SysFile> updateFile(MultipartFile file, String path,SysFile sysFile);
	ServerResponse<String> deleteFile(int fileId);
	
	ServerResponse<SysFile> getFileDetails(int fileId);
	
}
