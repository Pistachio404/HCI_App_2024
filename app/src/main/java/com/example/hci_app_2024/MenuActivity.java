package com.example.hci_app_2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hci_app_2024.CallsActivity;
import com.example.hci_app_2024.MessagesActivity;
import com.example.hci_app_2024.RemindersActivity;
import com.example.hci_app_2024.SOSActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }

    public void onCallsButtonClick(View view) {
        Intent intent = new Intent(this, CallsActivity.class);
        startActivity(intent);
    }

    public void onMessagesButtonClick(View view) {
        // Handle the messages button click
    }

    public void onRemindersButtonClick(View view) {
        // Handle the reminders button click
    }

    public void onSosButtonClick(View view) {
        // Handle the SOS button click
    }
}


