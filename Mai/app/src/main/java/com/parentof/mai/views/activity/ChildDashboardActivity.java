package com.parentof.mai.views.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.AddReminderCallBack;
import com.parentof.mai.activityinterfaces.AllActiveInterventionCallBack;
import com.parentof.mai.activityinterfaces.StatisticsRespCallBack;
import com.parentof.mai.activityinterfaces.UpdateTaskYesNoCallBack;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.allActivatedInterventionModel.AllActiveInterventionModel;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.getchildrenmodel.GetChildrenRespModel;
import com.parentof.mai.model.statisticsmodel.StatisticsRespModel;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.activity.childProfile.ChildProfileActivity;
import com.parentof.mai.views.activity.childstatistics.ChildStatistics;
import com.parentof.mai.views.activity.ipTabs.IpsFragment;
import com.parentof.mai.views.activity.reminder.ReminderFragment;
import com.parentof.mai.views.adapters.ViewPagerAdapter;
import com.parentof.mai.views.fragments.childTabs.HomeFragment;
import com.parentof.mai.views.fragments.dpfrgamant.ItemFragment;

public class ChildDashboardActivity extends AppCompatActivity implements AddReminderCallBack, StatisticsRespCallBack, AllActiveInterventionCallBack, UpdateTaskYesNoCallBack /*SelectedDpTOActivtyCallback, GetDPSkillsRespCallback, ActivateSkillRespCallback, GetSkillQuestionsCallback, DecisionPointsCallback*/ {


    Child childBean;
    String cIdToUpdate;
    private String childId;
    private String TAG = "chldDshBrdActi";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int[] tabIcon;
    String[] tabArray;
    int[] tabSelectedArrayIcons = new int[]{R.drawable.home_select, R.drawable.dps_select,
            R.drawable.statistics_select, R.drawable.ips_select,
            /*R.drawable.health_select,*/ R.drawable.reminder_selected}; // image sof that tabs

    private SharedPreferences prefs;
    private DatabaseHelper databaseHelper;


    private GetChildrenRespModel getChildrenRespModel;

    private int categorySelectedVal;//set the view pager

    LinearLayout rlChildName;
    TextView childName;
    CircularImageView childImage;
    ViewPagerAdapter viewPagerAdapter;

