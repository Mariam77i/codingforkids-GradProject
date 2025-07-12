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
import com.example.codingforkids.parent.ParentActivity;
import com.example.codingforkids.parent.ViewTutorialsActivity;
import com.example.codingforkids.teacher.AddLessonRequestActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangeRequestStatusActivity extends AppCompatActivity {

    String strTitle, strDesc, strTopic, strLanguage, strID, strType;
    TextInputLayout txtTitle, txtDesc, txtTopic, txtLang, txtType;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    AppCompatButton btnAccept, btnReject;
    ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_request_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        strID = getIntent().getStringExtra("id");
        strTitle = getIntent().getStringExtra("title");
        strDesc = getIntent().getStringExtra("desc");
        strTopic = getIntent().getStringExtra("topic");
        strLanguage = getIntent().getStringExtra("lang");
        strType = getIntent().getStringExtra("type");

        txtTitle = (TextInputLayout)findViewById(R.id.txtTitle);
        txtDesc = (TextInputLayout)findViewById(R.id.txtDesc);
        txtTopic = (TextInputLayout)findViewById(R.id.txtTopic);
        txtLang = (TextInputLayout) findViewById(R.id.txtLang);
        txtType = (TextInputLayout) findViewById(R.id.txtType);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        progress = (ProgressBar) findViewById(R.id.progress);
        btnAccept = (AppCompatButton)findViewById(R.id.btnAccept);
        btnReject = (AppCompatButton)findViewById(R.id.btnReject);

        txtTitle.getEditText().setText(strTitle);
        txtDesc.getEditText().setText(strDesc);
        txtTopic.getEditText().setText(strTopic);
        txtLang.getEditText().setText(strLanguage);
        txtType.getEditText().setText(strType);

        imgBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent in = new Intent(getApplicationContext(), ManageRequestsActivity.class);
                                           startActivity(in);
                                       }
                                   }
        );
        btnAccept.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                           new AcceptRequest().execute();
                   }
               }
        );
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RejectRequest().execute();
            }
        });
    }
    class AcceptRequest extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("id", strID));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_accept_request_link, "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ManageRequestsActivity.class);
                    startActivity(intent);
                    return  parser.getString("Request is accepted") ;
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
    class RejectRequest extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("reqID", strID));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_delete_request_link, "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ManageRequestsActivity.class);
                    startActivity(intent);
                    return  parser.getString("Request is accepted") ;
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