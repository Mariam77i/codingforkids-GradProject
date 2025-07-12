package com.example.codingforkids.child;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codingforkids.R;
import com.example.codingforkids.admin.AddLessonVideoActivity;

public class ViewLessonVideoChildActivity extends AppCompatActivity {

    String strVideo, lessonID, strCome;
    VideoView videoView;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_lesson_video_child);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        strVideo = getIntent().getStringExtra("video");

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
                onBackPressed();
            }
        });
        enterFullscreen();
    }
    private void enterFullscreen() {
        // Hide the system UI for fullscreen mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}