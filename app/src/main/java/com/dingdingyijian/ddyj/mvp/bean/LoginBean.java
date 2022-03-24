package com.dingdingyijian.ddyj.mvp.bean;

public class LoginBean {


    private String rcToken;
    private String nickName;
    private String wxUnionid;
    private String mobile;
    private String loginToken;
    private String lon;
    private String id;
    private String userName;
    private UserExtMapDTO userExtMap;
    private String lat;
    //以下是传参字段
    private String lastAddress;
    private String lastAcArea;
    private String lastShortAddress;
    private String loginType;
    private String password;
    private String valiCode;
    private String terminal;
    private String regIdAlias;
    private String imei;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLastAddress() {
        return lastAddress;
    }

    public void setLastAddress(String lastAddress) {
        this.lastAddress = lastAddress;
    }

    public String getLastAcArea() {
        return lastAcArea;
    }

    public void setLastAcArea(String lastAcArea) {
        this.lastAcArea = lastAcArea;
    }

    public String getLastShortAddress() {
        return lastShortAddress;
    }

    public void setLastShortAddress(String lastShortAddress) {
        this.lastShortAddress = lastShortAddress;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getRegIdAlias() {
        return regIdAlias;
    }

    public void setRegIdAlias(String regIdAlias) {
        this.regIdAlias = regIdAlias;
    }

    public String getRcToken() {
        return rcToken;
    }

    public void setRcToken(String rcToken) {
        this.rcToken = rcToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getWxUnionid() {
        return wxUnionid;
    }

    public void setWxUnionid(String wxUnionid) {
        this.wxUnionid = wxUnionid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserExtMapDTO getUserExtMap() {
        return userExtMap;
    }

    public void setUserExtMap(UserExtMapDTO userExtMap) {
        this.userExtMap = userExtMap;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public static class UserExtMapDTO {
        private String gdRoleName;
        private String gdRoleValue;

        public String getGdRoleName() {
            return gdRoleName;
        }

        public void setGdRoleName(String gdRoleName) {
            this.gdRoleName = gdRoleName;
        }

        public String getGdRoleValue() {
            return gdRoleValue;
        }

        public void setGdRoleValue(String gdRoleValue) {
            this.gdRoleValue = gdRoleValue;
        }
    }
}
