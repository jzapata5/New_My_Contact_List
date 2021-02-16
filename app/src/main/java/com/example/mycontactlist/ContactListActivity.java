package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {
    ArrayList<Contact> contacts;
    ContactAdapter contactAdapter;

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
        String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield", "contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
        ContactDataSource ds = new ContactDataSource(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initListButton();
        initMapButton();
        initSettingsButton();

        try {
            ds.open();
            contacts = ds.getContacts(sortBy, sortOrder);
            ds.close();
            RecyclerView contactList = findViewById(R.id.rvContacts);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            contactList.setLayoutManager(layoutManager);
            contactAdapter = new ContactAdapter(contacts, ContactListActivity.this);
            contactAdapter.setOnItemClickListener(onItemClickListener);
            contactList.setAdapter(contactAdapter);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

        initDeleteSwitch();
        initAddContactButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield", "contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
        ContactDataSource ds = new ContactDataSource(this);
        try {
            ds.open();
            contacts = ds.getContacts(sortBy, sortOrder);
            ds.close();
            if (contacts.size() > 0) {
                RecyclerView contactList = findViewById(R.id.rvContacts);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                contactList.setLayoutManager(layoutManager);
                contactAdapter = new ContactAdapter(contacts, ContactListActivity.this);
                contactAdapter.setOnItemClickListener(onItemClickListener);
                contactList.setAdapter(contactAdapter);
            }
            else {
                Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(intent);
            }

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
                Intent intent = new Intent(ContactListActivity.this, ContactMapActivity.class); // A mew Intent is created, the Intent's constructors requires reference to the current activity and know what activity to start
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // An Intent flag is set to alert the operating system to not make multiple copes of the same activity
                startActivity(intent); // And listen
            }
        });
    }

    private void initSettingsButton() {
        ImageButton contactList = findViewById(R.id.imageButtonSettings); // Same as above
        contactList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent); // And listen
            }
        });
    }

    private void initAddContactButton() {
        Button newContact = findViewById(R.id.buttonAddContact);
        newContact.setOnClickListener(view -> {
            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.switchDelete);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean status = buttonView.isChecked();
                contactAdapter.setDelete(status);
                contactAdapter.notifyDataSetChanged();
            }
        });
    }

}