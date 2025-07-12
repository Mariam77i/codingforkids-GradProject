package com.example.codingforkids.child;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.codingforkids.R;

public class GameActivity extends AppCompatActivity {
    ImageView imgGIF;
    AppCompatButton btnGameBlocks, btnGameConcepts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgGIF = findViewById(R.id.gifImageView);
        btnGameBlocks = findViewById(R.id.btnGameBlocks);
        btnGameConcepts = findViewById(R.id.btnGameConcepts);

        Glide.with(this)
                .asGif()
                .load(R.drawable.child_lay)
                .into(imgGIF);   // Use the drawable resource ID
        btnGameBlocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CodeGameActivity.class);
                startActivity(i);
            }
        });
        btnGameConcepts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConceptsGameLevelActivity.class);
                startActivity(i);
            }
        });
    }

}