package com.example.chi6rag.mykart;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MyKartApplication extends Application {
    public static final long HTTP_TIMEOUT = 10 * 1000;
    public static final long CACHE_SIZE = 1024 * 1024 * 10l;  // 10 MB HTTP Cache
    private MyKartService myKartService;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(logging);
        okHttpClient.setConnectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);

        File cacheDir = new File(getCacheDir(), "http-cache");
        Cache cache = new Cache(cacheDir, CACHE_SIZE);
        okHttpClient.setCache(cache);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        myKartService = retrofit.create(MyKartService.class);
    }

    public MyKartService getMyKartService() {
        return myKartService;
    }
}
