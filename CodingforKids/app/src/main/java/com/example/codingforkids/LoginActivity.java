package com.example.codingforkids;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    TextView txtForget, txtRegister;
    TextInputLayout txtEmail, txtPass;
    AppCompatButton btnLogin;
    ProgressBar progress;
    public static Admin admin;
    public static Parent parent;
    public static Teacher teacher;
    public static Child child;
    public static String type;
    JsonParser jsonParser = new JsonParser();
    String  strEmail, strPass, strError = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtForget = findViewById(R.id.txtForget);
        txtRegister = findViewById(R.id.txtRegister);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        progress = findViewById(R.id.progress);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ResetPassword1Activity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckForm();
                if(strError.equals("no")) {
                    new LoginUser().execute();
                }
            }
        });
    }
    public void CheckForm(){
        // first check for inputs
        if (txtEmail.getEditText().getText().toString().trim().isEmpty()) {
            txtEmail.setError(getString(R.string.email_error));
            txtEmail.requestFocus();
            strError = "yes";
            return;
        }
        if (txtPass.getEditText().getText().toString().trim().isEmpty()) {
            txtPass.setError(getString(R.string.pass_wrong));
            txtPass.requestFocus();
            strError = "yes";
            return;
        }
        strEmail = txtEmail.getEditText().getText().toString().trim();
        strPass = txtPass.getEditText().getText().toString().trim();
    }

    class LoginUser extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);//progress bar is visible
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            String userID, fullName, userEmail, userName, userPass, userGender, userAge, userEdu, userParentID;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("email", strEmail));
                parameters.add(new BasicNameValuePair("pass", strPass));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_login_link, "POST", parameters);
                String[] response_data;
                success = parser.getInt("success");
                if (success == 1) {
                    response_data = parser.getString("message").split("###");
                    userID = response_data[0];
                    fullName = response_data[1];
                    userEmail = response_data[2];
                    userName = response_data[3];
                    userPass = response_data[4];
                    type = "admin";
                    admin = new Admin(userID,fullName,userEmail,userName, userPass);
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivityForResult(intent, 100);
                    return  "Success" ;
                }else  if (success == 2) {
                    response_data = parser.getString("message").split("###");
                    userID = response_data[0];
                    fullName = response_data[1];
                    userEmail = response_data[2];
                    userName = response_data[3];
                    userPass = response_data[4];
                    type = "parent";
                    parent = new Parent(userID,fullName,userEmail,userName,userPass);
                    Intent intent = new Intent(getApplicationContext(), ParentActivity.class);
                    startActivityForResult(intent, 100);
                    return "Success";
                }else  if (success == 3) {
                    response_data = parser.getString("message").split("###");
                    userID = response_data[0];
                    fullName = response_data[1];
                    userEmail = response_data[2];
                    userName = response_data[3];
                    userPass = response_data[4];
                    type = "teacher";
                    teacher = new Teacher(userID,fullName,userEmail,userName,userPass);
                    Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
                    startActivityForResult(intent, 100);
                    return "Success";
                }else if (success == 4) {
                    response_data = parser.getString("message").split("###");
                    userID = response_data[0];
                    fullName = response_data[1];
                    userEmail = response_data[2];
                    userName = response_data[3];
                    userPass = response_data[4];
                    userAge = response_data[5];
                    userGender = response_data[6];
                    userEdu = response_data[7];
                    userParentID = response_data[8];
                    type = "child";
                    child = new Child(userID,fullName,userEmail,userName,userPass,userAge,userGender,userEdu,userParentID);
                    Intent intent = new Intent(getApplicationContext(), ChildActivity.class);
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