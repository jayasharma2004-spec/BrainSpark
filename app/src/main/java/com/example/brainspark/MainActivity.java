package com.example.brainspark;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnAptitude = findViewById(R.id.btnAptitude);
        btnAptitude.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AptitudeActivity.class);
            startActivity(intent);
        });
        Button btnTechnical = findViewById(R.id.btnTechnical);
        btnTechnical.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Technical.class);
            startActivity(intent);
        });
        Button btnSvar = findViewById(R.id.btnSvar);
        btnSvar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, instructionActivity.class);
            startActivity(intent);
        });
    }
}