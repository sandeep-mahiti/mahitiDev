package com.parentof.mai.views.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.AddparentRespCallBack;
import com.parentof.mai.activityinterfaces.EmailRespCallbackInterface;
import com.parentof.mai.activityinterfaces.SecurityQuestionsCallBack;
import com.parentof.mai.activityinterfaces.UpdateUserGenaralResponse;
import com.parentof.mai.api.apicalls.AddParentAPI;
import com.parentof.mai.api.apicalls.EmailSubmitAPI;
import com.parentof.mai.api.apicalls.SecurityQuestionsAPI;
import com.parentof.mai.api.apicalls.UpdateUserGeneralApi;
import com.parentof.mai.model.addparentmodel.AddparentRespModel;
import com.parentof.mai.model.emailrespmodels.EmailRespModel;
import com.parentof.mai.model.getuserdetail.Data;
import com.parentof.mai.model.updateuser.UpdateUserGeneralBean;
import com.parentof.mai.model.verifyuser.failmodel.anonymus.VerifyAPIAnonymusRespModel;
import com.parentof.mai.model.verifyuser.failmodel.locked.VerifyAPILockedRespModel;
import com.parentof.mai.model.verifyuser.succesModel.Question;
import com.parentof.mai.model.verifyuser.succesModel.VerifyAPISuccessRespModel;
import com.parentof.mai.model.verifyuser.succesModel.nochild.VerifyAPINoChildRespModel;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.utils.Utility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ParentDetailsActivity extends AppCompatActivity implements EmailRespCallbackInterface, SecurityQuestionsCallBack, AddparentRespCallBack, UpdateUserGenaralResponse {

    private static final String TAG = "ParentDAct ";
    @Bind(R.id.fName)
    EditText firstName;

    @Bind(R.id.lName)
    EditText lastName;

    @Bind(R.id.eMail)
    EditText eMail;

    @Bind(R.id.beginTextBtn)
    TextView letsBegin;

    @Bind(R.id.emailErrorString)
    TextView emailErrorTV;

    @Bind(R.id.welcome_user)
    TextView welcomeUser;

    String firName;
    String lasName;
    String emMail;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String mobOtpResp;
    JSONObject mobOTPResponse;
    String userMobile;

    JSONObject usData;
    Data userData;
    private Bundle bundle;
    List<Question> secQstsAnsList;
    Dialog emailPopup;


    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_details);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        prefs.edit().putInt(PreferencesConstants.SNOOZE_TIME, 30).apply();

        try {
            getUserDetails();
            if (userData != null && userData.getEmail() != null) {
                displayData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserDetails() {
        try {

            if (getIntent().getExtras() != null) {
                bundle = getIntent().getExtras();
                userData = bundle.getParcelable(Constants.USER_DATA);
                if (userData != null && userData.getFirstname() != null) {
                    welcomeUser.setText(getString(R.string.welcome).concat(", "+userData.getFirstname()+"."));
                }
            }
            userMobile = prefs.getString(Constants.MOBILENUM, "");
            if ("".equals(userMobile)) {
                Logger.logD(Constants.PROJECT, " PDA gtusrdetl, no mbl  ");
                // ToastUtils.displayToast("Couldn't get Mobile Number. please try again!", this);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logE(TAG, " PDA , gtusrdtl : ", e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void displayData() {
        try {

            if (userData.getFirstname() != null && !this.userData.getFirstname().equalsIgnoreCase("null")) {
                prefs.edit().putString(PreferencesConstants.FIRST_NAME, this.userData.getFirstname()).apply();
                firstName.setText(this.userData.getFirstname()/*.toUpperCase()*/);
                firstName.setTextColor(getResources().getColor(R.color.text_grey));
                firstName.setEnabled(false);
                firstName.setFocusable(false);
            } else {
                firstName.setFocusable(true);
            }
            if (userData.getLastname() != null && !this.userData.getLastname().equalsIgnoreCase("null")) {
                lastName.setText(this.userData.getLastname()/*.toUpperCase()*/);
                lastName.setEnabled(false);
                lastName.setTextColor(getResources().getColor(R.color.text_grey));
                lastName.setFocusable(false);
            }
            if (userData.getEmail() != null && !this.userData.getEmail().equalsIgnoreCase("null")) {
                emMail = this.userData.getEmail();
                eMail.setTextColor(getResources().getColor(R.color.text_grey));
                eMail.setEnabled(false);
                eMail.setText(this.userData.getEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logE(TAG, " PDA , dispDat : ", e);
        }

    }


    @OnClick(R.id.beginTextBtn)
    void letsBeginInitiated() {

        try {
            firName = firstName.getText().toString();
            lasName = lastName.getText().toString();
            emMail = eMail.getText().toString().trim();
            if (firName.isEmpty()) {
                ToastUtils.displayToast("First name should not be empty", this);
                return;
            }
            if (lasName.isEmpty()) {
                ToastUtils.displayToast("Last name should not be empty", this);
                return;
            }
            if (emMail.isEmpty()) {
                ToastUtils.displayToast("Email should not be empty", this);
                return;
            }

            validateEmail();
           /* if (firName != null && !(" ".equals(firName)) && lasName != null && !(" ".equals(lasName))) {

            } else {
                ToastUtils.displayToast(" Both First and Last name are mandatory! ", this);
            }*/
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, " PDA ltBgnInit : ", e);
        }

    }
    @Override
    public void updateUserGeneralResponse(UpdateUserGeneralBean updateUserGeneralBean) {
        if (ResponseConstants.SUCCESS_RESPONSE.equalsIgnoreCase(updateUserGeneralBean.getResponseDesc()))
            callAPIToVerifyUser();
        else if(ResponseConstants.EXCEPTION_RESPONSE.equalsIgnoreCase(updateUserGeneralBean.getResponseDesc()) /*&& updateUserGeneralBean.getData().contains("Cause: com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException: Duplicate entry")*/){
            ToastUtils.displayToast("Email Already Registered with another mobile.", this);
            loadEmailExistsPOPUp();
        }
        else {
            ToastUtils.displayToast("Some thing went wrong on updating user profile", this);
            ToastUtils.displayToast("Email Already Registered with another mobile.", this);
        }

    }
    private void validateEmail() {
        try {
            if (emMail.contains("@") && emMail.contains(".") && emMail.matches(emailPattern) && Utility.isValidEmail(emMail)) {

                checkMobChnage();
            } else {
                emailErrorTV.setVisibility(View.VISIBLE);
                eMail.setTextColor(getResources().getColor(R.color.email_text_error));
                ToastUtils.displayToast(" Please check your email address! ", this);
            }
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, " PDA valEml : ", e);
        }
    }


    private void checkMobChnage() {
        if (prefs.getBoolean(Constants.MOBNUM_CHANGE, false)) {
            checknetAndCallAPi();
        } else {
            if (userData == null || userData.getEmail() == null) {
                // TODO update user for fNmae lName email API
                callUpdateProfileAPI();
            } else {
                callAPIToVerifyUser();
            }
        }
    }

    public void callUpdateProfileAPI() {
        try {
            prefs.edit().putBoolean(Constants.EMAIL_OTP_VERIFIED, true).commit();

            HashMap userDetailsMap = new HashMap<>();
            userDetailsMap.put("firstname", firName);
            userDetailsMap.put("lastname", lasName);
            userDetailsMap.put("email", emMail);
            userDetailsMap.put("mobile", userMobile);
            prefs.edit().putString(PreferencesConstants.FIRST_NAME, firName).apply();
            if (CheckNetwork.checkNet(this)) {
                UpdateUserGeneralApi.addUserGeneralInfo(this, userDetailsMap, String.valueOf(userData.getId()), this);
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
                Logger.logD(TAG, " Please check your internet  ");
            }

        } catch (Exception e) {
            Logger.logE(TAG, " emailOtpResp : ", e);
        }
    }


    private void checknetAndCallAPi() {
        try {
            if (CheckNetwork.checkNet(this)) {
                EmailSubmitAPI emailSubmitAPI = new EmailSubmitAPI(this);
                emailSubmitAPI.callEmailSubmitAPI(emMail, userMobile, this);
                //sendMobNumtoServer();
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " chkNetNclAp : ", e);
        }
    }


    private void callAPIToVerifyUser() {
        try {
//TODO uncomment for API call

            if (CheckNetwork.checkNet(this)) {
                SecurityQuestionsAPI.getQuestions(this, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
                Logger.logD(TAG, " Please check your internet ");
            }


/*
for (int m = 0; m < names1.length; m++) {
SkillData datum = new SkillData();
datum.setQuestion1(status1[m]);
datum.setAnswer1(names1[m]);
secQstsAnsList.add(datum);
}
displayQuestions();*/
        } catch (Exception e) {
            e.printStackTrace();
//MyProgress.show(this, "", "");
            ToastUtils.displayToast("Missing questions, please check!", this);
        }
//TODO uncomment for API call
// SecurityQuestionsAPI.getQuestions(this,mobile_num, this);
    }

   /* void loadEmailPopup() {
        Dialog emailPopup = new Dialog(this);
        emailPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailPopup.setContentView(R.layout.email_already_registered_popup);
        emailPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        emailPopup.show();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this, MobileNumberActivity.class);
        finish();
        startActivity(i);
    }

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

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logE(TAG, " loadEmailPopup ", e);
        }

    }

    @Override
    public void secQuestions(VerifyAPISuccessRespModel secQstns) {

        try {

            userData.setFirstname(String.valueOf(firstName.getText()));
            userData.setLastname(String.valueOf(lastName.getText()));
            userData.setEmail(emMail);
            Bundle bundleObj = new Bundle();

            bundleObj.putParcelable(Constants.SEC_QST_BEAN, secQstns);
            Intent i = new Intent(this, SecurityQuestionActivity.class);
            i.putExtra(Constants.USER_EMAIL, emMail);
            i.putExtra(Constants.USER_FNAME, String.valueOf(firstName.getText()));  // bundleObj.putParcelable(Constants.USER_FNAME, userData);
            i.putExtras(bundleObj);
            startActivity(i);
            finish();
        } catch (Exception e) {
            Logger.logE(TAG, " secQuestions ", e);
        }


    }

    @Override
    public void existNoChild(VerifyAPINoChildRespModel verifyAPINoChildRespModel) {

        prefs.edit().putBoolean(Constants.USER_VERIFIED, true).commit();
        Intent i = new Intent(this, MaiDashboardActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void newUser(VerifyAPIAnonymusRespModel newuserModel) {
        try {

            if (newuserModel.getStatus().equalsIgnoreCase(ResponseConstants.ERROR_RESPONSE)) {
                if (CheckNetwork.checkNet(this)) {
                    AddParentAPI.addParent(this, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
                    //sendMobNumtoServer();
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", this);
                }
            }
        } catch (Exception e) {
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
        try {
            if (addparentRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                prefs.edit().putBoolean(Constants.USER_VERIFIED, true).apply();
                Intent i = new Intent(this, MaiDashboardActivity.class);
                startActivity(i);
                finish();
            }

        } catch (Exception e) {
            Logger.logE(TAG, " addprntResp : ", e);
        }
    }


    private void loadEmailExistsPOPUp() {
        try {
            emailPopup = new Dialog(this);
            Window window = emailPopup.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            emailPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
            emailPopup.setContentView(R.layout.email_already_registered_popup);
            emailPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            emailPopup.setCanceledOnTouchOutside(false);
            TextView welcomeNameTv = (TextView) emailPopup.findViewById(R.id.welcomeNameTV);
            TextView mobileNUmTV = (TextView) emailPopup.findViewById(R.id.mobile_numTV);
            TextView oldNUmberTV = (TextView) emailPopup.findViewById(R.id.oldNumberTV);
            TextView newContinueTV = (TextView) emailPopup.findViewById(R.id.continueTV);
            emailPopup.show();
            welcomeNameTv.setText(getString(R.string.welcome).concat(" " + firName));
            mobileNUmTV.setText("*******879");
            mobileNUmTV.setVisibility(View.GONE);
            newContinueTV.setText(getString(R.string.continue_existing).concat(" " + userMobile));
            newContinueTV.setVisibility(View.GONE);
            oldNUmberTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailPopup.dismiss();

                    Intent i = new Intent(ParentDetailsActivity.this, MobileNumberActivity.class);
                    finish();
                    startActivity(i);
                }
            });
            newContinueTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ParentDetailsActivity.this, MobileNumberActivity.class);
                    emailPopup.dismiss();
                    finish();
                    startActivity(i);
                }
            });


        } catch (Exception e) {
            Logger.logE(TAG, " emailResp ", e);
        }
    }


    @Override
    public void emailResp(EmailRespModel emailResponseModel) {
        try {
            Logger.logD(Constants.PROJECT, " emailResp, getMobileNumResp : " + emailResponseModel.getResponseDesc());

            Bundle bundleObj = new Bundle();
            bundleObj.putParcelable(Constants.USER_DATA, emailResponseModel);
            //TODO uncomment for proper validation flow
            if (emailResponseModel.getResponseCode() == ResponseConstants.SUCCESS_ZERO && emailResponseModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                Logger.logD(Constants.PROJECT, " OTPSubmit, getMobileNumResp : " + emailResponseModel.getResponseDesc());
                Intent intent = new Intent(this, EmailOtpActivity.class);
                intent.putExtra(Constants.USER_EMAIL, emMail);
                intent.putExtra(Constants.USER_FNAME, firstName.getText().toString());
                intent.putExtras(bundleObj);
                startActivity(intent);
                finish();
                //ToastUtils.displayToast(emailResponseModel.getResponseDesc(), this);

            } else {

                ToastUtils.displayToast(emailResponseModel.getResponseDesc(), this);
                Logger.logD(Constants.PROJECT, " OTPSubmit, getMobileNumResp : " + emailResponseModel.getResponseDesc());
            }
        } catch (Exception e) {
            Logger.logE(TAG, " emailResp ", e);
        }
    }
}
