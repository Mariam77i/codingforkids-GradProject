package com.example.codingforkids.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codingforkids.Links;
import com.example.codingforkids.LoginActivity;
import com.example.codingforkids.R;
import com.example.codingforkids.adapters.ManageExerciseAdapter;
import com.example.codingforkids.adapters.ManageLessonAdapter;
import com.example.codingforkids.models.Exercise;
import com.example.codingforkids.models.Lesson;
import com.example.codingforkids.teacher.TeacherActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageExercisesActivity extends AppCompatActivity {

    List<Exercise> exercisesData;
    RecyclerView recyclerView;
    ImageView imgBack;
    String strType, strUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_exercises);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        strType = LoginActivity.type;
        if(strType.equals("admin")){
            strUser = "admin";
        }else{
            strUser = LoginActivity.teacher.getTeacherID();
        }
        recyclerView = findViewById(R.id.recyclerView);
        imgBack = findViewById(R.id.imgBack);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exercisesData = new ArrayList<>();

        //to display it in recyclerview
        loadExercises();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(strType.equals("admin")){
                    Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), TeacherActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void loadExercises() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Links.url_view_exercises_link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject exercise = array.getJSONObject(i);
                                if(strType.equals("teacher") && exercise.getString("userID").equals(strUser)){
                                        exercisesData.add(new Exercise(
                                        exercise.getString("id"),
                                        exercise.getString("answer"),
                                        exercise.getString("lesson_title"),
                                        exercise.getString("lesson_id"),
                                        exercise.getString("lang"),
                                        exercise.getString("title"),
                                        exercise.getString("img1"),
                                        exercise.getString("img2"),
                                        exercise.getString("img3"),
                                        exercise.getString("userID")
                                    ));
                                }else if(strType.equals("admin")){
                                        exercisesData.add(new Exercise(
                                        exercise.getString("id"),
                                        exercise.getString("answer"),
                                        exercise.getString("lesson_title"),
                                        exercise.getString("lesson_id"),
                                        exercise.getString("lang"),
                                        exercise.getString("title"),
                                        exercise.getString("img1"),
                                        exercise.getString("img2"),
                                        exercise.getString("img3"),
                                        exercise.getString("userID")
                                    ));
                                }
                            }
                            ManageExerciseAdapter adapter = new ManageExerciseAdapter(ManageExercisesActivity.this, exercisesData);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}