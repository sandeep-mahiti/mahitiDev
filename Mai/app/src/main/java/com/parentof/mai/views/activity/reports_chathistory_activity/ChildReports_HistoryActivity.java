package com.parentof.mai.views.activity.reports_chathistory_activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.adapters.reports_history_adapters.ChildReportsHistoryPagerAdater;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;


public class ChildReports_HistoryActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


    LinearLayout imageLayout;

    TextView title;
    TextView subTitle;
    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    private String TAG="ChildRepHisActi";
    GetSkillsRespModel getSKillRespModel;
    ArrayList<Question> qstnAnsList;
    AllDP selectedDP;

    GetSkillQuestionsRespModel skillQuestionsRespModel;
    private Child childBean;
    SkillData selectedSkill;//Datum selectedSkillObj;
    private String dpId;
    private SharedPreferences prefs;

    DatabaseHelper dbHelper;
    //selAllFromSkillsTable;
    List<SkillData> skillsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_reports__history);

        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        imageLayout = (LinearLayout) findViewById(R.id.imageLayout);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageLayout.setBackgroundTintList( ColorStateList.valueOf(Color.parseColor("#99000000")));
        }*/
        getValuesFromBundle();
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);

        title= (TextView) findViewById(R.id.rep_his_dpTitle);
        subTitle= (TextView) findViewById(R.id.rep_his_subTitle);
        title.setText(selectedDP.getName());
        subTitle.setText(selectedSkill.getDescription());
        if(selectedDP.getCover()!=null)
        Picasso.with(this).load(selectedDP.getCover()).into( new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageLayout.setBackground(new BitmapDrawable(bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Report"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        ChildReportsHistoryPagerAdater adapter = new ChildReportsHistoryPagerAdater(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.setupWithViewPager(viewPager);


        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);


    }

    private void getValuesFromBundle() {
        try{
            if(getIntent().getExtras()!=null) {
                Bundle b = getIntent().getExtras();
                qstnAnsList = b.getParcelableArrayList(Constants.BUNDLE_QSTNANSLIST);
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
        }catch(Exception e){
            Logger.logE(TAG, "valsFrmBndl", e);
        }
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
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.edit().putInt(PreferencesConstants.FROMBACK, 1).apply();
        finish();
    }
}
