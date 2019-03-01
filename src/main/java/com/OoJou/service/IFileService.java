package com.OoJou.service;

import org.springframework.web.multipart.MultipartFile;

import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.File;
import com.github.pagehelper.PageInfo;


public interface IFileService {

	//file的比较麻烦，自创了File类，会有冲突，最后做，做的时候改下名
	//upload-关键方法，存放到指定路径的文件夹中
	String uploadFile(MultipartFile file, String path);
	ServerResponse<PageInfo> getAllFile(int pageNum,int pageSize);
	ServerResponse<File> downloadFile(int fileId);
	ServerResponse<File> updateFile(MultipartFile file, String path);
	ServerResponse<File> deleteFile(int fileId);
	
	ServerResponse<File> getFileDetails(int fileId);
	
}
