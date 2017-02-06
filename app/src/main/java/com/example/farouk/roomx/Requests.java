package com.example.farouk.roomx;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.farouk.roomx.app.Prefs;
import com.example.farouk.roomx.app.VolleySingleton;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.UserinfoLogin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by ababdullah on 30/01/2017.
 */

public class Requests {
    private final Gson gson;
    ProgressDialog pDialog;
    UserinfoLogin userinfoLoginObject;
    Response responseObject;
    private String jsonResponse;

    public Requests() {
        responseObject = new Response();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    public void makeLogin(final VolleyCallback callback, final Context context, final String email, final String password) {
        responseObject = new Response();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "authenticate?",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String responsee) {

                        Log.d("response", responsee.toString());
                        pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        try {
                            JSONObject callNode = new JSONObject(responsee.toString());
                            userinfoLoginObject = new UserinfoLogin();
                            userinfoLoginObject.setToken(callNode.optString("token"));
                            responseObject.setResult(callNode.optInt("result"));
                            responseObject.setOnError(callNode.optString("error"));
                            responseObject.setObject(userinfoLoginObject);
                            Log.d("getResult1", String.valueOf(responseObject.getResult()));
                            if (responseObject != null)
                                callback.onSuccess(responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Prefs.with(context).setUser(userinfoLoginObject);
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
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void makeRegister(final VolleyCallback callback, final Context context, final String name, final String email, final String password, final String passwordConfirm, final String phone) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "Register",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String responsee) {
                        Log.d("response", responsee.toString());
                        pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        responseObject = new Response();

                        try {
                            JSONObject callNode = new JSONObject(responsee.toString());
                            userinfoLoginObject = new UserinfoLogin();
                            userinfoLoginObject.setToken(callNode.optString("token"));
                            responseObject.setResult(callNode.optInt("result"));
                            responseObject.setMessage(callNode.optString("msg"));
                            if (responseObject != null)
                                callback.onSuccess(responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Prefs.with(context).setUserinfoLogin(userinfoLoginObject);

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
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("password_confirmation ", passwordConfirm);
                params.put("phone", phone);
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);

    }

    public void getUserProfile(final VolleyCallback callback, final Context context) {

        UserinfoLogin userinfoLogin = Prefs.with(context).getUser();
        final String token = userinfoLogin.getToken();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "getuserprofile",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String responsee) {
                        Log.d("response", responsee.toString());
                        pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        responseObject = new Response();

                        try {
                            JSONObject callNode = new JSONObject(responsee.toString());
                            userinfoLoginObject = new UserinfoLogin();
//                            userinfoLoginObject.setToken(callNode.optString("token"));
//                            responseObject.setResult(callNode.optInt("result"));
//                            responseObject.setMessage(callNode.optString("msg"));
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

    public void getExploreList(final VolleyCallback callback, final Context context) {

        UserinfoLogin userinfoLogin = Prefs.with(context).getUser();
        final String token = userinfoLogin.getToken();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest  req = new StringRequest (Request.Method.POST, Const.BASE_URL + "shownewestrooms",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

                        List<PlaceObject> posts = Arrays.asList(gson.fromJson(response, PlaceObject[].class));
                        responseObject = new Response();

                        Log.i("PostActivity", posts.size() + " posts loaded.");
                        for (PlaceObject post : posts) {
                            Log.i("PostActivity", post.getName());
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

/*
    public void getExploreList(final VolleyCallback callback, final Context context) {

        UserinfoLogin userinfoLogin = Prefs.with(context).getUser();
        final String token = userinfoLogin.getToken();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.BASE_URL + "shownewestrooms",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String responsee) {
                        Log.d("response", responsee.toString());
                        pDialog.hide();
                        //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        responseObject = new Response();

                        try {
                            JSONArray jsonArray = new JSONArray(responsee.toString());
                            for(int i=0;i<jsonArray.length();i++) {

                            }
                                userinfoLoginObject = new UserinfoLogin();
//                            userinfoLoginObject.setToken(callNode.optString("token"));
//                            responseObject.setResult(callNode.optInt("result"));
//                            responseObject.setMessage(callNode.optString("msg"));
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
*/

}
