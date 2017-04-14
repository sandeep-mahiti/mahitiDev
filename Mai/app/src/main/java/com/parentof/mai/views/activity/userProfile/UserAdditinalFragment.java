package com.parentof.mai.views.activity.userProfile;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.UpdateUserAdditionalResponse;
import com.parentof.mai.api.apicalls.UpdateUserAdditionalApi;
import com.parentof.mai.model.getchildrenmodel.AdditionalInfo;
import com.parentof.mai.model.updateuser.UpdateUserAdditionalBean;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UserAdditinalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAdditinalFragment extends Fragment implements View.OnFocusChangeListener, UpdateUserAdditionalResponse, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "UsrAddiFrag ";
    AdditionalInfo additionalInfo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText professionEdit;
    EditText incomeEdit;
    @Bind(R.id.txtMon)
    TextView txtMon;
    @Bind(R.id.txtTue)
    TextView txtTue;
    @Bind(R.id.txtWed)
    TextView txtWed;
    @Bind(R.id.txtThu)
    TextView txtThu;
    @Bind(R.id.txtFri)
    TextView txtFri;
    @Bind(R.id.txtSat)
    TextView txtSat;
    @Bind(R.id.txtSun)
    TextView txtSun;
    HashMap<String, Boolean> officeDaysMap = new HashMap<>();
    List<String> daysList = new ArrayList<>();

    @Bind(R.id.fromOfficeTime)
    TextView fromOTET;
    @Bind(R.id.toOfficeTime)
    TextView toOTET;

    String office_timings;

    @Bind(R.id.checkBox_room)
    CheckBox chSeparateRoom;
    HashMap<String, String> userAddMap = new HashMap<>();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String listString = "";

    boolean fromFlag;

    private int hour;
    private int minute;

    @Bind(R.id.ll_profession)
    LinearLayout llProfession;
    @Bind(R.id.ll_income)
    LinearLayout llIncome;
    Context context;


    final CharSequence[] income = {"0 - 1,00,000", "1,00,000 - 2,50,000",
            "2,50,000 - 5,00,000",
            "5,00,000 - 10,00,000",
            "10,00,000 - 25,00,000",
            "25,00,000 - 50,00,000",
            "50,00,000 and above"};
    final CharSequence[] profession = {"Self Employed", "Homemaker", "IT Professional", "Law / Legal",
            "Medical / Healthcare", "Finance / Accounting", "Logistics", "Creative",
            "Teaching/Education", "Engineer", "Human Resource", "Sports person",
            "Sales", "Marketing", "Government/Civil Service", "Manufacturing", "Agriculture",
            "Administration", "Retired", "Nonprofit", "Science and Research", "Others"};
    private boolean toToast = true;


    public UserAdditinalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAdditinalFragment newInstance(AdditionalInfo additionalInfo) {
        UserAdditinalFragment fragment = new UserAdditinalFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, additionalInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        /*if (getArguments() != null) {
            additionalInfo = getArguments().getParcelable(ARG_PARAM1);
            // Logger.logD("additionalInfo", "additionalInfo==" + additionalInfo.getOfficeDays());

        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_additinal, container, false);
        ButterKnife.bind(this, view);
        prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
        getAdditionalInfo();
        Button parentSave = (Button) view.findViewById(R.id.parent_save);
        parentSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveSelectedOfficeDays(officeDaysMap);
                for (int i = 0; i < daysList.size(); i++) {
                    Logger.logD("for", "for==" + daysList.get(i));
                }
                toToast = true;
                callUpdateApi();

            }
        });

        /********* display current time on screen Start ********/

        final Calendar c = Calendar.getInstance();
        // Current Hour
        hour = c.get(Calendar.HOUR_OF_DAY);
        // Current Minute
        minute = c.get(Calendar.MINUTE);

        // set current time into output textview
        // updateTime(hour, minute);

        /********* display current time on screen End ********/
        professionEdit = (EditText) view.findViewById(R.id.profession);
        professionEdit.setOnFocusChangeListener(this);
        professionEdit.setOnClickListener(this);
        llProfession.setOnClickListener(this);
        llProfession.setOnFocusChangeListener(this);
        toOTET.setOnClickListener(this);
        toOTET.setOnFocusChangeListener(this);
        fromOTET.setOnClickListener(this);
        fromOTET.setOnFocusChangeListener(this);

        incomeEdit = (EditText) view.findViewById(R.id.income);
        incomeEdit.setOnFocusChangeListener(this);
        incomeEdit.setOnClickListener(this);
        llIncome.setOnClickListener(this);
        llIncome.setOnFocusChangeListener(this);

        setAdditionalInformation();
        return view;
    }

    void getAdditionalInfo() {
        String str = null;
        if (prefs.getString(PreferencesConstants.OFFICE_DAYS, "") != null)
            str = prefs.getString(PreferencesConstants.OFFICE_DAYS, "");
        List<String> officeDaysSplitList = Arrays.asList(str.split(","));
        Logger.logD("officeDaysSplitList", "officeDaysSplitList==" + officeDaysSplitList.size());
        if (officeDaysSplitList != null && !officeDaysSplitList.isEmpty())
            setOfficeDays(officeDaysSplitList);

    }

    void setOfficeDays(List<String> officeDaysSplitList) {
        for (int i = 0; i < officeDaysSplitList.size(); i++) {
            Logger.logD(Constants.PROJECT, "List--" + officeDaysSplitList.get(i));
            if ("Mon".equalsIgnoreCase(officeDaysSplitList.get(i))) {
                txtMon.setTextColor(getResources().getColor(R.color.white));
                txtMon.setBackgroundResource(R.color.bg_color);
                txtMon.setTag("2");
                officeDaysMap.put("mon", true);
            } else if ("Tue".equalsIgnoreCase(officeDaysSplitList.get(i))) {
                txtTue.setTextColor(getResources().getColor(R.color.white));
                txtTue.setBackgroundResource(R.color.bg_color);
                txtTue.setTag("2");
                officeDaysMap.put("tue", true);
            } else if ("Wed".equalsIgnoreCase(officeDaysSplitList.get(i))) {
                txtWed.setTextColor(getResources().getColor(R.color.white));
                txtWed.setBackgroundResource(R.color.bg_color);
                txtWed.setTag("2");
                officeDaysMap.put("wed", true);
            } else if ("Thr".equalsIgnoreCase(officeDaysSplitList.get(i))) {
                txtThu.setTextColor(getResources().getColor(R.color.white));
                txtThu.setBackgroundResource(R.color.bg_color);
                txtThu.setTag("2");
                officeDaysMap.put("thr", true);
            } else if ("Fri".equalsIgnoreCase(officeDaysSplitList.get(i))) {
                txtFri.setTextColor(CommonClass.getColor(getActivity(), R.color.white));
                txtFri.setBackgroundResource(R.color.bg_color);
                txtFri.setTag("2");
                officeDaysMap.put("fri", true);
            } else if ("Sat".equalsIgnoreCase(officeDaysSplitList.get(i))) {
                txtSat.setTextColor(getResources().getColor(R.color.white));
                txtSat.setBackgroundResource(R.color.bg_color);
                txtSat.setTag("2");
                officeDaysMap.put("sat", true);
            } else if ("Sun".equalsIgnoreCase(officeDaysSplitList.get(i))) {
                txtSun.setTextColor(getResources().getColor(R.color.white));
                txtSun.setBackgroundResource(R.color.bg_color);
                txtSun.setTag("2");
                officeDaysMap.put("sun", true);
            }
        }
    }

    private void callUpdateApi() {
        getUserDetails();
        if (CheckNetwork.checkNet(getActivity())) {
            if (professionEdit.getText().toString().isEmpty()) {
                ToastUtils.displayToast("Please select type of profession", context);
            } else if (incomeEdit.getText().toString().isEmpty()) {
                ToastUtils.displayToast("Please select average income", context);
            } else if (userAddMap != null && !userAddMap.isEmpty()) {
                UpdateUserAdditionalApi.addUserAdditionalInfo(context, userAddMap, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
            }
        } else {
            ToastUtils.displayToast(Constants.NO_INTERNET, context);
        }
    }

    /*private void callUpdateApiInstantSave() {
        getUserDetails();
        if (CheckNetwork.checkNet(getActivity())) {
            if (userAddMap != null && !userAddMap.isEmpty()) {
                UpdateUserAdditionalApi.addUserAdditionalInfoSave(context, userAddMap, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
            } else {
                ToastUtils.displayToast("Missing User Information, please check!", context);
            }
        } else {
            ToastUtils.displayToast(Constants.NO_INTERNET, context);
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.logD(TAG, "nDestroyView()");
        retrieveSelectedOfficeDays(officeDaysMap);
        for (int i = 0; i < daysList.size(); i++) {
            Logger.logD("for", "for==" + daysList.get(i));
        }
        toToast = false;
        callUpdateApi();
    }

    void getUserDetails() {
        listString = "";
        for (String s : daysList) {
            listString += s + ",";
        }

        office_timings = fromOTET.getText().toString().concat(" - " + toOTET.getText().toString());
        Logger.logD(Constants.PROJECT, "listString--" + listString);
        userAddMap.put("profession", professionEdit.getText().toString().trim());
        userAddMap.put("officeDays", listString);
        userAddMap.put("officeTiming", office_timings);
        userAddMap.put("averageIncome", incomeEdit.getText().toString().trim());
        if (chSeparateRoom.isChecked()) {
            userAddMap.put("seperateChildRoom", "yes");
        } else {
            userAddMap.put("seperateChildRoom", "no");
        }

        userAddMap.put("dob", prefs.getString(PreferencesConstants.DOB, ""));
        userAddMap.put("relation", prefs.getString(PreferencesConstants.RELATION, ""));


    }

    void saveToPreference() {
        String office_timings = fromOTET.getText().toString().concat(" - " + toOTET.getText().toString());
        editor.putString(PreferencesConstants.TYPE_OF_PROFESSION, professionEdit.getText().toString().trim());
        editor.putString(PreferencesConstants.OFFICE_DAYS, listString);
        editor.putString(PreferencesConstants.OFFICE_TIMINGS, office_timings);
        editor.putString(PreferencesConstants.AVERAGE_INCOME, incomeEdit.getText().toString().trim());
        if (chSeparateRoom.isChecked()) {
            editor.putBoolean(PreferencesConstants.SEPARATE_ROOM, true);
        } else {
            editor.putBoolean(PreferencesConstants.SEPARATE_ROOM, false);
        }
        editor.apply();
    }

    void setAdditionalInformation() {
        try {
            if (prefs.getString(PreferencesConstants.TYPE_OF_PROFESSION, "") != null)
                professionEdit.setText(prefs.getString(PreferencesConstants.TYPE_OF_PROFESSION, ""));
            if (prefs.getString(PreferencesConstants.TYPE_OF_PROFESSION, "") != null)
                fromOTET.setText(prefs.getString(PreferencesConstants.OFFICE_TIMINGS, ""));
            if (prefs.getString(PreferencesConstants.AVERAGE_INCOME, "") != null)
                incomeEdit.setText(prefs.getString(PreferencesConstants.AVERAGE_INCOME, ""));
            if (!prefs.getString(PreferencesConstants.OFFICE_TIMINGS, "").equals(""))
                setOfficeTimings(prefs.getString(PreferencesConstants.OFFICE_TIMINGS, ""));
            if (prefs.getBoolean(PreferencesConstants.SEPARATE_ROOM, false)) {
                chSeparateRoom.setChecked(true);
            } else {
                chSeparateRoom.setChecked(false);
            }
               /* if (additionalInfo.getSeperateChildRoom() != null)
                    chSeparateRoom.setChecked(true);*/

        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, "listString--", e);
        }
    }

    private void setOfficeTimings(String officeTimings) {
        try {
            if (officeTimings.equals(""))
                return;

            String[] oTimes = officeTimings.split(" - ");
            if (oTimes.length != 0 && oTimes.length == 2) {
                fromOTET.setText(oTimes[0]);
                toOTET.setText(oTimes[1]);
            } /*else {
                ToastUtils.displayToast("Office timings not available !", context);
            }*/
        } catch (Exception e) {
            Logger.logE(TAG, "stOfcTmngs : ", e);
        }
    }

    /*This method retrieves values from Map
            */
    public void retrieveSelectedOfficeDays(Map map) {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        String key;
        boolean value;
        daysList.clear();
        while (itr.hasNext()) {
            key = (String) itr.next();
            value = (boolean) map.get(key);
            try {
                if (value) {
                    daysList.add(key);
                    Logger.logD(Constants.PROJECT, "day list==" + daysList.size());
                    //ToastUtils.displayToast("Selected Days--"+daysList.size(),getActivity());
                }

            } catch (Exception e) {
                Logger.logE(Constants.PROJECT, "retrieve Office Days", e);
            }
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

    @OnClick(R.id.txtMon)
    public void mon() {
        // tag==1 day is not selected
        //tag=="2" day is selected
        if (txtMon.getTag().toString().equals("1")) {
            txtMon.setTag("2");
            officeDaysMap.put("mon", true);
            txtMon.setTextColor(getResources().getColor(R.color.white));
            txtMon.setBackgroundResource(R.color.bg_color);
            Logger.logD(Constants.PROJECT, "Office map" + officeDaysMap.get("mon"));
        } else if (txtMon.getTag().toString().equals("2")) {
            txtMon.setTag("1");
            officeDaysMap.put("mon", false);
            txtMon.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            txtMon.setBackgroundResource(R.color.white);
            Logger.logD(Constants.PROJECT, "Office map f" + officeDaysMap.get("mon"));
        }
    }

    @OnClick(R.id.txtTue)
    public void tue() {
        if (txtTue.getTag().toString().equals("1")) {
            txtTue.setTag("2");
            officeDaysMap.put("tue", true);
            txtTue.setTextColor(getResources().getColor(R.color.white));
            txtTue.setBackgroundResource(R.color.bg_color);
            Logger.logD(Constants.PROJECT, "Office map" + officeDaysMap.get("tue"));
        } else if (txtTue.getTag().toString().equals("2")) {
            txtTue.setTag("1");
            officeDaysMap.put("tue", false);
            txtTue.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            txtTue.setBackgroundResource(R.color.white);
            Logger.logD(Constants.PROJECT, "Office map f" + officeDaysMap.get("tue"));
        }
    }

    @OnClick(R.id.txtWed)
    public void wed() {
        if (txtWed.getTag().toString().equals("1")) {
            txtWed.setTag("2");
            officeDaysMap.put("wed", true);
            txtWed.setTextColor(getResources().getColor(R.color.white));
            txtWed.setBackgroundResource(R.color.bg_color);
            Logger.logD(Constants.PROJECT, "Office map" + officeDaysMap.get("wed"));
        } else if (txtWed.getTag().toString().equals("2")) {
            txtWed.setTag("1");
            officeDaysMap.put("wed", false);
            txtWed.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            txtWed.setBackgroundResource(R.color.white);
            Logger.logD(Constants.PROJECT, "Office map f" + officeDaysMap.get("wed"));
        }
    }

    @OnClick(R.id.txtThu)
    public void thr() {
        if (txtThu.getTag().toString().equals("1")) {
            txtThu.setTag("2");
            officeDaysMap.put("thr", true);
            txtThu.setTextColor(getResources().getColor(R.color.white));
            txtThu.setBackgroundResource(R.color.bg_color);
            Logger.logD(Constants.PROJECT, "Office map" + officeDaysMap.get("thr"));
        } else if (txtThu.getTag().toString().equals("2")) {
            txtThu.setTag("1");
            officeDaysMap.put("thr", false);
            txtThu.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            txtThu.setBackgroundResource(R.color.white);
            Logger.logD(Constants.PROJECT, "Office map f" + officeDaysMap.get("thr"));
        }
    }

    @OnClick(R.id.txtFri)
    public void fri() {
        if (txtFri.getTag().toString().equals("1")) {
            txtFri.setTag("2");
            officeDaysMap.put("fri", true);
            txtFri.setTextColor(getResources().getColor(R.color.white));
            txtFri.setBackgroundResource(R.color.bg_color);
            Logger.logD(Constants.PROJECT, "Office map" + officeDaysMap.get("fri"));
        } else if (txtFri.getTag().toString().equals("2")) {
            txtFri.setTag("1");
            officeDaysMap.put("fri", false);
            txtFri.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            txtFri.setBackgroundResource(R.color.white);
            Logger.logD(Constants.PROJECT, "Office map f" + officeDaysMap.get("fri"));
        }
    }

    @OnClick(R.id.txtSat)
    public void sat() {
        if (txtSat.getTag().toString().equals("1")) {
            txtSat.setTag("2");
            officeDaysMap.put("sat", true);
            txtSat.setTextColor(getResources().getColor(R.color.white));
            txtSat.setBackgroundResource(R.color.bg_color);
            Logger.logD(Constants.PROJECT, "Office map" + officeDaysMap.get("sat"));
        } else if (txtSat.getTag().toString().equals("2")) {
            txtSat.setTag(1);
            officeDaysMap.put("sat", false);
            txtSat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            txtSat.setBackgroundResource(R.color.white);
            Logger.logD(Constants.PROJECT, "Office map f" + officeDaysMap.get("sat"));
        }
    }

    @OnClick(R.id.txtSun)
    public void sun() {
        if (txtSun.getTag().toString().equals("1")) {
            txtSun.setTag("2");
            officeDaysMap.put("sun", true);
            txtSun.setTextColor(getResources().getColor(R.color.white));
            txtSun.setBackgroundResource(R.color.bg_color);
            Logger.logD(Constants.PROJECT, "Office map" + officeDaysMap.get("sun"));
        } else if (txtSun.getTag().toString().equals("2")) {
            txtSun.setTag("1");
            officeDaysMap.put("sun", false);
            txtSun.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            txtSun.setBackgroundResource(R.color.white);
            Logger.logD(Constants.PROJECT, "Office map f--" + officeDaysMap.get("sun"));
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.profession: {
                if (!hasFocus) {
                    return;
                }
                popupItems(professionEdit, profession);
            }
            break;
            case R.id.income: {
                if (!hasFocus) {
                    return;
                }
                popupItems(incomeEdit, income);
            }
            break;
            case R.id.ll_profession: {
                if (!hasFocus) {
                    return;
                }
                popupItems(professionEdit, profession);
            }
            break;
            case R.id.ll_income: {
                if (!hasFocus) {
                    return;
                }
                popupItems(incomeEdit, income);
            }
            break;

            case R.id.fromOfficeTime: {
                if (!hasFocus) {
                    return;
                }
                showTimePicker();
            }
            break;
            case R.id.toOfficeTime: {
                if (!hasFocus) {
                    return;
                }
                showTimePicker();
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void updateUserAddResponse(UpdateUserAdditionalBean updateUserAdditionalBean) {
        Logger.logD(Constants.PROJECT, "Add bean--" + updateUserAdditionalBean.getData());
        if ("success".equalsIgnoreCase(updateUserAdditionalBean.getStatus())) {
            if (toToast)
                ToastUtils.displayToast(updateUserAdditionalBean.getData(), context);
            saveToPreference();
        } else {
            if (toToast)
                ToastUtils.displayToast(updateUserAdditionalBean.getData(), context);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fromOfficeTime:
                fromFlag = true;
                showTimePicker();
                break;
            case R.id.toOfficeTime:
                fromFlag = false;
                showTimePicker();
                break;
            case R.id.income:
                popupItems(incomeEdit, income);
                break;
            case R.id.profession:
                popupItems(professionEdit, profession);
                break;
            case R.id.ll_income:
                popupItems(incomeEdit, income);
                break;
            case R.id.ll_profession:
                popupItems(professionEdit, profession);
                break;
            default:
                break;
        }
    }

    private void showTimePicker() {
        TimePickerDialog newFragment = new TimePickerDialog(getActivity(), timePickerListener, hour, minute, false);
        newFragment.show(); //getFragmentManager(), "TimePicker");
    }


    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            try {
                // updateTime(hour,minute);
                if (fromFlag) {
                    fromOTET.setText(hour + " : " + utilTime(minute));
                } else {
                    toOTET.setText(hour + " : " + utilTime(minute));
                }
            } catch (Exception e) {
                Logger.logE(TAG, "onTimeSet", e);
            }

        }

    };

    private static String utilTime(int value) {

        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }
}
