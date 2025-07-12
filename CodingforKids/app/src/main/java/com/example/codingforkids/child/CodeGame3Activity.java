package com.example.codingforkids.child;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codingforkids.LoginActivity;
import com.example.codingforkids.R;

public class CodeGame3Activity extends AppCompatActivity {
    EditText txt1, txt2, txt3;
    AppCompatButton btnDone;
    ImageView imgBack;
    String childID, strScore;
    MediaPlayer correctSound;
    MediaPlayer incorrectSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_code_game3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        strScore = getIntent().getStringExtra("score");
        correctSound = MediaPlayer.create(this, R.raw.correct);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrect);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        btnDone = findViewById(R.id.btnDone);
        imgBack = findViewById(R.id.imgBack);

        childID = LoginActivity.child.getChildID();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(i);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt1.getText().toString().equals("") || txt2.getText().toString().equals("") || txt3.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Fill all Empty cells", Toast.LENGTH_LONG).show();
                    playIncorrectSound();
                }else if(!txt1.getText().toString().contains("for") || !txt2.getText().toString().contains("i++") || !txt3.getText().toString().contains("Yes")){
                    Toast.makeText(getApplicationContext(), "Wrong Answer", Toast.LENGTH_LONG).show();
                    playIncorrectSound();
                    int new_score = Integer.parseInt(strScore) + 0;
                    strScore = new_score + "";
                    Intent i = new Intent(getApplicationContext(), DisplayScoreActivity.class);
                    i.putExtra("score", strScore);
                    startActivity(i);
                }else if(txt1.getText().toString().contains("for") && txt2.getText().toString().contains("i++") && txt3.getText().toString().contains("Yes")){
                    Toast.makeText(getApplicationContext(), "Good Answer", Toast.LENGTH_LONG).show();
                    playCorrectSound();
                    int new_score = Integer.parseInt(strScore) + 5;
                    strScore = new_score + "";
                    Intent i = new Intent(getApplicationContext(), DisplayScoreActivity.class);
                    i.putExtra("score", strScore);
                    startActivity(i);
                }
            }
        });
    }
    private void playCorrectSound() {
        if (correctSound != null) {
            correctSound.start();
        }
    }
    private void playIncorrectSound() {
        if (incorrectSound != null) {
            incorrectSound.start();
        }
    }
    @Override protected void onDestroy() {
        super.onDestroy();
        if (correctSound != null) {
            correctSound.release();
            correctSound = null;
        }
        if (incorrectSound != null) {
            incorrectSound.release();
            incorrectSound = null;
        }
    }
}