package com.parentof.mai.views.activity.improvementplan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.IPActivateRespCallBack;
import com.parentof.mai.activityinterfaces.IPCompRespCallback;
import com.parentof.mai.activityinterfaces.IPPauseApiRespCallback;
import com.parentof.mai.activityinterfaces.ImprovementPlanCallBack;
import com.parentof.mai.api.apicalls.IPCompleteApi;
import com.parentof.mai.api.apicalls.IPPauseApi;
import com.parentof.mai.api.apicalls.ImprovementPlanApi;
import com.parentof.mai.api.apicalls.InterventionActApi;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.DaysBean;
import com.parentof.mai.model.ImprovementPlanModel.Data;
import com.parentof.mai.model.ImprovementPlanModel.HomeTaskBean;
import com.parentof.mai.model.ImprovementPlanModel.ImprovementPlanModel;
import com.parentof.mai.model.InterventionModel.InterventaionModel;
import com.parentof.mai.model.dayLoggedModel.DayLoggedModel;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.playpauserespmodel.PlayPauseModel;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.StringUtils;
import com.parentof.mai.utils.TaskUtils;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.adapters.DaysAdapter;
import com.parentof.mai.views.adapters.IPActDaysAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ImprovementActivatedActivity extends AppCompatActivity implements View.OnClickListener, IPCompRespCallback, IPActivateRespCallBack, ImprovementPlanCallBack, IPPauseApiRespCallback {

    RecyclerView ipDaysRView;

    LinearLayout congratsLLayout;
    TextView congratsTv;
    TextView beguntaskTV;
    ImageView playImg;

    TextView taskTitleTV;
    TextView taskHedingtv;

    //ListView taskList;
    TextView pausetaskTV;
    TextView canDoTv;

    private RecyclerView.Adapter adapter;
    private String TAG = "IPActivatedActivity";
    private boolean fromReportScreen = false;


    GetSkillQuestionsRespModel skillQuestionsRespModel;
    private Child childBean;
    SkillData selectedSkill;//Datum selectedSkillObj;
    private String dpId;
    private SharedPreferences prefs;

    DatabaseHelper databaseHelper;
    private String userId;
    private String childId;
    GetSkillsRespModel getSkillsRespModel;
    AllDP selectedDP;
    ImprovementPlanModel improvementPlanModel;
    Data ipDataObj;

    //Qualifying question popup fields
    private TextView backBtn;
    private TextView qQuestionBtn;
    private TextView alwaysBtn;
    private TextView rarelyBtn;
    private TextView oftenTVBtn;
    private TextView notAtAllBtn;
    private TextView ipCompBeginTV;
    private StringUtils strutils;
    private int successFlag = 0;
    private boolean isPaused;
    private  boolean moveToNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improvement_activated);


        Toolbar toolbar = (Toolbar) findViewById(R.id.ipAct_toolbar);
        toolbar.setTitle("Improvement Plan");

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

        }
        ipDaysRView = (RecyclerView) findViewById(R.id.ipDaysRV);

        congratsLLayout = (LinearLayout) findViewById(R.id.congratsLLAyout);
        congratsTv = (TextView) findViewById(R.id.congratsTV);
        beguntaskTV = (TextView) findViewById(R.id.begunTaskTV);
        playImg = (ImageView) findViewById(R.id.playImgButton);
        playImg.setOnClickListener(this);
        isPaused = false;
        taskTitleTV = (TextView) findViewById(R.id.task_hello_TV);
        taskHedingtv = (TextView) findViewById(R.id.practice_string_TV);
        //taskList= (ListView) findViewById(R.id.taskList);

        pausetaskTV = (TextView) findViewById(R.id.pausetaskBtnTv);
        pausetaskTV.setOnClickListener(this);
        canDoTv = (TextView) findViewById(R.id.canDoThisBtnTv);
        canDoTv.setOnClickListener(this);
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        userId = String.valueOf(prefs.getInt(Constants._ID, -1));
        getValuesFromBundle();
        getPlanDetailsfromDB();
        strutils = new StringUtils();
        Logger.logD(TAG, "setdaysweeksAdapter " + getIntent().getExtras().getBoolean(Constants.INTENT_FROMREPORTS));
        if (getIntent().getExtras().getBoolean(Constants.INTENT_FROMREPORTS)) {
            fromReportScreen = getIntent().getExtras().getBoolean(Constants.INTENT_FROMREPORTS);
            congratsLLayout.setBackgroundColor(getResources().getColor(R.color.custom_blue));
            congratsTv.setText(getString(R.string.ip_curr_active));
            beguntaskTV.setVisibility(View.GONE);
            improvementPlanModel = new ImprovementPlanModel();
            improvementPlanModel.setData(ipDataObj);
            setdaysweeksAdapter(ipDataObj);
        } else {
            congratsLLayout.setBackgroundColor(getResources().getColor(R.color.dark_green));
            beguntaskTV.setVisibility(View.VISIBLE);
            beguntaskTV.setText(getString(R.string.beguntask).concat(" " + childBean.getChild().getFirstName()));
            setdaysweeksAdapter(improvementPlanModel.getData());
        }
        taskTitleTV.setText(improvementPlanModel.getData().getTaskName());
        taskHedingtv.setText(improvementPlanModel.getData().getTaskObjectives());
        setTasksListAdapter();

    }


    private void getValuesFromBundle() {
        try {
            improvementPlanModel = new ImprovementPlanModel();
            if (getIntent().getExtras() != null) {
                Bundle b = getIntent().getExtras();
                //qstnAnsList = b.getParcelableArrayList(Constants.BUNDLE_QSTNANSLIST);

                // skillQuestionsRespModel = b.getParcelable(Constants.BUNDLE_QUESTOBJ);
                getSkillsRespModel = b.getParcelable(Constants.BUNDLE_SKILLSOBJ);
                selectedSkill = b.getParcelable(Constants.BUNDLE_SELSKILLOBJ);
                childBean = b.getParcelable(Constants.BUNDLE_CHILDOBJ);
                selectedDP = b.getParcelable(Constants.BUNDLE_SELDPOBJ);
                improvementPlanModel = b.getParcelable(Constants.BUNDLE_IPMODEL);
                if (childBean != null && childBean.getChild() != null)
                    childId = childBean.getChild().getId();
                else {
                    ToastUtils.displayToast("ChildId not available, please comeback later ", this);
                    finish();
                }
                if (selectedDP != null) {
                    dpId = selectedDP.getId();
                } else {
                    ToastUtils.displayToast("Please comeback later to continue ", this);
                }

            }
        } catch (Exception e) {
            Logger.logE(TAG, "valsFrmBndl : ", e);
        }
    }


    private void getPlanDetailsfromDB() {
        try {
            databaseHelper = new DatabaseHelper(this);
            ipDataObj = databaseHelper.selPlanFromIPTable(childId, dpId, selectedSkill.getId());
           if (ipDataObj!=null )
              checkPlanPaused();
        } catch (Exception e) {
            Logger.logE(TAG, "gtPlnDetailsfrmDB : ", e);
        }
    }

    private void checkPlanPaused() {
        try{
            if(ipDataObj.getIsPaused()!=null && ipDataObj.getIsPaused().equalsIgnoreCase("play")) {
                isPaused = false;
                playImg.setImageResource(R.drawable.pausebutton100);

            }else {
                isPaused = true;
                playImg.setImageResource(R.drawable.playbigicon100);
                pausetaskTV.setText("Play this Task");
            }
        }catch (Exception e){
            Logger.logE(TAG, "checkPlanPaused : ", e);
        }
    }

    private void setdaysweeksAdapter(Data ipDataObj) {
        try {
            adapter = new IPActDaysAdapter(this, ipDataObj);
            TaskUtils taskUtils = new TaskUtils();
            List<DayLoggedModel> dayLoggedModelList = databaseHelper.getIpLogTable(childId, improvementPlanModel.getData().getId());
            List<HomeTaskBean> homeTaskBeansList = databaseHelper.getChildImprovementPlan(childBean.getChild().getId(), improvementPlanModel.getData().getId());
            //if(homeTaskBeansList.size()>0)
            List<DaysBean> daysList = taskUtils.loopDaysList(homeTaskBeansList);
            Calendar calendar = Calendar.getInstance();
            Date todayDate = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
            String currentDate = dateFormat.format(todayDate);
            int currentPosition = taskUtils.getCurrentItemFromList(daysList, dateFormat, 0, todayDate);
            if (currentPosition == -1) {

            }
            daysList = taskUtils.setAnsweredData(currentPosition, daysList, dayLoggedModelList);
            if (daysList.size() > currentPosition)
                daysList.get(currentPosition).setCurrent(true);
            DaysAdapter daysAdapter = new DaysAdapter(this, improvementPlanModel.getData().getDuration(), daysList, "ip");

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ipDaysRView.setLayoutManager(linearLayoutManager);
            //Adding adapter to recyclerview
            ipDaysRView.setAdapter(daysAdapter);
        } catch (Exception e) {
            Logger.logE(TAG, "setdaysweeksAdapter : ", e);
        }
    }

    private void setTasksListAdapter() {
        try {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // fill in any details dynamically here
            LinearLayout insertPoint = (LinearLayout) findViewById(R.id.container);
            for (int i = 0; i < improvementPlanModel.getData().getTaskSteps().size(); i++) {
                View v = vi.inflate(R.layout.iptaskslist, null);
                TextView textView = (TextView) v.findViewById(R.id.task_steps_label);
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setText(strutils.replaceLabel(childBean.getChild(), selectedDP.getName(), selectedSkill.getName(), this, improvementPlanModel.getData().getTaskSteps().get(i), ""));
                insertPoint.addView(v);// 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }
        } catch (Exception e) {
            Logger.logE(TAG, "setdaysweeksAdapter : ", e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.pausetaskBtnTv:
               checkNcallPause();
                break;
            case R.id.canDoThisBtnTv:
                moveToNext=true;
                loadQQPopUp();
                break;
            case R.id.playImgButton:
                checkNcallPause();
                break;
           /* case R.id.pausetaskBtnTv:
                break;
            case R.id.pausetaskBtnTv:
                break;*/
            default:
                break;
        }

    }

    private void checkNcallPause() {
        try{
            moveToNext=false;
            if (isPaused) {
                //if paused call Activate API
                callPause("play");
            } else {
                // If not paused then call pause API.
                callPause("pause");
            }
        }catch (Exception e){
            Logger.logE(TAG, " checkNcallPause : ", e);
        }
    }

    private void loadQQPopUp() {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.ip_qualifyingquestion_popup, null);

            qQuestionBtn = (TextView) dialogView.findViewById(R.id.qQuestionTV);
            alwaysBtn = (TextView) dialogView.findViewById(R.id.alwaysTV);
            oftenTVBtn = (TextView) dialogView.findViewById(R.id.oftenTV);
            rarelyBtn = (TextView) dialogView.findViewById(R.id.rarelyTV);
            notAtAllBtn = (TextView) dialogView.findViewById(R.id.notAtAllTV);
            backBtn = (TextView) dialogView.findViewById(R.id.backTV);

            qQuestionBtn.setText(strutils.replaceLabel(childBean.getChild(), selectedDP.getName(), selectedSkill.getName(), this, improvementPlanModel.getData().getQualifyingQn(), improvementPlanModel.getData().getTaskName()));
            //qQuestionBtn.setText(improvementPlanModel.getData().getQualifyingQn());


            alertDialog.setView(dialogView);
            final AlertDialog alertDialog1 = alertDialog.create();
            alertDialog1.setCanceledOnTouchOutside(false);
            alertDialog1.setCancelable(false);
            alertDialog1.show();

            alwaysBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successFlag = 1;
                    callIPCompleted();
                    alertDialog1.dismiss();

                }
            });
            oftenTVBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successFlag = 1;
                    callIPCompleted();
                    alertDialog1.dismiss();

                }
            });
            rarelyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successFlag = 0;
                    callIPCompleted();
                    alertDialog1.dismiss();

                }
            });
            notAtAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successFlag = 0;
                    callIPCompleted();
                    alertDialog1.dismiss();
                }
            });
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                }
            });
        } catch (Exception e) {
            Logger.logE(TAG, "loadQQPopUp : ", e);
        }
    }

    private void callIPCompleted() {
        try {
            //TODO call below method in IP completed resp method
            //showIPCompCongratsPOPup();
            if (CheckNetwork.checkNet(this)) {
                if (successFlag == 1)
                    IPCompleteApi.callCompleteApi(this, userId, childId, ipDataObj.getId(), Feeds.COMPLETE, this);
                else
                    IPCompleteApi.callCompleteApi(this, userId, childId, ipDataObj.getId(), Feeds.CANTCOMPLETE, this);
            } else {
                Logger.logD(TAG, " Check Internet and try again ");
            }

        } catch (Exception e) {
            Logger.logE(TAG, "callCompleted : ", e);
        }
    }


    @Override
    public void ipCompResp(ImprovementPlanModel ipcctRespModel) {
        try {
            if (ipcctRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast( ipcctRespModel.getStatus(), this);
                checkNCallNext(ipcctRespModel);
            } else {
                databaseHelper.updateCompletionStatus(childId, improvementPlanModel.getData().getId(), 2);
                this.improvementPlanModel = ipcctRespModel;
            }

        } catch (Exception e) {
            Logger.logE(TAG, "ipCompResp : ", e);
        }
    }

    private void checkNCallNext(ImprovementPlanModel ipcctRespModel) {
        try{
            databaseHelper.updateCompletionStatus(childId, improvementPlanModel.getData().getId(), 3);
            playImg.setImageResource(R.drawable.playbigicon100);
            //TODO uncomment after testing completed IPs in activity
           /* if(successFlag==0){
                showIPCompPOPup();
                this.improvementPlanModel = ipcctRespModel;
            }else{
                showIPCompPOPup();
            }*/
        }catch (Exception e){
            Logger.logE(TAG, "checkNCallNext : ", e);
        }
    }


    private void showIPCompPOPup() {
        try {
            View dialogView;
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            if (successFlag == 1)
                dialogView = inflater.inflate(R.layout.ip_comp_congrats_popup, null);
            else
                dialogView = inflater.inflate(R.layout.ip_comp_negative_popup, null);

            ipCompBeginTV = (TextView) dialogView.findViewById(R.id.beginTV);

            alertDialog.setView(dialogView);
            final AlertDialog alertDialog1 = alertDialog.create();
            alertDialog1.setCanceledOnTouchOutside(false);
            alertDialog1.setCancelable(false);
            alertDialog1.show();

            ipCompBeginTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                    if (successFlag==1)
                        callNextIPAPI();
                    else
                        activateIP();


                }
            });


        } catch (Exception e) {
            Logger.logE(TAG, "callCompleted : ", e);
        }
    }

    private void activateIP() {
        try {
            //check for 3 interventions.
            //TODO uncomment for popup
            //activeInterventionsPopup();
            /*if (databaseHelper.checkFor3ActiveInterventions(childId)) {
                activeInterventionsPopup();
            } else {*/
            if (CheckNetwork.checkNet(this)) {
                InterventionActApi.callInterventionActivateApi(this, this, userId, childId, dpId, improvementPlanModel.getData().getId());
            } else {
                Logger.logD(TAG, "Check Internet and try again! ");
            }
            // }

        } catch (Exception e) {
            Logger.logE(TAG, "beginIP : ", e);
        }
    }

    @Override
    public void iPActResp(InterventaionModel interventaionModel) {
        try {

            Logger.logD(TAG, interventaionModel.getStatus());
            if (interventaionModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                prefs.edit().putBoolean("ip_activated", true).apply();
                playImg.setImageResource(R.drawable.pausebutton100);
                if (improvementPlanModel.getData() != null)
                    databaseHelper.delFromIPTable(childId, improvementPlanModel.getData().getId());
                databaseHelper.insImprovementPlanTable(improvementPlanModel.getData(), userId, childId, selectedDP, selectedSkill);
                callPause("pause");
            } else {
                ToastUtils.displayToast(interventaionModel.getStatus(), this);
                Logger.logD(TAG, interventaionModel.getStatus());
            }


        } catch (Exception e) {
            Logger.logE(TAG, "iPActResp : ", e);
        }
    }

    private void callNextIPAPI() {
        try {
            if (CheckNetwork.checkNet(this)) {
                ImprovementPlanApi.getImprovementDetails(this, String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), selectedDP.getId(), selectedSkill.getId(), this);
            } else {
                Logger.logD(TAG, " Check Internet and try again ");
            }


        } catch (Exception e) {
            Logger.logE(TAG, "callNextIPAPI : ", e);
        }
    }


    @Override
    public void getImprovementPlanDetails(ImprovementPlanModel improvementPlanModel) {
        try {
            if (improvementPlanModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                //ToastUtils.displayToast( improvementPlanModel.getStatus() , getActivity());
                this.improvementPlanModel=improvementPlanModel;
                activateIP();
            } else {
                ToastUtils.displayToast(improvementPlanModel.getStatus(), this);
            }

        } catch (Exception e) {
            Logger.logE(TAG, "getIPPlanDetails : ", e);
        }
    }

    private void callPause(String pause) {
        try {
            if (CheckNetwork.checkNet(this)) {
                IPPauseApi.callPauseApi(this, userId, childId,  improvementPlanModel.getData().getId(), pause, this);
            } else {
                Logger.logD(TAG, " Check Internet and try again ");
            }

        } catch (Exception e) {
            Logger.logE(TAG, "callPause : ", e);

        }
    }


    @Override
    public void pausePlayAPiResp(PlayPauseModel ppRespModel) {
        try {
            if (ppRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {

                if(isPaused){
                    isPaused=false;
                    pausetaskTV.setText("pause this Task");
                    playImg.setImageResource(R.drawable.pausebutton100);
                }else{
                    isPaused=true;
                    pausetaskTV.setText("Play this Task");
                    playImg.setImageResource(R.drawable.playbigicon100);
                }
                updatePlayPauseTODB();
                if(moveToNext)
                    moveToNextScreen();
            } else {
                ToastUtils.displayToast("Play/pause Unsuccessful",this);
            }

        } catch (Exception e) {
            Logger.logE(TAG, "pausePlayAPiResp : ", e);
        }
    }

    private void moveToNextScreen() {
        try{
            Intent i = new Intent(this, ImprovementPlanActivity.class);
            Bundle b = new Bundle();
            b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSkillsRespModel);
            b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
            b.putParcelable(Constants.BUNDLE_IPMODEL, improvementPlanModel );
            b.putBoolean(Constants.INTENT_FROMIPACTIVATED, true);
            i.putExtras(b);
            startActivity(i);
            finish();
        }catch (Exception e){
            Logger.logE(TAG, "moveToNextScreen : ", e);
        }
    }

    private void updatePlayPauseTODB() {
        try{
            if(isPaused){
                databaseHelper.updateIPPlayStatus(childId, improvementPlanModel.getData().getId() , "pause");
                databaseHelper.insertPPStatus(childId, improvementPlanModel.getData().getId(), "pause");
                ToastUtils.displayToast("Intervention paused successfully",this);
            }else{
                databaseHelper.updateIPPlayStatus(childId, improvementPlanModel.getData().getId() , "play");
                databaseHelper.insertPPStatus(childId, improvementPlanModel.getData().getId(), "play");
                ToastUtils.displayToast("Intervention Resumed successfully",this);
            }
        }catch(Exception e){
            Logger.logE(TAG, "updatePlayPauseTODB : ", e);
        }
    }

}
