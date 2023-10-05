package com.example.androidcontactsapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ContactDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        // Open the database for both reading and writing
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        // Close the database connection
        dbHelper.close();
    }

    // Insert a new contact into the database
    public long insertContact(String name) {
        open(); // Open the database
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        long id = database.insert(DatabaseHelper.TABLE_CONTACTS, null, values);
        close(); // Close the database
        return id;
    }

    // Update an existing contact's information
    public int updateContact(long contactId, String newName) {
        open(); // Open the database
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, newName);
        int rowsAffected = database.update(DatabaseHelper.TABLE_CONTACTS, values,
                DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(contactId)});
        close(); // Close the database
        return rowsAffected;
    }

    // Delete a contact from the database
    public void deleteContact(long contactId) {
        open(); // Open the database
        database.delete(DatabaseHelper.TABLE_CONTACTS,
                DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(contactId)});
        close(); // Close the database
    }

    // Retrieve a list of all contacts from the database
    public List<ContactModel> getAllContacts() {
        open(); // Open the database
        List<ContactModel> contacts = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_CONTACTS, null, null, null, null, null, null);
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                    ContactModel contact = new ContactModel(id, name);
                    contacts.add(contact);
                    cursor.moveToNext();
                }
            } finally {
                cursor.close(); // Always close the cursor to avoid resource leaks
            }
        }
        close(); // Close the database
        return contacts;
    }
}