package com.parentof.mai.views.activity.improvementplan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.IPActivateRespCallBack;
import com.parentof.mai.activityinterfaces.IPPauseApiRespCallback;
import com.parentof.mai.api.apicalls.IPPauseApi;
import com.parentof.mai.api.apicalls.InterventionActApi;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.ImprovementPlanModel.Data;
import com.parentof.mai.model.ImprovementPlanModel.ImprovementPlanModel;
import com.parentof.mai.model.InterventionModel.InterventaionModel;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.getchildrenmodel.Child_;
import com.parentof.mai.model.playpauserespmodel.PlayPauseModel;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ProgressDrawable;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.StringUtils;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.activity.ChildDashboardActivity;
import com.parentof.mai.views.adapters.DaysWeeksAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;


public class ImprovementPlanActivity extends AppCompatActivity implements View.OnClickListener, IPActivateRespCallBack, IPPauseApiRespCallback {

    //Elements in tOP layout with banner Image and progressBar
    RelativeLayout bannerImage;
    TextView learnPlanNumTV;
    TextView dpNameTV;
    ImageView skillIcon;
    TextView skillNameTV;
    TextView levelTV;
    ProgressBar progressBar;

    //Elements below TaskLine layout
    ImageView playPauseImgBtn;
    TextView taskHelloTV;
    TextView practiceStringTV;
    ImageView smallCircleImg1;
    TextView circleString1;
    ImageView smallCircleImg2;
    TextView circleString2;
    ImageView smallCircleImg3;
    TextView circleString3;
    ImageView smallCircleImg4;
    TextView circleString4;

    //do activity for no of days and repeat for no of times - elements
    TextView doActivityStringTV;
    TextView doNoOfDaysTV;
    TextView doActivityDaysStringTV;
    TextView repeatStringTV;
    TextView repeatNoOfTimesTV;
    TextView repeatTimeStringTV;

    TextView pointsToKeepStrTV;

    //DO layout elements
    ImageView doUpArrowImg;
    ImageView doDownArrowImg;
    LinearLayout doStringsLL;
    TextView doStringTV1;
    TextView doStringTV2;

    //DOn't layout elements
    ImageView dontUpArrowImg;
    ImageView dontDownArrowImg;
    LinearLayout dontStringsLL;
    TextView dontStringTV1;
    TextView dontStringTV2;

    TextView beginTV;


    RecyclerView daysweeksRV;
    private RecyclerView.Adapter adapter;
    private String TAG = "ImprmntPlnActvt";


    GetSkillsRespModel getSkillsRespModel;
    AllDP selectedDP;
    ArrayList<Question> qstnAnsList;

    GetSkillQuestionsRespModel skillQuestionsRespModel;
    private Child childBean;
    SkillData selectedSkill;//Datum selectedSkillObj;
    private String dpId;
    private SharedPreferences prefs;
    ImprovementPlanModel improvementPlanModel;
    DatabaseHelper databaseHelper;
    private String userId;
    private String childId;
    private TextView pausePlanTVBtn;
    private TextView doThisLaterTVBtn;
    private TextView backTVBtn;
    TextView myChildCanDdTV;

