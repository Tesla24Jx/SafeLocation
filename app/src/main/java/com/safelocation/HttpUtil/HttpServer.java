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

    @FormUrlEncoded
    @POST("/safelocation/login.php")
    Observable<HttpRequest> getLogin_post(@Field("login") String login);

    @GET("/safelocation/register.php")
    Observable<HttpRequest> getRegData_post(@Query("register") String register);

    @GET("/safelocation/uploadhead.php")
    Observable<HttpRequest> getheadurl_get(@Query("headurl") String headurl);

    @FormUrlEncoded
    @POST("/safelocation/uploadhead.php")
    Observable<StrJson> getheadurl_post(@Field("headurl") String headurl);

    @FormUrlEncoded
    @POST("/safelocation/searchfriend.php")
    Observable<StrJson> getfriend_post(@Field("searchfriend") String searchfriend);

    @FormUrlEncoded
    @POST("/safelocation/submitCID.php")
    Observable<StrJson> submitCID_post(@Field("submitCID") String submitCID);

    @FormUrlEncoded
    @POST("/safelocation/addfriend.php")
    Observable<StrJson> addfriend_post(@Field("addfriend") String addfriend);

    @FormUrlEncoded
    @POST("/safelocation/add_agree.php")
    Observable<StrJson> add_f_agree_post(@Field("add_agree") String add_agree);


    @FormUrlEncoded
    @POST("/safelocation/login.php")
    Observable<String> getNewsData_post(@Field("login") String login);

}
