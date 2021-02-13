package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {
    ArrayList<Contact> contacts;

    private View.OnClickListener onItemClickListener = view -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        int contactId = contacts.get(position).getContactID();
        Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
        intent.putExtra("contactId", contactId);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ContactDataSource ds = new ContactDataSource(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initListButton();
        initMapButton();
        initSettingsButton();


        try {
            ds.open();
            contacts = ds.getContacts();
            ds.close();
            RecyclerView contactList = findViewById(R.id.rvContacts);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            contactList.setLayoutManager(layoutManager);
            ContactAdapter contactAdapter = new ContactAdapter(contacts);
            contactAdapter.setOnItemClickListener(onItemClickListener);
            contactList.setAdapter(contactAdapter);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }
    }

    private void initListButton() {
        ImageButton ibSetting = findViewById(R.id.imageButtonList);
        ibSetting.setEnabled(false);
    }

    private void initMapButton() {
        ImageButton contactList = findViewById(R.id.imageButtonMap); // Variable to hold the ImageButton
        contactList.setOnClickListener(new View.OnClickListener() { // Listener is added to the ImageButton to make it respond to different things
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, ContactListActivity.class); // A mew Intent is created, the Intent's constructors requires reference to the current activity and know what activity to start
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // An Intent flag is set to alert the operating system to not make multiple copes of the same activity
                startActivity(intent); // And listen
            }
        });
    }

    private void initSettingsButton() {
        ImageButton contactList = findViewById(R.id.imageButtonSettings); // Same as above
        contactList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent); // And listen
                //setForEditing(false);
            }
        });
    }
}