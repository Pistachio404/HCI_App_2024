package com.example.hci_app_2024;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class NewMessageActivity extends AppCompatActivity {

    private EditText messageEditText;
    private String contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_message);

        contactName = getIntent().getStringExtra("contact_name");
        messageEditText = findViewById(R.id.message_body);

        ImageButton microphoneButton = findViewById(R.id.button_microphone);
        microphoneButton.setOnClickListener(v -> {
            Intent intent = new Intent(NewMessageActivity.this, VoiceRecognitionActivity.class);
            startActivity(intent);
        });

        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(v -> {
            String messageText = messageEditText.getText().toString();
            if (!messageText.trim().isEmpty()) {
                Intent intent = new Intent(NewMessageActivity.this, Message_DetailsActivity.class);
                intent.putExtra("message_text", messageText);
                intent.putExtra("contact_name", contactName);
                startActivity(intent);
                finish(); // Close NewMessageActivity
            }
        });

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(NewMessageActivity.this, MessagesActivity.class);
            startActivity(intent);
            finish(); // Close NewMessageActivity
        });

        messageEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(messageEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }
}
