package com.project.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.project.cookies.PersistentCookieStore;
import com.project.service.NetworkService;
import com.project.splash.model.ConnectResult;
import com.tsengvn.typekit.Typekit;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;    //retrofit2 안드로이드와 자바사이의 안전한 통신을 도와주는 HTTP클라이언트
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by hpj on 2017-06-01.
 */

public class GlobalApplication extends Application{
    private static String TAG = "GlobalApplication";
    private static volatile GlobalApplication instance = null;
    private static volatile Activity currentActivity = null;

    private static String baseUrl = "http://192.168.0.26:8080/";
    private NetworkService networkService;

    CookieManager cookieManager;
    PersistentCookieStore cookieStore;

    @Override
    public void onCreate() {
        super.onCreate();
        //멀티덱스 적용(64K 함수 이상 사용)
        MultiDex.install(this);

        //글씨체 적용
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "OTF_L.otf"))
                .addBold(Typekit.createFromAsset(this, "OTF_B.otf"))
                .addItalic(Typekit.createFromAsset(this, "OTF_B.otf"))
                .addCustom1(Typekit.createFromAsset(this, "OTF_R.otf"));


        cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);

        instance = this;

        try{
            this.buildService();
        }catch(Exception ex){

        }
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return super.getApplicationInfo();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "OTF_L.otf"))
                .addBold(Typekit.createFromAsset(this, "OTF_B.otf"))
                .addItalic(Typekit.createFromAsset(this, "OTF_B.otf"))
                .addCustom1(Typekit.createFromAsset(this, "OTF_R.otf"));
    }

    private void buildService(){
        OkHttpClient client =  new OkHttpClient().newBuilder()
                .connectTimeout(7676, TimeUnit.SECONDS)
                .writeTimeout(7676, TimeUnit.SECONDS)
                .readTimeout(7676, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(client)
                .build();

        networkService = retrofit.create(NetworkService.class);
    }

    public static GlobalApplication getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return instance;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    public static GlobalApplication getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
