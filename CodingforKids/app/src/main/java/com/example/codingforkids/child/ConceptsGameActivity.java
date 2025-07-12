package com.example.codingforkids.child;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.codingforkids.R;
import com.example.codingforkids.models.Question;

import java.util.ArrayList;
import java.util.List;

public class ConceptsGameActivity extends AppCompatActivity {

    ImageButton btnBlock1, btnBlock2, btnBlock3, btnBlock4;
    ImageView imgQtn, imgBack;
    private List<Question> questions = new ArrayList<>();
    Question new_question, item;
    int score = 0, current = 0, allowed_current = 1;
    int resourceId;
    MediaPlayer correctSound;
    MediaPlayer incorrectSound;
    String strLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_concepts_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        strLevel = getIntent().getStringExtra("level");
        if(strLevel.equals("1")) allowed_current = 6;
        else if(strLevel.equals("2")) allowed_current = 10;
        else if(strLevel.equals("3")) allowed_current = 18;

        correctSound = MediaPlayer.create(this, R.raw.correct);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrect);

       FillArray();

        btnBlock1 = findViewById(R.id.btnBlock1);
        btnBlock2 = findViewById(R.id.btnBlock2);
        btnBlock3 = findViewById(R.id.btnBlock3);
        btnBlock4 = findViewById(R.id.btnBlock4);
        imgQtn = findViewById(R.id.imgQtn);
        imgBack = findViewById(R.id.imgBack);
        DisplayRandomQtn();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(i);
            }
        });
        btnBlock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getqAnswer().equals("1")){
                    playCorrectSound();
                    score +=1;
                }else{
                    playIncorrectSound();
                }
                DisplayRandomQtn();

            }
        });
        btnBlock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getqAnswer().equals("2")){
                    playCorrectSound();
                    score +=1;
                }else{
                    playIncorrectSound();
                }
                DisplayRandomQtn();
            }
        });
        btnBlock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getqAnswer().equals("3")){
                    playCorrectSound();
                    score +=1;
                }else{
                    playIncorrectSound();
                }
                DisplayRandomQtn();
            }
        });
        btnBlock4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getqAnswer().equals("4")){
                    playCorrectSound();
                    score +=1;
                }else{
                    playIncorrectSound();
                }
                DisplayRandomQtn();
            }
        });
    }
    public void FillArray(){
        new_question = new Question("1", "q1", "q1_1", "q1_2","q1_3", "q1_4", "4" );
        questions.add(new_question);
        new_question = new Question("2", "q2", "q2_1", "q2_2","q2_3", "q2_4", "1" );
        questions.add(new_question);
        new_question = new Question("3", "q3", "q3_1", "q3_2","q3_3", "q3_4", "4" );
        questions.add(new_question);
        new_question = new Question("4", "q4", "q4_1", "q4_2","q4_3", "q4_4", "4" );
        questions.add(new_question);
        new_question = new Question("5", "q5", "q5_1", "q5_2","q5_3", "q5_4", "3" );
        questions.add(new_question);
        new_question = new Question("6", "q6", "q6_1", "q6_2","q6_3", "q6_4", "1" );
        questions.add(new_question);
        new_question = new Question("7", "q7", "q7_1", "q7_2","q7_3", "q7_4", "2" );
        questions.add(new_question);
        new_question = new Question("8", "q8", "q8_1", "q8_2","q8_3", "q8_4", "2" );
        questions.add(new_question);
        new_question = new Question("9", "q9", "q9_1", "q9_2","q9_3", "q9_4", "4" );
        questions.add(new_question);
        new_question = new Question("10", "q10", "q10_1", "q10_2","q10_3", "q10_4", "1" );
        questions.add(new_question);
        new_question = new Question("11", "q11", "q11_1", "q11_2","q11_3", "q11_4", "3" );
        questions.add(new_question);
        new_question = new Question("12", "q12", "q12_1", "q12_2","q12_3", "q12_4", "3" );
        questions.add(new_question);
        new_question = new Question("13", "q13", "q13_1", "q13_2","q13_3", "q13_4", "1" );
        questions.add(new_question);
        new_question = new Question("14", "q14", "q14_1", "q14_2","q14_3", "q14_4", "2" );
        questions.add(new_question);
        new_question = new Question("15", "q15", "q15_1", "q15_2","q15_3", "q15_4", "4" );
        questions.add(new_question);
        new_question = new Question("16", "q16", "q16_1", "q16_2","q16_3", "q16_4", "2" );
        questions.add(new_question);
        new_question = new Question("17", "q17", "q17_1", "q17_2","q17_3", "q17_4", "2" );
        questions.add(new_question);
        new_question = new Question("18", "q18", "q18_1", "q18_2","q18_3", "q18_4", "2" );
        questions.add(new_question);
    }
    public void DisplayRandomQtn(){
        if (!questions.isEmpty() && current < allowed_current) {
            int index = (int) (Math.random() * questions.size());
            item = questions.remove(index);
            resourceId = getResources().getIdentifier(item.getqTitle(), "drawable", getPackageName());
            imgQtn.setImageResource(resourceId);
            resourceId = getResources().getIdentifier(item.getqOption1(), "drawable", getPackageName());
            btnBlock1.setImageResource(resourceId);
            resourceId = getResources().getIdentifier(item.getqOption2(), "drawable", getPackageName());
            btnBlock2.setImageResource(resourceId);
            resourceId = getResources().getIdentifier(item.getqOption3(), "drawable", getPackageName());
            btnBlock3.setImageResource(resourceId);
            resourceId = getResources().getIdentifier(item.getqOption4(), "drawable", getPackageName());
            btnBlock4.setImageResource(resourceId);
            current +=1;
        } else {
            Intent i = new Intent(getApplicationContext(), DisplayScoreActivity.class);
            i.putExtra("score", score + "");
            startActivity(i);
        }
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