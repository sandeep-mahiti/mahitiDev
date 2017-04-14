package com.parentof.mai.views.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.AddparentRespCallBack;
import com.parentof.mai.activityinterfaces.BlockuserRespCallBack;
import com.parentof.mai.activityinterfaces.FCMTokenUpdateCallBack;
import com.parentof.mai.activityinterfaces.SecurityQuestionsCallBack;
import com.parentof.mai.api.apicalls.AddParentAPI;
import com.parentof.mai.api.apicalls.BlockuserAPI;
import com.parentof.mai.api.apicalls.FCMTokenUpdateApi;
import com.parentof.mai.api.apicalls.SecurityQuestionsAPI;
import com.parentof.mai.model.BlockuserRespModel;
import com.parentof.mai.model.addparentmodel.AddparentRespModel;
import com.parentof.mai.model.fcmtokenupdatemodel.FCMTokenUpdateRespModel;
import com.parentof.mai.model.verifyuser.failmodel.anonymus.VerifyAPIAnonymusRespModel;
import com.parentof.mai.model.verifyuser.failmodel.locked.VerifyAPILockedRespModel;
import com.parentof.mai.model.verifyuser.succesModel.Data;
import com.parentof.mai.model.verifyuser.succesModel.Question;
import com.parentof.mai.model.verifyuser.succesModel.VerifyAPISuccessRespModel;
import com.parentof.mai.model.verifyuser.succesModel.nochild.VerifyAPINoChildRespModel;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecurityQuestionActivity extends AppCompatActivity implements SecurityQuestionsCallBack, BlockuserRespCallBack, AddparentRespCallBack, FCMTokenUpdateCallBack {
    @Bind(R.id.qns_name)
    TextView questionName;
    @Bind(R.id.qns_numbers)
    TextView questionNum;
    @Bind(R.id.enter_qn_ans_edt)
    EditText enterQnAns;

    @Bind(R.id.answer_error)
    TextView llErrorAns;
    @Bind(R.id.NextBtn)
    TextView nextBtn;
    @Bind(R.id.notAble_tv)
    TextView notAble;
    @Bind(R.id.changeQn_tv)
    TextView changeQns;

    @Bind(R.id.nameTextView)
    TextView welcomeNameTV;


    com.parentof.mai.model.mobotpcreateusermodels.Data userData;
    private SharedPreferences prefs;
    String mobile_num;
    private String TAG = "SecQstns ";
    VerifyAPISuccessRespModel securityQuestionsModel;
    VerifyAPISuccessRespModel vsrm;
    Data secQDataobj;
    List<Question> secQstsAnsList;
    private ArrayList detailList;
    private int count = 0;
    private int skip = 0;
    private int rightAnswers = 0;
    private int wrongAnswers = 0;
    Dialog emailPopup;

    String email;
    String fName;
    String lName;

    String[] names1 = {"1234", "4321", "12", "4", "5"};
    String[] status1 = {"Question1", "Question2", "Question3", "Question4", "Question5"};
    private AlertDialog alert;
    private boolean topTextNeeded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_question);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(R.drawable.qns_bg);
        // EmailOtpAPi.submitOtp(this, "mai", "180211", "9620479549", "sandeep.h.r@mahiti.org", this);
        //loadEmailPopup();


        llErrorAns = (TextView) findViewById(R.id.answer_error);
        llErrorAns.setVisibility(View.INVISIBLE);
        try {
            prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
            //getQstnsFromBundle();

            Bundle b;
            checkMobNUm();
            if (getIntent().getExtras() != null) {
                b = getIntent().getExtras();
                vsrm = b.getParcelable(Constants.SEC_QST_BEAN);
                if (vsrm != null && vsrm.getData().getMin() != null) {
                    displayQuestions();
                }

                String fName = getIntent().getStringExtra(Constants.USER_FNAME);
                email = getIntent().getStringExtra(Constants.USER_EMAIL);
                if (fName != null) {
                    welcomeNameTV.setText(Constants.WLCM.concat(fName + "."));
                } else {
                    welcomeNameTV.setText(Constants.WLCM.concat("."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logE(TAG, "onCrt ", e);
        }
        //MyProgress.show(this, "", "");
        //callAPIToGetQuestions();


    }


    private void checkMobNUm() {
        if (prefs.contains(Constants.MOBILENUM)) {
            mobile_num = prefs.getString(Constants.MOBILENUM, "");
        } else {
            ToastUtils.displayToast("Couldn't get Mobile Number. please try again!", this);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getQstnsFromBundle() {

    }


    private void callAPIToGetQuestions() {
        try {
//TODO uncomment for API call
            secQstsAnsList = new ArrayList<>();
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
            Logger.logE(TAG, " clAPI2gtqst ", e);
            ToastUtils.displayToast("Missing questions, please check!", this);
        }
//TODO uncomment for API call
// SecurityQuestionsAPI.getQuestions(this,mobile_num, this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(this, MobileNumberActivity.class);
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
            if(topTextNeeded)
                topText.setVisibility(View.GONE);
            else
                topText.setVisibility(View.VISIBLE);
            emailPopup.show();
            donebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailPopup.dismiss();
                    finish();
                    Intent i = new Intent(SecurityQuestionActivity.this, MobileNumberActivity.class);
                    startActivity(i);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logE(TAG, " loadEmailPopup ", e);
        }

    }






    /*public List<SkillData> getDataBatchOne() {
        detailList = new ArrayList<>();
        for (int i = 0; i < names1.length; i++) {
            detailList.add(new SkillData(names1[i], status1[i]));
        }
        return detailList;
    }*/

    @OnClick(R.id.NextBtn)
    void next() {
        String ans = String.valueOf(enterQnAns.getText());
        if (ans != null && !"".equals(ans)) {
            chekcCorrectAns(ans);

        } else {
            ToastUtils.displayToast(" Answer cannot be empty!", this);
        }


    }

    @OnClick(R.id.enter_qn_ans_edt)
    void showAnsPopUps() {
        try {
            if (alert != null && alert.isShowing()) {
                alert.dismiss();
            } else {
                loadPopUpForAns(secQstsAnsList.get(count - 1).getQuestion());
            }
        } catch (Exception e) {
            Logger.logE(TAG, " showAnsPopUps : ", e);
        }
    }


    void loadPopUpForAns(String qns) {
        if (qns.contains("gender")) {
            hideSoftKeyboard();
            popupItems(enterQnAns, getResources().getStringArray(R.array.gender_array));
            enterQnAns.setCursorVisible(false);
        } else if (qns.contains("blood")) {
            hideSoftKeyboard();
            popupItems(enterQnAns, Constants.bloodGroupArray);
            enterQnAns.setCursorVisible(false);
        } else if (qns.contains("class")) {
            hideSoftKeyboard();
            popupItems(enterQnAns, Constants.classesArray);
            enterQnAns.setCursorVisible(false);
        }
    }


    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }catch (Exception e){
            Logger.logE(TAG, "hideSoftKeyboard : ", e);
        }
    }

    void popupItems(final EditText itemEt, final CharSequence[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                // Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
                itemEt.setText(items[item]);
            }

        });
        alert = builder.create();
        alert.show();
    }


    private void chekcCorrectAns(String answer) {
        try {
            if (answer.equalsIgnoreCase(secQstsAnsList.get(count - 1).getAnswer())) {
                rightAnswers++;
                enterQnAns.setText("");
                Logger.logD(TAG, " right answer ");

            } else {
                // wrong answers

                wrongAnswers = wrongAnswers + 1;
                llErrorAns.setVisibility(View.VISIBLE);
                llErrorAns.setText("Number of wrong answers entered " + wrongAnswers);
                Logger.logD(TAG, " wrong answer ");
                //ToastUtils.displayToast(getString(R.string.error_qn), this);

            }
            setNextQuestion();
        } catch (Exception e) {
            Logger.logE(TAG, " chekcCorrectAns ", e);
        }

    }

    private void setNextQuestion() {
        try {
            if (rightAnswers < secQDataobj.getMin()) {
                if (count - 1 < secQstsAnsList.size() && wrongAnswers < secQDataobj.getMin()) {
                    // llErrorAns.setVisibility(View.INVISIBLE);
                    count = count + 1;
                    enterQnAns.setText("");
                    questionNum.setText("Security question ".concat(" " + String.valueOf(count)));
                    questionName.setText(secQstsAnsList.get(count - 1).getQuestion());

                } else {
                    checkNetAndCallAPI();
                    Logger.logD(TAG, "User locked ");
                }
            } else {
                //prefs.edit().putBoolean(Constants.SEC_QST_VERIFIED, true).commit();

                if (prefs.getBoolean(Constants.MOBNUM_CHANGE, false)) {
                    Intent intent = new Intent(this, NewMobNumActivity.class);
                    intent.putExtra(Constants.USER_EMAIL, email);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtils.displayToast("Successfully verified user!.", this);
                    prefs.edit().putBoolean(Constants.USER_VERIFIED, true).apply();
                    /* update prefs token to server*/
                    if (CheckNetwork.checkNet(this))
                        FCMTokenUpdateApi.updateToken(this, String.valueOf(prefs.getInt(Constants._ID, -1)), prefs.getString(PreferencesConstants.FCM_TOKEN, ""), this);
                    else
                        Logger.logD(TAG, "No internet connection ");


                }

            }
        } catch (Exception e) {
            Logger.logE(TAG, " setNxtQ : ", e);
        }
    }

    private void checkNetAndCallAPI() {

        if (CheckNetwork.checkNet(this)) {

            BlockuserAPI.blockUser(this, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
            //sendMobNumtoServer();
        } else {
            ToastUtils.displayToast("Please check your internet connection and try again!", this);
        }
    }

    @OnClick(R.id.changeQn_tv)
    void changeQstns() {
        try {
            // llErrorAns.setVisibility(View.INVISIBLE);
            enterQnAns.setText("");
            if (count - 1 < secQstsAnsList.size()) {
                checkToSkip();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " changeQstns : ", e);
        }
    }

    private void checkToSkip() {
        try {
            if (secQDataobj.getMin() < secQDataobj.getQuestions().size()) {
                if (skip < 2) {
                    skip++;
                    count = count + 1;
                    questionNum.setText("Security question ".concat(" " + String.valueOf(count)));
                    questionName.setText(secQstsAnsList.get(count - 1).getQuestion());
                  /*  if (secQstsAnsList.get(count - 1).getQuestion().contains("gender") || secQstsAnsList.get(count - 1).getQuestion().contains("blood") ||secQstsAnsList.get(count - 1).getQuestion().contains("class")) {
                        enterQnAns.setFocusable(false);
                        hideSoftKeyboard();
                        enterQnAns.setCursorVisible(false);
                    }else{
                        enterQnAns.setFocusable(true);
                        enterQnAns.setCursorVisible(true);
                    }*/

                } else {
                    ToastUtils.displayToast("You can skip only 2 questions!", this);
                }
            }
        } catch (Exception e) {
            Logger.logE(TAG, " checkToSkip : ", e);
        }
    }


    private void displayQuestions() {

        try {
            //TODO uncomment for API call
            secQDataobj = vsrm.getData();
            secQstsAnsList = secQDataobj.getQuestions();
            if (secQstsAnsList != null && !secQstsAnsList.isEmpty()) {
                count = count + 1;
                questionNum.setText("Security question ".concat(" " + String.valueOf(count)));
                questionName.setText(secQstsAnsList.get(count - 1).getQuestion());
               /* if (secQstsAnsList.get(count - 1).getQuestion().contains("gender") || secQstsAnsList.get(count - 1).getQuestion().contains("blood") ||secQstsAnsList.get(count - 1).getQuestion().contains("class")) {
                    enterQnAns.setFocusable(false);
                    hideSoftKeyboard();
                    enterQnAns.setCursorVisible(false);
                }else{
                    enterQnAns.setFocusable(true);
                    enterQnAns.setCursorVisible(true);
                }*/

            } else {
                ToastUtils.displayToast("Security questions unavailable!", this);
            }
            // MyProgress.show(this, "", "");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logE(TAG, " dispQstns : ", e);
            //MyProgress.show(this, "", "");
        }

    }

    @Override
    public void blockUserResp(BlockuserRespModel blockuserRespModel) {

        try {
            if (blockuserRespModel.getStatus().equals(ResponseConstants.SUCCESS_RESPONSE)) {
                //TODO clear prefs for OTP and secQstns verifications, need clarification
                //prefs.edit().clear();
                //prefs.edit().remove(Constants)
                ToastUtils.displayToast(blockuserRespModel.getData(), this);
                Logger.logD(TAG, "block User resp " + blockuserRespModel.getStatus() + "\n " + blockuserRespModel.getData());
                topTextNeeded=true;
                loadEmailPopup();
            } else {
                ToastUtils.displayToast(blockuserRespModel.getData(), this);
                Logger.logD(TAG, "block User resp " + blockuserRespModel.getStatus() + "\n " + blockuserRespModel.getData());
                finish();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " BlkUsrResp : ", e);
        }

    }

    @Override
    public void secQuestions(VerifyAPISuccessRespModel secQstns) {
        try {
            if (secQstns != null) {
                Logger.logD(TAG, "Sec qstns : " + secQstns.toString());
                securityQuestionsModel = secQstns; //gson.fromJson(data,SecurityQuestionsModel.class);
                displayQuestions();
            } else {
                ToastUtils.displayToast("Security Questions Unavailable ", this);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " secQstns : ", e);
        }
    }

    @Override
    public void existNoChild(VerifyAPINoChildRespModel verifyAPINoChildRespModel) {
        try {
            if (verifyAPINoChildRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE) && !verifyAPINoChildRespModel.getData().getHaveChild()) {
                //TODO clear prefs for OTP and secQstns verifications, need clarification
                prefs.edit().putBoolean(Constants.USER_VERIFIED, true).apply();
                Intent i = new Intent(this, MaiDashboardActivity.class);
                startActivity(i);
                finish();
                Logger.logD(TAG, "block User resp " + verifyAPINoChildRespModel.getStatus() + "\n " + verifyAPINoChildRespModel.getData());

            } else {
                //ToastUtils.displayToast(verifyAPILockedRespModel.getData(), this);
                Logger.logD(TAG, "block User resp " + verifyAPINoChildRespModel.getStatus() + "\n " + verifyAPINoChildRespModel.getData());
                finish();
            }

        } catch (Exception e) {
            Logger.logE(TAG, " BlkUsrResp : ", e);
        }

    }

    @Override
    public void newUser(VerifyAPIAnonymusRespModel newuserModel) {
        try {
            if (CheckNetwork.checkNet(this)) {
                AddParentAPI.addParent(this, String.valueOf(prefs.getInt(Constants._ID, 0)), this);
                //sendMobNumtoServer();
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", this);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " BlkUsrResp : ", e);
        }
    }

    @Override
    public void blockedUser(VerifyAPILockedRespModel verifyAPILockedRespModel) {
        try {
            if (verifyAPILockedRespModel.getStatus().equalsIgnoreCase(ResponseConstants.ERROR_RESPONSE) && verifyAPILockedRespModel.getData().getIsLocked()) {
                //TODO clear prefs for OTP and secQstns verifications, need clarification
                //prefs.edit().clear();
                //prefs.edit().remove(Constants)
                //ToastUtils.displayToast(verifyAPILockedRespModel.getData(), this);
                Logger.logD(TAG, "block User resp " + verifyAPILockedRespModel.getStatus() + "\n " + verifyAPILockedRespModel.getData());
                topTextNeeded=false;
                loadEmailPopup();
            } else {
                //ToastUtils.displayToast(verifyAPILockedRespModel.getData(), this);
                Logger.logD(TAG, "block User resp " + verifyAPILockedRespModel.getStatus() + "\n " + verifyAPILockedRespModel.getData());
                finish();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " BlkUsrResp : ", e);
        }

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
            Logger.logE(TAG, " BlkUsrResp : ", e);
        }

    }

    @Override
    public void tokenUpdated(FCMTokenUpdateRespModel fcmTokenUpdateRespModel) {
        if (ResponseConstants.SUCCESS_RESPONSE.equalsIgnoreCase(fcmTokenUpdateRespModel.getStatus())) {
            Logger.logD(TAG, fcmTokenUpdateRespModel.getData());
            Intent verified = new Intent(this, MaiDashboardActivity.class);
            finish();
            startActivity(verified);
        } else {
            if (fcmTokenUpdateRespModel.getMessage() != null)
                Logger.logD(TAG, fcmTokenUpdateRespModel.getMessage());
            else if (fcmTokenUpdateRespModel.getData() != null)
                Logger.logD(TAG, fcmTokenUpdateRespModel.getData());
        }
    }
}
