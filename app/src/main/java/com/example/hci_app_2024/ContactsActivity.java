package com.example.hci_app_2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactsActivity extends AppCompatActivity {

    private LinearLayout contactListLayout;
    private Button btnAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        contactListLayout = findViewById(R.id.contactListLayout);
        btnAddContact = findViewById(R.id.btnAddContact);

        // Προσθήκη επαφών στη λίστα (στατική για το παράδειγμα)
        addContact("1", "ΜΑΡΙΑ");
        addContact("2", "ΓΙΩΡΓΟΣ");
        addContact("3", "ΝΙΚΟΛΑΣ");
        addContact("4", "ΑΝΝΑ");
        addContact("5", "ΕΓΓΟΝΟΣ");
        addContact("6", "ΕΓΓΟΝΗ");

        // Διαχείριση κουμπιού "Προσθήκη νέας επαφής"
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactsActivity.this, NewContactActivity.class));
            }
        });
    }

    private void addContact(final String id, final String name) {
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