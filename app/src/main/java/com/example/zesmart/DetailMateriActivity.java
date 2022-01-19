package com.example.zesmart;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.VideoView;

import com.google.android.material.tabs.TabLayout;

public class DetailMateriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        Log.i("Log Content", "test");
        getContent("materi");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getContent("materi");
                        break;
                    case 1:
                        getContent("video");
                        final VideoView videoView = findViewById(R.id.VideoDetailMater); //id in your xml file
                        videoView.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=HexFqifusOk&list=RDHexFqifusOk&start_radio=1")); //the string of the URL mentioned above

                        videoView.start();
                        MediaController ctlr = new MediaController(getApplicationContext());
                        ctlr.setMediaPlayer(videoView);
                        videoView.setMediaController(ctlr);
                        videoView.requestFocus();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void getContent(String content) {
        ScrollView myLayout = null;
        int getMyLayout = R.layout.component_materi;
        // get a reference to the already created main layout
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.tabs);
        mainLayout.removeAllViews();

        Log.i("Log Content", content);
        Log.i("Log Content", String.valueOf(TextUtils.equals(content, "video")));
        LayoutInflater inflater = getLayoutInflater();

        if (TextUtils.equals(content, "video")) {
            getMyLayout = R.layout.component_video;
        }

        myLayout = (ScrollView) inflater.inflate(getMyLayout, mainLayout, false);

        // add our custom layout to the main layout
        mainLayout.addView(myLayout);

    }
}