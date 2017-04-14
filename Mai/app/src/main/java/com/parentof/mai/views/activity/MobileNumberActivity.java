package com.parentof.mai.views.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.MobileNumCallbackInterface;
import com.parentof.mai.api.apicalls.SubmitMobNumAPI;
import com.parentof.mai.model.mobilerespmodel.MobileSubmitModel;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.utils.Utility;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MobileNumberActivity extends AppCompatActivity implements View.OnClickListener, MobileNumCallbackInterface {

    private static final String TAG = "MobNumActiv ";
    @Bind(R.id.mobnum)
    EditText mobileNumberET;

    @Bind(R.id.mobnumVerify)
    Button mobnumVerifyBtn;

    @Bind(R.id.mobnumchange)
    TextView mobnumChange;

    @Bind(R.id.mobileNum)
    TextView mobileString;

    String mobileNum;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    final public static int RECEIVE_SMS = 101;
    private AlertDialog alertDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(R.drawable.ph_bg);

        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);

        checkAndroidVersion();

        //to get soft keyboard on screen

        // mobileNumberET.setInputType(Configuration.KEYBOARD_12KEY);
        //mobileNumberET.setKeyListener(new DigitsKeyListener());

        // sv.fullScroll(ScrollView.FOCUS_DOWN);


    }

    public void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ActivityCompat.checkSelfPermission(MobileNumberActivity.this, Manifest.permission.RECEIVE_SMS);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MobileNumberActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS);
            } else {
                // sendSmsToMultiNumbers();
            }
        } else {
            // sendSmsToMultiNumbers();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RECEIVE_SMS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkAndroidVersion();
                } else {
                    Logger.logD(TAG, "SEND_SMS Denied");
                    ToastUtils.displayToast("SMS Permission Denied", this);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /*@OnClick(R.id.mobnum)
    public void callMobilebg(){
        mobileNumberET.setBackgroundColor(getResources().getColor(R.color.white));
    }*/


    @OnClick(R.id.mobnumVerify)
    public void mobverifyBtnClick() {
        try {

            // uncomment below line for 3IPs popup design testing
            //activeInterventionsPopup();

            if (CheckNetwork.checkNet(this)) {
                sendMobNumtoServer();
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);

            }
        } catch (Exception e) {
            Logger.logE(TAG, " NewMobNVrfyBtnClk : ", e);
        }
    }

/*
    void activeInterventionsPopup() {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.improvementplan_popup, null);
            TextView activePlansTV = (TextView) dialogView.findViewById(R.id.alreadyActiveTV);
            activePlansTV.setText(getString(R.string.active_ip).concat(" " + childBean.getChild().getFirstName()));

            pausePlanTVBtn = (TextView) dialogView.findViewById(R.id.pausePlanTV);
            doThisLaterTVBtn = (TextView) dialogView.findViewById(R.id.doThisLaterTV);
            backTVBtn = (TextView) dialogView.findViewById(R.id.backTV);
            alertDialog.setView(dialogView);
            alertDialog1 = alertDialog.create();
            alertDialog1.show();


        } catch (Exception e) {
            Logger.logE(TAG, "activeInterventionsPopup", e);
        }
    }
*/


    private void sendMobNumtoServer() {
        mobileNum = String.valueOf(mobileNumberET.getText());
        if (mobileNum.length() == 10) {
            editor = prefs.edit();
            editor.putString(Constants.MOBILENUM, mobileNum).apply();
            SubmitMobNumAPI submitMobile = new SubmitMobNumAPI(this);
            submitMobile.callAPI(mobileNum, this);
        } else {
            mobileNumberET.findFocus();
            ToastUtils.displayToast("Please enter 10 digit mobile number", this);
        }
    }

    @OnClick(R.id.mobnumchange)
    void changeMobileNum() {
        prefs.edit().putBoolean(Constants.MOBNUM_CHANGE, true).apply();
        Intent i = new Intent(this, ParentDetailsActivity.class); // Email screen
        startActivity(i);
        finish();
    }


    @Override
    public void onClick(View v) {


    }



    @Override
    public void getMobileNumResp(MobileSubmitModel mobileSubmitModel) {
        try {
            if (mobileSubmitModel.getResponseCode() == ResponseConstants.SUCCESS && mobileSubmitModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                if(mobileSubmitModel.getData()!=null && mobileSubmitModel.getData().getId()!=null)
                    prefs.edit().putInt(Constants._ID, mobileSubmitModel.getData().getId()).apply();
                   // prefs.edit().putString(Constants._ID, String.valueOf(mobileSubmitModel.getData().getId())).apply();
                Intent intent = new Intent(this, OTPSubmitActivity.class); //ParentDetailsActivity.class);
                startActivity(intent);
                finish();
            } else {
                ToastUtils.displayToast(mobileSubmitModel.getResponseDesc(), this);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " getMobNumResp : ", e);
        }
    }
}
