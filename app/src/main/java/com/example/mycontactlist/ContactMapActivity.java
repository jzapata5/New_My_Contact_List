package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ContactMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_map);
        initListButton();
        initMapButton();
        initSettingsButton();
    }

    private void initListButton() {
        ImageButton contactList = findViewById(R.id.imageButtonList); // Variable to hold the ImageButton
        contactList.setOnClickListener(new View.OnClickListener() { // Listener is added to the ImageButton to make it respond to different things
            public void onClick(View view) {
                Intent intent = new Intent(ContactMapActivity.this, ContactListActivity.class); // A mew Intent is created, the Intent's constructors requires reference to the current activity and know what activity to start
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // An Intent flag is set to alert the operating system to not make multiple copes of the same activity
                startActivity(intent); // And listen
            }
        });
    }

    private void initMapButton() {
        ImageButton ibSetting = findViewById(R.id.imageButtonSettings);
        ibSetting.setEnabled(false);
    }

    private void initSettingsButton() {
        ImageButton contactList = findViewById(R.id.imageButtonSettings); // Same as above
        contactList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ContactMapActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent); // And listen
                //setForEditing(false);
            }
        });
    }
}