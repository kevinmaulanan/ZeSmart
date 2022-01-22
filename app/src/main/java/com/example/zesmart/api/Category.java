package com.example.zesmart.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

public class Category {
    private final Activity activity;
    private String url = "http://13.213.41.13:3005";

    public Category(Activity myActivity) {
        activity = myActivity;
    }


    public void categoryList(View view) {
        url = url + "/category";
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            TableRow tr = new TableRow(inflater.getContext());
            tr.setGravity(Gravity.CENTER);


            final TableLayout tableLayout = view.findViewById(R.id.table_category);
            for (int i = 0; i < response.length(); i++) {

                View myCompenent = inflater.inflate(R.layout.component_linear_layout_category, null);
                final TextView textViewComponent = myCompenent.findViewById(R.id.text_category);

                try {
                    if (i == 0 || i  % 3 == 0)  {
                        tableLayout.addView(tr);
                        tr = new TableRow(inflater.getContext());
                        tr.setGravity(Gravity.CENTER);
                    }


                    JSONObject jsonObject = response.getJSONObject(i);
                    String getCategory = jsonObject.getString("category");
                    int getId = jsonObject.getInt("id");

                    textViewComponent.setText(getCategory);
                    textViewComponent.setTag(getId);
                    myCompenent.setTag(getId);

                    myCompenent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent redirectListMateri = new Intent(activity, ListMateriActivity.class);
                            redirectListMateri.putExtra("id", getId);
                            redirectListMateri.putExtra("category", getCategory);
                            activity.startActivity(redirectListMateri);

                        }
                    });

                    tr.addView(myCompenent);

                    if (i + 1  == response.length() && i > 2) {
                        for (int j = 0; j < (3 - (i + 1) % 3); j++) {
                            View myCompenent2 = inflater.inflate(R.layout.component_linear_layout_category, null);
                            myCompenent2.setAlpha(0);
                            tr.addView(myCompenent2);
                        }
                        tableLayout.addView(tr);

                    }

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
