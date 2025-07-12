package com.example.codingforkids.parent;

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
import com.example.codingforkids.adapters.ManageLessonAdapter;
import com.example.codingforkids.adapters.ViewLessonsAdapter;
import com.example.codingforkids.admin.AddLessonActivity;
import com.example.codingforkids.admin.AdminActivity;
import com.example.codingforkids.admin.ManageLessonsActivity;
import com.example.codingforkids.models.Lesson;
import com.example.codingforkids.teacher.AddLessonRequestActivity;
import com.example.codingforkids.teacher.TeacherActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewTutorialsActivity extends AppCompatActivity {
    List<Lesson> lessonsData;
    RecyclerView recyclerView;
    ImageView imgBack, imgRequestAdd;
    String strCome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_tutorials);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        strCome = getIntent().getStringExtra("come");

        recyclerView = findViewById(R.id.recyclerView);
        imgBack = findViewById(R.id.imgBack);
        imgRequestAdd = findViewById(R.id.imgRequestAdd);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lessonsData = new ArrayList<>();
        if(strCome.equals("1")){
            imgRequestAdd.setVisibility(View.VISIBLE);
        }else if(strCome.equals("2")){
            imgRequestAdd.setVisibility(View.INVISIBLE);
        }

        //to display it in recyclerview
        loadLessons();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(strCome.equals("1")){
                    Intent i = new Intent(getApplicationContext(), ParentActivity.class);
                    startActivity(i);
                }else if(strCome.equals("2")){
                    Intent i = new Intent(getApplicationContext(), TeacherActivity.class);
                    startActivity(i);
                }
            }
        });
        imgRequestAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(strCome.equals("1")){
                    Intent i = new Intent(getApplicationContext(), AddLessonRequestActivity.class);
                    startActivity(i);
                }else if(strCome.equals("2")){
                    Intent i = new Intent(getApplicationContext(), AddLessonActivity.class);
                    i.putExtra("come", "2");
                    startActivity(i);
                }
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
                            ViewLessonsAdapter adapter = new ViewLessonsAdapter(ViewTutorialsActivity.this, lessonsData);
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