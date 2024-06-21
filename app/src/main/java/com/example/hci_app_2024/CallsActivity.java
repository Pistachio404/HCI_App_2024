package com.example.hci_app_2024;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CallsActivity extends AppCompatActivity {

    private TextView tvDialerInput;
    private Button btnContacts;
    private GridLayout gridLayout;
    private ImageButton callButton, backspaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call);

        tvDialerInput = findViewById(R.id.tvDialerInput);
        btnContacts = findViewById(R.id.btnContacts);
        gridLayout = findViewById(R.id.gridLayout);
        callButton = findViewById(R.id.callButton);
        backspaceButton = findViewById(R.id.backspaceButton);

        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CallsActivity.this, ContactsActivity.class));
            }
        });

        setupDialer();
    }

    private void setupDialer() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String currentText = tvDialerInput.getText().toString();
                        Button button = (Button) v;
                        String buttonText = button.getText().toString();
                        tvDialerInput.setText(currentText + buttonText);
                    }
                });
            }
        }

        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = tvDialerInput.getText().toString();
                if (!currentText.isEmpty()) {
                    tvDialerInput.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = tvDialerInput.getText().toString();
                if (!phoneNumber.isEmpty()) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(callIntent);
                }
            }
        });
    }
}

