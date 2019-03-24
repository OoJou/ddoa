package com.OoJou.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	@Value("${file.location}")//@value注入properties里面配置的值。这里是本地路径
	String locationPath;
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
	public ServerResponse<PageInfo> getAllFile(int pageNum, int pageSize,String sortType, SysFile sysFile) {
		PageHelper.startPage(pageNum, pageSize);
		List<SysFile> fileList= null;
		
		// 模糊查询
		if(!sortType.equals("DESC")&& !sortType.equals("ASC")) {
			fileList=sysFileMapper.selectAllFile();
//			return ServerResponse.createByErrorMsg("无此排序");
		}
		if (sortType.equals("DESC")) {
			fileList=sysFileMapper.selectAllFileByDESC(sysFile);
		}else {
			fileList=sysFileMapper.selectAllFileByASC(sysFile);
		}
		
		PageInfo pageResult = new PageInfo(fileList);
		pageResult.setList(fileList);
		return ServerResponse.createBySuccess("查询成功", pageResult);
	}

	@Override
	public ServerResponse<SysFile> uploadFile(MultipartFile file, String path,SysFile sysFile) {
		String fileName = file.getOriginalFilename();
		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
		logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);
		
		File fileDir = new File(path);//查看Tomcat服务器中是否存在当前目录
		if (!fileDir.exists()) {
			fileDir.setWritable(true);
			fileDir.mkdirs();
		}
		File tomcatFile = new File(path, uploadFileName);
		try {
			LocationUploadUtil.setFilep(locationPath);
			LocationUploadUtil.uploadFile(file,uploadFileName);//存储到本地
			
			file.transferTo(tomcatFile);//存储到Tomcat，此时file文件已变样，不能重复使用

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
	public ServerResponse<SysFile> downloadFile(HttpServletRequest request
			,HttpServletResponse response
			,String downloadath
			,int fileId) {//前端点击确认下载，只传入id。根据id查出文件名和路径，传入工具类开始下载
		SysFile file=sysFileMapper.selectByPrimaryKey(fileId);
		if(file==null) {
			return ServerResponse.createByErrorMsg("无此文件");
		}
		String fileSource=file.getFileSource();
		//存在数据库的fileName是未经uuid处理的，所以要在路径中截取
		String uuidName=fileSource.substring(fileSource.lastIndexOf("/")+1);
		//存取的路径是包括uuidName的，要截取
		String cutFileSource=fileSource.substring(0, fileSource.lastIndexOf("/")+1);
		logger.info("开始下载文件,下载文件的文件名:{},下载的路径:{}", uuidName, cutFileSource);
		
		Boolean isSuccess = LocationUploadUtil.downloadFile(request, response, cutFileSource, uuidName);
		if (!isSuccess) {
			return ServerResponse.createBySuccess("获取下载数据成功，下载失败", file);
		}
		return ServerResponse.createBySuccess("获取下载数据成功", file);
	}


	@Override
	public ServerResponse<String> deleteFile(int fileId) {
		//保存路径信息，作为实际删除的文件路径
		SysFile sysFile=sysFileMapper.selectByPrimaryKey(fileId);
		if(sysFile==null) {
			return ServerResponse.createByErrorMsg("删除失败,无此文件!");
		}
		String fileSource=sysFile.getFileSource();
		//删除数据库路径信息
		int resultCount=sysFileMapper.deleteByPrimaryKey(fileId);
		if(resultCount==0) {
			return ServerResponse.createByErrorMsg("删除失败");
		}		
		//实际删除
		File file=new File(fileSource);
		if(!file.exists()) {
			return ServerResponse.createByErrorMsg("删除失败,无此文件!");
		}
		else if(file.isDirectory()) {
			if (!deleteDirectory(fileSource)) {//判断是否为目录。这里是为了后面考虑，当前其实只能上传单个文件，不能上传文件夹
				return ServerResponse.createByErrorMsg("删除失败");
			}
		}
		else if (file.isFile()) {
			if (!deleteFile(fileSource)) {
				return ServerResponse.createByErrorMsg("删除失败");
			}
		}
		
		return ServerResponse.createBySuccessMsg("删除成功");
	}
	
	public boolean deleteFile(String sPath) {  
	      boolean flag = false;  
	      File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	     if (file.isFile() && file.exists()) {  
	         file.delete();  
	         flag = true;  
	    }  
	    return flag;  
	 }  

	public boolean deleteDirectory(String sPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}
	
	@Override
	public ServerResponse<SysFile> getFileDetails(int fileId) {
		SysFile file = sysFileMapper.selectByPrimaryKey(fileId);
		if (file == null) {
			ServerResponse.createByErrorMsg("无此文件");
		}
		return ServerResponse.createBySuccess("获取文件信息成功", file);
	}



}
