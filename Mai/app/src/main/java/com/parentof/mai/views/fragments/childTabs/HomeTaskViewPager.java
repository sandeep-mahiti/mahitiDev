package com.parentof.mai.views.fragments.childTabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.DayLoggedCallBack;
import com.parentof.mai.activityinterfaces.RefreshTaskViewCallBack;
import com.parentof.mai.activityinterfaces.UpdateTaskYesNoCallBack;
import com.parentof.mai.api.apicalls.DayLoggedApi;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.DaysBean;
import com.parentof.mai.model.ImprovementPlanModel.HomeTaskBean;
import com.parentof.mai.model.dayLoggedModel.DayLoggedModel;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.StringUtils;
import com.parentof.mai.utils.TaskUtils;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.adapters.DaysAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeTaskViewPager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeTaskViewPager extends Fragment implements DayLoggedCallBack, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters

    RecyclerView rcvDays;
    DatabaseHelper databaseHelper;
    List<HomeTaskBean> homeTaskBeansList = new ArrayList<>();
    List<DayLoggedModel> dayLoggedModelList = new ArrayList<>();

    Child childBean;
    @Bind(R.id.taskChildName)
    TextView taskChildName;

    @Bind(R.id.taskQn)
    TextView taskQn;
    @Bind(R.id.dayYes)
    ImageView dayYes;

    @Bind(R.id.dayNo)
    ImageView dayNo;


    @Bind(R.id.dp_name_widget)
    TextView dpNameWidget;

    @Bind(R.id.skill_name_widget)
    TextView skillNameWidget;
    @Bind(R.id.task_childImage)
    com.mikhaellopez.circularimageview.CircularImageView childImageView;

    String ans;
    String currentDate;

    public HomeTaskViewPager() {
        // Required empty public constructor
    }

    DateFormat dateFormat;
    Date todayDate;
    int current = 0;
    List<DaysBean> daysList;
    String interventionId;
    SharedPreferences preferences;

    @Bind(R.id.leftView)
    View taskLeftView;

    @Bind(R.id.rightView)
    View taskRightView;
    @Bind(R.id.taskWidgetClose)
    ImageView closeTaskWidget;

    List<String> ipList;
    int pos;
    UpdateTaskYesNoCallBack updateYesNoClass;
    RefreshTaskViewCallBack refreshTaskViewCallBack;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeTaskViewPager newInstance(Child param1, String interventionId, List<String> ipList, int pos) {
        HomeTaskViewPager fragment = new HomeTaskViewPager();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, interventionId);
        args.putStringArrayList(ARG_PARAM3, (ArrayList<String>) ipList);
        args.putInt(ARG_PARAM4, pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childBean = getArguments().getParcelable(ARG_PARAM1);
            interventionId = getArguments().getString(ARG_PARAM2);
            ipList = getArguments().getStringArrayList(ARG_PARAM3);
            pos = getArguments().getInt(ARG_PARAM4);
            updateYesNoClass = (UpdateTaskYesNoCallBack) getActivity();
            refreshTaskViewCallBack = (RefreshTaskViewCallBack) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_task_view_pager, container, false);
        ButterKnife.bind(this, view);
        preferences = getActivity().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        todayDate = calendar.getTime();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        currentDate = dateFormat.format(todayDate);

        rcvDays = (RecyclerView) view.findViewById(R.id.rcvDaysList);
        dayYes.setOnClickListener(this);
        dayNo.setOnClickListener(this);
        databaseHelper = new DatabaseHelper(getActivity());
        getImprovementPlanDataFromDb(); // getting from DB
        if (childBean != null && childBean.getChild() != null && childBean.getChild().getFirstName() != null)
            taskChildName.setText(childBean.getChild().getFirstName().toUpperCase().concat("'S TASK")); // setting from bean
        if (childBean != null && childBean.getChild() != null && childBean.getChild().getId() != null) {
            boolean imgFlag = CommonClass.getImageFromDirectory(childImageView, childBean.getChild().getId());
            if (!imgFlag) {
                childImageView.setImageBitmap(CommonClass.StringToBitMap(preferences.getString("child_image", "")));
            }
        }

        return view;
    }

    @OnClick(R.id.taskWidgetClose)
    public void clickCloseTaskWidget() {
        ToastUtils.displayToast("Close", getActivity());
        homeTaskBeansList.remove(0);
    }

    void setLeftRightViews() {
        try {
            if (pos == 0 && ipList.size() == 1) {
                taskRightView.setVisibility(View.GONE);
                taskLeftView.setVisibility(View.GONE);
            } else if (pos == 0) {
                taskRightView.setVisibility(View.VISIBLE);
                taskLeftView.setVisibility(View.GONE);
            } else if ((ipList.size() - 1) == pos) {
                taskRightView.setVisibility(View.GONE);
                taskLeftView.setVisibility(View.VISIBLE);
            } else {
                taskRightView.setVisibility(View.VISIBLE);
                taskLeftView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    void getImprovementPlanDataFromDb() {
        try {
            if (childBean != null && childBean.getId() != null && childBean.getChild().getId() != null)
                homeTaskBeansList = databaseHelper.getChildImprovementPlan(childBean.getChild().getId(), interventionId);

            if (!homeTaskBeansList.isEmpty()) {
                getIpLogTableFromDb(); //For getting day log
                setLeftRightViews();
            }
            StringUtils stringUtils = new StringUtils();
            if (childBean != null && childBean.getChild() != null && homeTaskBeansList.size() > 0) {
                dpNameWidget.setText(homeTaskBeansList.get(0).getDpName());
                skillNameWidget.setText(homeTaskBeansList.get(0).getSkillName());
                taskQn.setText(stringUtils.replaceLabel(childBean.getChild(), "DP name", "Skill name",
                        getActivity(), homeTaskBeansList.get(0).getData().getQualifyingQn(), homeTaskBeansList.get(0).getData().getTaskName()));
                Logger.logD(Constants.PROJECT, "getQualifyingQn--" + homeTaskBeansList.get(0).getData().getQualifyingQn());
                Logger.logD(Constants.PROJECT, "getReminder--" + homeTaskBeansList.get(0).getData().getReminder());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void getIpLogTableFromDb() {
        try {
            if (homeTaskBeansList.get(0).getData() != null &&
                    homeTaskBeansList.get(0).getData().getId() != null) {
                dayLoggedModelList = databaseHelper.getIpLogTable(homeTaskBeansList.get(0).getChildId(),
                        homeTaskBeansList.get(0).getData().getId());
                setDays(homeTaskBeansList.get(0).getData().getDuration()); // Set days list
                Logger.logD(Constants.PROJECT, "getIpLogTableFromDb size" + dayLoggedModelList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean getExistOrNOt(int position) {
        for (int i = 0; i < dayLoggedModelList.size(); i++) {
            if (dayLoggedModelList.get(i).getAns().equalsIgnoreCase(daysList.get(position).getAnswer())) {
                return true;
            }

        }
        return false;
    }


    void setDays(int duration) {
        TaskUtils taskUtils = new TaskUtils();
        daysList = taskUtils.loopDaysList(homeTaskBeansList);
        Logger.logD(Constants.PROJECT, "daysList date-- " + daysList.size());
        int currentPosition = taskUtils.getCurrentItemFromList(daysList, dateFormat, current, todayDate);
        current = currentPosition;
        Logger.logD(Constants.PROJECT, "currentPosition date-- " + currentPosition);

        if (currentPosition == -1) {
            dayYes.setVisibility(View.INVISIBLE);
            dayNo.setVisibility(View.INVISIBLE);
            return;
        }

        daysList = taskUtils.setAnsweredData(currentPosition, daysList, dayLoggedModelList);
        if (getExistOrNOt(currentPosition)) {
            dayYes.setVisibility(View.INVISIBLE);
            dayNo.setVisibility(View.INVISIBLE);
        } else {
            dayYes.setVisibility(View.VISIBLE);
            dayNo.setVisibility(View.VISIBLE);
        }
        Logger.logD(Constants.PROJECT, "daysList From ansed-- " + daysList.size());
        if (daysList.size() > currentPosition)
            daysList.get(currentPosition).setCurrent(true);

        DaysAdapter daysAdapter = new DaysAdapter(getActivity(), duration, daysList, "task");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvDays.setLayoutManager(linearLayoutManager);
        rcvDays.setAdapter(daysAdapter);

    }

    @Override
    public void dayLoggedCallBack(DayLoggedModel dayLoggedModel) {
        if (dayLoggedModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
            ToastUtils.displayToast(dayLoggedModel.getStatus(), getActivity());
            DayLoggedModel dayLoggedModel1 = new DayLoggedModel();
            dayLoggedModel1.setChildId(homeTaskBeansList.get(0).getChildId());
            dayLoggedModel1.setiId(homeTaskBeansList.get(0).getData().getId());
            dayLoggedModel1.setDpId(homeTaskBeansList.get(0).getDpId());
            dayLoggedModel1.setCurrentDate(currentDate);
            dayLoggedModel1.setAns(ans);
            dayLoggedModel1.setTypeRem(daysList.get(current).getLabel());
            databaseHelper.insIPLogTable(dayLoggedModel1);
            dayYes.setVisibility(View.INVISIBLE);
            dayNo.setVisibility(View.INVISIBLE);
        } else {
            ToastUtils.displayToast(dayLoggedModel.getStatus(), getActivity());
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dayYes:
                ans = "Yes";
                callDayLogApi();
                //   updateYesNoClass.methodUpdateYesNo(interventionId);
                Constants.IID = interventionId;
                refreshTaskViewCallBack.refreshCallBack(Constants.IID);
                break;
            case R.id.dayNo:
                ans = "No";
                callDayLogApi();
             //   updateYesNoClass.methodUpdateYesNo(interventionId);
                Constants.IID = interventionId;
                refreshTaskViewCallBack.refreshCallBack(Constants.IID);
                break;
            default:
                break;
        }
    }

    void callDayLogApi() {
        boolean userAns = false;
        if (ans.equalsIgnoreCase("yes"))
            userAns = true;

        if (CheckNetwork.checkNet(getActivity())) {
            DayLoggedApi.callDayLoggedApi(getActivity(), homeTaskBeansList.get(0).getUserId(), homeTaskBeansList.get(0).getChildId(), homeTaskBeansList.get(0).getDpId(),
                    homeTaskBeansList.get(0).getData().getId(), daysList.get(current).getLabel(), userAns, this);
        } else {
            ToastUtils.displayToast(Constants.NO_INTERNET, getActivity());
        }
    }


}
