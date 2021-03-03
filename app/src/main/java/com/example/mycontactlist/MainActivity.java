 package com.example.mycontactlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.example.mycontactlist.DatePickerDialog.SaveDateListener;
import com.google.android.material.snackbar.Snackbar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.*;

import android.text.format.DateFormat;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import static android.text.format.DateFormat.format;
import static java.text.DateFormat.*;

public class MainActivity extends AppCompatActivity implements SaveDateListener {

    private Contact currentContact;
    final int PERMISSION_REQUEST_PHONE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListButton();
        initMapButton();
        initToggleButton();
        initSettingsButton();
        initChangeDateButton();
        initCallFunction();


        Bundle extras = getIntent().getExtras();
        if (extras != null) { //this is when you click on someone's contact via List
            initContact(extras.getInt("contactId"));
        } else {
            currentContact = new Contact(); // this will be for the start menu
        }

        setForEditing(false);
        initTextChangedEvents();
        initSaveButton();


    }

    private void initListButton() {
        ImageButton contactList = findViewById(R.id.imageButtonList); // Variable to hold the ImageButton
        // Listener is added to the ImageButton to make it respond to different things
        contactList.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ContactListActivity.class); // A mew Intent is created, the Intent's constructors requires reference to the current activity and know what activity to start
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // An Intent flag is set to alert the operating system to not make multiple copes of the same activity
            startActivity(intent); // And listen
        });
    }

    private void initMapButton() {
        ImageButton contactList = findViewById(R.id.imageButtonMap); // Same as above
        contactList.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ContactMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent); // And listen
            setForEditing(false);
        });
    }

    private void initSettingsButton() {
        ImageButton contactList = findViewById(R.id.imageButtonSettings); // Same as above
        contactList.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ContactSettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent); // And listen
        });
    }

    private void initToggleButton() {
        final ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(view -> setForEditing(editToggle.isChecked()));
    }

    private void initTextChangedEvents() {
        final EditText editContactName = findViewById(R.id.editName); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setContactName(editContactName.getText().toString());
            }
        });

        final EditText editStreetAddress = findViewById(R.id.editAddress);
        editStreetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This code is executed when the user ends editing of the EditText.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // This method is executed when the user presses down on a key to enter it into an EditText but before the value in the EditText is actually changed.
            }

            @Override
            public void afterTextChanged(Editable s) { // this is called after the user complete editin the data and leaves the editText box
                currentContact.setStreetAddress(editStreetAddress.getText().toString());
            }
        });

        final EditText editCity = findViewById(R.id.editCity);
        editCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // This method is executed when the user presses down on a key to enter it into an EditText but before the value in the EditText is actually changed.
            }

            @Override
            public void afterTextChanged(Editable s) { // this is called after the user complete editin the data and leaves the editText box
                currentContact.setCity(editCity.getText().toString()); // This code is executed when the user ends editing of the EditText.
            }
        });

        final EditText editState = findViewById(R.id.editState); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setState(editState.getText().toString());

            }
        });

        final EditText editZipCode = findViewById(R.id.editZipCode); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editZipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setZipCode(editZipCode.getText().toString());

            }
        });

        final EditText editPhone = findViewById(R.id.editHome); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setPhoneNumber(editPhone.getText().toString());

            }
        });

        final EditText editCell = findViewById(R.id.editCell); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editCell.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setCellNumber(editCell.getText().toString());

            }
        });

        final EditText editEmail = findViewById(R.id.editEmail); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setEmail(editEmail.getText().toString());

            }
        });
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener((View view) -> {
            boolean wasSuccessful = false;
            hideKeyboard();
            ContactDataSource dataSource = new ContactDataSource(MainActivity.this); //data source receives context as a parameter
            try {
                dataSource.open(); //open the database

                if (currentContact.getContactID() == -1) { // remember that a new contact has an id of -1
                    wasSuccessful = dataSource.insertContact(currentContact); // if the contact does not exist, then create a new one
                }
                if (wasSuccessful) {
                    int newID = dataSource.getLastContactID();
                    currentContact.setContactID(newID);
                } else {
                    wasSuccessful = dataSource.updateContact(currentContact); // if it does exist, then just update it
                }
                dataSource.close();
            } catch (Exception e) {
                wasSuccessful = false;
            }
            if (wasSuccessful) {
                ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
                editToggle.toggle();
                setForEditing(false);
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editName = findViewById(R.id.editName);
        imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
        EditText editAddress = findViewById(R.id.editAddress);
        imm.hideSoftInputFromWindow(editAddress.getWindowToken(), 0);
        EditText editCity = findViewById(R.id.editCity);
        imm.hideSoftInputFromWindow(editCity.getWindowToken(), 0);
        EditText editState = findViewById(R.id.editState);
        imm.hideSoftInputFromWindow(editState.getWindowToken(), 0);
        EditText editZipCode = findViewById(R.id.editZipCode);
        imm.hideSoftInputFromWindow(editZipCode.getWindowToken(), 0);
        EditText editPhone = findViewById(R.id.editHome);
        imm.hideSoftInputFromWindow(editPhone.getWindowToken(), 0);
        EditText editCell = findViewById(R.id.editCell);
        imm.hideSoftInputFromWindow(editCell.getWindowToken(), 0);
        EditText editEmail = findViewById(R.id.editEmail);
        imm.hideSoftInputFromWindow(editEmail.getWindowToken(), 0);
    }

    private void setForEditing(boolean enabled) {
        EditText editName = findViewById(R.id.editName);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editCity = findViewById(R.id.editCity);
        EditText editState = findViewById(R.id.editState);
        EditText editZipCode = findViewById(R.id.editZipCode);
        EditText editHome = findViewById(R.id.editHome);
        EditText editCell = findViewById(R.id.editCell);
        EditText editEmail = findViewById(R.id.editEmail);
        Button buttonChange = findViewById(R.id.btnBirthday);
        Button buttonSave = findViewById(R.id.buttonSave);

        editName.setEnabled(enabled);
        editAddress.setEnabled(enabled);
        editCity.setEnabled(enabled);
        editState.setEnabled(enabled);
        editZipCode.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        buttonChange.setEnabled(enabled);
        buttonSave.setEnabled(enabled);

        if (enabled) {
            editName.requestFocus();
            editHome.setInputType(InputType.TYPE_CLASS_PHONE);
            editCell.setInputType(InputType.TYPE_CLASS_PHONE);
        } else {
            ScrollView s = findViewById(R.id.scrollView);
            s.fullScroll(ScrollView.FOCUS_UP);
            editHome.setInputType(InputType.TYPE_NULL);
            editCell.setInputType(InputType.TYPE_NULL);
        }
    }


    private void initChangeDateButton() {
        Button changeDate = findViewById(R.id.btnBirthday);
        changeDate.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            DatePickerDialog datePickerDialog = new DatePickerDialog(); // We create the dialog here
            datePickerDialog.show(fm, "DatePick"); // DialogFragment.show() method shows the actual dialog. For this to happen, we need the fragment manager that is above
        });
    }

    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView birthDay = findViewById(R.id.textBirthday);
        Log.i("Hello there", "hello");
        birthDay.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        currentContact.setBirthday(selectedTime);
    }

    private void initContact(int id) {

        ContactDataSource ds = new ContactDataSource(MainActivity.this);

        try {
            ds.open();
            currentContact = ds.getSpecifiedContact(id);
            Log.i("tag", "-----------------------------------------\n");

            ds.close();
        } catch (Exception e) {
            Toast.makeText(this, "Load contact failed", Toast.LENGTH_LONG).show();
        }

        EditText editName = findViewById(R.id.editName);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editCity = findViewById(R.id.editCity);
        EditText editState = findViewById(R.id.editState);
        EditText editZipCode = findViewById(R.id.editZipCode);
        EditText editHome = findViewById(R.id.editHome);
        EditText editCell = findViewById(R.id.editCell);
        EditText editEmail = findViewById(R.id.editEmail);
        TextView birthDay = findViewById(R.id.textBirthday);

        editName.setText(currentContact.getContactName());
        editAddress.setText(currentContact.getStreetAddress());
        editCity.setText(currentContact.getCity());
        editState.setText(currentContact.getState());
        editZipCode.setText(currentContact.getZipCode());
        editHome.setText(currentContact.getPhoneNumber());
        editCell.setText(currentContact.getCellNumber());
        editEmail.setText(currentContact.getEmail());
        birthDay.setText(DateFormat.format("MM/dd/yyyy", currentContact.getBirthday().getTimeInMillis()).toString());
    }

    private void initCallFunction() {
        EditText editPhone = (EditText) findViewById(R.id.editHome);
        editPhone.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                checkPhonePermission(currentContact.getPhoneNumber());
                return false;
            }
        });

        EditText editCell = (EditText) findViewById(R.id.editCell);
        editCell.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                checkPhonePermission(currentContact.getCellNumber());
                return false;
            }
        });
    }

    private void checkPhonePermission(String phoneNumber) {
        if (Build.VERSION.SDK_INT >= 23) {

            //sees if permission has already been granted and show location
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {

                //if permission denied, explain why it's needed
                if (ActivityCompat.shouldShowRequestPermissionRationale
                        (MainActivity.this,
                                Manifest.permission.CALL_PHONE)) {

                    Snackbar.make(findViewById(R.id.name), // this whole block asks for permission
                            "MyContactList requires this permission to call from " +
                                    "your contacts", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Ok", new View.OnClickListener() {


                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(
                                            MainActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            PERMISSION_REQUEST_PHONE);
                                }
                            }).show();
                } else {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            PERMISSION_REQUEST_PHONE);
                }
            } else {
                callContact(phoneNumber);
            }
        } else {
            callContact(phoneNumber);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You may now call from this app.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "You may not call from this app.", Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    private void callContact(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel: " + phoneNumber));

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            startActivity(intent);

        }
    }
}