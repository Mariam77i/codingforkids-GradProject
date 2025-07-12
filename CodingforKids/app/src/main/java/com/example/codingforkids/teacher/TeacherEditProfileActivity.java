package com.example.codingforkids.teacher;

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

import com.example.codingforkids.JsonParser;
import com.example.codingforkids.Links;
import com.example.codingforkids.LoginActivity;
import com.example.codingforkids.R;
import com.example.codingforkids.parent.ParentActivity;
import com.example.codingforkids.parent.ParentEditProfileActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherEditProfileActivity extends AppCompatActivity {
    TextInputLayout txtEmail, txtName, txtPass;
    ImageView imgBack;
    AppCompatButton btnSave;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    String strName, strEmail, strPass, strID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btnSave = findViewById(R.id.btnSave);
        progress = findViewById(R.id.progress);
        imgBack = findViewById(R.id.imgBack);

        strName = LoginActivity.teacher.getTeacherName();
        strEmail = LoginActivity.teacher.getTeacherEmail();
        strPass = LoginActivity.teacher.getTeacherPass();
        strID = LoginActivity.teacher.getTeacherID();

        txtName.getEditText().setText(strName);
        txtEmail.getEditText().setText(strEmail);
        txtPass.getEditText().setText(strPass);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TeacherActivity.class);
                startActivity(i);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first check for inputs
                if(txtName.getEditText().getText().toString().trim().isEmpty()){
                    txtName.setError(getString(R.string.name_required));
                    txtName.requestFocus();
                    return;
                }
                if(txtEmail.getEditText().getText().toString().trim().isEmpty()){
                    txtEmail.setError(getString(R.string.email_required));
                    txtEmail.requestFocus();
                    return;
                }
                if(txtPass.getEditText().getText().toString().trim().isEmpty()){
                    txtPass.setError(getString(R.string.pass_wrong));
                    txtPass.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getEditText().getText().toString()).matches()){
                    txtEmail.setError(getString(R.string.email_not_valid));
                    txtEmail.requestFocus();
                    return;
                }
                if(txtPass.getEditText().getText().toString().trim().length() < 6 || txtPass.getEditText().getText().toString().trim().length() > 20){
                    txtPass.setError(getString(R.string.password_length));
                    txtPass.requestFocus();
                    return;
                }
                if(!isValidPassword(txtPass.getEditText().getText().toString().trim())){
                    txtPass.setError(getString(R.string.pass_not_valid));
                    txtPass.requestFocus();
                    return;
                }

                strName = txtName.getEditText().getText().toString().trim();
                strEmail = txtEmail.getEditText().getText().toString().trim();
                strPass = txtPass.getEditText().getText().toString().trim();
                new EditProfileProcess().execute();
            }
        });
    }

    boolean isValidPassword(String str)
    {
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern numberPattern = Pattern.compile("[0-9]");

        Matcher hasLowerCase = lowerCasePattern.matcher(str);
        Matcher hasUpperCase = upperCasePattern.matcher(str);
        Matcher hasNumber = numberPattern.matcher(str);
        return hasLowerCase.find() && hasUpperCase.find() && hasNumber.find();
    }

    class EditProfileProcess extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("name", strName));
                parameters.add(new BasicNameValuePair("email", strEmail));
                parameters.add(new BasicNameValuePair("pass", strPass));
                parameters.add(new BasicNameValuePair("id", strID));

                JSONObject parser = jsonParser.makeHttpRequest(Links.url_edit_teacher_link, "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    LoginActivity.parent.setParentEmail(strEmail);
                    LoginActivity.parent.setParentName(strName);
                    LoginActivity.parent.setParentPass(strPass);
                    Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
                    startActivityForResult(intent, 100);
                    return  parser.getString("message") ;
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