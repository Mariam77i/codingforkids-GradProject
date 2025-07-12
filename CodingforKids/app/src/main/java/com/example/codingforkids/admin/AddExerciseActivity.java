package com.example.codingforkids.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.example.codingforkids.teacher.ManageTeacherLessonsActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AddExerciseActivity extends AppCompatActivity {

    String strQtn, strAnswer, strLanguage;
    String lessonID, strType, strID, strCome;
    Spinner spinLang, txtAnswer;
    TextInputLayout txtQtn;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    AppCompatButton btnSave, btnOption1, btnOption2, btnOption3;
    ImageView imgBack, imgOption1, imgOption2, imgOption3;

    boolean choose_image1 = false, choose_image2 = false, choose_image3 = false;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap1, bitmap2, bitmap3;
    Uri filePath1, filePath2, filePath3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lessonID = getIntent().getStringExtra("id");
        strType = LoginActivity.type;
        if(strType.equals("admin")){
            strID = "admin";
        }else{
            strID = LoginActivity.teacher.getTeacherID();
        }

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
                       if(choose_image1 == true && choose_image2 == true && choose_image3 == true){
                           AddExercise();
                       }else{
                           Toast.makeText(getApplicationContext(), getString(R.string.choose_image), Toast.LENGTH_SHORT).show();
                       }
                   }
               }
           }
        );
        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            if(choose_image1 == false){
                filePath1 = data.getData();
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath1);
                    imgOption1.setImageBitmap(bitmap1);
                    choose_image1 = true;
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Choose Image", Toast.LENGTH_LONG).show();
                }
            }else if(choose_image2 == false && choose_image1 == true){
                filePath2 = data.getData();
                try {
                    bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
                    imgOption2.setImageBitmap(bitmap2);
                    choose_image2 = true;
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Choose Image", Toast.LENGTH_LONG).show();
                }
            } else if(choose_image3 == false && choose_image1 == true && choose_image2 == true){
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
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void AddExercise(){
        class UploadExercise extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddExerciseActivity.this, "Adding Exercise ..", null,true,true);
            }
            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Bitmap... params) {
                int success;
                String uploadImage1 = getStringImage(bitmap1);
                String uploadImage2 = getStringImage(bitmap2);
                String uploadImage3 = getStringImage(bitmap3);
                HashMap<String,String> data = new HashMap<>();
                data.put("image1", uploadImage1);
                data.put("image2", uploadImage2);
                data.put("image3", uploadImage3);
                data.put("qtn", strQtn);
                data.put("answer", strAnswer);
                data.put("lng", strLanguage);
                data.put("lessonID", lessonID);
                data.put("userID", strID);
                String result = rh.sendPostRequest(Links.url_add_exercise_link,data);
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
}