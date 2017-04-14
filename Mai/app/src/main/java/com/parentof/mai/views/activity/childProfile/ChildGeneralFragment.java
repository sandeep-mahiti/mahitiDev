package com.parentof.mai.views.activity.childProfile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.AddChildRespCallBack;
import com.parentof.mai.activityinterfaces.ChildUpdateInterfaceCallback;
import com.parentof.mai.activityinterfaces.UpdateChildRespCallBack;
import com.parentof.mai.api.apicalls.AddChildAPI;
import com.parentof.mai.api.apicalls.UpdateChildGenAPI;
import com.parentof.mai.model.addchildmodel.AddChildRespModel;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.getchildrenmodel.Child_;
import com.parentof.mai.model.updatechild.UpdateChildRespModel;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.utils.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChildGeneralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildGeneralFragment extends Fragment implements AddChildRespCallBack, View.OnFocusChangeListener, UpdateChildRespCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "ChldGenFrgmn ";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //@Bind(R.id.child_gen_fName)
    EditText firstName;
    //@Bind(R.id.child_gen_lName)
    EditText lastName;
    //@Bind(R.id.child_gen_nName)
    EditText nickName;
    //@Bind(R.id.child_gen_dob)
    EditText dateOfBirth;
    //@Bind(R.id.child_gen_gender_male)
    RadioButton male;
    //@Bind(R.id.child_gen_gender_female)
    RadioButton female;
    //@Bind(R.id.child_gen_bgroup)
    EditText bloodGroup;
    //@Bind(R.id.child_gen_school)
    EditText school;
    //@Bind(R.id.child_gen_class)
    EditText clas;
    // @Bind(R.id.child_gen_rollNum)
    EditText rollNumber;
    EditText section;

    Button saveBtn;

    HashMap<String, String> childDetails;

    static Child childBean;
    String cIdToUpdate;
    private SharedPreferences prefs;
    SharedPreferences.Editor editor;
    //static GetChildrenRespModel getChildrenRespModelGlobal;

    private String childId;
    ChildUpdateInterfaceCallback callbackObj;
    int childAge;
    LinearLayout llBlood, llChildClass;
    Context context;


    public ChildGeneralFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1         Parameter 1.
     * @param childLocalBean Parameter 2.
     * @return A new instance of fragment ChildGeneralFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildGeneralFragment newInstance(String param1, Child childLocalBean) {
        ChildGeneralFragment fragment = new ChildGeneralFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        childBean = childLocalBean;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = getActivity();
        childDetails = new HashMap<>();
    }

    public void callInterface(ChildUpdateInterfaceCallback childUpdateInterfaceCallback) {
        callbackObj = childUpdateInterfaceCallback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_general, container, false);
        saveBtn = (Button) view.findViewById(R.id.child_general_saveBtn);
        firstName = (EditText) view.findViewById(R.id.child_gen_fName);
        lastName = (EditText) view.findViewById(R.id.child_gen_lName);
        nickName = (EditText) view.findViewById(R.id.child_gen_nName);
        dateOfBirth = (EditText) view.findViewById(R.id.child_gen_dob);
        bloodGroup = (EditText) view.findViewById(R.id.child_gen_bgroup);
        school = (EditText) view.findViewById(R.id.child_gen_school);
        clas = (EditText) view.findViewById(R.id.child_gen_class);
        clas.setOnFocusChangeListener(this);
        section = (EditText) view.findViewById(R.id.child_gen_section);
        male = (RadioButton) view.findViewById(R.id.child_gen_gender_male);
        female = (RadioButton) view.findViewById(R.id.child_gen_gender_female);
        rollNumber = (EditText) view.findViewById(R.id.child_gen_rollNum);
        llBlood = (LinearLayout) view.findViewById(R.id.ll_blood);
        dateOfBirth.setOnFocusChangeListener(this);
        bloodGroup.setOnFocusChangeListener(this);
        llBlood.setOnFocusChangeListener(this);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChildGeneral();
            }
        });
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        bloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupItems(bloodGroup, Constants.bloodGroupArray);
            }
        });
        llBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupItems(bloodGroup, Constants.bloodGroupArray);
            }
        });
        llChildClass = (LinearLayout) view.findViewById(R.id.ll_child_class);
        llChildClass.setOnFocusChangeListener(this);
        llChildClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupItems(clas, Constants.classesArray);
            }
        });
        clas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupItems(clas, Constants.classesArray);
            }
        });
        prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
        if (childBean != null) {
            childId = childBean.getChild().getId();
            setData(childBean);

        }
        male.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    male.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    female.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
                }

            }
        });

        female.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    female.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    male.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
                }

            }
        });

        //childDetailsFromBundle();

        //setData(getChildrenRespModelGlobal);
        callbackObj = (ChildUpdateInterfaceCallback) getActivity();
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.logD(TAG, "nDestroyView()");
        try {
            addChildGeneralInstant();
        } catch (Exception e) {
            Logger.logE(TAG, " ondDestryview", e);
        }

    }

    void childDetailsFromBundle() {
        try {
            if (getActivity().getIntent().getExtras() != null) {
                cIdToUpdate = getActivity().getIntent().getStringExtra(Constants.UPDATE_CHILD_ID);

                Bundle b = getActivity().getIntent().getExtras();
                if (b != null && b.getParcelable(Constants.SELECTED_CHILD) != null) {
                    childBean = b.getParcelable(Constants.SELECTED_CHILD);
                    childId = b.getString(Constants.UPDATE_CHILD_ID);
                    setData(childBean);
                }
            }
        } catch (Exception e) {
            Logger.logE(TAG, " gtChldinfo ", e);
        }

    }

    // @OnClick(R.id.child_general_saveBtn)
    void addChildGeneral() {
        try {
            if (getChildDetails())
                checkIfExistingChild();

        } catch (Exception e) {
            Logger.logE(TAG, " addChildGeneral  ", e);
        }
    }


    // @OnClick(R.id.child_general_saveBtn)
    void addChildGeneralInstant() {
        try {
            if (getChildDetails())
                checkIfExistingChildInstantSave();

        } catch (Exception e) {
            Logger.logE(TAG, " addChildGeneral  ", e);
        }
    }

    private String getDate(String dateString) {
        String dt = null;
        try {
            if (!dateString.contains("T"))
                return dateString;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = null;
            try {
                value = formatter.parse(dateString);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
            dateFormatter.setTimeZone(TimeZone.getDefault());

            try {
                dt = dateFormatter.format(value);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " ChildGen Frag , getChildren error", e);
        }
        if (dt == null)
            return dateString;
        else
            return dt;

    }

    private void checkIfExistingChild() {
        try {
            if (childBean != null && childBean.getChild().getId() != null) {
                if (CheckNetwork.checkNet(getActivity())) {
                    UpdateChildGenAPI.updateGenInfo(context, childId, childDetails, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", getActivity());
                    Logger.logD(TAG, " Please check your internet  ");
                }
            } else {
                addNewChild();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " chkIfExstChld  ", e);
        }
    }

    private void checkIfExistingChildInstantSave() {
        try {
            if (childBean != null && childBean.getChild().getId() != null) {
                if (CheckNetwork.checkNet(getActivity())) {
                    UpdateChildGenAPI.updateGenInfoSave(context, childId, childDetails, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", getActivity());
                    Logger.logD(TAG, " Please check your internet  ");
                }
            } else {
                addNewChildSave();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " chkIfExstChld  ", e);
        }
    }

    private void addNewChild() {
        try {
            if (childDetails != null && !childDetails.isEmpty()) {
                if (CheckNetwork.checkNet(getActivity())) {
                    AddChildAPI.addChild(context, childDetails, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", getActivity());

                }

            }
        } catch (Exception e) {
            Logger.logE(TAG, " addNwChld  ", e);
        }
    }

    private void addNewChildSave() {
        try {
            if (childDetails != null && !childDetails.isEmpty()) {
                if (CheckNetwork.checkNet(getActivity())) {
                    AddChildAPI.addChildSave(context, childDetails, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", getActivity());

                }

            }
        } catch (Exception e) {
            Logger.logE(TAG, " addNwChld  ", e);
        }
    }

    void popupItems(final EditText itemEt, final CharSequence[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                // Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
                itemEt.setText(items[item]);
            }

        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private boolean getChildDetails() {
        //get Child details from view entered by user.
        try {
            String dateOfBirthValue = null;
            String gender = "";
            if (male.isChecked()) {
                gender = "male";
            } else if (female.isChecked()) {
                gender = "female";
            }
            String firstNameValue = firstName.getText().toString().trim();
            String lastNameValue = lastName.getText().toString().trim();
            String nickNameValue = nickName.getText().toString().trim();
            if(dateOfBirth.getText()!=null && !dateOfBirth.getText().toString().equals(""))
                 dateOfBirthValue = Utility.dateTO_Mdy_Format(dateOfBirth.getText().toString().trim());
            String bloodGroupValue = bloodGroup.getText().toString().trim();
            String schoolValue = school.getText().toString().trim();
            String classValue = clas.getText().toString().trim();
            String sectionValue = section.getText().toString().trim();
            String rollNumberValue = rollNumber.getText().toString().trim();

            if (childBean!=null && firstNameValue.isEmpty()) {
                ToastUtils.displayToast("First name is mandatory!", getActivity());
                return false;
            }

            if (childBean!=null && lastNameValue.isEmpty()) {
                ToastUtils.displayToast("Last name is mandatory!", getActivity());
                return false;
            }
            if (childBean!=null && dateOfBirthValue!=null && dateOfBirthValue.isEmpty()) {
                ToastUtils.displayToast("Date of birth is mandatory!", getActivity());
                return false;
            }
            if (childBean!=null && gender.isEmpty()) {
                ToastUtils.displayToast("Gender is mandatory!", getActivity());
                return false;
            }
            if (childBean!=null && bloodGroupValue.isEmpty()) {
                ToastUtils.displayToast("Blood group is mandatory!", getActivity());
                return false;
            }


            childDetails.put(Constants.CHILD_FIRSTNAME, firstNameValue);
            childDetails.put(Constants.CHILD_LASTNAME, lastNameValue);
            childDetails.put(Constants.CHILD_NICKNAME, nickNameValue);
            childDetails.put(Constants.CHILD_DOB, dateOfBirthValue);
            childDetails.put(Constants.CHILD_GENDER, gender);
            childDetails.put(Constants.CHILD_BLOODGROUP, bloodGroupValue);
            childDetails.put(Constants.CHILD_SCHOOL, schoolValue);
            childDetails.put(Constants.CHILD_CLASS, classValue);
            childDetails.put(Constants.CHILD_SECTION, sectionValue);
            childDetails.put(Constants.CHILD_ROLLNUMBER, rollNumberValue);

            if (childBean == null || childBean.getChild() == null || childBean.getChild().getId() == null)
                return true;
            Child_ child = childBean.getChild();
            if (!firstNameValue.equals(child.getFirstName()))
                return true;
            if ((child.getLastName() == null && !lastNameValue.isEmpty()) || !lastNameValue.equals(child.getLastName()))
                return true;
            if ((child.getNickName() == null && !nickNameValue.isEmpty()) || !nickNameValue.equals(child.getNickName()))
                return true;
            if ((child.getDob() == null && !dateOfBirthValue.isEmpty()) || !dateOfBirthValue.equals(getDate(child.getDob())))
                return true;
            if ((child.getGender() == null && !gender.isEmpty()) || !gender.equals(child.getGender()))
                return true;
            if (!bloodGroup.getText().toString().equals(child.getBloodGroup()))
                return true;
            if ((child.getSchool() == null && !schoolValue.isEmpty()) || !schoolValue.equals(child.getSchool()))
                return true;
            if ((child.getClass_() == null && !classValue.isEmpty()) || !classValue.equals(child.getClass_()))
                return true;
            if ((child.getSection() == null && !sectionValue.isEmpty()) || !sectionValue.equals(child.getSection()))
                return true;
            if ((child.getRollNumber() == null && !rollNumberValue.isEmpty()) || !rollNumberValue.equals(child.getRollNumber()))
                return true;
        } catch (Exception e) {
            Logger.logE(TAG, "getChldDetails ", e);
        }
        return false;
    }


    @Override
    public void addChildResp(AddChildRespModel addChildRespModel) {

        try {
            if (addChildRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast("Child added successfully", context);
                childBean = new Child();
                // childBean.setChild();
                Child_ child = new Child_();
                child.setFirstName(childDetails.get(Constants.CHILD_FIRSTNAME));
                child.setNickName(childDetails.get(Constants.CHILD_NICKNAME));
                child.setLastName(childDetails.get(Constants.CHILD_LASTNAME));
                child.setDob(childDetails.get(Constants.CHILD_DOB));
                child.setGender(childDetails.get(Constants.CHILD_GENDER));
                child.setBloodGroup(childDetails.get(Constants.CHILD_BLOODGROUP));
                child.setSchool(childDetails.get(Constants.CHILD_SCHOOL));
                child.setClass_(childDetails.get(Constants.CHILD_CLASS));
                child.setSection(childDetails.get(Constants.CHILD_SECTION));
                child.setRollNumber(childDetails.get(Constants.CHILD_ROLLNUMBER));
                child.setId(addChildRespModel.getData());
                childBean.setChild(child);
                callbackObj.getUpdatedGenData(childBean.getChild());
                childId = addChildRespModel.getData();
                prefs.edit().putString("childId", addChildRespModel.getData()).apply();
                editor.putBoolean(PreferencesConstants.CALL_CHILD_API_FLAG, true);
                editor.apply();

                Logger.logD(TAG, " OTPSubmit, getMobileNumResp : " + addChildRespModel.getData());


            } else {
                ToastUtils.displayToast(addChildRespModel.getData(), getActivity());
                Logger.logD(TAG, " OTPSubmit, getMobileNumResp : " + addChildRespModel.getData());
            }
        } catch (Exception e) {
            Logger.logE(TAG, "getChldDetails ", e);
        }
    }

    public void setTextToEditText(EditText editText, String value) {
        if (value != null)
            editText.setText(value);
    }


    public void setData(Child childBean) {
        if (childBean.getChild() == null || childBean.getChild().getId().isEmpty())
            return;
        try {

            setTextToEditText(firstName, childBean.getChild().getFirstName());
            setTextToEditText(lastName, childBean.getChild().getLastName());
            setTextToEditText(nickName, childBean.getChild().getNickName());
            if (childBean.getChild().getDob() != null)
                dateOfBirth.setText(Utility.dateTo_dMy_Format(getDate(childBean.getChild().getDob())));
            setTextToEditText(bloodGroup, childBean.getChild().getBloodGroup());
            setTextToEditText(school, childBean.getChild().getSchool());
            setTextToEditText(section, childBean.getChild().getSection());
            setTextToEditText(rollNumber, childBean.getChild().getRollNumber());
            setTextToEditText(clas, childBean.getChild().getClass_());
            if (childBean.getChild().getGender() != null) {
               setChildGender(childBean);
            }
            prefs.edit().putString("childId", childBean.getId()).apply();
            editor.putBoolean(PreferencesConstants.CALL_CHILD_API_FLAG, true);
            editor.apply();
        } catch (Exception e) {
            Logger.logE(TAG, " ChildGen Frag , getChildren error", e);
        }
    }

    private void setChildGender(Child childBean) {
        try{
            if (childBean.getChild().getGender().equalsIgnoreCase("male")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    male.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
                male.setChecked(true);
            } else if (childBean.getChild().getGender().equalsIgnoreCase("female")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    female.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }
                female.setChecked(true);
            }

        }catch (Exception e){
            Logger.logE(TAG, " setChildGender, error", e);
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.child_gen_dob:
                if (!hasFocus) {
                    return;
                }
                showDatePicker();
                break;
            case R.id.child_gen_bgroup: {
                if (!hasFocus) {
                    return;
                }
                popupItems(bloodGroup, Constants.bloodGroupArray);
            }
            break;
            case R.id.ll_blood: {
                if (!hasFocus) {
                    return;
                }
                popupItems(bloodGroup, Constants.bloodGroupArray);
            }
            break;
            case R.id.child_gen_class: {
                if (!hasFocus) {
                    return;
                }
                popupItems(clas, Constants.classesArray);
            }
            break;

            case R.id.ll_child_class: {
                if (!hasFocus) {
                    return;
                }
                popupItems(clas, Constants.classesArray);
            }
            break;
            default:
                break;
        }

    }

    void showDatePicker() {
        DialogFragment newFragment = new DateFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
    }

    @Override
    public void updateChildResp(UpdateChildRespModel updateChildRespModel) {
        if (updateChildRespModel.getStatus().equals(ResponseConstants.SUCCESS_RESPONSE)) {
            ToastUtils.displayToast(updateChildRespModel.getStatus(), context);

            childBean.getChild().setFirstName(childDetails.get(Constants.CHILD_FIRSTNAME));
            childBean.getChild().setNickName(childDetails.get(Constants.CHILD_NICKNAME));
            childBean.getChild().setLastName(childDetails.get(Constants.CHILD_LASTNAME));
            childBean.getChild().setDob(childDetails.get(Constants.CHILD_DOB));
            childBean.getChild().setGender(childDetails.get(Constants.CHILD_GENDER));
            childBean.getChild().setBloodGroup(childDetails.get(Constants.CHILD_BLOODGROUP));
            childBean.getChild().setSchool(childDetails.get(Constants.CHILD_SCHOOL));
            childBean.getChild().setClass_(childDetails.get(Constants.CHILD_CLASS));
            childBean.getChild().setSection(childDetails.get(Constants.CHILD_SECTION));
            childBean.getChild().setRollNumber(childDetails.get(Constants.CHILD_ROLLNUMBER));
            callbackObj.getUpdatedGenData(childBean.getChild());

        } else {
            ToastUtils.displayToast(updateChildRespModel.getMessage(), context);
        }
    }


    /*
        private String getAge(int year, int month, int day) {
            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();

            dob.set(year, month, day);

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            Integer ageInt = new Integer(age);
            String ageS = ageInt.toString();

            return ageS;
        }*/


    @SuppressLint("ValidFragment")
    public class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
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
            childAge = CommonClass.getAge(year, month, day);
            Logger.logD(Constants.PROJECT, "Age-->" + childAge);
            if (childAge != -1 && (childAge > Constants.CHILD_MIN_AGE) && (childAge <= Constants.CHILD_MAX_AGE))
                dateOfBirth.setText(day + "/" + month + "/" + year);
            else {
                ToastUtils.displayToast("Your child age should be 1 to 16", getActivity());
                dateOfBirth.setHint(R.string.dob);

            }
        }

    }


}
