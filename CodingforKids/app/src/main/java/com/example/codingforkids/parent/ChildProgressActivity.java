package com.example.codingforkids.parent;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codingforkids.JsonParser;
import com.example.codingforkids.Links;
import com.example.codingforkids.R;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChildProgressActivity extends AppCompatActivity {

    String strID, strName,strLevel, strScore, strLessons;
    TextInputLayout txtName, txtLevel, txtScore, txtLessons;
    AppCompatButton btnSave;
    ImageView imgBack;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    boolean found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_progress);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        strID = getIntent().getStringExtra("id");
        strName = getIntent().getStringExtra("name");

        btnSave = findViewById(R.id.btnSave);
        progress = findViewById(R.id.progress);
        imgBack = findViewById(R.id.imgBack);
        txtName = findViewById(R.id.txtName);
        txtScore = findViewById(R.id.txtScore);
        txtLevel = findViewById(R.id.txtLevel);
        txtLessons = findViewById(R.id.txtLessons);

        txtName.getEditText().setText(strName);
        new ReadProgress().execute();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ManageChildActivity.class);
                startActivity(i);
            }
        });
    }

    class ReadProgress extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("id", strID));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_progress_child_link, "POST", parameters);
                success = parser.getInt("success");
                String[] data = parser.getString("message").split("###");
                if (success == 1) {
                    found = true;
                    strLevel = data[0];
                    strScore = data[1];
                    strLessons = data[2];
                    return  "Child Progress Found";
                }else{
                    found = false;
                    return  parser.getString("message");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            progress.setVisibility(View.INVISIBLE);
            if(found){
                txtLevel.getEditText().setText(strLevel);
                txtScore.getEditText().setText(strScore);
                txtLessons.getEditText().setText(strLessons);
            }else{
                txtLevel.getEditText().setText("not found");
                txtScore.getEditText().setText("not found");
                txtLessons.getEditText().setText("not found");
            }
            Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_SHORT).show();
        }
    }


}