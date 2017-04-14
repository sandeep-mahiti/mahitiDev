package com.parentof.mai.views.activity.childstatistics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.ActivateSkillRespCallback;
import com.parentof.mai.activityinterfaces.GetDPSkillsRespCallback;
import com.parentof.mai.activityinterfaces.GetSkillQuestionsCallback;
import com.parentof.mai.activityinterfaces.StatisticsRespCallBack;
import com.parentof.mai.activityinterfaces.StatisticsToViewReportCB;
import com.parentof.mai.api.apicalls.ActivateDPSKillAPI;
import com.parentof.mai.api.apicalls.GetDPSkillsAPI;
import com.parentof.mai.api.apicalls.GetSkillQuestionsAPI;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.db.DbConstants;
import com.parentof.mai.model.activateskillmodel.ActivateSkillRespModel;
import com.parentof.mai.model.decisionpointsmodel.ActiveDP;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.decisionpointsmodel.CompletedDP;
import com.parentof.mai.model.decisionpointsmodel.DPRespModel;
import com.parentof.mai.model.decisionpointsmodel.Data;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.activity.dpchat.ChatActivity;
import com.parentof.mai.views.activity.reports_chathistory_activity.ChildReports_HistoryActivity;
import com.parentof.mai.views.adapters.statisticsadapter.StatisticsAdapter;

import java.util.ArrayList;
import java.util.List;


public class ChildStatistics extends Fragment implements StatisticsToViewReportCB{

    private static final String TAG = "ChildStatistics";
    RecyclerView statisticsRV;
    StatisticsAdapter adapter;

