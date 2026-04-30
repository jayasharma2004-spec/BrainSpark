package com.example.brainspark;
import android.os.CountDownTimer;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class SvarTestActivity extends AppCompatActivity {
    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 15 * 60 * 1000; // 15 minutes

    LinearLayout layoutRead, layoutListen, layoutJumble;

    TextView tvReadSentence, tvJumble;
    Button btnReadSpeak, btnPlayAudio, btnListenSpeak, btnNext, btnJumbleSpeak;

    int section = 1; // 1=Read, 2=Listen, 3=Jumble
    int index = 0;
    boolean answered = false;

    ArrayList<String> readQs = new ArrayList<>();
    ArrayList<String> listenQs = new ArrayList<>();
    ArrayList<String> jumbleQs = new ArrayList<>();

    ArrayList<UserAnswer> readResults = new ArrayList<>();
    ArrayList<UserAnswer> listenResults = new ArrayList<>();
    ArrayList<UserAnswer> jumbleResults = new ArrayList<>();

    TextToSpeech tts;
    String currentSentence = "";

    ActivityResultLauncher<Intent> speechLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            ArrayList<String> res =
                                    result.getData().getStringArrayListExtra(
                                            RecognizerIntent.EXTRA_RESULTS);
                            if (res != null && !res.isEmpty()) {
                                handleSpeech(res.get(0));
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svar_test);
        tvTimer = findViewById(R.id.tvTimer);
        startTimer();

        layoutRead = findViewById(R.id.layoutRead);
        layoutListen = findViewById(R.id.layoutListen);
        layoutJumble = findViewById(R.id.layoutJumble);

        tvReadSentence = findViewById(R.id.tvReadSentence);
        tvJumble = findViewById(R.id.tvJumble);

        btnReadSpeak = findViewById(R.id.btnReadSpeak);
        btnPlayAudio = findViewById(R.id.btnPlayAudio);
        btnListenSpeak = findViewById(R.id.btnListenSpeak);
        btnJumbleSpeak = findViewById(R.id.btnJumbleSpeak);
        btnNext = findViewById(R.id.btnNext);

        loadData();

        tts = new TextToSpeech(this, status -> {});

        btnReadSpeak.setOnClickListener(v -> startSpeech());
        btnListenSpeak.setOnClickListener(v -> startSpeech());
        btnJumbleSpeak.setOnClickListener(v -> startSpeech());

        btnPlayAudio.setOnClickListener(v ->
                tts.speak(currentSentence, TextToSpeech.QUEUE_FLUSH, null, null));

        btnNext.setOnClickListener(v -> next());

        showRead();
    }

    // ---------------- DATA ----------------
    private void loadData() {
        readQs.addAll(Arrays.asList(
                "Communication improves teamwork",
                "Confidence helps in interviews",
                "Listening is an important skill",
                "Practice makes a person confident",
                "Clear speech creates a good impression",
                "Positive attitude leads to success",
                "Time management improves productivity",
                "Learning new skills is very important",
                "Teamwork helps achieve common goals",
                "Good manners reflect good character"
        ));

        listenQs.addAll(Arrays.asList(
                "Practice builds strong communication skills",
                "Confidence grows when we speak regularly",
                "Listening carefully avoids misunderstandings",
                "Good communication improves professional life",
                "Time management helps meet deadlines",
                "Positive thinking improves performance",
                "Clear pronunciation makes speech effective",
                "Consistency leads to long term success",
                "Leadership requires patience and empathy",
                "Preparation increases confidence while speaking"
        ));

        jumbleQs.addAll(Arrays.asList(
                "Teamwork leads to great success",
                "Practice builds confidence over time",
                "Clear communication improves understanding",
                "Positive attitude creates better opportunities",
                "Listening carefully avoids common mistakes",
                "Time management improves work efficiency",
                "Confidence helps overcome fear",
                "Learning daily improves personal growth",
                "Hard work brings good results",
                "Good communication builds strong relationships"
        ));
        Collections.shuffle(readQs);
        Collections.shuffle(listenQs);
        Collections.shuffle(jumbleQs);
        readQs = new ArrayList<>(readQs.subList(0, 5));
        listenQs = new ArrayList<>(listenQs.subList(0, 5));
        jumbleQs = new ArrayList<>(jumbleQs.subList(0, 5));
    }

    // ---------------- FLOW ----------------
    private void resetState() {
        answered = false;
        btnNext.setEnabled(false);

        btnReadSpeak.setEnabled(true);
        btnListenSpeak.setEnabled(true);
        btnJumbleSpeak.setEnabled(true);
    }

    private void showRead() {
        resetState();

        layoutRead.setVisibility(View.VISIBLE);
        layoutListen.setVisibility(View.GONE);
        layoutJumble.setVisibility(View.GONE);

        currentSentence = readQs.get(index);
        tvReadSentence.setText(currentSentence);
    }

    private void showListen() {
        resetState();

        layoutRead.setVisibility(View.GONE);
        layoutListen.setVisibility(View.VISIBLE);
        layoutJumble.setVisibility(View.GONE);

        currentSentence = listenQs.get(index);
    }

    private void showJumble() {
        resetState();

        layoutRead.setVisibility(View.GONE);
        layoutListen.setVisibility(View.GONE);
        layoutJumble.setVisibility(View.VISIBLE);

        currentSentence = jumbleQs.get(index);
        tvJumble.setText(shuffleSentence(currentSentence));
    }

    private void next() {
        index++;

        if (section == 1 && index == readQs.size()) {
            section = 2;
            index = 0;
            showListen();
            return;
        }

        if (section == 2 && index == listenQs.size()) {
            section = 3;
            index = 0;
            showJumble();
            return;
        }

        if (section == 3 && index == jumbleQs.size()) {
            goToResult();
            return;
        }

        if (section == 1) showRead();
        else if (section == 2) showListen();
        else showJumble();
    }

    // ---------------- SPEECH ----------------
    private void startSpeech() {
        if (answered) return;

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechLauncher.launch(i);
    }

    private void handleSpeech(String spoken) {
        if (answered) return;

        boolean correct = spoken.equalsIgnoreCase(currentSentence);
        UserAnswer ua = new UserAnswer(currentSentence, spoken, correct);

        if (section == 1) readResults.add(ua);
        else if (section == 2) listenResults.add(ua);
        else jumbleResults.add(ua);

        answered = true;

        btnReadSpeak.setEnabled(false);
        btnListenSpeak.setEnabled(false);
        btnJumbleSpeak.setEnabled(false);

        btnNext.setEnabled(true); // move only AFTER answer
    }

    // ---------------- RESULT ----------------
    private void goToResult() {

        if (countDownTimer != null) {
            countDownTimer.cancel(); // stop timer
        }

        ResultStore.read = readResults;
        ResultStore.listen = listenResults;
        ResultStore.jumble = jumbleResults;

        startActivity(new Intent(this, ResultSvarActivity.class));
        finish();
    }

    // ---------------- UTIL ----------------
    private String shuffleSentence(String s) {
        List<String> words = Arrays.asList(s.split(" "));
        Collections.shuffle(words);
        return TextUtils.join(" ", words);
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;

                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                String timeFormatted = String.format("%02d:%02d", minutes, seconds);
                tvTimer.setText(timeFormatted);

                // last 1 minute warning
                if (millisUntilFinished <= 60000) {
                    tvTimer.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                Toast.makeText(SvarTestActivity.this, "Time's up! Auto submitting...", Toast.LENGTH_SHORT).show();
                goToResult(); // AUTO SUBMIT
            }
        }.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
