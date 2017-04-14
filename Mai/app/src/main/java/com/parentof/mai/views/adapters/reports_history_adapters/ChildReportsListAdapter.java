package com.parentof.mai.views.adapters.reports_history_adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.ImprovementActiCallback;
import com.parentof.mai.activityinterfaces.TalkToMaiCallback;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeep HR on 30/01/17.
 */


//Class extending RecyclerviewAdapter
public class ChildReportsListAdapter extends RecyclerView.Adapter<ChildReportsListAdapter.ViewHolder> {

    //user id
    private int userId;
    private Context context;



    //ArrayList of messages object containing all the messages in the thread
    private ArrayList<String> messages;
    GetSkillsRespModel getSkillsRespModel;
    int totQstns;
    int answeredQstns;
    private int percent;
    TalkToMaiCallback talkToMaiCallback;
    ImprovementActiCallback improvementActiCallback;
    AllDP selectedDP;
    private List<Question> qstnsList;
    private String TAG = "ChildRepAdaptr";
    DatabaseHelper dbHelper;

    //Constructor
    public ChildReportsListAdapter(Context context, AllDP selectedDP, GetSkillsRespModel getSkillsRespModel, int totalQstns, int ansQstns, TalkToMaiCallback talkToMaiCallback, ImprovementActiCallback improvementActiCallback) {
        this.userId = userId;
        this.selectedDP = selectedDP;
        this.getSkillsRespModel = getSkillsRespModel;
        this.context = context;
        totQstns = totalQstns;
        answeredQstns = ansQstns;
        this.talkToMaiCallback = talkToMaiCallback;
        this.improvementActiCallback = improvementActiCallback;
    }

