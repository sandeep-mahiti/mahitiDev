package com.parentof.mai.views.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {
    @Bind(R.id.ll_logout)
    LinearLayout logout;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Bind(R.id.ll_about)
    LinearLayout about;


    @Bind(R.id.ll_notification)
    LinearLayout notification;

    @Bind(R.id.ll_privacy)
    LinearLayout privacy;

    @Bind(R.id.ll_feq_qns)
    LinearLayout FreQns;

    @Bind(R.id.ll_accounts)
    LinearLayout accounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        toolbarInit();
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    @OnClick(R.id.ll_accounts)
    void accountsClick() {
        ToastUtils.displayToast("Coming soon...", this);
    }


    @OnClick(R.id.ll_privacy)
    void privacyOnClick() {
        ToastUtils.displayToast("Coming soon...", this);
    }

    @OnClick(R.id.ll_notification)
    void notificationOnClick() {
        Intent i = new Intent(SettingsActivity.this, NotificationActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.ll_feq_qns)
    void freQnsOnClick() {
        ToastUtils.displayToast("Coming soon...", this);
    }

    @OnClick(R.id.ll_logout)
    void logoutClick() {
        loadLogoutPopup();
    }

    @OnClick(R.id.ll_about)
    void aboutOnClick() {
        ToastUtils.displayToast("Coming soon...", this);
    }


    void toolbarInit() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (toolbar != null) {
                toolbar.setTitle(getResources().getString(R.string.title_activity_settings));
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

    void loadLogoutPopup() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logout_popup, null);
        alertDialog.setView(dialogView);
        TextView logoutYes = (TextView) dialogView.findViewById(R.id.logout_yes);
        logoutYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                finish();
                Intent i = new Intent(SettingsActivity.this, MobileNumberActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
        TextView logoutNO = (TextView) dialogView.findViewById(R.id.logout_no);

        final AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
        alertDialog1.setCanceledOnTouchOutside(false);
        logoutNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
    }
}