    private ListView tasksListRv;
    StringUtils strutils;
    private boolean fromActivated;
    private String pauseStr = "play";
    private Data ipDataObj;
    private boolean isPaused;
    int[] percentageLevels={10,20,30,40,50,60};
    private boolean moreThanThreeIP=false;
    private AlertDialog alertDialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improvement_plan);


        //Adding toolbar to activity

        Toolbar toolbar = (Toolbar) findViewById(R.id.ip_toolbar);
        toolbar.setTitle("Improvement Plan");

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

        }
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        //Elements in tOP layout with banner Image and progressBar
        bannerImage = (RelativeLayout) findViewById(R.id.bannerImg_below_toolbar);
        learnPlanNumTV = (TextView) findViewById(R.id.learn_plan_level_TV); //skill_name_TV
        dpNameTV = (TextView) findViewById(R.id.DP_Name_TV);
        skillIcon = (ImageView) findViewById(R.id.skill_icon);
        skillNameTV = (TextView) findViewById(R.id.skill_name_TV);
        levelTV = (TextView) findViewById(R.id.level_TV);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_test);
        ProgressDrawable bgProgress = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            bgProgress = new ProgressDrawable(6, getColor(R.color.green), getColor(R.color.white));
        } else {
            bgProgress = new ProgressDrawable(6, getResources().getColor(R.color.green), getResources().getColor(R.color.white));
        }
        progressBar.setProgressDrawable(bgProgress);

        //Elements below TaskLine layout
        playPauseImgBtn = (ImageView) findViewById(R.id.playImageButton);
        playPauseImgBtn.setOnClickListener(this);
        taskHelloTV = (TextView) findViewById(R.id.task_hello_TV);
        practiceStringTV = (TextView) findViewById(R.id.practice_string_TV);

        //do activity for no of days and repeat for no of times - elements
        doActivityStringTV = (TextView) findViewById(R.id.do_activity_string_TV);
        doNoOfDaysTV = (TextView) findViewById(R.id.do_activity_noOfDays_TV);
        doActivityDaysStringTV = (TextView) findViewById(R.id.do_activity_daysString_TV);
        repeatStringTV = (TextView) findViewById(R.id.repeat_string_TV);
        repeatNoOfTimesTV = (TextView) findViewById(R.id.repeat_noOfTimes_TV);
        repeatTimeStringTV = (TextView) findViewById(R.id.repeat_timesString_TV);
        pointsToKeepStrTV = (TextView) findViewById(R.id.pointsToKeep_String_TV);

        //Do layout elements
        doUpArrowImg = (ImageView) findViewById(R.id.do_upArrow);
        doUpArrowImg.setOnClickListener(this);
        doDownArrowImg = (ImageView) findViewById(R.id.do_downArrow);
        doDownArrowImg.setOnClickListener(this);
        doStringsLL = (LinearLayout) findViewById(R.id.do_Strings_Llayout);
        doStringTV1 = (TextView) findViewById(R.id.do_TV1);
        doStringTV2 = (TextView) findViewById(R.id.do_TV2);

        //Don't layout elements
        dontUpArrowImg = (ImageView) findViewById(R.id.dont_upArrow);
        dontUpArrowImg.setOnClickListener(this);
        dontDownArrowImg = (ImageView) findViewById(R.id.dont_downArrow);
        dontDownArrowImg.setOnClickListener(this);
        dontStringsLL = (LinearLayout) findViewById(R.id.dont_Strings_Llayout);
        dontStringTV1 = (TextView) findViewById(R.id.dont_TV1);
        dontStringTV2 = (TextView) findViewById(R.id.dont_TV2);
        beginTV = (TextView) findViewById(R.id.beginBtnTv);
        beginTV.setOnClickListener(this);
        myChildCanDdTV= (TextView) findViewById(R.id.myChildCanDoTVBtn);
        myChildCanDdTV.setOnClickListener(this);

        daysweeksRV = (RecyclerView) findViewById(R.id.daysWeeksRv);

        databaseHelper = new DatabaseHelper(this);
        getValuesFromBundle();
        userId = String.valueOf(prefs.getInt(Constants._ID, -1));
        childId = childBean.getChild().getId();
        if(fromActivated)
            getPlanDetailsfromDB();





        setdaysweeksAdapter();

        //tasksListRv= (ListView) findViewById(R.id.taskListRV);
        setTasksListAdapter();

        if (selectedSkill.getCover() != null)
            Picasso.with(this).load(selectedSkill.getCover()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    bannerImage.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });


        dpNameTV.setText(selectedDP.getName());
        learnPlanNumTV.setText(getString(R.string.learning).concat(" " + improvementPlanModel.getData().getLevel()));
        skillNameTV.setText(selectedSkill.getName());
        if(improvementPlanModel.getData().getLevel()>0 && improvementPlanModel.getData().getLevel()<7)
            progressBar.setProgress(percentageLevels[improvementPlanModel.getData().getLevel()-1]);
        taskHelloTV.setText(improvementPlanModel.getData().getTaskName());
        practiceStringTV.setText(improvementPlanModel.getData().getTaskObjectives());
        doNoOfDaysTV.setText(String.valueOf(improvementPlanModel.getData().getDuration()));
        strutils = new StringUtils();
        String dos = "";
        for (int i = 0; i < improvementPlanModel.getData().getDos().size(); i++) {
            dos = dos.concat("- " + strutils.replaceLabel(childBean.getChild(), selectedDP.getName(), selectedSkill.getName(), this, improvementPlanModel.getData().getDos().get(i), "") + "\n");
        }
        doStringTV1.setText(dos);
        doStringTV2.setVisibility(View.GONE);


        String donts = "";
        for (int i = 0; i < improvementPlanModel.getData().getDonts().size(); i++) {
            donts = donts.concat("- " + strutils.replaceLabel(childBean.getChild(), selectedDP.getName(), selectedSkill.getName(), this, improvementPlanModel.getData().getDonts().get(i), "") + "\n");
        }
        dontStringTV1.setText(donts);
        dontStringTV2.setVisibility(View.GONE);

        //Calling Improvement plan API....
       /* if(CheckNetwork.checkNet(this)) {
            ImprovementPlanApi.getImprovementDetails(this, this);
        }else {
            ToastUtils.displayToast(Constants.NO_INTERNET,this);
        }*/


    }


    private void getValuesFromBundle() {
        try {
            improvementPlanModel = new ImprovementPlanModel();
            if (getIntent().getExtras() != null) {
                Bundle b = getIntent().getExtras();
                //qstnAnsList = b.getParcelableArrayList(Constants.BUNDLE_QSTNANSLIST);

                // skillQuestionsRespModel = b.getParcelable(Constants.BUNDLE_QUESTOBJ);
                fromActivated = b.getBoolean(Constants.INTENT_FROMIPACTIVATED);
                getSkillsRespModel = b.getParcelable(Constants.BUNDLE_SKILLSOBJ);
                selectedSkill = b.getParcelable(Constants.BUNDLE_SELSKILLOBJ);
                childBean = b.getParcelable(Constants.BUNDLE_CHILDOBJ);
                selectedDP = b.getParcelable(Constants.BUNDLE_SELDPOBJ);
                improvementPlanModel = b.getParcelable(Constants.BUNDLE_IPMODEL);
                if (selectedDP != null) {
                    dpId = selectedDP.getId();
                } else {
                    ToastUtils.displayToast("Please comeback later to continue", this);
                }


            }
        } catch (Exception e) {
            Logger.logE(TAG, "valsFrmBndl", e);
        }
    }

    private void getPlanDetailsfromDB() {
        try {
            databaseHelper = new DatabaseHelper(this);
            // if()
            ipDataObj = databaseHelper.selPlanFromIPTable(childId, dpId, selectedSkill.getId());
            if (ipDataObj != null) {
                checkPlanPaused();
                improvementPlanModel = new ImprovementPlanModel();
                improvementPlanModel.setData(ipDataObj);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "gtPlnDetailsfrmDB : ", e);
        }
    }

    private void checkPlanPaused() {
        try {
            if (ipDataObj.getIsPaused()!=null && ipDataObj.getIsPaused().equalsIgnoreCase("play")) {
                isPaused = false;
                playPauseImgBtn.setImageResource(R.drawable.pausebutton100);
            } else {
                isPaused = true;
                playPauseImgBtn.setImageResource(R.drawable.playbigicon100);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "checkPlanPaused : ", e);
        }
    }


    private void setdaysweeksAdapter() {
        try {
            Logger.logD(TAG, "getDuration" + improvementPlanModel.getData().getDuration());
            Logger.logD(TAG, "getDuration" + improvementPlanModel.getData().getReminder());
            adapter = new DaysWeeksAdapter(this, improvementPlanModel.getData().getDuration(), improvementPlanModel.getData().getReminder());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            daysweeksRV.setLayoutManager(linearLayoutManager);
            //Adding adapter to recyclerview
            daysweeksRV.setAdapter(adapter);
        } catch (Exception e) {
            Logger.logE(TAG, "setdaysweeksAdapter", e);
        }
    }

    private void setTasksListAdapter() {
        try {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LinearLayout insertPoint = (LinearLayout) findViewById(R.id.container);
// fill in any details dynamically here
            for (int i = 0; i < improvementPlanModel.getData().getTaskSteps().size(); i++) {
                View v = vi.inflate(R.layout.iptaskslist, null);
                TextView textView = (TextView) v.findViewById(R.id.task_steps_label);
                textView.setTextColor(getResources().getColor(R.color.white));
                Child_ child = childBean.getChild();
                String dpName = selectedDP.getName();
                String skillName = selectedSkill.getName();
                String steps = improvementPlanModel.getData().getTaskSteps().get(i);
                StringUtils utils = new StringUtils();
                String text = utils.replaceLabel(child, dpName, skillName, this, steps, "");
                textView.setText(text);
                insertPoint.addView(v);// 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }
        } catch (Exception e) {
            Logger.logE(TAG, "setdaysweeksAdapter", e);
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
            case R.id.do_upArrow:
                doUpArrowImg.setVisibility(View.GONE);
                doDownArrowImg.setVisibility(View.VISIBLE);
                doStringsLL.setVisibility(View.GONE);
                break;
            case R.id.do_downArrow:
                doDownArrowImg.setVisibility(View.GONE);
                doUpArrowImg.setVisibility(View.VISIBLE);
                doStringsLL.setVisibility(View.VISIBLE);
                break;
            case R.id.dont_upArrow:
                dontUpArrowImg.setVisibility(View.GONE);
                dontDownArrowImg.setVisibility(View.VISIBLE);
                dontStringsLL.setVisibility(View.GONE);
                break;
            case R.id.dont_downArrow:
                dontDownArrowImg.setVisibility(View.GONE);
                dontUpArrowImg.setVisibility(View.VISIBLE);
                dontStringsLL.setVisibility(View.VISIBLE);
                break;
            case R.id.playImageButton:
                beginIP();
                break;
            case R.id.beginBtnTv:
                if (fromActivated) {
                    pauseStr = "play";
                    playPauseIP();
                } else {
                    beginIP();
                }
                break;
            case R.id.myChildCanDoTVBtn:
                //beginIP();
                break;
            default:
                break;

        }

    }

    private void playPauseIP() {
        try {
            if (CheckNetwork.checkNet(this)) {
                Logger.logD(TAG, "Calling IP PAuse/play with  " + pauseStr);
                IPPauseApi.callPauseApi(this, userId, childId, improvementPlanModel.getData().getId(), pauseStr, this);
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
                ToastUtils.displayToast(ppRespModel.getData(), this);
                playPauseImgBtn.setImageResource(R.drawable.playbigicon100);
                updatePlayPauseTODB();
                setPausePlay();
            } else {
                ToastUtils.displayToast(ppRespModel.getData(), this);
            }

        } catch (Exception e) {
            Logger.logE(TAG, "pausePlayAPiResp : ", e);
        }
    }

    private void setPausePlay() {
        try{
            if(pauseStr.equalsIgnoreCase("play")) {
                pauseStr = "pause";
                playPauseImgBtn.setImageResource(R.drawable.pausebutton100);
            }else {
                pauseStr = "play";
                playPauseImgBtn.setImageResource(R.drawable.playbigicon100);
            }
            if(moreThanThreeIP)
                moveToActivityPage();
            else
                moveTOActivatedActivity();
        }catch (Exception e){
            Logger.logE(TAG, "setPausePlay : ", e);
        }
    }

    private void moveToActivityPage() {
        try{
            if (childBean.getChild().getId() == null) {
                Logger.logD(TAG, "Child data not available");
            } else {
                Bundle b=new Bundle();
                int selectedValues = 3;
                Logger.logD(TAG, "moving to activity " +selectedValues);
                b.putString(Constants.UPDATE_CHILD_ID, childId);
                b.putParcelable(Constants.SELECTED_CHILD, childBean);
                // b.putParcelable(Constants.DECISION_POINTS, decisionPointsRespModel);
                b.putInt(Constants.SELECTED_CATEGORY, selectedValues);
                Intent i = new Intent(this, ChildDashboardActivity.class);
                i.putExtras(b);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(i);
            }
        }catch (Exception e){
            Logger.logE(TAG, "moveToActivityPage : ", e);
        }
    }

    private void updatePlayPauseTODB() {
        try {
            if (isPaused) {
                databaseHelper.updateIPPlayStatus(childId, improvementPlanModel.getData().getId(), "pause");
            } else {
                databaseHelper.updateIPPlayStatus(childId, improvementPlanModel.getData().getId(), "play");
            }
        } catch (Exception e) {
            Logger.logE(TAG, "updatePlayPauseTODB : ", e);
        }
    }

    private void beginIP() {
        try {
            //check for 3 interventions.
            //TODO uncomment for popup
            //activeInterventionsPopup();
            moreThanThreeIP=databaseHelper.checkFor3ActiveInterventions(childId);
            Logger.logD(TAG, " moreThanThreeIP : "+moreThanThreeIP);
            if (moreThanThreeIP) {
                activeInterventionsPopup();
            } else {
                callIPActivateAPI();
            }

        } catch (Exception e) {
            Logger.logE(TAG, "beginIP", e);
        }
    }

    private void callIPActivateAPI() {
        try{
            if (CheckNetwork.checkNet(this)) {
                Logger.logD(TAG, "Calling IP activate  ");
                InterventionActApi.callInterventionActivateApi(this, this, userId, childId, dpId, improvementPlanModel.getData().getId());
            } else {
                Logger.logD(TAG, "Check Internet and try again! ");
            }
        }catch (Exception e){
            Logger.logE(TAG, "callIPActivateAPI : ", e);
        }
    }


    void activeInterventionsPopup() {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.improvementplan_popup, null);
            TextView activePlansTV = (TextView) dialogView.findViewById(R.id.alreadyActiveTV);
            activePlansTV.setText(getString(R.string.active_ip).concat(" " + childBean.getChild().getFirstName()));

            pausePlanTVBtn = (TextView) dialogView.findViewById(R.id.pausePlanTV);
            doThisLaterTVBtn = (TextView) dialogView.findViewById(R.id.doThisLaterTV);
            backTVBtn = (TextView) dialogView.findViewById(R.id.backTV);

            alertDialog.setView(dialogView);
            alertDialog1 = alertDialog.create();
            alertDialog1.setCanceledOnTouchOutside(false);
            alertDialog1.setCancelable(false);
            alertDialog1.show();

            pausePlanTVBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                    activateAndCheckToPause();

                }
            });
            doThisLaterTVBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                    activateAndCheckToPause();

                }
            });
            backTVBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                }
            });
        } catch (Exception e) {
            Logger.logE(TAG, "activeInterventionsPopup", e);
        }
    }

    private void activateAndCheckToPause() {
        try{
            callIPActivateAPI();
            if(moreThanThreeIP) {
                pauseStr="pause";
                playPauseIP();
            }
        }catch (Exception e) {
            Logger.logE(TAG, "activateAndCheckToPause", e);
        }
    }

    @Override
    public void iPActResp(InterventaionModel interventaionModel) {

        try {
            if (interventaionModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                Logger.logD(TAG, interventaionModel.getStatus());

                prefs.edit().putBoolean("ip_activated", true).apply();
                playPauseImgBtn.setImageResource(R.drawable.pausebutton100);
                if (improvementPlanModel.getData() != null)
                    databaseHelper.delFromIPTable(childId, improvementPlanModel.getData().getId());
                databaseHelper.insImprovementPlanTable(improvementPlanModel.getData(), userId, childId, selectedDP, selectedSkill);
                if(!moreThanThreeIP)
                    moveTOActivatedActivity();

            } else {
                Logger.logD(TAG, interventaionModel.getStatus());
                ToastUtils.displayToast(interventaionModel.getMessage(), this);
            }

        } catch (Exception e) {
            Logger.logE(TAG, "beginIP", e);
        }

    }

    private void moveTOActivatedActivity() {
        try{
            Intent i = new Intent(this, ImprovementActivatedActivity.class);
            Bundle b = new Bundle();
            b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSkillsRespModel);
            b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
            b.putParcelable(Constants.BUNDLE_IPMODEL, improvementPlanModel);
            b.putBoolean(Constants.INTENT_FROMREPORTS, false);
            i.putExtras(b);
            startActivity(i);
            finish();
        }catch (Exception e) {
            Logger.logE(TAG, "moveTOActivatedActivity ", e);
        }
    }


}
