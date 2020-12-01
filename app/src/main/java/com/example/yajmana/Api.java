package com.example.yajmana;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Retrofit retrofit = null;
    public static ApiInterface getClient(Context mContext) {
        if (retrofit==null) {
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().addInterceptor(new ConnectivityInterceptor(mContext));
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.eastro.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build();
        }
        //Creating object for our interface
        ApiInterface api = retrofit.create(ApiInterface.class);
        return api; // return the APIInterface object
    }
}

/*
public static ApiInterface getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.eastro.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //Creating object for our interface
        ApiInterface api = retrofit.create(ApiInterface.class);
        return api; // return the APIInterface object
}
 */