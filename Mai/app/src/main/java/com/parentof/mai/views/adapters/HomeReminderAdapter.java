package com.parentof.mai.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.SelectedDpTOActivtyCallback;
import com.parentof.mai.model.reminders.ReminderModel;
import com.parentof.mai.utils.CommonClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahiti on 30/3/17.
 */
public class HomeReminderAdapter extends RecyclerView.Adapter<HomeReminderAdapter.ViewHolder> {

    Context context;
    List<ReminderModel> reminderModelList = new ArrayList<>();
    SelectedDpTOActivtyCallback addReminderCallBack;

    //Constructor
    public HomeReminderAdapter(Context context, List<ReminderModel> reminderModelList, SelectedDpTOActivtyCallback addReminderCallBack) {
        this.reminderModelList = reminderModelList;
        this.context = context;
        this.addReminderCallBack = addReminderCallBack;
    }

    //IN this method we are tracking the self message
    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_home_sub_rows, parent, false);
        //returing the view
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nextReminderTime.setText(CommonClass.convertingOneDateToOther(reminderModelList.get(position).getReminderDate()));
        holder.nextReminderName.setText(reminderModelList.get(position).getDescription());
        if (reminderModelList.size() - 1 == position) {
            holder.addReminder.setVisibility(View.VISIBLE);
        } else {
            holder.addReminder.setVisibility(View.GONE);
        }
        holder.addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminderCallBack.addReminder(position);
            }
        });
        holder.nextDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminderCallBack.responseDone(reminderModelList.get(position).getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return reminderModelList.size();
    }

    //Initializing views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nextReminderTime;
        TextView nextReminderName;
        RelativeLayout addReminder;
        ImageView nextDone;

        public ViewHolder(View itemView) {
            super(itemView);
            nextReminderTime = (TextView) itemView.findViewById(R.id.next_reminder_time);
            nextReminderName = (TextView) itemView.findViewById(R.id.next_reminder);
            addReminder = (RelativeLayout) itemView.findViewById(R.id.addReminder);
            nextDone = (ImageView) itemView.findViewById(R.id.next_reminder_right_done);
        }
    }
}