package com.OoJou.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.OoJou.common.Const;
import com.OoJou.common.ResponseCode;
import com.OoJou.common.ServerResponse;
import com.OoJou.pojo.SysFile;
import com.OoJou.pojo.User;
import com.OoJou.service.ISysFileService;
import com.OoJou.service.IUserService;
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
	@RequestMapping(value="get_all_file.do")
	public ServerResponse<PageInfo> getAllFile(
			@RequestParam(value="pageNum",defaultValue="1")int pageNum
			,@RequestParam(value="pageSize",defaultValue="5")int pageSize
			,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取公告详情");
		}
		return iSysFileService.getAllFile(pageNum, pageSize);
	}
	
	/**
	 * 获取文件信息详情
	 */
	@RequestMapping(value="get_file_details.do")
	public ServerResponse<SysFile> getFileDetails(int fileId,HttpSession session){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null) {
			return ServerResponse.createByErrorMsg("用户未登录，无法获取公告详情");
		}
		return iSysFileService.getFileDetails(fileId);
	}
	
//	/**
//	 * 管理页面-上传文件(上传到ftp)
//	 */
//    @RequestMapping("upload_file.do")
//    public ServerResponse uploadFile(HttpSession session
//    		,@RequestParam(value = "uploadParam",required = false) MultipartFile file){
//        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        if(user == null){
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//        String path = session.getServletContext().getRealPath("upload");
//        String targetFileName = iSysFileService.uploadFile(file,path);
//        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
//
//        Map fileMap = Maps.newHashMap();
//        fileMap.put("uri",targetFileName);
//        fileMap.put("url",url);
//        return ServerResponse.createBySuccess(fileMap);
//    }
    
	/**
	 * 管理页面-上传文件
	 */
	@RequestMapping(value="upload_file.do")
	public ServerResponse<SysFile> uploadFile(HttpSession session
    		,@RequestParam(value = "uploadParam",required = false) MultipartFile file){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        
		SysFile sysFile =new SysFile();
		sysFile.setFileUploadUser(user.getUserName());//设置当前用户为上传者
        String path = session.getServletContext().getRealPath("upload");
        
        return iSysFileService.uploadFile(file, path, sysFile);
	}
	
	/**
	 * 管理页面-下载文件
	 */
	@RequestMapping(value="download_file.do")
	public ServerResponse<SysFile> downloadFile(){
		return null;
	}
	
	/**
	 * 管理页面-修改文件
	 */
	@RequestMapping(value="delete_file.do")
	public ServerResponse<String> deleteFile(HttpSession session){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
		if(!iUserService.checkAdminRole(user).isSuccess()){
       	return ServerResponse.createByErrorMsg("无权限操作");
        }
		return null;
	}
}
