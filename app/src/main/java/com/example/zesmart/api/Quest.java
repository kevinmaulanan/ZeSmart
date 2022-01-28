package com.example.zesmart.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.zesmart.R;
import com.example.zesmart.localstorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Quest {
    private final Activity activity;
    private String url = "http://13.213.41.13:3005";

    public Quest(Activity myActivity) {
        activity = myActivity;
    }

    public void questList(int id) {
        url += "/category/" + id;
        final RequestQueue requestQueue = Volley.newRequestQueue(activity);

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            try {
                JSONArray jsonArray = response.getJSONArray("question");
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.setString("question", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        if (i == 0) {
                            JSONObject question = jsonArray.getJSONObject(i);
                            JSONArray choice = question.getJSONArray("choice");
                            String questionText = question.getString("question");
                            String questionTextNo = question.getString("no");

                            Button buttonPref = activity.findViewById(R.id.quest_prev);
                            Button buttonNext = activity.findViewById(R.id.quest_next);
                            buttonPref.setVisibility(View.INVISIBLE);

                            TextView number = (TextView) activity.findViewById(R.id.quest_number);
                            TextView questText = (TextView) activity.findViewById(R.id.quest_text);

                            String textNumber = "Questions " + questionTextNo + "/" + jsonArray.length();
                            number.setText(textNumber);
                            questText.setText(questionText);

                            LinearLayout questComponentActivity = (LinearLayout) activity.findViewById(R.id.quest_component);

                            questComponentActivity.setTag(i);

                            for (int j = 0; j < choice.length(); j++) {
                                View myCompenent = inflater.inflate(R.layout.component_button_qiest, null);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(0, 40,0, 0);
                                myCompenent.setLayoutParams(layoutParams);

                                JSONObject choiceList = choice.getJSONObject(j);
                                TextView componentTextAbjad = (TextView) myCompenent.findViewById(R.id.quest_abjad);
                                TextView componentTextChoice = (TextView) myCompenent.findViewById(R.id.quest_text_choice);
                                String apiStringChoiceNo = choiceList.getString("no");
                                String apiStringAnswer = choiceList.getString("answer");

                                componentTextAbjad.setText(apiStringChoiceNo + ".");
                                componentTextChoice.setText(apiStringAnswer);
                                int finalI1 = i;
                                myCompenent.setOnClickListener(v -> {
                                    try {
                                        pilihJawaban(finalI1, apiStringChoiceNo);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                });
                                questComponentActivity.addView(myCompenent);
                            }

                            int finalI = i;
                            buttonNext.setOnClickListener(v -> {
                                try {
                                    next(finalI);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
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


    public void next(int id) throws JSONException {
        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(activity);

        JSONArray jsonArray =  new JSONArray(sharedPref.getString("question", null));

        for (int i = 0; i < jsonArray.length(); i++) {
            if (i > id) {
                Button buttonPref = activity.findViewById(R.id.quest_prev);
                Button buttonNext = activity.findViewById(R.id.quest_next);
                if (i + 1 == jsonArray.length()) {
                    buttonNext.setText("Finish");
                    buttonPref.setVisibility(View.VISIBLE);

                    int finalI = i;
                    buttonPref.setOnClickListener(v -> {
                        try {
                            prev(finalI);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    });
                }
                JSONObject question = jsonArray.getJSONObject(i);
                JSONArray choice = question.getJSONArray("choice");
                String questionText = question.getString("question");
                String questionTextNo = question.getString("no");


                TextView number = (TextView) activity.findViewById(R.id.quest_number);
                TextView questText = (TextView) activity.findViewById(R.id.quest_text);

                String textNumber = "Questions " + (i + 1) + "/" + jsonArray.length();
                number.setText(textNumber);
                questText.setText(questionText);

                LinearLayout questComponentActivity = (LinearLayout) activity.findViewById(R.id.quest_component);
                questComponentActivity.removeAllViews();
                for (int j = 0; j < choice.length(); j++) {
                    final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View myCompenent = inflater.inflate(R.layout.component_button_qiest, null);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 40,0, 0);
                    myCompenent.setLayoutParams(layoutParams);

                    JSONObject choiceList = choice.getJSONObject(j);
                    TextView componentTextAbjad = (TextView) myCompenent.findViewById(R.id.quest_abjad);
                    TextView componentTextChoice = (TextView) myCompenent.findViewById(R.id.quest_text_choice);
                    String apiStringChoiceNo = choiceList.getString("no");
                    String apiStringAnswer = choiceList.getString("answer");

                    componentTextAbjad.setText(apiStringChoiceNo + ".");
                    componentTextChoice.setText(apiStringAnswer);

                    int finalI1 = i;
                    myCompenent.setOnClickListener(v -> {
                        try {
                            pilihJawaban(finalI1, apiStringChoiceNo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });

                    questComponentActivity.addView(myCompenent);
                }
                break;
            }
        }


    }


    public void prev(int id) throws JSONException {
        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(activity);

        JSONArray jsonArray =  new JSONArray(sharedPref.getString("question", null));

        for (int i = 0; i < jsonArray.length(); i++) {
            if (i == id - 1) {
                Button buttonPref = activity.findViewById(R.id.quest_prev);
                Button buttonNext = activity.findViewById(R.id.quest_next);
                if (i == 0) {
                    buttonPref.setVisibility(View.INVISIBLE);
                }

                buttonNext.setText("Next");
                JSONObject question = jsonArray.getJSONObject(i);
                JSONArray choice = question.getJSONArray("choice");
                String questionText = question.getString("question");
                String questionTextNo = question.getString("no");


                TextView number = (TextView) activity.findViewById(R.id.quest_number);
                TextView questText = (TextView) activity.findViewById(R.id.quest_text);

                String textNumber = "Questions " + (i + 1) + "/" + jsonArray.length();
                number.setText(textNumber);
                questText.setText(questionText);

                LinearLayout questComponentActivity = (LinearLayout) activity.findViewById(R.id.quest_component);
                questComponentActivity.removeAllViews();
                for (int j = 0; j < choice.length(); j++) {
                    final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View myCompenent = inflater.inflate(R.layout.component_button_qiest, null);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 40,0, 0);
                    myCompenent.setLayoutParams(layoutParams);

                    JSONObject choiceList = choice.getJSONObject(j);
                    TextView componentTextAbjad = (TextView) myCompenent.findViewById(R.id.quest_abjad);
                    TextView componentTextChoice = (TextView) myCompenent.findViewById(R.id.quest_text_choice);
                    String apiStringChoiceNo = choiceList.getString("no");
                    String apiStringAnswer = choiceList.getString("answer");

                    componentTextAbjad.setText(apiStringChoiceNo + ".");
                    componentTextChoice.setText(apiStringAnswer);

                    int finalI = i;
                    myCompenent.setOnClickListener(v -> {
                        try {
                            pilihJawaban(finalI, apiStringChoiceNo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    questComponentActivity.addView(myCompenent);
                }
                break;
            }
        }


    }

    public void pilihJawaban(int id, String abjad) throws JSONException {
        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(activity);

        JSONArray jsonArray =  new JSONArray(sharedPref.getString("question", null));

        for (int i = 0; i < jsonArray.length(); i++) {
            if (i == id) {
                Button buttonPref = activity.findViewById(R.id.quest_prev);
                Button buttonNext = activity.findViewById(R.id.quest_next);
                if (i == 0) {
                    buttonPref.setVisibility(View.INVISIBLE);
                }

                buttonNext.setText("Next");
                JSONObject question = jsonArray.getJSONObject(i);
                JSONArray choice = question.getJSONArray("choice");
                String questionText = question.getString("question");
                String questionTextNo = question.getString("no");


                TextView number = (TextView) activity.findViewById(R.id.quest_number);
                TextView questText = (TextView) activity.findViewById(R.id.quest_text);

                String textNumber = "Questions " + (i + 1) + "/" + jsonArray.length();
                number.setText(textNumber);
                questText.setText(questionText);

                LinearLayout questComponentActivity = (LinearLayout) activity.findViewById(R.id.quest_component);
                questComponentActivity.removeAllViews();
                for (int j = 0; j < choice.length(); j++) {
                    final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View myCompenent = inflater.inflate(R.layout.component_button_qiest, null);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 40,0, 0);
                    myCompenent.setLayoutParams(layoutParams);

                    JSONObject choiceList = choice.getJSONObject(j);
                    TextView componentTextAbjad = (TextView) myCompenent.findViewById(R.id.quest_abjad);
                    TextView componentTextChoice = (TextView) myCompenent.findViewById(R.id.quest_text_choice);
                    String apiStringChoiceNo = choiceList.getString("no");
                    String apiStringAnswer = choiceList.getString("answer");

                    componentTextAbjad.setText(apiStringChoiceNo + ".");
                    componentTextChoice.setText(apiStringAnswer);

                    if (apiStringChoiceNo.equals(abjad)) {
                        myCompenent.setBackgroundColor(Color.parseColor("#B5D4FF"));
                    }

                    int finalI1 = i;
                    myCompenent.setOnClickListener(v -> {
                        try {
                            pilihJawaban(finalI1, apiStringChoiceNo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    questComponentActivity.addView(myCompenent);
                }
                break;
            }
        }


    }
}
