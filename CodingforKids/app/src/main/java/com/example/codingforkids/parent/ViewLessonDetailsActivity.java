package com.example.codingforkids.parent;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codingforkids.R;
import com.example.codingforkids.admin.ViewVideoActivity;
import com.google.android.material.textfield.TextInputLayout;

public class ViewLessonDetailsActivity extends AppCompatActivity {

    String strID, strTitle, strDesc, strLevel, strLang, strTopic, strImage, strSound, strVideo;
    TextInputLayout txtName, txtDesc, txtLevel, txtLang, txtTopic;
    AppCompatButton btnImage, btnVideo, btnPlay, btnStop;
    ImageView imgBack;
    TextView txtFile;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_lesson_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //// read lesson data
        strID = getIntent().getStringExtra("id");
        strTitle = getIntent().getStringExtra("title");
        strDesc = getIntent().getStringExtra("desc");
        strLevel = getIntent().getStringExtra("level");
        strLang = getIntent().getStringExtra("lang");
        strTopic = getIntent().getStringExtra("topic");
        strImage = getIntent().getStringExtra("img");
        strSound = getIntent().getStringExtra("sound");
        strVideo = getIntent().getStringExtra("video");

        imgBack = findViewById(R.id.imgBack);
        btnImage = findViewById(R.id.btnImage);
        btnVideo = findViewById(R.id.btnVideo);
        txtName = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDesc);
        txtLevel = findViewById(R.id.txtLevel);
        txtLang = findViewById(R.id.txtLang);
        txtTopic = findViewById(R.id.txtTopic);
        txtFile = findViewById(R.id.txtFile);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);

        txtName.getEditText().setText(strTitle);
        txtDesc.getEditText().setText(strDesc);
        txtLevel.getEditText().setText(strLevel);
        txtLang.getEditText().setText(strLang);
        txtTopic.getEditText().setText(strTopic);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewLessonImageActivity.class);
                i.putExtra("img", strImage);
                startActivity(i);
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ViewVideoActivity.class);
                in.putExtra("lessonID", strID);
                in.putExtra("video", strVideo);
                startActivity(in);
            }
        });
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(strSound);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> btnPlay.setEnabled(true));
        } catch (Exception e) {
            Log.e("MediaPlayer", "Error initializing MediaPlayer", e);
        }

        if(!strSound.equals("-")){
            txtFile.setText("Lesson" + strID + ".mp3");
            btnPlay.setEnabled(true);
            btnStop.setEnabled(true);
        }
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    try {
                        mediaPlayer.setDataSource(strSound);
                        mediaPlayer.prepareAsync();
                    } catch (Exception e) {
                        Log.e("MediaPlayer", "Error resetting MediaPlayer", e);
                    }
                }
            }
        });

    }
}