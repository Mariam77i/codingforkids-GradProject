package com.example.codingforkids;

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

import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPassword2Activity extends AppCompatActivity {

    TextInputLayout txtPass, txtConfirmPass;
    ImageView imgBack;
    AppCompatButton btnSave;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    String strEmail,strType, strPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        strEmail = getIntent().getStringExtra("email");
        strType = getIntent().getStringExtra("type");

        txtPass = findViewById(R.id.txtPass);
        txtConfirmPass = findViewById(R.id.txtConfirmPass);
        imgBack = findViewById(R.id.imgBack);
        btnSave = findViewById(R.id.btnSave);
        progress = findViewById(R.id.progress);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// check if the user types the new password and confirm pass
                if(txtPass.getEditText().getText().toString().trim().isEmpty()){
                    txtPass.setError(getString(R.string.pass_wrong));
                    txtPass.requestFocus();
                    return;
                }
                if(txtPass.getEditText().getText().toString().trim().length() < 6 || txtPass.getEditText().getText().toString().trim().length() > 20){
                    txtPass.setError(getString(R.string.password_length));
                    txtPass.requestFocus();
                    return;
                }
                if(txtConfirmPass.getEditText().getText().toString().trim().isEmpty()){
                    txtConfirmPass.setError(getString(R.string.confirm_pass));
                    txtConfirmPass.requestFocus();
                    return;
                }
                if(!txtConfirmPass.getEditText().getText().toString().trim().equals(txtPass.getEditText().getText().toString().trim())){
                    txtPass.setError(getString(R.string.pass_mismatch));
                    txtPass.requestFocus();
                    return;
                }
                if(!isValidPassword(txtPass.getEditText().getText().toString().trim())){
                    txtPass.setError(getString(R.string.pass_not_valid));
                    txtPass.requestFocus();
                    return;
                }
                strPass = txtPass.getEditText().getText().toString().trim();
                new ResetProcess().execute();
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
                parameters.add(new BasicNameValuePair("type", strType));
                parameters.add(new BasicNameValuePair("pass", strPass));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_reset_pass2_link, "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, 100);
                    return  "Success Reset Password" ;
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