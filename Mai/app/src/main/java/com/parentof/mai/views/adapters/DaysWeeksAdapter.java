package com.parentof.mai.views.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.utils.Logger;

/**
 * Created by sandeep HR on 01/03/17.
 */

public class DaysWeeksAdapter extends RecyclerView.Adapter<DaysWeeksAdapter.ChildrenViewHolder> {

    private static final String TAG = "chldrnAdaptr ";
    Context context;
    int noOfDays;
    String reminder = "";


    public DaysWeeksAdapter(Context context, int duration, String reminder) {
        this.context = context;
        noOfDays=duration;
        this.reminder = reminder;
    }

    @Override
    public ChildrenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.daysweeksrvrow, parent, false);
        return new ChildrenViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ChildrenViewHolder holder, final int position) {

        try {
           // holder.daysweeks.setBackground(context.getResources().getDrawable(R.drawable.dayselected100));
            if (position == 0) {
                holder.daysweeks.setBackground(context.getResources().getDrawable(R.drawable.dayselected100));
            } else {
                holder.daysweeks.setBackground(context.getResources().getDrawable(R.drawable.daynormal100));
                holder.daysweeks.setTextColor(context.getResources().getColor(R.color.black));

            }

            holder.daysweeks.setText(getTextLabel(reminder,position));
        } catch (Exception e) {
            Logger.logE(TAG, "onBndvwHldr 1 :", e);
        }


        holder.daysweeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                } catch (Exception e) {
                    Logger.logE(TAG, "onBndvwHldr :", e);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return noOfDays;
    }

    public class ChildrenViewHolder extends RecyclerView.ViewHolder {

        TextView daysweeks;

        public ChildrenViewHolder(View itemView) {
            super(itemView);
            daysweeks = (TextView) itemView.findViewById(R.id.dayweekTV);

        }
    }

    public String getTextLabel(String reminder, int position){
        String labelValue = "";
         switch(reminder.toLowerCase()){
             case "daily":
                 labelValue = "Day".concat(" " + (position+1));
                 break;
             case "weekly":
                 labelValue = "Week".concat(" " + (position+1));
                 break;
             case "fortnight":
                 labelValue = "FortNight".concat(" " + (position+1));
                 break;
             case "monthly":
                 labelValue = "Month".concat(" " + (position+1));
                 break;
         }
        return labelValue;
    }


}

