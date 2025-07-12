package com.example.codingforkids.teacher;

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
import com.example.codingforkids.R;
import com.example.codingforkids.adapters.ViewLessonsTeacherAdapter;
import com.example.codingforkids.models.Lesson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateLessonRequestActivity extends AppCompatActivity {
    List<Lesson> lessonsData;
    RecyclerView recyclerView;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_lesson_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);
        imgBack = findViewById(R.id.imgBack);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lessonsData = new ArrayList<>();

        //to display it in recyclerview
        loadLessons();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TeacherActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadLessons() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Links.url_view_lessons_link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject lesson = array.getJSONObject(i);
                                lessonsData.add(new Lesson(
                                        lesson.getString("id"),
                                        lesson.getString("title"),
                                        lesson.getString("lang"),
                                        lesson.getString("level"),
                                        lesson.getString("topic"),
                                        lesson.getString("desc"),
                                        lesson.getString("img"),
                                        lesson.getString("sound"),
                                        lesson.getString("video"),
                                        lesson.getString("strID")
                                ));
                            }
                            ViewLessonsTeacherAdapter adapter = new ViewLessonsTeacherAdapter(UpdateLessonRequestActivity.this, lessonsData);
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