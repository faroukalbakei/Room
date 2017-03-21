package com.dev.farouk.roomx.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.dev.farouk.roomx.model.Error;
import com.dev.farouk.roomx.model.ErrorResponse;
import com.dev.farouk.roomx.model.ErrorResponse2;
import com.dev.farouk.roomx.model.LoginResponse;
import com.dev.farouk.roomx.model.Reservation;
import com.dev.farouk.roomx.model.ReservationResult;
import com.dev.farouk.roomx.model.UserResult;
import com.dev.farouk.roomx.model.RoomPhoto;
import com.dev.farouk.roomx.model.RoomReservation;
import com.dev.farouk.roomx.model.User;
import com.dev.farouk.roomx.util.Const;
import com.dev.farouk.roomx.app.Prefs;
import com.dev.farouk.roomx.app.VolleySingleton;
import com.dev.farouk.roomx.model.PlaceObject;
import com.dev.farouk.roomx.model.Response;
import com.dev.farouk.roomx.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

/**
 * Created by ababdullah on 30/01/2017.
 */

public class Requests {
    private final Gson gson;
    ProgressDialog pDialog;
    LoginResponse loginResponse;
    com.dev.farouk.roomx.model.Response responseObject;
    private String token;
    Context mContext;

    public Requests(Context context) {
        mContext = context;
        loginResponse = Prefs.with(context).getUser();
        if (loginResponse != null) {
            token = loginResponse.getToken();
        }

        responseObject = new com.dev.farouk.roomx.model.Response();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

    }

