package com.example.sgram.data.remote.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.sgram.data.remote.interceptor.TokenInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {
    private final Retrofit retrofit;
    private final String BASE_URL = "http://172.20.10.5:8080";

    private final Context context;

    public ApiProvider(Context context) {
        this.context = context;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public OkHttpClient getOkHttpClient() {
        if (context == null) {
            Log.d("TEST","context is null");
            //throw new IllegalStateException("");
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("sgram", Context.MODE_PRIVATE);
        int t = sharedPreferences.getInt("sgram",0);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(sharedPreferences))
                .build();
        return okHttpClient;
    }

    public AuthApi getAuthApi() {
        return getRetrofit().create(AuthApi.class);
    }

    public ChatApi getChatApi() {
        return getRetrofit().create(ChatApi.class);
    }
}



