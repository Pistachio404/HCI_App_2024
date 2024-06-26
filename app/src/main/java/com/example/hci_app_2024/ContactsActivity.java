package com.example.hci_app_2024;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private LinearLayout contactListLayout;
    private Button btnAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        contactListLayout = findViewById(R.id.contactListLayout);
        btnAddContact = findViewById(R.id.btnAddContact);

        // Προσθήκη επαφών στον ContactManager (στατική για το παράδειγμα)
        ContactManager contactManager = ContactManager.getInstance(this);
        contactManager.addContact("1", "ΜΑΡΙΑ", "1234567890");
        contactManager.addContact("2", "ΓΙΩΡΓΟΣ", "2345678901");
        contactManager.addContact("3", "ΝΙΚΟΛΑΣ", "3456789012");
        contactManager.addContact("4", "ΑΝΝΑ", "4567890123");
        contactManager.addContact("5", "ΕΓΓΟΝΟΣ", "5678901234");
        contactManager.addContact("6", "ΕΓΓΟΝΗ", "6789012345");

        // Ορισμός προεπιλεγμένων αγαπημένων
        contactManager.addFavorite("1");
        contactManager.addFavorite("2");

        // Φόρτωση επαφών στη λίστα
        loadContacts();

        // Διαχείριση κουμπιού "Προσθήκη νέας επαφής"
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactsActivity.this, NewContactActivity.class));
            }
        });

        ImageButton microphoneButton = findViewById(R.id.button_microphone);
        microphoneButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactsActivity.this, VoiceRecognitionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshContactList();
    }

    private void loadContacts() {
        ContactManager contactManager = ContactManager.getInstance(this);
        ArrayList<ContactManager.Contact> contacts = contactManager.getAllContacts();

        for (ContactManager.Contact contact : contacts) {
            addContact(contact.getId(), contact.getName(), contact.getPhone());
        }
    }

    private void refreshContactList() {
        contactListLayout.removeAllViews();
        loadContacts();
    }

    private void addContact(final String id, final String name, final String phone) {
        TextView contactTextView = new TextView(this);
        contactTextView.setText(name);
        contactTextView.setTextSize(20);
        contactTextView.setPadding(10, 10, 10, 10);
        contactTextView.setTextColor(getResources().getColor(android.R.color.black));
        contactTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, ContactDetailsActivity.class);
                intent.putExtra("CONTACT_ID", id);
                startActivity(intent);
            }
        });
        contactListLayout.addView(contactTextView);
    }
}