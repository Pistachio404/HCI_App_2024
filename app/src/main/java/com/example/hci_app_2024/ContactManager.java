package com.example.hci_app_2024;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactManager {
    private static ContactManager instance;
    private HashMap<String, Contact> contacts;
    private static final String PREFS_NAME = "contacts";
    private SharedPreferences sharedPreferences;

    private ContactManager(Context context) {
        contacts = new HashMap<>();
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadContacts();
    }

    public static synchronized ContactManager getInstance(Context context) {
        if (instance == null) {
            instance = new ContactManager(context);
        }
        return instance;
    }

    public void addContact(String id, String name, String phone) {
        contacts.put(id, new Contact(id, name, phone));
        saveContacts();
    }

    public Contact getContact(String id) {
        return contacts.get(id);
    }

    public ArrayList<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    private void saveContacts() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Map.Entry<String, Contact> entry : contacts.entrySet()) {
            String id = entry.getKey();
            Contact contact = entry.getValue();
            editor.putString(id + "_name", contact.getName());
            editor.putString(id + "_phone", contact.getPhone());
        }
        editor.apply();
    }

    private void loadContacts() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            if (key.endsWith("_name")) {
                String id = key.substring(0, key.indexOf("_name"));
                String name = (String) entry.getValue();
                String phone = sharedPreferences.getString(id + "_phone", null);
                contacts.put(id, new Contact(id, name, phone));
            }
        }
    }

    public static class Contact {
        private String id;
        private String name;
        private String phone;

        public Contact(String id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone = phone;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }
}
