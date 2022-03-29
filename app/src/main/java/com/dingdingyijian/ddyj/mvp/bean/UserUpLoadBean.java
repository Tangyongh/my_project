package com.dingdingyijian.ddyj.mvp.bean;

/**
 * @author: DDYiJian
 * @time: 2022/3/29
 * @describe: 用户上传头像
 */
public class UserUpLoadBean {


    /**
     * fileId : 561503660837183488
     * fileUrl : ddyjnew/picture/20220329/1648522510453.jpeg
     * original : IMG_CMP_77747_20002829.jpeg
     * saveName : 1648522510453.jpeg
     * size : 118080
     * state : SUCCESS
     * title : IMG_CMP_77747_20002829.jpeg
     * type : .jpeg
     * url : https://ddyj.oss-cn-shenzhen.aliyuncs.com/ddyjnew/picture/20220329/1648522510453.jpeg
     */

    private String fileId;
    private String fileUrl;
    private String original;
    private String saveName;
    private String size;
    private String state;
    private String title;
    private String type;
    private String url;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
