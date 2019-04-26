package com.OoJou.pojo;

public class UploadImageResModel {
    /**
     * 1成功，0失败
     */
    private Integer uploaded;

    private String fileName;

    private String url;

    public Integer getUploaded() {
        return uploaded;
    }

    public void setUploaded(Integer uploaded) {
        this.uploaded = uploaded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}