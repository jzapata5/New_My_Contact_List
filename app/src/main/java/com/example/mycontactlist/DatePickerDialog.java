package com.example.mycontactlist;
import android.os.Bundle;
import java.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.fragment.app.DialogFragment;

public class DatePickerDialog extends DialogFragment { // all custom dialog should be created this way

    Calendar selectedDate; // This variable holds the selected date

    public interface SaveDateListener { // this is the listener that communicates with the user's actions on the dialog back to the activity
        void didFinishDatePickerDialog(Calendar selectedTime);
    }

    public DatePickerDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.select_date, container);

        getDialog().setTitle("Select Date");
        selectedDate = Calendar.getInstance(); // This mean that the instantiated date is by default whatever day it is today

        final CalendarView cv = view.findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                selectedDate.set(year, month, day);
            }
        });
        Button saveButton = view.findViewById(R.id.buttonSelect);
        saveButton.setOnClickListener(view1 -> saveItem(selectedDate));
        Button cancelButton = view.findViewById(R.id.buttonCancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    private void saveItem(Calendar selectedTime) {
        SaveDateListener activity = (SaveDateListener) getActivity();

        activity.didFinishDatePickerDialog(selectedTime);
        getDialog().dismiss();

    }
}
