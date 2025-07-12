package com.example.codingforkids.parent;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateChildActivity extends AppCompatActivity {

    TextInputLayout txtName, txtEmail, txtUser, txtPass, txtAge;
    AppCompatButton btnSave;
    Spinner txtGender, txtEducation;
    ProgressBar progress;
    ImageView imgBack;
    JsonParser jsonParser = new JsonParser();
    String strID, strName, strEmail, strUser, strGender, strPass, strEducation, strAge, strParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_child);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ///// retreive child data
        strID = getIntent().getStringExtra("id");
        strName = getIntent().getStringExtra("name");
        strEmail = getIntent().getStringExtra("email");
        strUser = getIntent().getStringExtra("username");
        strGender = getIntent().getStringExtra("gender");
        strPass = getIntent().getStringExtra("pass");
        strEducation = getIntent().getStringExtra("edu");
        strAge = getIntent().getStringExtra("age");
        strParent = getIntent().getStringExtra("parentID");

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtUser = findViewById(R.id.txtUser);
        txtGender = findViewById(R.id.txtGender);
        txtAge = findViewById(R.id.txtAge);
        txtEducation = findViewById(R.id.txtEducation);
        txtPass = findViewById(R.id.txtPass);
        btnSave = findViewById(R.id.btnSave);
        progress = findViewById(R.id.progress);
        imgBack = findViewById(R.id.imgBack);

        //// display data
        txtName.getEditText().setText(strName);
        txtEmail.getEditText().setText(strEmail);
        txtUser.getEditText().setText(strUser);
        txtPass.getEditText().setText(strPass);
        txtAge.getEditText().setText(strAge);
        for (int j = 0; j < txtGender.getCount(); j++) {
            if (txtGender.getItemAtPosition(j).equals(strGender)) {
                txtGender.setSelection(j);
                break;
            }
        }
        for (int j = 0; j < txtEducation.getCount(); j++) {
            if (txtEducation.getItemAtPosition(j).equals(strEducation)) {
                txtEducation.setSelection(j);
                break;
            }
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ManageChildActivity.class);
                startActivity(i);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAge = txtAge.getEditText().getText().toString().trim();
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
                if(txtUser.getEditText().getText().toString().trim().isEmpty()){
                    txtUser.setError(getString(R.string.user_required));
                    txtUser.requestFocus();
                    return;
                }
                if(txtPass.getEditText().getText().toString().trim().isEmpty()){
                    txtPass.setError(getString(R.string.pass_wrong));
                    txtPass.requestFocus();
                    return;
                }
                if(txtAge.getEditText().getText().toString().trim().isEmpty()){
                    txtAge.setError(getString(R.string.age_error));
                    txtAge.requestFocus();
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
                if(Integer.parseInt(strAge) > 12 || Integer.parseInt(strAge) < 7){
                    txtAge.setError(getString(R.string.wrong_age));
                    txtAge.requestFocus();
                    return;
                }

                strName = txtName.getEditText().getText().toString().trim();
                strEmail = txtEmail.getEditText().getText().toString().trim();
                strUser = txtUser.getEditText().getText().toString().trim();
                strAge = txtAge.getEditText().getText().toString().trim();
                strGender = txtGender.getSelectedItem().toString().trim();
                strEducation = txtEducation.getSelectedItem().toString().trim();
                strPass = txtPass.getEditText().getText().toString().trim();
                new UpdateChild().execute();
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

    class UpdateChild extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("username", strUser));
                parameters.add(new BasicNameValuePair("pass", strPass));
                parameters.add(new BasicNameValuePair("gender", strGender));
                parameters.add(new BasicNameValuePair("age", strAge));
                parameters.add(new BasicNameValuePair("edu", strEducation));
                parameters.add(new BasicNameValuePair("childID", strID));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_update_child_link, "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ManageChildActivity.class);
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