    DPRespModel decisionPointsRespModel;
    private SharedPreferences prefs;
    private DatabaseHelper databaseHelper;
    private AllDP selectedDP;
    DPRespModel dpListModel;
    static Child childBean;
    private String childId;
    private GetSkillsRespModel getSKillRespModel;
    private SkillData selectedSkill;
    private GetSkillQuestionsRespModel skillQstnRespModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            databaseHelper = new DatabaseHelper(getActivity());
            prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);

           /* if (getArguments() != null) {
                // mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
                dpListModel = getArguments().getParcelable(ARG_DP);
                recDateOfAllDPs = getArguments().getString(ARG_RECDATE);
                // checkAvailableList();
            }*/

        } catch (Exception e) {
            Logger.logE(TAG, "oncrte ", e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_child_statistics, container, false);
        try {
            childDetailsFromBundle();
            callStatisticsAPI();

            statisticsRV = (RecyclerView) view.findViewById(R.id.statistics_RV);

            adapter = new StatisticsAdapter(getActivity(), fillDPFromDBtoAdapter(), childBean, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            statisticsRV.setLayoutManager(linearLayoutManager);
            //Adding adapter to recyclerview
            statisticsRV.setAdapter(adapter);
        } catch (Exception e) {
            Logger.logE(TAG, " oncrt ", e);
        }
        return view;
    }

    void childDetailsFromBundle() {
        try {
            if (getActivity().getIntent().getExtras() != null) {
                Bundle b = getActivity().getIntent().getExtras();
                if (b != null && b.getParcelable(Constants.SELECTED_CHILD) != null) {
                    childBean = b.getParcelable(Constants.SELECTED_CHILD);
                }
                if (childBean != null && childBean.getChild() != null) {
                    childId = childBean.getChild().getId();
                }
            }
        } catch (Exception e) {
            Logger.logE(TAG, " gtChldinfo ", e);
        }
    }

    void callStatisticsAPI(){
        try{
            new CallStatistics(getActivity(), (StatisticsRespCallBack) getActivity(), String.valueOf(prefs.getInt(Constants._ID, -1)), childId ).execute();
        }catch (Exception e) {
            Logger.logE(TAG, "fillMainList ", e);
        }
    }

    private    List<com.parentof.mai.model.statisticsmodel.Data> fillDPFromDBtoAdapter() {
        List<com.parentof.mai.model.statisticsmodel.Data> data;
        try {

              data=  databaseHelper.selFromStatisticsTable(String.valueOf(prefs.getInt(Constants._ID,-1)), childId/* selectedDP.getId()*/);
            if(data!=null && data.size()>0)
                return data;

        } catch (Exception e) {
            Logger.logE(TAG, "fillMainList ", e);
        }
        return new ArrayList<com.parentof.mai.model.statisticsmodel.Data>();
    }

    @Override
    public void showReport(String dpId) {

        try{
            this.selectedDP=databaseHelper.selDPbasedonDPID(String.valueOf(prefs.getInt(Constants._ID,-1)), childId, dpId);
            CommonClass commonClassObj=new CommonClass(getActivity(), getActivity(), childBean, selectedDP);
            commonClassObj.checkIfDpisActive();
        }catch (Exception e){
            Logger.logE(TAG, "showReport", e);
        }


    }

/*
    private void checkIfDpisActive() {
        try {
            if (databaseHelper.selDPisActive(String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP)) {//dpis active){

                this.getSKillRespModel = databaseHelper.selAllFromSkillsTable(childId, selectedDP.getId());//from skill table
                if(!this.getSKillRespModel.getData().isEmpty()) {

                    checkForCompSkills();
                }else{
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

    private void checkForCompSkills() {
        try{
            this.selectedSkill = databaseHelper.selLeastRankSkill(childId, selectedDP.getId());//getSelSkill();

            if(selectedSkill.getRank().equalsIgnoreCase("1") ) {
                if (selectedSkill.getIsLocked().equalsIgnoreCase("false")) {
                    this.skillQstnRespModel = databaseHelper.selFromQAsTable(selectedDP.getId(), selectedSkill.getId());//from SKill questions table
                    checkDBForQA();
                    //callActivtSkillAPI();
                } else {
                    ToastUtils.displayToast("Plan is locked! please try another.", getActivity());
                    Logger.logD(TAG, "checkForCompSkills else... skill is locked");
                }
            }else{
                goToSkillsArea();

            }

        }catch (Exception e){
            Logger.logE(TAG, "checkForCompSkills : ", e);
        }
    }


    private void checkDBForQA() {
        try{
            if(skillQstnRespModel.getData().getQuestions().isEmpty()){
                callActivtSkillAPI();
            }else {
                moveToNextActivity();

            }
        }catch (Exception e){
            Logger.logE(TAG, "checkDBForQA", e);
        }
    }


    private void goToSkillsArea() {
        try{
            this.skillQstnRespModel = databaseHelper.selFromQAsTable(selectedDP.getId(), selectedSkill.getId());
            Bundle b = new Bundle();
            b.putParcelable(Constants.BUNDLE_QUESTOBJ, skillQstnRespModel);
            b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSKillRespModel);
            b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
            Intent i = new Intent(getActivity(), ChildReports_HistoryActivity.class);
            i.putExtras(b);
            startActivity(i);
        }catch (Exception e){
            Logger.logE(TAG, "goToSkillsArea", e);
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
                    checkForCompSkills();
                    //this.selectedSkill = databaseHelper.selLeastRankSkill(childId, selectedDP.getId());;//databaseHelper.selLeastRankSkill(childId, selectedDP.getId());
                    // if(selectedDP.getActive().equalsIgnoreCase("false")){

                    *//*}
                    else{
                        GetSkillQuestionsAPI.callSkillQuestions(this, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP.getId(), selectedSkill.getId()  , this);
                    }*//*
                }

            } else {
                ToastUtils.displayToast(getSkillsRespModel.getStatus(), getActivity());
            }
        } catch (Exception e) {
            Logger.logE(TAG, " skillResp : ", e);
        }
    }

    private void callActivtSkillAPI() {
        try {
            if (CheckNetwork.checkNet(getActivity())) {
                ActivateDPSKillAPI.actSkill(getActivity(), String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP.getId(), selectedSkill.getId(), this);
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
            for (SkillData skill :                    skillsList) {
                if (skill.getRank().equalsIgnoreCase("1") )
                    selSkill= skill;

            }



            for (int i=0;i< skillsList.size();i++) {
                for(int j=1;j<=skillsList.size();j++) {
                    if (skillsList.get(i).getRank().equalsIgnoreCase(String.valueOf(j)) && skillsList.get(i).getCompleted() < 100) {
                        selSkill = skillsList.get(i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Logger.logE(TAG, " DPIdClSkilAPI : ", e);
        }
        return selSkill;
    }

    private void callSkillsAPI(String dpId) {
        try {
            if (CheckNetwork.checkNet(getActivity())) {
                GetDPSkillsAPI.getSkillsList(getActivity(), String.valueOf(prefs.getInt(Constants._ID, -1)), childId, dpId, this);
            }else{
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
                callSkillQuestionsAPI();
            } else {
                ToastUtils.displayToast(activateSkillRespModel.getStatus(), getActivity());
            }
        } catch (Exception e) {
            Logger.logE(TAG, " skillResp : ", e);
        }

    }

    private void callSkillQuestionsAPI() {
        try{
            if (CheckNetwork.checkNet(getActivity())) {
                GetSkillQuestionsAPI.callSkillQuestions(getActivity(), String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP.getId(), selectedSkill.getId(), this);
            }else{
                Logger.logD(TAG, " Check Internet and try again ");
            }

        }catch (Exception e){
            Logger.logE(TAG, "calSkilQAPI", e);
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
                    databaseHelper.insToSkillQstnTable( skillQuestionsRespModel.getData().getQuestions(), childId, selectedDP.getId(), selectedSkill.getId());
                    databaseHelper.updateDPType(String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP.getId(), "active");
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
            b.putParcelable(Constants.BUNDLE_QUESTOBJ, skillQstnRespModel);
            b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSKillRespModel);
            b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
            Intent i = new Intent(getActivity(), ChatActivity.class);
            i.putExtras(b);
            startActivity(i);

        } catch (Exception e) {
            Logger.logE(TAG, " mvToNxActvt : ", e);
        }

    }*/

}
