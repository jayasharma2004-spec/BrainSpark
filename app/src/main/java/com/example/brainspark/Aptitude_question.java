package com.example.brainspark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class Aptitude_question extends AppCompatActivity {
    private TextView tvQuestion;
    private RadioButton rb1, rb2, rb3, rb4;
    private RadioGroup radioGroup;

    private FirebaseFirestore db;
    private List<QuestionModel> questionList = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aptitude_question);
        tvQuestion = findViewById(R.id.tvQuestion);
        rb1 = findViewById(R.id.rbOption1);
        rb2 = findViewById(R.id.rbOption2);
        rb3 = findViewById(R.id.rbOption3);
        rb4 = findViewById(R.id.rbOption4);
        Button btnNext = findViewById(R.id.btnNext);
        radioGroup = findViewById(R.id.radioGroup);

        db = FirebaseFirestore.getInstance();

        loadQuestions();
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aptitudequestion), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void loadQuestions() {
        CollectionReference questionsRef = db.collection("aptitude_questions");
        questionsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                for (DocumentSnapshot doc : snapshot.getDocuments()) {
                    QuestionModel q = doc.toObject(QuestionModel.class);
                    questionList.add(q);
                }

                // Shuffle questions and show first 10
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
    private void showQuestion() {
        if (currentIndex < questionList.size()) {
            QuestionModel q = questionList.get(currentIndex);
            tvQuestion.setText(q.getQuestion());
            rb1.setText(q.getOption1());
            rb2.setText(q.getOption2());
            rb3.setText(q.getOption3());
            rb4.setText(q.getOption4());
            radioGroup.clearCheck();
        } else {
            Toast.makeText(this, "Quiz Over! Score: " + score + "/" + questionList.size(), Toast.LENGTH_LONG).show();
        }
    }
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
            // All questions completed → Go to result
            Intent intent = new Intent(Aptitude_question.this, Result.class);
            intent.putExtra("score", score);
            intent.putExtra("total", questionList.size());
            startActivity(intent);
            finish();

        }

    }
}