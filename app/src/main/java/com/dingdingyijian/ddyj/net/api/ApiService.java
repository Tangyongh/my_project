package com.dingdingyijian.ddyj.net.api;

import com.dingdingyijian.ddyj.base.BaseResponse;
import com.dingdingyijian.ddyj.mvp.bean.BannerBean;
import com.dingdingyijian.ddyj.mvp.bean.HtmlContentBean;
import com.dingdingyijian.ddyj.mvp.bean.LoginBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsAcceptListBean;
import com.dingdingyijian.ddyj.mvp.bean.NeedsCountBean;
import com.dingdingyijian.ddyj.mvp.bean.NoticeNoReadBean;
import com.dingdingyijian.ddyj.mvp.bean.UserCenterInfoBean;
import com.dingdingyijian.ddyj.mvp.bean.UserIconBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: tyh
 * @date: 2022/3/20 22:29
 * @description: api接口
 */
public interface ApiService {

    //注册
    @POST(ApiUrl.REGISTER_URL)
    Observable<BaseResponse<String>> registered(@Body RequestBody requestBody);

    //获取验证码
    @GET(ApiUrl.SEND_CODE_URL)
    Observable<BaseResponse<String>> userSendCode(@Query("mobile") String mobile, @Query("type") String type);

    //登录
    @POST(ApiUrl.LOGIN_URL)
    Observable<BaseResponse<LoginBean>> login(@Body LoginBean loginBean);

    //退出登录
    @GET(ApiUrl.USER_LOGOUT_URL)
    Observable<BaseResponse<String>> logOut();

    //设置推送别名
    @POST(ApiUrl.SET_ALIAS_URL)
    Observable<BaseResponse<String>> setAlias(@Body RequestBody requestBody);

    //忘记密码
    @POST(ApiUrl.SET_PWD_URL)
    Observable<BaseResponse<String>> forgetPwd(@Body RequestBody requestBody);

    //首页banner
    @POST(ApiUrl.BANNER_URL)
    Observable<BaseResponse<BannerBean>> banner(@Body BannerBean bannerBean);

    //首页接单滚动信息
    @POST(ApiUrl.HOME_ACCEPT_LIST_URL)
    Observable<BaseResponse<List<NeedsAcceptListBean>>> homeNeedsNotice(@Body NeedsAcceptListBean acceptListBean);

    //首页待处理多少单提示信息
    @GET(ApiUrl.HOME_NEEDS_COUNT_URL)
    Observable<BaseResponse<NeedsCountBean>> needsCount(@Query("uid") String uid);

    //富文本内容
    @GET(ApiUrl.HTML_CONTENT_URL)
    Observable<BaseResponse<HtmlContentBean>> webHtmlContent(@Query("code") String code);

    //个人中心未读消息
    @GET(ApiUrl.NOTICE_NO_READ_URL)
    Observable<BaseResponse<NoticeNoReadBean>> noticeNoRead();

    //个人中心信息
    @GET(ApiUrl.USER_CENTER_URL)
    Observable<BaseResponse<UserCenterInfoBean>> userCenterInfo(@Query("uid") String uid);

    //推荐分享
    @GET(ApiUrl.USER_SHARE_URL)
    Observable<BaseResponse<String>> userCenterShare();

    //获取用户有没有评价的需求单
    @POST(ApiUrl.USER_COMMENT_NEEDS_URL)
    Observable<BaseResponse<String>> userCommentNeeds();

    //主页广告弹窗
    @POST(ApiUrl.MAIN_AD_POPUP_URL)
    Observable<BaseResponse<String>> mainAdPopup(@Body RequestBody requestBody);

    //首页地图上的头像
    @POST(ApiUrl.USER_HOME_MAP_ICON_URL)
    Observable<BaseResponse<List<UserIconBean>>> userHomeMapViewIcon(@Body UserIconBean UserIconBean);

    //微信授权登录验证是否绑定手机号
    @POST(ApiUrl.USER_WX_BIND_URL)
    Observable<BaseResponse<String>> userWeiXinBind(@Body RequestBody requestBody);
}
