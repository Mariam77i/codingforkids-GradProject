package com.example.codingforkids.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.example.codingforkids.teacher.TeacherActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddLessonActivity extends AppCompatActivity {

    String strTitle, strDesc, strTopic, strLanguage, strLevel;
    Spinner spinLang, spinLevel;
    TextInputLayout txtTitle, txtDesc, txtTopic;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    AppCompatButton btnSave;
    ImageView imgBack;
    String strCome, strID = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_lesson);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        strCome = getIntent().getStringExtra("come");
        if(strCome.equals("2")){
            strID = LoginActivity.teacher.getTeacherID();
        }else{
            strID = "admin";
        }

        txtTitle = (TextInputLayout)findViewById(R.id.txtTitle);
        txtDesc = (TextInputLayout)findViewById(R.id.txtDesc);
        txtTopic = (TextInputLayout)findViewById(R.id.txtTopic);
        spinLang = (Spinner) findViewById(R.id.txtLang);
        spinLevel = (Spinner) findViewById(R.id.txtLevel);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        progress = (ProgressBar) findViewById(R.id.progress);
        btnSave = (AppCompatButton)findViewById(R.id.btnSave);

        imgBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(strCome.equals("1")){
                                               Intent in = new Intent(getApplicationContext(), ManageLessonsActivity.class);
                                               startActivity(in);
                                           }else{
                                               Intent in = new Intent(getApplicationContext(), TeacherActivity.class);
                                               startActivity(in);
                                           }
                                       }
                                   }
        );
        btnSave.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          if (txtTitle.getEditText().getText().toString().equals("")) {
                                              txtTitle.getEditText().setError(getString(R.string.title_required));
                                              txtTitle.requestFocus();
                                              return;
                                          }
                                          if (txtDesc.getEditText().getText().toString().equals("")) {
                                              txtDesc.getEditText().setError(getString(R.string.desc_required));
                                              txtDesc.requestFocus();
                                              return;
                                          }
                                          if (txtTopic.getEditText().getText().toString().equals("")) {
                                              txtTopic.getEditText().setError(getString(R.string.topic_required));
                                              txtTopic.requestFocus();
                                              return;
                                          }
                                          if (spinLang.getSelectedItem().toString().equals("Choose Programming Language") ||
                                                  spinLang.getSelectedItem().toString().equals("اختر لغة البرمجة")) {
                                              Toast.makeText(getApplicationContext(), getString(R.string.lang_required), Toast.LENGTH_LONG).show();
                                          }
                                          if (spinLevel.getSelectedItem().toString().equals("Choose Lesson Level") ||
                                                  spinLevel.getSelectedItem().toString().equals("اختر مستوى الدرس")) {
                                              Toast.makeText(getApplicationContext(), getString(R.string.level_required), Toast.LENGTH_LONG).show();
                                          }
                                          else {
                                              strTitle = txtTitle.getEditText().getText().toString().trim();
                                              strDesc = txtDesc.getEditText().getText().toString().trim();
                                              strTopic = txtTopic.getEditText().getText().toString().trim();
                                              strLanguage = spinLang.getSelectedItem().toString();
                                              strLevel = spinLevel.getSelectedItem().toString();
                                              new AddLesson().execute();
                                          }
                                      }
                                  }
        );
    }
    class AddLesson extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("title", strTitle));
                parameters.add(new BasicNameValuePair("desc", strDesc));
                parameters.add(new BasicNameValuePair("topic", strTopic));
                parameters.add(new BasicNameValuePair("lang", strLanguage));
                parameters.add(new BasicNameValuePair("level", strLevel));
                parameters.add(new BasicNameValuePair("strID", strID));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_add_lesson_link, "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    String lessonID = parser.getString("message");
                    Intent intent = new Intent(getApplicationContext(), AddLessonImageActivity.class);
                    intent.putExtra("lessonID", lessonID);
                    intent.putExtra("img", "-");
                    intent.putExtra("sound", "-");
                    intent.putExtra("video", "-");
                    intent.putExtra("come", strCome);
                    startActivityForResult(intent, 100);
                    return  parser.getString("New Lesson added, Next choose image") ;
                }
                else {
                    return parser.getString("message");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_SHORT).show();
        }
    }
}