package com.dev.farouk.roomx.service;

import com.dev.farouk.roomx.util.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Dev Abir on 2/5/2017.
 */
public class RetroClient {
    public static final String BASE_URL = "http://roomxapi.com/gaithapi/public/api/";
    //public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public RetroClient() {

    }

    /**
     * Get Retro Client
     *
     * @return JSON Object
     */
    private static Retrofit getRetroClient() {
        GsonBuilder gsonBuilder = new GsonBuilder();
       // gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return getRetroClient().create(ApiService.class);
    }

}
