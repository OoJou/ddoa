package com.OoJou.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.OoJou.common.Const;
import com.OoJou.common.ResponseCode;
import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.SysFile;
import com.OoJou.pojo.UploadImageResModel;
import com.OoJou.pojo.User;
import com.OoJou.service.ISysFileService;
import com.OoJou.service.IUserService;
import com.OoJou.service.impl.SysFileServiceImpl;
import com.OoJou.utils.PropertiesUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

@RestController
@RequestMapping("/file")
public class SysFileController {

	@Autowired
	private IUserService iUserService;

	@Autowired
	private ISysFileService iSysFileService;

	/**
	 * 管理页面-获取全部文件列表
	 */
	@RequestMapping(value = "get_all_file.do")
	public ServerResponse<PageInfo> getAllFile(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "DESC") String sortType, 
			SysFile sysFile,
			HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取公告详情");
		}
		return iSysFileService.getAllFile(pageNum, pageSize, sortType, sysFile);
	}

	/**
	 * 获取文件信息详情
	 */
	@RequestMapping(value = "get_file_details.do")
	public ServerResponse<SysFile> getFileDetails(int fileId, HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取公告详情");
		}
		return iSysFileService.getFileDetails(fileId);
	}

	// /**
	// * 管理页面-上传文件(上传到ftp)
	// */
	// @RequestMapping("upload_file.do")
	// public ServerResponse uploadFile(HttpSession session
	// ,@RequestParam(value = "uploadParam",required = false) MultipartFile file){
	// User user = (User)session.getAttribute(Const.CURRENT_USER);
	// if(user == null){
	// return ServerResponse.createByErrorMsg("用户未登录");
	// }
	// String path = session.getServletContext().getRealPath("upload");
	// String targetFileName = iSysFileService.uploadFile(file,path);
	// String url =
	// PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
	//
	// Map fileMap = Maps.newHashMap();
	// fileMap.put("uri",targetFileName);
	// fileMap.put("url",url);
	// return ServerResponse.createBySuccess(fileMap);
	// }

	/**
	 * 管理页面-上传文件
	 */
	@RequestMapping(value = "upload_file.do")
	public ServerResponse<SysFile> uploadFile(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		if (file == null) {
			return ServerResponse.createByErrorMsg("上传文件失败，传参错误");
		}
		SysFile sysFile = new SysFile();
		sysFile.setFileUploadUser(user.getUserName());// 设置当前用户为上传者
		String path = session.getServletContext().getRealPath("upload");

		return iSysFileService.uploadFile(file, path, sysFile);
	}

	/**
	 * 管理页面-下载文件
	 */
	@RequestMapping(value = "download_file.do")
	public ServerResponse<SysFile> downloadFile(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, String downloadPath, int fileId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		iSysFileService.downloadFile(request, response, downloadPath, fileId);// 下载
		return null;
		// 下载只需要返回成功
	}

	/**
	 * 管理页面-修改文件
	 */
	@RequestMapping(value = "delete_file.do")
	public ServerResponse<String> deleteFile(HttpSession session, int fileId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		if (!iUserService.checkAdminRole(user).isSuccess()) {
			return ServerResponse.createByErrorMsg("无权限操作");
		}
		return iSysFileService.deleteFile(fileId);
	}
	
	/**
	 * img标签显示本地磁盘的图片，读取流
	 * 直接使用src=file:\\\H:ftpfile\file\xxx.jpg是不能显示，浏览器不支持直接读取磁盘的图片
	 * 解决方法1：在项目中的resource包下创建一个存放静态资源的文件，这样可根据根目录进行读取，src=../static/upload/xx.jpg
	 * 解决方法2：读写流，然后通过response返回到浏览器。src=http://localhost:8080/file/show_img.do?url=h:/ftpfile/file/xx.jpg
	 */
	@RequestMapping(value = "show_img.do")
	public ServerResponse<String> showImg(HttpSession session,HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam(value = "url")String url) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorMsg("用户未登录");
		}
		iSysFileService.showImg(request,response,url);
		return null;
	}
	
	/**
	 * 富文本框上传图片专用方法，pojo+config+controller
	 */
	private Logger logger = LoggerFactory.getLogger(SysFileController.class);
	@Value("${img.location}")
	String locationPath;
	@RequestMapping("upload_image.do")
	public UploadImageResModel uploadImage(@RequestParam("upload") MultipartFile multipartFile) {
		UploadImageResModel res = new UploadImageResModel();
		res.setUploaded(0);

		if (multipartFile == null || multipartFile.isEmpty())
			return res;

		// 生成新的文件名及存储位置
		String fileName = multipartFile.getOriginalFilename();
		String newFileName = UUID.randomUUID().toString().replaceAll("-", "")
				.concat(fileName.substring(fileName.lastIndexOf(".")));

		String fullPath = locationPath.concat(newFileName);

		try {
			File target = new File(fullPath);
			if (!target.getParentFile().exists()) { // 判断文件父目录是否存在
				target.getParentFile().mkdirs();
			}

			multipartFile.transferTo(target);

			String imgUrl = "/img/".concat(newFileName);

			res.setUploaded(1);
			res.setFileName(fileName);
			res.setUrl(imgUrl);
			return res;
		} catch (IOException ex) {
			logger.error("上传图片异常", ex);
		}

		return res;
	}
}
