package com.example.anurag.vistar.Actvities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anurag.vistar.R;

import java.util.HashMap;
import java.util.Locale;

public class SpeechToText extends AppCompatActivity {
    private TextView englishString, hindiString;
    private Button englishButton, hindiButton;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        englishString = (TextView) findViewById(R.id.englishString);
        hindiString = (TextView) findViewById(R.id.hindiString);

        englishButton = (Button) findViewById(R.id.englishButton);
        hindiButton = (Button) findViewById(R.id.hindiButton);

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSpeakingLanguages(englishString.getText().toString());
            }
        });

//        hindiButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadSpeakingLanguages(hindiString.getText().toString());
//            }
//        });
        hindiButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                loadSpeakingLanguages(hindiString.getText().toString());
                textToSpeech.setLanguage(Locale.forLanguageTag("hin"));
            }
        });
    }


    private void loadSpeakingLanguages(String textToTranslate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(textToTranslate);
        } else {
            ttsUnder20(textToTranslate);
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }


    @Override
    protected void onResume() {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        super.onResume();
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
        super.onPause();
    }}