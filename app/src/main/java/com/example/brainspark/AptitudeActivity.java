package com.example.brainspark;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AptitudeActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.aptitude_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aptitude), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnTips = findViewById(R.id.btnTips);

        btnTips.setOnClickListener(v -> {
            Intent intent = new Intent(AptitudeActivity.this, TipsActivity.class);
            startActivity(intent);
        });
        Button mockTestButton = findViewById(R.id.btnMockTest);

        mockTestButton.setOnClickListener(v -> {
            Intent intent = new Intent(AptitudeActivity.this, Aptitude_question.class);
            startActivity(intent);
        });
        Button btnTopic = findViewById(R.id.btnTopicWise);

        btnTopic.setOnClickListener(v -> {
            Intent intent = new Intent(AptitudeActivity.this, TopicWiseAptitude.class);
            startActivity(intent);
        });
        Button btnHistory = findViewById(R.id.btnHistory);

        btnHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, activity_history.class));
        });


    }
}

