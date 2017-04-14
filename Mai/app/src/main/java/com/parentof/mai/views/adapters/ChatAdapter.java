package com.parentof.mai.views.adapters;

/**
 * Created by sandeep HR on 28/01/17.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.mikhaellopez.circularimageview.CircularImageView;
import com.parentof.mai.R;

import com.parentof.mai.model.getSkillQuestionsModel.Question;

import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.PreferencesConstants;

import java.util.ArrayList;


//Class extending RecyclerviewAdapter
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    //user id
    private int userId;
    private Context context;

    //Tag for tracking self message
    private int SELF = 321;
    Animation animFadeIn;

    //ArrayList of messages object containing all the messages in the thread
    //private  ArrayList<Datum> questions;
    ArrayList<Question> questions = new ArrayList<>();
    private SharedPreferences prefs;


    //Constructor
    public ChatAdapter(Context context, ArrayList<Question> questions ) {
        this.userId = userId;
        this.questions = questions;
        this.context = context;
    }

    //IN this method we are tracking the self message
    @Override
    public int getItemViewType(int position) {
        //getting message object of current position
        //  CustomQstAnsModel message = messages.get(position);

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
        prefs = context.getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_row_right, parent, false);
        /*} else {
            //else inflating the layout others
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_row_left, parent, false);
        }*/
        //returing the view
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //Adding messages to the views
        // final Datum message = questions.get(position);
        Question message = questions.get(position);
        animFadeIn = AnimationUtils.loadAnimation(context,
                R.anim.fade_in);

        if (message.getQuestion() != null && !"".equals(message.getQuestion())) {
            holder.maiChatIcon.setVisibility(View.VISIBLE);
            holder.maiMessage.setVisibility(View.VISIBLE);
            holder.maiMessage.setText(message.getQuestion());
            if (message.getAnswer() != null && !"".equalsIgnoreCase(message.getAnswer())) {
                holder.userChaticon.setVisibility(View.VISIBLE);
                boolean userImgFlag = CommonClass.getUserImageFromDirectory(holder.userChaticon, String.valueOf(prefs.getInt(Constants._ID,-1)));
                if (!userImgFlag) {
                    CommonClass.setGravatarUserImage(prefs.getString(PreferencesConstants.RELATION, ""), holder.userChaticon);
                }
                holder.userMessgae.setVisibility(View.VISIBLE);
                holder.userMessgae.setText(message.getAnswer());
            }
        } else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    // Code here will run in UI thread
                    holder.maiChatIcon.setVisibility(View.VISIBLE);
                    holder.maiChatIcon.setImageResource(R.drawable.day_selecteds);
                    holder.maiChatIcon.startAnimation(animFadeIn);

                    holder.maiChatIcon.setImageResource(R.drawable.type_message);
                    holder.maiChatIcon.startAnimation(animFadeIn);

                    holder.maiChatIcon.setImageResource(R.drawable.mai_profile);
                    holder.maiChatIcon.startAnimation(animFadeIn);

                    // holder.maiMessage.setText(message.getMessage());
                    holder.maiMessage.startAnimation(animFadeIn);
                }
            });
            holder.maiMessage.animate().alpha(1).setDuration(500);
        }

        //holder.textViewTime.setText(message.getName() + ", " + message.getSentAt());
    }


    @Override
    public int getItemCount() {
        return questions.size();
    }

    //Initializing views
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircularImageView userChaticon;
        TextView userMessgae;
        ImageView maiChatIcon;
        TextView maiMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            userChaticon = (CircularImageView) itemView.findViewById(R.id.user_chat_icon);
            userMessgae = (TextView) itemView.findViewById(R.id.user_chatTv);
            maiChatIcon = (ImageView) itemView.findViewById(R.id.mai_chat_icon);
            maiMessage = (TextView) itemView.findViewById(R.id.mai_chatTv);
            // textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }
}