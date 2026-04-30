package com.example.brainspark;

import android.os.CountDownTimer;
import android.provider.Settings;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aptitude_question extends AppCompatActivity {

    private TextView tvTimer, tvQuestion;
    private RadioButton rb1, rb2, rb3, rb4;
    private RadioGroup radioGroup;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 15 * 60 * 1000;

    private FirebaseFirestore db;
    private List<QuestionModel> questionList = new ArrayList<>();

    private int currentIndex = 0;
    private int score = 0;

    // 🔥 Prevent multiple submissions
    private boolean isSubmitted = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aptitude_question);

        tvTimer = findViewById(R.id.tvTimer);
        tvQuestion = findViewById(R.id.tvQuestion);

        rb1 = findViewById(R.id.rbOption1);
        rb2 = findViewById(R.id.rbOption2);
        rb3 = findViewById(R.id.rbOption3);
        rb4 = findViewById(R.id.rbOption4);

        Button btnNext = findViewById(R.id.btnNext);
        radioGroup = findViewById(R.id.radioGroup);

        db = FirebaseFirestore.getInstance();

        startTimer();
        loadQuestions();

        btnNext.setOnClickListener(v -> checkAnswer());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aptitudequestion), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // ✅ Load Questions
    private void loadQuestions() {
        CollectionReference questionsRef = db.collection("aptitude_questions");

        questionsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();

                for (DocumentSnapshot doc : snapshot.getDocuments()) {
                    QuestionModel q = doc.toObject(QuestionModel.class);
                    questionList.add(q);
                }

                Collections.shuffle(questionList);

                if (questionList.size() > 8) {
                    questionList = questionList.subList(0, 8);
                }

                showQuestion();
            } else {
                Toast.makeText(this, "Failed to load questions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ Show Question
    private void showQuestion() {
        if (currentIndex < questionList.size()) {
            QuestionModel q = questionList.get(currentIndex);

            tvQuestion.setText(q.getQuestion());
            rb1.setText(q.getOption1());
            rb2.setText(q.getOption2());
            rb3.setText(q.getOption3());
            rb4.setText(q.getOption4());

            radioGroup.clearCheck();
        }
    }

    // ✅ Check Answer
    private void checkAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selected = findViewById(selectedId);
        String selectedAnswer = selected.getText().toString();

        if (selectedAnswer.equals(questionList.get(currentIndex).getAnswer())) {
            score++;
        }

        currentIndex++;

        if (currentIndex < questionList.size()) {
            showQuestion();
        } else {
            submitTest(); // 🔥 move to result
        }
    }

    // ✅ Timer
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;

                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                String timeFormatted = String.format("%02d:%02d", minutes, seconds);
                tvTimer.setText(timeFormatted);

                if (millisUntilFinished <= 60000) {
                    tvTimer.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");

                if (!isFinishing()) {
                    Toast.makeText(Aptitude_question.this, "Time's up!", Toast.LENGTH_SHORT).show();
                    submitTest();
                }
            }
        }.start();
    }

    // ✅ FINAL FIXED METHOD
    private void submitTest() {

        if (isSubmitted) return;
        isSubmitted = true;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // 👉 SHOW RESULT FIRST (IMPORTANT FIX)
        Intent intent = new Intent(Aptitude_question.this, Result.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questionList.size());
        startActivity(intent);
        finish();

        // 👉 SAVE DATA IN BACKGROUND (NO BLOCK)
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userId = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.ANDROID_ID
        );

        Map<String, Object> result = new HashMap<>();
        result.put("score", score);
        result.put("total", questionList.size());
        result.put("timestamp", System.currentTimeMillis());
        result.put("testType", "aptitude");
        result.put("userId", userId);

        db.collection("mock_results").add(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}