package com.parentof.mai.views.adapters.ipsAdapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.DaysBean;
import com.parentof.mai.model.ImprovementPlanModel.HomeTaskBean;
import com.parentof.mai.model.dayLoggedModel.DayLoggedModel;
import com.parentof.mai.utils.TaskUtils;
import com.parentof.mai.views.adapters.DaysAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by sandeep HR on 12/04/17.
 */

public class CompletedPlanAdapter extends RecyclerView.Adapter<CompletedPlanAdapter.CompletedAdapterHolder> {
    private static final String TAG = "DaysAdapter ";
    Context context;
    int duration;
    List<HomeTaskBean> homeTaskBeanList;

    public CompletedPlanAdapter(Context context, List<HomeTaskBean> homeTaskBeanList) {
        this.context = context;
        this.homeTaskBeanList = homeTaskBeanList;
    }


    @Override
    public CompletedPlanAdapter.CompletedAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pause_play_row_layout, parent, false);
        return new CompletedPlanAdapter.CompletedAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final CompletedPlanAdapter.CompletedAdapterHolder holder, final int position) {

        if (homeTaskBeanList.get(position).getData().getTaskName() != null)
            holder.taskName.setText(homeTaskBeanList.get(position).getData().getTaskName());
        if (homeTaskBeanList.get(position).getDpName() != null)
            holder.dpName.setText(homeTaskBeanList.get(position).getDpName());
        if (homeTaskBeanList.get(position).getData().getTaskObjectives() != null)
            holder.dpDesc.setText(homeTaskBeanList.get(position).getData().getTaskObjectives());
        try {
            holder.playPauseIV.setVisibility(View.GONE);
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            TaskUtils taskUtils = new TaskUtils();
            List<DayLoggedModel> dayLoggedModelList = databaseHelper.getIpLogTable(homeTaskBeanList.get(position).getChildId(), homeTaskBeanList.get(position).getData().getId());
            List<DaysBean> daysList = taskUtils.loopDaysList(homeTaskBeanList);
            Calendar calendar = Calendar.getInstance();
            Date todayDate = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
            int currentPosition = taskUtils.getCurrentItemFromList(daysList, dateFormat, 0, todayDate);
            daysList = taskUtils.setAnsweredData(currentPosition, daysList, dayLoggedModelList);
            if (daysList.size() > currentPosition)
                daysList.get(currentPosition).setCurrent(true);
            DaysAdapter daysAdapter = new DaysAdapter(context, homeTaskBeanList.get(position).getData().getDuration(), daysList,"current");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.daysRcv.setLayoutManager(linearLayoutManager);
            holder.daysRcv.setAdapter(daysAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return homeTaskBeanList.size();
    }

    public class CompletedAdapterHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView dpName;
        TextView dpDesc;
        RecyclerView daysRcv;
        ImageView playPauseIV;



        public CompletedAdapterHolder(View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            dpName = (TextView) itemView.findViewById(R.id.dpName);
            dpDesc = (TextView) itemView.findViewById(R.id.dpDesc);
            daysRcv = (RecyclerView) itemView.findViewById(R.id.rcv_pauseDays);
            playPauseIV= (ImageView) itemView.findViewById(R.id.pausePlayIV);

        }
    }
}
