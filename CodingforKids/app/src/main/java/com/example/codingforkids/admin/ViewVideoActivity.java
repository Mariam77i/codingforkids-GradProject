package com.example.codingforkids.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.MediaController;

import com.example.codingforkids.R;

public class ViewVideoActivity extends AppCompatActivity {

    String strVideo, lessonID, strCome;
    VideoView videoView;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lessonID = getIntent().getStringExtra("lessonID");
        strVideo = getIntent().getStringExtra("video");
        strCome = getIntent().getStringExtra("come");

        videoView = findViewById(R.id.videoView);
        imgBack = findViewById(R.id.imgBack);
        Uri uri = Uri.parse(strVideo);

        // Set the video URI and add a MediaController for playback controls
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Start playing the video
        videoView.setOnPreparedListener(mp -> {
            mp.setOnVideoSizeChangedListener((mp1, width, height) -> {
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
            });
            videoView.start();
        });

        // Set full screen
        videoView.setOnCompletionListener(mp -> {
            // Handle video completion, if needed
        });

        videoView.requestFocus();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(strCome.equals("1") || strCome.equals("2")) {
                    Intent in = new Intent(getApplicationContext(), AddLessonVideoActivity.class);
                    in.putExtra("lessonID", lessonID);
                    in.putExtra("video", strVideo);
                    in.putExtra("come", strCome);
                    startActivity(in);
                }else{
                    onBackPressed();
                }
            }
        });
    }
}