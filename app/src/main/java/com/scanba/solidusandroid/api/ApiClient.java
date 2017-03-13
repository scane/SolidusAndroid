package com.scanba.solidusandroid.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String API_BASE_URL = "YOUR API BASE URL";
    public static final String BASE_URL = "YOUR BASE URL";
    public static final String API_KEY = "YOUR API KEY";

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(API_BASE_URL).build();
        }
        return retrofit;
    }
}
