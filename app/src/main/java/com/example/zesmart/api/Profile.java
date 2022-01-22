package com.example.zesmart.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zesmart.FragmentProfile;
import com.example.zesmart.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class Profile {
    private final Activity activity;
    private String url = "http://13.213.41.13:3005";

    public Profile(Activity myActivity) {
        activity = myActivity;
    }

    public void ProfileDetail(View view) {
        url += "/user/profile";
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {
                final String name = response.getString("name");
                final String email = response.getString("email");
                final String username = response.getString("username");
                final TextView textViewName = (TextView) view.findViewById(R.id.profile_name);
                final TextView textViewEmail = (TextView) view.findViewById(R.id.profile_email);
                final TextView textViewUsername = (TextView) view.findViewById(R.id.profile_username);
                textViewName.setText(name);
                textViewEmail.setText(email);
                textViewUsername.setText(username);
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }, error -> {
            try {
                if (error.networkResponse.data != null) {
                    try {
                        String jsonString = new String(error.networkResponse.data,
                                HttpHeaderParser.parseCharset(error.networkResponse.headers));
                        JSONObject jsonObject = new JSONObject(jsonString);
                        final String message = jsonObject.getString("message");
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

                    } catch (UnsupportedEncodingException | JSONException e) {
                        Toast.makeText(activity, "Something Error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activity, "Something Error", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {

            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(activity);
                params.put("Authorization", sharedPref.getString("token", null));
                return params;
            }

        };

        requestQueue.add(objectRequest);

    }

    public void ProfileUpdate(JSONObject data) {
        url += "/profile/update";
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.PUT, url, null, response -> {
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

            public byte[] getBody() {
                String requestBody = data.toString();
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }


        };

        requestQueue.add(objectRequest);

    }
}
