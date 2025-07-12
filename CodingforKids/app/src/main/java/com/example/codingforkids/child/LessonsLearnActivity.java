package com.example.codingforkids.child;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codingforkids.JsonParser;
import com.example.codingforkids.Links;
import com.example.codingforkids.LoginActivity;
import com.example.codingforkids.R;
import com.example.codingforkids.admin.AdminActivity;
import com.example.codingforkids.admin.ViewVideoActivity;
import com.example.codingforkids.models.Admin;
import com.example.codingforkids.models.Child;
import com.example.codingforkids.models.Parent;
import com.example.codingforkids.models.Teacher;
import com.example.codingforkids.parent.ParentActivity;
import com.example.codingforkids.parent.ViewLessonImageActivity;
import com.example.codingforkids.teacher.TeacherActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LessonsLearnActivity extends AppCompatActivity {

    String strID, strTitle, strDesc, strLevel, strLang, strTopic, strImage, strSound="-", strVideo, strLessons, strLearned, strLessonID;
    TextInputLayout txtTitle, txtDesc, txtTopic;
    AppCompatButton btnImage, btnVideo, btnPlay, btnStop, btnExercise;
    ImageView imgBack;
    TextView txtFile, txtLessons;
    private MediaPlayer mediaPlayer;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lessons_learn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //// read lesson data
        strLang = getIntent().getStringExtra("lang");
        strID = LoginActivity.child.getChildID();

        imgBack = findViewById(R.id.imgBack);
        btnImage = findViewById(R.id.btnImage);
        btnVideo = findViewById(R.id.btnVideo);
        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDesc);
        txtTopic = findViewById(R.id.txtTopic);
        txtFile = findViewById(R.id.txtFile);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);
        btnExercise = findViewById(R.id.btnExercise);
        progress = findViewById(R.id.progress);
        txtLessons = findViewById(R.id.txtLessons);

        new GetChildLesson().execute();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChooseLanguageActivity.class);
                startActivity(i);
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewLessonImageChildActivity.class);
                i.putExtra("img", strImage);
                startActivity(i);
            }
        });
        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SolveExerciseActivity.class);
                i.putExtra("lessonID", strLessonID);
                i.putExtra("lang", strLang);
                startActivity(i);
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ViewLessonVideoChildActivity.class);
                in.putExtra("video", strVideo);
                startActivity(in);
            }
        });

        mediaPlayer = new MediaPlayer();
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

    class GetChildLesson extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);//progress bar is visible
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("strID", strID));
                parameters.add(new BasicNameValuePair("lang", strLang));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_get_child_lesson_link, "POST", parameters);
                String[] response_data;
                success = parser.getInt("success");
                if (success == 1) {
                    response_data = parser.getString("message").split("###");
                    strTitle = response_data[0];
                    strDesc = response_data[1];
                    strTopic = response_data[2];
                    strImage = response_data[3];
                    strVideo = response_data[4];
                    strSound = response_data[5];
                    strLessons = response_data[6];
                    strLearned = response_data[7];
                    strLevel = response_data[8];
                    strLessonID = response_data[9];
                    return  "Success" ;
                }else {
                    return parser.getString("message");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_SHORT).show();
            txtTitle.getEditText().setText(strTitle);
            txtDesc.getEditText().setText(strDesc);
            txtTopic.getEditText().setText(strTopic);
            Double percent = (Double.parseDouble(strLearned)/ Double.parseDouble(strLessons)) * 100;
            int int_percent = percent.intValue();
            txtLessons.setText(strLevel + " - (" + strLearned + "/" + strLessons + ") - ("+ int_percent + "%)");
            progress.setProgress(int_percent);
            if(!strSound.equals("-")){
                try {
                    txtFile.setText("Lesson" + strID + ".mp3");
                    btnPlay.setEnabled(true);
                    btnStop.setEnabled(true);
                    mediaPlayer.setDataSource(strSound);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(mp -> btnPlay.setEnabled(true));
                } catch (Exception e) {
                    Log.e("MediaPlayer", "Error initializing MediaPlayer", e);
                }
            }
        }
    }
}