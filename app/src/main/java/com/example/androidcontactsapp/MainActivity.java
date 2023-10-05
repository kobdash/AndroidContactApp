package com.example.androidcontactsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends Activity {

    private EditText contactNameEditText;
    private Button saveButton;
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private ContactDAO contactDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize your views
        contactNameEditText = findViewById(R.id.contactName);
        saveButton = findViewById(R.id.saveButton);

        // Initialize your RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize your ContactDAO to fetch data from the database
        contactDAO = new ContactDAO(this);

        // Set an OnClickListener for the saveButton
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cast the View to EditText and get the text
                EditText contactName = (EditText) contactNameEditText;
                String name = contactName.getText().toString();

                // Check if the name is not empty before proceeding
                if (!name.isEmpty()) {
                    // Insert the contact into the database and get its ID
                    long id = contactDAO.insertContact(name);

                    // Fetch data from the database and set it in the adapter
                    List<ContactModel> contacts = contactDAO.getAllContacts();
                    contactAdapter = new ContactAdapter(MainActivity.this, contacts);
                    recyclerView.setAdapter(contactAdapter);

                    // Clear the EditText after saving
                    contactName.setText("");
                } else {
                    // Handle the case where the name is empty (you may want to show a message to the user)
                }
            }
        });

        // Fetch initial data and set it in the adapter
        List<ContactModel> initialContacts = contactDAO.getAllContacts();
        contactAdapter = new ContactAdapter(MainActivity.this, initialContacts);
        recyclerView.setAdapter(contactAdapter);
    }
}


