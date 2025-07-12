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
import com.example.codingforkids.LoginActivity;
import com.example.codingforkids.R;
import com.example.codingforkids.adapters.ManageChildAdapter;
import com.example.codingforkids.adapters.ManageTeacherAdapter;
import com.example.codingforkids.admin.AdminActivity;
import com.example.codingforkids.admin.ManageTeacherActivity;
import com.example.codingforkids.models.Child;
import com.example.codingforkids.models.Teacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageChildActivity extends AppCompatActivity {
    List<Child> childsData;
    RecyclerView recyclerView;
    ImageView imgBack;
    String strID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_child);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        strID = LoginActivity.parent.getParentID();

        recyclerView = findViewById(R.id.recyclerView);
        imgBack = findViewById(R.id.imgBack);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        childsData = new ArrayList<>();

        //to display it in recyclerview
        loadChilds();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParentActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadChilds() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Links.url_view_child_link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject child = array.getJSONObject(i);
                                if(child.getString("parentID").equals(strID)){
                                    childsData.add(new Child(
                                            child.getString("id"),
                                            child.getString("name"),
                                            child.getString("email"),
                                            child.getString("username"),
                                            child.getString("pass"),
                                            child.getString("age"),
                                            child.getString("gender"),
                                            child.getString("edu"),
                                            child.getString("parentID")
                                    ));
                                }
                            }
                            ManageChildAdapter adapter = new ManageChildAdapter(ManageChildActivity.this, childsData);
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