    public void makeLogin(final VolleyCallback callback, final Context context, final String email, final String password) {
        //  login2(callback, context, email, password);
        responseObject = new Response();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "authenticate?",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String responsee) {

                        Log.d("makeLogin response", responsee.toString());
                        pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        try {
                            JSONObject callNode = new JSONObject(responsee.toString());
                            loginResponse = new LoginResponse();
                            loginResponse.setToken(callNode.optString("token"));
                            loginResponse.setResult(callNode.optInt("result"));
                            responseObject.setResult(callNode.optInt("result"));
                            responseObject.setOnError(callNode.optString("error"));
                            responseObject.setObject(loginResponse);
                            Log.d("getResult1", String.valueOf(responseObject.getResult()));
                            if (responseObject != null)
                                callback.onSuccess(responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Prefs.with(context).setUser(loginResponse);
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;

                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 401:
                        case 400:
                            json = new String(response.data);
                            Log.d("error", json + "");
/*                            ErrorResponse errorResponses = gson.fromJson(json, ErrorResponse.class);
                            Msg msg =errorResponses.getMsg();
                            String errorMesg=msg.getErrorLogin();*/
                            json = trimMessage(json, "error");
                            if (json != null) callback.onSuccess(new Response(false, json));
                            break;
                    }
                    //Additional cases
                }
                callback.onSuccess(new Response(false, json));
                //pDialog.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
                //login_button.setEnabled(true);
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void makeRegister(final VolleyCallback callback, final Context context, final String name, final String email, final String password, final String passwordConfirm, final String phone) {
        Log.d("name", name);
        Log.d("email", email);
        Log.d("password", password);
        Log.d("passwordConfirm", passwordConfirm);
        Log.d("phone", phone);

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "Register?",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("makeRegister response", response.toString());
                        pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        responseObject = new Response();

                        try {
                            JSONObject callNode = new JSONObject(response.toString());
                            loginResponse = new LoginResponse();
                            loginResponse.setToken(callNode.optString("token"));
                            responseObject.setResult(callNode.optInt("result"));
                            responseObject.setMessage(callNode.optString("msg"));
                            callback.onSuccess(responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;

                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 401:
                        case 400:
                            json = new String(response.data);
                            Log.d("json", json + "");
                            ErrorResponse errorResponses = gson.fromJson(json, ErrorResponse.class);
                            Error msg = errorResponses.getMsg().getError();
                            String errorMesg = Utils.replaceNull(String.valueOf(msg.getName())) + "\n" +
                                    Utils.replaceNull(String.valueOf(msg.getEmail())) + "\n" +
                                    Utils.replaceNull(String.valueOf(msg.getPassword())) + "\n" +
                                    Utils.replaceNull(String.valueOf(msg.getPhone())) + "\n";
                            // json = trimMessage(json, "message");
                            if (json != null) callback.onSuccess(new Response(false, errorMesg));
                            Log.d("error", errorMesg + "");
                            break;
                    }
                    //Additional cases
                }

                pDialog.hide();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //login_button.setEnabled(true);
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("password_confirmation", passwordConfirm);
                params.put("phone", phone);
                return params;
            }
        };
        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);

    }

    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public void getUserProfile(final VolleyCallback callback, final Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "myprofile",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        responseObject = new Response();
//                        List<User> user = Arrays.asList(gson.fromJson(response, User[].class));
                        UserResult userResult = (gson.fromJson(response, UserResult.class));
                        Prefs.with(context).setUserInfo(userResult);
                        responseObject = new Response();
                        if (userResult != null) {
                            Log.d("user", userResult.getUser().toString());
                            responseObject.setObject(userResult.getUser());
                            callback.onSuccess(responseObject);
                        } else
                            Log.d("null ", "user");

                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                responseObject.setOnError(error.getMessage());
                pDialog.hide();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //login_button.setEnabled(true);
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);

    }

    public void getPlacesList(final VolleyCallback callback, final Context context, String apiMethod) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        Log.d("apiMethod", apiMethod);

        StringRequest req = new StringRequest(Request.Method.POST, Const.BASE_URL + apiMethod,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        responseObject = new Response();
                        JSONObject callNode = null;
                        String result = null;
                        try {
                            callNode = new JSONObject(response.toString());
                            result = callNode.optString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (result == "0") {
                            responseObject.setResult(Integer.parseInt(result));

                        } else {
                            List<PlaceObject> posts = Arrays.asList(gson.fromJson(response, PlaceObject[].class));

                            for (int i = 0; i < posts.size(); i++) {
                                PlaceObject.save(posts.get(i));
                                User user;
                                if (posts.get(i).getUser() != null) {
                                    user = posts.get(i).getUser();

                                } else {
                                    user = Prefs.with(context).getUserInfo().getUser();
                                }
                                user.setId(posts.get(i).getId());
                                User.save(user);
                                if (posts.get(i).getRoomPhoto() != null) {
                                    for (int rp = 0; rp < posts.get(i).getRoomPhoto().size(); rp++) {
                                        RoomPhoto roomPhoto = posts.get(i).getRoomPhoto().get(rp);
                                        roomPhoto.setPlaceId(posts.get(i).getId());
                                        RoomPhoto.save(roomPhoto);
                                    }
                                }
                                if (posts.get(i).getRoomReservation() != null) {

                                    for (int rr = 0; rr < posts.get(i).getRoomReservation().size(); rr++) {
                                        RoomReservation roomReservation = posts.get(i).getRoomReservation().get(rr);
                                        roomReservation.setPlaceId(posts.get(i).getId());
                                        RoomReservation.save(roomReservation);
                                    }
                                }
                            }

                            Log.i("PostActivity", posts.size() + " posts loaded.");
                            for (PlaceObject post : posts) {
                                Log.i("PostActivity", post.toString());
                                if (post.getUser() == null)
                                    post.setUser(Prefs.with(context).getUserInfo().getUser());
                            }

                            responseObject.setObject(posts);

                        }

                        callback.onSuccess(responseObject);
                        pDialog.hide();
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                responseObject.setOnError(error.getMessage());
                pDialog.hide();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //login_button.setEnabled(true);
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(req);

    }

    public void editProfile(final VolleyCallback callback, final Context context, final User user) {
        responseObject = new Response();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "updateuserprofile",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String responsee) {

                        Log.d("response", responsee.toString());
                        pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        try {
                            JSONObject callNode = new JSONObject(responsee.toString());
                            if (callNode.has("user")) {
                                Log.d("callNode", "true");
                                responseObject.setResult(1);
                                callback.onSuccess(responseObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                responseObject.setOnError(error.getMessage());
                //pDialog.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
                //login_button.setEnabled(true);
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                params.put("name", user.getName());
                params.put("city", user.getCity());
                params.put("country", user.getCountry());
                params.put("dob", user.getDob());
                params.put("email", user.getEmail());
                params.put("phone", user.getPhone());
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * Upload Image Client Code
     */
    public void uploadImage(final VolleyCallback callback, final Context context, final String imagePath) {
        Log.d("uploadImage", imagePath);

        /**
         * Progressbar to Display if you need
         */
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("upLoading...");
        pDialog.show();
        //Create Upload Server Client
        ApiService service = RetroClient.getApiService();

        //File creating from selected URL
        File file = new File(imagePath);
        RequestBody tokenBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), token);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photolink", file.getName(), requestFile);

        Call<String> resultCall = service.uploadImage(tokenBody, body);

        resultCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                pDialog.hide();
                Log.d("response", response.body().toString());

                // Response Success or Fail
/*                if (response.isSuccessful()) {
                    if (response.body().getUser() != null)
                        Log.d("uploadImage", "success");
                    Log.d("response", response.body().toString());

                    callback.onSuccess(new Response(true));

                } else {
                    callback.onSuccess(new Response(false));
                    Log.d("uploadImage", "fail");
                }*/

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("onFailure", t.getMessage().toString());
                pDialog.hide();
            }
        });
    }

    public void uploadRoomImages(final VolleyCallback callback, final Context context,int roomId, final List<String> imagePathList) {
        Log.d("uploadRoomImages", imagePathList.toString());

        /**
         * Progressbar to Display if you need
         */
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("upLoading...");
        pDialog.show();
        //Create Upload Server Client
        ApiService service = RetroClient.getApiService();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("token", token);
        builder.addFormDataPart("room_id", String.valueOf(roomId));

        for (String filePath : imagePathList) {
            File file = new File(filePath);
            builder.addFormDataPart("images", file.getName(),
                    RequestBody.create(MediaType.parse("image/*"), file));
        }
        MultipartBody requestBody = builder.build();

/*        //File creating from selected URL
        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);*/
/*
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photolink", file.getName(), requestFile);*/

        Call<String> resultCall = service.uploadRoomImages(requestBody);

        resultCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                pDialog.hide();
                Log.d("response", response.body().toString());

                // Response Success or Fail
/*                if (response.isSuccessful()) {
                    if (response.body().getUser() != null)
                        Log.d("uploadImage", "success");
                    Log.d("response", response.body().toString());

                    callback.onSuccess(new Response(true));

                } else {
                    callback.onSuccess(new Response(false));
                    Log.d("uploadImage", "fail");
                }*/

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("onFailure", t.getMessage().toString());
                pDialog.hide();
            }
        });
    }


    public void login2(final VolleyCallback callback, final Context context, final String email, final String passwrod) {
        Log.d("email", email);

        /**
         * Progressbar to Display if you need
         */
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("upLoading...");
        pDialog.show();
        //Create Upload Server Client
        ApiService service = RetroClient.getApiService();

        //File creating from selected URL
        RequestBody tokenBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), token);
        // create RequestBody instance from file


        Call<LoginResponse> resultCall = service.login(token, email, passwrod);

        resultCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {

                pDialog.hide();

                // Response Success or Fail
                if (response.isSuccessful()) {
                    if (response.body().getToken() != null)
                        Log.d("getToken", response.body().getToken());
                    callback.onSuccess(new Response(true));

                } else {
                    callback.onSuccess(new Response(false));
                    Log.d("getToken", "fail");
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage().toString());
                pDialog.hide();
            }
        });
    }

    public void getReservations(final VolleyCallback callback, final Context context, String apiMethod) {

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        Log.d("apiMethod", apiMethod);

        StringRequest req = new StringRequest(Request.Method.POST, Const.BASE_URL + apiMethod,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("getreservations", response.toString());
                        responseObject = new Response();
                     //   try {
                          // JSONObject callNode = new JSONObject(response.toString());
                            if (response.length()<10) {
                                responseObject.setValid(false);
                            } else {

                                List<ReservationResult> posts = Arrays.asList(gson.fromJson(response, ReservationResult.class));

                                Log.i("getuserreservations", posts.size() + " posts loaded.");
                                List<Reservation> reservationList = new ArrayList<>();
                                for (Reservation reservation : posts.get(0).getReservation()) {
                                    reservationList.add(reservation);
                                    Log.i("reservation", reservation.toString());
                                }
                                Log.i("reservationList", reservationList.toString());
                                responseObject.setObject(reservationList);
                            }

                            callback.onSuccess(responseObject);
                            pDialog.hide();
/*                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                        }

                    }

                    ,new com.android.volley.Response.ErrorListener()

                    {

                        @Override
                        public void onErrorResponse (VolleyError error){
                        responseObject.setOnError(error.getMessage());
                        pDialog.hide();
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        //login_button.setEnabled(true);
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    }

                    )

                    {

                        @Override
                        public Map<String, String> getHeaders ()throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Accept", "application/json");
                        return params;
                    }

                        @Override
                        protected Map<String, String> getParams ()throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("token", token);
                        return params;
                    }
                    }

                    ;

                    // Adding request to request queue
                    VolleySingleton.getInstance().

                    addToRequestQueue(req);
                }

    public void reserveRoom(final VolleyCallback callback, final Context context, final String roomId, final String startDate, final String endDate) {
        responseObject = new Response();
/*        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();*/
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "reservearoom",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String responsee) {

                        Log.d("response", responsee.toString());
                        //  pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        try {
                            JSONObject callNode = new JSONObject(responsee.toString());
                            responseObject.setResult(callNode.optInt("result"));
                            responseObject.setOnError(callNode.optString("error"));
                            responseObject.setMessage(callNode.optString("msj"));
                            Log.d("getResult", String.valueOf(responseObject.getResult()));
                            if (responseObject != null)
                                callback.onSuccess(responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 500:
                        case 404:
                        case 400:
                            json = new String(response.data);
                            Log.d("json", json + "");
                            ErrorResponse2 errorResponses = gson.fromJson(json, ErrorResponse2.class);
                            Error msg = errorResponses.getError();
                            String errorMesg = Utils.replaceNull(String.valueOf(msg.getRoomId())) + "\n" +
                                    Utils.replaceNull(String.valueOf(msg.getStart())) + "\n" +
                                    Utils.replaceNull(String.valueOf(msg.getEnd())) + "\n"
/*                                    Utils.replaceNull(String.valueOf(msg.getPassword()))+"\n"+
                                    Utils.replaceNull(String.valueOf(msg.getPhone()))+"\n"*/;
                            // json = trimMessage(json, "message");
                            if (json != null)
                                callback.onSuccess(new Response(errorResponses.getResult()));
                            Log.d("error", errorMesg + "");
                            break;
                    }
                    //Additional cases
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // pDialog.hide();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                params.put("room_id", roomId);
                params.put("start", startDate);
                params.put("end", endDate);
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void doReservationReques(final VolleyCallback callback, final Context context, String apiMethod, final String reservationId, final int isAccepted) {
        responseObject = new Response();
/*        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();*/
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + apiMethod,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String responsee) {

                        Log.d("response", responsee.toString());
                        //  pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        try {
                            JSONObject callNode = new JSONObject(responsee.toString());
                            responseObject.setResult(callNode.optInt("result"));
                            responseObject.setOnError(callNode.optString("error"));
                            responseObject.setMessage(callNode.optString("msj"));
                            Log.d("getResult", String.valueOf(responseObject.getResult()));
                            if (responseObject != null)
                                callback.onSuccess(responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 500:
                        case 404:
                        case 400:
                            callback.onSuccess(new Response(0));
/*                            json = new String(response.data);
                            Log.d("json", json + "");
                            ErrorResponse2 errorResponses = gson.fromJson(json, ErrorResponse2.class);
                            Error msg = errorResponses.getError();
                            String errorMesg = Utils.replaceNull(String.valueOf(msg.getRoomId())) + "\n" +
                                    Utils.replaceNull(String.valueOf(msg.getStart())) + "\n" +
                                    Utils.replaceNull(String.valueOf(msg.getEnd())) + "\n"
*//*                                    Utils.replaceNull(String.valueOf(msg.getPassword()))+"\n"+
                                    Utils.replaceNull(String.valueOf(msg.getPhone()))+"\n"*//*;
                            // json = trimMessage(json, "message");
                            if (json != null)
                                callback.onSuccess(new Response(errorResponses.getResult()));
                            Log.d("error", errorMesg + "");
                            break;*/
                    }
                    //Additional cases
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // pDialog.hide();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                params.put("reservation_id", reservationId);
                params.put("isAccepted", String.valueOf(isAccepted));
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void addToWishList(final VolleyCallback callback, final Context context, final String roomId, final Long id) {
        responseObject = new Response();
/*        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();*/
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "addtowishlist",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String responsee) {

                        Log.d("response", responsee.toString());
                        //  pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        try {
                            JSONObject callNode = new JSONObject(responsee.toString());
                            responseObject.setResult(callNode.optInt("result"));
                            responseObject.setOnError(callNode.optString("error"));
                            responseObject.setMessage(callNode.optString("msj"));
                            responseObject.setObject(new PlaceObject(id));
                            Log.d("getResult", String.valueOf(responseObject.getResult()));
                            if (responseObject != null)
                                callback.onSuccess(responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                responseObject.setOnError(error.getMessage());
                //pDialog.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //  pDialog.hide();
                //login_button.setEnabled(true);
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                params.put("room_id", roomId);
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void addRoom(final VolleyCallback callback, final Context context, final PlaceObject placeObject) {
        Log.d("placeObject", placeObject.toString());
        responseObject = new Response();

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "addroom",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String responsee) {
                        if (responsee != null && responsee.length() > 0) {

                            Log.d("response", responsee.toString());
                        }
                        pDialog.hide();
                        try {
                            JSONObject callNode = new JSONObject(responsee.toString());
                            responseObject.setResult(callNode.optInt("result"));
                            responseObject.setOnError(callNode.optString("error"));
                            responseObject.setMessage(callNode.optString("msj"));
                            responseObject.setObject(new PlaceObject(Integer.parseInt(callNode.getString("room_id"))));
                            Log.d("getResult", String.valueOf(responseObject.getResult()));
                            if (responseObject != null)
                                callback.onSuccess(responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String json = null;
                Log.d("error", error.getMessage() + "");

                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 500:
                        case 404:
                        case 400:
                            json = new String(response.data);
                            Log.d("json", json + "");
                            ErrorResponse2 errorResponses = gson.fromJson(json, ErrorResponse2.class);
                            Error msg = errorResponses.getError();
                            String errorMesg = Utils.replaceNull(String.valueOf(msg.getName())) + "\n" +
                                    Utils.replaceNull(String.valueOf(msg.getAirCondition())) + "\n" +
                                    Utils.replaceNull(String.valueOf(msg.getLocation())) + "\n"
/*                                    Utils.replaceNull(String.valueOf(msg.getPassword()))+"\n"+
                                    Utils.replaceNull(String.valueOf(msg.getPhone()))+"\n"*/;
                            // json = trimMessage(json, "message");
                            if (json != null)
                                callback.onSuccess(new Response(errorResponses.getResult()));
                            Log.d("error", errorMesg + "");
                            break;
                    }
                    //Additional cases
                }                //pDialog.dismiss();
                pDialog.hide();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                params.put("name", placeObject.getName());
                params.put("number_of_guests", placeObject.getNumberOfGuests());
                params.put("number_of_beds", placeObject.getNumberOfBeds());
                params.put("number_of_baths", placeObject.getNumberOfBaths());
                params.put("number_of_rooms", placeObject.getNumberOfRooms());
                params.put("description", placeObject.getDescription());
/*                params.put("tv", placeObject.getTv());
                params.put("wifi", placeObject.getWifi());
                params.put("pool", placeObject.getPool());
                params.put("air_condition", placeObject.getAirCondition());
                params.put("kitchen", placeObject.getKitchen());*/

                params.put("tv", "1");
                params.put("wifi", "1");
                params.put("pool", "1");
                params.put("air_condition", "1");
                params.put("kitchen", "1");
                params.put("price", placeObject.getPrice());
                params.put("location", placeObject.getLocation());
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }


}
