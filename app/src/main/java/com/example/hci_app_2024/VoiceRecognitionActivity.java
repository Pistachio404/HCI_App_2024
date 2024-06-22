package com.example.hci_app_2024;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Locale;

public class VoiceRecognitionActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognizer speechRecognizer;
    private TextView textViewResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recognition);

        Button speechButton = findViewById(R.id.button);
        textViewResults = findViewById(R.id.textView_results);

        // Request microphone permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        }

        // Initialize the SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                // Called when the endpointer is ready for the user to start speaking.
            }

            @Override
            public void onBeginningOfSpeech() {
                // Called after the user starts speaking.
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // Called to provide the user with feedback on the received signal.
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // Called when more sound has been received.
            }

            @Override
            public void onEndOfSpeech() {
                // Called after the user stops speaking.
            }

            @Override
            public void onError(int error) {
                // Detailed error logging
                String errorMessage = getErrorText(error);
                Toast.makeText(VoiceRecognitionActivity.this, "Recognition Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResults(Bundle results) {
                // Called when recognition results are ready.
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String recognizedText = matches.get(0);
                    textViewResults.setText("Αναγνωρισμένο Κείμενο: " + recognizedText);
                    handleVoiceCommand(recognizedText.toLowerCase(Locale.ROOT));
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // Called when partial recognition results are available.
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // Called when a recognition event occurs.
            }
        });

        // Set the button click listener
        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListening();
            }
        });
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "el-GR"); // Greek language
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Πείτε την εντολή σας"); // "Say your command"
        speechRecognizer.startListening(intent);
    }

    private void handleVoiceCommand(String command) {
        Intent intent;
        switch (command) {
            case "άνοιξε τις κλήσεις":
            case "open calls":
                intent = new Intent(this, CallsActivity.class);
                startActivity(intent);
                break;
            case "άνοιξε τα μηνύματα":
            case "open messages":
                intent = new Intent(this, MessagesActivity.class);
                startActivity(intent);
                break;
            case "άνοιξε τις υπενθυμίσεις":
            case "open reminders":
                intent = new Intent(this, RemindersActivity.class);
                startActivity(intent);
                break;
            case "άνοιξε τις επαφές":
            case "open contacts":
                intent = new Intent(this, ContactsActivity.class);
                startActivity(intent);
                break;
            case "άνοιξε το sos":
            case "open sos":
                intent = new Intent(this, SOSActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Άγνωστη εντολή / Unknown command: " + command, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to record audio is required", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // Helper method to provide detailed error messages
    private String getErrorText(int errorCode) {
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                return "Audio recording error";
            case SpeechRecognizer.ERROR_CLIENT:
                return "Client side error";
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                return "Insufficient permissions";
            case SpeechRecognizer.ERROR_NETWORK:
                return "Network error";
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                return "Network timeout";
            case SpeechRecognizer.ERROR_NO_MATCH:
                return "No match found";
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                return "Recognition service busy";
            case SpeechRecognizer.ERROR_SERVER:
                return "Error from server";
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                return "No speech input";
            default:
                return "Unknown error";
        }
    }
}
