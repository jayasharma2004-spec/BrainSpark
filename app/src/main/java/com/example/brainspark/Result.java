package com.example.brainspark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {
    TextView tvScore, tvRemark;
    Button btnTryAgain;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        tvScore = findViewById(R.id.tvScore);
        tvRemark = findViewById(R.id.tvRemark);
        btnTryAgain = findViewById(R.id.btnTryAgain);

        // Get score from Intent
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 10);

        tvScore.setText("Your Score: " + score + "/" + total);
        if (score == total) {
            tvRemark.setText("🏆 Excellent! Perfect Score!");
        } else if (score >= total * 0.7) {
            tvRemark.setText("👏 Great job! Keep it up!");
        } else if (score >= total * 0.4) {
            tvRemark.setText("👍 Not bad, practice more!");
        } else {
            tvRemark.setText("💪 Keep practicing, you’ll improve!");
        }

        btnTryAgain.setOnClickListener(v -> {
            Intent intent = new Intent(Result.this, Aptitude_question.class);
            startActivity(intent);
            finish();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.result), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}