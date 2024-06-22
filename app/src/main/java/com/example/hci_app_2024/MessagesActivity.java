package com.example.hci_app_2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);

        setClickListener(R.id.textView_maria, "ΜΑΡΙΑ");
        setClickListener(R.id.textView_giorgos, "ΓΙΩΡΓΟΣ");
        setClickListener(R.id.textView_nikolas, "ΝΙΚΟΛΑΣ");
        setClickListener(R.id.textView_anna, "ΑΝΝΑ");
        setClickListener(R.id.textView_eggons, "ΕΓΓΟΝΟΣ");
        setClickListener(R.id.textView_eggoni, "ΕΓΓΟΝΗ");
        setClickListener(R.id.textView_doctor, "ΓΙΑΤΡΟΣ");
        setClickListener(R.id.textView_pathologist, "ΠΑΘΟΛΟΓΟΣ");

        Button newMessageButton = findViewById(R.id.btnNew_message);
        newMessageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MessagesActivity.this, NewMessageActivity.class);
            startActivity(intent);
        });

        ImageButton microphoneButton = findViewById(R.id.button_microphone);
        microphoneButton.setOnClickListener(v -> {
            Intent intent = new Intent(MessagesActivity.this, VoiceRecognitionActivity.class);
            startActivity(intent);
        });
    }

    private void setClickListener(int textViewId, String contactName) {
        TextView textView = findViewById(textViewId);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(MessagesActivity.this, Message_DetailsActivity.class);
            intent.putExtra("contact_name", contactName);
            startActivity(intent);
        });
    }
}
