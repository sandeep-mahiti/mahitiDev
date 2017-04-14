package com.parentof.mai.views.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.MobValidOnlyCallback;
import com.parentof.mai.activityinterfaces.MobileNumCallbackInterface;
import com.parentof.mai.api.apicalls.MobValidOnlyAPI;
import com.parentof.mai.api.apicalls.SubmitMobNumAPI;
import com.parentof.mai.model.MobValidOnlyRespModel;
import com.parentof.mai.model.mobilerespmodel.MobileSubmitModel;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewMobNumActivity extends AppCompatActivity implements View.OnClickListener, MobValidOnlyCallback {



    private static final String TAG = " NewMobNActiv ";
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


    ProgressBar pb;
    boolean mobileNumChanged = false;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mob_num);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(R.drawable.ph_bg);

        prefs= getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);

        mobnumChange= (TextView) findViewById(R.id.mobnumchange);
        if(prefs.getBoolean(Constants.MOBNUM_CHANGE,false)){
            mobnumChange.setVisibility(View.INVISIBLE);
        }

        if(getIntent().getExtras()!=null && getIntent().hasExtra(Constants.USER_EMAIL)) {
            email = getIntent().getStringExtra(Constants.USER_EMAIL);
        }
        //to get soft keyboard on screen
        mobileNumberET.setInputType(Configuration.KEYBOARD_12KEY);
        mobileNumberET.setKeyListener(new DigitsKeyListener());

    }


    @OnClick(R.id.mobnumVerify)
    public void mobverifyBtnClick() {
        try {
            if(CheckNetwork.checkNet(this)){
                sendMobNumtoServer();
            }else{
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " NewMobNVrfyBtnClk : ", e);
        }
    }

    private void sendMobNumtoServer() {
        mobileNum = String.valueOf(mobileNumberET.getText());
        if (mobileNum.length() == 10) {
            editor = prefs.edit();
            editor.putString(Constants.MOBILENUM, mobileNum).commit();
            MobValidOnlyAPI.submitMobi(this, mobileNum, this);

        } else {
            mobileNumberET.findFocus();
            ToastUtils.displayToast("Please enter 10 digit mobile number", this);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void getMobOTP(MobValidOnlyRespModel mobValidOnlyRespModel) {
        try {
            if (mobValidOnlyRespModel.getResponseCode() == ResponseConstants.SUCCESS && mobValidOnlyRespModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                Intent intent = new Intent(this, OTPSubmitActivity.class);
                intent.putExtra(Constants.USER_EMAIL, email);
                startActivity(intent);
                finish();
            } else {
                ToastUtils.displayToast(mobValidOnlyRespModel.getResponseDesc(), this);
            }
        }catch (Exception e) {
            Logger.logE(TAG, " getMobNumResp : ", e);
        }
    }
}
