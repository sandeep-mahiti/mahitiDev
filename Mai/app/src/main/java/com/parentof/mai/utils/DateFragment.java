package com.parentof.mai.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.parentof.mai.R;

import java.util.Calendar;

/**
 * Created by mahiti on 18/1/17.
 */
@SuppressLint("ValidFragment")
public class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText editText;
    int parentAge;

    @SuppressLint("ValidFragment")
    public DateFragment(EditText editText) {
        this.editText = editText;
    }

    @SuppressLint("ValidFragment")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, yy, mm, dd);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return dialog;
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm + 1, dd);
    }

    public void populateSetDate(int year, int month, int day) {
        parentAge = CommonClass.getAge(year, month, day);
        Logger.logD(Constants.PROJECT, "Age--" + parentAge);
        if (Constants.PARENT_MIN_AGE < parentAge)
            editText.setText(day + "/" + month + "/" + year);
        else {
            ToastUtils.displayToast("Age should be above 16", getActivity());
            editText.setHint(R.string.dob);
        }
    }


}


