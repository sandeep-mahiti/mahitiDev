package com.parentof.mai.views.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.model.ImprovementPlanModel.Data;
import com.parentof.mai.utils.Logger;

/**
 * Created by sandeep HR on 01/03/17.
 */

public class IPActDaysAdapter extends RecyclerView.Adapter<IPActDaysAdapter.DaysViewHolder> {
    private static final String TAG = "chldrnAdaptr ";
    Context context;
    Data noOfDays;


    public IPActDaysAdapter(Context context, Data duration) {
        this.context = context;
        noOfDays=duration;
    }

    @Override
    public IPActDaysAdapter.DaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ip_days_row_layout, parent, false);
        return new IPActDaysAdapter.DaysViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final IPActDaysAdapter.DaysViewHolder holder, final int position) {

        try {
            // holder.daysweeks.setBackground(context.getResources().getDrawable(R.drawable.dayselected100));
            if (position == 0) {
                holder.days.setBackground(context.getResources().getDrawable(R.drawable.rounddayblue100));
                holder.connectbarBlue.setVisibility(View.VISIBLE);
                holder.connectbarGrey.setVisibility(View.GONE);
                holder.locked.setVisibility(View.INVISIBLE);
                holder.right.setVisibility(View.VISIBLE);
                holder.wrong.setVisibility(View.GONE);
                holder.questionMark.setVisibility(View.GONE);

            } else if (position == 1){
                holder.days.setBackground(context.getResources().getDrawable(R.drawable.rounddayblue100));
                holder.connectbarBlue.setVisibility(View.VISIBLE);
                holder.connectbarGrey.setVisibility(View.GONE);
                holder.locked.setVisibility(View.VISIBLE);
                holder.right.setVisibility(View.GONE);
                holder.wrong.setVisibility(View.VISIBLE);
                holder.questionMark.setVisibility(View.GONE);

            }else {
                holder.days.setBackground(context.getResources().getDrawable(R.drawable.roundgray100));
                holder.connectbarBlue.setVisibility(View.GONE);
                holder.connectbarGrey.setVisibility(View.VISIBLE);
                holder.locked.setVisibility(View.VISIBLE);
                holder.right.setVisibility(View.GONE);
                holder.wrong.setVisibility(View.GONE);
                holder.questionMark.setVisibility(View.VISIBLE);
            }
            if(position==noOfDays.getDuration()-1){
                holder.connectbarBlue.setVisibility(View.GONE);
                holder.connectbarGrey.setVisibility(View.GONE);
            }

            holder.days.setText("Day".concat(" " + (position+1)));
        } catch (Exception e) {
            Logger.logE(TAG, "onBndvwHldr 1 :", e);
        }


        holder.days.setOnClickListener(new View.OnClickListener() {
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
        return noOfDays.getDuration();
    }

    public class DaysViewHolder extends RecyclerView.ViewHolder {

        TextView days;
        View connectbarBlue;
        View connectbarGrey;
        ImageView locked;

        ImageView right;
        ImageView wrong;
        ImageView questionMark;


        public DaysViewHolder(View itemView) {
            super(itemView);
            days = (TextView) itemView.findViewById(R.id.tv_days);
            connectbarBlue= itemView.findViewById(R.id.connectorBlueView);
            connectbarGrey= itemView.findViewById(R.id.connectorGreyView);
            locked= (ImageView) itemView.findViewById(R.id.lockImg);
            right= (ImageView) itemView.findViewById(R.id.rightImg);
            wrong= (ImageView) itemView.findViewById(R.id.wrongImg);
            questionMark= (ImageView) itemView.findViewById(R.id.questionsImg);

        }
    }


}



