package com.parentof.mai.views.activity.userProfile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.EmailOTPRespCallBack;
import com.parentof.mai.activityinterfaces.EmailRespCallbackInterface;
import com.parentof.mai.activityinterfaces.MobOTPRespCallbackInterface;
import com.parentof.mai.activityinterfaces.MobOTPValidOnlyCallback;
import com.parentof.mai.activityinterfaces.MobValidOnlyCallback;
import com.parentof.mai.activityinterfaces.MobileNumCallbackInterface;
import com.parentof.mai.activityinterfaces.OneUserCallBack;
import com.parentof.mai.activityinterfaces.UpdateImageWithGender;
import com.parentof.mai.activityinterfaces.UpdateUserAdditionalResponse;
import com.parentof.mai.activityinterfaces.UpdateUserGenaralResponse;
import com.parentof.mai.activityinterfaces.UpdateUserResponseCallBack;
import com.parentof.mai.api.apicalls.EmailOTPVerifyOnlyAPI;
import com.parentof.mai.api.apicalls.EmailVerifyOnlyAPI;
import com.parentof.mai.api.apicalls.GetOneUserAPI;
import com.parentof.mai.api.apicalls.MobOTPValidonlyAPI;
import com.parentof.mai.api.apicalls.MobValidOnlyAPI;
import com.parentof.mai.api.apicalls.UpdateUserAdditionalApi;
import com.parentof.mai.api.apicalls.UpdateUserGeneralApi;
import com.parentof.mai.model.MobValidOnlyRespModel;
import com.parentof.mai.model.emailotprespmodels.EmailOTPRespModel;
import com.parentof.mai.model.emailrespmodels.EmailRespModel;
import com.parentof.mai.model.getuserdetail.GetUserDetailRespModel;
import com.parentof.mai.model.mobileotprespmodel.MobileOTPRespModel;
import com.parentof.mai.model.mobilerespmodel.MobileSubmitModel;
import com.parentof.mai.model.updateuser.UpdateUserAdditionalBean;
import com.parentof.mai.model.updateuser.UpdateUserGeneralBean;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.DateFragment;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p/>
 * to handle interaction events.
 * Use the {@link UserGeneralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserGeneralFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener, OneUserCallBack, UpdateUserGenaralResponse, EmailRespCallbackInterface, EmailOTPRespCallBack, UpdateUserAdditionalResponse, MobileNumCallbackInterface, MobOTPRespCallbackInterface, MobValidOnlyCallback, MobOTPValidOnlyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "UserGeneralFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @Bind(R.id.ll_parent_bday)
    LinearLayout llBday;
    @Bind(R.id.edit_parent_bday)
    EditText edtParentBday;
    @Bind(R.id.addParent)
    TextView tvAddParent;
    @Bind(R.id.parent_save)
    Button parentSave;
    CheckNetwork checkNetwork;

    @Bind(R.id.userFName)
    EditText edtFName;
    @Bind(R.id.userLName)
    EditText edtLName;

    @Bind(R.id.userMobile)
    EditText edtMobile;

    @Bind(R.id.userEmail)
    EditText edtEmail;

    @Bind(R.id.userRelation)
    EditText edtRelation;

    @Bind(R.id.editing_email)
    ImageView imgEditEmail;

    @Bind(R.id.editing_mobile)
    ImageView imgEditMobile;

    @Bind(R.id.changeEmailImage)
    ImageView changeEmailImage;

    @Bind(R.id.changeMobileImage)
    ImageView changeMobileImage;
    int emailFlag = 1;
    int mobileFlag = 1;
    String[] relationArray = {"Father", "Mother", "Grandfather", "Grandmother", "Guardian - Male", "Guardian - Female"};
    HashMap<String, String> userDetailsMap;
    SharedPreferences prefs;
    TextView tvEmail;
    GetUserDetailRespModel generalBean;
    EditText otpEdit;
    AlertDialog alertDialog1;
    SharedPreferences.Editor editor;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Bind(R.id.ll_relation)
    LinearLayout llRelation;
    int flagEmailMobile = 0;
    UpdateUserResponseCallBack updateUserResponseCallBack;
    Context context;
    UpdateImageWithGender callBackImages;

    public UserGeneralFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserGeneralFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserGeneralFragment newInstance(String param1, String param2) {
        UserGeneralFragment fragment = new UserGeneralFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_general, container, false);
        ButterKnife.bind(this, view);
        prefs = context.getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
        //context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        userDetailsMap = new HashMap<>();
        edtParentBday.setOnClickListener(this);
        edtParentBday.setOnFocusChangeListener(this);
        tvAddParent.setOnClickListener(this);
        parentSave.setOnClickListener(this);
        llRelation.setOnClickListener(this);
        llRelation.setOnFocusChangeListener(this);

        edtRelation.setOnClickListener(this);
        edtRelation.setOnFocusChangeListener(this);

        checkNetwork = new CheckNetwork(context);
        edtMobile.setInputType(InputType.TYPE_NULL);
        edtEmail.setInputType(InputType.TYPE_NULL);

        //imgEditMobile.setClickable(true);

        if (CheckNetwork.checkNet(context)) {
            GetOneUserAPI.getOneUserInfo(context, String.valueOf(prefs.getInt(Constants._ID, 0)), this);
        } else {
            ToastUtils.displayToast("Please check your internet connection and try again!", context);

        }
        updateUserResponseCallBack = (UpdateUserResponseCallBack) context;
        return view;
    }

    void popupItems(final EditText itemEt, final CharSequence[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                itemEt.setText(items[item]);
            }

        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.logD(TAG, "nDestroyView()");
        try {
            if (getUserDetails())
                callInstantSaveApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadUpdateEmailPopup() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_mobile_email_popup, null);
        otpEdit = (EditText) dialogView.findViewById(R.id.updateOtpNum);
        otpEdit.addTextChangedListener(mTextEditorWatcher);
        tvEmail = (TextView) dialogView.findViewById(R.id.mobile_or_email);
        if (generalBean.getData().getEmail() != null)
            tvEmail.setText(edtEmail.getText().toString());
        alertDialog.setView(dialogView);
        alertDialog1 = alertDialog.create();
        alertDialog1.show();
        resendEMailOtp();
        dialogView.findViewById(R.id.tv_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmail.setText(generalBean.getData().getEmail());
                alertDialog1.dismiss();
            }
        });
        dialogView.findViewById(R.id.resendOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendEMailOtp();
            }
        });
        alertDialog1.setCanceledOnTouchOutside(false);

    }

    void resendEMailOtp() {
        try {
            if (CheckNetwork.checkNet(context)) {
                if (generalBean.getData().getEmail() != null)
                    EmailVerifyOnlyAPI.submitMail(context, edtEmail.getText().toString(), this);
            } else
                ToastUtils.displayToast(Constants.NO_INTERNET, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub


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
            String text = otpEdit.getText().toString();

            if (text.length() == 4) {
                if (flagEmailMobile == 1)
                    callOTPSubAPI(text);
                else if (flagEmailMobile == 2)
                    callMobileOTPSubAPI(text);
            }/*else{
                ToastUtils.displayToast("Looks like your OTP is wrong!1", OTPSubmitActivity.this);
            }*/
        }
    };


    private void callMobileOTPSubAPI(String text) {
        String app_id = "mai";

        try {

            if (CheckNetwork.checkNet(context)) {

                MobOTPValidonlyAPI.submitOtp(context, app_id, text, tvEmail.getText().toString(), this);
                // OTPSubmitAPI_Volley.submitOtp(context, app_id, text, , "", this);

            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", context);
                Logger.logD(Constants.PROJECT, " Please check your internet  ");

            }


        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, " resendBtnActn : ", e);
        }
    }

    private void callOTPSubAPI(String text) {

        String app_id = "mai";
        String user_id = "";

        try {
            if (generalBean.getData().getMobile() != null && !generalBean.getData().getMobile().equals("")) {
                user_id = generalBean.getData().getMobile();
            } else {
                Logger.logD("TAG", " calotpsubap  ");
                ToastUtils.displayToast("Couldn't get Mobile Number. please try again!", context);
                getActivity().finish();
            }
            if (CheckNetwork.checkNet(context)) {
                EmailOTPVerifyOnlyAPI.submitOtp(context, app_id, text, user_id, edtEmail.getText().toString(), this);
                //TODO uncomment for retrofit call
                /*OTPSubmitAPI otpSubmit = new OTPSubmitAPI(this);
                otpSubmit.callOTPSubmitAPI(app_id, text, user_id, this);*/
            } else {
                ToastUtils.displayToast("Please check your internet connection and try again!", context);
                Logger.logD("TAG", " Please check your internet  ");

            }


        } catch (Exception e) {
            Logger.logE("TAG", " resendBtnActn : ", e);
        }
    }

   /* @Override
    public void emailResp(EmailResponseModel emailResponseModel) {
        ToastUtils.displayToast(emailResponseModel.getResponseDesc(), context);
        Logger.logD(Constants.PROJECT, " OTPSubmit, getMobileNumResp : " + emailResponseModel.getResponseDesc());
    }*/

    @OnClick(R.id.editing_email)
    void editingEmail() {


        if (emailFlag == 1) {
            emailFlag = 2;
            imgEditEmail.setImageResource(R.drawable.floppy_normal);

            changeEmailImage.setImageResource(R.drawable.gamil_selected);
            edtEmail.setFocusable(true);
            edtEmail.setCursorVisible(true);

            edtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


        } else if (emailFlag == 2) {
            flagEmailMobile = 1;
            if (edtEmail.getText().toString().matches(emailPattern)) {
                emailFlag = 1;
                edtEmail.setInputType(InputType.TYPE_NULL);
                edtEmail.setCursorVisible(false);
                edtEmail.setFocusable(false);
                if (generalBean != null && generalBean.getData().getEmail() != null) {
                    if (edtEmail.getText().toString().trim().equals(generalBean.getData().getEmail().trim()))
                        ToastUtils.displayToast("You didn't update your email..", context);
                    else
                        loadUpdateEmailPopup();
                } else
                    loadUpdateEmailPopup();
                imgEditEmail.setImageResource(R.drawable.edit_normal_blue);

                changeEmailImage.setImageResource(R.drawable.gamil_normal);
            } else
                ToastUtils.displayToast("Invalid Email..", context);
        }
    }


    @OnClick(R.id.editing_mobile)
    void editingMobile() {
        if (mobileFlag == 1) {
            mobileFlag = 2;
            edtMobile.setInputType(InputType.TYPE_CLASS_NUMBER);
            imgEditMobile.setImageResource(R.drawable.floppy_normal);
            changeMobileImage.setImageResource(R.drawable.mobilenumber_selected);
            edtMobile.setCursorVisible(true);
        } else if (mobileFlag == 2) {
            flagEmailMobile = 2;
            if (edtMobile.getText().toString().length() == 10) {
                mobileFlag = 1;
                edtMobile.setInputType(InputType.TYPE_NULL);
                edtMobile.setCursorVisible(false);
                imgEditMobile.setImageResource(R.drawable.edit_normal_blue);

                changeMobileImage.setImageResource(R.drawable.mobilenumber_normal);
                if (generalBean != null && generalBean.getData().getMobile() != null) {
                    if (edtMobile.getText().toString().trim().equals(generalBean.getData().getMobile().trim()))
                        ToastUtils.displayToast("You didn't update your mobile..", context);
                    else
                        loadMobileUpdatePopup();
                } else
                    loadMobileUpdatePopup();
            } else {
                ToastUtils.displayToast("Please enter 10 digit mobile number", context);
            }

        }
    }

    private void sendMobNumtoServer() {
        String mobileNum = String.valueOf(edtMobile.getText());
        if (mobileNum.length() == 10) {
            editor = prefs.edit();
            editor.putString(Constants.MOBILENUM, mobileNum).commit();
           /* SubmitMobNumAPI submitMobile = new SubmitMobNumAPI(context);
            submitMobile.callAPI(edtMobile.getText().toString(), this);*/

            MobValidOnlyAPI.submitMobi(context, mobileNum, this);
        } else {
            edtMobile.findFocus();
            ToastUtils.displayToast("Please enter 10 digit mobile number", context);
        }
    }

    void loadMobileUpdatePopup() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_mobile_email_popup, null);
        otpEdit = (EditText) dialogView.findViewById(R.id.updateOtpNum);
        otpEdit.addTextChangedListener(mTextEditorWatcher);
        tvEmail = (TextView) dialogView.findViewById(R.id.mobile_or_email);
        if (generalBean.getData().getMobile() != null)
            tvEmail.setText(edtMobile.getText().toString());
        alertDialog.setView(dialogView);
        alertDialog1 = alertDialog.create();
        alertDialog1.show();
        sendMobNumtoServer();
        dialogView.findViewById(R.id.tv_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMobile.setText(generalBean.getData().getMobile());
                alertDialog1.dismiss();
            }
        });
        dialogView.findViewById(R.id.resendOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpEdit.setText("");
                sendMobNumtoServer();
            }
        });
        alertDialog1.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edit_parent_bday: {
                if (!hasFocus) {
                    return;
                }
                showDatePicker();
            }
            break;
            case R.id.ll_relation: {
                if (!hasFocus) {
                    return;
                }
                popupItems(edtRelation, relationArray);
            }
            case R.id.userRelation: {
                if (!hasFocus) {
                    return;
                }
                popupItems(edtRelation, relationArray);
            }
            break;

            default:
                break;

        }

    }

    void addParentPopup() {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addparent_popup);
        dialog.show();
    }

    void showDatePicker() {
        DialogFragment newFragment = new DateFragment(edtParentBday);
        newFragment.show(getFragmentManager(), "DatePicker");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_parent_bday:
                showDatePicker();
                break;
            case R.id.addParent:
                addParentPopup();
                break;
            case R.id.parent_save:
                try {
                    if (getUserDetails())
                        callUpdateApi();
                    else
                        ToastUtils.displayToast("No update to save", getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ll_relation:
                popupItems(edtRelation, relationArray);
                break;
            case R.id.userRelation:
                popupItems(edtRelation, relationArray);
                break;
            default:
                break;
        }
    }

    private void callUpdateApi() {
        //  getUserDetails();
        if (CheckNetwork.checkNet(context)) {
            if (userDetailsMap != null && !userDetailsMap.isEmpty()) {
                UpdateUserGeneralApi.addUserGeneralInfo(context, userDetailsMap, String.valueOf(prefs.getInt(Constants._ID, 0)), this);
                callAdditionalApi();
            } /*else {
                ToastUtils.displayToast("Missing User Information, please check!", context);
            }*/
        } else {
            ToastUtils.displayToast(Constants.NO_INTERNET, context);
        }
    }

    private void callInstantSaveApi() {
        // getUserDetails();
        if (CheckNetwork.checkNet(context)) {
            if (userDetailsMap != null && !userDetailsMap.isEmpty()) {
                UpdateUserGeneralApi.addUserGeneralInfoSave(context, userDetailsMap, String.valueOf(prefs.getInt(Constants._ID, 0)), this);
                callAdditionalApiInstantSave();
            } /*else {
                ToastUtils.displayToast("Missing User Information, please check!", context);
            }*/
        } else {
            ToastUtils.displayToast(Constants.NO_INTERNET, context);
        }
    }


    private boolean getUserDetails() {
        try {

            if (edtFName.getText().toString().trim().isEmpty()) {
                ToastUtils.displayToast("First name should not be empty", context);
                return false;
            }
            if (edtLName.getText().toString().trim().isEmpty()) {
                ToastUtils.displayToast("Last name should not be empty", context);
                return false;
            }
            if (edtEmail.getText().toString().trim().isEmpty()) {
                ToastUtils.displayToast("Email should not be empty", context);
                return false;
            }
            if (!edtEmail.getText().toString().trim().matches(emailPattern)) {
                ToastUtils.displayToast("Please enter valid Email ID", context);
                return false;
            }

            if (edtMobile.getText().toString().trim().isEmpty()) {
                ToastUtils.displayToast("Mobile number should not be empty", context);
                return false;
            }
            if (edtMobile.getText().toString().trim().length() != 10) {
                ToastUtils.displayToast("Please enter 10 digit mobile number", context);
                return false;
            }
            setHashMap();

           /* if (generalBean == null || generalBean.getData() == null || generalBean.getData().getId() == null)
                return true;*/


            String firstNameValue = edtFName.getText().toString().trim();
            String lastNameValue = edtLName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String mobile = edtMobile.getText().toString().trim();
            String relation = edtRelation.getText().toString().trim();
            String dob = edtParentBday.getText().toString().trim();
            if (!firstNameValue.equals(prefs.getString(PreferencesConstants.FIRST_NAME, "")))
                return true;
            if (!lastNameValue.equals(prefs.getString(PreferencesConstants.LAST_NAME, "")))
                return true;
            if (!email.equals(prefs.getString(PreferencesConstants.EMAIL, "")))
                return true;
            if (!mobile.equals(prefs.getString(PreferencesConstants.MOBILE, "")))
                return true;
            if (!relation.equals(prefs.getString(PreferencesConstants.RELATION, "")))
                return true;
            if (!dob.equals(prefs.getString(PreferencesConstants.DOB, "")))
                return true;

        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, "Get user Details", e);
        }
        return false;

    }


    void setHashMap() {
        userDetailsMap.put("firstname", edtFName.getText().toString().trim());
        userDetailsMap.put("lastname", edtLName.getText().toString().trim());
        edtEmail.setText(verifyEmail(edtEmail.getText().toString().trim()));
        userDetailsMap.put("email", edtEmail.getText().toString().trim());
        edtMobile.setText(verifyMobile(edtMobile.getText().toString().trim()));
        userDetailsMap.put("mobile", edtMobile.getText().toString().trim());
        userDetailsMap.put("dob", String.valueOf(edtParentBday.getText()));
        userDetailsMap.put("relation", String.valueOf(edtRelation.getText()));

    }

    private String verifyMobile(String mobile) {
        if (mobileFlag == 2) {
            if (generalBean.getData().getMobile().equals(mobile)) {
                return mobile;
            } else {
                ToastUtils.displayToast("Reverted to Old mobile", context);
                return generalBean.getData().getMobile();
            }
        }
        return mobile;
    }

    private String verifyEmail(String email) {
        if (emailFlag == 2) {
            if (generalBean.getData().getEmail().equals(email)) {
                return email;
            } else {
                ToastUtils.displayToast("Reverted to Old email", context);
                return generalBean.getData().getEmail();
            }
        }
        return email;
    }

    void saveToPreference() {
        editor.putString(PreferencesConstants.FIRST_NAME, edtFName.getText().toString().trim());
        editor.putString(PreferencesConstants.LAST_NAME, edtLName.getText().toString().trim());
        editor.putString(PreferencesConstants.EMAIL, edtEmail.getText().toString().trim());
        editor.putString(PreferencesConstants.MOBILE, edtMobile.getText().toString().trim());
        editor.apply();
    }

    private void callAdditionalApi() {
        setHashMap();
        if (CheckNetwork.checkNet(context)) {
            if (userDetailsMap != null && !userDetailsMap.isEmpty()) {

                userDetailsMap.put("profession", prefs.getString(PreferencesConstants.TYPE_OF_PROFESSION, ""));
                userDetailsMap.put("officeDays", prefs.getString(PreferencesConstants.OFFICE_DAYS, ""));
                userDetailsMap.put("officeTiming", prefs.getString(PreferencesConstants.OFFICE_TIMINGS, ""));
                userDetailsMap.put("averageIncome", prefs.getString(PreferencesConstants.AVERAGE_INCOME, ""));
                userDetailsMap.put("seperateChildRoom", String.valueOf(prefs.getBoolean(PreferencesConstants.SEPARATE_ROOM, false)));

                UpdateUserAdditionalApi.addUserAdditionalInfo(context, userDetailsMap, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
            } else {
                ToastUtils.displayToast("Missing User Information fields, please check!", context);
            }
        } else {
            ToastUtils.displayToast(Constants.NO_INTERNET, context);
        }
    }

    private void callAdditionalApiInstantSave() {
        setHashMap();
        if (CheckNetwork.checkNet(context)) {
            if (userDetailsMap != null && !userDetailsMap.isEmpty()) {

                userDetailsMap.put("profession", prefs.getString(PreferencesConstants.TYPE_OF_PROFESSION, ""));
                userDetailsMap.put("officeDays", prefs.getString(PreferencesConstants.OFFICE_DAYS, ""));
                userDetailsMap.put("officeTiming", prefs.getString(PreferencesConstants.OFFICE_TIMINGS, ""));
                userDetailsMap.put("averageIncome", prefs.getString(PreferencesConstants.AVERAGE_INCOME, ""));
                userDetailsMap.put("seperateChildRoom", String.valueOf(prefs.getBoolean(PreferencesConstants.SEPARATE_ROOM, false)));

                UpdateUserAdditionalApi.addUserAdditionalInfoSave(context, userDetailsMap, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
            } else {
                ToastUtils.displayToast("Missing User Information fields, please check!", context);
            }
        } else {
            ToastUtils.displayToast(Constants.NO_INTERNET, context);
        }
    }

    @Override
    public void getOneUserResponse(GetUserDetailRespModel generalUserBean) {
        generalBean = generalUserBean;
        if (generalUserBean.getData() == null)
            return;
        if (generalUserBean.getData().getFirstname() != null && !generalUserBean.getData().getFirstname().isEmpty())
            edtFName.setText(generalUserBean.getData().getFirstname());
        if (generalUserBean.getData().getLastname() != null && !generalUserBean.getData().getLastname().isEmpty())
            edtLName.setText(generalUserBean.getData().getLastname());
        if (generalUserBean.getData().getEmail() != null && !generalUserBean.getData().getEmail().isEmpty())
            edtEmail.setText(generalUserBean.getData().getEmail());
        if (generalUserBean.getData().getMobile() != null && !generalUserBean.getData().getMobile().isEmpty())
            edtMobile.setText(generalUserBean.getData().getMobile());

        if (!prefs.getString(PreferencesConstants.DOB, "").isEmpty())
            edtParentBday.setText(prefs.getString(PreferencesConstants.DOB, ""));
        if (!prefs.getString(PreferencesConstants.RELATION, "").isEmpty())
            edtRelation.setText(prefs.getString(PreferencesConstants.RELATION, ""));
        updateUserResponseCallBack.responseBackToActivity(generalUserBean);

    }

    @Override
    public void updateUserGeneralResponse(UpdateUserGeneralBean updateUserGeneralBean) {
        Logger.logD(Constants.PROJECT, "General bean--" + updateUserGeneralBean.getResponseDesc());
        ToastUtils.displayToast(updateUserGeneralBean.getResponseDesc(), context);
        if (ResponseConstants.SUCCESS_RESPONSE.equalsIgnoreCase(updateUserGeneralBean.getResponseDesc())) {
            prefs.edit().putString(Constants.MOBILENUM, edtMobile.getText().toString()).apply();
            prefs.edit().putString(Constants.USER_EMAIL, edtEmail.getText().toString()).apply();
            saveToPreference();
            editor.putString(PreferencesConstants.DOB, edtParentBday.getText().toString());
            editor.putString(PreferencesConstants.RELATION, edtRelation.getText().toString());
            editor.apply();
            if (edtRelation.getText().toString().isEmpty()) {
                ToastUtils.displayToast("Relation should not be empty", context);
                return;
            }
            updateUserResponseCallBack.responseUserGeneralTOActivity(updateUserGeneralBean);
            callAdditionalApi();

        }


        /*Intent i = new Intent(context, MaiDashboardActivity.class);
        startActivity(i);
        context.finish();*/
    }

    @Override
    public void emailOtpResponse(EmailOTPRespModel response) {
        // Model Classes filling
        try {
            if (response.getResponseCode() == ResponseConstants.SUCCESS && response.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast(response.getResponseDesc(), context);
                prefs.edit().putBoolean(Constants.EMAIL_OTP_VERIFIED, true).apply();
                alertDialog1.dismiss();
                setHashMap();
                UpdateUserGeneralApi.addUserGeneralInfo(context, userDetailsMap, String.valueOf(prefs.getInt(Constants._ID, 0)), this);
                //callAdditionalApi();
                Logger.logD("TAG", " OTPSubmit, getMobileNumResp : " + response.getResponseDesc());
            } else {
                ToastUtils.displayToast(response.getResponseDesc(), context);
                Logger.logD("TAG", " OTPSubmit, getMobileNumResp : " + response.getResponseDesc());
            }
        } catch (Exception e) {
            Logger.logE("TAG", " emailOtpResp : ", e);
        }
    }

    @Override
    public void updateUserAddResponse(UpdateUserAdditionalBean updateUserAdditionalBean) {
        if ("success".equalsIgnoreCase(updateUserAdditionalBean.getStatus())) {
            editor.putString(PreferencesConstants.DOB, edtParentBday.getText().toString());
            editor.putString(PreferencesConstants.RELATION, edtRelation.getText().toString());
            editor.apply();
            ToastUtils.displayToast(updateUserAdditionalBean.getStatus(), context);
        } else {
            ToastUtils.displayToast(updateUserAdditionalBean.getData(), context);
        }
    }

    @Override
    public void emailResp(EmailRespModel emailResponseModel) {
        ToastUtils.displayToast(emailResponseModel.getResponseDesc(), context);
        Logger.logD(Constants.PROJECT, " OTPSubmit, getMobileNumResp : " + emailResponseModel.getResponseDesc());
    }


    @Override
    public void getMobileNumResp(MobileSubmitModel mobileSubmitModel) {
        try {
            if (mobileSubmitModel.getResponseCode() == ResponseConstants.SUCCESS && mobileSubmitModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast(mobileSubmitModel.getResponseDesc(), context);

            } else {
                ToastUtils.displayToast(mobileSubmitModel.getResponseDesc(), context);
            }
        } catch (Exception e) {
            Logger.logE("TAG", " getMobNumResp : ", e);
        }

    }

    @Override
    public void otpSubmitResp(MobileOTPRespModel otpResponseModel) {

        try {

            if (otpResponseModel.getResponseCode() == ResponseConstants.SUCCESS && otpResponseModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                // prefs.edit().putBoolean(Constants.MOB_OTP_VERIFIED, true).commit();
                prefs.edit().putString(Constants.MOBILENUM, otpResponseModel.getData().getMobile()).apply();
                //callSuccessFun(otpResponseModel);
                if (otpResponseModel.getData() != null && otpResponseModel.getData().getMobile() != null) {
                    edtMobile.setText(otpResponseModel.getData().getMobile());
                }
                alertDialog1.dismiss();
                callUpdateApi();
                // alertDialog1.dismiss();

            } else {
                ToastUtils.displayToast(otpResponseModel.getResponseDesc(), context);
            }
        } catch (Exception e) {
            Logger.logE("TAG", "otpSubResp : ", e);
        }

    }

    @Override
    public void getMobOTP(MobValidOnlyRespModel mobValidOnlyRespModel) {
        try {
            if (mobValidOnlyRespModel.getResponseCode() == ResponseConstants.SUCCESS && mobValidOnlyRespModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast(mobValidOnlyRespModel.getResponseDesc(), context);

            } else {
                ToastUtils.displayToast(mobValidOnlyRespModel.getResponseDesc(), context);
            }
        } catch (Exception e) {
            Logger.logE("TAG", " getMobNumResp : ", e);
        }
    }

    @Override
    public void getMobOTPVerify(MobValidOnlyRespModel mobValidOnlyRespModel) {
        try {

            if (mobValidOnlyRespModel.getResponseCode() == ResponseConstants.SUCCESS && mobValidOnlyRespModel.getResponseDesc().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {

               /* if(mobValidOnlyRespModel.getData()!=null && mobValidOnlyRespModel.getData().getMobile()!=null){
                    edtMobile.setText(otpResponseModel.getData().getMobile());
                }*/
                alertDialog1.dismiss();
                callUpdateApi();


            } else {
                ToastUtils.displayToast(mobValidOnlyRespModel.getResponseDesc(), context);
            }
        } catch (Exception e) {
            Logger.logE("TAG", "otpSubResp : ", e);
        }
    }
}
