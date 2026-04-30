package com.example.brainspark;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        // ⭐ FIXED: Avoid deprecated Handler() constructor
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(splash_screen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 4000);

        ImageView img = findViewById(R.id.imageView);
        img.setBackgroundResource(R.drawable.animation);
        AnimationDrawable ani = (AnimationDrawable) img.getBackground();
        ani.start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
