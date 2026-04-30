package com.example.brainspark;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.Date;

public class activity_history extends AppCompatActivity {

    ListView listView;
//    Button button
    ArrayList<String> historyList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listView);
        db = FirebaseFirestore.getInstance();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                historyList
        );

        listView.setAdapter(adapter);

        loadHistory();
    }

    private void loadHistory() {

        // ✅ Get unique device user ID
        String userId = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.ANDROID_ID
        );

        // 🔍 Debug (optional)
        Toast.makeText(this, "UserID: " + userId, Toast.LENGTH_SHORT).show();

        db.collection("mock_results")
                .whereEqualTo("userId", userId) // filter current user
                .orderBy("timestamp", Query.Direction.DESCENDING) // latest first
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    historyList.clear();

                    if (queryDocumentSnapshots.isEmpty()) {
                        historyList.add("No test history found");
                    } else {
                        int count = 1;

                        for (DocumentSnapshot doc : queryDocumentSnapshots) {

                            Long scoreObj = doc.getLong("score");
                            Long totalObj = doc.getLong("total");
                            Long timeObj = doc.getLong("timestamp");

                            if (scoreObj != null && totalObj != null) {

                                long score = scoreObj;
                                long total = totalObj;

                                String text = "Test " + count +
                                        "\nScore: " + score + "/" + total;

                                // ✅ Convert timestamp correctly
                                if (timeObj != null) {
                                    Date date = new Date(timeObj);
                                    text += "\nDate: " + date.toString();
                                }

                                historyList.add(text);
                                count++;
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading history", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                });
    }
}