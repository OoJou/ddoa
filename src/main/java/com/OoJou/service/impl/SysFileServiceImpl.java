package com.OoJou.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.OoJou.common.ServerResponse;
import com.OoJou.dao.SysFileMapper;
import com.OoJou.pojo.SysFile;
import com.OoJou.service.ISysFileService;
import com.OoJou.utils.FTPUtil;
import com.OoJou.utils.LocationUploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

@Service("iSysFileService")
public class SysFileServiceImpl implements ISysFileService {

	@Autowired
	private SysFileMapper sysFileMapper;
	private Logger logger = LoggerFactory.getLogger(SysFileServiceImpl.class);

//	public String uploadFile(MultipartFile file, String path) {
//		String fileName = file.getOriginalFilename();
//		// 扩展名
//		// abc.jpg
//		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
//		String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
//		logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);
//
//		File fileDir = new File(path);
//		if (!fileDir.exists()) {
//			fileDir.setWritable(true);
//			fileDir.mkdirs();
//		}
//		File targetFile = new File(path, uploadFileName);
//
//		try {
//			// 文件已经上传到Tomcat，此时file文件已变样，不能重复使用
//			file.transferTo(targetFile);
//			// 上传到ftp服务器上
//			FTPUtil.uploadFile(Lists.newArrayList(targetFile));
//			//把Tomcat里的文件删除，免得越积越大。之后试下能否访问Tomcat下的图片（服务器），当前发现img不能访问非项目的文件
//			targetFile.delete();
//		} catch (IOException e) {
//			logger.error("上传文件异常", e);
//			return null;
//		}
//		// A:abc.jpg
//		// B:abc.jpg
//		return targetFile.getName();
//	}
	
	@Override
	public ServerResponse<PageInfo> getAllFile(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<com.OoJou.pojo.SysFile> fileList = sysFileMapper.selectAllFile();
		PageInfo pageResult = new PageInfo(fileList);
		pageResult.setList(fileList);
		return ServerResponse.createBySuccess("查询成功", pageResult);
	}


	@Override
	public ServerResponse<SysFile> uploadFile(MultipartFile file, String path,SysFile sysFile) {
		String locationPath="h:/ftpfile/file/";
		String fileName = file.getOriginalFilename();
		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
		logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);
		
		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.setWritable(true);
			fileDir.mkdirs();
		}
		File tomcatFile = new File(path, uploadFileName);
		try {
			LocationUploadUtil.uploadFile(file,uploadFileName);//存储到本地
			
			file.transferTo(tomcatFile);//存储到Tomcat

			tomcatFile.delete();//每次删除Tomcat，防止爆满
		} catch (IOException e) {
			logger.error("上传文件异常", e);
			return null;
		}
		//设置sysfile存储信息
		sysFile.setFileName(fileName);
		sysFile.setFileSource(locationPath+uploadFileName);
		sysFileMapper.insert(sysFile);
		return ServerResponse.createBySuccess("上传成功",sysFile);
		
	}
	
	@Override
	public ServerResponse<String> downloadFile(int noticeId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ServerResponse<String> deleteFile(int noticeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerResponse<com.OoJou.pojo.SysFile> getFileDetails(int fileId) {
		com.OoJou.pojo.SysFile file = sysFileMapper.selectByPrimaryKey(fileId);
		if (file == null) {
			ServerResponse.createByErrorMsg("无此文件");
		}
		return ServerResponse.createBySuccess("获取文件信息成功", file);
	}

}
