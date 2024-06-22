package com.example.hci_app_2024;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailsActivity extends AppCompatActivity {

    private EditText contactName, contactPhone;
    private Button buttonCall, buttonMessage, buttonEdit;
    private String phoneNumber;
    private String contactId; // Unique identifier for the contact

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);

        contactName = findViewById(R.id.contactName);
        contactPhone = findViewById(R.id.contactPhone);
        buttonCall = findViewById(R.id.button_call);
        buttonMessage = findViewById(R.id.button_message);
        buttonEdit = findViewById(R.id.button_edit);

        // Λήψη του ονόματος και του τηλεφώνου της επαφής από το Intent
        contactId = getIntent().getStringExtra("CONTACT_ID");
        loadContactDetails(contactId);

        // Διαχείριση κλήσης
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            }
        });

        // Διαχείριση μηνύματος
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(ContactDetailsActivity.this, Message_DetailsActivity.class);
                smsIntent.putExtra("CONTACT_NAME", contactName.getText().toString());
                startActivity(smsIntent);
            }
        });

        // Διαχείριση επεξεργασίας
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditing();
            }
        });

        // Listener για την αποθήκευση όταν πατηθεί το Enter
        contactName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    saveChanges();
                    return true;
                }
                return false;
            }
        });

        contactPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    saveChanges();
                    return true;
                }
                return false;
            }
        });
    }

    // Ενεργοποίηση της επεξεργασίας των πεδίων
    private void enableEditing() {
        contactName.setEnabled(true);
        contactPhone.setEnabled(true);
        contactName.setFocusableInTouchMode(true);
        contactPhone.setFocusableInTouchMode(true);
    }

    // Αποθήκευση των αλλαγών
    private void saveChanges() {
        contactName.setEnabled(false);
        contactPhone.setEnabled(false);
        contactName.setFocusableInTouchMode(false);
        contactPhone.setFocusableInTouchMode(false);

        // Αποθήκευση των ενημερωμένων δεδομένων
        String updatedName = contactName.getText().toString();
        String updatedPhone = contactPhone.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("contacts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(contactId + "_name", updatedName);
        editor.putString(contactId + "_phone", updatedPhone);
        editor.apply();
    }

    // Φόρτωση των στοιχείων της επαφής από το SharedPreferences
    private void loadContactDetails(String contactId) {
        SharedPreferences sharedPreferences = getSharedPreferences("contacts", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(contactId + "_name", "ΟΝΟΜΑ");
        phoneNumber = sharedPreferences.getString(contactId + "_phone", "69XXXXXXXXX");
        contactName.setText(name);
        contactPhone.setText(phoneNumber);
    }
}