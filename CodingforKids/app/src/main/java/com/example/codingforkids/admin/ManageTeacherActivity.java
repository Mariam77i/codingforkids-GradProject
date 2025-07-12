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
import com.example.codingforkids.R;
import com.example.codingforkids.adapters.ManageParentAdapter;
import com.example.codingforkids.adapters.ManageTeacherAdapter;
import com.example.codingforkids.models.Parent;
import com.example.codingforkids.models.Teacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageTeacherActivity extends AppCompatActivity {

    List<Teacher> teachersData;
    RecyclerView recyclerView;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_teacher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        imgBack = findViewById(R.id.imgBack);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        teachersData = new ArrayList<>();

        //to display it in recyclerview
        loadTeachers();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadTeachers() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Links.url_view_teacher_link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject teacher = array.getJSONObject(i);
                                teachersData.add(new Teacher(
                                        teacher.getString("id"),
                                        teacher.getString("name"),
                                        teacher.getString("email"),
                                        teacher.getString("username"),
                                        teacher.getString("pass")
                                ));
                            }
                            ManageTeacherAdapter adapter = new ManageTeacherAdapter(ManageTeacherActivity.this, teachersData);
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