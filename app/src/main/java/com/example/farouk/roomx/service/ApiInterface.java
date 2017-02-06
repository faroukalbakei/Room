package com.example.farouk.roomx.service;

import com.example.farouk.roomx.model.LoginResponse;
import com.example.farouk.roomx.model.MoviesResponse;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.model.ResponsePlace;
import com.example.farouk.roomx.model.UserinfoLogin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dev Abir on 2/5/2017.
 */
public interface ApiInterface {
    @GET("shownewestrooms/")
    Call<ResponsePlace> getExploreList(@Query("token") String mtoken);

    @GET("authenticate")
    Call<LoginResponse> login(@Query("email") String email, @Query("password") String passwrod);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
