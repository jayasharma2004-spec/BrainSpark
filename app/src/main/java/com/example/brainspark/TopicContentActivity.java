package com.example.brainspark;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class TopicContentActivity extends AppCompatActivity {

    LinearLayout container;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.topic_content);

        container = findViewById(R.id.container);
        TextView tvTitle = findViewById(R.id.tvTitle);

        String topic = getIntent().getStringExtra("TOPIC");
        tvTitle.setText(topic.toUpperCase());

        db = FirebaseFirestore.getInstance();

        // 🔥 FIRESTORE COLLECTION READ
        db.collection(topic)
                .get()
                .addOnSuccessListener(querySnapshot -> {

                    if (querySnapshot.isEmpty()) {
                        Toast.makeText(this,
                                "No questions found", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        TopicApptitudeModel q =
                                doc.toObject(TopicApptitudeModel.class);
                        addView(q);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "Firestore error", Toast.LENGTH_SHORT).show()
                );

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.topicContent), (v, insets) -> {
                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);
                    return insets;
                });
    }

    private void addView(TopicApptitudeModel q) {

        TextView tvQ = new TextView(this);
        tvQ.setText("Q. " + q.question);
        tvQ.setTextSize(16);
        tvQ.setTextColor(Color.BLACK);
        tvQ.setPadding(0, 12, 0, 6);

        TextView tvS = new TextView(this);
        tvS.setText("Solution: " + q.solution);
        tvS.setTextColor(Color.DKGRAY);
        tvS.setPadding(0, 0, 0, 24);

        container.addView(tvQ);
        container.addView(tvS);
    }
}

