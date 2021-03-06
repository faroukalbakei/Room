package com.dev.farouk.roomx.service;

import com.dev.farouk.roomx.model.ErrorResponse;
import com.dev.farouk.roomx.model.ErrorResponse2;
import com.dev.farouk.roomx.model.LoginResponse;
import com.dev.farouk.roomx.model.User;
import com.dev.farouk.roomx.model.UserResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<String> uploadImage(@Part("token") RequestBody mtoken, @Part MultipartBody.Part file);

    @Multipart
    @POST("addphotostoroom")
    Call<ResponseBody> uploadRoomImages(@Part("token") RequestBody mtoken, @Part("room_id") RequestBody roomId, @Part List<MultipartBody.Part> images);
   // Call<ResponseBody> uploadRoomImages(@Part("token") RequestBody mtoken,@Part List<MultipartBody.Part> images);

   // @Multipart
    @POST("addphototoroom")
    Call<String> uploadRoomImages(@Body RequestBody data);

    @POST("authenticate")
    Call<LoginResponse> login(@Query("token") String mtoken,@Query("email") String email, @Query("password") String passwrod);
}
