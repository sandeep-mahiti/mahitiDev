package com.parentof.mai.views.fragments.reports_chathistory_fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.ActivateSkillRespCallback;
import com.parentof.mai.activityinterfaces.GetSkillQuestionsCallback;
import com.parentof.mai.activityinterfaces.ImprovementActiCallback;
import com.parentof.mai.activityinterfaces.ImprovementPlanCallBack;
import com.parentof.mai.activityinterfaces.TalkToMaiCallback;
import com.parentof.mai.api.apicalls.ActivateDPSKillAPI;
import com.parentof.mai.api.apicalls.GetSkillQuestionsAPI;
import com.parentof.mai.api.apicalls.ImprovementPlanApi;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.db.DbConstants;
import com.parentof.mai.model.ImprovementPlanModel.ImprovementPlanModel;
import com.parentof.mai.model.activateskillmodel.ActivateSkillRespModel;
import com.parentof.mai.model.dayLoggedModel.DayLoggedModel;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.activity.dpchat.ChatActivity;
import com.parentof.mai.views.activity.improvementplan.ImprovementActivatedActivity;
import com.parentof.mai.views.activity.improvementplan.ImprovementPlanActivity;
import com.parentof.mai.views.adapters.reports_history_adapters.ChildReportsListAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link ChildReportsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildReportsFragment extends Fragment implements TalkToMaiCallback, ImprovementActiCallback, ImprovementPlanCallBack, ActivateSkillRespCallback, GetSkillQuestionsCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //Recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    LinearLayout reportsListTopLayout;

    GetSkillsRespModel getSkillsRespModel;
    AllDP selectedDP;
    ArrayList<Question> qstnAnsList;
    //private ActivateSkillRespModel activateSkillRespModel;
    GetSkillQuestionsRespModel skillQuestionsRespModel;
    private Child childBean;
    SkillData selectedSkill;//Datum selectedSkillObj;
    private String dpId;
    private SharedPreferences prefs;
    TextView startPlanTV;


    // private OnFragmentInteractionListener mListener;
    private String TAG = "chldRepFrag";

    DatabaseHelper dbHelper;
    //selAllFromSkillsTable;
    List<SkillData> skillsList;
    int noOfQstnsAnswered = 0;


    public ChildReportsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildReportsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildReportsFragment newInstance(String param1, String param2) {
        ChildReportsFragment fragment = new ChildReportsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getValuesFromBundle();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_reports, container, false);
        //reports_list= (ListView) view.findViewById(R.id.reports_listV);

       /* RecyclerView recyclerView = (RecyclerView) view;

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new VerticalRVAdapter(getActivity(),null, mListener));*/
        //Initializing recyclerview

        //startPlanTV= (TextView) view.findViewById(R.id.startPlanTVBtn);

        //reportsListTopLayout= (LinearLayout) view.findViewById(R.id.reportsBG_lLayout);

        dbHelper = new DatabaseHelper(getActivity());
        prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);


        noOfQstnsAnswered = dbHelper.noOfQstnsAnswered(selectedDP.getId(), selectedSkill.getId());
        recyclerView = (RecyclerView) view.findViewById(R.id.reports_listV);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChildReportsListAdapter(getActivity(), selectedDP, getSkillsRespModel, skillQuestionsRespModel.getData().getQuestions().size(), noOfQstnsAnswered, this, this);
        recyclerView.setAdapter(adapter);

        //recyclerView.addOnScrollListener(new CustomScrollListener());


        //scrollToBottom();

        /*startPlanTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), ImprovementPlanActivity.class);
                getActivity().startActivity(i);

            }
        });*/


        //method to scroll the recyclerview to bottom


        return view;


