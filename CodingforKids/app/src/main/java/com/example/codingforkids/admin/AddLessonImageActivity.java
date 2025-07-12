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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.codingforkids.Links;
import com.example.codingforkids.R;
import com.example.codingforkids.RequestHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class AddLessonImageActivity extends AppCompatActivity {

    String lesson_id, strImage, strSound, strVideo, strCome;
    ImageView imgImage;
    TextView txtESC;
    AppCompatButton btnUpload, btnSave;
    boolean choose_image = false;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_lesson_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lesson_id = getIntent().getStringExtra("lessonID");
        strImage = getIntent().getStringExtra("img");
        strSound = getIntent().getStringExtra("sound");
        strVideo = getIntent().getStringExtra("video");
        strCome = getIntent().getStringExtra("come");

        imgImage = findViewById(R.id.imgImage);
        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);
        txtESC = findViewById(R.id.txtESC);

        //// display the image /////
        new ViewImage(imgImage).execute(strImage);

        btnUpload.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             showFileChooser();
                                         }
                                     }
        );
        txtESC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), AddLessonSoundActivity.class);
                in.putExtra("lessonID", lesson_id);
                in.putExtra("sound", strSound);
                in.putExtra("video", strVideo);
                in.putExtra("come", strCome);
                startActivity(in);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choose_image){
                    AddImage();
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.choose_image), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgImage.setImageBitmap(bitmap);
                choose_image = true;
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Choose Image", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), PICK_IMAGE_REQUEST);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

    private void AddImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddLessonImageActivity.this, "Upload Image ..", null,true,true);
            }
            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Bitmap... params) {
                int success;
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                HashMap<String,String> data = new HashMap<>();
                data.put("image", uploadImage);
                data.put("lessonID", lesson_id);
                String result = rh.sendPostRequest(Links.url_add_lesson_image_link,data);
                if (result.contains("success")) {
                    Intent in = new Intent(getApplicationContext(), AddLessonSoundActivity.class);
                    in.putExtra("lessonID", lesson_id);
                    in.putExtra("sound", strSound);
                    in.putExtra("video", strVideo);
                    in.putExtra("come", strCome);
                    startActivity(in);
                    return "Success";
                }
                else
                {
                    return "Failed Upload";
                }
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
}