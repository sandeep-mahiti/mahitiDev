package com.parentof.mai.views.adapters.statisticsadapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.StatisticsToViewReportCB;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.statisticsmodel.Data;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ToastUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by sandeep HR on 29/01/17.
 */

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ChildrenViewHolder> {

    private static final String TAG = "chldrnAdaptr ";
    private final SharedPreferences prefs
            ;
    Context context;

    List<Data> data;

    int percent;
    double val2;
    Child childBean;
    StatisticsToViewReportCB statisticsToViewReportCB;


    public StatisticsAdapter(Context context, List<Data> data, Child childBean,  StatisticsToViewReportCB statisticsToViewReportCB) {
        this.context = context;
        this.data = data;
        this.childBean=childBean;
        this.statisticsToViewReportCB=statisticsToViewReportCB;
        //this.childrenList = childrenList;
        prefs = context.getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
    }

    @Override
    public ChildrenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.statistics_row_layout, parent, false);
        return new ChildrenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChildrenViewHolder holder, final int position) {
        //holder.civ.setImageResource(R.drawable.kid_image);
        try {

           /* holder.daysPBar.setProgress(0);
            holder.achievedPBar.setProgress(0);
            holder.questionsPBar.setProgress(0);*/

            holder.statChildNameTV.setText(childBean.getChild().getFirstName());
            holder.statTopicTV.setText(data.get(position).getDpName());
            holder.cImage.setVisibility(View.VISIBLE);

            boolean imgFlag = CommonClass.getImageFromDirectory(holder.cImage, childBean.getChild().getId());
            if (!imgFlag) {
                holder.cImage.setImageBitmap(CommonClass.StringToBitMap(prefs.getString("child_image", "")));
            }
            if(data!=null && data.size()>0 ){
                if(data.get(position).getDpImage()!=null)
                    Picasso.with(context).load(data.get(position).getDpImage()).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            holder.bannerImageLLayout.setBackground(new BitmapDrawable(bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
                int percent=calPercent(data.get(position).getTotalIndicator(), data.get(position).getAchievedIndicator());
                holder.daysPBar.setProgress(percent);
                holder.statDayPercentTV.setText(String.valueOf(percent).concat("%"));
                percent=calPercent(data.get(position).getCtTotal(), data.get(position).getCtAchieved());
                holder.achievedPBar.setProgress(percent);
                holder.statAchievedPercentTV.setText(String.valueOf(percent).concat("%"));
                percent=calPercent(data.get(position).getTotalQuestion(), data.get(position).getAnsweredQuestion());
                holder.questionsPBar.setProgress(percent);
                holder.statQuestionPrecentTV.setText(String.valueOf(percent).concat("%"));
            }else{
                ToastUtils.displayToast("Statistics data Not available.",context);
            }

            holder.cardRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    statisticsToViewReportCB.showReport(data.get(position).getDpId());
                }
            });
        }catch (Exception e){
            Logger.logE(TAG, "onBndvwHldr :" ,e);
        }

    }

    private int calPercent(int total , int achieved) {
        try{
            if( total>0 &&  total == achieved) {
                percent=100;
                val2=100;
            }else{
                double val=((double)achieved )/total;
                val2=val*100;
                percent= (int) (Math.ceil(val2));
            }
        }catch (Exception e){
            Logger.logE(TAG, "calPercent :" ,e);
        }
        return percent;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ChildrenViewHolder extends RecyclerView.ViewHolder {

        CardView eachCV;
        ImageView cImage;
        TextView statChildNameTV;
        TextView statTopicTV;
        TextView statDayPercentTV;
        TextView statAchievedPercentTV;
        TextView statQuestionPrecentTV;
        RelativeLayout cardRL;

        ProgressBar daysPBar;
        ProgressBar achievedPBar;
        ProgressBar questionsPBar;
        LinearLayout bannerImageLLayout;
       // TextView stat_childName;
        //TextView stat_childName;
       // TextView stat_childName;


        public ChildrenViewHolder(View itemView) {
            super(itemView);

            eachCV= (CardView) itemView.findViewById(R.id.eachCardView);
            cardRL= (RelativeLayout) itemView.findViewById(R.id.cardRL);
            statChildNameTV= (TextView) itemView.findViewById(R.id.statstics_childName);
            statTopicTV= (TextView) itemView.findViewById(R.id.statistics_topic);
            cImage= (ImageView) itemView.findViewById(R.id.statistics_childImage);

            statDayPercentTV= (TextView) itemView.findViewById(R.id.days_percentageTV);
            statAchievedPercentTV= (TextView) itemView.findViewById(R.id.achieved_percentageTV);
            statQuestionPrecentTV= (TextView) itemView.findViewById(R.id.questions_percentageTV);

            daysPBar = (ProgressBar) itemView.findViewById(R.id.days_progressbar);
            achievedPBar = (ProgressBar) itemView.findViewById(R.id.achieved_progressbar);
            questionsPBar = (ProgressBar) itemView.findViewById(R.id.questions_progressbar);
            bannerImageLLayout = (LinearLayout) itemView.findViewById(R.id.topLayout);

        }
    }
}