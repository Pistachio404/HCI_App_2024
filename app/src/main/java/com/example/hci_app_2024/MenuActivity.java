package com.example.hci_app_2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hci_app_2024.CallsActivity;
import com.example.hci_app_2024.MessagesActivity;
import com.example.hci_app_2024.RemindersActivity;
import com.example.hci_app_2024.SOSActivity;

public class MenuActivity extends AppCompatActivity {

    private Button callsButton, messagesButton, remindersButton, sosButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        callsButton = findViewById(R.id.callsButton);
        messagesButton = findViewById(R.id.messagesButton);
        remindersButton = findViewById(R.id.remindersButton);
        sosButton = findViewById(R.id.sosButton);

        ImageButton microphoneButton = findViewById(R.id.button_microphone);

        microphoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, VoiceRecognitionActivity.class);
                startActivity(intent);
            }
        });

        callsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, CallsActivity.class));
            }
        });

        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MessagesActivity.class));
            }
        });

        remindersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, RemindersActivity.class));
            }
        });

        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, SOSActivity.class));
            }
        });
    }
}
