package com.example.jongseong3;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class timepicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar mCalendar = Calendar.getInstance();
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int min = mCalendar.get(Calendar.MINUTE);
        TimePickerDialog mTimePickerDialog = new TimePickerDialog(
                getContext(), this, hour, min, DateFormat.is24HourFormat(getContext())
        );
        return mTimePickerDialog;

    }
}
