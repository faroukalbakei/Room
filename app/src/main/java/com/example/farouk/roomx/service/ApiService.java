package com.example.farouk.roomx.service;

import com.example.farouk.roomx.model.LoginResponse;
import com.example.farouk.roomx.model.ResponsePlace;
import com.example.farouk.roomx.model.Result;
import com.example.farouk.roomx.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Dev Abir on 2/5/2017.
 */
public interface ApiService {
/*    @GET("shownewestrooms/")
    Call<ResponsePlace> getExploreList(@Query("token") String mtoken);

    @GET("authenticate")
    Call<LoginResponse> login(@Query("email") String email, @Query("password") String passwrod);*/

    @Multipart
    @POST("updateuserprofilepicture")
    Call<Result> uploadImage(@Part("token") RequestBody mtoken, @Part MultipartBody.Part file);
}
