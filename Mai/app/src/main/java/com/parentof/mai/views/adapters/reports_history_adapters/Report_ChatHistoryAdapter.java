package com.parentof.mai.views.adapters.reports_history_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.UpdateChatAnsCallback;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.activateskillmodel.chatmodel.CustomQstAnsModel;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by sandeep HR on 30/01/17.
 */

public class Report_ChatHistoryAdapter extends RecyclerView.Adapter<Report_ChatHistoryAdapter.ViewHolder> {

    private final Child childBean;
    private final AllDP selectedDp;

    //user id
    private int userId;
    private Context context;

    //Tag for tracking self message
    private int SELF = 321;

    //ArrayList of messages object containing all the messages in the thread
    private ArrayList<CustomQstAnsModel> messages;
    ArrayList<Question> qstnAnsList;
    private String TAG="repChtHisAdaptr";
    UpdateChatAnsCallback updateChatAnsCallback;

    String skillId;
    DatabaseHelper databaseHelper;


    //Constructor
    public Report_ChatHistoryAdapter(Context context, Child childBean, AllDP selectedDP,  ArrayList<Question> qstnAnsList, UpdateChatAnsCallback updateChatAnsCallback) {
        this.userId = userId;
        this.childBean=childBean;
        this.selectedDp=selectedDP;
        this.qstnAnsList = qstnAnsList;
        this.context = context;
        this.updateChatAnsCallback=updateChatAnsCallback;

    }

    //IN this method we are tracking the self message
    @Override
    public int getItemViewType(int position) {
        //getting message object of current position


        //If its owner  id is  equals to the logged in user id
       /* if (321 == userId) {
            //Returning self
            return SELF;
        }*/
        //else returning position
        return position;
    }

    @Override
    public Report_ChatHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Creating view
        View itemView;
        //if view type is self
        //if (viewType == SELF) {
        //Inflating the layout self
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_chathistory_row, parent, false);
        /*} else {
            //else inflating the layout others
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_row_left, parent, false);
        }*/
        //returing the view
        return new Report_ChatHistoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Report_ChatHistoryAdapter.ViewHolder holder, final int position) {
        //Adding messages to the views
        String answer="";
        holder.history_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.yesnoBtnLayout.setVisibility(View.VISIBLE);
                skillId=qstnAnsList.get(position).getSkillId();
            }
        });
        holder.yesTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer="Yes";
                holder.history_answer.setText(answer);
                holder.yesnoBtnLayout.setVisibility(View.GONE);
                holder.bottomLayout.setVisibility(View.GONE);
                updateAns(qstnAnsList.get(position).getId(),answer);

            }
        });
        holder.noTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer="No";
                holder.history_answer.setText(answer);
                holder.yesnoBtnLayout.setVisibility(View.GONE);
                holder.bottomLayout.setVisibility(View.GONE);
                updateAns(qstnAnsList.get(position).getId(),answer);

            }
        });
        holder.skipTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bottomLayout.setVisibility(View.VISIBLE);

            }
        });
        holder.hisNotApplcbleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer="No - Not Applicable";
                holder.history_answer.setText("Not Applicable");
                holder.yesnoBtnLayout.setVisibility(View.GONE);
                holder.bottomLayout.setVisibility(View.GONE);
                updateAns(qstnAnsList.get(position).getId(),answer);
            }
        });
        holder.hisNeedToObsrvTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer="Under observation";
                holder.history_answer.setText(answer);
                holder.yesnoBtnLayout.setVisibility(View.GONE);
                holder.bottomLayout.setVisibility(View.GONE);
                updateAns(qstnAnsList.get(position).getId(),answer);
            }
        });

        StringUtils stringUtils=new StringUtils();
        databaseHelper=new DatabaseHelper(context);
        String skillname=databaseHelper.selSkillNameFromSKills(childBean.getChild().getId(), selectedDp.getId(), qstnAnsList.get(position).getSkillId());
        String qstn=stringUtils.replaceLabel(childBean.getChild(), selectedDp.getName(), skillname,context,qstnAnsList.get(position).getQuestion(),"");
        holder.history_question.setText(qstn);

        String ans="";
        if(qstnAnsList.get(position).getAnswer().equalsIgnoreCase("No - Not Applicable"))
            ans="Not Applicable";
        else
            ans=qstnAnsList.get(position).getAnswer().toUpperCase();

        holder.history_answer.setText(ans);

    }

    private void updateAns( String qstnId, String ans) {
        try{
            updateChatAnsCallback.upChatAnsAgain( skillId, qstnId, ans);
        }catch(Exception e){
            Logger.logE(TAG, "updateAns ",e);

        }

    }


    @Override
    public int getItemCount() {
        return qstnAnsList.size();
    }

    //Initializing views
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView history_question;
        TextView history_answer;
        TextView history_change;
        LinearLayout yesnoBtnLayout;
        TextView yesTvBtn;
        TextView noTvBtn;
        TextView skipTvBtn;
        LinearLayout bottomLayout;
        TextView hisNotApplcbleTV;
        TextView hisNeedToObsrvTV;

        public ViewHolder(View itemView) {
            super(itemView);
            history_question = (TextView) itemView.findViewById(R.id.history_questionTV);
            history_answer = (TextView) itemView.findViewById(R.id.history_yesnoTV);
            history_change = (TextView) itemView.findViewById(R.id.history_changeTV);
            yesnoBtnLayout= (LinearLayout) itemView.findViewById(R.id.yesno_layout);
            yesTvBtn=(TextView) itemView.findViewById(R.id.yesTextBtn);
            noTvBtn=(TextView) itemView.findViewById(R.id.noTextBtn);
            skipTvBtn=(TextView) itemView.findViewById(R.id.skipTextBtn);
            bottomLayout= (LinearLayout) itemView.findViewById(R.id.hisBbottom_layout);
            hisNotApplcbleTV= (TextView) itemView.findViewById(R.id.hisNotApplicableTV);
            hisNeedToObsrvTV= (TextView) itemView.findViewById(R.id.hisNeedToObsrvTV);

            // textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }

}
