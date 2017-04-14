package com.parentof.mai.views.fragments.reports_chathistory_fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.UpdateChatAnsCallback;
import com.parentof.mai.activityinterfaces.UpdateSkillQAnsCallback;
import com.parentof.mai.api.apicalls.UpdateSkillAnswerAPI;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.UpdateSkillAnswerRespModel;

import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.adapters.reports_history_adapters.Report_ChatHistoryAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChildHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChildHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildHistoryFragment extends Fragment implements UpdateChatAnsCallback, UpdateSkillQAnsCallback {
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
    TextView qstnsPercentTV;


    GetSkillsRespModel getSkillsRespModel;
    AllDP selectedDP;

    //private ActivateSkillRespModel activateSkillRespModel;
    GetSkillQuestionsRespModel skillQuestionsRespModel;
    private Child childBean;
    SkillData selectedSkill;//Datum selectedSkillObj;
    private String dpId;
    private SharedPreferences prefs;
    ProgressBar progressBar;


    private OnFragmentInteractionListener mListener;
    private String TAG="chldHistryFrag";
    private String qstndId;
    private String ans;
    DatabaseHelper dbHelper;
    private List<Question> qstnListfromDB;
    private List<Question> qstnAnsListfromDB;


    public ChildHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildHistoryFragment newInstance(String param1, String param2) {
        ChildHistoryFragment fragment = new ChildHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        dbHelper=new DatabaseHelper(getActivity());
        getValuesFromBundle();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_child_history, container, false);
        int percent;
        double val2;
        qstnsPercentTV= (TextView) view.findViewById(R.id.questions_percentageTV);
        progressBar= (ProgressBar) view.findViewById(R.id.questions_progressbar);

        recyclerView = (RecyclerView) view.findViewById(R.id.history_rv);


        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Report_ChatHistoryAdapter(getActivity(), childBean, selectedDP, (ArrayList<Question>) qstnAnsListfromDB, this);
        recyclerView.setAdapter(adapter);


        if(qstnAnsListfromDB.size()>0 &&  qstnAnsListfromDB.size() == qstnListfromDB.size()) {
            percent=0;
            val2=100;
        }else{
            double val=((double)qstnAnsListfromDB.size() )/qstnListfromDB.size();
            val2=val*100;
            percent= (int) (100-Math.ceil(val2));
        }
        progressBar.setProgress((int) val2);
        qstnsPercentTV.setText(String.valueOf(percent).concat("%"));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void getValuesFromBundle() {
        try{
            if(getActivity().getIntent().getExtras()!=null) {
                Bundle b = getActivity().getIntent().getExtras();

                skillQuestionsRespModel = b.getParcelable(Constants.BUNDLE_QUESTOBJ);
                getSkillsRespModel = b.getParcelable(Constants.BUNDLE_SKILLSOBJ);
                selectedSkill=b.getParcelable(Constants.BUNDLE_SELSKILLOBJ);
                childBean=b.getParcelable(Constants.BUNDLE_CHILDOBJ);
                selectedDP=b.getParcelable(Constants.BUNDLE_SELDPOBJ);
                if (selectedDP != null) {
                    dpId=selectedDP.getId();
                }else{
                    ToastUtils.displayToast("Please comeback later to continue",getActivity());
                }
                qstnListfromDB=dbHelper.selQAfrmSkillQATable( childBean.getChild().getId(), dpId,selectedSkill.getId(), 3 );
                qstnAnsListfromDB=dbHelper.selQAfrmSkillQATable( childBean.getChild().getId(), dpId,selectedSkill.getId(), 2 );
            }
        }catch(Exception e){
            Logger.logE(TAG, "valsFrmBndl", e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getValuesFromBundle();
    }

    @Override
    public void upChatAnsAgain(String skillId,String qstndId, String ans) {
        try{
            this.qstndId=qstndId;
            this.ans=ans;
            if (CheckNetwork.checkNet(getActivity())) {
                UpdateSkillAnswerAPI.updateAnswer(getActivity(), String.valueOf(prefs.getInt(Constants._ID,-1)), childBean.getChild().getId(), dpId, skillId, qstndId, ans, this );
            }else{
                Logger.logD(TAG, " Check Internet and try again ");
            }
        }catch(Exception e){
            Logger.logE(TAG, "updateAns ",e);
        }

    }

    @Override
    public void skillAnsResp(UpdateSkillAnswerRespModel skillAnswerRespModel) {
        try {
            if (skillAnswerRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {

                MyProgress.show(getActivity(),"","");
                String ans="";
                if(this.ans.equalsIgnoreCase("No%20-%20Not%20Applicable"))
                    ans="Not Applicable";
                else if(this.ans.equalsIgnoreCase("To%20be%20Observed"))
                    ans="TO be Observed";
                else
                    ans=this.ans;

                dbHelper.updateAnsToSkilQstnTab( dpId, selectedSkill.getId(), qstndId, ans);
                MyProgress.CancelDialog();

            } else {
                ToastUtils.displayToast("Couldn't update answer, Please try again!", getActivity());
            }
        }catch (Exception e){
            Logger.logE(TAG, " skillAnsResp : ", e);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
