package com.parentof.mai.views.fragments.childTabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.ActivateSkillRespCallback;
import com.parentof.mai.activityinterfaces.AddReminderCallBack;
import com.parentof.mai.activityinterfaces.GetDPSkillsRespCallback;
import com.parentof.mai.activityinterfaces.GetSkillQuestionsCallback;
import com.parentof.mai.activityinterfaces.RefreshTaskViewCallBack;
import com.parentof.mai.activityinterfaces.SelectedDpTOActivtyCallback;
import com.parentof.mai.api.apicalls.ActivateDPSKillAPI;
import com.parentof.mai.api.apicalls.GetDPSkillsAPI;
import com.parentof.mai.api.apicalls.GetSkillQuestionsAPI;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.db.DbConstants;
import com.parentof.mai.model.activateskillmodel.ActivateSkillRespModel;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.decisionpointsmodel.DPRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.reminders.ReminderModel;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.utils.Utility;
import com.parentof.mai.views.activity.dpchat.ChatActivity;
import com.parentof.mai.views.activity.reports_chathistory_activity.ChildReports_HistoryActivity;
import com.parentof.mai.views.adapters.HomeReminderAdapter;
import com.parentof.mai.views.adapters.ViewPagerAdapter;
import com.parentof.mai.views.adapters.homefragdpadapter.HomeFragNewDPAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, GetDPSkillsRespCallback, ActivateSkillRespCallback, GetSkillQuestionsCallback, SelectedDpTOActivtyCallback,RefreshTaskViewCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Child childBean;
    @Bind(R.id.pager)
    ViewPager viewPager;
    @Bind(R.id.recent_reminder)
    TextView recentReminderName;

    @Bind(R.id.recent_reminder_time)
    TextView recentReminderTime;

    @Bind(R.id.reminder_hosptial_address)
    TextView recentReminderAddress;
    @Bind(R.id.img_close_reminder)
    ImageView imgCloseReminder;

  /*  @Bind(R.id.next_reminder)
    TextView nextReminderName;

    @Bind(R.id.next_reminder_time)
    TextView nextReminderTime;

    @Bind(R.id.ll_next_remind)
    LinearLayout llNextRemind;*/


    @Bind(R.id.widgetReminder)
    LinearLayout llWidgetReminder;

    @Bind(R.id.todayWidget)
    LinearLayout llTodayReminderWidget;


    @Bind(R.id.label_no_task)
    TextView labelNoTask;
    @Bind(R.id.reminder_done)
    TextView reminderDone;

    @Bind(R.id.reminder_snooze)
    TextView reminderSnooze;

    @Bind(R.id.reminder_dismiss)
    TextView reminderDismiss;
    String reminderStatus = "open";
    List<ReminderModel> reminderModelList;
    ReminderModel tempReminderModel;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    RecyclerView newDpsRv;
    private HomeFragNewDPAdapter adapter;
    private String childId;
    private String TAG = "HomFrag";

    private DatabaseHelper databaseHelper;
    private AllDP selectedDP;
    private GetSkillsRespModel getSKillRespModel;
    private SkillData selectedSkill;

    DPRespModel dpListModel;

    private GetSkillQuestionsRespModel skillQstnRespModel;
    private String recDateOfAllDPs;
    List<AllDP> newDPsList;
    private TextView newDPStrTV;
    @Bind(R.id.rcv_remidndersList)
    RecyclerView rcvRemindersList;
    AddReminderCallBack addReminderCallBack;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(Child param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childBean = getArguments().getParcelable(ARG_PARAM1);
            //  mParam2 = getArguments().getString(ARG_PARAM2);
            if (childBean != null && childBean.getChild() != null && childBean.getChild().getId() != null)
                childId = childBean.getChild().getId();
        }
        addReminderCallBack = (AddReminderCallBack) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        preferences = getActivity().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        newDPStrTV = (TextView) view.findViewById(R.id.newDPTV);
        newDpsRv = (RecyclerView) view.findViewById(R.id.homeFragRV);

        databaseHelper = new DatabaseHelper(getActivity());
        reminderDone.setOnClickListener(this);
        reminderSnooze.setOnClickListener(this);
        reminderDismiss.setOnClickListener(this);
        //childDetailsFromBundle();
        getReminder();
        setupViewPager(viewPager);
        fillListToadapter();

        if (newDPsList != null && !newDPsList.isEmpty() && newDPsList.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            newDpsRv.setLayoutManager(linearLayoutManager);
            adapter = new HomeFragNewDPAdapter(getActivity(), newDPsList, this);
            newDpsRv.setAdapter(adapter);
        } else {
            newDPStrTV.setVisibility(View.GONE);
        }
        return view;

    }

    private void fillListToadapter() {
        try {
            if (databaseHelper.isTableNotEmpty(DbConstants.DECISION_POINTS_TABLE)) {
                //if table is not empty delete all records
                recDateOfAllDPs = databaseHelper.selRecDateOfAllDPs(String.valueOf(preferences.getInt(Constants._ID, -1)), childId, "all");
                newDPsList = new ArrayList<>();
                if (recDateOfAllDPs != null && !recDateOfAllDPs.isEmpty())
                    newDPsList = databaseHelper.selNewDPsFromDB(String.valueOf(preferences.getInt(Constants._ID, -1)), childBean.getChild().getId(), "all", recDateOfAllDPs);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " fillListToadapter ", e);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (preferences.getBoolean("ip_activated", true)) {
            setupViewPager(viewPager);
            preferences.edit().putBoolean("ip_activated", false).apply();
            getReminder();
        }
    }

    void getReminder() {
        try {
            Logger.logD(Constants.PROJECT, "getReminder--");
            reminderModelList = databaseHelper.getAllReminders(childBean.getChild().getId(), Utility.currentDate(), reminderStatus);
            if (!reminderModelList.isEmpty())
                tempReminderModel = reminderModelList.get(0);
            Logger.logD(Constants.PROJECT, "reminderModelList--" + reminderModelList.size());
            if (reminderModelList.isEmpty()) {
                llWidgetReminder.setVisibility(View.GONE);
            } else if (reminderModelList != null && "Done".equalsIgnoreCase(tempReminderModel.getReminderStatus())) {
                llWidgetReminder.setVisibility(View.GONE);
            } else if (reminderModelList != null && "Dismiss".equalsIgnoreCase(tempReminderModel.getReminderStatus())) {
                llWidgetReminder.setVisibility(View.GONE);
            } else if (reminderModelList != null && "Snooze".equalsIgnoreCase(tempReminderModel.getReminderStatus())) {
                // llWidgetReminder.setVisibility(View.GONE);
                snoozeValidation();
            } else {
                displayReminderWidget();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void displayReminderWidget() {
        try {
            Logger.logD(Constants.PROJECT, "returnCurrentDate--" + CommonClass.currentDateTime());
            Logger.logD(Constants.PROJECT, "stringToDate--" + CommonClass.returnDateTime(tempReminderModel.getReminderDate()));
            if (CommonClass.currentDateTime().equals(CommonClass.returnDateTime(tempReminderModel.getReminderDate()))) {
                llTodayReminderWidget.setVisibility(View.VISIBLE);
                Logger.logD(Constants.PROJECT, "reminderModelList--" + reminderModelList.size());
                recentReminderName.setText(childBean.getChild().getFirstName() + " has a " + tempReminderModel.getDescription() + " Today at:");
                recentReminderTime.setText(CommonClass.convertDateTimeToTime(tempReminderModel.getReminderDate()));
                recentReminderAddress.setText(tempReminderModel.getLocation());
                Logger.logD(Constants.PROJECT, "Reminder ID--" + tempReminderModel.getId());
                reminderModelList.remove(0);
                imgCloseReminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Logger.logD(Constants.PROJECT, "Reminder ID--" + tempReminderModel.getId());
                        databaseHelper.deleteReminderFromTable(childBean.getChild().getId(), tempReminderModel.getId());
                        getReminder();
                    }
                });
            } else {
                llTodayReminderWidget.setVisibility(View.GONE);
                Logger.logD(Constants.PROJECT, "else part");
            }
            if (reminderModelList.size() != 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rcvRemindersList.setLayoutManager(linearLayoutManager);
                //  reminderModelList.remove(0);
                HomeReminderAdapter adapter = new HomeReminderAdapter(getActivity(), reminderModelList, this);
                rcvRemindersList.setAdapter(adapter);
            } else {
                rcvRemindersList.setVisibility(View.GONE);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        try {
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
            DatabaseHelper handler = new DatabaseHelper(getActivity());
            List<String> ipList = handler.getIpsList(childBean.getChild().getId());
            Logger.logD(Constants.PROJECT, "ipList--" + ipList.size());
            if (ipList.isEmpty()) {
                labelNoTask.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
            } else {
                labelNoTask.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < ipList.size(); i++) {
                viewPagerAdapter.addFragment(HomeTaskViewPager.newInstance(childBean, ipList.get(i), ipList, i), "");
            }
            viewPager.setAdapter(viewPagerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reminder_done:
                reminderStatus = "Done";
                databaseHelper.updateReminderStatus(reminderStatus, tempReminderModel.getId());
                reminderStatus = "open";
                getReminder();
                break;
            case R.id.reminder_snooze:
                onClickSnooze();
                break;
            case R.id.reminder_dismiss:
                reminderStatus = "Dismiss";
                databaseHelper.updateReminderStatus(reminderStatus, tempReminderModel.getId());
                reminderStatus = "open";
                getReminder();
                break;
            default:
                break;
        }

    }

    void onClickSnooze() {
        reminderStatus = "Snooze";
        databaseHelper.updateReminderStatus(reminderStatus, tempReminderModel.getId());
        snoozeValidation();
        ToastUtils.displayToast("Remind you later", getActivity());
        getActivity().finish();
        /*editor.putString("store_snooze_time", String.valueOf(Utility.currentTimeWithSnoozeTime(preferences.getInt(PreferencesConstants.SNOOZE_TIME, 0))));
        editor.apply();*/
    }

    void snoozeValidation() {
        Logger.logD(Constants.PROJECT, "if Utility.currentTime() --" + Utility.currentTime());
        Logger.logD(Constants.PROJECT, "if Utility.currentTimeWithSnoozeTime() --" + Utility.currentTimeWithSnoozeTime(preferences.getInt(PreferencesConstants.SNOOZE_TIME, 0)));
        //  Logger.logD(Constants.PROJECT, "if Utility.getSnoozeTime() --" + Utility.getSnoozeTime(preferences.getString("store_snooze_time", "")));
        if (Utility.currentTime().after(Utility.currentTimeWithSnoozeTime(preferences.getInt(PreferencesConstants.SNOOZE_TIME, 0)))) {
            Logger.logD(Constants.PROJECT, "if Szooze time--" + preferences.getInt(PreferencesConstants.SNOOZE_TIME, 0));
            displayReminderWidget();
        } else {
            Logger.logD(Constants.PROJECT, "else Szooze time--" + preferences.getInt(PreferencesConstants.SNOOZE_TIME, 0));
            llWidgetReminder.setVisibility(View.GONE);
        }

    }

    @Override
    public void selectedDPtoCallSkillsAPI(AllDP selectedDP, boolean gotoChat) {
        //this.gotoChat = gotoChat;
        this.selectedDP = selectedDP;
        //if (gotoChat) {
        if (selectedDP != null && selectedDP.getId() != null) {
            if (selectedDP.getActive().equalsIgnoreCase("true")) {
                checkIfDpisActive();

            } else {
                ToastUtils.displayToast("Not active!", getActivity());
            }
            /*else{
                GetSkillQuestionsAPI.callSkillQuestions(this, String.valueOf(preferences.getInt(Constants._ID, -1)), childId, selectedDP.getId(),   ,  this);
            }*/
        }
        //}
    }


    private void checkIfDpisActive() {
        try {
            if (databaseHelper.selDPisActive(String.valueOf(preferences.getInt(Constants._ID, -1)), childId, selectedDP)) {//dpis active){

                this.getSKillRespModel = databaseHelper.selAllFromSkillsTable(childId, selectedDP.getId());//from skill table
                if (!this.getSKillRespModel.getData().isEmpty()) {
                    this.selectedSkill = getSelSkill();
                    this.skillQstnRespModel = databaseHelper.selFromQAsTable(selectedDP.getId(), selectedSkill.getId());//from SKill questions table

                    moveToNextActivity();
                } else {
                    //ToastUtils.displayToast("Contacting Server!", getActivity());
                    Logger.logD(TAG, " No skills for selected DP, Contacting server...");
                    callSkillsAPI(selectedDP.getId());
                }

            } else {
                callSkillsAPI(selectedDP.getId());
            }

        } catch (Exception e) {
            ToastUtils.displayToast("checkIfDpisActive", getActivity());
            Logger.logE(TAG, "checkIfDpisActive : ", e);
        }
    }


    @Override
    public void skillResp(GetSkillsRespModel getSkillsRespModel) {
        try {
            if (getSkillsRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast(getSkillsRespModel.getStatus(), getActivity());
                // if(gotoChat){
                this.getSKillRespModel = getSkillsRespModel;
                if (!getSkillsRespModel.getData().isEmpty() && getSkillsRespModel.getData().size() > 0) {
                    // String skillId = getSkillsId();
                    databaseHelper.delskillsfromTable(childId, selectedDP.getId());
                    databaseHelper.insToSkillsTable(getSkillsRespModel, childId, selectedDP.getId());
                    this.selectedSkill = getSelSkill();
                    // if(selectedDP.getActive().equalsIgnoreCase("false")){
                    callActivtSkillAPI();
                    /*}
                    else{
                        GetSkillQuestionsAPI.callSkillQuestions(this, String.valueOf(preferences.getInt(Constants._ID, -1)), childId, selectedDP.getId(), selectedSkill.getId()  , this);
                    }*/
                }

            } else {
                ToastUtils.displayToast(getSkillsRespModel.getStatus(), getActivity());
            }
        } catch (Exception e) {
            Logger.logE(TAG, " skillResp : ", e);
        }
    }

    /*@Override
    public void methodUpdateYesNo() {
        Logger.logD(Constants.PROJECT, "Home Fragment--");
        setupViewPager(viewPager);
    }*/

    private void callActivtSkillAPI() {
        try {
            if (CheckNetwork.checkNet(getActivity())) {
                ActivateDPSKillAPI.actSkill(getActivity(), String.valueOf(preferences.getInt(Constants._ID, -1)), childId, selectedDP.getId(), selectedSkill.getId(), this);
            } else {
                Logger.logD(TAG, " Check Internet and try again ");
            }
        } catch (Exception e) {
            Logger.logE(TAG, " callActvtSkillAPI : ", e);
        }
    }

    private SkillData getSelSkill() {

        SkillData selSkill = null;
        try {
            List<SkillData> skillsList = getSKillRespModel.getData();

            for (SkillData skill : skillsList) {
                if (skill.getRank().equalsIgnoreCase("1"))
                    selSkill = skill;
            }
        } catch (Exception e) {
            Logger.logE(TAG, " DPIdClSkilAPI : ", e);
        }
        return selSkill;
    }

    private void callSkillsAPI(String dpId) {
        try {
            if (CheckNetwork.checkNet(getActivity())) {
                GetDPSkillsAPI.getSkillsList(getActivity(), String.valueOf(preferences.getInt(Constants._ID, -1)), childId, dpId, this);
            } else {
                Logger.logD(TAG, " Check Internet and try again ");
            }
        } catch (Exception e) {
            Logger.logE(TAG, " callSkillsAPI : ", e);
        }
    }

    @Override
    public void activateSkillResp(ActivateSkillRespModel activateSkillRespModel) {

        try {
            if (activateSkillRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                // ToastUtils.displayToast(activateSkillRespModel.getStatus(), this);
                // if(gotoChat){
                if (CheckNetwork.checkNet(getActivity())) {
                    GetSkillQuestionsAPI.callSkillQuestions(getActivity(), String.valueOf(preferences.getInt(Constants._ID, -1)), childId, selectedDP.getId(), selectedSkill.getId(), this);
                } else {
                    Logger.logD(TAG, " Check Internet and try again ");
                }

            } else {
                ToastUtils.displayToast(activateSkillRespModel.getStatus(), getActivity());
            }
        } catch (Exception e) {
            Logger.logE(TAG, " skillResp : ", e);
        }

    }

    @Override
    public void getSkillQuestions(GetSkillQuestionsRespModel skillQuestionsRespModel) {
        try {
            if (skillQuestionsRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                // ToastUtils.displayToast(skillQuestionsRespModel.getStatus(), this);
                // if(gotoChat){

                if (skillQuestionsRespModel.getData() != null && skillQuestionsRespModel.getData().getQuestions().size() > 0) {
                    this.skillQstnRespModel = skillQuestionsRespModel;
                    databaseHelper.delfromQATable(DbConstants.SKILLS_QSTN_TABLE, selectedDP.getId(), selectedSkill.getId());
                    databaseHelper.insToSkillQstnTable(skillQuestionsRespModel.getData().getQuestions(), childId, selectedDP.getId(), selectedSkill.getId());
                    databaseHelper.updateDPType(String.valueOf(preferences.getInt(Constants._ID, -1)), childId, selectedDP.getId(), "active");
                    moveToNextActivity();
                }


            } else {
                ToastUtils.displayToast(skillQuestionsRespModel.getStatus(), getActivity());
            }
        } catch (Exception e) {
            Logger.logE(TAG, " skillResp : ", e);
        }
    }

    private void moveToNextActivity() {
        try {
            Bundle b = new Bundle();
            b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSKillRespModel);
            b.putParcelable(Constants.BUNDLE_QUESTOBJ, skillQstnRespModel);
            b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
            if (databaseHelper.noOfQstnsAnswered(selectedDP.getId(), selectedSkill.getId()) >= 9) {
                Intent i = new Intent(getActivity(), ChildReports_HistoryActivity.class);
                i.putExtras(b);
                startActivity(i);
            } else {
                Intent i = new Intent(getActivity(), ChatActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " mvToNxActvt : ", e);
        }

    }

    @Override
    public void addReminder(int pos) {
        addReminderCallBack.responseReminderCallBack(pos);
    }

    @Override
    public void responseDone(int reminderId) {
        reminderStatus = "Done";
        databaseHelper.updateReminderStatus(reminderStatus, reminderId);
        reminderStatus = "open";
        getReminder();
    }

    @Override
    public void refreshCallBack(String interventionId) {
        Logger.logD(Constants.PROJECT,"HOME FRAGMENT--"+interventionId);

    }
}
