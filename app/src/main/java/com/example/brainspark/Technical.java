package com.example.brainspark;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Technical extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_technical);
        Button btnDSA=findViewById(R.id.btnDSA);
        btnDSA.setOnClickListener(v -> {
            Intent intent = new Intent(Technical.this, DSA.class);
            startActivity(intent);
        });
        Button btnDBMS=findViewById(R.id.btnDBMS);
        btnDBMS.setOnClickListener(v -> {
            Intent intent = new Intent(Technical.this, TechnicalSubject.class);
            intent.putExtra("subject", "DBMS");
            startActivity(intent);
        });
        Button btnOS=findViewById(R.id.btnOS);
        btnOS.setOnClickListener(v -> {
            Intent intent = new Intent(Technical.this, TechnicalSubject.class);
            intent.putExtra("subject", "OS");
            startActivity(intent);
        });
        Button btnCN=findViewById(R.id.btnCN);
        btnCN.setOnClickListener(v -> {
            Intent intent = new Intent(Technical.this, TechnicalSubject.class);
            intent.putExtra("subject", "CN");
            startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.technical), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}