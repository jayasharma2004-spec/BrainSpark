package com.example.brainspark;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.*;

import java.util.*;

public class TechnicalSubject extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore db;

    List<TechnicalModel> questionList = new ArrayList<>();
    TechnicalAdapter adapter;

    String selectedSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technicalsubject);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TechnicalAdapter(questionList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // 🔥 GET SUBJECT FROM BUTTON CLICK
        selectedSubject = getIntent().getStringExtra("subject");

        if (selectedSubject == null) {
            selectedSubject = "DBMS"; // fallback
        }

        Toast.makeText(this, "Loading: " + selectedSubject, Toast.LENGTH_SHORT).show();

        loadQuestions();
    }

    private void loadQuestions() {

        db.collection("technical_questions")
                .whereEqualTo("subject", selectedSubject)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    questionList.clear();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        TechnicalModel q = doc.toObject(TechnicalModel.class);

                        if (q != null) {
                            questionList.add(q);
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (questionList.isEmpty()) {
                        Toast.makeText(this, "No questions found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}