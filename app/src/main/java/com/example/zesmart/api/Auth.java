package com.example.zesmart.api;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zesmart.FragmentDashboard;
import com.example.zesmart.ListMateriActivity;
import com.example.zesmart.LoadingClass;
import com.example.zesmart.LoginActivity;
import com.example.zesmart.localstorage.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Auth  {
    private final Activity activity;
    private String url = "http://13.213.41.13:3005";

    public Auth(Activity myActivity) {
        activity = myActivity;
    }

    public void getUserLogin() {
        Intent pindahActivity;
        try{
            SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(activity);
            String checkToken = sharedPref.getString("token", null);
            Log.i("token", checkToken);

            activity.startActivity(new Intent(activity, FragmentDashboard.class));
        } catch (Exception e) {
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }

    }

    public void doLogin(String email, String password) {
        final LoadingClass loadingDialog = new LoadingClass(activity);
        loadingDialog.startLoadingDialog(null);
        url = url + "/user/login";
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            try {
                final String token = response.getString("token");
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.setString("token", token);
                loadingDialog.dissmissDialog();
                final Intent pindahActivity = new Intent(activity, FragmentDashboard.class);
                activity.startActivity(pindahActivity);

            } catch (JSONException e) {
                loadingDialog.dissmissDialog();
                Toast.makeText(activity, "Error, no token data in response", Toast.LENGTH_LONG).show();
            }

        }, error -> {
            if (error.networkResponse.data != null) {
                try {
                    String jsonString = new String(error.networkResponse.data,
                            HttpHeaderParser.parseCharset(error.networkResponse.headers));
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Log.i("test", jsonObject.toString());
                    final String message = jsonObject.getString("message");
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

                } catch (UnsupportedEncodingException | JSONException e) {
                    Toast.makeText(activity, "Something Error", Toast.LENGTH_LONG).show();
                }
            }
            loadingDialog.dissmissDialog();
        }) {
            @Override
            public byte[] getBody() {
                try {
                    // request body goes here
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("email", email);
                    jsonBody.put("password", password);
                    String requestBody = jsonBody.toString();
                    return requestBody.getBytes(StandardCharsets.UTF_8);
                } catch (JSONException exception) {
                    Toast.makeText(activity, "Something Error", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        requestQueue.add(objectRequest);

    }

    public void register(String email, String password, String username) {
        final LoadingClass loadingDialog = new LoadingClass(activity);
        loadingDialog.startLoadingDialog(null);
        url = url + "/user/register";
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            loadingDialog.dissmissDialog();
            Toast.makeText(activity, "Akun berhasil terdaftar, silahkan  login!", Toast.LENGTH_LONG).show();

            final Intent pindahActivity = new Intent(activity, LoginActivity.class);
            activity.startActivity(pindahActivity);

        }, error -> {
            if (error.networkResponse.data != null) {
                try {
                    String jsonString = new String(error.networkResponse.data,
                            HttpHeaderParser.parseCharset(error.networkResponse.headers));
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Log.i("test", jsonObject.toString());
                    final String message = jsonObject.getString("message");
                    if (message == null) {
                        Toast.makeText(activity, "Something Error", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    }

                } catch (UnsupportedEncodingException | JSONException e) {
                    Toast.makeText(activity, "Something Error", Toast.LENGTH_LONG).show();
                }
            }
            loadingDialog.dissmissDialog();
        }) {
            @Override
            public byte[] getBody() {
                try {
                    // request body goes here
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("email", email);
                    jsonBody.put("password", password);
                    jsonBody.put("username", username);
                    String requestBody = jsonBody.toString();
                    return requestBody.getBytes(StandardCharsets.UTF_8);
                } catch (JSONException exception) {
                    Toast.makeText(activity, "Something Error", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        requestQueue.add(objectRequest);
    }

}
