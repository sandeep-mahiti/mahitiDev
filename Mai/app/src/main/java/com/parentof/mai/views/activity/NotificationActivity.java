package com.parentof.mai.views.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.parentof.mai.R;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;

import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {


    Spinner dropdown;
    AlertDialog alert;
    ToggleButton toggleButton1, toggleButton2;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int numberOnly = 30;
    int snoozeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        toolbarInit();
       /* View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }*/
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putInt(PreferencesConstants.SNOOZE_TIME, numberOnly);
        editor.apply();
        Logger.logD(Constants.PROJECT, "if Szooze time--" + prefs.getInt(PreferencesConstants.SNOOZE_TIME, 0));
        toggleButton1 = (ToggleButton) findViewById(R.id.tooglePush);
        toggleButton1.setOnCheckedChangeListener(this);
        dropdown = (Spinner) findViewById(R.id.snoozeDropDown);
        dropdown.setOnItemSelectedListener(this);
        toggleButton2 = (ToggleButton) findViewById(R.id.toogleRemind);
        toggleButton2.setOnCheckedChangeListener(this);

        if (alert != null)
            alert.dismiss();
        if (prefs.getBoolean(PreferencesConstants.PUSH_NOTIF, false))
            toggleButton1.setChecked(true);

        if (prefs.getBoolean(PreferencesConstants.REMIND_NOTIF, false))
            toggleButton2.setChecked(true);

    }

    void getSnoozeTime(String selectedTime) {
        if (selectedTime.contains("Hour")) {
            numberOnly = Integer.parseInt(selectedTime.replaceAll("[^0-9]", "").trim());
            Logger.logD(Constants.PROJECT, "hour--" + numberOnly);
            snoozeTime = numberOnly * 60;
            Logger.logD(Constants.PROJECT, "Mints--" + snoozeTime);
            editor.putInt(PreferencesConstants.SNOOZE_TIME, snoozeTime);
            editor.apply();
        } else if (selectedTime.contains("Mints")) {
            numberOnly =Integer.parseInt(selectedTime.replaceAll("[^0-9]", "").trim());
            Logger.logD(Constants.PROJECT, "hours--" + numberOnly);
            snoozeTime = numberOnly;
            Logger.logD(Constants.PROJECT, "Mints--" + snoozeTime);
            editor.putInt(PreferencesConstants.SNOOZE_TIME, snoozeTime);
            editor.apply();
        } /*else if (selectedTime.contains("Never")) {

        }*/

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tooglePush:
                if (isChecked) {
                    Logger.logD(Constants.PROJECT, "IsCheked");
                    editor.putBoolean(PreferencesConstants.PUSH_NOTIF, true);
                    editor.apply();
                } else {
                    Logger.logD(Constants.PROJECT, "else");
                    editor.putBoolean(PreferencesConstants.PUSH_NOTIF, false);
                    editor.apply();

                }
                break;
            case R.id.toogleRemind:
                if (isChecked) {
                    Logger.logD(Constants.PROJECT, "IsCheked");
                    editor.putBoolean(PreferencesConstants.REMIND_NOTIF, true);
                    editor.apply();
                } else {
                    Logger.logD(Constants.PROJECT, "else");
                    editor.putBoolean(PreferencesConstants.REMIND_NOTIF, false);
                    editor.apply();

                }
                break;

        }

    }


    void toolbarInit() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (toolbar != null) {
                toolbar.setTitle(getResources().getString(R.string.title_activity_notification));
                toolbar.setTitleTextColor(CommonClass.getColor(this, R.color.white));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            default:
                break;

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getSnoozeTime(dropdown.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
