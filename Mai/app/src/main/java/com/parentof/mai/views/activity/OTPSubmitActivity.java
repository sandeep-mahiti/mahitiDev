package com.parentof.mai.views.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.GetuserDetailsCallBack;
import com.parentof.mai.activityinterfaces.MobOTPRespCallbackInterface;
import com.parentof.mai.activityinterfaces.MobOTPValidOnlyCallback;
import com.parentof.mai.activityinterfaces.MobValidOnlyCallback;
import com.parentof.mai.activityinterfaces.MobileNumCallbackInterface;
import com.parentof.mai.activityinterfaces.SMSCallBackInterface;
import com.parentof.mai.activityinterfaces.UpdateUserGenaralResponse;
import com.parentof.mai.api.apicalls.GetUserDetailAPI;
import com.parentof.mai.api.apicalls.MobOTPValidonlyAPI;
import com.parentof.mai.api.apicalls.MobValidOnlyAPI;
import com.parentof.mai.api.apicalls.OTPSubmitAPI_Volley;
import com.parentof.mai.api.apicalls.SubmitMobNumAPI;
import com.parentof.mai.api.apicalls.UpdateUserGeneralApi;
import com.parentof.mai.model.MobValidOnlyRespModel;
import com.parentof.mai.model.getuserdetail.GetUserDetailRespModel;
import com.parentof.mai.model.mobileotprespmodel.MobileOTPRespModel;
import com.parentof.mai.model.mobilerespmodel.MobileSubmitModel;
import com.parentof.mai.model.updateuser.UpdateUserGeneralBean;
import com.parentof.mai.receivers.SmsReceiver;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OTPSubmitActivity extends AppCompatActivity implements View.OnClickListener, MobileNumCallbackInterface, SMSCallBackInterface, MobOTPRespCallbackInterface, GetuserDetailsCallBack, UpdateUserGenaralResponse, MobValidOnlyCallback, MobOTPValidOnlyCallback {

    @Bind(R.id.otpNum)
    EditText otpNum;
    @Bind(R.id.resendOtp)
    TextView resendOTP;

   /* @Bind(R.id.wrongOTP)
    TextView otpErrorText;*/

    @Bind(R.id.otpSentText)
    TextView sentText;

    @Bind(R.id.otpSentSubText)
    TextView subtext;

   /* @Bind(R.id.ortext)
    TextView ortextV;*/

    private SharedPreferences prefs;
    String mobile_num = "";

    private String TAG = " otpsubAct ";
    String email;
    private TextView orTextView;
    private TextView wrongOtpTV;



   /* public static OTPSubmitActivity instance() {
        return new OTPSubmitActivity();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpsubmit_);
        getWindow().setBackgroundDrawableResource(R.drawable.ph_bg);
        ButterKnife.bind(this);
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);

        otpNum = (EditText) findViewById(R.id.otpNum);
        orTextView = (TextView) findViewById(R.id.orText);

        wrongOtpTV= (TextView) findViewById(R.id.wrongOTPTextView);
        otpNum.addTextChangedListener(mTextEditorWatcher);
        //to get soft keyboard on screen
        otpNum.setInputType(Configuration.KEYBOARD_12KEY);
        otpNum.setKeyListener(new DigitsKeyListener());
        if (getIntent().getExtras() != null && getIntent().hasExtra(Constants.USER_EMAIL)) {
            email = getIntent().getStringExtra(Constants.USER_EMAIL);
        }
        if (prefs.contains(Constants.MOBILENUM)) {
            mobile_num = prefs.getString(Constants.MOBILENUM, "");
        } else {
            ToastUtils.displayToast("Couldn't get Mobile Number. please try again!", this);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }

        if(!prefs.getBoolean(Constants.USER_VERIFIED, false)) {
            SmsReceiver.bindListener(new SMSCallBackInterface() {
                @Override
                public void populateSMSOtp(String smsOtp) {
                    Logger.logD(TAG, "message text " + smsOtp);
                    otpNum.setText(smsOtp);
                }
            });
        }
    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

            Logger.logD(TAG, " \n Start : " + start + " \n before :" + before + " \n count : " + " \n charseq : " + s);

            if (count == 0) {
                //orTextView.setVisibility(View.VISIBLE);
                //sentText.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                //  sentText.setText(getString(R.string.otpSentString));
                //subtext.setText(getString(R.string.otpSentSubString));
                // ortextV.setVisibility(View.INVISIBLE);
            } else if (count == 3) {
                orTextView.setVisibility(View.GONE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            String text = otpNum.getText().toString();
            if (text.length() == 4) {
                callOTPSubAPI(text);
            }/*else{
                ToastUtils.displayToast("Looks like your OTP is wrong!1", OTPSubmitActivity.this);
            }*/
        }
    };


    @OnClick(R.id.resendOtp)
    void callResendOTP() {
        orTextView.setVisibility(View.GONE);
        resendBtnAction();
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void callOTPSubAPI(String text) {

        String app_id = "mai";
        String user_id = "";
        String email = "";
        try {
            if (mobile_num != null && !mobile_num.equals("")) {
                user_id = mobile_num;
            } else {
                Logger.logD(Constants.PROJECT, " calotpsubap  ");
                ToastUtils.displayToast("Couldn't get Mobile Number. please try again!", this);
                finish();
            }
            if (CheckNetwork.checkNet(this)) {

                checkMObNumChange(app_id, user_id, email, text);
                //TODO uncomment for retrofit call
                /*OTPSubmitAPI otpSubmit = new OTPSubmitAPI(this);
                otpSubmit.callOTPSubmitAPI(app_id, text, user_id, this);*/
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
                Logger.logD(Constants.PROJECT, " Please check your internet  ");

            }


        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, " resendBtnActn : ", e);
        }
    }

    void checkMObNumChange(String app_id, String user_id, String email, String text) {
        try {
            if (prefs.getBoolean(Constants.MOBNUM_CHANGE, false)) {
                MobOTPValidonlyAPI.submitOtp(this, app_id, text, mobile_num, this);
            } else {
                OTPSubmitAPI_Volley.submitOtp(this, app_id, text, user_id, email, this);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "checkMobNUmchange", e);
        }
    }

    private void resendBtnAction() {
        try {
            if (CheckNetwork.checkNet(this)) {
                otpNum.setText("");

                String mobile = prefs.getString(Constants.MOBILENUM, "");
                if (prefs.getBoolean(Constants.MOBNUM_CHANGE, false)) {
                    MobValidOnlyAPI.submitMobi(this, mobile, this);
                } else {
                    SubmitMobNumAPI submitMobile = new SubmitMobNumAPI(this);
                    submitMobile.callAPI(mobile, this);

                }
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);

            }
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, " resendBtnActn : ", e);
        }
    }


    @Override
    public void populateSMSOtp(String smsOtp) {

    }

    @Override
    public void getMobileNumResp(MobileSubmitModel mobileSubmitModel) {

        if (mobileSubmitModel.getResponseCode() == ResponseConstants.SUCCESS && mobileSubmitModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {


            ToastUtils.displayToast(mobileSubmitModel.getResponseDesc(), this);
            //otpErrorText.setVisibility(View.INVISIBLE);
            sentText.setVisibility(View.VISIBLE);
            wrongOtpTV.setVisibility(View.GONE);
            //ortextV.setVisibility(View.INVISIBLE);
            sentText.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            sentText.setText(getString(R.string.otpSentString));
            subtext.setText(getString(R.string.otpSentSubString));
            Logger.logD(Constants.PROJECT, " OTPSubmit, getMobileNumResp : " + mobileSubmitModel.getResponseDesc());

        } else {


            ToastUtils.displayToast(mobileSubmitModel.getResponseDesc(), this);
            Logger.logD(Constants.PROJECT, " OTPSubmit, getMobileNumResp : " + mobileSubmitModel.getResponseDesc());
        }
    }

    @Override
    public void otpSubmitResp(MobileOTPRespModel otpResponseModel) {
        try {

            if (otpResponseModel.getResponseCode() == ResponseConstants.SUCCESS && otpResponseModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                // prefs.edit().putBoolean(Constants.MOB_OTP_VERIFIED, true).commit();
                prefs.edit().putString(Constants.MOBILENUM, otpResponseModel.getData().getMobile()).apply();
                //callSuccessFun(otpResponseModel);
                callgetUserDetailsAPI();

            } else {
                wrongOtpTV.setVisibility(View.VISIBLE);
                sentText.setVisibility(View.GONE); //setBackgroundColor(getResources().getColor(R.color.red));
                orTextView.setVisibility(View.VISIBLE);
                //sentText.setText(getString(R.string.wrongOtpString));
                //sentText.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.sixteensp) );
                subtext.setText(getString(R.string.lets_try_again));

                ToastUtils.displayToast(otpResponseModel.getResponseDesc(), this);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "otpSubResp : ", e);
        }
    }

    private void callgetUserDetailsAPI() {
        try {
            String mobile = prefs.getString(Constants.MOBILENUM, "");
            if (CheckNetwork.checkNet(this)) {
                otpNum.setText("");
                GetUserDetailAPI.getUserDetails(this, mobile, this);
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
            }
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, " resendBtnActn : ", e);
        }
    }

   /* private void callSuccessFun(MobileOTPRespModel otpResponseModel) {
        if(otpResponseModel.getData()!=null && otpResponseModel.getData().getEmail()!=null) {
            Bundle b=new Bundle();
            b.putParcelable(Constants.USER_DATA,otpResponseModel.getData());

            //prefs.edit().putString(Constants.USER_DATA, String.valueOf(otpResponseModel.getData())).commit();
           *//* if( prefs.getBoolean(Constants.MOBNUM_CHANGE, false)) {
                Intent verified = new Intent(this, MaiDashboardActivity.class);
                verified.putExtras(b);
                startActivity(verified);
                finish();
            }else{*//*
            // prefs.edit().putBoolean(Constants.NEW_USER, false).commit();
            Intent intent = new Intent(this, ParentDetailsActivity.class);
            intent.putExtras(b);
            startActivity(intent);
            finish();
            // }

        }else{
            // prefs.edit().putBoolean(Constants.NEW_USER, true).commit();
            Intent intent = new Intent(this, ParentDetailsActivity.class);
            startActivity(intent);
            finish();
        }
    }*/

    @Override
    public void getUserDetail(GetUserDetailRespModel getUserDetailRespModel) {
        try {
            if (getUserDetailRespModel.getResponseCode() == ResponseConstants.SUCCESS && getUserDetailRespModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                if (getUserDetailRespModel.getData() != null && getUserDetailRespModel.getData().getId() != null) {

                    prefs.edit().putInt(Constants._ID, getUserDetailRespModel.getData().getId()).apply();
                    Bundle b = new Bundle();
                    b.putParcelable(Constants.USER_DATA, getUserDetailRespModel.getData());
                    //prefs.edit().putString(Constants.USER_DATA, String.valueOf(otpResponseModel.getData())).commit();
                    checkMobChange(b);

                } else {
                    // prefs.edit().putBoolean(Constants.NEW_USER, true).commit();
                    Intent intent = new Intent(this, ParentDetailsActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Intent intent = new Intent(this, ParentDetailsActivity.class);
                startActivity(intent);
                finish();

            }
        } catch (Exception e) {
            Logger.logE(TAG, " getUserDetail : ", e);
        }
    }

    private void checkMobChange(Bundle b) {
        try {
            if (prefs.getBoolean(Constants.MOBNUM_CHANGE, false)) {
                prefs.edit().putBoolean(Constants.USER_VERIFIED, true).apply();
                prefs.edit().putBoolean(Constants.MOBNUM_CHANGE, false).apply();
                Intent verified = new Intent(this, MaiDashboardActivity.class);
                verified.putExtras(b);
                startActivity(verified);
                finish();
            } else {
                // prefs.edit().putBoolean(Constants.NEW_USER, false).commit();
                Intent intent = new Intent(this, ParentDetailsActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " checkMobChange : ", e);
        }
    }

    @Override
    public void updateUserGeneralResponse(UpdateUserGeneralBean updateUserGeneralBean) {

        try {
            if (updateUserGeneralBean.getResponseCode() == ResponseConstants.SUCCESS && updateUserGeneralBean.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                String mobile = prefs.getString(Constants.MOBILENUM, "");
                if (CheckNetwork.checkNet(this)) {
                    GetUserDetailAPI.getUserDetails(this, mobile, this);
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", this);
                }
            }

        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, " updtusrGenres : ", e);
        }

    }

    @Override
    public void getMobOTP(MobValidOnlyRespModel mobValidOnlyRespModel) {
        try {
            if (mobValidOnlyRespModel.getResponseCode() == ResponseConstants.SUCCESS && mobValidOnlyRespModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {

                ToastUtils.displayToast(mobValidOnlyRespModel.getResponseDesc(), this);
                sentText.setVisibility(View.VISIBLE);
                wrongOtpTV.setVisibility(View.GONE);
                subtext.setText(getString(R.string.otpSentSubString));

                Logger.logD(Constants.PROJECT, " OTPSubmit, getMobileNumResp : " + mobValidOnlyRespModel.getResponseDesc());
            } else {
                ToastUtils.displayToast(mobValidOnlyRespModel.getResponseDesc(), this);
                Logger.logD(Constants.PROJECT, " OTPSubmit, getMobileNumResp : " + mobValidOnlyRespModel.getResponseDesc());
            }
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, " resendBtnActn : ", e);
        }

    }

    @Override
    public void getMobOTPVerify(MobValidOnlyRespModel mobValidOnlyRespModel) {

        if (mobValidOnlyRespModel.getResponseCode() == ResponseConstants.SUCCESS && mobValidOnlyRespModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
            String mobile = prefs.getString(Constants.MOBILENUM, "");
            if (CheckNetwork.checkNet(this)) {
                otpNum.setText("");
                HashMap userDetailsMap = new HashMap<>();
                userDetailsMap.put("email", email);
                userDetailsMap.put("mobile", mobile);
                String id = String.valueOf(prefs.getInt(Constants._ID, 0));
                UpdateUserGeneralApi.addUserGeneralInfo(this, userDetailsMap, id, this);
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
            }
        } else {
            wrongOtpTV.setVisibility(View.VISIBLE);
            sentText.setVisibility(View.GONE);
            subtext.setText(getString(R.string.enter_otp_again));
            ToastUtils.displayToast(mobValidOnlyRespModel.getResponseDesc(), this);
        }
    }
}
