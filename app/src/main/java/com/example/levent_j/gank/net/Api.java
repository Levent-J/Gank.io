package com.example.levent_j.gank.net;

import android.util.Log;

import com.example.levent_j.gank.bean.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by levent_j on 16-2-27.
 */
public class Api {
    //http://gank.io/api/data/数据类型/请求个数/第几页
    private static final String URL = "http://gank.io/api/";

    private static volatile Api instance;

    public final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

    private final ApiService apiService;

    public Api() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static Api getInstance(){
        if (null==instance){
            synchronized (Api.class){
                if (null == instance){
                    instance = new Api();
                }
            }
        }
        return instance;
    }

    public void getDates(String type,String counts,String page,Observer<Result> observer){
        apiService.getDates(type,counts,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public interface ApiService{
        @GET("data/{type}/{counts}/{page}")
        Observable<Result> getDates(@Path("type") String type,
                                    @Path("counts") String counts,
                                    @Path("page") String page);
    }
}
