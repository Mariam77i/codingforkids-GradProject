package com.example.codingforkids.child;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codingforkids.JsonParser;
import com.example.codingforkids.Links;
import com.example.codingforkids.LoginActivity;
import com.example.codingforkids.R;
import com.example.codingforkids.admin.AddLessonImageActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SolveExerciseActivity extends AppCompatActivity {

    String strLessonID, strExID, strExQuestion, strExOption1, strExOption2, strExOption3, strExAnswer, strLang, strID;
    JsonParser jsonParser = new JsonParser();
    TextInputLayout txtQuestion;
    ImageView imgBack;
    ImageButton btnAnswer1, btnAnswer2, btnAnswer3;
    MediaPlayer correctSound;
    MediaPlayer incorrectSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_solve_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        strID = LoginActivity.child.getChildID();
        strLessonID = getIntent().getStringExtra("lessonID");
        strLang = getIntent().getStringExtra("lang");

        correctSound = MediaPlayer.create(this, R.raw.correct);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrect);

        txtQuestion = findViewById(R.id.txtQuestion);
        imgBack = findViewById(R.id.imgBack);
        btnAnswer1 = findViewById(R.id.btnAnswer1);
        btnAnswer2 = findViewById(R.id.btnAnswer2);
        btnAnswer3 = findViewById(R.id.btnAnswer3);

        new GetExercise().execute();

        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(strExAnswer.equals("option1")){
                    playCorrectSound();
                    new SaveAnswer().execute();
                }else{
                    playIncorrectSound();
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.return_lesson);
                    builder.setMessage(R.string.confirm_return);
                    builder.setPositiveButton(R.string.return_lesson, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), LessonsLearnActivity.class);
                            i.putExtra("lang", strLang);
                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, null);
                    builder.show();
                }
            }
        });
        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(strExAnswer.equals("option2")){
                    playCorrectSound();
                    new SaveAnswer().execute();
                }else{
                    playIncorrectSound();
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.return_lesson);
                    builder.setMessage(R.string.confirm_return);
                    builder.setPositiveButton(R.string.return_lesson, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), LessonsLearnActivity.class);
                            i.putExtra("lang", strLang);
                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, null);
                    builder.show();
                }
            }
        });
        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(strExAnswer.equals("option3")){
                    playCorrectSound();
                    new SaveAnswer().execute();
                }else{
                    playIncorrectSound();
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.return_lesson);
                    builder.setMessage(R.string.confirm_return);
                    builder.setPositiveButton(R.string.return_lesson, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), LessonsLearnActivity.class);
                            i.putExtra("lang", strLang);
                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, null);
                    builder.show();
                }
            }
        });
    }
    private void playCorrectSound() {
        if (correctSound != null) {
            correctSound.start();
        }
    }
    private void playIncorrectSound() {
        if (incorrectSound != null) {
            incorrectSound.start();
        }
    }
    @Override protected void onDestroy() {
        super.onDestroy();
        if (correctSound != null) {
            correctSound.release();
            correctSound = null;
        }
        if (incorrectSound != null) {
            incorrectSound.release();
            incorrectSound = null;
        }
    }
        class GetExercise extends AsyncTask<String, String, String> {
            @Override
            public void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            public String doInBackground(String... args) {
                int success;
                try {
                    List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                    parameters.add(new BasicNameValuePair("strID", strID));
                    parameters.add(new BasicNameValuePair("lessonID", strLessonID));

                    JSONObject parser = jsonParser.makeHttpRequest(Links.url_get_child_exercise_link, "POST", parameters);
                    String[] response_data;
                    success = parser.getInt("success");
                    if (success == 1) {
                        response_data = parser.getString("message").split("###");
                        strExID = response_data[0];
                        strExQuestion = response_data[1];
                        strExOption1 = response_data[2];
                        strExOption2 = response_data[3];
                        strExOption3 = response_data[4];
                        strExAnswer = response_data[5];
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
                txtQuestion.getEditText().setText(strExQuestion);
                new ViewImage(btnAnswer1).execute(strExOption1);
                new ViewImage(btnAnswer2).execute(strExOption2);
                new ViewImage(btnAnswer3).execute(strExOption3);
            }
        }
    class SaveAnswer extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("strID", strID));
                parameters.add(new BasicNameValuePair("lessonID", strLessonID));
                parameters.add(new BasicNameValuePair("exID", strExID));
                parameters.add(new BasicNameValuePair("lang", strLang));

                JSONObject parser = jsonParser.makeHttpRequest(Links.url_save_child_exercise_link, "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    Intent i = new Intent(getApplicationContext(), LessonsLearnActivity.class);
                    i.putExtra("lang", strLang);
                    startActivity(i);
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
        }
    }


    private class ViewImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public ViewImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}