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
        ImageButton microphoneButton = findViewById(R.id.button_microphone);

        microphoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, VoiceRecognitionActivity.class);
                startActivity(intent);
            }
        });

        // Προσθήκη επαφών στον ContactManager
        ContactManager contactManager = ContactManager.getInstance(this);
        contactManager.addContact("1", "Μαρία", "1234567890");
        contactManager.addContact("2", "Γιώργος", "2345678901");
        contactManager.addContact("3", "Νικόλας", "3456789012");
        contactManager.addContact("4", "Άννα", "4567890123");
        contactManager.addContact("5", "Εγγονός", "5678901234");
        contactManager.addContact("6", "Εγγονή", "6789012345");

        // Φόρτωση επαφών στη λίστα
        loadContacts();

        // Διαχείριση κουμπιού "Προσθήκη νέας επαφής"
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactsActivity.this, NewContactActivity.class));
            }
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
