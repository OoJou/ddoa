package com.OoJou.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.OoJou.service.impl.SysFileServiceImpl;

public class LocationUploadUtil {
	
//	@Value("${img.location}")
//	private static String imgp="H:\\ftpfile\\img\\";
	
	@Value("${file.location}")
	private static String filep= "H:\\ftpfile\\file\\";
	
	private static Logger logger = LoggerFactory.getLogger(LocationUploadUtil.class);
	
	// 单文件上传
	public static Boolean uploadFile(MultipartFile file,String uploadFileName) {
		logger.info("filep:{}",filep);
		BufferedOutputStream stream = null;
		File fileDir = new File(filep);
		if (!fileDir.exists()) {
			fileDir.setWritable(true);
			fileDir.mkdirs();
		}
		try {
			byte[] bytes = file.getBytes();
            stream = new BufferedOutputStream(new FileOutputStream(
                    new File(filep + uploadFileName)));//设置文件路径及名字
            stream.write(bytes);// 写入
            stream.close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return true;
	}
	
	
	//多文件上传
	public static String handleFileUpload(List<File> fileList) {
	    File file = null;
	    BufferedOutputStream stream = null;
	    for (int i = 0; i < fileList.size(); ++i) {
	        file = fileList.get(i);
	        String filePath = filep;
	        if (!((MultipartFile) file).isEmpty()) {
	            try {
	                byte[] bytes = ((MultipartFile) file).getBytes();
	                stream = new BufferedOutputStream(new FileOutputStream(
	                        new File(filePath + ((MultipartFile) file).getOriginalFilename())));//设置文件路径及名字
	                stream.write(bytes);// 写入
	                stream.close();
	            } catch (Exception e) {
	                stream = null;
	                return "第 " + i + " 个文件上传失败  ==> "
	                        + e.getMessage();
	            }
	        } else {
	            return "第 " + i
	                    + " 个文件上传失败因为文件为空";
	        }
	    }
	    return "上传成功";
	}
	
	//文件下载相关代码,可以考虑吧request传入
	public static String downloadFile(HttpServletRequest request
			, HttpServletResponse response
			,String fileSource
			,String fileName) {
	    fileName = "aim_test.txt";// 设置文件名，根据业务需要替换成要下载的文件名
	    if (fileName != null) {
	        //设置文件路径
	        String realPath = fileSource;//格式"D://aim//"
	        File file = new File(realPath , fileName);
	        if (file.exists()) {
	            response.setContentType("application/force-download");// 设置强制下载不打开
	            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
	            byte[] buffer = new byte[1024];
	            FileInputStream fis = null;
	            BufferedInputStream bis = null;
	            try {
	                fis = new FileInputStream(file);
	                bis = new BufferedInputStream(fis);
	                OutputStream os = response.getOutputStream();
	                int i = bis.read(buffer);
	                while (i != -1) {
	                    os.write(buffer, 0, i);
	                    i = bis.read(buffer);
	                }
	                return "下载成功";
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                if (bis != null) {
	                    try {
	                        bis.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (fis != null) {
	                    try {
	                        fis.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	    }
	    return "下载失败";
	}
}
