package com.OoJou.pojo;

import java.util.Date;

public class Notice {
    private Integer noticeId;

    private String noticeTitle;

    private String noticePubilsher;

    private String noticeDetails;

    private String noticeImage;

    private Date createTime;

    private Date updateTime;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
    }

    public String getNoticePubilsher() {
        return noticePubilsher;
    }

    public void setNoticePubilsher(String noticePubilsher) {
        this.noticePubilsher = noticePubilsher == null ? null : noticePubilsher.trim();
    }

    public String getNoticeDetails() {
        return noticeDetails;
    }

    public void setNoticeDetails(String noticeDetails) {
        this.noticeDetails = noticeDetails == null ? null : noticeDetails.trim();
    }

    public String getNoticeImage() {
        return noticeImage;
    }

    public void setNoticeImage(String noticeImage) {
        this.noticeImage = noticeImage == null ? null : noticeImage.trim();
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