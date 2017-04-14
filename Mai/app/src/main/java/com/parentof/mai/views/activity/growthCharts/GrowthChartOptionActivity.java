package com.parentof.mai.views.activity.growthCharts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.db.DbConstants;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GrowthChartOptionActivity extends AppCompatActivity {

    @Bind(R.id.img_height)
    ImageView imgHeight;

    @Bind(R.id.img_weight)
    ImageView imgWeight;

    @Bind(R.id.img_circumfarence)
    ImageView imgCircumfarence;

    /*@Bind(R.id.rl_healthReport)
    RelativeLayout rlHealthReport;

    @Bind(R.id.rl_immunization)
    RelativeLayout rlImmunization;*/

    DatabaseHelper databaseHelper;
    Child childBean;
    String childId;
    private String TAG = "GrowthChartOptionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_option);
        ButterKnife.bind(this);
        toolbarInit();
        childBean = (Child) getIntent().getParcelableExtra(Constants.SELECTED_CHILD);
        if (childBean != null && childBean.getId() != null)
            childId = childBean.getId();
        databaseHelper = new DatabaseHelper(this);

    }

    void toolbarInit() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (toolbar != null) {
                toolbar.setTitle(getResources().getString(R.string.title_activity_chart_option));
                toolbar.setTitleTextColor(CommonClass.getColor(this, R.color.white));
                ImageView imageView = (ImageView) toolbar.findViewById(R.id.parentIcon);
                imageView.setImageResource(R.drawable.ic_action_overflow);
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass.loadSettingsActivity(GrowthChartOptionActivity.this);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @OnClick(R.id.img_height)
    void imgHeight() {
        navigateNextActivity(DbConstants.Height);
    }

    @OnClick(R.id.img_weight)
    void imgWeight() {
        navigateNextActivity(DbConstants.Weight);
    }

    @OnClick(R.id.img_circumfarence)
    void imgCircumfarence() {
        navigateNextActivity(DbConstants.Head_Circumfarence);

    }

    /* @OnClick(R.id.rl_healthReport)
     void healthReport() {
        // navigateToNextActivity(HealthReportActivity.class);
     }

     @OnClick(R.id.rl_immunization)
     void immunization() {
        // navigateToNextActivity(ImmunizationActivity.class);

     }
 */
    void navigateNextActivity(String option) {
        Intent i = new Intent(this, GraphListActivity.class);
        i.putExtra(DbConstants.SELECTED_OPTION, option);
        i.putExtra(Constants.CHILD_LIST_BEAN, childBean);
        startActivity(i);
    }




}