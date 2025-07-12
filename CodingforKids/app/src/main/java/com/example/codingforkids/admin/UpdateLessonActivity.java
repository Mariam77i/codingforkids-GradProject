package com.example.codingforkids.admin;

import android.content.Intent;
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
import com.example.codingforkids.teacher.ManageTeacherLessonsActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateLessonActivity extends AppCompatActivity {
    String strTitle, strDesc, strTopic, strLanguage, strLevel, strID, strImage="-", strSound="-", strVideo="-";
    Spinner spinLang, spinLevel;
    TextInputLayout txtTitle, txtDesc, txtTopic;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    AppCompatButton btnSave;
    ImageView imgBack;
    String strUser, strType, strCome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_lesson);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        strType = LoginActivity.type;
        if(strType.equals("admin")){
            strCome = "1";
        }else if(strType.equals("teacher")){
            strCome = "2";
        }
        strID = getIntent().getStringExtra("id");
        strTitle = getIntent().getStringExtra("title");
        strLanguage = getIntent().getStringExtra("lang");
        strLevel = getIntent().getStringExtra("level");
        strTopic = getIntent().getStringExtra("topic");
        strDesc = getIntent().getStringExtra("desc");
        strImage = getIntent().getStringExtra("img");
        strSound = getIntent().getStringExtra("sound");
        strVideo = getIntent().getStringExtra("video");

        txtTitle = (TextInputLayout)findViewById(R.id.txtTitle);
        txtDesc = (TextInputLayout)findViewById(R.id.txtDesc);
        txtTopic = (TextInputLayout)findViewById(R.id.txtTopic);
        spinLang = (Spinner) findViewById(R.id.txtLang);
        spinLevel = (Spinner) findViewById(R.id.txtLevel);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        progress = (ProgressBar) findViewById(R.id.progress);
        btnSave = (AppCompatButton)findViewById(R.id.btnSave);

        txtTitle.getEditText().setText(strTitle);
        txtTopic.getEditText().setText(strTopic);
        txtDesc.getEditText().setText(strDesc);
        for (int j = 0; j < spinLang.getCount(); j++) {
            if (spinLang.getItemAtPosition(j).equals(strLanguage)) {
                spinLang.setSelection(j);
                break;
            }
        }
        for (int j = 0; j < spinLevel.getCount(); j++) {
            if (spinLevel.getItemAtPosition(j).equals(strLevel)) {
                spinLevel.setSelection(j);
                break;
            }
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(strType.equals("admin")){
                                               Intent in = new Intent(getApplicationContext(), ManageLessonsActivity.class);
                                               startActivity(in);
                                           }else{
                                               Intent in = new Intent(getApplicationContext(), ManageTeacherLessonsActivity.class);
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
                       new UpdateLesson().execute();
                   }
               }
           }
        );
    }
    class UpdateLesson extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("lessonID", strID));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_update_lesson_link, "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    String lessonID = parser.getString("message");
                    Intent intent = new Intent(getApplicationContext(), AddLessonImageActivity.class);
                    intent.putExtra("lessonID", strID);
                    intent.putExtra("img", strImage);
                    intent.putExtra("sound", strSound);
                    intent.putExtra("video", strVideo);
                    intent.putExtra("come", strCome);
                    startActivityForResult(intent, 100);
                    return  parser.getString("Lesson updated, Next change image") ;
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