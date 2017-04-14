package com.parentof.mai.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.CloseReminderCallBack;
import com.parentof.mai.model.reminders.ReminderModel;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;

import java.util.List;

/**
 * Created by mahiti on 21/3/17.
 */
public class ReminderAdapter extends BaseAdapter {

    Context context;
    List<ReminderModel> reminderModelList;
    CloseReminderCallBack closeReminderCallBack;

    public ReminderAdapter(Context context, List<ReminderModel> reminderModelList, CloseReminderCallBack closeReminderCallBack) {
        this.context = context;
        this.reminderModelList = reminderModelList;
        this.closeReminderCallBack = closeReminderCallBack;

    }

    @Override
    public int getCount() {
        return reminderModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return reminderModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder reminderViewHolder;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.snippet_reminder_row, parent, false);
        reminderViewHolder = new ViewHolder();
        reminderViewHolder.tvReminder = (TextView) convertView.findViewById(R.id.tv_reminder);
        reminderViewHolder.tvReminderTime = (TextView) convertView.findViewById(R.id.tv_reminder_time);
        reminderViewHolder.reminderClose = (ImageView) convertView.findViewById(R.id.reminder_close);
        // reminderViewHolder.tvReminderAddress = (TextView) convertView.findViewById(R.id.tv_reminder_address);

        Logger.logD(Constants.PROJECT, "Desc---" + reminderModelList.get(position).getDescription());

        if (reminderModelList.get(position).getDescription() != null && !reminderModelList.get(position).getDescription().isEmpty())
            reminderViewHolder.tvReminder.setText(reminderModelList.get(position).getDescription());

        if (reminderModelList.get(position).getReminderDate() != null && !reminderModelList.get(position).getReminderDate().isEmpty())
            reminderViewHolder.tvReminderTime.setText(CommonClass.convertingOneDateToOther(reminderModelList.get(position).getReminderDate()));

/*
        if (reminderModelList.get(position).getLocation() != null && !reminderModelList.get(position).getLocation().isEmpty())
            reminderViewHolder.tvReminderAddress.setText(reminderModelList.get(position).getLocation());

     */
        reminderViewHolder.reminderClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeReminderCallBack.reminderCloseResponse(reminderModelList.get(position).getId());
            }
        });
        convertView.setTag(reminderViewHolder);

        return convertView;
    }

    public class ViewHolder {
        TextView tvReminder;
        TextView tvReminderTime;
        ImageView reminderClose;
        //   TextView tvReminderAddress;
    }
}
