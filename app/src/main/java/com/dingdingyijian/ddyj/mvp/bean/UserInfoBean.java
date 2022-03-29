package com.dingdingyijian.ddyj.mvp.bean;

import java.util.List;

/**
 * @author: DDYiJian
 * @time: 2022/3/29
 * @describe: com.dingdingyijian.ddyj.mvp.bean
 */
public class UserInfoBean {


    /**
     * creditUserModel : {"creditScore":51,"starScore":2.2}
     * ddUser : {"avatarUrl":"https://ddyj.oss-cn-shenzhen.aliyuncs.com/ddyjnew/picture/20220329/1648523262623.jpeg","idcardVerify":2,"nickName":"唐","sex":1,"mobile":"15016709483","idcardNo":"360726199510163913","lon":113.928646,"realName":"唐永","regShortAddress":"广东省深圳市南山区深云立交桥","lastShortAddress":"广东省深圳市","id":385205,"lastAddress":"广东省深圳市南山区中山园路1001号E3栋靠近TCL电子大厦","introduction":"专业安装水电监控","lat":22.569712,"age":"27"}
     * userCategoryInfo : {"goodAtCategory":null,"canCategory":"中面包车,卫浴安装,家具安装,小面包车,木工,水电工,泥瓦工,灯具安装"}
     * commentModel : {"goodRate":100,"totalNum":2,"noPromiseNum":0,"totalScore":5,"goodNum":2}
     * pledgeMoney : false
     * totalScore : null
     * servicePromises : []
     */

    private CreditUserModelBean creditUserModel;
    private DdUserBean ddUser;
    private UserCategoryInfoBean userCategoryInfo;
    private CommentModelBean commentModel;
    private boolean pledgeMoney;
    private String totalScore;
    private List<ServicePromisesBean> servicePromises;

    public CreditUserModelBean getCreditUserModel() {
        return creditUserModel;
    }

    public void setCreditUserModel(CreditUserModelBean creditUserModel) {
        this.creditUserModel = creditUserModel;
    }

    public DdUserBean getDdUser() {
        return ddUser;
    }

    public void setDdUser(DdUserBean ddUser) {
        this.ddUser = ddUser;
    }

    public UserCategoryInfoBean getUserCategoryInfo() {
        return userCategoryInfo;
    }

    public void setUserCategoryInfo(UserCategoryInfoBean userCategoryInfo) {
        this.userCategoryInfo = userCategoryInfo;
    }

    public CommentModelBean getCommentModel() {
        return commentModel;
    }

    public void setCommentModel(CommentModelBean commentModel) {
        this.commentModel = commentModel;
    }

    public boolean isPledgeMoney() {
        return pledgeMoney;
    }

