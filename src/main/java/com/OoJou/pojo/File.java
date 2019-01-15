package com.OoJou.pojo;

import java.util.Date;

public class File {
    private Integer fileId;

    private String fileName;

    private String fileSource;

    private String fileImage;

    private String fileUploadUser;

    private Date fileUploadTime;

    private Date createTime;

    private Date updateTime;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileSource() {
        return fileSource;
    }

    public void setFileSource(String fileSource) {
        this.fileSource = fileSource == null ? null : fileSource.trim();
    }

    public String getFileImage() {
        return fileImage;
    }

    public void setFileImage(String fileImage) {
        this.fileImage = fileImage == null ? null : fileImage.trim();
    }

    public String getFileUploadUser() {
        return fileUploadUser;
    }

    public void setFileUploadUser(String fileUploadUser) {
        this.fileUploadUser = fileUploadUser == null ? null : fileUploadUser.trim();
    }

    public Date getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(Date fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}