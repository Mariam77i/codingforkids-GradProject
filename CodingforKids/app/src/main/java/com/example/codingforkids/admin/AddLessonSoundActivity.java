package com.example.codingforkids.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codingforkids.Links;
import com.example.codingforkids.R;
import com.example.codingforkids.RequestHandler;
import com.example.codingforkids.UriUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class AddLessonSoundActivity extends AppCompatActivity {

    String lesson_id, strSound, strVideo, strCome;
    ImageView imgImage;
    AppCompatButton btnUpload, btnSave, btnPlay, btnStop;
    boolean choose_sound = false;
    private int PICK_IMAGE_REQUEST = 1;
    Uri selectedAudioUri;
    TextView txtESC,txtTV, txtFile;
    File audioFile;
    private static final int PICK_AUDIO_REQUEST = 1; // Define the request code for picking an audio file
    private Uri filePath;
    String encodedAudio;
    RelativeLayout soundLayout;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_lesson_sound);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lesson_id = getIntent().getStringExtra("lessonID");
        strSound = getIntent().getStringExtra("sound");
        strVideo = getIntent().getStringExtra("video");
        strCome = getIntent().getStringExtra("come");

        imgImage = findViewById(R.id.imgImage);
        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);
        txtESC = findViewById(R.id.txtESC);
        txtFile = findViewById(R.id.txtFile);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);
        soundLayout = findViewById(R.id.soundLayout);
        mediaPlayer = new MediaPlayer();
        if(!strSound.equals("-")){
            try {
                mediaPlayer.setDataSource(strSound);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(mp -> btnPlay.setEnabled(true));
            } catch (Exception e) {
                Log.e("MediaPlayer", "Error initializing MediaPlayer", e);
            }
        }

        if(!strSound.equals("-")){
            txtFile.setText("Lesson" + lesson_id + ".mp3");
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
        btnUpload.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             showFileChooser();
                                         }
                                     }
        );
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choose_sound){
                    AddSound();
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.choose_sound), Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtESC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), AddLessonVideoActivity.class);
                in.putExtra("lessonID", lesson_id);
                in.putExtra("video", strVideo);
                in.putExtra("come", strCome);
                startActivity(in);
            }
        });

    }
    // Handle file selection
    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_AUDIO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                    InputStream inputStream = getContentResolver().openInputStream(filePath);
                    byte[] audioBytes = convertStreamToByteArray(inputStream);
                    encodedAudio = Base64.encodeToString(audioBytes, Base64.DEFAULT);
                choose_sound = true;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error selecting audio file", Toast.LENGTH_LONG).show();
            }
        }
    }
    public byte[] convertStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }
     // Method to start the audio picker
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Audio"), PICK_AUDIO_REQUEST);
    }

    private void AddSound(){
        class UploadSound extends AsyncTask<String, String ,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddLessonSoundActivity.this, "Upload Sound ..", null,true,true);
            }
            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... args) {
                HashMap<String,String> data = new HashMap<>();
                data.put("sound", encodedAudio);
                data.put("lessonID", lesson_id);
                String result = rh.sendPostRequest(Links.url_add_lesson_sound_link,data);
                if(result.contains("success")) {
                    Intent in = new Intent(getApplicationContext(), AddLessonVideoActivity.class);
                    in.putExtra("lessonID", lesson_id);
                    in.putExtra("video", strVideo);
                    in.putExtra("come", strCome);
                    startActivity(in);
                    return "Success";
                }
                else
                {
                    return "Failed Upload";
                }
            }
        }
        UploadSound ui = new UploadSound();
        ui.execute();
    }
}