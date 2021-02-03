 package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.example.mycontactlist.DatePickerDialog.SaveDateListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListButton();
        initMapButton();
        initToggleButton();
        initSettingsButton();
        initChangeDateButton();
        setForEditing(false);
        currentContact = new Contact();
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
                currentContact.setContactName(editContactName.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final EditText editStreetAddress = findViewById(R.id.editAddress);
        editStreetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currentContact.setStreetAddress(editStreetAddress.getText().toString()); // This code is executed when the user ends editing of the EditText.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // This method is executed when the user presses down on a key to enter it into an EditText but before the value in the EditText is actually changed.
            }

            @Override
            public void afterTextChanged(Editable s) { // this is called after the user complete editin the data and leaves the editText box
            }
        });

        final EditText editCity = findViewById(R.id.editCity);
        editStreetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currentContact.setCity(editCity.getText().toString()); // This code is executed when the user ends editing of the EditText.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // This method is executed when the user presses down on a key to enter it into an EditText but before the value in the EditText is actually changed.
            }

            @Override
            public void afterTextChanged(Editable s) { // this is called after the user complete editin the data and leaves the editText box
            }
        });

        final EditText editState = findViewById(R.id.editState); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currentContact.setState(editState.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final EditText editZipCode = findViewById(R.id.editZipCode); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currentContact.setZipCode(editZipCode.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final EditText editPhone = findViewById(R.id.editHome); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editContactName.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currentContact.setPhoneNumber(editPhone.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final EditText editCell = findViewById(R.id.editCell); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editContactName.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currentContact.setCellNumber(editCell.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final EditText editEmail = findViewById(R.id.editEmail); // this is final because it is used inside of the event loop (if you were wondering why variables are final)
        editContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currentContact.setEmail(editEmail.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
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
                }
                else {
                    wasSuccessful = dataSource.updateContact(currentContact); // if it does exist, then just update it
                }
                dataSource.close();
            }
            catch (Exception e) {
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
        EditText editCell= findViewById(R.id.editCell);
        EditText editEmail = findViewById(R.id.editEmail);
        Button buttonChange = findViewById(R.id.btnBirthday);
        Button buttonSave = findViewById(R.id.buttonSave);

        editName.setEnabled(enabled);
        editAddress.setEnabled(enabled);
        editCity.setEnabled(enabled);
        editState.setEnabled(enabled);
        editZipCode.setEnabled(enabled);
        editHome.setEnabled(enabled);
        editCell.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        buttonChange.setEnabled(enabled);
        buttonSave.setEnabled(enabled);

        if (enabled) {
            editName.requestFocus();
        }
        else {
            ScrollView s = findViewById(R.id.scrollView);
            s.fullScroll(ScrollView.FOCUS_UP);
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

}