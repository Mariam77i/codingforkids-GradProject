package com.example.codingforkids.parent;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codingforkids.LoginActivity;
import com.example.codingforkids.R;
import com.example.codingforkids.admin.AdminEditProfileActivity;
import com.example.codingforkids.child.ChildActivity;

public class ParentActivity extends AppCompatActivity {

    AppCompatButton btnLogout, btnEditProfile, btnAddChild, btnManageChild, btnViewProgress, btnViewLessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnLogout = findViewById(R.id.btnLogout);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnAddChild = findViewById(R.id.btnAddChild);
        btnManageChild = findViewById(R.id.btnManageChild);
        btnViewProgress = findViewById(R.id.btnViewProgress);
        btnViewLessons = findViewById(R.id.btnViewLessons);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ParentEditProfileActivity.class);
                startActivity(i);
            }
        });
        btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddChildActivity.class);
                startActivity(i);
            }
        });
        btnViewLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewTutorialsActivity.class);
                i.putExtra("come", "1");
                startActivity(i);
            }
        });
        btnManageChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ManageChildActivity.class);
                startActivity(i);
            }
        });
        btnViewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewProgressActivity.class);
                startActivity(i);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutConfirmation();
            }
        });
    }

    private void LogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.logout));
        builder.setMessage(getString(R.string.confirm_logout));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform logout action
                Intent intent = new Intent(ParentActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
        builder.setNegativeButton(getString(R.string.no), null);
        builder.show();
    }
}