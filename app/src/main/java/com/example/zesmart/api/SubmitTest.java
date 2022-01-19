package com.example.zesmart.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zesmart.ListMateriActivity;
import com.example.zesmart.LoadingClass;
import com.example.zesmart.LoginActivity;
import com.example.zesmart.R;
import com.example.zesmart.localstorage.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SubmitTest {
    private final Activity activity;
    private String url = "http://13.213.41.13:3005";

    public SubmitTest(Activity myActivity) {
        activity = myActivity;
    }

    public void doSubmitTest(int id) {
        url = url + "/submit-test";
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {

        }, error -> {
            if (error.networkResponse.data != null) {
                try {
                    String jsonString = new String(error.networkResponse.data,
                            HttpHeaderParser.parseCharset(error.networkResponse.headers));
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Log.i("test json", jsonObject.toString());
                    final String message = jsonObject.getString("message");
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

                } catch (UnsupportedEncodingException | JSONException e) {
                    Toast.makeText(activity, "Something Error", Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharedPref = activity.getSharedPreferences("MyPref", 0);

                params.put("Authorization", sharedPref.getString("token", null));
                return params;
            }

        };

        requestQueue.add(objectRequest);

    }

    public void doSaveTest(int id) {
        url = url + "/save-test";
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PATCH, url, null, response -> {

        }, error -> {
            if (error.networkResponse.data != null) {
                try {
                    String jsonString = new String(error.networkResponse.data,
                            HttpHeaderParser.parseCharset(error.networkResponse.headers));
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Log.i("test json", jsonObject.toString());
                    final String message = jsonObject.getString("message");
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

                } catch (UnsupportedEncodingException | JSONException e) {
                    Toast.makeText(activity, "Something Error", Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharedPref = activity.getSharedPreferences("MyPref", 0);

                params.put("Authorization", sharedPref.getString("token", null));
                return params;
            }

        };

        requestQueue.add(objectRequest);

    }
}
