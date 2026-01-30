package com.example.brainspark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TopicWiseAptitude extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_topic_wise_aptitude);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.topic), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // ---------------- COMMON METHOD ----------------
    private void openTopic(String topic) {
        Intent i = new Intent(this, TopicContentActivity.class);
        i.putExtra("TOPIC", topic);
        startActivity(i);
    }

    // ---------------- BUTTON HANDLERS ----------------

    public void openPercentage(View v) {
        openTopic("percentage");
    }

    public void openDivisibility(View v) {
        openTopic("divisiblity");
    }

    public void openAreaPerimeter(View v) {
        openTopic("area_perimeter");
    }

    public void openHcfLcm(View v) {
        openTopic("hcf_and_lcm");
    }

    public void openAverage(View v) {
        openTopic("average");
    }

    public void openPermutation(View v) {
        openTopic("permutation_and_combination");
    }

    public void openProbability(View v) {
        openTopic("probability");
    }

    public void openTimeWork(View v) {
        openTopic("Time_and_Work");
    }

    public void openTimeDistance(View v) {
        openTopic("time_and_distance");
    }
}
