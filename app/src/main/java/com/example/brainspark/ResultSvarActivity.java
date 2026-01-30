package com.example.brainspark;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultSvarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_svar);

        TextView tv = findViewById(R.id.tvResult);

        int score = 0;
        int total=0;
        StringBuilder sb = new StringBuilder();

        sb.append("READING:\n");
        for (UserAnswer u : ResultStore.read) {
            total++;
            sb.append(u.original).append("\nYou said: ")
                    .append(u.spoken).append("\n")
                    .append(u.correct ? "✔\n\n" : "✘\n\n");
            if (u.correct) score++;
        }

        sb.append("LISTENING:\n");
        for (UserAnswer u : ResultStore.listen) {
            total++;
            sb.append(u.original).append("\nYou said: ")
                    .append(u.spoken).append("\n")
                    .append(u.correct ? "✔\n\n" : "✘\n\n");
            if (u.correct) score++;
        }

        sb.append("JUMBLE:\n");
        for (UserAnswer u : ResultStore.jumble) {
            total++;
            sb.append(u.original).append("\nYou said: ")
                    .append(u.spoken).append("\n")
                    .append(u.correct ? "✔\n\n" : "✘\n\n");
            if (u.correct) score++;
        }

        sb.append("OVERALL SCORE: ")
                .append(score)
                .append(" / ")
                .append(total);

        tv.setText(sb.toString());
        Button btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(v -> {
            Intent intent = new Intent(ResultSvarActivity.this, MainActivity.class);
            startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.svaresult), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}
