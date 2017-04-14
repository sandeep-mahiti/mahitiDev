package com.parentof.mai.views.activity.ipTabs;

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

import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.ImprovementPlanModel.HomeTaskBean;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.views.adapters.ipsAdapters.CompletedPlanAdapter;
import com.parentof.mai.views.adapters.ipsAdapters.CurrentPlanAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link CompleteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompleteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    static Child childBean;
    View view;
    SharedPreferences preferences;
    DatabaseHelper databaseHelper;
    List<HomeTaskBean> homeTaskBeanList;

    public CompleteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment PreviousFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompleteFragment newInstance(Child childBean,  ArrayList<HomeTaskBean> homeTaskBeanList) {
        CompleteFragment fragment = new CompleteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, childBean);
        args.putParcelableArrayList(ARG_PARAM2, homeTaskBeanList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childBean = getArguments().getParcelable(ARG_PARAM1);
            homeTaskBeanList=getArguments().getParcelableArrayList(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Logger.logD(Constants.PROJECT, "CompleteFragment");
        view= inflater.inflate(R.layout.fragment_previous, container, false);
        preferences = getActivity().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_completedPlans);
        showCompletedPlans(view);
        // if(databaseHelper.anyIPForThisSkill()) {
            /*if (CheckNetwork.checkNet(getActivity()))
                AllActiveApiCall.allActiveInterventionApi(getActivity(), String.valueOf(preferences.getInt(Constants._ID, -1)), childBean.getChild().getId(), this);
            else
                ToastUtils.displayToast(Constants.NO_INTERNET, getActivity());*/
        //}
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showCompletedPlans(view);
    }

    void showCompletedPlans(View view) {
        try {
                if (homeTaskBeanList==null || homeTaskBeanList.isEmpty()) {
                    view.findViewById(R.id.tv_noCurrrentTasks).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.tv_noCurrrentTasks).setVisibility(View.GONE);
                    CompletedPlanAdapter completedPlanAdapter = new CompletedPlanAdapter(getActivity(), homeTaskBeanList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(completedPlanAdapter);
                }

        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, "Exce", e);
        }
    }

}
