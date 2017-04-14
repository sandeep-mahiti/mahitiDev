package com.parentof.mai.views.activity.reminder;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.CloseReminderCallBack;
import com.parentof.mai.activityinterfaces.PassChildToActivityCallBack;
import com.parentof.mai.activityinterfaces.ResponseForReminderSavedNot;
import com.parentof.mai.activityinterfaces.SaveReminderCallBack;
import com.parentof.mai.api.apicalls.SendRemindersToServer;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.reminders.GetReminderResponse;
import com.parentof.mai.model.reminders.ReminderModel;
import com.parentof.mai.model.reminders.ReminderSavedResponse;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.RangeTimePickerDialog;
import com.parentof.mai.utils.ReminderClass;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.utils.Utility;
import com.parentof.mai.views.adapters.ReminderAdapter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener, PassChildToActivityCallBack, CloseReminderCallBack, SaveReminderCallBack, ResponseForReminderSavedNot {


    EditText spVaccineName;
    EditText reminderDate;
    EditText reminderTime;

    private int hour;
    private int minute;

    int remDay;
    int remMonth;
    int remYear;
    DateFragmentReminder newFragment;

    TextView reminderSave;

    EditText edtReminderText;

    ImageView imgSchool;
    ImageView imgVaccination;
    ImageView imgFriends;
    ImageView imgFamily;
    ImageView imgOther;

    LinearLayout llVacc;
    LinearLayout llSchool;
    LinearLayout llFriends;
    LinearLayout llFamily;
    LinearLayout llOther;

    TextView tvSchool;
    TextView tvVaccination;
    TextView tvFriends;
    TextView tvFamily;
    TextView tvOther;

    LinearLayout llVaccinationDown;

    LinearLayout llVaccinationSpinner;


    String[] schoolEventArray = {"Sports Day", "School Outing", "PTA Meeting", "Report Cards", "School Celebrations", "Others"};
    String[] familyEventArray = {"Family Gathering", "Wedding", "Family Visit", "Others"};
    String[] friendsEventArray = {"Birthday", "Picnic", "Outing", "Play date", "Others"};
    AlertDialog alertDown;

    Spinner spEventTypes;
    TextView tvSelectType;
    EditText eventLocation;
    ImageView imgReminderHelpText;

    String reminderType = "";

    ListView recyclerView;
    String TAG = "Remider";
    String reminderDescription;
    String childName;

    int pos = 0;
    DatabaseHelper databaseHelper;
    Child childBeanReminder;
    String reminderStatus = "open";
    List<ReminderModel> reminderModelList = new ArrayList<>();
    Child childBean;
    @Bind(R.id.tv_noreminders)
    TextView tvNoReminders;
    View footerView;
    String selDate = "";
    @Bind(R.id.reminder_img)
    ImageView reminderNotiImage;

    private static final String ARG_PARAM1 = "param1";
    SharedPreferences preferences;
    ReminderAdapter reminderAdapter;
    List<ReminderModel> tempRemindList;
    boolean viewFlag = true;

    public ReminderFragment newInstance(Child childBean) {
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, childBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childBean = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reminder, container, false);
        ButterKnife.bind(this, view);
        preferences = getActivity().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(getActivity());
        childName = childBean.getChild().getFirstName();

        recyclerView = (ListView) view.findViewById(R.id.childrenReminderList);
        footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.reminder_footerview_layout, null, false);

        init(footerView);
        showChildReminderList();
        showReminder();
        // Inflate the layout for getActivity() fragment
       /* if (reminderModelList.size() == 0 && reminderModelList.isEmpty()) {
            SendRemindersToServer.getRemindersAPI(getActivity(), String.valueOf(preferences.getInt(Constants._ID, 0)), childBean.getChild().getId(), this);
        }*/
        return view;
    }

    void init(View view) {
        spVaccineName = (EditText) view.findViewById(R.id.sp_vaccine_names);
        reminderSave = (TextView) view.findViewById(R.id.reminderSave);
        edtReminderText = (EditText) view.findViewById(R.id.edt_reminder);
        imgSchool = (ImageView) view.findViewById(R.id.img_school);

        imgVaccination = (ImageView) view.findViewById(R.id.img_vaccination);
        imgFriends = (ImageView) view.findViewById(R.id.img_friends);
        imgFamily = (ImageView) view.findViewById(R.id.img_family);
        imgOther = (ImageView) view.findViewById(R.id.img_other);

        llVacc = (LinearLayout) view.findViewById(R.id.ll_vaccination);
        llSchool = (LinearLayout) view.findViewById(R.id.ll_school);
        llFriends = (LinearLayout) view.findViewById(R.id.ll_friends);
        llFamily = (LinearLayout) view.findViewById(R.id.ll_family);
        llOther = (LinearLayout) view.findViewById(R.id.ll_other);

        tvSchool = (TextView) view.findViewById(R.id.tv_school);
        tvVaccination = (TextView) view.findViewById(R.id.tv_vaccination);
        tvFriends = (TextView) view.findViewById(R.id.tv_friends);
        tvFamily = (TextView) view.findViewById(R.id.tv_family);
        tvOther = (TextView) view.findViewById(R.id.tv_other);

        llVaccinationDown = (LinearLayout) view.findViewById(R.id.ll_vaccination_dromdown);
        llVaccinationSpinner = (LinearLayout) view.findViewById(R.id.ll_vaccination_spinner);


        spEventTypes = (Spinner) view.findViewById(R.id.sp_eventTypes);
        tvSelectType = (TextView) view.findViewById(R.id.tv_select_type_reminder);
        eventLocation = (EditText) view.findViewById(R.id.eventLocation);
        imgReminderHelpText = (ImageView) view.findViewById(R.id.reminderHelpText);
        reminderDate = (EditText) view.findViewById(R.id.reminder_date);
        reminderTime = (EditText) view.findViewById(R.id.reminder_time);

        spVaccineName.setOnClickListener(this);
        spVaccineName.setVisibility(View.VISIBLE);
        spVaccineName.setInputType(InputType.TYPE_NULL);
        reminderDate.setOnClickListener(this);
        reminderTime.setOnClickListener(this);
        reminderSave.setOnClickListener(this);
        llFamily.setOnClickListener(this);
        llVacc.setOnClickListener(this);
        llFriends.setOnClickListener(this);
        llSchool.setOnClickListener(this);
        llOther.setOnClickListener(this);

        imgReminderHelpText.setOnClickListener(this);
        spVaccineName.setOnFocusChangeListener(this);
        reminderDate.setOnFocusChangeListener(this);
        reminderTime.setOnFocusChangeListener(this);
    }

    void showChildReminderList() {
        try {
            Logger.logD(Constants.PROJECT, "Child ID--" + childBean.getChild().getId());
            if (childBean != null && childBean.getChild() != null && childBean.getChild().getId() != null)
                reminderModelList = databaseHelper.getAllReminders(childBean.getChild().getId(), Utility.currentDate(), reminderStatus);

            Logger.logD(Constants.PROJECT, "Sizee---" + reminderModelList.size());
            footerView.setVisibility(View.GONE);

            if (reminderModelList.size() == 0)
                tvNoReminders.setVisibility(View.VISIBLE);

            reminderAdapter = new ReminderAdapter(getActivity(), reminderModelList, this);
            recyclerView.setAdapter(reminderAdapter);
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, "Exce", e);
        }

    }

    @Override
    public void onStop() {
        viewFlag = true;
        super.onStop();
    }

    void popupItems(final EditText itemEt, final CharSequence[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                // Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
                itemEt.setText(items[item]);
                pos = item;
                //  alertDown.dismiss();
            }
        });
        alertDown = builder.create();
        alertDown.show();
    }

    @OnClick(R.id.reminder_img)
    public void clickReminder() {
        if (viewFlag) {
            recyclerView.addFooterView(footerView);
            recyclerView.setVisibility(View.VISIBLE);
            footerView.setVisibility(View.VISIBLE);
            recyclerView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            recyclerView.setStackFromBottom(true);
            /*recyclerView.setSelection(adapter.getCount() - 1);*/
            tvNoReminders.setVisibility(View.GONE);
            viewFlag = false;
        } else if (!viewFlag) {
            viewFlag = true;
            recyclerView.removeFooterView(footerView);
            // recyclerView.setVisibility(View.GONE);
            footerView.setVisibility(View.GONE);
            Logger.logD(Constants.PROJECT, "");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.reminder_date: {
                if (!hasFocus) {
                    return;
                }
                newFragment = new DateFragmentReminder(reminderDate);
                newFragment.show(getActivity().getFragmentManager(), "DatePicker");
            }
            break;
            case R.id.reminder_time: {
                if (!hasFocus) {
                    return;
                }
                // showTimePicker();
                showNewTimePicker();
            }
            break;
            case R.id.sp_vaccine_names: {
                if (!hasFocus) {
                    return;
                }
                popupItems(spVaccineName, Constants.vaccinationGroups);
            }
            break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sp_vaccine_names:
                popupItems(spVaccineName, Constants.vaccinationGroups);
                break;
            case R.id.reminder_date:
                newFragment = new DateFragmentReminder(reminderDate);
                newFragment.show(getActivity().getFragmentManager(), "DatePicker");
                break;
            case R.id.reminder_time:
                if (reminderDate.getText().toString().isEmpty()) {
                    ToastUtils.displayToast("First date should select", getActivity());
                    return;
                }
                showNewTimePicker();
                break;
            case R.id.reminderSave:
                saveReminder();
                break;
            case R.id.ll_vaccination:
                setVaccinationMode(tvVaccination, tvFamily, tvFriends, tvSchool, tvOther,
                        imgVaccination, imgFamily, imgFriends, imgSchool, imgOther);
                showReminder();
                reminderType = getResources().getString(R.string.Vaccination);
                llVaccinationSpinner.setVisibility(View.GONE);
                if (alertDown != null)
                    alertDown.dismiss();
                break;
            case R.id.ll_family:
                setVaccinationMode(tvFamily, tvVaccination, tvFriends, tvSchool, tvOther,
                        imgFamily, imgVaccination, imgFriends, imgSchool, imgOther);
                showReminder();
                showEventSpinner(familyEventArray);
                reminderType = "Family";
                tvSelectType.setText("FAMILY");
                break;
            case R.id.ll_school:
                setVaccinationMode(tvSchool, tvFamily, tvFriends, tvVaccination, tvOther,
                        imgSchool, imgFamily, imgFriends, imgVaccination, imgOther);
                showReminder();
                showEventSpinner(schoolEventArray);
                reminderType = "School";
                tvSelectType.setText("SCHOOL");
                break;
            case R.id.ll_friends:
                setVaccinationMode(tvFriends, tvFamily, tvVaccination, tvSchool, tvOther,
                        imgFriends, imgFamily, imgVaccination, imgSchool, imgOther);
                showReminder();
                showEventSpinner(friendsEventArray);
                reminderType = "Friends";
                tvSelectType.setText("FRIENDS");

                break;
            case R.id.ll_other:
                setVaccinationMode(tvOther, tvFamily, tvVaccination, tvSchool, tvFriends,
                        imgOther, imgFamily, imgVaccination, imgSchool, imgFriends);
                edtReminderText.setVisibility(View.VISIBLE);
                llVaccinationDown.setVisibility(View.GONE);
                llVaccinationSpinner.setVisibility(View.GONE);
                reminderType = "Other";
                break;
            case R.id.reminderHelpText:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(Constants.helpText[pos]);
                builder.show();
                break;
            default:
                break;
        }
    }

    void showNewTimePicker() {
        final Calendar mcurrentTime = Calendar.getInstance();
        final int hourCurrent = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minuteCurrent = mcurrentTime.get(Calendar.MINUTE);
        final RangeTimePickerDialog mTimePicker;
        mTimePicker = new RangeTimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                String _24HourTime = selectedHour + ":" + selectedMinute;
                hour = selectedHour;
                minute = selectedMinute;
                try {
                    Date _24HourDt = _24HourSDF.parse(_24HourTime);
                    if (Utility.validatedDatesEqualOrNot(selDate) && Utility.returnCurrentTime().after(_24HourDt)) {
                        ToastUtils.displayToast("Please select valid time", getActivity());
                        reminderTime.setText("");
                    } else {
                        reminderTime.setText(_12HourSDF.format(_24HourDt));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "inside OnTimeSetListener");

            }
        }, hourCurrent, minuteCurrent, false);//true = 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.setMin(hourCurrent, minuteCurrent);
        mTimePicker.show();
    }


    @TargetApi(Build.VERSION_CODES.M)
    void saveReminder() {
        if (reminderDate.getText().toString().isEmpty()) {
            ToastUtils.displayToast("Please select date", getActivity());
            return;
        }
        if (reminderTime.getText().toString().isEmpty()) {
            ToastUtils.displayToast("Please select time", getActivity());
            return;
        }
        if (eventLocation.getText().toString().isEmpty()) {
            ToastUtils.displayToast("Place is required", getActivity());
            return;
        }
        try {
            if ("Vaccination".equalsIgnoreCase(reminderType)) {
                reminderDescription = spVaccineName.getText().toString();
            } else if ("Other".equalsIgnoreCase(reminderType)) {
                reminderDescription = edtReminderText.getText().toString();
            } else if (!"Vaccination".equalsIgnoreCase(reminderType) && !"Other".equalsIgnoreCase(reminderType)) {
                reminderDescription = spEventTypes.getSelectedItem().toString();
            }

            ReminderClass.addEvent(getActivity(), remYear, remMonth, remDay, hour, minute, Calendar.getInstance().getTimeInMillis(), reminderType, reminderType + "-" + reminderDescription, eventLocation.getText().toString(), childBean, String.valueOf(preferences.getInt(Constants._ID, -1)), this);
            //  ToastUtils.displayToast("Reminder Saved", getActivity());
            recyclerView.removeFooterView(footerView);
            showChildReminderList();
            clearReminder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void clearReminder() {
        reminderDate.setText("");
        reminderTime.setText("");
    }

    void showReminder() {
        llVaccinationDown.setVisibility(View.VISIBLE);
        reminderType = getResources().getString(R.string.Vaccination);
        edtReminderText.setVisibility(View.GONE);
    }

    void showEventSpinner(String[] eventArray) {
        llVaccinationSpinner.setVisibility(View.VISIBLE);
        llVaccinationDown.setVisibility(View.GONE);
        spEventTypes.setVisibility(View.VISIBLE);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, eventArray);
        spEventTypes.setAdapter(arrayAdapter);

    }

    private void showTimePicker() {

        TimePickerDialog newFragment = new TimePickerDialog(getActivity(), timePickerListener, hour, minute, false);
        newFragment.show(); //getFragmentManager(), "TimePicker");
    }


    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        final Calendar mcurrentTime = Calendar.getInstance();
        final int currentHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int currentMminute = mcurrentTime.get(Calendar.MINUTE);

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {

            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            String _24HourTime = hour + ":" + minute;
            try {
                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                reminderTime.setText(_12HourSDF.format(_24HourDt));
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    };

    @Override
    public void passSelectedChildToActivity(Child childBean, String bitmap) {
        ToastUtils.displayToast("Selected Child " + childBean.getChild().getFirstName(), getActivity());
        if (childBean.getChild() != null && childBean.getChild() != null) {
            this.childBeanReminder = childBean;
            childName = childBean.getChild().getFirstName();

        }
    }

    @Override
    public void reminderCloseResponse(int reminderId) {
        Logger.logD(Constants.PROJECT, "CLose Reminder ID--" + reminderId);
        databaseHelper.deleteReminderFromTable(childBean.getChild().getId(), reminderId);
        showChildReminderList();
    }

    @Override
    public void saveReminderResponseCallBack(ReminderSavedResponse reminderSavedResponse) {
        if ("error".equalsIgnoreCase(reminderSavedResponse.getStatus())) {
            ToastUtils.displayToast(reminderSavedResponse.getMessage(), getActivity());
        } else {
            viewFlag = true;
            footerView.setVisibility(View.GONE);
            recyclerView.removeFooterView(footerView);
           /* recyclerView.setTranscriptMode(ListView.SCROLL_INDICATOR_TOP);
            recyclerView.setSelection(1);*/
            ToastUtils.displayToast(reminderSavedResponse.getData(), getActivity());
        }

    }

    @Override
    public void getResponseCallBack(GetReminderResponse getReminderResponse) {

        Logger.logD(Constants.PROJECT, "GetReminderResponse Reminder ID--" + getReminderResponse.getData().getReminders());
       /* List<ReminderModel> reminderModelList = new ArrayList<ReminderModel>(Arrays.asList(getReminderResponse.getData().getReminders().split("}")))
        Logger.logD(Constants.PROJECT, "GetReminderResponse ID--" );*/

    }

    @Override
    public void reminderSaveResponse(String rId, List<ReminderModel> reminderModels) {
        Logger.logD(Constants.PROJECT, rId + "--" + reminderModels);
        Type type = new TypeToken<List<ReminderModel>>() {
        }.getType();
        String jsonString = new Gson().toJson(reminderModels, type);
        Logger.logD(Constants.PROJECT, "remindString---" + jsonString);

        JsonParser parser = new JsonParser();
        JsonElement tradeElement = parser.parse(jsonString);
        JsonArray trade = tradeElement.getAsJsonArray();
        Logger.logD(Constants.PROJECT, "remindString JsonArray---" + trade);


        if (rId != null && !rId.isEmpty()) {
            saveReminderAPI(rId, trade);
        }
    }

    void saveReminderAPI(String reminderID, JsonArray jsonArray) {
        if (CheckNetwork.checkNet(getActivity())) {
            if (reminderID != null && !reminderID.isEmpty()) {
                Logger.logD(Constants.PROJECT, "reminderID---" + reminderID);
                SendRemindersToServer.sendRemindersAPI(getActivity(), jsonArray, String.valueOf(preferences.getInt(Constants._ID, 0)), reminderModelList.get(0).getChildId(), this);

            }
        } else
            ToastUtils.displayToast(Constants.NO_INTERNET, getActivity());

    }

    @SuppressLint("ValidFragment")
    public class DateFragmentReminder extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        EditText editText;
        int parentAge;

        @SuppressLint("ValidFragment")
        public DateFragmentReminder(EditText editText) {
            this.editText = editText;
        }

        @NonNull
        @SuppressLint("ValidFragment")
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, yy, mm, dd);
            dialog.getDatePicker().setMinDate(new Date().getTime() - 10000);
            return dialog;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            remDay = day;
            remMonth = month;
            remYear = year;
            parentAge = CommonClass.getAge(year, month, day);
            Logger.logD(Constants.PROJECT, "Age--" + parentAge);
            selDate = month + "/" + day + "/" + year;
            editText.setText(Utility.dateToMonthFullNameFormat(day + "/" + month + "/" + year));
            reminderTime.setText("");
        }
    }
    // Add an event to the calendar of the user.

    void setVaccinationMode(TextView textView, TextView textView1, TextView textView2, TextView textView3, TextView textView5,
                            ImageView imageView, ImageView imageView1, ImageView imageView2, ImageView imageView3, ImageView imageView4) {
        textView.setTextColor(CommonClass.getColor(getActivity(), R.color.colorPrimary));
        textView1.setTextColor(CommonClass.getColor(getActivity(), R.color.black));
        textView2.setTextColor(CommonClass.getColor(getActivity(), R.color.black));
        textView3.setTextColor(CommonClass.getColor(getActivity(), R.color.black));
        textView5.setTextColor(CommonClass.getColor(getActivity(), R.color.black));
        imageView.setImageResource(R.drawable.tupeofreminder_selected);
        imageView1.setImageResource(R.drawable.tupeofreminder);
        imageView2.setImageResource(R.drawable.tupeofreminder);
        imageView3.setImageResource(R.drawable.tupeofreminder);
        imageView4.setImageResource(R.drawable.tupeofreminder);
    }
}
