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
import com.example.zesmart.SubDetailMateriActivity;
import com.example.zesmart.localstorage.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SubCategory {
    private final Activity activity;
    private String url = "http://13.213.41.13:3005";

    public SubCategory(Activity myActivity) {
        activity = myActivity;
    }

    public void subCategoryList(int id) {
        url = url + "/subcategory/" + id;
        Log.i("url", url);
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.i("Response api", response.toString());
            for (int i = 0; i < response.length(); i++) {
                View myCompenent = inflater.inflate(R.layout.component_button_list_materi, null);
                final Button buttonComponent = myCompenent.findViewById(R.id.button_list_materi);
                LinearLayout mainLayout = (LinearLayout) activity.findViewById(R.id.layout_list_materi);
                JSONObject jsonObject = null;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0,0, 30);
                myCompenent.setLayoutParams(layoutParams);
                try {
                    jsonObject = response.getJSONObject(i);
                    String getCategory = jsonObject.getString("subCategoryTitle");
                    int getId = jsonObject.getInt("id");
                    buttonComponent.setText(getCategory);
                    buttonComponent.setTag(getId);

                    myCompenent.setOnClickListener(v -> {
                        Intent redirectListMateri = new Intent(activity, SubDetailMateriActivity.class);
                        redirectListMateri.putExtra("id", getId);
                        activity.startActivity(redirectListMateri);
                    });

                    mainLayout.addView(buttonComponent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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