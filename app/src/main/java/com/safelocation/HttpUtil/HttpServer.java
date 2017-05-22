package com.safelocation.HttpUtil;

import com.safelocation.Entity.HttpRequest;
import com.safelocation.Entity.StrJson;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Juliet on 2017/1/28.
 */

public interface HttpServer {

    //登录接口
    @FormUrlEncoded
    @POST("/safelocation/login.php")
    Observable<HttpRequest> getLogin_post(@Field("login") String login);

    //注册接口
    @GET("/safelocation/register.php")
    Observable<HttpRequest> getRegData_post(@Query("register") String register);

    //上传头像接口
    @FormUrlEncoded
    @POST("/safelocation/uploadhead.php")
    Observable<StrJson> getheadurl_post(@Field("headurl") String headurl);

    //查询手机号是否已注册
    @FormUrlEncoded
    @POST("/safelocation/checkaccount.php")
    Observable<StrJson> getcheckAccountResult_post(@Field("phone") String phone);

    //修改密码接口
    @FormUrlEncoded
    @POST("/safelocation/alertpwd.php")
    Observable<StrJson> getAlertPwdResult_post(@Field("alertpwd") String result);

    //查找好友接口
    @FormUrlEncoded
    @POST("/safelocation/searchfriend.php")
    Observable<StrJson> getfriend_post(@Field("searchfriend") String searchfriend);

    //提交个推cid接口
    @FormUrlEncoded
    @POST("/safelocation/submitCID.php")
    Observable<StrJson> submitCID_post(@Field("submitCID") String submitCID);

    //添加好友接口
    @FormUrlEncoded
    @POST("/safelocation/addfriend.php")
    Observable<StrJson> addfriend_post(@Field("addfriend") String addfriend);

    //好友同意添加接口
    @FormUrlEncoded
    @POST("/safelocation/add_agree.php")
    Observable<StrJson> add_f_agree_post(@Field("add_agree") String add_agree);

    //修改个人信息接口
    @FormUrlEncoded
    @POST("/safelocation/alertInfo.php")
    Observable<StrJson> alertInfo_post(@Field("alertInfo") String alertInfo);

    //删除好友接口
    @FormUrlEncoded
    @POST("/safelocation/delfriend.php")
    Observable<StrJson> delfriend_post(@Field("delfriend") String delfriend);

    //授权给好友接口
    @FormUrlEncoded
    @POST("/safelocation/alertpermission.php")
    Observable<StrJson> alertPermission_post(@Field("alertpermission") String alertpermission);


}
