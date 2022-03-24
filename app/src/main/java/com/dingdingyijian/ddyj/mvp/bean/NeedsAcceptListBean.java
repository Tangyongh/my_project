package com.dingdingyijian.ddyj.mvp.bean;

public class NeedsAcceptListBean {

    private String realName;
    private String createTime;
    private String avatarUrl;
    private String mobile;
    private String subTags;
    private String categoryName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSubTags() {
        return subTags;
    }

    public void setSubTags(String subTags) {
        this.subTags = subTags;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "NeedsAcceptListBean{" +
                "realName='" + realName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", mobile='" + mobile + '\'' +
                ", subTags='" + subTags + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