    //IN this method we are tracking the self message
    @Override
    public int getItemViewType(int position) {
        //getting message object of current position
        //CustomQstAnsModel message = messages.get(position);

        //else returning position
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Creating view
        View itemView;
        //if view type is self
        //if (viewType == SELF) {
        //Inflating the layout self
         dbHelper = new DatabaseHelper(context);
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reports_list_row, parent, false);
        /*} else {
            //else inflating the layout others
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_row_left, parent, false);
        }*/
        //returing the view

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        try {

            holder.openSkillTitleTV.setText(getSkillsRespModel.getData().get(position).getName());
            if (getSkillsRespModel.getData().get(position).getIsLocked().equalsIgnoreCase("false") && getSkillsRespModel.getData().get(position).getCompleted() > 0 && getSkillsRespModel.getData().get(position).getCompleted() < 100) {
                GetSkillQuestionsRespModel getSkillsQARespModel = dbHelper.selFromQAsTable(selectedDP.getId(), getSkillsRespModel.getData().get(position).getId());
                totQstns=getSkillsQARespModel.getData().getQuestions().size();
                answeredQstns = dbHelper.noOfQstnsAnswered(selectedDP.getId(), getSkillsRespModel.getData().get(position).getId());
                String qstnsLeft = String.valueOf(totQstns - answeredQstns).concat(" " + context.getString(R.string.qstnsLeft));
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.progressBar.setProgress(getSkillsRespModel.getData().get(position).getCompleted());
                holder.progressBarTV.setVisibility(View.VISIBLE);
                holder.progressBarTV.setText(String.valueOf(getSkillsRespModel.getData().get(position).getCompleted()).concat("%"));
                holder.openskillQuestnTV.setVisibility(View.VISIBLE);
                holder.openskillQuestnTV.setText(qstnsLeft);
                holder.improveNowTv.setVisibility(View.VISIBLE);
                //calPercAndSt(holder, qstnsCount, position);
            } else if (getSkillsRespModel.getData().get(position).getCompleted() == 100 && getSkillsRespModel.getData().get(position).getIsLocked().equalsIgnoreCase("false")) {
                holder.talk2maiTVBtn.setVisibility(View.GONE);
                holder.openskillQuestnTV.setVisibility(View.INVISIBLE);
                holder.improveNowTv.setVisibility(View.VISIBLE);
                holder.progressBar.setProgress(getSkillsRespModel.getData().get(position).getCompleted());
                holder.progressBarTV.setText(String.valueOf(getSkillsRespModel.getData().get(position).getCompleted()).concat("%"));
            } else if (getSkillsRespModel.getData().get(position).getCompleted() == 0 && getSkillsRespModel.getData().get(position).getIsLocked().equalsIgnoreCase("false")) {
                holder.talk2maiTVBtn.setVisibility(View.VISIBLE);
                if (getSkillsRespModel.getData().get(position).getQuestionsLeft() != null && getSkillsRespModel.getData().get(position).getQuestionsLeft() <= 0)
                    holder.openskillQuestnTV.setVisibility(View.GONE);
                else {
                    holder.openskillQuestnTV.setVisibility(View.VISIBLE);
                    holder.openskillQuestnTV.setText(getSkillsRespModel.getData().get(position).getQuestionsLeft() + " " + context.getString(R.string.qstnsLeft));
                }
                holder.improveNowTv.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.progressBar.setProgress(getSkillsRespModel.getData().get(position).getCompleted());
                holder.progressBarTV.setVisibility(View.VISIBLE);
                holder.progressBarTV.setText(String.valueOf(getSkillsRespModel.getData().get(position).getCompleted()).concat("%"));
            } else if (getSkillsRespModel.getData().get(position).getIsLocked().equalsIgnoreCase("true")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.progressBarTV.setBackground(context.getDrawable(R.drawable.locked100));
                    }
                }
                holder.openskillQuestnTV.setVisibility(View.INVISIBLE);
                holder.progressBarTV.setText("");
                holder.talk2maiTVBtn.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.GONE);
                holder.improveNowTv.setVisibility(View.GONE);
            }


            switch (position) {
                case 1:
                    // holder.openSkillTitleTV.setText(getSkillsRespModel.getData().get(position).getName());
                    holder.openSkillImage.setImageResource(R.drawable.approach100);
                    break;
                case 2:
                    // holder.openSkillTitleTV.setText(getSkillsRespModel.getData().get(position).getName());
                    holder.openSkillImage.setImageResource(R.drawable.happiness_normal);
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        holder.progressBarTV.setBackground(context.getDrawable(R.drawable.locked100));
                    }
                }*/
                    break;
                case 3:

                    // holder.openSkillTitleTV.setText(getSkillsRespModel.getData().get(position).getName());
                    holder.openSkillImage.setImageResource(R.drawable.everybody100);

                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.progressBarTV.setBackground(context.getDrawable(R.drawable.locked100));

                    }
                }*/
                    break;
            }
            holder.talk2maiTVBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    talkToMaiCallback.callChatActivity(getSkillsRespModel.getData().get(position), totQstns - answeredQstns);
                }
            });

            holder.openSkillLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               /* Intent i = new Intent(context, ImprovementPlanActivity.class);
                context.startActivity(i);*/
                }
            });
            holder.improveNowTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    improvementActiCallback.toImprovemntActivity(getSkillsRespModel.getData().get(position));

                }
            });

            //holder.textViewTime.setText(message.getName() + ", " + message.getSentAt());
        } catch (Exception e) {
            Logger.logE(TAG, " bindview Exe : ", e);
        }
    }

   /* public void calPercAndSt(ViewHolder holder, int qstnsCount, int position) {
        try{
            if ( qstnsCount > 0) {
                // qstnsList = dbHelper.selQAfrmSkillQATable(selectedDP.getId(), getSkillsRespModel.getData().get(position).getId(), true);
                // totQstns = qstnsList.size();
                //answeredQstns = dbHelper.noOfQstnsAnswered(selectedDP.getId(), getSkillsRespModel.getData().get(position).getId());
                Logger.logD(TAG, "\n ------------------------------------ > answeredQstns : "+answeredQstns +"\n ------------------------------------ > totQstns : "+totQstns);
                if (answeredQstns>0 && answeredQstns == totQstns) {
                    percent = 100;
                    holder.talk2maiTVBtn.setVisibility(View.GONE);
                    holder.improveNowTv.setVisibility(View.VISIBLE);
                } else {
                    double val = ((double) answeredQstns) / totQstns;
                    Logger.logD(TAG, "\n ------------------------------------ > val : "+val);
                    double val2 = val * 100;
                    Logger.logD(TAG, "\n ------------------------------------ > val2 : "+val2);
                    percent = (int) Math.ceil(val2);
                    Logger.logD(TAG, "\n ------------------------------------ > percent : "+percent);
                }


                String qstnsLeft = String.valueOf(totQstns - answeredQstns).concat(" " + context.getString(R.string.qstnsLeft));
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.progressBar.setProgress(percent);
                holder.progressBarTV.setVisibility(View.VISIBLE);
                holder.progressBarTV.setText(String.valueOf(percent).concat("%"));
                holder.openskillQuestnTV.setVisibility(View.VISIBLE);
                holder.openskillQuestnTV.setText(String.valueOf(qstnsLeft));
                holder.improveNowTv.setVisibility(View.VISIBLE);

            }else{
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.progressBar.setProgress(getSkillsRespModel.getData().get(position).getCompleted());
                holder.progressBarTV.setVisibility(View.VISIBLE);
                holder.progressBarTV.setText(String.valueOf(getSkillsRespModel.getData().get(position).getCompleted()).concat("%"));
                holder.improveNowTv.setVisibility(View.GONE);
                holder.talk2maiTVBtn.setVisibility(View.VISIBLE);
                holder.openskillQuestnTV.setVisibility(View.VISIBLE);
                holder.openskillQuestnTV.setText(String.valueOf(qstnsCount).concat(" "+" Questions Left"));

            }
        }catch(Exception e){
            Logger.logE(TAG, "calPercAndSt", e);
        }
    }*/


    @Override
    public int getItemCount() {
        return getSkillsRespModel.getData().size();
    }

    //Initializing views
    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout openSkillLL;
        TextView openSkillTitleTV;
        ImageView openSkillImage;
        TextView openskillQuestnTV;
        ProgressBar progressBar;
        TextView progressBarTV;
        TextView talk2maiTVBtn;
        TextView improveNowTv;

        ViewHolder(View rowView) {
            super(rowView);
            openSkillLL = (LinearLayout) rowView.findViewById(R.id.listV);
            openSkillTitleTV = (TextView) rowView.findViewById(R.id.open_skillTV);
            openSkillImage = (ImageView) rowView.findViewById(R.id.skillsIcon);
            openskillQuestnTV = (TextView) rowView.findViewById(R.id.openSkills_questionsTV);
            progressBar = (ProgressBar) rowView.findViewById(R.id.circProgrssbar);
            progressBarTV = (TextView) rowView.findViewById(R.id.progressTV);
            talk2maiTVBtn = (TextView) rowView.findViewById(R.id.talktoMaiTV);
            improveNowTv = (TextView) rowView.findViewById(R.id.improveNowTVBtn);
        }
    }
}