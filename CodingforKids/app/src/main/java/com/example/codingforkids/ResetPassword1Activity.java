package com.example.codingforkids;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
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

import com.example.codingforkids.admin.AdminActivity;
import com.example.codingforkids.child.ChildActivity;
import com.example.codingforkids.models.Admin;
import com.example.codingforkids.models.Child;
import com.example.codingforkids.models.Parent;
import com.example.codingforkids.models.Teacher;
import com.example.codingforkids.parent.ParentActivity;
import com.example.codingforkids.teacher.TeacherActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResetPassword1Activity extends AppCompatActivity {

    TextInputLayout txtEmail, txtUser;
    ImageView imgBack;
    AppCompatButton btnSubmit;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    String strEmail,strUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtEmail = findViewById(R.id.txtEmail);
        txtUser = findViewById(R.id.txtUser);
        imgBack = findViewById(R.id.imgBack);
        btnSubmit = findViewById(R.id.btnLogin);
        progress = findViewById(R.id.progress);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtEmail.getEditText().getText().toString().trim().isEmpty()){
                    txtEmail.setError(getString(R.string.email_required));
                    txtEmail.requestFocus();
                    return;
                }
                if(txtUser.getEditText().getText().toString().trim().isEmpty()){
                    txtUser.setError(getString(R.string.user_required));
                    txtUser.requestFocus();
                    return;
                }
                strEmail = txtEmail.getEditText().getText().toString().trim();
                strUser = txtUser.getEditText().getText().toString().trim();
                new ResetProcess().execute();
            }
        });
    }
    class ResetProcess extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("email", strEmail));
                parameters.add(new BasicNameValuePair("user", strUser));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_reset_pass1_link, "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ResetPassword2Activity.class);
                    intent.putExtra("email", strEmail);
                    intent.putExtra("type", "admin");
                    startActivityForResult(intent, 100);
                    return  "Success Admin" ;
                }else  if (success == 2) {
                    Intent intent = new Intent(getApplicationContext(), ResetPassword2Activity.class);
                    intent.putExtra("email", strEmail);
                    intent.putExtra("type", "parent");
                    startActivityForResult(intent, 100);
                    return  "Success" ;
                }else  if (success == 3) {
                    Intent intent = new Intent(getApplicationContext(), ResetPassword2Activity.class);
                    intent.putExtra("email", strEmail);
                    intent.putExtra("type", "teacher");
                    startActivityForResult(intent, 100);
                    return "Success";
                }else if (success == 4) {
                    Intent intent = new Intent(getApplicationContext(), ResetPassword2Activity.class);
                    intent.putExtra("email", strEmail);
                    intent.putExtra("type", "child");
                    startActivityForResult(intent, 100);
                    return "Success";
                }else {
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