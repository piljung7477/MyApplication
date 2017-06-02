package com.project.service;

import com.project.splash.model.ConnectResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hpj on 2017-06-01.
 * HTTP통신 관련 인터페이스
 */

public interface NetworkService {
    //연결확인
    @POST("/weom/servlet/servlet.PROJECTTEST")
    Call<ConnectResult> getConnection(@Query("test") String test);
}
