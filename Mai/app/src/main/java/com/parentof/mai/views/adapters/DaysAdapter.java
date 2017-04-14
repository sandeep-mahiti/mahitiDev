package com.parentof.mai.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.model.DaysBean;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;

import java.util.List;

/**
 * Created by mahiti on 27/2/17.
 */
public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysAdapterHolder> {
    private static final String TAG = "DaysAdapter ";
    Context context;
    int duration;
    List<DaysBean> daysBeans;
    String whichScreen;

    public DaysAdapter(Context context, int duration, List<DaysBean> daysBeans, String whichScreen) {
        this.context = context;
        this.duration = duration;
        this.daysBeans = daysBeans;
        this.whichScreen = whichScreen;
    }


    @Override
    public DaysAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.days_row_layout, parent, false);
        return new DaysAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final DaysAdapterHolder holder, final int position) {


        holder.tvDays.setText(daysBeans.get(position).getLabel());
        Logger.logD(Constants.PROJECT, "getLabel---->" + daysBeans.get(position).getLabel());

        switch (whichScreen) {
            case "task":
                Logger.logD(Constants.PROJECT, "Ans-->" + daysBeans.get(position).getAnswer());
                if ("Yes".equalsIgnoreCase(daysBeans.get(position).getAnswer())) {
                    holder.lockImg.setVisibility(View.INVISIBLE);
                    holder.img_arrow.setVisibility(View.GONE);
                    holder.imgYes.setVisibility(View.VISIBLE);
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.rounddayblue100));
                } else if ("No".equalsIgnoreCase(daysBeans.get(position).getAnswer())) {
                    holder.lockImg.setVisibility(View.INVISIBLE);
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.rounddayblue100));
                    holder.imgYes.setVisibility(View.VISIBLE);
                    holder.imgYes.setImageResource(R.drawable.wrong100);
                    holder.img_arrow.setVisibility(View.GONE);
                } else if (daysBeans.get(position).getCurrent() /*&& "task".equalsIgnoreCase(whichScreen) */ && (!"Yes".equalsIgnoreCase(daysBeans.get(position).getAnswer()) || !"No".equalsIgnoreCase(daysBeans.get(position).getAnswer()))) {
                    Logger.logD(Constants.PROJECT, "Current--" + daysBeans.get(position).getCurrent());
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.rounddayselected100));
                    holder.lockImg.setVisibility(View.INVISIBLE);
                    holder.img_arrow.setVisibility(View.VISIBLE);
                    holder.rightView.setBackgroundColor(CommonClass.getColor(context, R.color.day_deselect));
                    //holder.imgYes.setVisibility(View.GONE);
                } else {
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.roundgray100));
                    holder.lockImg.setVisibility(View.VISIBLE);
                    holder.rightView.setBackgroundColor(CommonClass.getColor(context, R.color.day_deselect));
                    //holder.imgYes.setVisibility(View.GONE);
                }
                break;
            case "ip":
                holder.img_arrow.setVisibility(View.GONE);
                holder.lockImg.setVisibility(View.INVISIBLE);
                if ("Yes".equalsIgnoreCase(daysBeans.get(position).getAnswer())) {
                    holder.imgYes.setVisibility(View.VISIBLE);
                    holder.imgYes.setImageResource(R.drawable.right100);
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.rounddayblue100));
                    holder.rightView.setBackgroundColor(CommonClass.getColor(context, R.color.day_select));
                } else if ("No".equalsIgnoreCase(daysBeans.get(position).getAnswer())) {
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.rounddayblue100));
                    holder.imgYes.setVisibility(View.VISIBLE);
                    holder.imgYes.setImageResource(R.drawable.wrong100);
                } else if (daysBeans.get(position).getCurrent() /*&& "task".equalsIgnoreCase(whichScreen) */ && (!"Yes".equalsIgnoreCase(daysBeans.get(position).getAnswer()) || !"No".equalsIgnoreCase(daysBeans.get(position).getAnswer()))) {
                    Logger.logD(Constants.PROJECT, "Current--" + daysBeans.get(position).getCurrent());
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.rounddayselected100));
                    holder.imgYes.setVisibility(View.GONE);
                    holder.imgYes.setImageResource(R.drawable.question100);
                    holder.rightView.setBackgroundColor(CommonClass.getColor(context, R.color.day_deselect));
                } else {
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.roundgray100));
                    holder.lockImg.setVisibility(View.VISIBLE);
                    holder.rightView.setBackgroundColor(CommonClass.getColor(context, R.color.day_deselect));
                    holder.imgYes.setVisibility(View.GONE);
                }
                break;
            case "current":
                if (daysBeans.get(position).getCurrent() && (!"Yes".equalsIgnoreCase(daysBeans.get(position).getAnswer()) || !"No".equalsIgnoreCase(daysBeans.get(position).getAnswer()))) {
                    Logger.logD(Constants.PROJECT, "Current--" + daysBeans.get(position).getCurrent());
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.rounddayselected100));
                    holder.lockImg.setVisibility(View.GONE);
                    holder.img_arrow.setVisibility(View.GONE);
                    holder.imgYes.setVisibility(View.GONE);
                    holder.rightView.setBackgroundColor(CommonClass.getColor(context, R.color.day_deselect));
                } else if ("Yes".equalsIgnoreCase(daysBeans.get(position).getAnswer())) {
                    holder.imgYes.setVisibility(View.VISIBLE);
                    holder.imgYes.setImageResource(R.drawable.right100);
                    holder.lockImg.setVisibility(View.GONE);
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.rounddayblue100));
                    holder.rightView.setBackgroundColor(CommonClass.getColor(context, R.color.day_select));
                } else if ("No".equalsIgnoreCase(daysBeans.get(position).getAnswer())) {
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.rounddayblue100));
                    holder.imgYes.setVisibility(View.VISIBLE);
                    holder.imgYes.setImageResource(R.drawable.wrong100);
                    holder.lockImg.setVisibility(View.GONE);
                }
                else {
                    holder.tvDays.setBackground(context.getResources().getDrawable(R.drawable.roundgray100));
                    holder.lockImg.setVisibility(View.VISIBLE);
                    holder.rightView.setBackgroundColor(CommonClass.getColor(context, R.color.day_deselect));
                }
                break;
            default:
                break;
        }


        Logger.logD(TAG, " Answer --- >  " + daysBeans.get(position).getAnswer());
        Logger.logD(TAG, " Answer --- >  " + daysBeans.get(position).getDate() + " -- " + daysBeans.get(position).getAnswer());


        if (position == getItemCount() - 1) {
            holder.rightView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return daysBeans.size();
    }

    public class DaysAdapterHolder extends RecyclerView.ViewHolder {
        TextView tvDays;
        View rightView;
        ImageView lockImg;
        ImageView imgYes;
        ImageView img_arrow;

        public DaysAdapterHolder(View itemView) {
            super(itemView);
            tvDays = (TextView) itemView.findViewById(R.id.tv_days);
            rightView = (View) itemView.findViewById(R.id.rightView);

            lockImg = (ImageView) itemView.findViewById(R.id.img_lock);
            imgYes = (ImageView) itemView.findViewById(R.id.img_yes);
            img_arrow = (ImageView) itemView.findViewById(R.id.img_arrow);

        }
    }
}
