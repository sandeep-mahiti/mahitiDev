package com.parentof.mai.views.adapters.dpsadapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.SelectedDpTOActivtyCallback;
import com.parentof.mai.model.decisionpointsmodel.ActiveDP;
import com.parentof.mai.model.decisionpointsmodel.AllDP;

import com.parentof.mai.model.decisionpointsmodel.CompletedDP;
import com.parentof.mai.model.decisionpointsmodel.DPRespModel;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.fragments.dpfrgamant.ItemFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class VerticalRVAdapter extends RecyclerView.Adapter<VerticalRVAdapter.ViewHolder> {

    private static final String TAG = "vRVAdapt";
    private List<AllDP> newDPsList;


    DPRespModel dpListModel=new DPRespModel();

    Context context;
    private int counter=0;
    HorizontalRVAdapter itemListDataAdapter;
    SelectedDpTOActivtyCallback selectedDpTOActivtyCallback;
    HashMap<Integer,Integer> hashMapIndex = new HashMap<>();


    public  VerticalRVAdapter(Context context, List<AllDP> newDPsList, DPRespModel dpListModel, SelectedDpTOActivtyCallback selectedDpTOActivtyCallback) {
        if(dpListModel!=null) {
            this.dpListModel = dpListModel;
        }
        if(newDPsList.size()>0){
            this.newDPsList=newDPsList;
        }
        this.selectedDpTOActivtyCallback=selectedDpTOActivtyCallback;
        this.context=context;
        //this.counter=counter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {

        try {
            Logger.logD(TAG,"position for data - "+hashMapIndex.get(i+1));
                switch (hashMapIndex.get(i+1)){
                    case 1:
                        holder.row_heading.setText(Constants.NEW_DPS);
                        setDPList(holder, 1);
                        break;
                    case 2:
                        holder.row_heading.setText(Constants.SKILLED_DPS);
                        setDPList(holder, 2);
                        break;
                    case 3:
                        holder.row_heading.setText(Constants.PENDING_DPS);
                        setDPList(holder, 3);
                        break;
                    case 4:
                        holder.row_heading.setText(Constants.COMPLETED_DPS);
                        setDPList(holder, 4);
                        break;

                }
        } catch (Exception e) {
            Logger.logE(TAG, "fillMainList ", e);
        }






        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setDPList(ViewHolder holder, int index) {
        try {

            List<AllDP> allDPList=convertToAllDPList(dpListModel,index);
            if(allDPList.isEmpty())
                return;
            itemListDataAdapter = new HorizontalRVAdapter(context, allDPList, selectedDpTOActivtyCallback);
            holder.horizontalList.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.horizontalList.setLayoutManager(linearLayoutManager);
            holder.horizontalList.setAdapter(itemListDataAdapter);
            itemListDataAdapter.setRowIndex(index);
        }catch(Exception e){
            Logger.logE(TAG, "setDPList ", e);
        }


    }

    private List<AllDP> convertToAllDPList(DPRespModel dpListModel,int position) {
        List<AllDP> allDPList=new ArrayList<>();
        try {

           /* if (!newDPsList.isEmpty() && newDPsList.size() > 0) {

                allDPList.addAll(newDPsList);
            }*/
            switch(position){

                case 1:
                    if (!dpListModel.getData().getAllDP().isEmpty() ) {
                        allDPList.addAll(newDPsList);
                    }
                    break;
                case 2:
                    if (!dpListModel.getData().getAllDP().isEmpty() ) {
                        allDPList.addAll(dpListModel.getData().getAllDP());
                    }
                    break;
                case 3:
                    if (!dpListModel.getData().getActiveDP().isEmpty() ) {
                        for (ActiveDP acti : dpListModel.getData().getActiveDP()) {
                            AllDP alldp = new AllDP();
                            alldp.setId(acti.getId());
                            alldp.setName(acti.getName());
                            alldp.setActive(acti.getActive());
                            alldp.setImage(acti.getImage());
                            alldp.setCompPercent(acti.getCompPercent());
                            alldp.setCreatedDate(acti.getCreatedDate());
                            allDPList.add(alldp);
                        }
                    }
                    break;
                case 4:
                    if (!dpListModel.getData().getCompletedDP().isEmpty()) {
                        for (CompletedDP acti : dpListModel.getData().getCompletedDP()) {
                            AllDP alldp = new AllDP();
                            alldp.setId(acti.getId());
                            alldp.setName(acti.getName());
                            alldp.setActive(acti.getActive());
                            alldp.setImage(acti.getImage());
                            alldp.setCompPercent(acti.getCompPercent());
                            alldp.setCreatedDate(acti.getCreatedDate());
                            allDPList.add(alldp);
                        }
                    }
            }
        }catch(Exception e){
            Logger.logE(TAG, "convertToAllDPList --> ", e);
        }
        return allDPList;
    }


    @Override
    public int getItemCount() {
        int rows=0;

        if(newDPsList!=null && !newDPsList.isEmpty()){
            rows=rows+1;
            hashMapIndex.put(rows,1);
        }
        if(dpListModel!=null && !dpListModel.getData().getAllDP().isEmpty()){
            rows=rows+1;
            hashMapIndex.put(rows,2);
        }
        if(dpListModel!=null && !dpListModel.getData().getActiveDP().isEmpty()) {
            rows = rows + 1;
            hashMapIndex.put(rows,3);
        }
        if(dpListModel!=null && !dpListModel.getData().getCompletedDP().isEmpty()) {
            rows = rows + 1;
            hashMapIndex.put(rows,4);
        }
        return rows;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
         public TextView row_heading;
        // public final TextView mContentView;
        // public DummyItem mItem;
        private HorizontalRVAdapter horizontalAdapter;
        private RecyclerView horizontalList;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            row_heading = (TextView) view.findViewById(R.id.row_headings);
            //mContentView = (TextView) view.findViewById(R.id.content);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.horizontal_list);

            //horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        }

        @Override
        public String toString() {
            return super.toString() + " hello" ;//+ mContentView.getText() + "'";
        }
    }
}
