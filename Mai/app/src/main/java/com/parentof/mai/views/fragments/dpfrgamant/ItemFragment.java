package com.parentof.mai.views.fragments.dpfrgamant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.ActivateSkillRespCallback;
import com.parentof.mai.activityinterfaces.DecisionPointsCallback;
import com.parentof.mai.activityinterfaces.GetDPSkillsRespCallback;
import com.parentof.mai.activityinterfaces.GetSkillQuestionsCallback;
import com.parentof.mai.activityinterfaces.SelectedDpTOActivtyCallback;
import com.parentof.mai.api.apicalls.ActivateDPSKillAPI;
import com.parentof.mai.api.apicalls.DecisionPointsAPI;
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
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.utils.Utility;
import com.parentof.mai.views.activity.dpchat.ChatActivity;
import com.parentof.mai.views.activity.reports_chathistory_activity.ChildReports_HistoryActivity;
import com.parentof.mai.views.adapters.dpsadapters.VerticalRVAdapter;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class ItemFragment extends Fragment implements SelectedDpTOActivtyCallback,  DecisionPointsCallback {


    private static final String TAG = "itmFrgmn ";

    static Child childBean;
    private String childId;


    DPRespModel decisionPointsRespModel;
    private SharedPreferences prefs;
    private DatabaseHelper databaseHelper;
    private AllDP selectedDP;
    private GetSkillsRespModel getSKillRespModel;
    private SkillData selectedSkill;

    DPRespModel dpListModel;

    private GetSkillQuestionsRespModel skillQstnRespModel;
    private String recDateOfAllDPs;
    List<AllDP> newDPsList;
    Context context;
    private VerticalRVAdapter adapter;
    RecyclerView recyclerView;



    TextView noDps;

    boolean executed;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_COLUMN_COUNT, columnCount);
       /* args.put(ARG_CONTEXT, context);
        args.putString(ARG_RECDATE, recDateOfAllDPs);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            databaseHelper = new DatabaseHelper(getActivity());
            prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
            childDetailsFromBundle();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // createDummyData();
        // Set the adapter
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_dps);
        noDps = (TextView) view.findViewById(R.id.no_dps);
        MyProgress.show(context, "","");
        if (prefs.getBoolean(childId, true) && CheckNetwork.checkNet(getActivity())) {
            executed=true;
            DecisionPointsAPI.getDPs(getActivity(), String.valueOf(prefs.getInt(Constants._ID, -1)), childId, this);
        } else {

                fillDPFromDBtoAdapter();
                fetchNewDPsFromDB();
                callThreadtowait();

        }

        MyProgress.CancelDialog();
        return view;
    }

    private void fillDPFromDBtoAdapter() {
        try {
            //mainDPList = new HashMap<>();
            dpListModel = new DPRespModel();
            List<AllDP> allDPList = databaseHelper.selDPBasedOnTypeAll(String.valueOf(prefs.getInt(Constants._ID, -1)), childId, "all");
            List<ActiveDP> activeDPList = databaseHelper.selDPBasedOnTypeActive(String.valueOf(prefs.getInt(Constants._ID, -1)), childId, "active");
            List<CompletedDP> completedDPList = databaseHelper.selDPBasedOnTypeComp(String.valueOf(prefs.getInt(Constants._ID, -1)), childId, "completed");
            Data data = new Data();
            data.setAllDP(allDPList);
            data.setActiveDP(activeDPList);
            data.setCompletedDP(completedDPList);
            dpListModel.setData(data);
        } catch (Exception e) {
            Logger.logE(TAG, "fillMainList ", e);
        }
    }



    private void fetchNewDPsFromDB() {
        try {
            newDPsList = new ArrayList<>();
            if (recDateOfAllDPs != null && !recDateOfAllDPs.isEmpty())
                newDPsList = databaseHelper.selNewDPsFromDB(String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), "all", recDateOfAllDPs);
        } catch (Exception e) {
            Logger.logE(TAG, " fetchNewDPsFromDB : ", e);
        }
    }

    private void callThreadtowait() {
        try{

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // CallStatistics your code here
                    try {

                        Logger.logD(TAG, "try block of thread ");
                        sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Logger.logE(TAG, "catch block of thread ", e);
                    } finally {

                        fillListAndSetAdapter();
                        Logger.logD(TAG, "Finally block of thread ");
                    }
                }
            });
        }catch(Exception e){
            Logger.logE(TAG, " callThreadtowait : ", e);
        }
    }

/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if(!executed) {
                Logger.logD(TAG, "onActivityCreated ");
                fillDPFromDBtoAdapter();
                fetchNewDPsFromDB();

                callThreadtowait();
            }//fillListAndSetAdapter();
        } catch (Exception e) {
            Logger.logE(TAG, " onResume : ", e);
        }
    }*/


    @Override
    public void onStart() {
        super.onStart();
        try {
            Logger.logD(TAG, "onStart ");
            if (prefs.getInt(PreferencesConstants.FROMBACK, -1) == 1) {
                    prefs.edit().putInt(PreferencesConstants.FROMBACK, 0).apply();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " onResume : ", e);
        }

    }



    /* @Override
    public void onResume() {
        super.onResume();
        try{
            fillDPFromDBtoAdapter();
            fetchNewDPsFromDB();
            fillListAndSetAdapter();
        }catch (Exception e){
            Logger.logE(TAG, " onResume : ", e);
        }
    }*/

    private void fillListAndSetAdapter() {
        try {
            if ((dpListModel != null && dpListModel.getData() != null) && !dpListModel.getData().getAllDP().isEmpty() || !dpListModel.getData().getCompletedDP().isEmpty() || !dpListModel.getData().getActiveDP().isEmpty()) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new VerticalRVAdapter(getActivity(), newDPsList, dpListModel, this);
                recyclerView.setAdapter(adapter);
            } else {
                noDps.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " fillListAndSetAdapter : ", e);
        }
    }

    @Override
    public void getAllDPsForChild(DPRespModel decisionPointsRespModel) {
        try {
            if (decisionPointsRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                //TODO clear prefs for OTP and secQstns verifications, need clarification
                //prefs.edit().clear();
                //prefs.edit().remove(Constants)
                // ToastUtils.displayToast(blockuserRespModel.getData(), this);
                Logger.logD(TAG, "block User resp " + decisionPointsRespModel.getStatus() + "\n " + decisionPointsRespModel.getData());
                this.decisionPointsRespModel = decisionPointsRespModel;
                checkTabIsEmpty();
            } else {
                //ToastUtils.displayToast(blockuserRespModel.getData(), this);
                Logger.logD(TAG, "block User resp " + decisionPointsRespModel.getStatus() + "\n " + decisionPointsRespModel.getData());
                //finish();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " BlkUsrResp : ", e);
        }
    }

    private void checkTabIsEmpty() {
        try {
            //check if table has records or empty
            if (databaseHelper.isTableNotEmpty(DbConstants.DECISION_POINTS_TABLE)) {
                //if table is not empty delete all records
                recDateOfAllDPs = databaseHelper.selRecDateOfAllDPs(String.valueOf(prefs.getInt(Constants._ID, -1)), childId, "all");
                databaseHelper.delAllfromTable(DbConstants.DECISION_POINTS_TABLE, String.valueOf(prefs.getInt(Constants._ID, -1)), childId);
            }
            databaseHelper.insToDPTable(decisionPointsRespModel, String.valueOf(prefs.getInt(Constants._ID, -1)), childId);
            fillDPFromDBtoAdapter();
            fetchNewDPsFromDB();
            callThreadtowait();//fillListAndSetAdapter();
            prefs.edit().putBoolean(childId, false).apply();
            // adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Logger.logE(TAG, " checkTabIsEmpty : ", e);
        }
    }

    @Override
    public void selectedDPtoCallSkillsAPI(AllDP selectedDP, boolean gotoChat) {
        //this.gotoChat = gotoChat;
        this.selectedDP = selectedDP;
        //if (gotoChat) {
        if (selectedDP != null && selectedDP.getId() != null) {
            if (selectedDP.getActive().equalsIgnoreCase("true")) {
                CommonClass commonClassObj=new CommonClass(getActivity(), getActivity(), childBean, selectedDP);
                commonClassObj.checkIfDpisActive();

            } else {
                ToastUtils.displayToast("Not active!", getActivity());
            }
            /*else{
                GetSkillQuestionsAPI.callSkillQuestions(this, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP.getId(),   ,  this);
            }*/
        }
        //}
    }

    @Override
    public void addReminder(int pos) {

    }

    @Override
    public void responseDone(int reminderId) {

    }

   /* private void checkIfDpisActive() {
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
