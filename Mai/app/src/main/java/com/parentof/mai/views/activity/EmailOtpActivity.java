package com.parentof.mai.views.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.AddparentRespCallBack;
import com.parentof.mai.activityinterfaces.EmailOTPRespCallBack;
import com.parentof.mai.activityinterfaces.EmailRespCallbackInterface;
import com.parentof.mai.activityinterfaces.SecurityQuestionsCallBack;
import com.parentof.mai.activityinterfaces.UpdateUserGenaralResponse;
import com.parentof.mai.api.apicalls.AddParentAPI;
import com.parentof.mai.api.apicalls.EmailOtpAPi;
import com.parentof.mai.api.apicalls.EmailSubmitAPI;
import com.parentof.mai.api.apicalls.SecurityQuestionsAPI;
import com.parentof.mai.api.apicalls.UpdateUserGeneralApi;


import com.parentof.mai.model.addparentmodel.AddparentRespModel;


import com.parentof.mai.model.emailotprespmodels.EmailOTPRespModel;
import com.parentof.mai.model.emailrespmodels.EmailRespModel;
import com.parentof.mai.model.updateuser.UpdateUserGeneralBean;
import com.parentof.mai.model.verifyuser.failmodel.anonymus.VerifyAPIAnonymusRespModel;
import com.parentof.mai.model.verifyuser.failmodel.locked.VerifyAPILockedRespModel;
import com.parentof.mai.model.verifyuser.succesModel.VerifyAPISuccessRespModel;
import com.parentof.mai.model.verifyuser.succesModel.nochild.VerifyAPINoChildRespModel;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailOtpActivity extends AppCompatActivity implements EmailOTPRespCallBack, EmailRespCallbackInterface, SecurityQuestionsCallBack, AddparentRespCallBack, UpdateUserGenaralResponse {

    private static final String TAG = "EmailOtpAct ";
    @Bind(R.id.email_not_recieve)
    TextView emailNotRecieve;
    @Bind(R.id.email_resendOtp)
    TextView emailResendOtp;
    @Bind(R.id.edt_email_otp)
    EditText emailOtpEdt;
    @Bind(R.id.email_otp_tv)
    TextView emailTv;


    Bundle userDataBundle;
    EmailRespModel userData;
    private SharedPreferences prefs;
    String mobile_num = "";
    String email;
    String fName;
    String lName;
    String mobile;
    Dialog emailPopup;
    ImageView editMail;
    EditText emailET;
    private int mobileFlag=1;
    private String oldEmail;
    private HashMap<String, String> userDetailsMap;
    @Bind(R.id.wcNameTV)
    TextView welcomeNameTv;
    String oldMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_otp);
        ButterKnife.bind(this);
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setBackgroundDrawableResource(R.drawable.ph_bg);
        editMail= (ImageView) findViewById(R.id.edit_email);
        emailET= (EditText) findViewById(R.id.email_otp_tv);

        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);

        try {
            if (getIntent().getExtras() != null) {
                userDataBundle = getIntent().getExtras();
                userData = userDataBundle.getParcelable(Constants.USER_DATA);
                fName= getIntent().getStringExtra(Constants.USER_FNAME);
                if (userData != null) {
                    email = userData.getData().getEmail();
                    oldMobile=userData.getData().getMobile();
                    welcomeNameTv.setText(Constants.WLCM.concat(fName.toUpperCase()));
                }else{
                    Logger.logD(TAG, "userdata null in EmailOTPActivity ");
                }
                emailTv.setText(email);
            } else {
                ToastUtils.displayToast("Couldn't get Email Number. please try again!", this);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finish();
            }
            emailOtpEdt = (EditText) findViewById(R.id.edt_email_otp);

            emailOtpEdt.addTextChangedListener(mTextEditorWatcher);
            //to get soft keyboard on screen
            emailOtpEdt.setInputType(Configuration.KEYBOARD_12KEY);
            emailOtpEdt.setKeyListener(new DigitsKeyListener());


            editMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mobileFlag == 1) {
                        mobileFlag = 2;
                        oldEmail=String.valueOf(emailET.getText());
                        emailET.setFocusable(true);
                        emailET.setCursorVisible(true);
                        emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                        editMail.setImageResource(R.drawable.floppy_normal);
                        //editMail.setImageResource(R.drawable.mobilenumber_selected);

                    } else if (mobileFlag == 2) {
                        mobileFlag = 1;

                        emailET.setInputType(InputType.TYPE_NULL);
                        emailET.setCursorVisible(false);
                        emailET.setFocusable(false);
                        editMail.setImageResource(android.R.drawable.ic_menu_edit);
                        String newEmail=String.valueOf(emailET.getText());
                        if (newEmail.contains("@") && newEmail.contains(".")) {
                           checkMailNcalAPI(newEmail);

                        }else{
                            ToastUtils.displayToast(" Please check your email address! ", EmailOtpActivity.this);
                        }


                        //editMail.setImageResource(R.drawable.mobilenumber_normal);
                    }

                }
            });
        } catch (Exception e) {
            Logger.logE(TAG, " onCRt : ", e);
        }
        getMobileNum();
    }

    private void checkMailNcalAPI(String newEmail) {
        if (newEmail.equals(oldEmail)) {
            ToastUtils.displayToast("No change in email !", this);

        }else{
            email=newEmail;
            if (CheckNetwork.checkNet(this)) {
                emailOtpEdt.setText("");
                String mobile = prefs.getString(Constants.MOBILENUM, "");
                EmailSubmitAPI emailSubmitAPI = new EmailSubmitAPI(this);
                emailSubmitAPI.callEmailSubmitAPI(email, mobile, this);

            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);

            }
        }
    }

    private void getMobileNum() {
        try {

            if (prefs.contains(Constants.MOBILENUM)) {
                mobile_num = prefs.getString(Constants.MOBILENUM, "");
            } else {
                email=userData.getData().getEmail();
                mobile_num=userData.getData().getMobile();

               /* ToastUtils.displayToast("Couldn't get Mobile Number. please try again!", this);
                Thread.sleep(3000);
                finish();*/
            }
            if (userData.getData() != null && userData.getData().getId()!=null) {
                email = userData.getData().getEmail();
                mobile_num = userData.getData().getMobile();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " GetMob : ", e);
        }
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

            Logger.logD(TAG, " \n Start : " + start + " \n before :" + before + " \n count : " + " \n charseq : " + s);

            if (count == 0) {
                //sentText.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                // sentText.setText(getString(R.string.otpSentString));
                //subtext.setText(getString(R.string.otpSentSubString));
                // ortextV.setVisibility(View.INVISIBLE);

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
            String text = emailOtpEdt.getText().toString();
            if (text.length() == 4) {
                callOTPSubAPI(text);
            }/*else{
                ToastUtils.displayToast("Looks like your OTP is wrong!1", OTPSubmitActivity.this);
            }*/
        }
    };


    @OnClick(R.id.email_resendOtp)
    void resendEmailOtp() {
        resendBtnAction();
    }

    private void callOTPSubAPI(String text) {

        String app_id = "mai";
        String user_id = "";

        try {
           /* if (mobile_num != null && !mobile_num.equals("")) {
                user_id = mobile_num;
            } else {
                Logger.logD(TAG, " calotpsubap  ");
                ToastUtils.displayToast("Couldn't get Mobile Number. please try again!", this);
                finish();
            }*/
            if (CheckNetwork.checkNet(this)) {

                oldMobile=userData.getData().getMobile();
                EmailOtpAPi.submitOtp(this, app_id, text, oldMobile, email, this);

                //TODO uncomment for retrofit call
                /*OTPSubmitAPI otpSubmit = new OTPSubmitAPI(this);
                otpSubmit.callOTPSubmitAPI(app_id, text, user_id, this);*/
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
                Logger.logD(TAG, " Please check your internet  ");
            }


        } catch (Exception e) {
            Logger.logE(TAG, " resendBtnActn : ", e);
        }
    }

    private void resendBtnAction() {
        try {
            if (CheckNetwork.checkNet(this)) {
                emailOtpEdt.setText("");
                String mobile = prefs.getString(Constants.MOBILENUM, "");
                EmailSubmitAPI emailSubmitAPI = new EmailSubmitAPI(this);
                emailSubmitAPI.callEmailSubmitAPI(email, mobile, this);

            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);

            }
        } catch (Exception e) {
            Logger.logE(TAG, " resendBtnActn : ", e);
        }
    }

    @Override
    public void emailOtpResponse(EmailOTPRespModel response) {
        // Model Classes filling
        try {
            if (response.getResponseCode() == ResponseConstants.SUCCESS && response.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast(response.getResponseDesc(), this);
            /*Intent i = new Intent(this, SecurityQuestionActivity.class);
            //i.putExtras(bundleObj);
            startActivity(i); // This is for existing user
            finish();*/

                prefs.edit().putInt(Constants._ID, response.getData().getId()).apply();
                prefs.edit().putBoolean(Constants.EMAIL_OTP_VERIFIED, true).apply();
                Logger.logD(TAG, " OTPSubmit, getMobileNumResp : " + response.getResponseDesc());
                checkMobChange();

            } else {
                ToastUtils.displayToast(response.getResponseDesc(), this);
                Logger.logD(TAG, " OTPSubmit, getMobileNumResp : " + response.getResponseDesc());
            }
        } catch (Exception e) {
            Logger.logE(TAG, " emailOtpResp : ", e);
        }
    }

    private void checkMobChange() {
        try {

            if(prefs.getBoolean(Constants.MOBNUM_CHANGE, false)){
                checkIfNewUser();
            }else {

                fillMapwithParams();
                if (CheckNetwork.checkNet(this)) {
                    UpdateUserGeneralApi.addUserGeneralInfo(this, userDetailsMap, String.valueOf(userData.getData().getId()), this);
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", this);
                    Logger.logD(TAG, " Please check your internet  ");
                }
            }
        }catch (Exception e){
            Logger.logE(TAG, " checkMobChange : ", e);
        }
    }

    void fillMapwithParams(){

        try{
            userDetailsMap=new HashMap<>();
            userDetailsMap.put("firstname", fName);
            userDetailsMap.put("lastname",lName);
            userDetailsMap.put("email", email);
            userDetailsMap.put("mobile", mobile_num);
        }catch(Exception e){
            Logger.logE(TAG, "filMpWithPrms ",e);
        }

    }

    private void checkIfNewUser() {
        try {
            //TODO uncomment for API call
            if (CheckNetwork.checkNet(this)) {
                SecurityQuestionsAPI.getQuestions(this,  String.valueOf(prefs.getInt(Constants._ID, -1)), this);
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
                Logger.logD(TAG, " Please check your internet ");
            }
        }catch(Exception e){
            e.printStackTrace();
            Logger.logE(TAG, "chkIfNewUsr ", e);

        }
    }
        /*try {
            if (prefs.getBoolean(Constants.NEW_USER, false)) {
                //New User
                Intent i = new Intent(this, MaiDashboardActivity.class);
                startActivity(i);
                finish();
            } else {
                //Existing user
                Bundle bundleObj = new Bundle();
                bundleObj.putParcelable(Constants.USER_DATA, userData);
                prefs.edit().putBoolean(Constants.NEW_USER, false).commit();
                Intent i = new Intent(this,SecurityQuestionActivity.class);
                i.putExtras(bundleObj);
                startActivity(i); // This is for existing user
                finish();
            }
        }catch (Exception e){
            Logger.logE(TAG, "chkIfNewUsr ", e);
        }*/


    void loadEmailPopup() {
        try {
            emailPopup = new Dialog(this);
            Window window = emailPopup.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            emailPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
            emailPopup.setContentView(R.layout.user_locked_popup);
            emailPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            emailPopup.setCanceledOnTouchOutside(false);
            emailPopup.setCancelable(false);
            TextView donebtn = (TextView) emailPopup.findViewById(R.id.popUpDone);
            TextView topText = (TextView) emailPopup.findViewById(R.id.topTextTV);
            topText.setVisibility(View.GONE);
            emailPopup.show();
            donebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailPopup.dismiss();
                    finish();
                }
            });

        }catch(Exception e){
            e.printStackTrace();
            Logger.logE(TAG, " loadEmailPopup ", e);
        }

    }


    @Override
    public void secQuestions(VerifyAPISuccessRespModel secQstns) {

        Bundle b=new Bundle();
        b.putParcelable(Constants.SEC_QST_BEAN, secQstns);
        Intent i=new Intent(this, SecurityQuestionActivity.class);
        i.putExtra(Constants.USER_EMAIL, email);
        i.putExtra(Constants.USER_FNAME, fName);

        i.putExtras(b);
        startActivity(i);
        finish();
    }

    @Override
    public void existNoChild(VerifyAPINoChildRespModel verifyAPINoChildRespModel) {

        if(prefs.getBoolean(Constants.MOBNUM_CHANGE, false)){
            Intent i = new Intent(this, NewMobNumActivity.class);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(this, MaiDashboardActivity.class);
            startActivity(i);
            finish();
        }


    }

    @Override
    public void newUser(VerifyAPIAnonymusRespModel newuserModel) {
        try {

            if(newuserModel.getStatus().equalsIgnoreCase(ResponseConstants.ERROR_RESPONSE)) {
                if (CheckNetwork.checkNet(this)) {
                    AddParentAPI.addParent(this,   String.valueOf(prefs.getInt(Constants._ID, -1)), this);
                    prefs.edit().putBoolean(Constants.USER_VERIFIED, true).commit();
                    //sendMobNumtoServer();
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", this);
                }

            }
        }catch(Exception e){
            Logger.logE(TAG, " newUser : ", e);
        }

    }
    @Override
    public void blockedUser(VerifyAPILockedRespModel verifyAPILockedRespModel) {

        Logger.logD(TAG, "block User resp " + verifyAPILockedRespModel.getStatus() + "\n " + verifyAPILockedRespModel.getData());
        loadEmailPopup();
    }

    @Override
    public void addparentResp(AddparentRespModel addparentRespModel) {
        try{
            if (addparentRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)){
                Intent i=new Intent(this, MaiDashboardActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                ToastUtils.displayToast("Some thing went wrong on registering parent", this);
            }

        }catch (Exception e){
            Logger.logE(TAG, " addprntResp : ", e);
        }
    }

    @Override
    public void updateUserGeneralResponse(UpdateUserGeneralBean updateUserGeneralBean) {
        if (ResponseConstants.SUCCESS_RESPONSE.equalsIgnoreCase(updateUserGeneralBean.getResponseDesc())) {
            Logger.logD(TAG, "update user fname,lname , phone " + updateUserGeneralBean.getResponseDesc());
            checkIfNewUser();
        }
    }

    @Override
    public void emailResp(EmailRespModel emailResponseModel) {
        ToastUtils.displayToast(emailResponseModel.getResponseDesc(), this);
        Logger.logD(TAG, " OTPSubmit, getMobileNumResp : " + emailResponseModel.getResponseDesc());
    }

    /* Intent i = new Intent(this, MaiDashboardActivity.class);
        startActivity(i);
        finish();*/
}