    public void setPledgeMoney(boolean pledgeMoney) {
        this.pledgeMoney = pledgeMoney;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public List<ServicePromisesBean> getServicePromises() {
        return servicePromises;
    }

    public void setServicePromises(List<ServicePromisesBean> servicePromises) {
        this.servicePromises = servicePromises;
    }

    public static class CreditUserModelBean {
        /**
         * creditScore : 51
         * starScore : 2.2
         */

        private double creditScore;
        private double starScore;

        public double getCreditScore() {
            return creditScore;
        }

        public void setCreditScore(double creditScore) {
            this.creditScore = creditScore;
        }

        public double getStarScore() {
            return starScore;
        }

        public void setStarScore(double starScore) {
            this.starScore = starScore;
        }
    }

    public static class DdUserBean {
        /**
         * avatarUrl : https://ddyj.oss-cn-shenzhen.aliyuncs.com/ddyjnew/picture/20220329/1648523262623.jpeg
         * idcardVerify : 2
         * nickName : 唐
         * sex : 1
         * mobile : 15016709483
         * idcardNo : 360726199510163913
         * lon : 113.928646
         * realName : 唐永
         * regShortAddress : 广东省深圳市南山区深云立交桥
         * lastShortAddress : 广东省深圳市
         * id : 385205
         * lastAddress : 广东省深圳市南山区中山园路1001号E3栋靠近TCL电子大厦
         * introduction : 专业安装水电监控
         * lat : 22.569712
         * age : 27
         */

        private String avatarUrl;
        private String idcardVerify;
        private String nickName;
        private String sex;
        private String mobile;
        private String idcardNo;
        private double lon;
        private String realName;
        private String regShortAddress;
        private String lastShortAddress;
        private String id;
        private String lastAddress;
        private String introduction;
        private double lat;
        private String age;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getIdcardVerify() {
            return idcardVerify;
        }

        public void setIdcardVerify(String idcardVerify) {
            this.idcardVerify = idcardVerify;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIdcardNo() {
            return idcardNo;
        }

        public void setIdcardNo(String idcardNo) {
            this.idcardNo = idcardNo;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRegShortAddress() {
            return regShortAddress;
        }

        public void setRegShortAddress(String regShortAddress) {
            this.regShortAddress = regShortAddress;
        }

        public String getLastShortAddress() {
            return lastShortAddress;
        }

        public void setLastShortAddress(String lastShortAddress) {
            this.lastShortAddress = lastShortAddress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLastAddress() {
            return lastAddress;
        }

        public void setLastAddress(String lastAddress) {
            this.lastAddress = lastAddress;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

    public static class UserCategoryInfoBean {
        /**
         * goodAtCategory : null
         * canCategory : 中面包车,卫浴安装,家具安装,小面包车,木工,水电工,泥瓦工,灯具安装
         */

        private String goodAtCategory;
        private String canCategory;

        public String getGoodAtCategory() {
            return goodAtCategory;
        }

        public void setGoodAtCategory(String goodAtCategory) {
            this.goodAtCategory = goodAtCategory;
        }

        public String getCanCategory() {
            return canCategory;
        }

        public void setCanCategory(String canCategory) {
            this.canCategory = canCategory;
        }
    }
    public static class ServicePromisesBean {
        /**
         * uid : 221654753988845568
         * createTime : 2020-04-16 17:44:31
         * articleId : 303195191220117504
         * updateTime : 2020-04-16 17:44:31
         * id : 303586536069799936
         * article : {"code":null,"keywords":"价格优","statusStr":null,"description":"不乱要价，价格谈好后，不因其他理由乱加价","updateTime":"2020-04-16 10:50:36","sort":1,"title":"价格优","categoryName":null,"content":"<p>不乱要价，价格谈好后，不因其他理由乱加价<\/p>","url":"https://ddyj.oss-cn-shenzhen.aliyuncs.com/ddyjnew/picture/20200416/1587005435544.png","categoryIds":"[13, 303194673013858304]","coverId":null,"system":0,"createTime":"2020-04-15 15:49:27","viewNums":null,"id":"303195191220117504","categoryIdsArr":null,"categoryId":"303194673013858304","imageUrlArr":null,"status":1}
         */

        private String uid;
        private String createTime;
        private String articleId;
        private String updateTime;
        private String id;
        private ArticleBean article;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ArticleBean getArticle() {
            return article;
        }

        public void setArticle(ArticleBean article) {
            this.article = article;
        }

        public static class ArticleBean {
            /**
             * code : null
             * keywords : 价格优
             * statusStr : null
             * description : 不乱要价，价格谈好后，不因其他理由乱加价
             * updateTime : 2020-04-16 10:50:36
             * sort : 1
             * title : 价格优
             * categoryName : null
             * content : <p>不乱要价，价格谈好后，不因其他理由乱加价</p>
             * url : https://ddyj.oss-cn-shenzhen.aliyuncs.com/ddyjnew/picture/20200416/1587005435544.png
             * categoryIds : [13, 303194673013858304]
             * coverId : null
             * system : 0
             * createTime : 2020-04-15 15:49:27
             * viewNums : null
             * id : 303195191220117504
             * categoryIdsArr : null
             * categoryId : 303194673013858304
             * imageUrlArr : null
             * status : 1
             */

            private String code;
            private String keywords;
            private String statusStr;
            private String description;
            private String updateTime;
            private String sort;
            private String title;
            private String categoryName;
            private String content;
            private String url;
            private String categoryIds;
            private String coverId;
            private String system;
            private String createTime;
            private String viewNums;
            private String id;
            private Object categoryIdsArr;
            private String categoryId;
            private Object imageUrlArr;
            private String status;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getStatusStr() {
                return statusStr;
            }

            public void setStatusStr(String statusStr) {
                this.statusStr = statusStr;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCategoryIds() {
                return categoryIds;
            }

            public void setCategoryIds(String categoryIds) {
                this.categoryIds = categoryIds;
            }

            public String getCoverId() {
                return coverId;
            }

            public void setCoverId(String coverId) {
                this.coverId = coverId;
            }

            public String getSystem() {
                return system;
            }

            public void setSystem(String system) {
                this.system = system;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getViewNums() {
                return viewNums;
            }

            public void setViewNums(String viewNums) {
                this.viewNums = viewNums;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Object getCategoryIdsArr() {
                return categoryIdsArr;
            }

            public void setCategoryIdsArr(Object categoryIdsArr) {
                this.categoryIdsArr = categoryIdsArr;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public Object getImageUrlArr() {
                return imageUrlArr;
            }

            public void setImageUrlArr(Object imageUrlArr) {
                this.imageUrlArr = imageUrlArr;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }

    public static class CommentModelBean {
        /**
         * goodRate : 100
         * totalNum : 2
         * noPromiseNum : 0
         * totalScore : 5
         * goodNum : 2
         */

        private double goodRate;
        private String totalNum;
        private String noPromiseNum;
        private String totalScore;
        private String goodNum;

        public double getGoodRate() {
            return goodRate;
        }

        public void setGoodRate(double goodRate) {
            this.goodRate = goodRate;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public String getNoPromiseNum() {
            return noPromiseNum;
        }

        public void setNoPromiseNum(String noPromiseNum) {
            this.noPromiseNum = noPromiseNum;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(String totalScore) {
            this.totalScore = totalScore;
        }

        public String getGoodNum() {
            return goodNum;
        }

        public void setGoodNum(String goodNum) {
            this.goodNum = goodNum;
        }

    }

}
