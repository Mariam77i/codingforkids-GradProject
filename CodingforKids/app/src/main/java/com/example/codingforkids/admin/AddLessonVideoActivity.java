package com.example.codingforkids.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
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
import com.example.codingforkids.teacher.TeacherActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class AddLessonVideoActivity extends AppCompatActivity {
    String lesson_id, strVideo, strCome;
    ImageView imgImage;
    AppCompatButton btnUpload, btnSave, btnPlay;
    boolean choose_video = false;
    TextView txtESC, txtFile;
    private static final int PICK_VIDEO_REQUEST = 1; // Define the request code for picking an audio file
    private Uri filePath;
    String encodedVedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_lesson_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lesson_id = getIntent().getStringExtra("lessonID");
        strVideo = getIntent().getStringExtra("video");
        strCome = getIntent().getStringExtra("come");

        imgImage = findViewById(R.id.imgImage);
        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);
        txtESC = findViewById(R.id.txtESC);
        btnPlay = findViewById(R.id.btnPlay);
        txtFile = findViewById(R.id.txtFile);

        if(!strVideo.equals("-")){
            txtFile.setText("video" + lesson_id + ".mp4");
            btnPlay.setEnabled(true);
        }
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ViewVideoActivity.class);
                in.putExtra("lessonID", lesson_id);
                in.putExtra("video", strVideo);
                in.putExtra("come", strCome);
                startActivity(in);
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
                if(choose_video){
                    AddVideo();
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.choose_video), Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtESC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), AddExerciseActivity.class);
                in.putExtra("id", lesson_id);
                startActivity(in);
            }
        });

    }
    // Handle file selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                byte[] audioBytes = convertStreamToByteArray(inputStream);
                encodedVedio = Base64.encodeToString(audioBytes, Base64.DEFAULT);
                choose_video = true;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error selecting video file", Toast.LENGTH_LONG).show();
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
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }

    private void AddVideo(){
        class UploadVideo extends AsyncTask<String, String ,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddLessonVideoActivity.this, "Upload Video ..", null,true,true);
            }
            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... args) {
                HashMap<String,String> data = new HashMap<>();
                data.put("video", encodedVedio);
                data.put("lessonID", lesson_id);
                String result = rh.sendPostRequest(Links.url_add_lesson_video_link,data);
                if(result.contains("success")) {
                    Intent in = new Intent(getApplicationContext(), AddExerciseActivity.class);
                    in.putExtra("id", lesson_id);
                    startActivity(in);
                    return "Success";
                }
                else
                {
                    return "Failed Upload";
                }
            }
        }
        UploadVideo ui = new UploadVideo();
        ui.execute();
    }
}