// as 60 is max, we specified in the xml layout, 30 will be its half 😉
        // The      text inside the circular progressbar


    }

    private void getValuesFromBundle() {
        try {
            if (getActivity().getIntent().getExtras() != null) {
                Bundle b = getActivity().getIntent().getExtras();
                // qstnAnsList = b.getParcelableArrayList(Constants.BUNDLE_QSTNANSLIST);
                skillQuestionsRespModel = b.getParcelable(Constants.BUNDLE_QUESTOBJ);
                getSkillsRespModel = b.getParcelable(Constants.BUNDLE_SKILLSOBJ);
                selectedSkill = b.getParcelable(Constants.BUNDLE_SELSKILLOBJ);
                childBean = b.getParcelable(Constants.BUNDLE_CHILDOBJ);
                selectedDP = b.getParcelable(Constants.BUNDLE_SELDPOBJ);
                if (selectedDP != null) {
                    dpId = selectedDP.getId();
                } else {
                    ToastUtils.displayToast("Please comeback later to continue", getActivity());
                }
            }
        } catch (Exception e) {
            Logger.logE(TAG, "valsFrmBndl", e);
        }
    }

    private void scrollToBottom() {
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() > 1)
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
    }


    @Override
    public void callChatActivity(SkillData skillData, int questionsLeft) {
        try {
            this.selectedSkill = skillData;
            //if()  No questions related to skills call skill activate API
            // if (dbHelper.qstnsAvailableForSelSkill(String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), selectedDP, skillData.getId())) {//dpis active){

            //this.skillQuestionsRespModel = dbHelper.selFromQAsTable(selectedDP.getId(), selectedSkill.getId());
            List<Question> qusetionsList = dbHelper.selQAfrmSkillQATable(childBean.getChild().getId(), selectedDP.getId(), selectedSkill.getId(), 1);
            if (!qusetionsList.isEmpty()) {

                //from SKill questions table
                moveToNextActivity();
            } else {
                //ToastUtils.displayToast("Contacting Server!", getActivity());
                Logger.logD(TAG, " No skills for selected DP, Contacting server...");
                callActivtSkillAPI();
            }



           /* if(questionsLeft==0) {
                callActivtSkillAPI();
            }else {


                Intent i = new Intent(getActivity(), ChatActivity.class);
                getActivity().getIntent().getExtras().putParcelable(Constants.BUNDLE_SELSKILLOBJ, skillData);
                i.putExtras(getActivity().getIntent().getExtras());
                startActivity(i);
            }*/
        } catch (Exception e) {
            Logger.logE(TAG, "callChatActivity", e);
        }
    }

    private void callActivtSkillAPI() {
        try {
            if (CheckNetwork.checkNet(getActivity())) {
                ActivateDPSKillAPI.actSkill(getActivity(), String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), selectedDP.getId(), selectedSkill.getId(), this);
            } else {
                Logger.logD(TAG, " Check Internet and try again ");
            }
        } catch (Exception e) {
            Logger.logE(TAG, " callActvtSkillAPI : ", e);
        }
    }

    @Override
    public void activateSkillResp(ActivateSkillRespModel activateSkillRespModel) {

        try {
            if (activateSkillRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                // ToastUtils.displayToast(activateSkillRespModel.getStatus(), this);
                // if(gotoChat){
                if (CheckNetwork.checkNet(getActivity())) {
                    GetSkillQuestionsAPI.callSkillQuestions(getActivity(), String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), selectedDP.getId(), selectedSkill.getId(), this);
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
                    this.skillQuestionsRespModel = skillQuestionsRespModel;
                    dbHelper.delfromQATable(DbConstants.SKILLS_QSTN_TABLE, selectedDP.getId(), selectedSkill.getId());
                    dbHelper.insToSkillQstnTable( skillQuestionsRespModel.getData().getQuestions(), childBean.getChild().getId(), selectedDP.getId(), selectedSkill.getId());
                    dbHelper.updateDPType(String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), selectedDP.getId(), "active");
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
            b.putParcelable(Constants.BUNDLE_QUESTOBJ, skillQuestionsRespModel);
            b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSkillsRespModel);
            b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
            Intent i = new Intent(getActivity(), ChatActivity.class);
            i.putExtras(b);
            startActivity(i);

        } catch (Exception e) {
            Logger.logE(TAG, " mvToNxActvt : ", e);
        }

    }


    @Override
    public void toImprovemntActivity(SkillData skillData) {
        try {
            this.selectedSkill=skillData;
            if (dbHelper.anyIPForThisSkill(childBean.getChild().getId(), skillData.getId())) {
                Intent i = new Intent(getActivity(), ImprovementActivatedActivity.class);
                Bundle b = new Bundle();
                b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSkillsRespModel);
                b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, skillData);
                b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
                b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
                b.putBoolean(Constants.INTENT_FROMREPORTS, true);
                i.putExtras(b);
                startActivity(i);
            } else {
                if (CheckNetwork.checkNet(getActivity())) {
                    ImprovementPlanApi.getImprovementDetails(getActivity(), String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), selectedDP.getId(), skillData.getId(), this);
                } else {
                    Logger.logD(TAG, " Check Internet and try again ");
                }

            }
        } catch (Exception e) {
            Logger.logE(TAG, "toImprovemntActivity", e);
        }

    }

    @Override
    public void getImprovementPlanDetails(ImprovementPlanModel improvementPlanModel) {
        try {
            if (improvementPlanModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                //ToastUtils.displayToast( improvementPlanModel.getStatus() , getActivity());

                Logger.logD(TAG, "Get Impl inside Main IF Try");
                if (!improvementPlanModel.getData().getLogs().isEmpty()) {
                    Logger.logD(TAG, "Get Impl inside if" + improvementPlanModel.getData().getLogs().size());
                    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                    databaseHelper.delFromIPTable(childBean.getChild().getId(), improvementPlanModel.getData().getId());
                    databaseHelper.insImprovementPlanTable(improvementPlanModel.getData(), String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), selectedDP, selectedSkill);
                    for (int i = 0; i < improvementPlanModel.getData().getLogs().size(); i++) {
                        Logger.logD(TAG, "Get Impl inside For--" + improvementPlanModel.getData().getLogs());
                        DayLoggedModel model = new DayLoggedModel();
                        model.setChildId(childBean.getChild().getId());
                        model.setDpId(selectedDP.getId());
                        model.setUserId(String.valueOf(prefs.getInt(Constants._ID, -1)));
                        model.setCurrentDate(improvementPlanModel.getData().getLogs().get(i).getLogDate());
                        model.setSkillId(selectedSkill.getId());
                        model.setAns("No");
                        model.setiId(improvementPlanModel.getData().getId());
                        model.setTypeRem(improvementPlanModel.getData().getReminder());
                        databaseHelper.insIPLogTable(model);

                    }

                    Intent i = new Intent(getActivity(), ImprovementActivatedActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSkillsRespModel);
                    b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
                    b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
                    b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
                    b.putBoolean(Constants.INTENT_FROMREPORTS, true);
                    i.putExtras(b);
                    startActivity(i);
                } else {
                    Logger.logD(TAG, "Get Impl inside else");
                    Intent i = new Intent(getActivity(), ImprovementPlanActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSkillsRespModel);
                    b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
                    b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
                    b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
                    b.putParcelable(Constants.BUNDLE_IPMODEL, improvementPlanModel);
                    i.putExtras(b);
                    getActivity().startActivity(i);
                }

            } else {
                ToastUtils.displayToast(improvementPlanModel.getStatus(), getActivity());
            }

            //Logger.logD("TAG", "" + improvementPlanModel.getData().getTaskSteps());

        } catch (Exception e) {
            Logger.logE(TAG, "ImpPlanAPIResp", e);
        }
    }

}
