package com.OoJou.service.impl;

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
import com.OoJou.dao.FileMapper;
import com.OoJou.service.IFileService;
import com.OoJou.utils.FTPUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

@Service("iFileService")
public class FileServiceImpl implements IFileService {
	
	@Autowired
	private FileMapper fileMapper;
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    
	public String uploadFile(MultipartFile file, String path) {
		 String fileName = file.getOriginalFilename();
	        //扩展名
	        //abc.jpg
	        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
	        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
	        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

	        File fileDir = new File(path);
	        if(!fileDir.exists()){
	            fileDir.setWritable(true);
	            fileDir.mkdirs();
	        }
	        File targetFile = new File(path,uploadFileName);


	        try {
	            file.transferTo(targetFile);
	            //文件已经上传成功了


	            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
	            //已经上传到ftp服务器上

	            targetFile.delete();
	        } catch (IOException e) {
	            logger.error("上传文件异常",e);
	            return null;
	        }
	        //A:abc.jpg
	        //B:abc.jpg
	        return targetFile.getName();
	}

	@Override
	public ServerResponse<PageInfo> getAllFile(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<com.OoJou.pojo.File> fileList=fileMapper.selectAllFile();
		PageInfo pageResult=new PageInfo(fileList);
		pageResult.setList(fileList);
		return ServerResponse.createBySuccess("查询成功", pageResult);
	}

	@Override
	public ServerResponse<com.OoJou.pojo.File> downloadFile(int noticeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerResponse<com.OoJou.pojo.File> updateFile(MultipartFile file, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerResponse<com.OoJou.pojo.File> deleteFile(int noticeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerResponse<com.OoJou.pojo.File> getFileDetails(int fileId) {
		com.OoJou.pojo.File file=fileMapper.selectByPrimaryKey(fileId);
		if (file==null) {
			ServerResponse.createByErrorMsg("无此文件");
		}
		return ServerResponse.createBySuccess("获取文件信息成功", file);
	}

}
