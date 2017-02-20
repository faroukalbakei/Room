package com.example.farouk.roomx.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.farouk.roomx.model.Error;
import com.example.farouk.roomx.model.ErrorResponse;
import com.example.farouk.roomx.model.LoginResponse;
import com.example.farouk.roomx.model.Msg;
import com.example.farouk.roomx.model.Reservation;
import com.example.farouk.roomx.model.ReservationResult;
import com.example.farouk.roomx.model.UserResult;
import com.example.farouk.roomx.model.RoomPhoto;
import com.example.farouk.roomx.model.RoomReservation;
import com.example.farouk.roomx.model.User;
import com.example.farouk.roomx.util.Const;
import com.example.farouk.roomx.app.Prefs;
import com.example.farouk.roomx.app.VolleySingleton;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.UserinfoLogin;
import com.example.farouk.roomx.util.Utils;
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
    com.example.farouk.roomx.model.Response responseObject;
    private String token;
    Context mContext;

    public Requests(Context context) {
        mContext = context;
        loginResponse = Prefs.with(context).getUser();
        if (loginResponse != null) {
            token = loginResponse.getToken();
        }

        responseObject = new com.example.farouk.roomx.model.Response();
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
                            Error msg =errorResponses.getMsg().getError();
                            String errorMesg= Utils.replaceNull(String.valueOf(msg.getName()))+"\n"+
                            Utils.replaceNull(String.valueOf(msg.getEmail()))+"\n"+
                                    Utils.replaceNull(String.valueOf(msg.getPassword()))+"\n"+
                                    Utils.replaceNull(String.valueOf(msg.getPhone()))+"\n"
                                    ;
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

        StringRequest req = new StringRequest(Request.Method.POST, Const.BASE_URL + apiMethod,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

                        List<PlaceObject> posts = Arrays.asList(gson.fromJson(response, PlaceObject[].class));
                        responseObject = new Response();
                        for (int i = 0; i < posts.size(); i++) {
                            PlaceObject.save(posts.get(i));
                            User user = posts.get(i).getUser();
                            user.setId(posts.get(i).getId());
                            User.save(user);
                            for (int rp = 0; rp < posts.get(i).getRoomPhoto().size(); rp++) {
                                RoomPhoto roomPhoto = posts.get(i).getRoomPhoto().get(rp);
                                roomPhoto.setPlaceId(posts.get(i).getId());
                                RoomPhoto.save(roomPhoto);
                            }
                            for (int rr = 0; rr < posts.get(i).getRoomReservation().size(); rr++) {
                                RoomReservation roomReservation = posts.get(i).getRoomReservation().get(rr);
                                roomReservation.setPlaceId(posts.get(i).getId());
                                RoomReservation.save(roomReservation);
                            }
                        }

                        Log.i("PostActivity", posts.size() + " posts loaded.");
                        for (PlaceObject post : posts) {
                            Log.i("PostActivity", post.toString());
                        }
                        responseObject.setObject(posts);
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

        Call<UserResult> resultCall = service.uploadImage(tokenBody, body);

        resultCall.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, retrofit2.Response<UserResult> response) {

                pDialog.hide();

                // Response Success or Fail
                if (response.isSuccessful()) {
                    if (response.body().getUser() != null)
                        Log.d("uploadImage", "success");
                    Log.d("response", response.body().toString());

                    callback.onSuccess(new Response(true));

                } else {
                    callback.onSuccess(new Response(false));
                    Log.d("uploadImage", "fail");
                }

            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
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

    public void getUserReservations(final VolleyCallback callback, final Context context) {

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest req = new StringRequest(Request.Method.POST, Const.BASE_URL + "getuserreservations",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("getuserreservations", response.toString());

                        List<ReservationResult> posts = Arrays.asList(gson.fromJson(response, ReservationResult.class));
                        responseObject = new Response();

                        Log.i("getuserreservations", posts.size() + " posts loaded.");
                        List<Reservation> reservationList = new ArrayList<>();
                        for (Reservation reservation : posts.get(0).getReservation()) {
                            reservationList.add(reservation);
                            Log.i("reservation", reservation.toString());
                        }
                        Log.i("reservationList", reservationList.toString());

                        responseObject.setObject(reservationList);
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
                params.put("start", startDate);
                params.put("end", endDate);
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void addToWishList(final VolleyCallback callback, final Context context, final String roomId) {
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
        Log.d("placeObject" , placeObject.toString());
        responseObject = new Response();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "addroom",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String responsee) {

                        Log.d("response", responsee.toString());
                        pDialog.hide();
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
                            json = new String(response.data);
                            ErrorResponse errorResponses = gson.fromJson(json, ErrorResponse.class);
                            Log.d("error", json + "");

                            // json = trimMessage(json, "message");
                            if (json != null) callback.onSuccess(new Response(false, json));
                            break;
                    }
                    //Additional cases
                }                //pDialog.dismiss();
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
                params.put("name", placeObject.getName());
                params.put("number_of_guests", placeObject.getNumberOfGuests());
                params.put("number_of_beds", placeObject.getNumberOfBeds());
                params.put("number_of_baths", placeObject.getNumberOfBaths());
                params.put("number_of_rooms", placeObject.getNumberOfRooms());
                params.put("description", placeObject.getDescription());
                params.put("tv", placeObject.getTv());
                params.put("wifi", placeObject.getWifi());
                params.put("pool", placeObject.getPool());
                params.put("air_condition", placeObject.getAirCondition());
                params.put("kitchen", placeObject.getKitchen());
                params.put("price", placeObject.getPrice());
                params.put("location", placeObject.getLocation());
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }


}