    TextView childAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_dashboard2);
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        childDetailsFromBundle();
        toolbarInit();
        databaseHelper = new DatabaseHelper(this);

        // DecisionPointsAPI.getDPs(this, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, this);

    /*    databaseHelper = new DatabaseHelper(this);
        DecisionPointsAPI.getDPs(this, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, this );*/
        rlChildName = (LinearLayout) findViewById(R.id.ll_childName);
        childName = (TextView) findViewById(R.id.ds_childName);
        childImage = (CircularImageView) findViewById(R.id.ds_childImage);
        childAge = (TextView) findViewById(R.id.ds_childYear);
        setChildImage();
        if (childBean.getChild().getDob() != null)
            setChildAge();

        rlChildName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNextActivity();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabArray = getResources().getStringArray(R.array.tab_names); // names of the tabs
        tabIcon = new int[]{R.drawable.home_normal, R.drawable.dps_normal, R.drawable.statistics_normal, R.drawable.ips_normal, /*R.drawable.health_normal,*/ R.drawable.reminder_normal}; // image sof that tabs

        //viewPager.setCurrentItem(4);
        //set the view pager
        MyProgress.show(this, "", "");
        setupViewPager(viewPager);
        viewPager.setCurrentItem(categorySelectedVal);
        setallTabs();
        MyProgress.CancelDialog();
       /* if (CheckNetwork.checkNet(this))
            AllActiveApiCall.allActiveInterventionApi(this, String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId(), this);*/

    }

    void setChildImage() {
        if (childBean != null && childBean.getChild().getId() != null) {
            boolean imgFlag = CommonClass.getImageFromDirectory(childImage, childBean.getChild().getId());
            if (!imgFlag) {
                childImage.setImageBitmap(CommonClass.StringToBitMap(prefs.getString("child_image", "")));
            }
            if (childBean != null && childBean.getChild().getFirstName() != null)
                childName.setText(childBean.getChild().getFirstName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setChildImage();
        //  setupViewPager(viewPager);

    }

    void setallTabs() {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                setTabIcons();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
                    MyProgress.show(ChildDashboardActivity.this, "", "");
                    int position = tab.getPosition();
                    tabLayout.getTabAt(position).setIcon(tabSelectedArrayIcons[position]);
                    tabLayout.setTabTextColors(CommonClass.getColor(ChildDashboardActivity.this, R.color.tab_text_color), CommonClass.getColor(ChildDashboardActivity.this, R.color.white));
                    MyProgress.CancelDialog();
                } catch (Exception e) {
                    Logger.logE("on tab", "tab--", e);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {

                    int position = tab.getPosition();
                    tabLayout.getTabAt(position).setIcon(tabIcon[position]);
                    tabLayout.setTabTextColors(CommonClass.getColor(ChildDashboardActivity.this, R.color.tab_text_color), CommonClass.getColor(ChildDashboardActivity.this, R.color.white));

                } catch (Exception e) {
                    Logger.logE("on tab", "tab--", e);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //   Logger.logD("onPageScrolled", "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Logger.logD("onPageSelected", "onPageSelected" + position);
                viewPager.getCurrentItem();
                try {
                    MyProgress.show(ChildDashboardActivity.this, "", "");
                    tabLayout.getTabAt(position).setIcon(tabSelectedArrayIcons[position]);
                    tabLayout.setTabTextColors(CommonClass.getColor(ChildDashboardActivity.this, R.color.tab_text_color), CommonClass.getColor(ChildDashboardActivity.this, R.color.white));
                    MyProgress.CancelDialog();
                } catch (Exception e) {
                    Logger.logE("on tab", "tab--", e);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Logger.logD("onPageScrollStateChanged", "onPageScrollStateChanged" + state);
            }
        });
    }

    //set icons for the view pager
    private void setTabIcons() {
        try {
            for (int i = 0; i < tabArray.length; i++) {
                tabLayout.getTabAt(i).setIcon(tabIcon[i]);
            }
            tabLayout.getTabAt(categorySelectedVal).setIcon(tabSelectedArrayIcons[categorySelectedVal]);
        } catch (Exception e) {
            Logger.logE("on tab", "tab--", e);
        }
    }


    private void setupViewPager(ViewPager viewPager) {

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(HomeFragment.newInstance(childBean), getResources().getString(R.string.dashboard_home));
        viewPagerAdapter.addFragment(ItemFragment.newInstance(), getResources().getString(R.string.dashboard_dp));
        viewPagerAdapter.addFragment(new ChildStatistics(), getResources().getString(R.string.dashboard_statistics));
        viewPagerAdapter.addFragment(IpsFragment.newInstance(childBean), getResources().getString(R.string.dashboard_ip));
        //  viewPagerAdapter.addFragment(HealthReportFragment.newInstance(childBean), getResources().getString(R.string.dashboard_health));
        viewPagerAdapter.addFragment(new ReminderFragment().newInstance(childBean), getResources().getString(R.string.reminder));
        viewPager.setAdapter(viewPagerAdapter);

    }


    void setChildAge() {
        try {
            String childDob = CommonClass.getDate(childBean.getChild().getDob());
            Logger.logD(Constants.PROJECT, "CHild AGE+" + childDob);
            String[] ageArray = childDob.split("/");
            int month = Integer.parseInt(ageArray[0]);
            int day = Integer.parseInt(ageArray[1]);
            int year = Integer.parseInt(ageArray[2]);
            Logger.logD(Constants.PROJECT, "child year+" + year + "-day--" + day);
            int finalAge = CommonClass.getChildAge(year, month, day);
            childAge.setText(String.valueOf(finalAge).concat(" Years"));
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, "Exc", e);
        }

    }

    void toolbarInit() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            /*if (toolbar != null) {
                ImageView imageView = (ImageView) toolbar.findViewById(R.id.parentIcon);
                imageView.setImageResource(R.drawable.ic_action_overflow);
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass.loadSettingsActivity(ChildDashboardActivity.this);
                    }
                });

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void childDetailsFromBundle() {
        try {
            if (getIntent().getExtras() != null) {
                cIdToUpdate = getIntent().getStringExtra(Constants.UPDATE_CHILD_ID);
                Bundle b = getIntent().getExtras();
                if (b != null && b.getParcelable(Constants.SELECTED_CHILD) != null) {
                    childBean = b.getParcelable(Constants.SELECTED_CHILD);
                    childId = b.getString(Constants.UPDATE_CHILD_ID);
                    // decisionPointsRespModel = b.getParcelable(Constants.DECISION_POINTS);
                    categorySelectedVal = b.getInt(Constants.SELECTED_CATEGORY);
                    getChildrenRespModel = b.getParcelable(Constants.CHILD_LIST_BEAN);

                }
            }
        } catch (Exception e) {
            Logger.logE(TAG, " gtChldinfo ", e);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    void navigateToNextActivity() {
        try {
            Bundle b = new Bundle();
            if (childBean != null && childBean.getId() == null) {
                Logger.logD(TAG, "Child data not available");
            } else {
                b.putParcelable(Constants.SELECTED_CHILD, childBean);
                Intent i = new Intent(this, ChildProfileActivity.class);
                i.putExtras(b);
                startActivityForResult(i, 199);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "onBndvwHldr :", e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == 199) {
                Logger.logD("BackToParent", "onActivityResult, requestCode: " + requestCode + ", resultCode: " + resultCode);
                if (data == null) {
                    finish();
                    return;
                }
                Bundle bundle = data.getExtras();
                childBean = bundle.getParcelable(Constants.BUNDLE_CHILDOBJ);
                finish();
                startActivity(getIntent());
               /* viewPagerAdapter.getItemPosition(childBean);
                viewPager.getAdapter().notifyDataSetChanged();*/
                if (childBean != null && childBean.getChild() != null)
                    Logger.logD(Constants.PROJECT, "OnActivty-->" + childBean.getChild().getBloodGroup());
                setChildAge();

            }
        } catch (Exception e) {
            Logger.logE(TAG, "onActivityResult", e);
        }

    }

    @Override
    public void responseReminderCallBack(int pos) {
        viewPager.setCurrentItem(5);
    }

    @Override
    public void statisticsCallBack(StatisticsRespModel statisticsRespModel, String dpId) {
        try {
            if (statisticsRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                Logger.logD(TAG, statisticsRespModel.getStatus());
                ToastUtils.displayToast(statisticsRespModel.getStatus(), this);
                //TODO update statistics DB
                databaseHelper.delFromStatisticsTable(String.valueOf(prefs.getInt(Constants._ID, -1)), childId, dpId);
                String dpName = databaseHelper.selDPNameFromTable(dpId);
                String dpCoverImage = databaseHelper.selDPCoverFromTable(dpId);
                databaseHelper.insertStatisticsDetails(statisticsRespModel, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, dpId, dpName, dpCoverImage);
            } else {
                Logger.logD(TAG, statisticsRespModel.getStatus());
                ToastUtils.displayToast(statisticsRespModel.getStatus(), this);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "statisticsCallBack", e);
        }

    }

    @Override
    public void responseAllActiveIntervention(AllActiveInterventionModel allActiveInterventionModel) {
       /* if (allActiveInterventionModel != null && allActiveInterventionModel.getData()!=null && allActiveInterventionModel.getData().size() > 0)
            databaseHelper.insertListImprovementPlanTable(allActiveInterventionModel.getData(), String.valueOf(prefs.getInt(Constants._ID, -1)), childBean.getChild().getId());*/
    }

    @Override
    public void methodUpdateYesNo(String iID) {
        Logger.logD(Constants.PROJECT, "interventionId" + iID);
    }


   /* @Override
    public void methodUpdateYesNo() {
        setupViewPager(viewPager);
       // viewPagerAdapter.addFragment(HomeFragment.newInstance(childBean), getResources().getString(R.string.dashboard_home));
    }*/
}
