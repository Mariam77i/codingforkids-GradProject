package com.example.codingforkids.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.example.codingforkids.RequestHandler;
import com.example.codingforkids.child.ChildActivity;
import com.example.codingforkids.models.Admin;
import com.example.codingforkids.models.Child;
import com.example.codingforkids.models.Parent;
import com.example.codingforkids.models.Teacher;
import com.example.codingforkids.parent.ParentActivity;
import com.example.codingforkids.teacher.ManageTeacherLessonsActivity;
import com.example.codingforkids.teacher.TeacherActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateExerciseActivity extends AppCompatActivity {

    String strQtn, strAnswer, strLanguage, strImage1, strImage2, strImage3;
    String exerciseID, lessonID, strType;
    Spinner spinLang, txtAnswer;
    TextInputLayout txtQtn;
    ProgressBar progress;
    AppCompatButton btnSave, btnOption1, btnOption2, btnOption3;
    ImageView imgBack, imgOption1, imgOption2, imgOption3;
    JsonParser jsonParser = new JsonParser();

    boolean choose_image1 = false, choose_image2 = false, choose_image3 = false;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap1, bitmap2, bitmap3;
    Uri filePath1, filePath2, filePath3;
    int selected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lessonID = getIntent().getStringExtra("id");
        strType = LoginActivity.type;

        txtQtn = (TextInputLayout)findViewById(R.id.txtQtn);
        txtAnswer = (Spinner)findViewById(R.id.txtAnswer);
        spinLang = (Spinner) findViewById(R.id.txtLng);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        progress = (ProgressBar) findViewById(R.id.progress);
        btnSave = (AppCompatButton)findViewById(R.id.btnSave);
        imgOption1 = (ImageView) findViewById(R.id.imgOption1);
        imgOption2 = (ImageView) findViewById(R.id.imgOption2);
        imgOption3 = (ImageView) findViewById(R.id.imgOption3);
        btnOption1 = (AppCompatButton)findViewById(R.id.btnOption1);
        btnOption2 = (AppCompatButton)findViewById(R.id.btnOption2);
        btnOption3 = (AppCompatButton)findViewById(R.id.btnOption3);

        new GetExercise().execute();

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
                                           if (txtQtn.getEditText().getText().toString().equals("")) {
                                               txtQtn.getEditText().setError(getString(R.string.question_required));
                                               txtQtn.requestFocus();
                                               return;
                                           }
                                           if (txtAnswer.getSelectedItem().toString().equals("Choose Correct Answer") ||
                                                   spinLang.getSelectedItem().toString().equals("اختر لغة البرمجة")) {
                                               Toast.makeText(getApplicationContext(), getString(R.string.lang_required), Toast.LENGTH_LONG).show();
                                           }
                                           if (spinLang.getSelectedItem().toString().equals("Choose Programming Language") ||
                                                   spinLang.getSelectedItem().toString().equals("اختر الاجابة الصحيحة")) {
                                               Toast.makeText(getApplicationContext(), getString(R.string.correct_required), Toast.LENGTH_LONG).show();
                                           }
                                           else {
                                               strQtn = txtQtn.getEditText().getText().toString().trim();
                                               strAnswer = txtAnswer.getSelectedItem().toString();
                                               strLanguage = spinLang.getSelectedItem().toString();
                                               UpdateExercise();
                                           }
                                       }
                                   }
        );
        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 1;
                showFileChooser();
            }
        });
        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 2;
                showFileChooser();
            }
        });
        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 3;
                showFileChooser();
            }
        });
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if(selected ==1){
                filePath1 = data.getData();
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath1);
                    imgOption1.setImageBitmap(bitmap1);
                    choose_image1 = true;
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Choose Image", Toast.LENGTH_LONG).show();
                }
            }
            if(selected == 2){
                filePath2 = data.getData();
                try {
                    bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
                    imgOption2.setImageBitmap(bitmap2);
                    choose_image2 = true;
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Choose Image", Toast.LENGTH_LONG).show();
                }
            }
            if(selected == 3){
                filePath3 = data.getData();
                try {
                    bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath3);
                    imgOption3.setImageBitmap(bitmap3);
                    choose_image3 = true;
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Choose Image", Toast.LENGTH_LONG).show();
                }
            }
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
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void UpdateExercise(){
        class UploadExercise extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateExerciseActivity.this, "Updating Exercise ..", null,true,true);
            }
            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Bitmap... params) {
                String uploadImage1="", uploadImage2="", uploadImage3="";
                if(choose_image1 == true) {
                    uploadImage1 = getStringImage(bitmap1);
                }
                if(choose_image2 == true){
                    uploadImage2 = getStringImage(bitmap2);
                }
                if(choose_image3 == true){
                    uploadImage3 = getStringImage(bitmap3);
                }
                HashMap<String,String> data = new HashMap<>();
                if(choose_image1 == true){
                    data.put("image1", uploadImage1);
                }else{
                    data.put("image1", "-");
                }
                if(choose_image2 == true){
                    data.put("image2", uploadImage2);
                }else{
                    data.put("image2", "-");
                }
                if(choose_image3 == true){
                    data.put("image3", uploadImage3);
                }else{
                    data.put("image3", "-");
                }
                data.put("qtn", strQtn);
                data.put("answer", strAnswer);
                data.put("lng", strLanguage);
                data.put("exerciseID", exerciseID);
                String result = rh.sendPostRequest(Links.url_update_exercise_link,data);
                if (result.contains("success")) {
                    if(strType.equals("admin")){
                        Intent in = new Intent(getApplicationContext(), ManageLessonsActivity.class);
                        startActivity(in);
                    }else{
                        Intent in = new Intent(getApplicationContext(), ManageTeacherLessonsActivity.class);
                        startActivity(in);
                    }
                    return "Success";
                }
                else
                {
                    return "Failed Upload";
                }
            }
        }
        UploadExercise ui = new UploadExercise();
        ui.execute();
    }
    class GetExercise extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("lessonID", lessonID));
                JSONObject parser = jsonParser.makeHttpRequest(Links.url_GET_Exercise_link, "POST", parameters);
                String[] response_data;
                success = parser.getInt("success");
                if (success == 1) {
                    response_data = parser.getString("message").split("###");
                    exerciseID = response_data[0];
                    strQtn = response_data[1];
                    strImage1 = response_data[2];
                    strImage2 = response_data[3];
                    strImage3 = response_data[4];
                    strAnswer = response_data[5];
                    strLanguage = response_data[6];
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
            progress.setVisibility(View.INVISIBLE);
            txtQtn.getEditText().setText(strQtn);
            for (int j = 0; j < spinLang.getCount(); j++) {
                if (spinLang.getItemAtPosition(j).equals(strLanguage)) {
                    spinLang.setSelection(j);
                    break;
                }
            }
            for (int j = 0; j < txtAnswer.getCount(); j++) {
                if (txtAnswer.getItemAtPosition(j).equals(strAnswer)) {
                    txtAnswer.setSelection(j);
                    break;
                }
            }
            new ViewImage(imgOption1).execute(strImage1);
            new ViewImage(imgOption2).execute(strImage2);
            new ViewImage(imgOption3).execute(strImage3);
        }
    }
}