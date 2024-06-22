package com.example.hci_app_2024;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailsActivity extends AppCompatActivity {

    private EditText contactName, contactPhone;
    private Button buttonCall, buttonMessage, buttonEdit;
    private String contactId;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);

        contactName = findViewById(R.id.contactName);
        contactPhone = findViewById(R.id.contactPhone);
        buttonCall = findViewById(R.id.button_call);
        buttonMessage = findViewById(R.id.button_message);
        buttonEdit = findViewById(R.id.button_edit);

        // Λήψη του ID της επαφής από το Intent
        contactId = getIntent().getStringExtra("CONTACT_ID");

        // Φόρτωση των στοιχείων της επαφής
        loadContactDetails(contactId);

        // Διαχείριση κλήσης
        buttonCall.setOnClickListener(v -> {
            String phoneNumber = contactPhone.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        });

        // Διαχείριση μηνύματος
        buttonMessage.setOnClickListener(v -> {
            Intent smsIntent = new Intent(ContactDetailsActivity.this, Message_DetailsActivity.class);
            smsIntent.putExtra("contact_name", contactName.getText().toString());
            startActivity(smsIntent);
        });

        // Διαχείριση επεξεργασίας και αποθήκευσης
        buttonEdit.setOnClickListener(v -> {
            if (isEditing) {
                saveChanges();
            } else {
                enableEditing();
            }
        });

        // Listener για την αποθήκευση όταν πατηθεί το Enter
        contactName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                saveChanges();
                return true;
            }
            return false;
        });

        contactPhone.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                saveChanges();
                return true;
            }
            return false;
        });
    }

    // Ενεργοποίηση της επεξεργασίας των πεδίων
    private void enableEditing() {
        contactName.setEnabled(true);
        contactPhone.setEnabled(true);
        contactName.setFocusableInTouchMode(true);
        contactPhone.setFocusableInTouchMode(true);
        buttonEdit.setText("Αποθήκευση");
        isEditing = true;
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
        ContactManager contactManager = ContactManager.getInstance(this);
        contactManager.addContact(contactId, updatedName, updatedPhone);

        buttonEdit.setText("Επεξεργασία");
        isEditing = false;
    }

    // Φόρτωση των στοιχείων της επαφής από το ContactManager
    private void loadContactDetails(String contactId) {
        ContactManager.Contact contact = ContactManager.getInstance(this).getContact(contactId);
        if (contact != null) {
            contactName.setText(contact.getName());
            contactPhone.setText(contact.getPhone());
        } else {
            contactName.setText("ΟΝΟΜΑ");
            contactPhone.setText("69XXXXXXXXX");
        }
    }
}
