package com.dingdingyijian.ddyj.mvp.bean;

import com.jeremyliao.liveeventbus.core.LiveEvent;

import java.util.List;

/**
 * @author: DDYiJian
 * @time: 2022/3/10
 * @describe: com.dingdingyijian.ddyj.bean
 */
public class BannerBean  implements LiveEvent {


    /**
     * startRow : 1
     * navigatepageNums : [1]
     * lastPage : 1
     * prePage : 0
     * hasNextPage : false
     * nextPage : 0
     * pageSize : 15
     * endRow : 3
     * list : [{"code":"220211100227502714","imageUrl":"https://ddyj.oss-cn-shenzhen.aliyuncs.com/ddyjnew/picture/20220211/1644545899682.png","description":"","id":"544824552623448064","title":"虎年开工红包","type":4,"targetUrl":"","target":"sectionRedpack"},{"code":"320501698689245184","imageUrl":"https://ddyj.oss-cn-shenzhen.aliyuncs.com/ddyjnew/picture/20200615/1592201380375.png","description":null,"id":"320501698689245184","title":"招代理商","type":4,"targetUrl":"213608550520856576","target":"news"},{"code":"207476018737848320","imageUrl":"https://ddyj.oss-cn-shenzhen.aliyuncs.com/ddyjnew/picture/20190726/1564115741411.png","description":null,"id":"207476018737848320","title":"常见问题","type":4,"targetUrl":"207463613072941056","target":"news"}]
     * pageNum : 1
     * navigatePages : 8
     * total : 3
     * navigateFirstPage : 1
     * pages : 1
     * size : 3
     * firstPage : 1
     * isLastPage : true
     * hasPreviousPage : false
     * navigateLastPage : 1
     * isFirstPage : true
     */

    private String startRow;
    private String lastPage;
    private String prePage;
    private boolean hasNextPage;
    private String nextPage;
    private String pageSize;
    private String endRow;
    private String pageNum;
    private String navigatePages;
    private String total;
    private String navigateFirstPage;
    private String pages;
    private String size;
    private String firstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private String navigateLastPage;
    private boolean isFirstPage;
    private List<Integer> navigatepageNums;
    private List<ListBean> list;
    //以下两个请求参数
    private String type;
    private String cityId;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public String getPrePage() {
        return prePage;
    }

    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getEndRow() {
        return endRow;
    }

    public void setEndRow(String endRow) {
        this.endRow = endRow;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(String navigatePages) {
        this.navigatePages = navigatePages;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(String navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public String getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(String navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * code : 220211100227502714
         * imageUrl : https://ddyj.oss-cn-shenzhen.aliyuncs.com/ddyjnew/picture/20220211/1644545899682.png
         * description :
         * id : 544824552623448064
         * title : 虎年开工红包
         * type : 4
         * targetUrl :
         * target : sectionRedpack
         */

        private String code;
        private String imageUrl;
        private String description;
        private String id;
        private String title;
        private String type;
        private String targetUrl;
        private String target;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }
}
