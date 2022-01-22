package com.example.zesmart.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zesmart.DetailMateriActivity;
import com.example.zesmart.ListMateriActivity;
import com.example.zesmart.LoadingClass;
import com.example.zesmart.LoginActivity;
import com.example.zesmart.R;
import com.example.zesmart.SubDetailMateriActivity;
import com.example.zesmart.localstorage.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Materi {
    private final Activity activity;
    private String url = "http://13.213.41.13:3005";

    public Materi(Activity myActivity) {
        activity = myActivity;
    }


    public void materiList(int id) {
        url = url + "/material/" + id;
        Log.i("url", url);
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.i("Response api", response.toString());
            for (int i = 0; i < response.length(); i++) {
                View myCompenent = inflater.inflate(R.layout.component_linear_layout_list_materi, null);
                final TextView textView = myCompenent.findViewById(R.id.text_list_materi);
                LinearLayout mainLayout = (LinearLayout) activity.findViewById(R.id.layout_list_materi);
                JSONObject jsonObject = null;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0,0, 10);
                myCompenent.setLayoutParams(layoutParams);
                try {
                    jsonObject = response.getJSONObject(i);
                    String materialTitle = jsonObject.getString("materialTitle");
                    int getId = jsonObject.getInt("id");
                    textView.setText(materialTitle);
                    textView.setTag(getId);



                    myCompenent.setOnClickListener(v -> {
                        Intent redirectDetailMateri = new Intent(activity, DetailMateriActivity.class);
                        redirectDetailMateri.putExtra("id", getId);
                        activity.startActivity(redirectDetailMateri);
                    });

                    mainLayout.addView(myCompenent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            try {
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

    public void materiDetail(int id) {
        url = url + "/material/detail/" + id;
        Log.i("url", url);
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.i("Response api", response.toString());
            View myCompenentText = inflater.inflate(R.layout.component_materi, null);
            View myCompenentVideo = inflater.inflate(R.layout.component_video, null);
            try {
                String materialText = response.getString("materialText");
                String materialVideo = response.getString("materialVideo");

                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.setString("materialText", materialText);
                localStorage.setString("materialVideo", materialVideo);

                TextView textView = myCompenentText.findViewById(R.id.text_material);
                textView.setText(materialText);



                LinearLayout mainLayout = (LinearLayout) activity.findViewById(R.id.tabs);
                mainLayout.removeAllViews();

                mainLayout.addView(myCompenentText);
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
                        Log.i("test json", jsonObject.toString());
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

    public void getContent(String content) {
        try {
            final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View getMyLayout = inflater.inflate(R.layout.component_materi, null);;

            LinearLayout mainLayout = (LinearLayout) activity.findViewById(R.id.tabs);
            mainLayout.removeAllViews();

            SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(activity);




            if (TextUtils.equals(content, "video")) {
                getMyLayout = inflater.inflate(R.layout.component_video, null);
                String materialVideo = sharedPref.getString("materialVideo", null);
                final VideoView videoView = getMyLayout.findViewById(R.id.VideoDetailMater); //id in your xml file
                videoView.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=HexFqifusOk&list=RDHexFqifusOk&start_radio=1")); //the string of the URL mentioned above

                videoView.start();
                MediaController ctlr = new MediaController(activity);
                ctlr.setMediaPlayer(videoView);
                videoView.setMediaController(ctlr);
                videoView.requestFocus();
            } else {
                String materialText = sharedPref.getString("materialText", null);
                TextView textView = getMyLayout.findViewById(R.id.text_material);
                textView.setText(materialText);
            }



            // add our custom layout to the main layout
            mainLayout.addView(getMyLayout);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

    }
}
