package com.parentof.mai.views.activity.dpchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.UpdateSkillQAnsCallback;
import com.parentof.mai.api.apicalls.UpdateSkillAnswerAPI;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.UpdateSkillAnswerRespModel;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.StringUtils;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.utils.Utility;
import com.parentof.mai.views.activity.reports_chathistory_activity.ChildReports_HistoryActivity;
import com.parentof.mai.views.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, UpdateSkillQAnsCallback {


    //Recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;



    TextView yesTVBtn;
    TextView noTVBtn;
    TextView skipTVBtn;
    TextView yesTVDot;
    TextView noTVDot;
    TextView skipTVDot;

    LinearLayout llBottomLayout;
    TextView notApplicableTVBtn;
    TextView needToObserveTVBtn;
    private String TAG = " chtAct ";

    LinearLayout yesNoSkipLayout;
    LinearLayout moreQuestnLlayout;

    TextView doingGrTV;
    View beloeBarview;
    TextView moreQstnsTV;
    TextView viewReportTV;


    private int qstnCount = 0;
    private int size = 0;
    private int moreQuestions = 0;
    private String userAns;
    GetSkillQuestionsRespModel skillQuestionsRespModel;
    GetSkillsRespModel getSKillRespModel;
    private Child childBean;
    SkillData selectedSkill;
    private String dpId;
    private SharedPreferences prefs;

    DatabaseHelper dbHelper;
    private AllDP selectedDP;
    private boolean viewRep=false;

    List<Question> qstnsAnsList;
    private ArrayList<Question> qstnToAdapterList;
    TextView dateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        dbHelper=new DatabaseHelper(this);
        qstnToAdapterList=new ArrayList<>();
        getValuesFromBundle();
        fillQAfromDB();

        //Adding toolbar to activity

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(selectedDP.getName());
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

        }
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        dateTv= (TextView) findViewById(R.id.dateTV);

        dateTv.setText(Utility.currentDateTime());


        yesTVBtn = (TextView) findViewById(R.id.yesTextBtn);
        yesTVBtn.setOnClickListener(this);
        noTVBtn = (TextView) findViewById(R.id.noTextBtn);
        noTVBtn.setOnClickListener(this);
        skipTVBtn = (TextView) findViewById(R.id.skipTextBtn);
        skipTVBtn.setOnClickListener(this);


        yesNoSkipLayout = (LinearLayout) findViewById(R.id.layout_below_list);
        llBottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);

        //answer more questions and view report layout and its elements
        moreQuestnLlayout = (LinearLayout) findViewById(R.id.answer_more_layout);
        moreQuestnLlayout.setVisibility(View.GONE);
        doingGrTV = (TextView) findViewById(R.id.doing_Great_TV);
        beloeBarview = findViewById(R.id.doing_great_belowBar_view);
        moreQstnsTV = (TextView) findViewById(R.id.more_questions_TV);
        moreQstnsTV.setOnClickListener(this);
        viewReportTV = (TextView) findViewById(R.id.view_report_tv);
        viewReportTV.setOnClickListener(this);

        yesTVDot = (TextView) findViewById(R.id.yesDotTv);
        noTVDot = (TextView) findViewById(R.id.noDotTv);
        skipTVDot = (TextView) findViewById(R.id.skipDotTv);


        notApplicableTVBtn = (TextView) findViewById(R.id.notApplicableTV);
        notApplicableTVBtn.setOnClickListener(this);
        needToObserveTVBtn = (TextView) findViewById(R.id.needToObsrvTV);
        needToObserveTVBtn.setOnClickListener(this);


        //Initializing recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.chatList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //fillData();


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.edit().putInt(PreferencesConstants.FROMBACK, 1).apply();
        finish();
    }

    private void getValuesFromBundle() {
        try {
            if (getIntent().getExtras() != null) {
                Bundle b = getIntent().getExtras();

                skillQuestionsRespModel = b.getParcelable(Constants.BUNDLE_QUESTOBJ);
                getSKillRespModel = b.getParcelable(Constants.BUNDLE_SKILLSOBJ);
                selectedSkill=b.getParcelable(Constants.BUNDLE_SELSKILLOBJ);
                childBean=b.getParcelable(Constants.BUNDLE_CHILDOBJ);
                selectedDP=b.getParcelable(Constants.BUNDLE_SELDPOBJ);
                if (selectedDP != null) {
                    dpId=selectedDP.getId();
                }else{
                    ToastUtils.displayToast("Please comeback later to continue",this);
                }

            }
        } catch (Exception e) {
            Logger.logE(TAG, "valsFrmBndl", e);
        }
    }

    private void fillQAfromDB(){
        try{

            qstnsAnsList=dbHelper.selQAfrmSkillQATable(childBean.getChild().getId(), selectedDP.getId(), selectedSkill.getId(), 1);
            size = qstnsAnsList.size();
            fillData();
        }catch(Exception e){
            Logger.logE(TAG, "fillQAfromDB", e);
        }
    }


    //method to scroll the recyclerview to bottom
    private void scrollToBottom() {
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() > 1)
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
    }

    private void fillData() {

        try {
            //Initializing message arraylist
           // qstnToAdapterListfromDB=dbHelper.selQAfrmSkillQATable(dpId,selectedSkill.getId(), true );


            if (qstnCount < size && !viewRep) {
                Question qstnAnsObj = new Question();
                qstnAnsObj.setId(qstnsAnsList.get(qstnCount).getId());
                StringUtils stringUtils=new StringUtils();
                qstnAnsObj.setQuestion(stringUtils.replaceLabel(childBean.getChild(), selectedDP.getName(), selectedSkill.getName(),this,qstnsAnsList.get(qstnCount).getQuestion(), ""));
                qstnAnsObj.setIndicator(qstnsAnsList.get(qstnCount).getIndicator());
                qstnAnsObj.setAnswer(qstnsAnsList.get(qstnCount).getAnswer());
                qstnToAdapterList.add(qstnAnsObj);
            }else{
                dbHelper.updateSkillsCompPer(childBean.getChild().getId() ,selectedDP.getId(), selectedSkill.getId(), calPerc());
                getSKillRespModel=dbHelper.selAllFromSkillsTable(childBean.getChild().getId(), selectedDP.getId());;
               moveToNextActivity();

            }


        } catch (Exception e) {
            Logger.logE(TAG, " FilldumData : ", e);
        }

    }

    private int calPerc() {
        int percent=0;
        try{

            if (qstnToAdapterList.size()>0 && qstnToAdapterList.size() == qstnsAnsList.size()) {
                percent = 100;

            } else {
                double val = ((double) qstnToAdapterList.size()) / qstnsAnsList.size();
                Logger.logD(TAG, "\n ------------------------------------ > val : "+val);
                double val2 = val * 100;
                Logger.logD(TAG, "\n ------------------------------------ > val2 : "+val2);
                percent = (int) Math.ceil(val2);
                Logger.logD(TAG, "\n ------------------------------------ > percent : "+percent);
            }
            return percent;
        }catch (Exception e){
            Logger.logE(TAG, " calPerc : ", e);
        }
        return percent;
    }

    private void moveToNextActivity() {
        try {
            Bundle b = new Bundle();
            b.putParcelable(Constants.BUNDLE_QUESTOBJ, skillQuestionsRespModel);
            b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSKillRespModel);
            b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
            b.putParcelableArrayList(Constants.BUNDLE_QSTNANSLIST, qstnToAdapterList);
            Intent i = new Intent(this, ChildReports_HistoryActivity.class);
            i.putExtras(b);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }catch(Exception e){
            Logger.logE(TAG, " mvTNxtAct : ", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new ChatAdapter(this, /*(ArrayList<Datum>) activateSkillRespModel.getData()*/ qstnToAdapterList);
        recyclerView.setAdapter(adapter);
        scrollToBottom();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                prefs.edit().putInt(PreferencesConstants.FROMBACK, 1).apply();
                finish();
                return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.yesTextBtn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        yesTVBtn.setBackground(getDrawable(R.drawable.chatbtnselected));
                        noTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                        skipTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                    }
                }
                //yesTVDot.setVisibility(View.VISIBLE);
                //noTVDot.setVisibility(View.INVISIBLE);
                skipTVDot.setVisibility(View.INVISIBLE);
                llBottomLayout.setVisibility(GONE);
                //qstnCount = qstnCount + 1;
                userAns = "Yes";
                addAnstoList();
                break;
            case R.id.noTextBtn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        yesTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                        noTVBtn.setBackground(getDrawable(R.drawable.chatbtnselected));
                        skipTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                    }
                }
                //noTVDot.setVisibility(View.VISIBLE);
                //yesTVDot.setVisibility(View.INVISIBLE);
                skipTVDot.setVisibility(View.INVISIBLE);
                llBottomLayout.setVisibility(GONE);
                //qstnCount = qstnCount + 1;
                userAns = "No";
                addAnstoList();
                break;
            case R.id.skipTextBtn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        yesTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                        noTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                        skipTVBtn.setBackground(getDrawable(R.drawable.chatbtnselected));
                    }
                }
                skipTVDot.setVisibility(View.VISIBLE);
                noTVDot.setVisibility(View.INVISIBLE);
                yesTVDot.setVisibility(View.INVISIBLE);
                llBottomLayout.setVisibility(View.VISIBLE);
                ///qstnCount=qstnCount+1;
                //userAns="Skipped";
                //addAnstoList(userAns);
                break;

            case R.id.notApplicableTV:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        notApplicableTVBtn.setBackground(getDrawable(R.drawable.chatbtnselected));
                        needToObserveTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                    }
                }
                skipTVDot.setVisibility(View.INVISIBLE);
                noTVDot.setVisibility(View.INVISIBLE);
                yesTVDot.setVisibility(View.INVISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        yesTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                        noTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                        skipTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                    }
                }

                llBottomLayout.setVisibility(GONE);
                //qstnCount = qstnCount + 1;
                userAns = "No%20-%20Not%20Applicable";
                addAnstoList();
                // waitNGotoStatistics();
                break;
            case R.id.needToObsrvTV:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        needToObserveTVBtn.setBackground(getDrawable(R.drawable.chatbtnselected));
                        notApplicableTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                    }
                }
                skipTVDot.setVisibility(View.INVISIBLE);
                noTVDot.setVisibility(View.INVISIBLE);
                yesTVDot.setVisibility(View.INVISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        yesTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                        noTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                        skipTVBtn.setBackground(getDrawable(R.drawable.begin_border));
                    }
                }

                llBottomLayout.setVisibility(GONE);
                //qstnCount = qstnCount + 1;
                userAns = "To%20be%20Observed";
                addAnstoList();
                break;

            case R.id.more_questions_TV:
                moreQuestnLlayout.setVisibility(View.INVISIBLE);
                yesNoSkipLayout.setVisibility(View.VISIBLE);
                fillAnsAndNextQstn();
                //addAnstoList();
                break;
            case R.id.view_report_tv:
                //moveToNextActivity();
                viewRep=true;
                fillAnsAndNextQstn();
                break;
            default:
                break;
        }
    }

    private void scrollMyListViewToBottom() {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
            }
        });
    }

    private void addAnstoList() {

        try {

                //TODO uncomment below 2 lines
                if (qstnCount != 8) {
                 fillAnsAndNextQstn();
                } else {
                    //moveToStatistics();
                    doingGrTV.setText(childBean.getChild().getFirstName().concat(" seems to be doing Great."));
                    moreQuestnLlayout.setVisibility(View.VISIBLE);

                    yesNoSkipLayout.setVisibility(View.GONE);
                }

        } catch (Exception e) {
            Logger.logE(TAG, " addAnstoList : ", e);
        }

    }

    private void fillAnsAndNextQstn() {
        try {
            String ans="";
            if(userAns.equalsIgnoreCase("No%20-%20Not%20Applicable"))
                ans="Not Applicable";
            else if(userAns.equalsIgnoreCase("To%20be%20Observed"))
                ans="To be Observed";
            else
                ans=userAns;
            qstnToAdapterList.get(qstnCount).setAnswer(ans); //making useranswer to uppercase
            if (CheckNetwork.checkNet(this)) {
                UpdateSkillAnswerAPI.updateAnswer(this, String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), dpId, selectedSkill.getId(), qstnToAdapterList.get(qstnCount).getId(), userAns, this);
            }else{
                Logger.logD(TAG, " Check Internet and try again ");
            }
          /*  adapter.notifyDataSetChanged();
            scrollMyListViewToBottom();*/
        }catch(Exception e){
            Logger.logE(TAG, " filAnsAnNxtQstn : ", e);
        }
    }

   /* private void loadMoreQuestion(String ans) {
        try {

        }catch(Exception e){
            Logger.logE(TAG, " loadMoreQues : ", e);
        }
    }*/

    private void waitNGotoStatistics() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    moveToNextActivity();
                }
            }
        };
        timerThread.start();
    }

    /*private void moveToStatistics() {
        Intent i = new Intent(this, ChildReports_HistoryActivity.class);
        startActivity(i);
    }*/

    @Override
    public void skillAnsResp(UpdateSkillAnswerRespModel skillAnswerRespModel) {
        try {
            if (skillAnswerRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                dbHelper=new DatabaseHelper(this);
                MyProgress.show(this,"","");
                String ans="";
                if(userAns.equalsIgnoreCase("No%20-%20Not%20Applicable"))
                    ans="Not Applicable";
                else if(userAns.equalsIgnoreCase("To%20be%20Observed"))
                    ans="TO be Observed";
                else
                    ans=userAns;
                dbHelper.updateAnsToSkilQstnTab(dpId, selectedSkill.getId(), qstnToAdapterList.get(qstnCount).getId(), ans); //qstnAnsList, dpId, selectedSkill.getId()
                MyProgress.CancelDialog();
                qstnCount = qstnCount + 1;
                fillData();
                adapter.notifyDataSetChanged();
                scrollMyListViewToBottom();
            } else {
                ToastUtils.displayToast("Couldn't update answer, Please try again!", this);
            }
        }catch (Exception e){
            Logger.logE(TAG, " skillAnsResp : ", e);
        }
    }

   /* private boolean sendChatMessage() {
        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
        chatText.setText("");
        side = !side;
        return true;
    }*/
}
